import { Enemy } from './Enemy.js';
import { Direction } from './Direction.js';
import { GameConfig } from '../common/GameConfig.js';

export class RedEnemy extends Enemy {
    // ==================================================
    // クラス定数 (Static Fields)
    // ==================================================
    // 初期位置（エネミーハウス中央付近）
    static START_COL = 13;
    static START_ROW = 13;
    // SCATTER状態で向かう縄張り（右上）
    static TERRITORY_COL = 24;
    static TERRITORY_ROW = 3;

    // ==================================================
    // コンストラクタ
    // ==================================================
    constructor(sampleModel) {
        // マスの中心座標を初期位置として親クラス(Enemy)へ渡す
        const startX = RedEnemy.START_COL * GameConfig.TILE_SIZE + GameConfig.TILE_SIZE / 2.0;
        const startY = RedEnemy.START_ROW * GameConfig.TILE_SIZE + GameConfig.TILE_SIZE / 2.0;
        super(startX, startY, 2);

        // ステージ情報を保存
        this.mapData = sampleModel;

        // FEVER状態・DEAD状態用画像を読み込む（親クラスに定義されている想定）
        this.loadFeverImage();
        this.loadDeadImage();

        // 画像の読み込み処理
        this.loadNormalImage();
    }

    /**
     * 現在のステージ番号によって、読み込む画像を切り替える
     */
    loadNormalImage() {
        // 通常時に使用する画像パス（デフォルト：ステージ1用）
        let imagePath = "/picture/nari_EnemyRed.png";

        // ステージごとに画像を切り替える
        if (this.mapData) {
            switch (this.mapData.getStageNumber()) {
                case 1:
                    imagePath = "/picture/nari_EnemyRed.png";
                    break;
                case 2:
                    imagePath = "/picture/taku_EnemyRed.png";
                    break;
                case 3:
                    imagePath = "/picture/aniki_EnemyRed.png";
                    break;
                default:
                    break;
            }
        }

        // HTML5 Image オブジェクトでの画像読み込み
        this.normalImage = new Image();
        this.normalImage.src = imagePath;

        this.normalImage.onload = () => {
            console.log(`【成功】ステージ ${this.mapData?.getStageNumber() || 1} 用の画像を読み込みました！`);
        };
        this.normalImage.onerror = () => {
            console.error(`【エラー】画像が見つかりません: ${imagePath}`);
        };
    }

    // ==================================================
    // 方向決定
    // ==================================================
    decideNextDirection(validDirections, map, mapData) {
        // 移動可能な方向がない場合は停止、または最初の方向を返す
        if (!mapData || !validDirections || validDirections.length === 0) {
            return Direction.NONE; // Direction オブジェクトが別途定義されている想定
        }

        // プレイヤーの現在位置を取得
        const pacX = mapData.getPacX() + GameConfig.TILE_SIZE / 2.0;
        const pacY = mapData.getPacY() + GameConfig.TILE_SIZE / 2.0;

        // プレイヤーの位置をタイル座標へ変換
        const targetCol = Math.floor(pacX / GameConfig.TILE_SIZE);
        const targetRow = Math.floor(pacY / GameConfig.TILE_SIZE);

        // 縄張りモード (this.currentState, EnemyState は親・外部で定義されている想定)
        if (this.currentState === EnemyState.SCATTER) {
            return this.getClosestDirection(validDirections, RedEnemy.TERRITORY_COL, RedEnemy.TERRITORY_ROW);
        }

        // FEVER・DEAD状態の共通処理
        const special = this.handleSpecialState(validDirections, targetCol, targetRow, map);
        if (special !== null && special !== undefined) {
            return special;
        }

        // 赤エネミー固有AI: プレイヤーへ最短距離で接近する
        return this.getClosestDirection(validDirections, targetCol, targetRow);
    }
}