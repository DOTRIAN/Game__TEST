Thứ quan trọng nhất trong AnimationTimer là:
1.
start()
•
bắt đầu timer
•
sau khi gọi, JavaFX sẽ liên tục gọi handle(...)
2.
stop()
•
dừng timer
3.
handle(long now)
•
đây là method bạn phải override
•
nó chạy lặp lại nhiều lần mỗi giây
•
thường dùng để update game và render


**handle()
Vậy nên handle() không phải chỉ để vẽ, mà là để chạy một vòng frame của game.
“bao nhiêu lần theo thời gian”
•
AnimationTimer cố chạy gần với nhịp refresh của màn hình
•
thường khoảng 60 lần/giây nếu máy và màn hình phù hợp
•
nhưng không nên hiểu là nó đảm bảo đúng tuyệt đối 60 FPS
Nói đơn giản Mỗi lần handle() được gọi là:
•
game tiến thêm một nhịp rất nhỏ
•
rồi màn hình được vẽ lại theo trạng thái mới
Giống như phim hoạt hình:
•
mỗi frame là một ảnh
•
handle() là lúc tạo ra frame tiếp theo