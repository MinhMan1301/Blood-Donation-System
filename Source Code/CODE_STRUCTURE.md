# 💻 Cấu Trúc Code - Blood Donation System

## 🎯 Tổng Quan

**Framework**: Spring Boot 3.x  
**Language**: Java 17+  
**Template Engine**: Thymeleaf  
**Database**: MySQL/MariaDB  
**Security**: Spring Security  
**Build Tool**: Maven  

---

## 📁 Cấu Trúc Thư Mục

```
blood-donation/
├── src/main/
│   ├── java/com/blooddonation/
│   │   ├── config/              # Cấu hình
│   │   ├── controller/          # Controllers (MVC)
│   │   ├── dao/                 # Data Access Objects
│   │   ├── model/               # Entity Models
│   │   ├── service/             # Business Logic
│   │   └── BloodDonationApplication.java
│   └── resources/
│       ├── templates/           # HTML Templates (Thymeleaf)
│       ├── static/              # CSS, JS, Images
│       └── application.properties
├── pom.xml                      # Maven dependencies
└── run.sh                       # Script khởi động
```

---

## 🔧 Layer Architecture

### 1. **Config Layer** (`config/`)
Cấu hình hệ thống.

#### `DatabaseConfig.java`
- Cấu hình kết nối MySQL
- DataSource configuration
- JdbcTemplate bean

#### `SecurityConfig.java`
- Spring Security configuration
- Authentication & Authorization
- Role-based access control
- Password encoding (BCrypt)
- Login/Logout handling

**Phân quyền:**
```java
/dashboard/doctor/**    → DOCTOR role
/dashboard/patient/**   → PATIENT role
/dashboard/donor/**     → DONOR role
/dashboard/bloodbank/** → BLOODBANK role
/donors, /doctors, etc. → Authenticated users
```

---

### 2. **Model Layer** (`model/`)
Các entity đại diện cho bảng database.

#### `Account.java`
```java
- id: String
- email: String
- password: String (BCrypt)
- role: String (Patient/Doctor/Donor/BloodBank)
- isActive: Boolean
- patientId, doctorId, donorId, bankId: String
```

#### `Doctor.java`
```java
- doctorId: String
- ssn: String
- fullName: String
- email: String
- phone: String
- specialization: String
```

#### `Patient.java`
```java
- patientId: String
- ssn: String
- fullName: String
- dateOfBirth: LocalDate
- age: Integer
- phone: String
- email: String
- gender: String
- status: String
- donationDate: LocalDate
```

#### `Donor.java`
```java
- donorsId: String
- ssn: String
- fullName: String
- dateOfBirth: LocalDate
- age: Integer
- phone: String
- email: String
- gender: String
- lastDonationDate: LocalDate
```

#### `BloodBank.java`
```java
- bankId: String
- bankName: String
- location: String
- contactPhone: String
- contactEmail: String
- volume: BigDecimal
- assignedDoctor: String
- requestId: String
```

#### `BloodInventory.java`
```java
- unitId: String
- bloodType: String (A+, A-, B+, B-, AB+, AB-, O+, O-)
- rh: String (+/-)
- volumeLitter: BigDecimal
- donatedDate: LocalDate
- expiredDate: LocalDate
- status: String (available/used/expired)
- bankId: String
```

#### `Request.java`
```java
- requestId: String
- bankId: Integer
- bloodType: String
- quantity: Integer
- status: String (Pending/Approved/Fulfilled/Rejected)
- dateRequest: LocalDate
- dateResponse: LocalDateTime
- patientId, doctorId, donorsId: String
```

#### `DonationEvent.java`
```java
- donationId: String
- dateEvent: LocalDate
- location: String
- volumeCollected: BigDecimal
- bankId: String
```

---

### 3. **DAO Layer** (`dao/`)
Truy xuất dữ liệu từ database (sử dụng JdbcTemplate).

#### `AccountDAO.java`
```java
+ findByEmail(email): Account
+ findAll(): List<Account>
+ countAll(): int
```

#### `DoctorDAO.java`
```java
+ findAll(): List<Doctor>
+ findById(id): Doctor
+ searchBySpecialty(specialty): List<Doctor>
+ searchByName(name): List<Doctor>
+ countAll(): int
```

#### `PatientDAO.java`
```java
+ findAll(): List<Patient>
+ findById(id): Patient
+ searchByName(name): List<Patient>
+ countAll(): int
```

#### `DonorDAO.java`
```java
+ findAll(): List<Donor>
+ findById(id): Donor
+ searchByName(name): List<Donor>
+ findByGender(gender): List<Donor>
+ countAll(): int
```

#### `BloodBankDAO.java`
```java
+ findAll(): List<BloodBank>
+ findById(id): BloodBank
+ searchByName(name): List<BloodBank>
+ findByLocation(location): List<BloodBank>
+ countAll(): int
```

#### `BloodInventoryDAO.java`
```java
+ findAll(): List<BloodInventory>
+ findById(id): BloodInventory
+ findByBloodType(type): List<BloodInventory>
+ findByStatus(status): List<BloodInventory>
+ countAll(): int
+ countByBloodType(type): int
```

#### `RequestDAO.java`
```java
+ findAll(): List<Request>
+ findById(id): Request
+ findRecent(limit): List<Request>
+ findByPatientId(patientId): List<Request>
+ findByStatus(status): List<Request>
+ countAll(): int
```

#### `DonationEventDAO.java`
```java
+ findAll(): List<DonationEvent>
+ findById(id): DonationEvent
+ findByDonorId(donorId): List<DonationEvent>
+ countAll(): int
```

---

### 4. **Service Layer** (`service/`)
Business logic và xử lý dữ liệu.

#### `AuthService.java`
```java
+ loadUserByUsername(email): UserDetails
+ getAccountByEmail(email): Account
```
- Xác thực người dùng
- Load thông tin tài khoản
- Tích hợp với Spring Security

#### `DataService.java`
```java
// Patient Services
+ getAllPatients(): List<Patient>
+ getPatientById(id): Patient
+ searchPatientsByName(name): List<Patient>
+ getTotalPatients(): int

// Doctor Services
+ getAllDoctors(): List<Doctor>
+ getDoctorById(id): Doctor
+ searchDoctorsByName(name): List<Doctor>
+ searchDoctorsBySpecialty(specialty): List<Doctor>
+ getTotalDoctors(): int

// Donor Services
+ getAllDonors(): List<Donor>
+ getDonorById(id): Donor
+ searchDonorsByName(name): List<Donor>
+ getDonorsByGender(gender): List<Donor>
+ getRecentDonors(limit): List<Donor>
+ getTotalDonors(): int

// Blood Bank Services
+ getAllBloodBanks(): List<BloodBank>
+ getBloodBankById(id): BloodBank
+ searchBloodBanksByName(name): List<BloodBank>
+ getBloodBanksByLocation(location): List<BloodBank>
+ getTotalBloodBanks(): int

// Blood Inventory Services
+ getAllBloodInventory(): List<BloodInventory>
+ getBloodInventoryById(id): BloodInventory
+ getBloodInventoryByType(type): List<BloodInventory>
+ getBloodInventoryByStatus(status): List<BloodInventory>
+ getTotalBloodUnits(): int
+ getBloodUnitsByType(type): int
+ getBloodUnitsByStatus(status): int

// Request Services
+ getAllRequests(): List<Request>
+ getRequestById(id): Request
+ getRecentRequests(limit): List<Request>
+ getRequestsByPatientId(patientId): List<Request>
+ getRequestsByStatus(status): List<Request>
+ getTotalRequests(): int

// Donation Event Services
+ getAllDonationEvents(): List<DonationEvent>
+ getDonationEventById(id): DonationEvent
+ getDonationEventsByDonorId(donorId): List<DonationEvent>
+ getTotalDonationEvents(): int

// Account Services
+ getAllAccounts(): List<Account>
+ getTotalAccounts(): int
```

---

### 5. **Controller Layer** (`controller/`)
Xử lý HTTP requests và responses.

#### `HomeController.java`
```java
GET / → index.html (Trang chủ)
```

#### `AuthController.java`
```java
GET  /login  → login.html
POST /login  → Xác thực và redirect theo role
GET  /logout → Đăng xuất
GET  /register → register.html
POST /register → Đăng ký tài khoản mới
```

#### `DashboardController.java`
```java
GET /dashboard          → dashboard.html (Admin)
GET /dashboard/doctor   → dashboard-doctor.html
GET /dashboard/patient  → dashboard-patient.html
GET /dashboard/donor    → dashboard-donor.html
GET /dashboard/bloodbank → dashboard-bloodbank.html
```

**Mỗi dashboard hiển thị:**
- Thống kê tổng quan
- Dữ liệu liên quan đến role
- Quick actions

#### `DataController.java`
```java
// Patients
GET /patients → patients.html
GET /patients?search={name} → Tìm theo tên
GET /patients/{id} → patient-detail.html

// Doctors
GET /doctors → doctors.html
GET /doctors?search={keyword}&searchType={name|specialty}
GET /doctors/{id} → doctor-detail.html

// Donors
GET /donors → donors.html
GET /donors?search={name} → Tìm theo tên
GET /donors?gender={Male|Female} → Lọc theo giới tính

// Blood Banks
GET /bloodbanks → bloodbanks.html
GET /bloodbanks?search={name} → Tìm theo tên
GET /bloodbanks?location={location} → Lọc theo địa điểm

// Blood Inventory
GET /inventory → inventory.html
GET /inventory?bloodType={type} → Lọc theo nhóm máu
GET /inventory?status={available|used|expired} → Lọc theo trạng thái

// Requests
GET /requests → requests.html
GET /requests?status={status} → Lọc theo trạng thái

// Donation Events
GET /events → events.html

// Accounts
GET /accounts → accounts.html

// Statistics
GET /statistics → statistics.html
```

#### `LegacyRedirectController.java`
```java
// Redirect các URL cũ sang URL mới
GET /loginpage/donors.html → /donors
GET /loginpage/doctor.html → /doctors
...
```

---

### 6. **View Layer** (`templates/`)
HTML templates sử dụng Thymeleaf.

#### Trang Chính
- `index.html` - Trang chủ
- `login.html` - Đăng nhập
- `register.html` - Đăng ký

#### Dashboards
- `dashboard.html` - Admin dashboard
- `dashboard-doctor.html` - Dashboard bác sĩ
- `dashboard-patient.html` - Dashboard bệnh nhân
- `dashboard-donor.html` - Dashboard người hiến
- `dashboard-bloodbank.html` - Dashboard ngân hàng máu

#### Data Pages
- `donors.html` - Danh sách người hiến máu
- `doctors.html` - Danh sách bác sĩ
- `patients.html` - Danh sách bệnh nhân
- `bloodbanks.html` - Danh sách ngân hàng máu
- `inventory.html` - Kho máu
- `requests.html` - Yêu cầu máu
- `events.html` - Sự kiện hiến máu
- `accounts.html` - Tài khoản
- `statistics.html` - Thống kê tổng quan

#### Detail Pages
- `doctor-detail.html` - Chi tiết bác sĩ
- `patient-detail.html` - Chi tiết bệnh nhân

#### Fragments
- `fragments/header.html` - Header chung

---

## 🎨 Frontend Features

### Tailwind CSS
- Responsive design
- Modern UI components
- Color-coded badges
- Card layouts

### Tính Năng Tìm Kiếm
- **Donors**: Tìm theo tên, lọc theo giới tính
- **Doctors**: Tìm theo tên hoặc chuyên khoa
- **Blood Banks**: Tìm theo tên, lọc theo địa điểm
- **Inventory**: Lọc theo nhóm máu và trạng thái

### Thống Kê
- Tổng số entities
- Phân bố nhóm máu (8 loại)
- Người hiến máu gần đây
- Trạng thái kho máu

---

## 🔐 Security Features

### Authentication
- Email + Password login
- BCrypt password hashing
- Session management

### Authorization
- Role-based access control (RBAC)
- 4 roles: Patient, Doctor, Donor, BloodBank
- URL-based protection
- Method-level security

### Password Policy
- Minimum length: 8 characters
- Encrypted with BCrypt
- Demo password: `password123`

---

## 🚀 Khởi Động Ứng Dụng

```bash
# Sử dụng script
./run.sh

# Hoặc Maven
mvn spring-boot:run

# Truy cập
http://localhost:8080
```

---

## 📊 API Endpoints Summary

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/` | Trang chủ |
| GET | `/login` | Trang đăng nhập |
| POST | `/login` | Xử lý đăng nhập |
| GET | `/register` | Trang đăng ký |
| GET | `/dashboard` | Dashboard admin |
| GET | `/dashboard/{role}` | Dashboard theo role |
| GET | `/donors` | Danh sách người hiến |
| GET | `/doctors` | Danh sách bác sĩ |
| GET | `/patients` | Danh sách bệnh nhân |
| GET | `/bloodbanks` | Danh sách ngân hàng máu |
| GET | `/inventory` | Kho máu |
| GET | `/requests` | Yêu cầu máu |
| GET | `/events` | Sự kiện hiến máu |
| GET | `/statistics` | Thống kê tổng quan |

---

## 🛠️ Dependencies (pom.xml)

```xml
- spring-boot-starter-web
- spring-boot-starter-thymeleaf
- spring-boot-starter-security
- spring-boot-starter-jdbc
- mysql-connector-java
- spring-boot-devtools
```

---

## 📝 Configuration (application.properties)

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/blood_donation
spring.datasource.username=root
spring.datasource.password=your_password

# Thymeleaf
spring.thymeleaf.cache=false
```

---

## 🎯 Design Patterns

1. **MVC Pattern**: Model-View-Controller
2. **DAO Pattern**: Data Access Objects
3. **Service Layer Pattern**: Business logic separation
4. **Dependency Injection**: Spring IoC container
5. **Template Method**: Thymeleaf templates

---

## 📈 Scalability

- **Stateless**: Dễ scale horizontal
- **Database Connection Pool**: Tối ưu performance
- **Caching**: Có thể thêm Redis/Memcached
- **Load Balancing**: Hỗ trợ multiple instances

---

## 🔍 Testing

```bash
# Compile
mvn clean compile

# Test
mvn test

# Package
mvn package
```

---

## 📚 Tài Liệu Tham Khảo

- Spring Boot: https://spring.io/projects/spring-boot
- Spring Security: https://spring.io/projects/spring-security
- Thymeleaf: https://www.thymeleaf.org
- Tailwind CSS: https://tailwindcss.com
