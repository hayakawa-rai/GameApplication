
//Characters/Character.js のイメージ
export class Character {
 constructor(x, y, speed = 1) {
     this.x = x;          // 現在のX座標
     this.y = y;          // 現在のY座標
     this.speed = speed;  // 移動速度
     this.direction = "NONE"; // 初期状態は停止 (列挙型の代わり)
 }

 // Javaの抽象メソッドの代わり
 move(map) {
     throw new Error("move(map) メソッドが子クラスで実装されていません！");
 }

 // JavaScriptでは get キーワードで getter をスマートに書けます
 get X() { return this.x; }
 get Y() { return this.y; }
 get Speed() { return this.speed; }
 get Direction() { return this.direction; }
}