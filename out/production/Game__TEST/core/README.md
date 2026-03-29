TỔNG QUAN core/
core/
├── Game.java
├── GameLoop.java
├── Renderer.java
├── InputHandler.java
└── (optional) GameConfig.java


1. Game.java (QUAN TRỌNG NHẤT)

👉 Trung tâm điều phối (orchestrator)

Nó làm gì?
tạo các thành phần:
GameLoop
Renderer
InputHandler
giữ trạng thái game
quyết định gọi ai
Nó KHÔNG làm:
❌ không tự loop
❌ không trực tiếp xử lý input
❌ không vẽ chi tiết


2. GameLoop.java

👉 Trái tim của game (vòng lặp)

Nó làm gì?
chạy liên tục (~60 FPS)
gọi:
game.update();
game.render();


3. Renderer.java

👉 Chịu trách nhiệm vẽ

Nó làm gì?
clear màn hình
vẽ:
player
enemy
UI

4. InputHandler.java

👉 Xử lý bàn phím / chuột

Nó làm gì?
lắng nghe phím:
W, A, S, D
lưu trạng thái:
boolean up, down, left, right;