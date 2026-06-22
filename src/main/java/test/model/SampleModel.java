package test.model;

import Characters.Direction;
import Characters.Sengoku;
import Items.Chii;
import Items.Item;
import Items.Point;
import test.Enemy;
import test.RedEnemy;

public class SampleModel {

	public static final int TILE_SIZE = 30;

	// 0: 道, 1: 壁, 9: ワープ
	private final int[][] map = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
			{ 1, 2, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 2, 1 },
			{ 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1 },
			{ 9, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 9 },
			{ 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1 },
			{ 1, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 2, 1 },
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
	};
	//Chii,Pointクラスにある設定を持っていく用のMap
	private final Item[][] itemMap;
<<<<<<< HEAD
	// パックマンの状態（タイル中央）
	private final Sengoku sengoku;
	private boolean paused = false;
=======
	// 初期アイテム配置（エサ復活用）(古田)
	private final Item[][] initialItemMap;
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
	// 口パク
	private double mouthAngle = 45;
	private int mouthOpening = -1;
	private boolean isBlocked = false;
=======
	// パックマンの状態（タイル中央）
	private final Sengoku sengoku;
	private boolean paused = false;
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
	// ワープ抑止
	private boolean justWarped = false;
	private int lastWarpX = -1;
	private int lastWarpY = -1;
=======
	// 口パク
	private double mouthAngle = 45;
	private int mouthOpening = -1;
	private boolean isBlocked = false;
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
	public SampleModel() {
		this.sengoku = new Sengoku(10 * TILE_SIZE, 14 * TILE_SIZE, 2);
		//マップと同じ大きさのアイテム配列を用意し、初期配置する
		this.itemMap = new Item[map.length][map[0].length];
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[0].length; col++) {
				double pixelX = col * TILE_SIZE + TILE_SIZE / 2.0;
				double pixelY = row * TILE_SIZE + TILE_SIZE / 2.0;
=======
	// ワープ抑止
	private boolean justWarped = false;
	private int lastWarpX = -1;
	private int lastWarpY = -1;
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
				if (map[row][col] == 0) {
					itemMap[row][col] = new Point(pixelX, pixelY); // Pointインスタンス生成
				} else if (map[row][col] == 2) {
					itemMap[row][col] = new Chii(pixelX, pixelY); // Chiiインスタンス生成
				}
			}
		}
	}
=======
	public SampleModel() {
		this.sengoku = new Sengoku(10 * TILE_SIZE, 14 * TILE_SIZE, 2);
		//マップと同じ大きさのアイテム配列を用意し、初期配置する
		this.itemMap = new Item[map.length][map[0].length];
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[0].length; col++) {
				double pixelX = col * TILE_SIZE + TILE_SIZE / 2.0;
				double pixelY = row * TILE_SIZE + TILE_SIZE / 2.0;
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
	public void togglePause() {
		paused = !paused;
=======
				if (map[row][col] == 0) {
					itemMap[row][col] = new Point(pixelX, pixelY); // Pointインスタンス生成
				} else if (map[row][col] == 2) {
					itemMap[row][col] = new Chii(pixelX, pixelY); // Chiiインスタンス生成
				}
			}
		}
		// 初期アイテム配置を保存（エサ復活用）(古田)
		this.initialItemMap = copyItemMap(itemMap);
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git
	}

<<<<<<< HEAD
	// --- 更新ロジック ---
	public void updatePacman() {
		if (paused || !sengoku.isAlive())
			return;
=======
	// --- itemMap をコピーする ---(古田)
	private Item[][] copyItemMap(Item[][] src) {
		Item[][] dst = new Item[src.length][src[0].length];
		for (int r = 0; r < src.length; r++) {
			for (int c = 0; c < src[0].length; c++) {
				dst[r][c] = src[r][c];
			}
		}
		return dst;
	}
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
		//  Sengokuの現在の座標から、現在のタイル位置を計算（CharacterクラスにgetX(), getY()がある前提だよ）
		int tileX = (int) (sengoku.getX() / TILE_SIZE);
		int tileY = (int) (sengoku.getY() / TILE_SIZE);
=======
	public void togglePause() {
		paused = !paused;
	}
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
		// --- ワープ抑止ロジック ---
		boolean skipWarp = false;
		if (justWarped) {
			if (tileX == lastWarpX && tileY == lastWarpY) {
				skipWarp = true;
			} else {
				justWarped = false;
				lastWarpX = -1;
				lastWarpY = -1;
			}
		}
=======
	// --- 更新ロジック ---
	public void updatePacman() {
		if (paused || !sengoku.isAlive())
			return;
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
		// --- ワープ処理 ---
		if (!skipWarp && tileX >= 0 && tileX < map[0].length && tileY >= 0 && tileY < map.length) {
			if (map[tileY][tileX] == 9) {
				int warpX = tileX;
				int warpY = tileY;
=======
		//  Sengokuの現在の座標から、現在のタイル位置を計算（CharacterクラスにgetX(), getY()がある前提だよ）
		int tileX = (int) (sengoku.getX() / TILE_SIZE);
		int tileY = (int) (sengoku.getY() / TILE_SIZE);
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
				// Sengokuの現在の進行方向（sengoku.getDirection()）を元に、ワープ先を探す
				Direction currentDir = sengoku.getDirection();
=======
		// --- ワープ抑止ロジック ---
		boolean skipWarp = false;
		if (justWarped) {
			if (tileX == lastWarpX && tileY == lastWarpY) {
				skipWarp = true;
			} else {
				justWarped = false;
				lastWarpX = -1;
				lastWarpY = -1;
			}
		}
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
				if (currentDir != Direction.NONE) {
					// 横方向のワープ
					if (currentDir.getDX() != 0) {
						for (int x = 0; x < map[0].length; x++) {
							if (map[tileY][x] == 9 && x != tileX) {
								warpX = x;
								break;
							}
						}
					}
					// 縦方向のワープ
					if (currentDir.getDY() != 0) {
						for (int y = 0; y < map.length; y++) {
							if (map[y][tileX] == 9 && y != tileY) {
								warpY = y;
								break;
							}
						}
					}
				}
=======
		// --- ワープ処理 ---
		if (!skipWarp && tileX >= 0 && tileX < map[0].length && tileY >= 0 && tileY < map.length) {
			if (map[tileY][tileX] == 9) {
				int warpX = tileX;
				int warpY = tileY;
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
				//  Sengokuの位置を直接ワープ先のタイル中央に更新する（CharacterクラスにsetPositionメソッドがあるね！）
				double newPacX = warpX * TILE_SIZE;
				double newPacY = warpY * TILE_SIZE;
				sengoku.setPosition(newPacX, newPacY);
=======
				// Sengokuの現在の進行方向（sengoku.getDirection()）を元に、ワープ先を探す
				Direction currentDir = sengoku.getDirection();
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
				justWarped = true;
				lastWarpX = warpX;
				lastWarpY = warpY;
=======
				if (currentDir != Direction.NONE) {
					// 横方向のワープ
					if (currentDir.getDX() != 0) {
						for (int x = 0; x < map[0].length; x++) {
							if (map[tileY][x] == 9 && x != tileX) {
								warpX = x;
								break;
							}
						}
					}
					// 縦方向のワープ
					if (currentDir.getDY() != 0) {
						for (int y = 0; y < map.length; y++) {
							if (map[y][tileX] == 9 && y != tileY) {
								warpY = y;
								break;
							}
						}
					}
				}
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
				return; // ワープしたフレームは移動処理をスキップして終了
			}
		}
=======
				//  Sengokuの位置を直接ワープ先のタイル中央に更新する（CharacterクラスにsetPositionメソッドがあるね！）
				double newPacX = warpX * TILE_SIZE;
				double newPacY = warpY * TILE_SIZE;
				sengoku.setPosition(newPacX, newPacY);
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
		//  ワープゾーンにいない場合は、通常通りSengoku自身の移動ロジックを実行
		sengoku.move(map);
=======
				justWarped = true;
				lastWarpX = warpX;
				lastWarpY = warpY;
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

<<<<<<< HEAD
		//移動した後の新しいマス目の位置を再計算
		int currentTileX = (int) ((sengoku.getX() + TILE_SIZE / 2.0) / TILE_SIZE);
		int currentTileY = (int) ((sengoku.getY() + TILE_SIZE / 2.0) / TILE_SIZE);
=======
				return; // ワープしたフレームは移動処理をスキップして終了
			}
		}

		//  ワープゾーンにいない場合は、通常通りSengoku自身の移動ロジックを実行！
		sengoku.move(map);

		//移動した後の新しいマス目の位置を再計算
		int currentTileX = (int) ((sengoku.getX() + TILE_SIZE / 2.0) / TILE_SIZE);
		int currentTileY = (int) ((sengoku.getY() + TILE_SIZE / 2.0) / TILE_SIZE);

		// マップの範囲内であることを確認
		if (currentTileY >= 0 && currentTileY < map.length && currentTileX >= 0 && currentTileX < map[0].length) {
			Item item = itemMap[currentTileY][currentTileX];
			//触ったものがnull出ない場合。実行
			if (item != null) {
				//各クラス（PointやChii）に定義された onEaten を実行してスコア加算
				item.onEaten(sengoku);

				// 食べたので配列から消去する
				itemMap[currentTileY][currentTileX] = null;
				System.out.println("アイテムを食べた！現在のスコア: " + sengoku.getScore());
			}
		}
		// ★ 全部食べたかチェック(古田)
		checkAllEaten();
	}

	// --- 全部食べたかチェック ---(古田)
	private void checkAllEaten() {
		for (int r = 0; r < itemMap.length; r++) {
			for (int c = 0; c < itemMap[0].length; c++) {
				if (itemMap[r][c] != null)
					return; // まだ残っている
			}
		}

		// ★ 全部食べた → 復活(古田)
		resetItems();
	}

	// --- エサ復活 ---(古田)
	private void resetItems() {
		for (int r = 0; r < itemMap.length; r++) {
			for (int c = 0; c < itemMap[0].length; c++) {
				itemMap[r][c] = initialItemMap[r][c];
			}
		}
	}

	public void updateMouth() {
		if (paused || !sengoku.isAlive() || sengoku.getDirection() == Direction.NONE)
			return;
		mouthAngle += mouthOpening * 2;
		if (mouthAngle <= 10)
			mouthOpening = +1;
		if (mouthAngle >= 45)
			mouthOpening = -1;
	}

	public void setNextDirection(Direction dir) {
		sengoku.setnextdirection(dir);
	}
>>>>>>> branch 'master' of https://github.com/hayakawa-rai/GameApplication.git

		// マップの範囲内であることを確認
		if (currentTileY >= 0 && currentTileY < map.length && currentTileX >= 0 && currentTileX < map[0].length) {
			Item item = itemMap[currentTileY][currentTileX];
			//触ったものがnull出ない場合。実行
			if (item != null) {
				//各クラス（PointやChii）に定義された onEaten を実行してスコア加算
				item.onEaten(sengoku);

				// 食べたので配列から消去する
				itemMap[currentTileY][currentTileX] = null;
				System.out.println("アイテムを食べた！現在のスコア: " + sengoku.getScore());
			}
		}
	}

	public void updateMouth() {
		if (paused || !sengoku.isAlive() || sengoku.getDirection() == Direction.NONE)
			return;
		mouthAngle += mouthOpening * 2;
		if (mouthAngle <= 10)
			mouthOpening = +1;
		if (mouthAngle >= 45)
			mouthOpening = -1;
	}

	public void setNextDirection(Direction dir) {
		sengoku.setnextdirection(dir);
	}

	// --- getters ---
	public int[][] getMap() {
		return map;
	}

	//Viewがアイテム配列を取得するためのゲッター
	public Item[][] getItemMap() {
		return itemMap;
	}

	public boolean isPaused() {
		return paused;
	}

	public double getMouthAngle() {
		return mouthAngle;
	}

	public Sengoku getSengoku() {
		return sengoku;
	}

	//メンバー変数エリアに追加（実際のゴーストクラスを保持する）
	private Enemy enemy;

	//コンストラクタの中で初期化する
	// ※ImageView、初期座標、スピード、プレイヤー(sengoku)を渡します
	public void initEnemy(javafx.scene.image.ImageView enemyImageView) {
		// 赤ゴースト(RedEnemy)の子クラスを生成する
		this.enemy = new RedEnemy(enemyImageView, this.sengoku);
	}

	//update() メソッドの中に移動処理を追加
	public void update() {
		if (paused)
			return;

		// パックマンの移動
		updatePacman();

		// 敵キャラが存在すれば移動ロジックを実行
		if (enemy != null) {
			enemy.move(map); // Enemy側の内部で自動的にImageViewの位置も同期されます
		}

		// （以下、既存のアイテム捕食処理や口パク処理はそのまま）
		int currentTileX = (int) ((sengoku.getX() + TILE_SIZE / 2.0) / TILE_SIZE);
		int currentTileY = (int) ((sengoku.getY() + TILE_SIZE / 2.0) / TILE_SIZE);
		if (currentTileY >= 0 && currentTileY < map.length && currentTileX >= 0 && currentTileX < map[0].length) {
			Item item = itemMap[currentTileY][currentTileX];
			if (item != null) {
				item.onEaten(sengoku);
				itemMap[currentTileY][currentTileX] = null;
			}
		}
		//口パク処理
		updateMouth();

		//仙石さんと敵の当たり判定を毎フレームチェック
		checkCollision();
	}

	//敵との接触を判定するメソッド
	private void checkCollision() {
		if (enemy == null || !sengoku.isAlive())
			return;

		// お互いの中心座標を計算する
		double pacCenterX = sengoku.getX() + TILE_SIZE / 2.0;
		double pacCenterY = sengoku.getY() + TILE_SIZE / 2.0;
		double enemyCenterX = enemy.getX() + TILE_SIZE / 2.0;
		double enemyCenterY = enemy.getY() + TILE_SIZE / 2.0;

		// 三平方の定理で2点間の直線距離を計算
		double dx = pacCenterX - enemyCenterX;
		double dy = pacCenterY - enemyCenterY;
		double distance = Math.sqrt(dx * dx + dy * dy);

		// 当たり判定の基準値（1タイルの8割ほどの距離「24ピクセル」以内に近づいたら接触となる）
		double collisionThreshold = TILE_SIZE * 0.8;

		if (distance < collisionThreshold) {
			//接触したときの処理
			System.out.println("敵に捕まった！ゲームオーバー！");

			// Sengokuクラスに死亡フラグやライフを減らすメソッドがある前提
			// なければ直接ライフを管理するか、ゲームを一時停止にします
			// 例: sengoku.setAlive(false); 

			// 今回は分かりやすく、ゲームを一時停止（フリーズ）状態にします
			this.paused = true;
		}
	}

	// 外部から敵を取得するためのゲッター
	public Enemy getEnemy() {
		return enemy;
	}
}