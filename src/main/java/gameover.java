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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class gameover extends Application {

	@Override
	public void start(Stage stage) {
		stage.setScene(create(stage));
		stage.setTitle("ゲーム");
		stage.setMaximized(true);
		stage.show();
	}

	public static Scene create(Stage stage) {

		//GAME OVERタイトル
		Label gameOverLabel = new Label("GAME OVER");
		gameOverLabel.getStyleClass().add("gameover-title");

		VBox titleBox = new VBox(gameOverLabel);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setStyle("-fx-padding: 150px 0 40px 0;");
		

		//いらすとや画像
		Image img = new Image(gameover.class.getResource("/irasutoya(gameover).png").toExternalForm());
		ImageView icon = new ImageView(img);
		icon.setFitWidth(200);
		icon.setFitHeight(200);

		
		//ボタン(練習モードへ戻る)
		Button retryBtn = new Button("練習モードへ");
		retryBtn.setPrefSize(300, 70);
		retryBtn.getStyleClass().add("gameover-button");
		
		//画面遷移：practice画面へ
		retryBtn.setOnAction(e -> {
			practice practiceScreen = new practice();
			try {
				practiceScreen.start(stage);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		

		//ボタン(タイトル画面へ戻る)
		Button titleBtn = new Button("タイトルへ");
		titleBtn.setPrefSize(300, 70);
		titleBtn.getStyleClass().add("gameover-button");
		
		//画面遷移：start画面へ
		titleBtn.setOnAction(e -> {
			start titleScreen = new start();
			try {
				titleScreen.start(stage);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		

		//ボタンを縦に並べる
		VBox buttonColumn = new VBox(20, retryBtn, titleBtn);
		buttonColumn.setAlignment(Pos.CENTER_LEFT);
		

		//画像とボタンを横に並べる
		HBox centerBox = new HBox(40, icon, buttonColumn);
		centerBox.setAlignment(Pos.CENTER);

		
		//レイアウト(BorderPaneに配置)
		BorderPane ui = new BorderPane();
		ui.setTop(titleBox);
		ui.setCenter(centerBox);
		
		
		//背景画像 + 白い透明レイヤー
		StackPane root = new StackPane();

		//背景画像
		Image bgImage = new Image(gameover.class.getResource("/gameover.jpg").toExternalForm());
		ImageView bg = new ImageView(bgImage);
		bg.setPreserveRatio(false);

		// 白い透明レイヤー（背景を柔らかくする）
		Rectangle whiteOverlay = new Rectangle();
		whiteOverlay.setFill(Color.rgb(255, 255, 255, 0.15));

		
		//rootのサイズに合わせて伸縮させる
		bg.fitWidthProperty().bind(root.widthProperty());
		bg.fitHeightProperty().bind(root.heightProperty());
		
		whiteOverlay.widthProperty().bind(root.widthProperty());
		whiteOverlay.heightProperty().bind(root.heightProperty());

		//rootに追加(背景→白レイヤー→UIの順に重ねる)
		root.getChildren().addAll(bg, whiteOverlay, ui);
		
		//シーン作成
		Scene scene = new Scene(root, 800, 600);
		
		
		scene.getStylesheets().add(gameover.class.getResource("/gameover.css").toExternalForm());

		return scene;

	}

	public static void main(String[] args) {
		launch();
	}

}
