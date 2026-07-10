import { Character } from './Character.js';
import { Direction } from './Direction.js';

// 敵の状態を固定値として定義（JavaのEnumの代わり）
export const EnemyState = Object.freeze({
    SCATTER: "SCATTER",
    FEVER: "FEVER",
    DEAD: "DEAD"
});

// タイルサイズの共通設定
const TILE_SIZE = 32; 

export class Enemy extends Character {

    // ==================================================
    // コンストラクタ
    // ==================================================
    constructor(startX, startY, speed) {
        super(startX, startY, speed);

        // 初期位置を保存（リスポーン用）
        this.startX = startX;
        this.startY = startY;

        // 現在の敵の状態
        this.currentState = EnemyState.SCATTER;

        // 画像管理
        this.normalImage = new Image();
        this.feverImage = new Image();
        this.deadImage = new Image();

        // ポーズ時間管理
        this.pauseStartTime = 0;

        // スコアポップアップ表示用
        this.lastDefeatScore = 0;
        this.scorePopupStartTime = 0;
        this.scorePopupActive = false;
        this.SCORE_POPUP_DURATION = 1000; // 表示時間(ms)
        this.defeatX = 0;
        this.defeatY = 0;

        // ステージデータ保持用
        this.mapData = null; 
    }

    // ==================================================
    // タイマー
    // ==================================================
    pauseTimer() {
        this.pauseStartTime = Date.now();
    }

    resumeTimer() {
    }

    // ==================================================
    // 画像読み込み設定
    // ==================================================
    setMapData(mapData) {
        this.mapData = mapData;
        this.loadFeverImage();
        this.loadDeadImage();
    }

    loadFeverImage() {
        let feverPath = "./picture/nari_EnemyFever.png"; 
        if (this.mapData) {
            switch (this.mapData.getStageNumber()) {
                case 1: feverPath = "./picture/nari_EnemyFever.png"; break;
                case 2: feverPath = "./picture/taku_EnemyFever.png"; break;
                case 3: feverPath = "./picture/aniki_EnemyFever.png"; break;
            }
        }
        this.feverImage.src = feverPath;
    }

    loadDeadImage() {
        let deadPath = "./picture/nari_EnemyDead.png"; 
        if (this.mapData) {
            switch (this.mapData.getStageNumber()) {
                case 1: deadPath = "./picture/nari_EnemyDead.png"; break;
                case 2: deadPath = "./picture/taku_EnemyDead.png"; break;
                case 3: deadPath = "./picture/aniki_EnemyDead.png"; break;
            }
        }
        this.deadImage.src = deadPath;
    }

    // ==================================================
    // スコア表示判定
    // ==================================================
    isScorePopupActive() {
        if (this.scorePopupActive && (Date.now() - this.scorePopupStartTime > this.SCORE_POPUP_DURATION)) {
            this.scorePopupActive = false;
        }
        return this.scorePopupActive;
    }

    onDefeated(score) {
        this.lastDefeatScore = score;
        this.scorePopupStartTime = Date.now();
        this.scorePopupActive = true;
        this.defeatX = this.x; 
        this.defeatY = this.y;
        this.setCurrentState(EnemyState.DEAD);
    }

    // ==================================================
    // ポジションリセット
    // ==================================================
    resetToStartPosition() {
        this.x = this.startX;
        this.y = this.startY;
        this.direction = Direction.NONE;
        this.currentState = EnemyState.SCATTER;
    }

    // ==================================================
    // 移動メインロジック
    // ==================================================
    move(map) {
        let tileX = Math.floor(this.x / TILE_SIZE);
        let tileY = Math.floor(this.y / TILE_SIZE);

        if (tileY < 0 || tileY >= map.length || tileX < 0 || tileX >= map[0].length) {
            return;
        }

        let cx = tileX * TILE_SIZE + TILE_SIZE / 2.0;
        let cy = tileY * TILE_SIZE + TILE_SIZE / 2.0;

        let currentTileType = map[tileY][tileX];

        if (this.currentState === EnemyState.DEAD && currentTileType === 8) {
            this.currentState = EnemyState.SCATTER;
            console.log(`${this.constructor.name} が巣に帰還し、復活しました`);
        }

        let currentSpeed = this.getSpeed();
        if (this.currentState === EnemyState.FEVER) {
            currentSpeed = this.getSpeed() * 0.5;
        }
        if (this.currentState === EnemyState.DEAD) {
            currentSpeed = this.getSpeed() * 3;
        }

        let atCenter = Math.abs(this.x - cx) < currentSpeed && Math.abs(this.y - cy) < currentSpeed;

        if (this.direction === Direction.NONE || atCenter) {

            let validDirections = this._getValidDirections(map);
            if (validDirections.length > 0) {

                let currentRow = Math.floor(this.y / TILE_SIZE);
                let currentCol = Math.floor(this.x / TILE_SIZE);

                if (this.currentState !== EnemyState.DEAD && currentRow >= 12 && currentRow <= 15 && currentCol >= 12 && currentCol <= 15) {
                    this.y = cy;
                    this.x = cx;
                    this.direction = Direction.UP;
                } else {
                    let chosenDirection = this.decideNextDirection(validDirections, map, this.mapData);

                    this.x = cx;
                    this.y = cy;
                    this.direction = chosenDirection;
                }
            } else {
                this.direction = Direction.NONE;
            }
        }

        if (this.direction !== Direction.NONE) {
            this.x += this.direction.dx * currentSpeed;
            this.y += this.direction.dy * currentSpeed;
            
            if (this.direction.dx !== 0) {
                this.y += (cy - this.y) * 0.2;
            }
            if (this.direction.dy !== 0) {
                this.x += (cx - this.x) * 0.2;
            }
        }
    }

    _canMove(direction, map) {
        if (direction === Direction.NONE) return false;

        let currentCol = Math.floor(this.x / TILE_SIZE);
        let currentRow = Math.floor(this.y / TILE_SIZE);

        let nextCol = currentCol + direction.dx;
        let nextRow = currentRow + direction.dy;

        if (nextRow < 0 || nextRow >= map.length || nextCol < 0 || nextCol >= map[0].length) {
            return false;
        }
        if (map[nextRow][nextCol] === 1) {
            return false;
        }

        let currentTileType = map[currentRow][currentCol];
        let nextTileType = map[nextRow][nextCol];

        if (this.currentState !== EnemyState.DEAD) {
            if (currentTileType !== 8 && (nextTileType === 7 || nextTileType === 8)) {
                return false;
            }
        }
        return true;
    }

    handleSpecialState(validDirections, targetCol, targetRow, map) {
        if (this.currentState === EnemyState.DEAD) {
            let bestGateCol = 14;
            let bestGateRow = 13;
            let minDistanceSq = Infinity;

            let myCol = Math.floor(this.x / TILE_SIZE);
            let myRow = Math.floor(this.y / TILE_SIZE);

            for (let r = 0; r < map.length; r++) {
                for (let c = 0; c < map[r].length; c++) {
                    if (map[r][c] === 7) {
                        let distSq = Math.pow(c - myCol, 2) + Math.pow(r - myRow, 2);
                        if (distSq < minDistanceSq) {
                            minDistanceSq = distSq;
                            bestGateCol = c;
                            bestGateRow = r;
                        }
                    }
                }
            }
            return this.getClosestDirection(validDirections, bestGateCol, bestGateRow);
        }

        if (this.currentState === EnemyState.FEVER) {
            return this.getFarthestDirection(validDirections, targetCol, targetRow);
        }
        return null;
    }

    // 💡 ここの for (let dir of validDirections) に修正しました！
    getClosestDirection(validDirections, targetCol, targetRow) {
        let bestDirection = Direction.NONE;
        let minDistance = Infinity;
        let currentCol = Math.floor(this.x / TILE_SIZE);
        let currentRow = Math.floor(this.y / TILE_SIZE);

        for (let dir of validDirections) {
            let nextCol = currentCol + dir.dx;
            let nextRow = currentRow + dir.dy;
            let distanceSq = Math.pow(nextCol - targetCol, 2) + Math.pow(nextRow - targetRow, 2);

            if (distanceSq < minDistance) {
                minDistance = distanceSq;
                bestDirection = dir;
            }
        }
        return bestDirection !== Direction.NONE ? bestDirection : validDirections[0];
    }

    // 💡 ここの for (let dir of validDirections) に修正しました！
    getFarthestDirection(validDirections, targetCol, targetRow) {
        let bestDirection = Direction.NONE;
        let maxDistance = -1;
        let currentCol = Math.floor(this.x / TILE_SIZE);
        let currentRow = Math.floor(this.y / TILE_SIZE);

        for (let dir of validDirections) {
            let nextCol = currentCol + dir.dx;
            let nextRow = currentRow + dir.dy;
            let distanceSq = Math.pow(nextCol - targetCol, 2) + Math.pow(nextRow - targetRow, 2);

            if (distanceSq > maxDistance) {
                maxDistance = distanceSq;
                bestDirection = dir;
            }
        }
        return bestDirection !== Direction.NONE ? bestDirection : validDirections[0];
    }

    _isOppositeDirection(dir1, dir2) {
        if (dir1 === Direction.NONE || dir2 === Direction.NONE) return false;
        return (dir1.dx + dir2.dx === 0) && (dir1.dy + dir2.dy === 0);
    }

    _getValidDirections(map) {
        let list = [];
        for (let key in Direction) {
            let dir = Direction[key];
            if (dir === Direction.NONE) continue;
            if (this._isOppositeDirection(dir, this.direction)) continue;

            if (this._canMove(dir, map)) {
                list.push(dir);
            }
        }
        return list;
    }

    decideNextDirection(validDirections, map, mapData) {
        throw new Error("decideNextDirection() を子クラスで実装してください。");
    }

    // ==================================================
    // ゲッター / セッター
    // ==================================================
    getEnemyImage() {
        if (this.currentState === EnemyState.DEAD) return this.deadImage;
        if (this.currentState === EnemyState.FEVER) return this.feverImage;
        return this.normalImage;
    }

    getScorePopupProgress() {
        let elapsed = Date.now() - this.scorePopupStartTime;
        return Math.min(1.0, Math.max(0.0, elapsed / this.SCORE_POPUP_DURATION));
    }

    getCurrentState() { return this.currentState; }
    setCurrentState(state) { this.currentState = state; }
    getLastDefeatScore() { return this.lastDefeatScore; }
    getDefeatX() { return this.defeatX; }
    getDefeatY() { return this.defeatY; }
}