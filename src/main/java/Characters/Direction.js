
export const Direction = Object.freeze({
    UP:    { name: "UP",    dx:  0, dy: -1 },
    DOWN:  { name: "DOWN",  dx:  0, dy:  1 },
    LEFT:  { name: "LEFT",  dx: -1, dy:  0 },
    RIGHT: { name: "RIGHT", dx:  1, dy:  0 },
    NONE:  { name: "NONE",  dx:  0, dy:  0 }
});

// JavaFXの fromKeyCode の代わり（ブラウザのKeyboardEvent.keyを受け取る）
export function directionFromKey(key) {
    switch (key) {
        case "ArrowUp":    case "w": case "W": return Direction.UP;
        case "ArrowDown":  case "s": case "S": return Direction.DOWN;
        case "ArrowLeft":  case "a": case "A": return Direction.LEFT;
        case "ArrowRight": case "d": case "D": return Direction.RIGHT;
        default: return Direction.NONE;
    }
}