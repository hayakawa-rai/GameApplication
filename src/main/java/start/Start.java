package start;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start extends Application {
	private AnimationTimer timer;
	
	// 【修正】クラッシュの原因となるAudioClip（効果音）は一時的にコメントアウト
	// private AudioClip clickSound;

	private void cleanup() {
		// 背景アニメーション停止
		if (timer != null) {
			timer.stop();
			timer = null;
		}

		// 効果音停止（一時的に無効化）
		/*
		if (clickSound != null) {
			clickSound.stop();
			clickSound = null;
		}
		*/

		// BGM停止
		Bgm.stopBGM();
	}

	// javafxのApplicationにもともとあるstartを上書き
	@Override
	public void start(Stage stage) {
		
		// startメソッドの最初の方に配置
		try {
			// 1. ファイルをストリームとして読み込む
			var fontStream = getClass().getResourceAsStream("/font/PixelMplus12-Regular.ttf");
			
			if (fontStream == null) {
				System.err.println("【警告】フォントファイルが見つかりません。パスを確認してください。");
			} else {
				// 2. JavaFXにフォントを登録
				Font loadedFont = Font.loadFont(fontStream, 20);
				
				if (loadedFont == null) {
					System.err.println("【警告】フォントファイルの読み込みに失敗しました（ファイルが壊れているか形式が違います）");
				} else {
					System.out.println("【成功】フォントを読み込みました。名前: " + loadedFont.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ★2. 画面を作る前にレトロフォントファイルを読み込む
		try {
			Font.loadFont(getClass().getResourceAsStream("/font/PixelMplus12-Regular.ttf"), 20);
		} catch (Exception e) {
			System.err.println("フォントの読み込みに失敗しました: " + e.getMessage());
		}

		//背景用の画像を読み込み
		Image bgImage = new Image(
				getClass().getResource("/picture/background.png").toExternalForm());

		// 背景用画像表示を２つ設定
		ImageView bg1 = new ImageView(bgImage);
		ImageView bg2 = new ImageView(bgImage);

		//bg2をbg1の横に置く(bg2の位置を取得した画像の横幅分右に配置)
		bg2.setLayoutX(bgImage.getWidth());

		// AnimationTimer:javafxでのループ処理(handle()のみを繰り返し呼び出す)
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				//背景画像を1pxづつ左に動かしている
				bg1.setLayoutX(bg1.getLayoutX() - 1);
				bg2.setLayoutX(bg2.getLayoutX() - 1);

				// 画面外に出たらループ
				if (bg1.getLayoutX() + bgImage.getWidth() <= 0) {
					bg1.setLayoutX(bg2.getLayoutX() + bgImage.getWidth());
				}
				if (bg2.getLayoutX() + bgImage.getWidth() <= 0) {
					bg2.setLayoutX(bg1.getLayoutX() + bgImage.getWidth());
				}
			}
		};
		//ここから自動的にループ開始
		timer.start();
		
		//重ねて表示するためのレイアウト(レイヤー構造の作成)
		StackPane root = new StackPane();

		// 背景用の画像をbagPaneに追加
		Pane bgPane = new Pane();
		bgPane.getChildren().addAll(bg1, bg2);

		//縦に並べるための箱を作成
		VBox ui = new VBox();
		ui.setSpacing(20);
		ui.setAlignment(Pos.CENTER);

		//title用の画像読み込み
		Image image = new Image(getClass().getResource("/picture/title.png").toExternalForm());
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(500);
		imageView.setFitHeight(400);
		imageView.setPreserveRatio(true); // 縦横比維持

		//縦に並べるための箱を作成
		VBox buttonBox = new VBox();
		buttonBox.setSpacing(20);
		buttonBox.setAlignment(Pos.CENTER);
		
		// 【修正】クラッシュを引き起こす初期BGM再生は一時的に無効化
		// Bgm.playBGM("/music/startbgm.mp3");

		// 【修正】音声読み込みと音量調整を一時的に無効化
		/*
		clickSound = new AudioClip(
				getClass().getResource("/music/select.mp3").toExternalForm());
		clickSound.setVolume(0.4);
		*/

		//ストーリーモードへ飛ぶボタンを作成
		Button btn1 = new Button("▶ストーリー");
		btn1.setPrefSize(300, 100);
		btn1.getStyleClass().add("game-button");
		btn1.setOnAction(e -> {
			try {
				// 【修正】効果音の再生を一時的に無効化
				/*
				clickSound.stop();
				clickSound.play();
				*/
				// 0.5秒後に画面遷移
				Timeline delay = new Timeline(
						new KeyFrame(Duration.millis(500), ev -> {
							cleanup();
							test.test2.GameController.startToStory(stage);
						}));
				delay.play();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		//練習モードへ飛ぶボタン作成
		Button btn2 = new Button("⚔練習モード");
		btn2.setPrefSize(300, 100);
		btn2.setOnAction(e -> {
			try {
				// 【修正】効果音の再生を一時的に無効化
				/*
				clickSound.stop();
				clickSound.play();
				*/
				// 0.5秒後に画面遷移
				Timeline delay = new Timeline(
						new KeyFrame(Duration.millis(500), ev -> {
							timer.stop();
							control.GameController.switchToPractice(stage);
						}));
				delay.play();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		btn2.getStyleClass().add("game-button");

		//無限モードへ飛ぶボタン作成
		Button btn3 = new Button("🔚ゲーム終了");
		btn3.setPrefSize(300, 100);
		btn3.getStyleClass().add("game-button");

		// クリックしたときにアプリを終了する処理
		btn3.setOnAction(e -> {
			try {
				// 【修正】効果音の再生を一時的に無効化
				/*
				clickSound.stop();
				clickSound.play();
				*/
				// 0.5秒後にアプリを閉じる
				Timeline delay = new Timeline(
						new KeyFrame(Duration.millis(500), ev -> {
							cleanup();
							Platform.exit();
						}));
				delay.play();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		//3個のボタンを縦に並ぶ箱に入れる
		buttonBox.getChildren().addAll(btn1, btn2, btn3);

		//titleの画像と3個のボタンが入った箱を縦に並ぶ箱に入れる
		ui.getChildren().addAll(imageView, buttonBox);
		
		//下から背景、uiの箱に入れたものの順でレイヤー構造のrootに入れる
		root.getChildren().addAll(bgPane, ui);

		//rootを中身とした1000×800のウィンドウを作成
		Scene scene = new Scene(root, 1000, 800);
		scene.getStylesheets().add(
				getClass().getResource("/css/style.css").toExternalForm());
		
		stage.setMinWidth(800);
		stage.setMinHeight(600);
		stage.setTitle("スタート画面");
		stage.setScene(scene);
		stage.show();
	}

	// 【超重要】JPro起動時にサウンドエラーを100%回避するメインメソッド
	public static void main(String[] args) {
		System.setProperty("javafx.platform", "monocle");
		System.setProperty("glass.platform", "Monocle");
		launch(args);
	}
}
