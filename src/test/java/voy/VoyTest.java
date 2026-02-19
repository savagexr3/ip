package voy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import voy.exception.OrbitException;
import voy.ui.gui.Voy;

public class VoyTest {

    @Test
    public void getResponse_invalidCommand_returnsErrorMessage() throws OrbitException {
        Voy voy = new Voy("./data/test.txt");

        String response = voy.getResponse("abc");

        assertTrue(response.contains("⚠"));
    }

    @Test
    public void getResponse_addTodo_success() throws OrbitException {
        Voy voy = new Voy("./data/test.txt");

        String response = voy.getResponse("todo read");

        assertTrue(response.toLowerCase().contains("read"));
    }
}
