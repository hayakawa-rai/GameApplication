package Characters;

import java.util.List;

import javafx.scene.image.ImageView;

public class YellowEnemy extends Enemy {

	private Sengoku player; // プレイヤー
	private static final int PREDICT_TILES = 4; // 4マス先を狙う
	private static final int CELL_SIZE = 24; // ゲームのマスサイズ

	public YellowEnemy(ImageView imageView, double x, double y, int speed, Sengoku player) {
		super(imageView, x, y, speed);
		this.player = player;
	}

	@Override
	protected Direction decideNextDirection(List<Direction> validDirections, int[][] map, Sengoku p) {

		// プレイヤーの現在マス
		int targetCol = (int) ((player.getX() + CELL_SIZE / 2) / CELL_SIZE);
		int targetRow = (int) ((player.getY() + CELL_SIZE / 2) / CELL_SIZE);

		// プレイヤーの向きに応じて 4 マス先を予測
		switch (player.getDirection()) {
		case UP:
			targetRow -= PREDICT_TILES;
			break;
		case DOWN:
			targetRow += PREDICT_TILES;
			break;
		case LEFT:
			targetCol -= PREDICT_TILES;
			break;
		case RIGHT:
			targetCol += PREDICT_TILES;
			break;
		default:
			break;
		}

		// 最も近づく方向を返す（Enemy にある共通メソッド）
		return getClosestDirection(validDirections, targetCol, targetRow);
	}
}

/*例 
 プレイヤー → 右向き
 プレイヤー位置 → (100, 100)

 プレイヤーは右に進んでる
 じゃあ 4マス先は 100 + (4 * 24) = 196
 (196, 100) を目指して動く？たぶん？*/