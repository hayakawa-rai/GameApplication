import { Enemy, EnemyState } from './Enemy.js';
import { Direction } from './Direction.js';
import { GameConfig } from '../common/GameConfig.js';

// タイルサイズの設定（Enemy.jsと合わせてください）
const TILE_SIZE = 32; 

export class BlueEnemy extends Enemy {

    // ==================================================
    // コンストラクタ
    // ==================================================
    constructor(mapData) {
        // 初期位置（エネミーハウス中央付近）の計算
        const startCol = 14;
        const startRow = 13;
        const initialX = startCol * TILE_SIZE + TILE_SIZE / 2.0;
        const initialY = startRow * TILE_SIZE + TILE_SIZE / 2.0;
        
        // スピードは 2 で初期化
        super(initialX, initialY, 2);
        
        this.mapData = mapData;

        // 定数定義
        this.PREDICT_TILES = 2; // プレイヤーの進行方向+2マス先を狙う
        this.TERRITORY_COL = 24; // SCATTER状態時の縄張り座標（右下）
        this.TERRITORY_ROW = 26;

        // 出撃時間管理用
        this.startTime = 0;
        this.timerStarted = false;
        this.released = false;

        // 赤（RedEnemy）の位置参照用
        this.red = null;

        // 共通画像の読み込み
        this.loadFeverImage();
        this.loadDeadImage();

        // 通常画像のパス決定と読み込み
        this.normalImage = new Image();
        let imagePath = "./picture/nari_EnemyBlue.png"; // デフォルト

        if (this.mapData) {
            switch (this.mapData.getStageNumber()) {
                case 1: imagePath = "./picture/nari_EnemyBlue.png"; break;
                case 2: imagePath = "./picture/taku_EnemyBlue.png"; break;
                case 3: imagePath = "./picture/aniki_EnemyBlue.png"; break;
            }
            
            // MapDataに登録されている敵の中からRedEnemyを探す
            // ※クラス名文字列で判定することで安全に検索
            for (let e of this.mapData.getEnemies()) {
                if (e.constructor.name === "RedEnemy") {
                    this.red = e;
                    break;
                }
            }
        }
        this.normalImage.src = imagePath;
    }

    // ==================================================
    // タイマー補正
    // ==================================================
    resumeTimer() {
        // 出撃待機中のみ補正を行う
        if (this.timerStarted && !this.released) {
            let pauseDuration = Date.now() - this.pauseStartTime;
            this.startTime += pauseDuration; // タイマーをその分だけ後ろへずらす
        }
    }

    // ==================================================
    // 移動・出撃制御
    // ==================================================
    move(map) {
        // READY中は移動しない
        if (this.mapData && this.mapData.isWaitingStart()) {
            return;
        }

        // 初回入力後に初めてタイマー開始
        if (!this.timerStarted) {
            this.startTime = Date.now();
            this.timerStarted = true;
        }

        // 出撃待機中（2秒経過するまで待機）
        if (!this.released) {
            let elapsed = Date.now() - this.startTime;
            if (elapsed < 2000) {
                return;
            }
            this.released = true; // 出撃許可
        }

        // Enemy共通の移動処理を実行
        super.move(map);
    }

    // ==================================================
    // ポジションリセット
    // ==================================================
    resetToStartPosition() {
        super.resetToStartPosition();
        this.released = false;
        this.timerStarted = false;
    }

    // ==================================================
    // AIの方向決定ロジック
    // ==================================================
    decideNextDirection(validDirections, map, mapData) {
        if (!mapData || validDirections.length === 0) {
            return Direction.NONE;
        }

        // プレイヤーの現在位置をタイル座標で取得
        let pacCol = Math.floor(mapData.getPacX() / TILE_SIZE);
        let pacRow = Math.floor(mapData.getPacY() / TILE_SIZE);

        // プレイヤーの進行方向の2マス先を予測
        let playerDir = mapData.getPlayerDirection(); // Directionオブジェクト or 文字列
        
        // ※ Directionがオブジェクト（this.direction.name等）か文字列かによって調整してください
        let dirName = playerDir.name || playerDir; 

        switch (dirName) {
            case "UP":    pacRow -= this.PREDICT_TILES; break;
            case "DOWN":  pacRow += this.PREDICT_TILES; break;
            case "LEFT":  pacCol -= this.PREDICT_TILES; break;
            case "RIGHT": pacCol += this.PREDICT_TILES; break;
        }

        // RedEnemyの現在位置を取得（Redがいなければプレイヤーをそのままターゲットにする安全策）
        let redCol = this.red ? Math.floor(this.red.getX() / TILE_SIZE) : pacCol;
        let redRow = this.red ? Math.floor(this.red.getY() / TILE_SIZE) : pacRow;

        // RedEnemy → 予測地点のベクトルを計算
        let vx = pacCol - redCol;
        let vy = pacRow - redRow;

        // ベクトルを2倍（反転追加）した地点をターゲットとする
        let targetCol = pacCol + vx;
        let targetRow = pacRow + vy;

        // 縄張りモード（SCATTER）
        if (this.currentState === EnemyState.SCATTER) {
            return this.getClosestDirection(validDirections, this.TERRITORY_COL, this.TERRITORY_ROW);
        }

        // FEVER・DEAD状態の共通処理
        let special = this.handleSpecialState(validDirections, pacCol, pacRow, map);
        if (special !== null) {
            return special;
        }

        // 通常追跡（CHASEモードなど）：赤と連携したターゲット地点へ向かう
        return this.getClosestDirection(validDirections, targetCol, targetRow);
    }
}