import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class gameover extends Application {

	@Override
	public void start(Stage stage) {
		stage.setScene(create(stage));
		stage.setTitle("ゲーム");
		stage.show();
	}

	public static Scene create(Stage stage) {

		//GAME OVER
		Label gameOverLabel = new Label("GAME OVER");
		gameOverLabel.setStyle("-fx-font-size: 70px; -fx-font-weight: bold; -fx-text-fill: red;");

		VBox titleBox = new VBox(gameOverLabel);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setStyle("-fx-padding: 150px 0 40px 0;");

		//いらすとや
		Image img = new Image(gameover.class.getResource("/irasutoya(gameover).png").toExternalForm());
		ImageView icon = new ImageView(img);
		icon.setFitWidth(150);
		icon.setFitHeight(150);
		
		//ボタンのスタイル
		String buttonStyle =
				"-fx-background-color: #1f3134;" +
				"-fx-text-fill: white;" +
				"-fx-font-size: 22px;" +
				"-fx background-radius: 10;" +
				"-fx cursor: hand;";
		
		String buttonHoverStyle =
				"-fx-background-color: #47585c;" +
				"-fx-text-fill: white;" +
				"-fx-font-size: 22px;" +
				"-fx-background-radius: 10;";
				
		//練習画面へ戻る
		Button retryBtn = new Button("練習画面へ");
		retryBtn.setPrefSize(300, 70);
		retryBtn.setStyle(buttonStyle);
		retryBtn.setOnMouseEntered(e -> retryBtn.setStyle(buttonHoverStyle));
		retryBtn.setOnMouseExited(e -> retryBtn.setStyle(buttonStyle));
		retryBtn.setOnAction(e -> {
			practice practiceScreen = new practice();
			try {
				practiceScreen.start(stage);
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		//タイトル画面へ戻る
		Button titleBtn = new Button("タイトルへ");
		titleBtn.setPrefSize(300, 70);
		titleBtn.setStyle(buttonStyle);
		titleBtn.setOnMouseEntered(e -> titleBtn.setStyle(buttonHoverStyle));
		titleBtn.setOnMouseExited(e -> titleBtn.setStyle(buttonStyle));
		titleBtn.setOnAction(e -> {
			start titleScreen = new start();
			try {
				titleScreen.start(stage);
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		//ボタンを縦に並べる
		VBox buttonColumn = new VBox(20, retryBtn, titleBtn);
		buttonColumn.setAlignment(Pos.CENTER_LEFT);

		//画像とボタン列を横に並べる
		HBox centerBox = new HBox(40, icon, buttonColumn);
		centerBox.setAlignment(Pos.CENTER);

		//背景
		Image bgImage = new Image(gameover.class.getResource("/gameover.jpg").toExternalForm());
		ImageView bg = new ImageView(bgImage);
		bg.setFitWidth(800);
		bg.setFitHeight(600);
		bg.setPreserveRatio(false);
		

		//レイアウト
		BorderPane ui = new BorderPane();
		ui.setTop(titleBox);
		ui.setCenter(centerBox);

		StackPane root = new StackPane(bg, ui);

		return new Scene(root, 800, 600);

	}

	public static void main(String[] args) {
		launch();
	}

}
