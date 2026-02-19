package voy.parser;

import org.junit.jupiter.api.Test;
import voy.command.Command;
import voy.command.CommandType;
import voy.exception.OrbitException;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_listCommand_success() throws Exception {
        Command c = Parser.parse("list");
        assertEquals(CommandType.LIST, c.getCommandType());
    }

    @Test
    public void parse_trimSpaces_success() throws Exception {
        Command c = Parser.parse("   mark 1   ");
        assertEquals(CommandType.MARK, c.getCommandType());
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        assertThrows(OrbitException.class, () -> Parser.parse("abc"));
    }

    @Test
    public void parse_emptyInput_throwsException() {
        assertThrows(OrbitException.class, () -> Parser.parse("   "));
    }

    @Test
    public void parse_findWithoutArgs_throwsException() {
        assertThrows(OrbitException.class, () -> Parser.parse("find"));
    }

    @Test
    public void parse_listWithExtraArgs_throwsException() {
        assertThrows(OrbitException.class,
                () -> Parser.parse("list something"));
    }
}
