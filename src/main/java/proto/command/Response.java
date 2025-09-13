package proto.command;

import proto.ui.DialogBox;

public class Response {
    public enum Type {
        DIALOG,
        EXIT,
    }

    public final Type type;
    private final DialogBox dialogBox;
    private static final Response RESPONSE_EXIT = new Response(Type.EXIT);

    private Response(Type type) {
        this.type = type;
        this.dialogBox = null;
    }


    public Response(DialogBox dialogBox) {
        this.type = Type.DIALOG;
        this.dialogBox = dialogBox;
    }

    public DialogBox getDialogBox() {
        assert this.type == Type.DIALOG;
        return this.dialogBox;
    }

    public static Response exit() {
        return Response.RESPONSE_EXIT;
    }
}
