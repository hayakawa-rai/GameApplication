package story;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import test.test2.GameController;
import util.WindowUtil;

public class Stageclear1 extends Application {

	// ウィンドウを保存してどのクラスでも共通のウィンドウを使用するため
	private Stage stage;

	// ★ スコアを数値として保持する変数を追加
	private int score = 0;

	// ★ 引数なしのコンストラクタ（GameControllerの new Stageclear1() で必要）
	public Stageclear1() {
	}

	// ★ GameControllerの App.setScore(score); でスコアを受け取るためのメソッド
	public void setScore(int score) {
		this.score = score;
	}

	// javafxではstartを呼び出さないと起動しないため、親クラスのstartを上書きすることで子クラスを起動
	@Override
	public void start(Stage stage) {
		// 受け取った変数Stageを自分のStageに保存
		this.stage = stage;
		// ウィンドウの中身を決定
		stage.setScene(clear());
		stage.setTitle("stage1CLEAR");
		//画面の強制再設定
		WindowUtil.fillScreen(stage);

	}

	// 音声とタイマー関連変数をコメントアウト
	/*
	private AudioClip clearSound;
	private AudioClip clickSound;
	private AudioClip cancelSound;
	private PauseTransition delay;
	private PauseTransition pause;
	*/

	private void cleanup() {
		// 効果音や遅延処理のクリーンアップをすべてコメントアウト
		/*
		if (delay != null) {
			delay.stop();
			delay = null;
		}

		if (pause != null) {
			pause.stop();
			pause = null;
		}

		if (clearSound != null) {
			clearSound.stop();
			clearSound = null;
		}

		if (clickSound != null) {
			clickSound.stop();
			clickSound = null;
		}

		if (cancelSound != null) {
			cancelSound.stop();
			cancelSound = null;
		}
		*/
	}

	public Scene clear() {
		// クリア音の処理（コメントアウト）
		/*
		clearSound = new AudioClip(
				getClass().getResource("/music/yay.mp3").toExternalForm());
		clearSound.setVolume(0.5);

		delay = new PauseTransition(Duration.seconds(0.5));
		delay.setOnFinished(e -> {
			clearSound.play();
		});
		delay.play();
		*/

		// どこのステージをクリアしたか表示する
		Text title = new Text("STAGE1   CLEAR!");
		// フォントサイズとカラーを指定
		title.setStyle("-fx-font-size: 80px; -fx-fill: rgb(180,180,180);");
		// 獲得したアイテムを表示
		Text text = new Text("鍵を獲得しました！！");
		// フォントサイズとカラーを指定
		text.setStyle("-fx-font-size: 20px; -fx-fill: gray;");
		// 獲得したアイテムの画像読み込み
		Image image = new Image(
				getClass().getResource("/picture/kagi.png").toExternalForm());
		// 読み込んだ画像を表示
		ImageView imageView = new ImageView(image);
		// 画像のサイズ調整
		imageView.setFitWidth(150);
		imageView.setFitHeight(150);

		// 横並びにする箱を設定
		HBox textAndImage = new HBox();
		// textと画像の間隔を設定
		textAndImage.setSpacing(10);
		// 中央に配置
		textAndImage.setAlignment(Pos.CENTER);
		// 画像とtextを箱に入れる
		textAndImage.getChildren().addAll(imageView, text);

		// 縦並びにする箱を設定
		VBox buttonBox = new VBox();
		// ボタン配置に間隔を設定
		buttonBox.setSpacing(20);
		// 中央に配置
		buttonBox.setAlignment(Pos.CENTER);

		// 音声読み込み（コメントアウト）
		/*
		clickSound = new AudioClip(
				getClass().getResource("/music/select.mp3").toExternalForm());
		clickSound.setVolume(0.4);

		cancelSound = new AudioClip(
				getClass().getResource("/music/cancel.mp3").toExternalForm());
		cancelSound.setVolume(0.4);
		*/

		// 次に進むボタン
		Button next = new Button("次のステージへ");
		// ボタンにcssに記述したgame-button2を付与、ボタンサイズを指定
		next.getStyleClass().add("game-button2");
		next.setPrefSize(250, 80);
		// 次の画面に遷移（遅延をなくし、クリック時に即座に遷移させます）
		next.setOnAction(e -> {
			try {
				cleanup();
				// 画面遷移
				GameController.switchStory2(stage);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		// ⭐ スコア表示
		Text scoreLabel = new Text("SCORE: " + this.score);
		scoreLabel.setStyle("-fx-font-size: 30px; -fx-fill:  gray;");

		// 戻るボタン
		Button backButton = new Button("タイトルへ");
		// ボタンにcssに記述したgame-button2を付与、ボタンサイズを指定
		backButton.getStyleClass().add("game-button2");
		backButton.setPrefSize(250, 80);
		// スタート画面へ戻る（こちらも遅延なしで即座に遷移します）
		backButton.setOnAction(e -> {
			try {
				cleanup();
				// 画面遷移
				GameController.switchStart(stage);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		// titleと画像とtextをまとめたもの、ボタン2つを箱に入れる。
		buttonBox.getChildren().addAll(title, textAndImage, scoreLabel, next, backButton);
		
		StackPane root = new StackPane();
		root.getChildren().add(buttonBox);
		Scene scene = new Scene(root);

		// CSSを接続
		scene.getStylesheets().add(
				getClass().getResource("/css/style.css").toExternalForm());
		// 画面に表示させたいものを返す
		return scene;
	}
}