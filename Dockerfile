# === STAGE 1: Build (Môi trường biên dịch) ===
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app

# Copy file cấu hình pom.xml và tải các thư viện trước để tối ưu thời gian build
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy toàn bộ thư mục code (src) vào và build ra file .jar
COPY src ./src
RUN mvn clean package -DskipTests

# === STAGE 2: Runtime (Môi trường chạy thực tế) ===
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Lấy file .jar đã build xong từ STAGE 1 (builder) mang sang đây
COPY --from=builder /app/target/*.jar app.jar

# Mở port 8080 để giao tiếp
EXPOSE 8080

# Lệnh khởi chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]