// Characters/SyujinkouState.js

/**
 * 主人公（仙石さん）の状態を管理するオブジェクト（JavaのEnumの代わり）
 * 各状態に「状態名(name)」と「速度倍率(speedMultiplier)」を持たせています。
 */
export const SyujinkouState = Object.freeze({
    
    // 通常状態 (速度倍率 1.0)
    NORMAL: Object.freeze({
        name: "NORMAL",
        speedMultiplier: 1.0
    }),

    // フィーバー状態 (速度倍率 1.2 にすることでEnemyより早くなり、食べることができる)
    FEVER: Object.freeze({
        name: "FEVER",
        speedMultiplier: 1.2
    })
});