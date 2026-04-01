Tong quan `core/`

core/
+-- Game.java
+-- GameLoop.java
+-- GameState.java

1. `Game.java`

- Trung tam dieu phoi toan bo game.
- Tao va noi cac thanh phan chinh: `GameLoop`, `Renderer`, `InputHandler`.
- Giu `GameState` hien tai.
- Goi `update()` va `render()`.

Khong nen lam trong class nay:
- Viet logic chien dau cu the.
- Ve truc tiep tung enemy/player.
- Xu ly chi tiet input.

2. `GameLoop.java`

- Vong lap cua game.
- Moi frame se goi:
  `game.update(now);`
  `game.render();`

3. `GameState.java`

- Enum quan ly trang thai game.
- Ban dau chi can:
  `MENU`, `PLAYING`, `PAUSED`, `GAME_OVER`
