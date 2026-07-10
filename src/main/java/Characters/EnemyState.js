// 敵の状態を固定値として定義（JavaのEnumの代わり）
export const EnemyState = Object.freeze({
    SCATTER: "SCATTER", // 縄張りモード
    CHASE:   "CHASE",   // 追跡モード（★これを追加！）
    FEVER:   "FEVER",   // フィーバーモード
    DEAD:    "DEAD"     // 死亡・巣に戻るモード
});