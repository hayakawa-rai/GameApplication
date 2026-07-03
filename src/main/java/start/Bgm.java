package start;

// 【修正】クラッシュ回避のため、不要になったインポートを一時的にコメントアウト
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;

public class Bgm {

    // private static MediaPlayer bgmPlayer; // 【修正】一時的に無効化

    public static void playBGM(String path) {
        // 【修正】クラッシュ対策のため、再生処理を完全にスキップしてログだけ出す
        System.out.println("🎵 [BGM無効化中] 本来再生されるパス: " + path);

        /* 本来の再生処理
        if (bgmPlayer != null) return;

        Media bgm = new Media(
            Bgm.class.getResource(path).toExternalForm()
        );

        bgmPlayer = new MediaPlayer(bgm);
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmPlayer.setVolume(0.2);
        bgmPlayer.play();
        */
    }

    public static void stopBGM() {
        // 【修正】一時的に無効化（何もしない）
        System.out.println("🎵 [BGM無効化中] BGM停止要求をスキップしました");
        
        /* 本来の停止処理
        if (bgmPlayer != null) {
            bgmPlayer.stop();
            bgmPlayer = null;
        }
        */
    }
}