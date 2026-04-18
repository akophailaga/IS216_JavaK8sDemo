# K8s & Docker in Action — IS216 Seminar Demo

**Java 21 | Spring Boot 3 | Docker | Kubernetes**

**Tác giả:** Nhóm Nần Ná Nân Na  
**Học phần:** Lập trình Java (IS216) - Đại học Công nghệ Thông tin

---
## 1. Giới thiệu

Project này là bài demo cho seminar môn IS216, minh họa phương pháp triển khai một ứng dụng Java theo kiến trúc Containerized Application. Ứng dụng được xây dựng bằng Spring Boot, được đóng gói bằng Docker và vận hành trên nền tảng Kubernetes nhằm tận dụng các khả năng tự động mở rộng (scaling) và tự phục hồi (self-healing).

### Mục tiêu
* Khẳng định vai trò và lợi ích của Container trong phát triển phần mềm.
* Giải thích khái niệm và cấu trúc của Docker Image.
* Xây dựng Dockerfile tối ưu (áp dụng Multi-stage build) cho ứng dụng Java.
* Triển khai và quản lý vòng đời ứng dụng trên môi trường Kubernetes.

---

## 2. Kiến trúc & Công nghệ

**Luồng hoạt động:** User -> Kubernetes Service -> Pod (Java App Container)

| Layer | Technology |
| :--- | :--- |
| **Language** | Java 21 |
| **Framework** | Spring Boot 3 |
| **Build Tool** | Maven |
| **Containerization** | Docker |
| **Orchestration** | Kubernetes (Minikube / Docker Desktop) |

### Cấu trúc Project
```text
.
├── k8s/                        
│   ├── deployment.yaml         
│   └── service.yaml            
├── src/                        
├── Dockerfile                  
├── pom.xml                     
└── README.md
```

## 3. Yêu cầu hệ thống (Prerequisites)

Để chạy được project này trên máy cá nhân, vui lòng đảm bảo máy tính của bạn đã cài đặt các công cụ sau:
* **Git**: Để clone repository.
* **Docker Desktop** (hoặc Docker Engine): Đã bật tính năng **Kubernetes** trong cài đặt (Settings > Kubernetes > Enable Kubernetes), hoặc sử dụng **Minikube**.
* **kubectl**: Công cụ dòng lệnh để giao tiếp với Kubernetes cluster.
* *(Tùy chọn)* **Java 21 & Maven**: Nếu bạn muốn chỉnh sửa code và chạy thử ở môi trường local trước khi đóng gói.
---

## 4. Hướng dẫn Thiết lập & Triển khai (Setup & Deployment)

### Bước 1: Clone Repository
Mở Terminal/Command Prompt và chạy lệnh sau để tải source code về máy:
bash
```
git clone https://github.com/akophailaga/IS216_JavaK8sDemo.git
cd DEMO
```

### Bước 2: Triển khai lên Kubernetes (K8s)
Ứng dụng đã được định nghĩa sẵn cấu hình trong thư mục k8s/. Bạn không cần phải tự build lại Docker Image nếu cấu hình deployment.yaml đã trỏ đến một image có sẵn trên Docker Hub.

Để yêu cầu Kubernetes khởi tạo hệ thống, chạy lệnh:
```Bash
kubectl apply -f k8s/
```
### Bước 3: Kiểm tra trạng thái hệ thống
Kiểm tra xem các máy chủ ảo (Pods) đã được tạo và chạy thành công chưa:

```Bash
kubectl get pods
```
Đợi đến khi cột STATUS của tất cả các Pod chuyển sang trạng thái Running.

Kiểm tra Service để biết cổng (port) truy cập:

```Bash
kubectl get services
```
### Bước 4: Truy cập Ứng dụng
Mở trình duyệt web và truy cập vào địa chỉ:
-> http://localhost:30001 (Lưu ý: Thay đổi cổng 30001 thành NodePort tương ứng nếu cấu hình service.yaml của bạn sử dụng cổng khác).

## 5. Hướng dẫn Test các tính năng cốt lõi (Demo Scenarios)
Dưới đây là các kịch bản test để thấy rõ sức mạnh của Kubernetes trong kiến trúc hệ thống:

🧪 Kịch bản 1: Cân bằng tải (Load Balancing)
Kiểm tra khả năng chia đều lượng truy cập của K8s.

Thao tác: Mở giao diện web và tải lại trang (Refresh/F5) hoặc bấm nút gửi Request liên tục.

Kết quả: Bạn sẽ thấy Tên Node/Pod xử lý (Pod Name) thay đổi luân phiên giữa các ID khác nhau, chứng tỏ hệ thống đang điều phối traffic rất tốt, không dồn tải vào một máy.

🧪 Kịch bản 2: Tự chữa lành (Self-Healing)
Mô tả: Giả lập sự cố máy chủ bị tắt đột ngột do lỗi phần cứng hoặc thao tác nhầm.

Thao tác: 
1. Mở Terminal, gõ
```bash
kubectl get pods -w để theo dõi realtime.
```
2. Mở một tab Terminal khác, copy tên của một Pod đang chạy và "kill" nó bằng lệnh:
```bash
kubectl delete pod <tên-pod-muốn-xóa>
```

Kết quả: Ngay lập tức, K8s sẽ phát hiện hệ thống bị hụt số lượng máy chủ so với cấu hình và tự động khởi tạo một Pod mới tinh để thay thế. Ứng dụng web vẫn hoạt động bình thường mà không bị gián đoạn (Downtime).

🧪 Kịch bản 3: Tự động Mở rộng (Scaling)
Mô tả: Xử lý tình huống lượng người dùng truy cập tăng vọt đột biến.

Thao tác: Chạy lệnh sau để tăng số lượng bản sao (Replicas) từ 2 lên 5 máy:

```Bash
kubectl scale deployment java-app-deployment --replicas=5
```
Kết quả: K8s ngay lập tức nhân bản ứng dụng ra thêm 3 Pods mới. Khi kiểm tra trên giao diện Web, lượng request sẽ được chia đều cho cả 5 máy chủ này.

## 6. Dọn dẹp tài nguyên (Clean up)
Sau khi test xong, để giải phóng tài nguyên cho máy tính, bạn hãy chạy lệnh sau để gỡ bỏ toàn bộ Deployment và Service:

```Bash
kubectl delete -f k8s/
```
