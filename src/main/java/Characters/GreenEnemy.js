import { Enemy, EnemyState } from './Enemy.js';
import { Direction } from './Direction.js';
import { GameConfig } from '../common/GameConfig.js';

export class GreenEnemy extends Enemy {
    // ==================================================
    // クラス定数 (Static Fields)
    // ==================================================
    // 初期位置（エネミーハウス内）
    static START_COL = 14;
    static START_ROW = 14;
    // 8 マス以上離れていたら追跡
    static BORDER = 8;
    // SCATTER状態時の縄張り座標（左下）
    static TERRITORY_COL = 3;
    static TERRITORY_ROW = 26;

    // ==================================================
    // コンストラクタ
    // ==================================================
    constructor(mapData) {
        // マスの中心座標を初期位置として親クラス(Enemy)に渡す
        const startX = GreenEnemy.START_COL * GameConfig.TILE_SIZE + GameConfig.TILE_SIZE / 2.0;
        const startY = GreenEnemy.START_ROW * GameConfig.TILE_SIZE + GameConfig.TILE_SIZE / 2.0;
        super(startX, startY, 2);

        this.mapData = mapData;

        // 出撃時間を記録する
        this.startTime = 0;
        // 出撃タイマーが開始されたか
        this.timerStarted = false;
        // 巣から出撃済みか
        this.released = false;

        // FEVER画像・DEAD画像を読み込む（親クラスに定義されている想定）
        this.loadFeverImage();
        this.loadDeadImage();

        // 画像の読み込み処理
        this.loadNormalImage();
    }

    /**
     * 現在のステージ番号によって、読み込む画像を切り替える
     */
    loadNormalImage() {
        // デフォルト（ステージ1用）
        let imagePath = "/picture/nari_EnemyGreen.png";

        if (this.mapData) {
            switch (this.mapData.getStageNumber()) {
                case 1:
                    imagePath = "/picture/nari_EnemyGreen.png";
                    break;
                case 2:
                    imagePath = "/picture/taku_EnemyGreen.png";
                    break;
                case 3:
                    imagePath = "/picture/aniki_EnemyGreen.png";
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
        // 出撃待機中のみタイマー補正を行う
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
        // 出撃タイマーをリセット
        this.timerStarted = false;
    }

    // ==================================================
    // 動き
    // ==================================================
    // 10秒経過後に出撃
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
            // 10秒経過するまで待機
            if (elapsed < 10000) {
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
    // GreenEnemyの移動方向を決定する
    // 遠い → 追跡
    // 近い → 左下の縄張りへ戻る
    decideNextDirection(validDirections, map, mapData) {
        // 移動可能な方向が存在しない場合
        if (!mapData || !validDirections || validDirections.length === 0) {
            return Direction.NONE; // Direction オブジェクトが別途定義されている想定
        }

        // プレイヤーの現在座標を取得
        const pacX = mapData.getPacX();
        const pacY = mapData.getPacY();

        // プレイヤーの位置をタイル座標へ変換
        const targetCol = Math.floor(pacX / GameConfig.TILE_SIZE);
        const targetRow = Math.floor(pacY / GameConfig.TILE_SIZE);

        // 縄張りモード (this.currentState, EnemyState は親・外部で定義されている想定)
        if (this.currentState === EnemyState.SCATTER) {
            return this.getClosestDirection(validDirections, GreenEnemy.TERRITORY_COL, GreenEnemy.TERRITORY_ROW);
        }

        // FEVER・DEAD状態の共通処理
        const special = this.handleSpecialState(validDirections, targetCol, targetRow, map);
        if (special !== null && special !== undefined) {
            return special;
        }

        // 自分の現在位置を取得
        const myCol = Math.floor(this.x / GameConfig.TILE_SIZE);
        const myRow = Math.floor(this.y / GameConfig.TILE_SIZE);

        // プレイヤーとの距離計算（マス単位）
        const distance = Math.sqrt(Math.pow(myCol - targetCol, 2) + Math.pow(myRow - targetRow, 2));
        
        // プレイヤーが遠い場合は追跡
        if (distance >= GreenEnemy.BORDER) {
            return this.getClosestDirection(validDirections, targetCol, targetRow);
        }

        // プレイヤーが近い場合は縄張りへ戻る
        return this.getClosestDirection(validDirections, GreenEnemy.TERRITORY_COL, GreenEnemy.TERRITORY_ROW);
    }
}