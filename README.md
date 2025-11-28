# 🩸 Blood Donation & Hospital Resource Management System  
A full-stack platform that optimizes the **blood donation lifecycle** and **hospital resource management**, allowing donors, patients, doctors, and blood banks to interact through a secure, real-time digital system.

---

## 🌐 Overview  
The system is structured around **four main user roles**, each with access to specific data entities and workflows:

### 👤 Patient  
- Views **only their own blood requests**.  
- Creates and tracks request statuses.  

### 🧑‍⚕️ Doctor  
- Views **all patients' blood requests**.  
- Reviews, verifies, and updates request statuses.  
- Supports hospital-wide workflow decisions.  

### 🏥 Blood Bank  
- Manages the **entire blood inventory** (type, RH, quantity, expiration).  
- Views **all patient requests** across the system.  
- Creates and updates **donation events**.  
- Acts as the main logistics and storage unit.  

### 🩸 Donor  
- Receives notifications for **all donation events**.  
- Views details of **all blood banks**.  
- Accesses donation schedules and updates.  

---

## 🚀 Key Features  

### 🔒 Core Functionalities  
- **Donor & Patient Management** — profiles, health data, and activity history.  
- **Blood Inventory Control** — real-time stock updates across blood banks.  
- **Request Management** — full lifecycle tracking across roles.  
- **Donation Event Coordination** — scheduling and event management.  
- **Role-Based Access Control** — secure and isolated permissions.

### 🌟 Highlighted Features  
- **Geo-Analysis Dashboard** — maps donor density by blood type.  
- **Blood Traceability** — donors can check the status of their donated blood unit (anonymized).  

---

## 🏗️ System Architecture  

### 💻 Technology Stack  
| Category | Tools | Description |
|---------|-------|-------------|
| **Backend** | Java Spring Boot | API development & business logic |
| **Frontend** | HTML, CSS | User interface for all role types |
| **Database** | PostgreSQL, MySQL | Medical and inventory data storage |
| **Version Control** | GitHub | Collaboration and issue tracking |
| **Design** | Figma, draw.io, ERDPlus | UI/UX and ERD modeling |

---

## 📊 Database Schema  

### 🔑 Key Entities  
- `account`, `patients`, `donors`, `doctor`  
- `request`, `donation_event`  
- `blood_bank`, `blood_inventory`  
- Many-to-Many tables:  
  - `doctor_bloodbank`  
  - `donors_donationevent`  

### 🧵 Relationships  
- Each **Account** has a 1:1 relationship with Patient, Donor, or Doctor.  
- A **Doctor** may manage or work at multiple Blood Banks (1:N).  
- A **Request** belongs to a Patient and is fulfilled through Blood Inventory.  
- A **Donation Event** is created by a Blood Bank and involves many Donors.  

---

## 🎥 Video Demo  

[![Video Demo]([https://img.youtube.com/vi/YOUR_VIDEO_ID/0.jpg](https://github.com/user-attachments/assets/835dddc2-0b46-4112-a06a-cc223066e364))](Blood System Management Website)  


https://github.com/user-attachments/assets/835dddc2-0b46-4112-a06a-cc223066e364



---

## 📬 Contact

**Leader:** Minh Mẫn  
📧 Email: [phamminhman1312005@gmail.com](mailto:phamminhman1312005@gmail.com)  
🔗 GitHub: [MinhMan1301](https://github.com/MinhMan1301)  
🔗 LinkedIn: [Minh Mẫn](https://www.linkedin.com/in/minh-m%E1%BA%ABn-47b493311/)
