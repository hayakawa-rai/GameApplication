import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class start extends Application {
    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane(new Label("Title Screen (placeholder)"));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Title");
        stage.show();
    }

    public static Scene create(Stage stage) {
        StackPane root = new StackPane(new Label("Title Screen (placeholder)"));
        return new Scene(root, 800, 600);
    }
}
