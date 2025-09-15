package proto.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    public enum Type {
        DEFAULT,
        ERROR,
        LIST,
    }

    private DialogBox(String text, Image img, Type type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.dialog.setText(text);
        this.displayPicture.setImage(img);

        switch (type) {
        case ERROR -> this.dialog.getStyleClass().add("error-label");
        case LIST -> this.dialog.getStyleClass().add("list-label");
        default -> {}
        }
    }

    private static final Image userImage = new Image(DialogBox.class.getResourceAsStream("/images/niko.png"));
    private static final Image protoImage = new Image(DialogBox.class.getResourceAsStream("/images/proto.png"));

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text, DialogBox.userImage, Type.DEFAULT);
    }

    public static DialogBox getProtoDialog(String text, Type type) {
        DialogBox db = new DialogBox(text, DialogBox.protoImage, type);
        db.flip();
        return db;
    }

    public static DialogBox getProtoDialog(String text) {
        return DialogBox.getProtoDialog(text, Type.DEFAULT);
    }
}
