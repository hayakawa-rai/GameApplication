package util;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowUtil {

    // 💡 最初に正しく取得できたサイズをキャッシュしておく（アプリ起動中は不変という前提）
    private static Rectangle2D cachedBounds = null;

    public static void fillScreen(Stage stage) {
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        }

        if (cachedBounds == null) {
            // 初回だけ実際に画面から取得する
            cachedBounds = Screen.getPrimary().getVisualBounds();
        }

        stage.setX(cachedBounds.getMinX());
        stage.setY(cachedBounds.getMinY());
        stage.setWidth(cachedBounds.getWidth());
        stage.setHeight(cachedBounds.getHeight());
    }
}