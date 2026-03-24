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

## 🧠 Coding Rules

* Tên class: PascalCase (Player, Enemy)
* Tên biến: camelCase (playerX, enemySpeed)
* Mỗi class 1 nhiệm vụ


---->T thấy trong slide OOP có dạy javafx nên ae dùng javafx cho đồng bộ nhá
Mà hình như JAVA mới ko tích hợp javafx nên chắc ae phải cài thủ công







