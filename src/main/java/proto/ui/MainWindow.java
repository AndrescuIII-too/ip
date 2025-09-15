package proto.ui;

import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import proto.Proto;
import proto.command.Response;

public class MainWindow extends AnchorPane {
    private Proto proto;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private void flushResponses(List<Response> responses) {
        for (Response response: responses) {
            switch (response.type) {
            case DIALOG -> {
                DialogBox dialogBox = response.getDialogBox();
                this.dialogContainer.getChildren().add(dialogBox);
            }
            case EXIT -> {
                Platform.exit();
                return;
            }
            }
        }
    }

    @FXML
    public void initialize() {
        this.scrollPane.vvalueProperty().bind(this.dialogContainer.heightProperty());
    }

    @FXML
    private void handleUserInput() {
        if (this.proto.getStatus() == Proto.Status.FATAL) {
            Platform.exit();
            return;
        }

        String input = this.userInput.getText();
        this.dialogContainer.getChildren().add(DialogBox.getUserDialog(input));

        List<Response> responses = this.proto.getResponses(input);
        this.flushResponses(responses);
        this.userInput.clear();
    }

    public void setInstance(Proto proto) {
        this.proto = proto;
    }

    public void showInitialResponses() {
        this.flushResponses(this.proto.initialResponses);
    }
}
