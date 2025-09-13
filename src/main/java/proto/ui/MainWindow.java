package proto.ui;

import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    private Button sendButton;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    @FXML
    private void handleUserInput() {
        if (this.proto.getStatus() == Proto.Status.FATAL) {
            Platform.exit();
        }

        String input = userInput.getText();
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input));

        List<Response> responses = proto.getResponse(input);
        for (Response response: responses) {
            switch (response.type) {
            case DIALOG -> {
                DialogBox dialogBox = response.getDialogBox();
                dialogContainer.getChildren().add(dialogBox);
            }
            case EXIT -> Platform.exit();
            }
        }

        userInput.clear();
    }

    public void setInstance(Proto proto) {
        this.proto = proto;
    }
}
