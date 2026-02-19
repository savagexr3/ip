package voy.parser;

import org.junit.jupiter.api.Test;
import voy.exception.OrbitException;

import static org.junit.jupiter.api.Assertions.*;

public class TaskParserTest {

    // -------- INDEX --------

    @Test
    public void parseTaskIndex_validIndex_success() throws Exception {
        assertEquals(0, TaskParser.parseTaskIndex("1"));
    }

    @Test
    public void parseTaskIndex_zero_throwsException() {
        assertThrows(OrbitException.class,
                () -> TaskParser.parseTaskIndex("0"));
    }

    @Test
    public void parseTaskIndex_nonNumber_throwsException() {
        assertThrows(OrbitException.class,
                () -> TaskParser.parseTaskIndex("abc"));
    }

    // -------- DEADLINE --------

    @Test
    public void parseDeadline_valid_success() throws Exception {
        assertNotNull(TaskParser.parseDeadline(
                "submit report /by 2026-03-12 14:30"));
    }

    @Test
    public void parseDeadline_feb30_throwsException() {
        assertThrows(OrbitException.class,
                () -> TaskParser.parseDeadline(
                        "submit report /by 2026-02-30 10:00"));
    }

    // -------- EVENT --------

    @Test
    public void parseEvent_endBeforeStart_throwsException() {
        assertThrows(OrbitException.class,
                () -> TaskParser.parseEvent(
                        "meeting /from 2026-03-12 14:00 /to 2026-03-12 13:00"));
    }

    // -------- FREE --------

    @Test
    public void parseFreeTime_hours_success() throws Exception {
        assertEquals(120, TaskParser.parseFreeTime("2h"));
    }

    @Test
    public void parseFreeTime_minutes_success() throws Exception {
        assertEquals(90, TaskParser.parseFreeTime("90m"));
    }

    @Test
    public void parseFreeTime_zero_throwsException() {
        assertThrows(OrbitException.class,
                () -> TaskParser.parseFreeTime("0h"));
    }

    @Test
    public void parseFreeTime_invalid_throwsException() {
        assertThrows(OrbitException.class,
                () -> TaskParser.parseFreeTime("abc"));
    }
}
