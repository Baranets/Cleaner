import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    //TODO Добавить регулировку задержки при помощи ползунка


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Interface/MainWindow.fxml"));
        primaryStage.setTitle("Main");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();
    }

}
