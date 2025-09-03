package proto.task;

import org.junit.jupiter.api.Test;
import proto.exception.ProtoInvalidData;

import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {
    @Test
    public void deserializeTest() {
        Todo task = assertDoesNotThrow(() -> Todo.deserialize("T | 1 | read book"));
        assertEquals(task.description, "read book");
        assertTrue(task.isDone);
    }
    @Test
    public void deserializeTestPipe() {
        Todo task = assertDoesNotThrow(() -> Todo.deserialize("T | 1 | escape\\|pipe"));
        assertEquals(task.description, "escape|pipe");
        assertTrue(task.isDone);
    }

    @Test
    public void deserializeFailFields() {
        assertThrows(ProtoInvalidData.class, () -> Todo.deserialize("T | 1 | field1 | field2"));
    }

    @Test
    public void deserializeFailPipe() {
        assertThrows(ProtoInvalidData.class, () -> Todo.deserialize("T | 1 | unescaped|pipe"));
    }
}
