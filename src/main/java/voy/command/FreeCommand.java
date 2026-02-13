package voy.command;

import static voy.command.CommandType.FREE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import voy.storage.Storage;
import voy.task.Task;
import voy.task.TaskList;
import voy.task.TaskType;
import voy.ui.format.UiMessageFormatter;

/**
 * Represents a time interval with a start and end {@link LocalDateTime}.
 *
 * <p>
 * Used internally by {@link FreeCommand} to represent busy time blocks
 * when searching for available free slots.
 */
class TimeBlock {
    private final LocalDateTime start;
    private final LocalDateTime end;

    TimeBlock(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    LocalDateTime getStart() {
        return start;
    }

    LocalDateTime getEnd() {
        return end;
    }

    /**
     * Formats a {@link LocalDateTime} into a user-friendly string.
     *
     * @param dateTime the date-time to format
     * @return formatted date-time string (e.g., Feb 13 2026 09:00AM)
     */

    public static String displayDateTime(LocalDateTime dateTime) {
        return dateTime.format(
                DateTimeFormatter.ofPattern("MMM d yyyy hh:mma")
        );
    }
}

/**
 * Finds the earliest available free time slot within the next 7 days.
 *
 * <p>
 * The command searches each day from 9:00 AM to 10:00 PM and
 * returns the earliest time slot that satisfies the requested duration.
 *
 * <p>
 * Only {@code EVENT} tasks are considered as busy time blocks.
 */
public class FreeCommand implements Command {
    private static final int DAY = 7;
    private long durationMinutes;

    public FreeCommand(long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * Executes the FREE command.
     *
     * <p>
     * Searches for the earliest free time slot within the next 7 days.
     * If a suitable slot is found, it is returned in a formatted message.
     * Otherwise, a message indicating no availability is returned.
     *
     * @param tasks the current task list
     * @param storage the storage handler (unused in this command)
     * @return formatted response message
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        ArrayList<Task> list = tasks.getTasks();

        if (list.isEmpty()) {
            return UiMessageFormatter.formatResponse("No tasks yet.");
        }

        for (int i = 0; i < DAY; i++) {
            LocalDateTime base = LocalDateTime.now().plusDays(i);
            LocalDateTime dayStart = base.withHour(9).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime dayEnd = base.withHour(22).withMinute(0).withSecond(0).withNano(0);

            ArrayList<TimeBlock> busyBlocks = buildBusyBlocks(list, dayStart, dayEnd);
            System.out.println("dayStart=" + dayStart + " dayEnd=" + dayEnd);
            System.out.println("durationMinutes=" + durationMinutes);
            System.out.println("busyBlocks size=" + busyBlocks.size());
            LocalDateTime slotStart = findSlot(busyBlocks, dayStart, dayEnd, durationMinutes);

            if (slotStart != null) {
                return UiMessageFormatter.formatResponse(
                        "Free slot found :\n" + TimeBlock.displayDateTime(slotStart)
                                + " to " + TimeBlock.displayDateTime(slotStart.plusMinutes(durationMinutes)));
            }
        }
        return UiMessageFormatter.formatResponse("No free slot found.");
    }

    /**
     * Builds and returns a list of busy time blocks for a given day.
     *
     * <p>
     * Only {@code EVENT} tasks that overlap with the specified day window
     * are included. Overlapping intervals are merged before returning.
     *
     * @param list the list of tasks
     * @param dayStart the start of the day window
     * @param dayEnd the end of the day window
     * @return merged list of busy time blocks
     */
    private static ArrayList<TimeBlock> buildBusyBlocks(
            ArrayList<Task> list, LocalDateTime dayStart, LocalDateTime dayEnd) {
        ArrayList<TimeBlock> busyBlocks = new ArrayList<>();
        for (Task task : list) {
            if (task.getTaskType() == TaskType.EVENT) {

                LocalDateTime start = task.getStartDate();
                LocalDateTime end = task.getEndDate();

                if (end.isBefore(dayStart) || start.isAfter(dayEnd)) {
                    continue;
                }
                LocalDateTime clippedStart = start.isBefore(dayStart) ? dayStart : start;
                LocalDateTime clippedEnd = end.isAfter(dayEnd) ? dayEnd : end;
                busyBlocks.add(new TimeBlock(clippedStart, clippedEnd));
            }
        }
        busyBlocks.sort((a, b) -> a.getStart().compareTo(b.getStart()));
        return mergeBlocks(busyBlocks);
    }

    /**
     * Finds the earliest available time slot within a given day.
     *
     * <p>
     * The method scans through the list of busy time blocks and determines
     * if there exists a gap that satisfies the requested duration.
     *
     * @param busyBlocks the list of merged busy time blocks
     * @param dayStart the start of the day window
     * @param dayEnd the end of the day window
     * @param durationMinutes the required duration in minutes
     * @return the starting time of the free slot if found, otherwise null
     */
    private static LocalDateTime findSlot(ArrayList<TimeBlock> busyBlocks, LocalDateTime dayStart,
                                          LocalDateTime dayEnd, long durationMinutes) {
        LocalDateTime cursor = dayStart;

        for (TimeBlock block : busyBlocks) {
            System.out.println("busy: " + block.getStart() + " -> " + block.getEnd());

            LocalDateTime startTime = block.getStart();
            LocalDateTime endTime = block.getEnd();

            long gapMinutes = java.time.Duration.between(cursor, startTime).toMinutes();
            if (gapMinutes >= durationMinutes) {
                return cursor;
            }
            if (endTime.isAfter(cursor)) {
                cursor = endTime;
            }
        }
        long finalGapMinutes = java.time.Duration.between(cursor, dayEnd).toMinutes();
        if (finalGapMinutes >= durationMinutes) {
            return cursor;
        }
        return null;
    }

    /**
     * Merges overlapping or adjacent time blocks into consolidated blocks.
     *
     * <p>
     * Assumes the input list is already sorted by start time.
     *
     * @param blocks the sorted list of time blocks
     * @return a new list containing merged time blocks
     */
    private static ArrayList<TimeBlock> mergeBlocks(ArrayList<TimeBlock> blocks) {
        ArrayList<TimeBlock> merged = new ArrayList<>();
        if (blocks.isEmpty()) {
            return merged;
        }

        TimeBlock cur = blocks.get(0);

        for (int i = 1; i < blocks.size(); i++) {
            TimeBlock next = blocks.get(i);

            // overlap or touching (next.start <= cur.end)
            if (!next.getStart().isAfter(cur.getEnd())) {
                LocalDateTime newEnd = next.getEnd().isAfter(cur.getEnd()) ? next.getEnd() : cur.getEnd();
                cur = new TimeBlock(cur.getStart(), newEnd);
            } else {
                merged.add(cur);
                cur = next;
            }
        }

        merged.add(cur);
        return merged;
    }

    @Override
    public CommandType getCommandType() {
        return FREE;
    }
}
