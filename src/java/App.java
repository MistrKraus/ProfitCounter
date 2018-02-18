import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Yield counter");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent, primaryStage.getWidth(), primaryStage.getHeight());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
