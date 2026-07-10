import { Enemy, EnemyState } from './Enemy.js';
import { Direction } from './Direction.js';
import { GameConfig } from '../common/GameConfig.js';

export class YellowEnemy extends Enemy {
    // ==================================================
    // クラス定数 (Static Fields)
    // ==================================================
    // 初期位置（エネミーハウス内）
    static START_COL = 13;
    static START_ROW = 14;
    // プレイヤーの進行方向の4マス先を狙う
    static PREDICT_TILES = 4;
    // SCATTER状態時の縄張り座標（左上）
    static TERRITORY_COL = 3;
    static TERRITORY_ROW = 3;

    // ==================================================
    // コンストラクタ
    // ==================================================
    constructor(mapData) {
        // マスの中心座標を初期位置として親クラス(Enemy)に渡す
        const startX = YellowEnemy.START_COL * GameConfig.TILE_SIZE + GameConfig.TILE_SIZE / 2.0;
        const startY = YellowEnemy.START_ROW * GameConfig.TILE_SIZE + GameConfig.TILE_SIZE / 2.0;
        super(startX, startY, 2);

        this.mapData = mapData;

        // 出撃タイマー用
        this.startTime = 0;
        // タイマー開始フラグ
        this.timerStarted = false;
        // 出撃済みかどうか
        this.released = false;

        // FEVER画像・DEAD画像を読み込む（親クラスに定義されている想定）
        this.loadFeverImage();
        this.loadDeadImage();

        // 通常画像の読み込み
        this.loadNormalImage();
    }

    /**
     * 現在のステージ番号によって、読み込む画像を切り替える
     */
    loadNormalImage() {
        // デフォルト（ステージ1用）
        let imagePath = "/picture/nari_EnemyYellow.png";

        if (this.mapData) {
            switch (this.mapData.getStageNumber()) {
                case 1:
                    imagePath = "/picture/nari_EnemyYellow.png";
                    break;
                case 2:
                    imagePath = "/picture/taku_EnemyYellow.png";
                    break;
                case 3:
                    imagePath = "/picture/aniki_EnemyYellow.png";
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
    // タイマー
    // ==================================================
    // ポーズ中の時間を出撃タイマーへ反映する
    resumeTimer() {
        // 出撃待機中のみ補正を行う
        if (this.timerStarted && !this.released) {
            // ポーズしていた時間を計算 (this.pauseStartTime は親クラスにある想定)
            const pauseDuration = Date.now() - this.pauseStartTime;
            // タイマーを補正
            this.startTime += pauseDuration;
        }
    }

    // ==================================================
    // ポジション
    // ==================================================
    // プレイヤーが被弾時に元の場所、出撃時間をリセット
    resetToStartPosition() {
        // Enemy共通のリセット処理
        super.resetToStartPosition();
        // 出撃状態を初期化
        this.released = false;
        // タイマーをリセット
        this.timerStarted = false;
    }

    // ==================================================
    // 動き
    // ==================================================
    // 6秒経過後に出撃
    move(map) {
        // READY中は移動しない
        if (this.mapData.isWaitingStart()) {
            return;
        }
        // 初回入力後にタイマー開始
        if (!this.timerStarted) {
            this.startTime = Date.now();
            this.timerStarted = true;
        }
        // 出撃待機中
        if (!this.released) {
            // 経過時間を取得
            const elapsed = Date.now() - this.startTime;
            // 6秒経過するまで待機（黄は6秒）
            if (elapsed < 6000) {
                return;
            }
            // 出撃開始
            this.released = true;
        }
        // Enemy共通の移動処理
        super.move(map);
    }

    // ==================================================
    // 方向決定
    // ==================================================
    decideNextDirection(validDirections, map, mapData) {
        // 移動可能な方向が存在しない場合
        if (!mapData || !validDirections || validDirections.length === 0) {
            return Direction.NONE;
        }

        // 縄張りモード(SCATTER状態)
        if (this.currentState === EnemyState.SCATTER) {
            return this.getClosestDirection(validDirections, YellowEnemy.TERRITORY_COL, YellowEnemy.TERRITORY_ROW);
        }

        // 1. 本来のプレイヤーのタイル座標を取得
        const pacCol = Math.floor(mapData.getPacX() / GameConfig.TILE_SIZE);
        const pacRow = Math.floor(mapData.getPacY() / GameConfig.TILE_SIZE);

        // FEVER・DEAD状態の共通処理（本来のプレイヤー座標を渡す）
        const special = this.handleSpecialState(validDirections, pacCol, pacRow, map);
        if (special !== null && special !== undefined) {
            return special;
        }

        // 2. 通常追跡（CHASE等）：プレイヤーの進行方向の4マス先を予測
        let targetCol = pacCol;
        let targetRow = pacRow;

        // ※ Directionがオブジェクト（nameプロパティを持つなど）か、純粋な文字列かで調整してください
        const playerDir = mapData.getPlayerDirection();
        const dirName = playerDir?.name || playerDir;

        switch (dirName) {
            case "UP":
                targetRow -= YellowEnemy.PREDICT_TILES;
                break;
            case "DOWN":
                targetRow += YellowEnemy.PREDICT_TILES;
                break;
            case "LEFT":
                targetCol -= YellowEnemy.PREDICT_TILES;
                break;
            case "RIGHT":
                targetCol += YellowEnemy.PREDICT_TILES;
                break;
            default:
                break;
        }

        // YellowEnemy固有AI: 算出した4マス先の目標地点へ最短距離で追跡
        return this.getClosestDirection(validDirections, targetCol, targetRow);
    }
}