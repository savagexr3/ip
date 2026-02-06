package voy.parser;

import voy.command.Command;
import voy.command.ListCommand;
import voy.command.AddDeadlineCommand;
import voy.command.AddEventCommand;
import voy.command.AddTodoCommand;
import voy.command.MarkCommand;

import voy.exception.OrbitException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

public class ParserTest {
    @Test
    public void parse_list_returnsListCommand() throws OrbitException {
        Command c = Parser.parse("list");
        assertTrue(c instanceof ListCommand);
    }

    @Test
    public void parse_todo_returnsAddTodoCommand() throws OrbitException {
        Command c = Parser.parse("todo read book");
        assertTrue(c instanceof AddTodoCommand);
    }

    @Test
    public void parse_mark_returnsMarkCommand() throws OrbitException {
        Command c = Parser.parse("mark 2");
        assertTrue(c instanceof MarkCommand);
    }

    @Test
    public void parse_deadline_returnsAddDeadlineCommand() throws OrbitException {
        Command c = Parser.parse("deadline return book /by 2025-01-01 13:00");
        assertTrue(c instanceof AddDeadlineCommand);
    }

    @Test
    public void parse_event_returnsAddEventCommand() throws OrbitException {
        Command c = Parser.parse("event meeting /from 2025-08-31 14:00 /to 2025-09-02 08:00");
        assertTrue(c instanceof AddEventCommand);
    }

    @Test
    public void parseTodo_empty_throwsOrbitException() {
        assertThrows(OrbitException.class, () -> Parser.parseTodo(""));
        assertThrows(OrbitException.class, () -> Parser.parseTodo("   "));
        assertThrows(OrbitException.class, () -> Parser.parseTodo(null));
    }

    @Test
    public void parseTaskIndex_valid_returnsZeroBased() throws OrbitException {
        assertEquals(0, Parser.parseTaskIndex("1"));
        assertEquals(2, Parser.parseTaskIndex("3"));
        assertEquals(9, Parser.parseTaskIndex("10"));
    }

    @Test
    public void parseTaskIndex_invalid_throwsOrbitException() {
        assertThrows(OrbitException.class, () -> Parser.parseTaskIndex(""));
        assertThrows(OrbitException.class, () -> Parser.parseTaskIndex("   "));
        assertThrows(OrbitException.class, () -> Parser.parseTaskIndex(null));
        assertThrows(OrbitException.class, () -> Parser.parseTaskIndex("abc"));
    }

    @Test
    public void parseDeadline_valid_success() throws OrbitException {
        // Ensure it parses and does not throw
        assertNotNull(Parser.parseDeadline("return book /by 2025-01-01 13:00"));
    }

    @Test
    public void parseDeadline_missingDescriptionOrBy_throwsOrbitException() {
        assertThrows(OrbitException.class, () -> Parser.parseDeadline("return book"));
        assertThrows(OrbitException.class, () -> Parser.parseDeadline("return book /by "));
        assertThrows(OrbitException.class, () -> Parser.parseDeadline(" /by 2025-01-01 13:00"));
    }

    @Test
    public void parseDeadline_badDateTime_throwsOrbitException() {
        assertThrows(OrbitException.class,
                () -> Parser.parseDeadline("return book /by Sunday"));
        assertThrows(OrbitException.class,
                () -> Parser.parseDeadline("return book /by 2025-01-01 1300")); // missing colon
    }

    @Test
    public void parseEvent_valid_success() throws OrbitException {
        assertNotNull(Parser.parseEvent("meeting /from 2025-08-31 14:00 /to 2025-09-02 08:00"));
    }

    @Test
    public void parseEvent_missingFromOrTo_throwsOrbitException() {
        assertThrows(OrbitException.class, () -> Parser.parseEvent("party /from 2025-01-01 10:00"));
        assertThrows(OrbitException.class, () -> Parser.parseEvent("party /to 2025-01-01 10:00"));
        assertThrows(OrbitException.class, () -> Parser.parseEvent("party"));
        assertThrows(OrbitException.class, () -> Parser.parseEvent("party /from /to 2025-01-01 10:00"));
    }

    @Test
    public void parseDateTime_valid_success() {
        LocalDateTime dt = Parser.parseDateTime("2025-01-01 13:00");
        assertEquals(2025, dt.getYear());
        assertEquals(1, dt.getMonthValue());
        assertEquals(1, dt.getDayOfMonth());
        assertEquals(13, dt.getHour());
        assertEquals(0, dt.getMinute());
    }

    @Test
    public void displayDateTime_format_matchesExpectedStyle() {
        LocalDateTime dt = LocalDateTime.of(2026, 12, 30, 23, 59);
        // Your method returns "Dec 30 2026 11:59pm" (lowercase)
        assertEquals("Dec 30 2026 11:59pm", Parser.displayDateTime(dt));
    }
}
