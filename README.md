# 🏹 Tribe Survival Game (2D)


Game 2D viết bằng Java (OOP), người chơi nhập vai tộc trưởng sinh tồn qua các đợt tấn công ban đêm.

---

## 🎮 Gameplay (MVP)

* Di chuyển nhân vật
* Enemy đuổi theo
* Combat (HP, attack)
* 1 màn chơi cơ bản (day/night)

IDE: ae dùng intelliJ cho đồng bộ nhá

##  Cấu trúc project

```
src/
 ├── main/        # Entry point (Main.java)
 ├── core/        # Game loop, render, input
 ├── entity/      # Player, Enemy
 ├── system/      # Combat, AI, Resource
 ├── ui/          # HUD, menu
 └── assets/      # Hình ảnh, âm thanh


## 🔁 Workflow (Git)

* Không push trực tiếp vào `main`
* Làm việc trên branch riêng:
  Sẽ có nhánh main/dev/các feature branch

feature/player
feature/enemy
feature/combat


* Tạo Pull Request trước khi merge

---
 Rule dùng AI

* Không copy code trực tiếp từ AI
* Phải hiểu code trước khi thêm vào project
* Code phải đúng structure của team

---



Rule đặt tên:  SOOO IMPORTANT
vowis class thì chữ cái đầu viết hoa, nếu gồm 2 từ ghép thì viết hoa chữ cái đầu tiên 
của mỗi từ. Ví dụ: HieuNgo
với tên biến thì ko viết hoa chữ đầu, còn lại có thể giống
Ví dụ: public class HieuNgo {
 HieuNgo hieuNgo;
 }




## 🧠 Coding Rules

* Tên class: PascalCase (Player, Enemy)
* Tên biến: camelCase (playerX, enemySpeed)
* Mỗi class 1 nhiệm vụ


---->T thấy trong slide OOP có dạy javafx nên ae dùng javafx cho đồng bộ nhá
Mà hình như JAVA mới ko tích hợp javafx nên chắc ae phải cài thủ công

src/
├── main/
│    └── Main.java
│
├── core/
│    ├── Game.java
│    ├── GameLoop.java
│    └── GameState.java
│
├── system/
│    ├── InputSystem.java
│    ├── MovementSystem.java
│    ├── CollisionSystem.java
│
├── entity/
│    ├── Player.java
│    ├── Enemy.java
│
├── ui/
│    └── Renderer.java
│
└── assets/

Mình khuyên scope khả thi hơn
Nên giữ bản MVP như sau:
•
3 màn thay vì 6 màn
•
3 tài nguyên chính: Wood, Stone, Food
•
2 công trình chính: Hut, Fence
•
2 vũ khí: StoneSpear, Bow
•
2 loại địch: Wolf, Boar
•
1 event ngẫu nhiên đơn giản
•
giữ Day/Night cycle
•
giữ Stage objective
Tạm hoãn hoặc để bản nâng cao:
•
Tiger
•
Boss Alpha Beast
•
Watchtower
•
Advanced Bow
•
Torch
•
hệ Tribe Spirit nếu chưa đủ thời gian
Lý do:
•
vẫn thể hiện được OOP rõ ràng
•
vẫn có gameplay đủ “ra game”
•
giảm số lượng class phụ thuộc lẫn nhau
•
2 người dễ chia việc hơn
Những chỗ áp dụng OOP rất đẹp
1.
Inheritance kế thừa
Dùng cho các đối tượng có nhiều điểm chung.
Ví dụ:
•
Entity là lớp cha cho mọi thực thể có vị trí, máu
•
Character kế thừa từ Entity
•
Enemy kế thừa từ Character
•
Wolf, Boar, Tiger kế thừa từ Enemy
Ý nghĩa:
•
các enemy đều có hp, attack, move(), takeDamage()
•
mỗi loại chỉ override phần hành vi riêng
2.
Abstract class lớp trừu tượng
Dùng khi muốn định nghĩa khung chung nhưng không tạo object trực tiếp.
Ví dụ:
•
abstract class Enemy
•
abstract class Building
•
abstract class Item
Ý nghĩa:
•
không có “enemy chung chung”, chỉ có Wolf, Boar
•
nhưng vẫn gom logic chung vào lớp cha
3.
Interface
Dùng để mô tả khả năng.
Ví dụ:
•
Damageable có takeDamage(int amount)
•
Collectible có collect()
•
Updatable có update(double deltaTime)
•
Renderable nếu bạn muốn tách phần hiển thị
Ý nghĩa:
•
Enemy, Building, thậm chí Player đều có thể bị đánh, nên cùng implement Damageable
•
tài nguyên như cây, đá, quả có thể implement Collectible
4.
Polymorphism đa hình
Đây là phần rất nên có trong đồ án OOP.



Gợi ý package tối thiểu cho cả project
src/
  main/
    Main.java
  core/
    Game.java
    GameLoop.java
    GameState.java
    GameConfig.java
  input/
    InputHandler.java
  ui/
    Renderer.java
  entity/
    Player.java
    Enemy.java





