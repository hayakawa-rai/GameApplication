import { Character } from './Character.js';
import { Direction } from './Direction.js';

const CELL_SIZE = 30; // 1マスのサイズ

export class Syujinkou extends Character {

    // ==================================================
    // コンストラクタ
    // ==================================================
    constructor(x, y, speed) {
        super(x, y, speed);
        this.startX = x;
        this.startY = y;

        // ステータス管理
        this.hp = 3;
        this.score = 0;
        this.isAliveStatus = true; // Javaの isAlive と名前の重複を避けるため
        this.fever = false;

        // 方向管理
        this.nextdirection = Direction.NONE;

        // 死亡アニメーション関連
        this.isDyingAnimationStatus = false;
        this.dyingTimer = 0;
    }

    // ==================================================
    // タイマー / アニメーション
    // ==================================================
    getDyingTimer() {
        return this.dyingTimer;
    }

    startDying() {
        this.isDyingAnimationStatus = true;
        this.dyingTimer = 0;
        this.direction = Direction.NONE;
        this.nextdirection = Direction.NONE;
    }

    updateDyingAnimation() {
        if (!this.isDyingAnimationStatus) return false;
        
        this.dyingTimer++;
        // 約60フレーム（約1秒）継続
        if (this.dyingTimer < 60) {
            return false;
        }
        this.isDyingAnimationStatus = false;
        return true;
    }

    // ==================================================
    // ポジション / 状態確認
    // ==================================================
    resetToStartPosition() {
        this.x = this.startX;
        this.y = this.startY;
        this.direction = Direction.NONE;
        this.nextdirection = Direction.NONE;
        this.isDyingAnimationStatus = false;
        this.dyingTimer = 0;
    }

    isAlive() {
        return this.hp > 0;
    }

    isFever() {
        return this.fever;
    }

    die() {
        this.hp = 0;
        this.direction = Direction.NONE;
    }

    // ==================================================
    // スコア・ダメージ処理
    // ==================================================
    addScore(point) {
        this.score += point;
    }

    decreaseHp() {
        if (this.hp > 0) {
            this.hp--;
            if (this.hp <= 0) {
                this.isAliveStatus = false;
            }
        }
    }

    takeDamage() {
        if (this.hp > 0) {
            this.hp--;
            if (this.hp === 0) {
                this.die();
            }
        }
    }

    // ==================================================
    // 移動ロジック
    // ==================================================
    move(map) {
        // 死亡中は移動禁止
        if (this.isDyingAnimationStatus || !this.isAlive()) return;

        // 曲がれる場合は方向転換 (JavaのMath.roundを再現)
        if (this._isAligned() && this._canMove(this.nextdirection, map)) {
            this.direction = this.nextdirection;
            // マスの中心へ補正
            this.x = Math.round(this.x / CELL_SIZE) * CELL_SIZE;
            this.y = Math.round(this.y / CELL_SIZE) * CELL_SIZE;
        }

        // 現在の方向が壁なら停止
        if (!this._canMove(this.direction, map)) {
            this.direction = Direction.NONE;
        }

        // 移動
        if (this.direction !== Direction.NONE) {
            this.x += this.direction.dx * this.speed;
            this.y += this.direction.dy * this.speed;
        }
    }

    // 指定方向へ進めるか判定
    _canMove(direction, map) {
        if (direction === Direction.NONE) {
            return false;
        }

        // 進行方向の「先端座標」を計算する
        let checkX = this.x;
        let checkY = this.y;

        if (direction === Direction.RIGHT) {
            checkX = this.x + CELL_SIZE - 1 + this.speed;
        } else if (direction === Direction.LEFT) {
            checkX = this.x - this.speed;
        } else if (direction === Direction.DOWN) {
            checkY = this.y + CELL_SIZE - 1 + this.speed;
        } else if (direction === Direction.UP) {
            checkY = this.y - this.speed;
        }

        // JavaScriptでは整数にキャストするために Math.floor を使う
        let checkCol = Math.floor(checkX / CELL_SIZE);
        let checkRow = Math.floor(checkY / CELL_SIZE);

        // マップ範囲外チェック
        if (checkRow < 0 || checkRow >= map.length || checkCol < 0 || checkCol >= map[0].length) {
            return false;
        }

        // 壁(1)以外なら通行可能
        return map[checkRow][checkCol] !== 1;
    }

    // マス境界に揃っているか（曲がれるタイミング）
    _isAligned() {
        return (this.x % CELL_SIZE === 0) && (this.y % CELL_SIZE === 0);
    }

    // ==================================================
    // 方向決定（外部入力・フリックから呼ばれる）
    // ==================================================
    setNextDirection(direction) {
        this.nextdirection = direction;
        // 停止中は即座に方向変更
        if (this.direction === Direction.NONE && !this.isDyingAnimationStatus) {
            this.direction = direction;
        }
    }

    // 呼び出し側の表記のブレを吸収するメソッド
    setnextDirection(direction) { this.setNextDirection(direction); }
    setnextdirection(direction) { this.setNextDirection(direction); }

    // ==================================================
    // ゲッター / セッター
    // ==================================================
    getDyingProgress() {
        return Math.min(1.0, this.dyingTimer / 60.0);
    }

    getScore() { return this.score; }
    getHp() { return this.hp; }
    isDyingAnimation() { return this.isDyingAnimationStatus; }
    setFever(fever) { this.fever = fever; }
    
    setPosition(x, y) {
        this.x = x;
        this.y = y;
    }
    setX(x) { this.x = x; }
    setY(y) { this.y = y; }
}