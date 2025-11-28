# 📊 Cấu Trúc Database - Blood Donation System

## Tổng Quan
Database: **blood_donation**  
Engine: **MySQL/MariaDB**  
Số bảng: **11 bảng**

---

## 📋 Các Bảng Chính

### 1. **Doctor** - Bác Sĩ
Lưu thông tin các bác sĩ trong hệ thống.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| doctor_id | VARCHAR(20) | Mã bác sĩ (Primary Key) |
| ssn | VARCHAR(15) | Số CMND/CCCD (Unique) |
| full_name | VARCHAR(100) | Họ và tên |
| email | VARCHAR(100) | Email (Unique) |
| phone | VARCHAR(20) | Số điện thoại |
| specialization | VARCHAR(100) | Chuyên khoa |

**Ví dụ:** DOC001, DOC002, ...

---

### 2. **Patients** - Bệnh Nhân
Lưu thông tin bệnh nhân cần máu.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| patient_id | VARCHAR(20) | Mã bệnh nhân (Primary Key) |
| ssn | VARCHAR(15) | Số CMND/CCCD (Unique) |
| full_name | VARCHAR(100) | Họ và tên |
| DateOfBirth | DATE | Ngày sinh |
| age | SMALLINT | Tuổi |
| phone | VARCHAR(20) | Số điện thoại |
| email | VARCHAR(100) | Email (Unique) |
| Gender | VARCHAR(10) | Giới tính |
| status | VARCHAR(50) | Trạng thái (mặc định: 'New') |
| donation_date | DATE | Ngày nhận máu |

**Ví dụ:** PAT001, PAT002, ...

---

### 3. **Donors** - Người Hiến Máu
Lưu thông tin người hiến máu.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| donors_id | VARCHAR(20) | Mã người hiến (Primary Key) |
| ssn | VARCHAR(15) | Số CMND/CCCD (Unique) |
| full_name | VARCHAR(100) | Họ và tên |
| DateOfBirth | DATE | Ngày sinh |
| age | SMALLINT | Tuổi |
| phone | VARCHAR(20) | Số điện thoại |
| email | VARCHAR(100) | Email (Unique) |
| Gender | VARCHAR(10) | Giới tính |
| last_donation_date | DATE | Lần hiến máu cuối |

**Ví dụ:** DON001, DON002, ...

---

### 4. **ACCOUNTT** - Tài Khoản
Lưu thông tin đăng nhập và phân quyền.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| id | VARCHAR(20) | Mã tài khoản (Primary Key) |
| email | VARCHAR(255) | Email đăng nhập (Unique) |
| password | VARCHAR(255) | Mật khẩu đã mã hóa (BCrypt) |
| role | ENUM | Vai trò: Patient, Doctor, Donor, BloodBank |
| is_active | BOOLEAN | Trạng thái kích hoạt |
| time_created | TIMESTAMP | Thời gian tạo |
| patient_id | VARCHAR(20) | FK → Patients |
| doctor_id | VARCHAR(20) | FK → Doctor |
| donor_id | VARCHAR(20) | FK → Donors |
| bank_id | VARCHAR(20) | FK → BLOOD_BANK |

**Ví dụ:** ACCPAT001, ACCDOC001, ACCDON001, ACCBANK001

---

### 5. **BLOOD_BANK** - Ngân Hàng Máu
Lưu thông tin các ngân hàng máu.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| bank_id | VARCHAR(20) | Mã ngân hàng (Primary Key) |
| bank_name | VARCHAR(255) | Tên ngân hàng |
| location | VARCHAR(255) | Địa điểm |
| contact_phone | VARCHAR(15) | Số điện thoại |
| contact_email | VARCHAR(100) | Email liên hệ |
| volume | DECIMAL(10,2) | Tổng dung tích (lít) |
| assigned_doctor | VARCHAR(20) | Bác sĩ phụ trách |
| request_id | VARCHAR(20) | FK → Request |

**Ví dụ:** BANK001, BANK002, ...

---

### 6. **Blood_Inventory** - Kho Máu
Lưu thông tin các đơn vị máu trong kho.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| unit_id | VARCHAR(20) | Mã đơn vị máu (Primary Key) |
| blood_type | ENUM | Nhóm máu: A+, A-, B+, B-, AB+, AB-, O+, O- |
| RH | ENUM | Yếu tố RH: +, - |
| volume_Litter | DECIMAL(10,2) | Dung tích (lít) |
| donated_date | DATE | Ngày hiến |
| expired_date | DATE | Ngày hết hạn |
| status | ENUM | Trạng thái: available, used, expired |
| bank_id | VARCHAR(20) | FK → BLOOD_BANK |

**Ví dụ:** UNIT001, UNIT002, ...

---

### 7. **Request** - Yêu Cầu Máu
Lưu các yêu cầu máu từ bệnh nhân/bác sĩ.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| request_id | VARCHAR(20) | Mã yêu cầu (Primary Key) |
| bank_id | INT | ID ngân hàng máu |
| blood_type | ENUM | Nhóm máu cần: A+, A-, B+, B-, AB+, AB-, O+, O- |
| quantity | INT | Số lượng cần |
| status | ENUM | Trạng thái: Pending, Approved, Fulfilled, Rejected |
| date_request | DATE | Ngày yêu cầu |
| date_response | DATETIME | Ngày phản hồi |
| fulfilled_inventory_id | INT | ID đơn vị máu đã cung cấp |
| patient_id | VARCHAR(20) | FK → Patients |
| doctor_id | VARCHAR(20) | FK → Doctor |
| donors_id | VARCHAR(20) | FK → Donors |

**Ví dụ:** REQ001, REQ002, ...

---

### 8. **Donation_Event** - Sự Kiện Hiến Máu
Lưu thông tin các sự kiện hiến máu.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| donation_id | VARCHAR(20) | Mã sự kiện (Primary Key) |
| Date_event | DATE | Ngày tổ chức |
| location | VARCHAR(255) | Địa điểm |
| volume_collected | DECIMAL(10,2) | Lượng máu thu được (lít) |
| bank_id | VARCHAR(20) | FK → BLOOD_BANK |

**Ví dụ:** EVENT001, EVENT002, ...

---

### 9. **Donors_DonationEvent** - Quan Hệ Người Hiến & Sự Kiện
Bảng trung gian liên kết người hiến máu với sự kiện (Many-to-Many).

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| donors_id | VARCHAR(20) | FK → Donors (Primary Key) |
| donation_id | VARCHAR(20) | FK → Donation_Event (Primary Key) |

---

### 10. **Doctor_BloodBank** - Quan Hệ Bác Sĩ & Ngân Hàng Máu
Bảng trung gian liên kết bác sĩ với ngân hàng máu (Many-to-Many).

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| doctor_id | VARCHAR(20) | FK → Doctor (Primary Key) |
| bank_id | VARCHAR(20) | FK → BLOOD_BANK (Primary Key) |

---

## 🔗 Mối Quan Hệ (Relationships)

### One-to-Many
- **BLOOD_BANK** → **Blood_Inventory** (1 ngân hàng có nhiều đơn vị máu)
- **BLOOD_BANK** → **Donation_Event** (1 ngân hàng tổ chức nhiều sự kiện)
- **Patients** → **Request** (1 bệnh nhân có nhiều yêu cầu)
- **Doctor** → **Request** (1 bác sĩ xử lý nhiều yêu cầu)

### Many-to-Many
- **Donors** ↔ **Donation_Event** (qua bảng Donors_DonationEvent)
- **Doctor** ↔ **BLOOD_BANK** (qua bảng Doctor_BloodBank)

### One-to-One
- **ACCOUNTT** → **Patients** (1 tài khoản cho 1 bệnh nhân)
- **ACCOUNTT** → **Doctor** (1 tài khoản cho 1 bác sĩ)
- **ACCOUNTT** → **Donors** (1 tài khoản cho 1 người hiến)
- **ACCOUNTT** → **BLOOD_BANK** (1 tài khoản cho 1 ngân hàng)

---

## 🎯 Các Nhóm Máu Hỗ Trợ

```
A+, A-, B+, B-, AB+, AB-, O+, O-
```

---

## 🔐 Bảo Mật

- **Mật khẩu**: Mã hóa bằng BCrypt (Spring Security)
- **Email**: Unique constraint để tránh trùng lặp
- **SSN**: Unique constraint cho mỗi người

---

## 📝 Lưu Ý Kỹ Thuật

1. **Foreign Key Constraints**: Đảm bảo tính toàn vẹn dữ liệu
2. **ENUM Types**: Giới hạn giá trị hợp lệ cho các trường
3. **Default Values**: 
   - status = 'New' (Patients)
   - status = 'available' (Blood_Inventory)
   - status = 'Pending' (Request)
   - is_active = TRUE (ACCOUNTT)
4. **Timestamps**: Tự động ghi thời gian tạo tài khoản

---

## 🚀 Khởi Tạo Database

### Các File SQL

#### 1. **blood_donation1.sql** - Tạo Database & Schema
```sql
CREATE DATABASE IF NOT EXISTS blood_donation;
```
- Tạo database `blood_donation`
- Định nghĩa cấu trúc bảng với constraints
- Thêm CHECK constraints cho validation:
  - `check_patient_age`: age > 0
  - `check_donor_age`: age >= 18 AND age <= 100
  - `check_patient_gender`: Gender IN ('Male', 'Female', 'Other')
  - `check_donor_gender`: Gender IN ('Male', 'Female', 'Other')
  - `chk_dates`: expired_date > donated_date

#### 2. **create_tables.sql** - Tạo Bảng (Production)
```sql
DROP TABLE IF EXISTS ... (xóa bảng cũ)
CREATE TABLE ... (tạo bảng mới)
```
- Drop tất cả bảng cũ (với FOREIGN_KEY_CHECKS=0)
- Tạo lại bảng với cấu trúc mới
- Phù hợp cho môi trường production

#### 3. **data.sql** - Dữ Liệu Mẫu
```sql
TRUNCATE TABLE ... (xóa dữ liệu cũ)
INSERT INTO ... (thêm dữ liệu mới)
```
**Dữ liệu mẫu bao gồm:**
- **10 Doctors** (DOC001-DOC010)
  - Chuyên khoa: Hematology, Transfusion Medicine, Internal Medicine, Pathology, Emergency Medicine, Pediatrics, Surgery, Anesthesiology, Cardiology, Obstetrics & Gynecology
  
- **50 Patients** (PAT001-PAT050)
  - Độ tuổi: 19-60
  - Giới tính: Male/Female
  - Status: 'New'
  
- **50 Donors** (DON001-DON050)
  - Độ tuổi: 25-48 (phải >= 18)
  - Giới tính: Male/Female
  - Có last_donation_date từ 2024-2025
  
- **50 Patient Accounts** (ACCPAT001-ACCPAT050)
  - Role: Patient
  - Password: `password123` (BCrypt hash)
  
- **10 Doctor Accounts** (ACCDOC001-ACCDOC010)
  - Role: Doctor
  - Password: `password123` (BCrypt hash)
  
- **5 Blood Banks** (BANK001-BANK005)
  - Hanoi, Ho Chi Minh City, Danang, Hue, Can Tho
  - Volume: 66.5-120.5 lít
  
- **6 Blood Inventory Units** (UNIT001-UNIT006)
  - Các nhóm máu: A+, O+, B-, AB+, A-, O-
  - Status: available, used, expired
  
- **5 Donation Events** (DONATE001-DONATE005)
  - Ngày: 2025-09-15 đến 2025-09-27
  - Volume collected: 8.6-12.0 lít
  
- **6 Donor-Event Links** (Donors_DonationEvent)
  
- **5 Doctor-BloodBank Links** (Doctor_BloodBank)
  
- **5 Requests** (REQ001-REQ005)
  - Status: Approved, Pending, Fulfilled, Rejected

#### 4. **add_donor_bloodbank_accounts.sql** - Thêm Role Mới
```sql
ALTER TABLE ACCOUNTT MODIFY COLUMN role ENUM(...)
ALTER TABLE ACCOUNTT ADD COLUMN donor_id, bank_id
```
**Chức năng:**
- Cập nhật ENUM role thêm 'Donor' và 'BloodBank'
- Thêm cột `donor_id` và `bank_id` vào bảng ACCOUNTT
- Thêm foreign key constraints
- Tạo 5 Donor accounts (ACCDON001-ACCDON005)
- Tạo 5 Blood Bank accounts (ACCBANK001-ACCBANK005)

**Donor Accounts:**
```
ACCDON001: nguyenthianh.don1@example.com → DON001
ACCDON002: tranvanbinh.don2@example.com → DON002
ACCDON003: lethicam.don3@example.com → DON003
ACCDON004: phamvandung.don4@example.com → DON004
ACCDON005: hoangthihanh.don5@example.com → DON005
```

**Blood Bank Accounts:**
```
ACCBANK001: central@bloodbank.vn → BANK001 (Hanoi)
ACCBANK002: saigon@bloodbank.vn → BANK002 (HCMC)
ACCBANK003: danang@bloodbank.vn → BANK003 (Danang)
ACCBANK004: hue@bloodbank.vn → BANK004 (Hue)
ACCBANK005: cantho@bloodbank.vn → BANK005 (Can Tho)
```

---

### Thứ Tự Khởi Tạo

```bash
# Bước 1: Tạo database và schema
mysql -u root -p < blood_donation1.sql

# Bước 2: Tạo các bảng (nếu cần reset)
mysql -u root -p blood_donation < create_tables.sql

# Bước 3: Thêm dữ liệu mẫu
mysql -u root -p blood_donation < data.sql

# Bước 4: Thêm tài khoản donor và blood bank
mysql -u root -p blood_donation < add_donor_bloodbank_accounts.sql
```

**Lưu ý:**
- File `blood_donation1.sql` và `create_tables.sql` tương tự nhau, chọn 1 trong 2
- File `data.sql` sẽ TRUNCATE (xóa) dữ liệu cũ trước khi insert
- File `add_donor_bloodbank_accounts.sql` phải chạy sau khi có dữ liệu

---

## 📊 Thống Kê Dữ Liệu

### Tổng Quan
- **Tổng số bảng**: 11
- **Bảng chính**: 8
- **Bảng quan hệ**: 2
- **Bảng tài khoản**: 1
- **Số ENUM types**: 4 (role, blood_type, RH, status)
- **Số Foreign Keys**: 15+

### Dữ Liệu Mẫu
- **Doctors**: 10 bác sĩ
- **Patients**: 50 bệnh nhân
- **Donors**: 50 người hiến máu
- **Blood Banks**: 5 ngân hàng máu
- **Blood Inventory**: 6 đơn vị máu
- **Donation Events**: 5 sự kiện
- **Requests**: 5 yêu cầu
- **Accounts**: 70 tài khoản (50 patients + 10 doctors + 5 donors + 5 blood banks)

### Mật Khẩu Demo
Tất cả tài khoản đều dùng password: **`password123`**  
BCrypt hash: `$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa`

---

## 🔍 Validation Rules

### Donors
- Tuổi: 18-100
- Giới tính: Male, Female, Other
- Email: Unique

### Patients
- Tuổi: > 0
- Giới tính: Male, Female, Other
- Email: Unique

### Blood Inventory
- expired_date phải > donated_date
- Blood type: A+, A-, B+, B-, AB+, AB-, O+, O-
- Status: available, used, expired

### Request
- Quantity: > 0
- Status: Pending, Approved, Fulfilled, Rejected

---

## 🗺️ Entity Relationship Diagram (ERD)

```
ACCOUNTT (1) ─── (1) Doctor
ACCOUNTT (1) ─── (1) Patient
ACCOUNTT (1) ─── (1) Donor
ACCOUNTT (1) ─── (1) BLOOD_BANK

BLOOD_BANK (1) ─── (N) Blood_Inventory
BLOOD_BANK (1) ─── (N) Donation_Event
BLOOD_BANK (M) ─── (N) Doctor (qua Doctor_BloodBank)

Donor (M) ─── (N) Donation_Event (qua Donors_DonationEvent)

Patient (1) ─── (N) Request
Doctor (1) ─── (N) Request
Donor (1) ─── (N) Request
```
