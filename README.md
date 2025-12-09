# Truyen - Story Management API

## 📖 Giới thiệu

Truyen là một ứng dụng RESTful API được xây dựng bằng Spring Boot để quản lý truyện, chương, bình luận, lịch sử đọc và các tính năng liên quan. Dự án sử dụng PostgreSQL làm cơ sở dữ liệu và tích hợp Spring Security để bảo mật.

## 🚀 Công nghệ sử dụng

- **Java**: 21
- **Spring Boot**: 3.3.5
- **Spring Data JPA**: Quản lý dữ liệu
- **Spring Security**: Bảo mật ứng dụng
- **Spring Validation**: Xác thực dữ liệu
- **PostgreSQL**: Cơ sở dữ liệu
- **Lombok**: Giảm boilerplate code
- **SpringDoc OpenAPI**: Tài liệu API (Swagger UI)
- **Maven**: Quản lý dependencies

## 📋 Yêu cầu hệ thống

- Java Development Kit (JDK) 21 trở lên
- Maven 3.6+
- PostgreSQL 12+
- IDE: IntelliJ IDEA, Eclipse, hoặc VS Code

## ⚙️ Cài đặt

### 1. Clone repository

```bash
git clone <repository-url>
cd truyen
```

### 2. Cấu hình Database

Tạo database PostgreSQL:

```sql
CREATE DATABASE sangtacviet;
```

### 3. Cấu hình application.properties

Cập nhật file `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/sangtacviet
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

# Server Port
server.port=8088

# Google AI Configuration (Optional)
spring.ai.google.genai.api-key=YOUR_GOOGLE_API_KEY
spring.ai.google.genai.project-id=YOUR_GOOGLE_PROJECT_ID
spring.ai.google.genai.location=us-central1
```

### 4. Build và chạy ứng dụng

```bash
# Build project
./mvnw clean install

# Chạy ứng dụng
./mvnw spring-boot:run
```

Hoặc trên Windows:

```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

Ứng dụng sẽ chạy tại: `http://localhost:8088`

## 📚 API Documentation

Sau khi chạy ứng dụng, truy cập Swagger UI tại:

```
http://localhost:8088/swagger-ui.html
```

### API Endpoints

#### 1. **Story API** (`/api/stories`)

| Method | Endpoint | Mô tả | Request Body |
|--------|----------|-------|--------------|
| POST | `/api/stories/create` | Tạo truyện mới | storyDTO |
| PUT | `/api/stories/update/{id}` | Cập nhật truyện | storyDTO |
| DELETE | `/api/stories/delete/{id}` | Xóa truyện | - |
| GET | `/api/stories/get/{id}` | Lấy truyện theo ID | - |
| GET | `/api/stories/search?title={title}` | Tìm truyện theo tên | - |
| GET | `/api/stories/get_all` | Lấy tất cả truyện | - |

#### 2. **Chapter API** (`/api/chapters`)

| Method | Endpoint | Mô tả | Request Body |
|--------|----------|-------|--------------|
| POST | `/api/chapters/create` | Tạo chương mới | ChapterDTO |
| PUT | `/api/chapters/update/{id}` | Cập nhật chương | ChapterDTO |
| DELETE | `/api/chapters/delete/{id}` | Xóa chương | - |
| GET | `/api/chapters/get/{id}` | Lấy chương theo ID | - |
| GET | `/api/chapters/get_all` | Lấy tất cả chương | - |
| GET | `/api/chapters/story/{storyId}` | Lấy các chương của truyện | - |
| GET | `/api/chapters/story/{storyId}/number/{chapterNumber}` | Lấy chương theo số thứ tự | - |
| GET | `/api/chapters/search?title={title}` | Tìm chương theo tên | - |

#### 3. **Comment API** (`/api/comments`)

| Method | Endpoint | Mô tả | Request Body |
|--------|----------|-------|--------------|
| POST | `/api/comments/create` | Tạo bình luận mới | CommentDTO |
| PUT | `/api/comments/update/{id}` | Cập nhật bình luận | CommentDTO |
| DELETE | `/api/comments/delete/{id}` | Xóa bình luận | - |
| GET | `/api/comments/get/{id}` | Lấy bình luận theo ID | - |
| GET | `/api/comments/get_all` | Lấy tất cả bình luận | - |
| GET | `/api/comments/story/{storyId}` | Lấy bình luận của truyện | - |
| GET | `/api/comments/chapter/{chapterId}` | Lấy bình luận của chương | - |
| GET | `/api/comments/user/{userId}` | Lấy bình luận của user | - |

#### 4. **History API** (`/api/history`)

| Method | Endpoint | Mô tả | Request Body |
|--------|----------|-------|--------------|
| POST | `/api/history/save` | Lưu/Cập nhật lịch sử đọc | HistoryDTO |
| DELETE | `/api/history/delete/{id}` | Xóa lịch sử | - |
| GET | `/api/history/get/{id}` | Lấy lịch sử theo ID | - |
| GET | `/api/history/get_all` | Lấy tất cả lịch sử | - |
| GET | `/api/history/user/{userId}` | Lấy lịch sử của user | - |
| GET | `/api/history/story/{storyId}` | Lấy lịch sử của truyện | - |
| GET | `/api/history/user/{userId}/story/{storyId}` | Lấy lịch sử user đọc truyện | - |

#### 5. **User API** (`/api/user`)

| Method | Endpoint | Mô tả | Request Body |
|--------|----------|-------|--------------|
| POST | `/api/user/register` | Đăng ký user mới | userDTO |
| POST | `/api/user/login` | Đăng nhập | LoginRequest |
| GET | `/api/user/name/{username}` | Lấy user theo username | - |
| GET | `/api/user/id/{id}` | Lấy user theo ID | - |

#### 6. **Tag API** (`/api/tags`)

| Method | Endpoint | Mô tả | Request Body |
|--------|----------|-------|--------------|
| POST | `/api/tags/create` | Tạo tag mới | tagDTO |
| PUT | `/api/tags/update/{id}` | Cập nhật tag | tagDTO |
| DELETE | `/api/tags/delete/{id}` | Xóa tag | tagDTO |
| GET | `/api/tags/get/{id}` | Lấy tag theo ID | - |
| GET | `/api/tags/get_all` | Lấy tất cả tag | - |

#### 7. **Tag Log API** (`/api/tag_log`)

| Method | Endpoint | Mô tả | Request Body |
|--------|----------|-------|--------------|
| POST | `/api/tag_log/create` | Tạo tag log mới | TaglogDTO |
| PUT | `/api/tag_log/update/{id}` | Cập nhật tag log | TaglogDTO |
| DELETE | `/api/tag_log/delete/{id}` | Xóa tag log | TaglogDTO |
| GET | `/api/tag_log/get/{id}` | Lấy tag log theo ID | - |
| GET | `/api/tag_log/get_all` | Lấy tất cả tag log | - |

### Request/Response Examples

#### Tạo truyện mới
```json
POST /api/stories/create
{
  "title": "Tên truyện",
  "description": "Mô tả truyện",
  "chapters": 10,
  "tags": "fantasy,adventure",
  "coverImage": "url_to_image",
  "type": "novel"
}
```

#### Đăng ký user
```json
POST /api/user/register
{
  "username": "user123",
  "password": "password123",
  "email": "user@example.com"
}
```

#### Đăng nhập
```json
POST /api/user/login
{
  "username": "user123",
  "password": "password123"
}
```

## 🏗️ Cấu trúc dự án

```
src/
├── main/
│   ├── java/com/search/truyen/
│   │   ├── config/          # Cấu hình Spring
│   │   ├── controller/      # REST Controllers
│   │   │   ├── ChapterController.java
│   │   │   ├── CommentController.java
│   │   │   ├── HistoryController.java
│   │   │   ├── StoryController.java
│   │   │   ├── TagController.java
│   │   │   ├── TaglogController.java
│   │   │   └── UserController.java
│   │   ├── dtos/            # Data Transfer Objects
│   │   ├── enums/           # Enumerations
│   │   ├── model/           # Entity Models
│   │   │   └── entities/
│   │   │       ├── Chapter.java
│   │   │       ├── Comment.java
│   │   │       ├── History.java
│   │   │       ├── Page.java
│   │   │       ├── Recommend_log.java
│   │   │       ├── Story.java
│   │   │       ├── Story_tag.java
│   │   │       ├── Tag.java
│   │   │       ├── Tag_log.java
│   │   │       └── User.java
│   │   ├── repository/      # JPA Repositories
│   │   ├── service/         # Business Logic
│   │   └── TruyenApplication.java
│   └── resources/
│       └── application.properties
└── test/                    # Unit & Integration Tests
```

## 🔑 Các tính năng chính

### 1. **Quản lý Truyện (Story)**
- Tạo, đọc, cập nhật, xóa truyện
- Tìm kiếm và lọc truyện

### 2. **Quản lý Chương (Chapter)**
- Quản lý các chương của truyện
- Phân trang nội dung

### 3. **Quản lý Bình luận (Comment)**
- Thêm, sửa, xóa bình luận
- Bình luận theo truyện/chương

### 4. **Lịch sử đọc (History)**
- Theo dõi lịch sử đọc của người dùng
- Lưu vị trí đọc

### 5. **Quản lý Tag**
- Phân loại truyện theo tag
- Tìm kiếm theo tag

### 6. **Quản lý User**
- Đăng ký, đăng nhập
- Xác thực và phân quyền

## 🧪 Testing

Chạy tests:

```bash
./mvnw test
```

Dự án bao gồm:
- Unit tests cho Service layer
- Integration tests cho Controller layer

## 🔒 Bảo mật

- Spring Security được tích hợp để bảo vệ các endpoints
- Xác thực và phân quyền người dùng
- Validation dữ liệu đầu vào

## 📝 Database Schema

Dự án sử dụng các bảng chính:
- `users` - Thông tin người dùng
- `stories` - Thông tin truyện
- `chapters` - Các chương của truyện
- `pages` - Nội dung từng trang
- `comments` - Bình luận
- `history` - Lịch sử đọc
- `tags` - Thẻ phân loại
- `story_tag` - Liên kết truyện và tag
- `tag_log` - Nhật ký tag
- `recommend_log` - Nhật ký đề xuất

## 🤝 Đóng góp

Mọi đóng góp đều được hoan nghênh! Vui lòng:

1. Fork repository
2. Tạo branch mới (`git checkout -b feature/AmazingFeature`)
3. Commit thay đổi (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📄 License

Dự án này được phát hành dưới giấy phép [MIT License](LICENSE).

⭐ Nếu bạn thấy dự án hữu ích, hãy cho một star nhé!
