package proto.command;

import java.util.Collections;
import java.util.List;

public record Command(String name, String body, List<Parameter> parameters) {
    public Command(String name, String body, List<Parameter> parameters) {
        this.name = name;
        this.body = body;
        this.parameters = Collections.unmodifiableList(parameters);
    }
}
