OOP:
•
Game là class đại diện cho toàn bộ trò chơi.
•
Sau này nó sẽ “chứa” nhiều object khác.



Tại sao không để InputHandler tự gọi player.moveLeft()?
Có thể bạn sẽ nghĩ:
•
InputHandler biết phím nào đang nhấn
•
vậy sao không cho nó điều khiển player luôn?
Vì làm vậy sẽ trộn trách nhiệm.
InputHandler nên chỉ trả lời câu hỏi:
•
phím nào đang nhấn?
Còn Game mới nên quyết định:
•
khi nhấn phím đó thì gameplay sẽ làm gì
Hôm nay:
•
A là đi trái
Sau này:
•
nếu đang mở menu, A có thể lại có nghĩa khác
•
nếu đang dialogue, W có thể dùng để chọn menu
Nên Game là nơi điều phối hợp lý hơn.