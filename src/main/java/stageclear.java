import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class stageclear extends Application{
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setScene(clear());
        stage.setTitle("stage1CLEAR");
        stage.show();
    }
    public Scene clear() {
    	Label title = new Label("練習モード");
    	
    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add(
        getClass().getResource("/style.css").toExternalForm()
    );
    return scene;// master側のサイズを採用
    	
    }
}
