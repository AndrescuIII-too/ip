package proto.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void parseParametersTest() {
        List<Parameter> parameters = Parser.parseParameters("/from Sunday /to Monday");
        assertEquals(parameters.get(0).name(), "from");
        assertEquals(parameters.get(0).value(), "Sunday");
        assertEquals(parameters.get(1).name(), "to");
        assertEquals(parameters.get(1).value(), "Monday");
    }
}
