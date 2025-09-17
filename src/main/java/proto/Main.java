package proto;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import proto.ui.MainWindow;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Proto proto = Proto.initialize();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Proto.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(200);
            stage.setMinWidth(400);
            stage.getIcons().add(new Image("/images/proto-icon.png"));
            stage.setTitle("Proto");

            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setInstance(proto);
            mainWindow.showInitialResponses();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
