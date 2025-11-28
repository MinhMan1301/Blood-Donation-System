# 📖 Giải Thích Chi Tiết Code - Blood Donation System

## 🎯 Mục Đích
Tài liệu này giải thích chi tiết logic, comments và cách hoạt động của từng phần code trong hệ thống.

---

## 1️⃣ Main Application

### `BloodDonationApplication.java`
```java
@SpringBootApplication
public class BloodDonationApplication {
    public static void main(String[] args) {
        SpringApplication.run(BloodDonationApplication.class, args);
        System.out.println("Blood Donation System Started Successfully!");
    }
}
```

**Giải thích:**
- `@SpringBootApplication`: Annotation tổng hợp bao gồm:
  - `@Configuration`: Đánh dấu class là nguồn cấu hình
  - `@EnableAutoConfiguration`: Tự động cấu hình Spring Boot
  - `@ComponentScan`: Tự động scan các component trong package
- `SpringApplication.run()`: Khởi động ứng dụng Spring Boot
- Console message: Thông báo khởi động thành công và URL truy cập

---

## 2️⃣ Configuration Layer

### `SecurityConfig.java` - Cấu Hình Bảo Mật

#### A. Password Encoder
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```
**Giải thích:**
- Sử dụng BCrypt để mã hóa mật khẩu
- BCrypt tự động thêm salt (muối) để tăng bảo mật
- Không thể giải mã ngược (one-way hash)
- Mỗi lần hash cùng 1 password sẽ ra kết quả khác nhau

#### B. Authentication Provider
```java
@Bean
public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(authService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
}
```
**Giải thích:**
- `DaoAuthenticationProvider`: Provider xác thực dựa trên database
- `setUserDetailsService(authService)`: Sử dụng AuthService để load user từ DB
- `setPasswordEncoder()`: Sử dụng BCrypt để so sánh password

#### C. Security Filter Chain
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())  // Tắt CSRF cho đơn giản
        .authorizeHttpRequests(auth -> auth
            // Public URLs - không cần đăng nhập
            .requestMatchers("/css/**", "/js/**", "/images/**", 
                           "/", "/login", "/register").permitAll()
            
            // Data pages - cho phép xem (có thể thay đổi)
            .requestMatchers("/donors", "/doctors", "/bloodbanks", 
                           "/inventory", "/patients", "/events").permitAll()
            
            // Role-based access control
            .requestMatchers("/dashboard/doctor/**").hasAuthority("Doctor")
            .requestMatchers("/dashboard/patient/**").hasAuthority("Patient")
            .requestMatchers("/dashboard/donor/**").hasAuthority("Donor")
            .requestMatchers("/dashboard/bloodbank/**").hasAuthority("BloodBank")
            
            // Tất cả URL khác phải đăng nhập
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")                    // Trang login custom
            .loginProcessingUrl("/login")           // URL xử lý login
            .usernameParameter("email")             // Dùng email thay vì username
            .passwordParameter("password")          // Tên field password
            .successHandler(customAuthenticationSuccessHandler())  // Redirect theo role
            .failureUrl("/login?error")             // URL khi login fail
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")                   // URL logout
            .logoutSuccessUrl("/login?logout")      // Redirect sau logout
            .permitAll()
        );
    return http.build();
}
```

**Giải thích chi tiết:**

1. **CSRF Protection**: Tắt để đơn giản hóa (trong production nên bật)

2. **Public URLs**: 
   - Static resources (CSS, JS, images)
   - Trang chủ, login, register
   - Các trang data (donors, doctors, etc.)

3. **Role-Based Access**:
   - `hasAuthority("Doctor")`: Chỉ Doctor mới vào được
   - Mỗi role có dashboard riêng
   - Spring Security tự động check role từ database

4. **Form Login**:
   - `usernameParameter("email")`: Quan trọng! Dùng email thay vì username
   - `successHandler()`: Custom redirect theo role
   - `failureUrl()`: Hiển thị lỗi khi login fail

#### D. Custom Success Handler
```java
@Bean
public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
    return (request, response, authentication) -> {
        // Lấy role đầu tiên của user
        String role = authentication.getAuthorities().stream()
            .findFirst()
            .map(grantedAuthority -> grantedAuthority.getAuthority())
            .orElse("none");

        // Redirect theo role
        switch (role) {
            case "Doctor":
                response.sendRedirect("/dashboard/doctor");
                break;
            case "Patient":
                response.sendRedirect("/dashboard/patient");
                break;
            case "Donor":
                response.sendRedirect("/dashboard/donor");
                break;
            case "BloodBank":
                response.sendRedirect("/dashboard/bloodbank");
                break;
            default:
                response.sendRedirect("/login?error");
        }
    };
}
```

**Giải thích:**
- Lambda expression: `(request, response, authentication) -> { ... }`
- `authentication.getAuthorities()`: Lấy danh sách quyền của user
- `.stream().findFirst()`: Lấy quyền đầu tiên (mỗi user chỉ có 1 role)
- `switch (role)`: Redirect đến dashboard tương ứng
- Nếu không match role nào → redirect về login với error

---

### `DatabaseConfig.java` - Cấu Hình Database

```java
@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/blood_donation");
        dataSource.setUsername("root");
        dataSource.setPassword("your_password");
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

**Giải thích:**
- `@Bean`: Tạo object được quản lý bởi Spring Container
- `DataSource`: Quản lý connection pool đến database
- `JdbcTemplate`: Wrapper để thực thi SQL queries dễ dàng hơn
- Spring tự động inject `JdbcTemplate` vào các DAO classes

---

## 3️⃣ Service Layer

### `AuthService.java` - Xác Thực

```java
@Service
public class AuthService implements UserDetailsService {
    
    @Autowired
    private AccountDAO accountDAO;
    
    @Override
    public UserDetails loadUserByUsername(String email) 
            throws UsernameNotFoundException {
        // Tìm account theo email
        Account account = accountDAO.findByEmail(email);
        
        if (account == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        
        // Tạo UserDetails object cho Spring Security
        return User.builder()
            .username(account.getEmail())
            .password(account.getPassword())  // BCrypt hash
            .authorities(account.getRole())   // Role: Doctor, Patient, etc.
            .accountLocked(!account.isActive())
            .build();
    }
    
    public Account getAccountByEmail(String email) {
        return accountDAO.findByEmail(email);
    }
}
```

**Giải thích:**
- `implements UserDetailsService`: Interface bắt buộc của Spring Security
- `loadUserByUsername()`: Method Spring Security gọi khi login
  - Tham số là `email` (vì ta config `usernameParameter("email")`)
  - Trả về `UserDetails` object chứa thông tin user
- `User.builder()`: Builder pattern để tạo UserDetails
  - `username`: Email của user
  - `password`: BCrypt hash từ database
  - `authorities`: Role của user (Doctor, Patient, Donor, BloodBank)
  - `accountLocked`: Khóa account nếu `is_active = false`

---

### `DataService.java` - Business Logic

```java
@Service
public class DataService {
    
    @Autowired
    private DonorDAO donorDAO;
    
    @Autowired
    private DoctorDAO doctorDAO;
    
    // ... other DAOs
    
    // ========== DONOR SERVICES ==========
    
    /**
     * Lấy tất cả người hiến máu
     * @return Danh sách tất cả donors
     */
    public List<Donor> getAllDonors() {
        return donorDAO.findAll();
    }
    
    /**
     * Tìm donor theo ID
     * @param id Mã donor (DON001, DON002, ...)
     * @return Donor object hoặc null nếu không tìm thấy
     */
    public Donor getDonorById(String id) {
        return donorDAO.findById(id);
    }
    
    /**
     * Tìm kiếm donor theo tên (LIKE search)
     * @param name Tên hoặc một phần tên
     * @return Danh sách donors có tên khớp
     */
    public List<Donor> searchDonorsByName(String name) {
        return donorDAO.searchByName(name);
    }
    
    /**
     * Lọc donor theo giới tính
     * @param gender "Male" hoặc "Female"
     * @return Danh sách donors theo giới tính
     */
    public List<Donor> getDonorsByGender(String gender) {
        return donorDAO.findByGender(gender);
    }
    
    /**
     * Lấy danh sách người hiến máu gần đây
     * Sắp xếp theo last_donation_date giảm dần
     * @param limit Số lượng tối đa
     * @return Danh sách donors hiến gần đây nhất
     */
    public List<Donor> getRecentDonors(int limit) {
        List<Donor> allDonors = donorDAO.findAll();
        return allDonors.stream()
            .filter(d -> d.getLastDonationDate() != null)  // Chỉ lấy người đã hiến
            .sorted((d1, d2) -> d2.getLastDonationDate()
                .compareTo(d1.getLastDonationDate()))      // Sắp xếp giảm dần
            .limit(limit)                                   // Giới hạn số lượng
            .collect(Collectors.toList());                  // Chuyển về List
    }
    
    /**
     * Đếm tổng số donors
     * @return Số lượng donors
     */
    public int getTotalDonors() {
        return donorDAO.countAll();
    }
}
```

**Giải thích:**
- `@Service`: Đánh dấu class là service layer
- `@Autowired`: Spring tự động inject DAO objects
- **JavaDoc comments**: Giải thích mục đích, tham số, giá trị trả về
- **Stream API**: 
  - `.filter()`: Lọc donors có lastDonationDate
  - `.sorted()`: Sắp xếp theo ngày giảm dần
  - `.limit()`: Giới hạn số lượng
  - `.collect()`: Chuyển stream về List

---

## 4️⃣ DAO Layer

### `DonorDAO.java` - Data Access

```java
@Repository
public class DonorDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * RowMapper: Chuyển đổi ResultSet (SQL) thành Donor object (Java)
     */
    private static class DonorRowMapper implements RowMapper<Donor> {
        @Override
        public Donor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Donor donor = new Donor();
            donor.setDonorsId(rs.getString("donors_id"));
            donor.setSsn(rs.getString("ssn"));
            donor.setFullName(rs.getString("full_name"));
            
            // Chuyển SQL Date sang LocalDate
            donor.setDateOfBirth(rs.getDate("DateOfBirth") != null ? 
                rs.getDate("DateOfBirth").toLocalDate() : null);
            
            donor.setAge(rs.getInt("age"));
            donor.setPhone(rs.getString("phone"));
            donor.setEmail(rs.getString("email"));
            donor.setGender(rs.getString("Gender"));
            
            donor.setLastDonationDate(rs.getDate("last_donation_date") != null ? 
                rs.getDate("last_donation_date").toLocalDate() : null);
            
            return donor;
        }
    }
    
    /**
     * Lấy tất cả donors, sắp xếp theo ID
     */
    public List<Donor> findAll() {
        String sql = "SELECT * FROM Donors ORDER BY donors_id";
        return jdbcTemplate.query(sql, new DonorRowMapper());
    }
    
    /**
     * Tìm donor theo ID
     * @param id Mã donor
     * @return Donor hoặc null
     */
    public Donor findById(String id) {
        String sql = "SELECT * FROM Donors WHERE donors_id = ?";
        List<Donor> donors = jdbcTemplate.query(sql, new DonorRowMapper(), id);
        return donors.isEmpty() ? null : donors.get(0);
    }
    
    /**
     * Tìm kiếm donor theo tên (LIKE search)
     * @param name Tên hoặc một phần tên
     * @return Danh sách donors
     */
    public List<Donor> searchByName(String name) {
        String sql = "SELECT * FROM Donors WHERE full_name LIKE ? ORDER BY full_name";
        return jdbcTemplate.query(sql, new DonorRowMapper(), "%" + name + "%");
    }
    
    /**
     * Lọc donor theo giới tính
     */
    public List<Donor> findByGender(String gender) {
        String sql = "SELECT * FROM Donors WHERE Gender = ? ORDER BY donors_id";
        return jdbcTemplate.query(sql, new DonorRowMapper(), gender);
    }
    
    /**
     * Đếm tổng số donors
     */
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Donors";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
```

**Giải thích:**

1. **@Repository**: Đánh dấu class là DAO layer
2. **RowMapper**: Interface để map ResultSet → Java Object
   - `mapRow()`: Được gọi cho mỗi row trong ResultSet
   - `rs.getString("column_name")`: Lấy giá trị từ column
   - Null check: `rs.getDate() != null ? ... : null`

3. **JdbcTemplate Methods**:
   - `query(sql, rowMapper, params)`: Trả về List<T>
   - `queryForObject(sql, Class)`: Trả về 1 object (count, sum, etc.)
   - `?` trong SQL: Placeholder cho parameters (prevent SQL injection)

4. **LIKE Search**:
   - `"%" + name + "%"`: Tìm kiếm có chứa `name` ở bất kỳ đâu
   - Ví dụ: `name = "Nguyen"` → tìm "Nguyen Van A", "Tran Nguyen B", etc.

---

## 5️⃣ Controller Layer

### `DataController.java` - Xử Lý Requests

```java
@Controller
public class DataController {
    
    @Autowired
    private DataService dataService;
    
    /**
     * Hiển thị danh sách donors với tìm kiếm và lọc
     * 
     * @param model Spring Model để truyền dữ liệu sang view
     * @param search Tham số tìm kiếm theo tên (optional)
     * @param gender Tham số lọc theo giới tính (optional)
     * @return Tên template (donors.html)
     */
    @GetMapping("/donors")
    public String donors(Model model, 
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String gender) {
        
        // Nếu có search parameter
        if (search != null && !search.isEmpty()) {
            model.addAttribute("donors", dataService.searchDonorsByName(search));
            model.addAttribute("searchQuery", search);  // Để hiển thị lại trong form
        } 
        // Nếu có gender parameter
        else if (gender != null && !gender.isEmpty()) {
            model.addAttribute("donors", dataService.getDonorsByGender(gender));
            model.addAttribute("genderFilter", gender);  // Để hiển thị lại trong form
        } 
        // Không có filter → hiển thị tất cả
        else {
            model.addAttribute("donors", dataService.getAllDonors());
        }
        
        return "donors";  // Trả về donors.html
    }
    
    /**
     * Trang thống kê tổng quan
     */
    @GetMapping("/statistics")
    public String statistics(Model model) {
        // Thống kê cơ bản
        model.addAttribute("totalDonors", dataService.getTotalDonors());
        model.addAttribute("totalDoctors", dataService.getTotalDoctors());
        model.addAttribute("totalBloodBanks", dataService.getTotalBloodBanks());
        model.addAttribute("totalBloodUnits", dataService.getTotalBloodUnits());

        // Phân bố nhóm máu
        List<Map<String, Object>> bloodTypes = new ArrayList<>();
        for (String type : new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}) {
            Map<String, Object> typeData = new HashMap<>();
            typeData.put("bloodType", type);
            typeData.put("count", dataService.getBloodUnitsByType(type));
            bloodTypes.add(typeData);
        }
        model.addAttribute("bloodTypes", bloodTypes);

        // Người hiến gần đây
        model.addAttribute("recentDonors", dataService.getRecentDonors(5));

        // Trạng thái kho máu
        model.addAttribute("availableUnits", dataService.getBloodUnitsByStatus("available"));
        model.addAttribute("usedUnits", dataService.getBloodUnitsByStatus("used"));
        model.addAttribute("expiredUnits", dataService.getBloodUnitsByStatus("expired"));

        return "statistics";
    }
}
```

**Giải thích:**

1. **@Controller**: Đánh dấu class là MVC controller
2. **@GetMapping**: Map HTTP GET request đến method
3. **Model**: Object để truyền dữ liệu từ Controller → View
   - `model.addAttribute("key", value)`: Thêm data
   - Trong Thymeleaf: `${key}` để truy cập value

4. **@RequestParam**:
   - `required = false`: Parameter không bắt buộc
   - `String search`: Lấy từ URL `?search=value`
   - Null check: `search != null && !search.isEmpty()`

5. **Return String**: Tên template (không cần .html)
   - `return "donors"` → `templates/donors.html`

6. **Logic Flow**:
   ```
   URL: /donors?search=Nguyen
   ↓
   Controller: donors() method
   ↓
   Service: searchDonorsByName("Nguyen")
   ↓
   DAO: query database
   ↓
   Controller: add to model
   ↓
   View: donors.html render với data
   ```

---

## 6️⃣ View Layer (Thymeleaf)

### `donors.html` - Template

```html
<!DOCTYPE html>
<html lang="vi" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Người Hiến Máu</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <!-- Header fragment -->
    <div th:replace="~{fragments/header :: header}"></div>
    
    <div class="container mx-auto mt-8 p-4">
        <h1 class="text-3xl font-bold mb-6">Danh Sách Người Hiến Máu</h1>
        
        <!-- Search Form -->
        <form th:action="@{/donors}" method="get">
            <input type="text" name="search" 
                   placeholder="Tìm kiếm theo tên..." 
                   th:value="${searchQuery}"/>
            <button type="submit">Tìm kiếm</button>
        </form>
        
        <!-- Results Table -->
        <table>
            <thead>
                <tr>
                    <th>Mã số</th>
                    <th>Họ và tên</th>
                    <th>Email</th>
                    <th>Giới tính</th>
                </tr>
            </thead>
            <tbody>
                <!-- Loop qua danh sách donors -->
                <tr th:each="donor : ${donors}">
                    <td th:text="${donor.donorsId}"></td>
                    <td th:text="${donor.fullName}"></td>
                    <td th:text="${donor.email}"></td>
                    <td>
                        <!-- Conditional rendering -->
                        <span th:if="${donor.gender == 'Male'}">Nam</span>
                        <span th:if="${donor.gender == 'Female'}">Nữ</span>
                    </td>
                </tr>
            </tbody>
        </table>
        
        <!-- No results message -->
        <div th:if="${#lists.isEmpty(donors)}">
            <p>Không tìm thấy người hiến máu nào.</p>
        </div>
    </div>
</body>
</html>
```

**Giải thích Thymeleaf Syntax:**

1. **xmlns:th**: Khai báo namespace Thymeleaf
2. **th:replace**: Include fragment từ file khác
   - `~{fragments/header :: header}`: File `fragments/header.html`, fragment tên `header`
3. **th:action**: URL cho form action
   - `@{/donors}`: Tạo URL `/donors`
4. **th:value**: Set giá trị cho input
   - `${searchQuery}`: Lấy từ model
5. **th:each**: Loop qua collection
   - `donor : ${donors}`: Mỗi item là `donor`
6. **th:text**: Set text content
   - `${donor.fullName}`: Gọi `getDonorFullName()`
7. **th:if**: Conditional rendering
   - `${donor.gender == 'Male'}`: Chỉ hiển thị nếu true
8. **#lists.isEmpty()**: Utility method check list rỗng

---

## 7️⃣ Flow Tổng Thể

### Ví Dụ: User Tìm Kiếm Donor

```
1. User nhập URL: http://localhost:8080/donors?search=Nguyen
   ↓
2. Spring DispatcherServlet nhận request
   ↓
3. Tìm Controller có @GetMapping("/donors")
   → DataController.donors()
   ↓
4. Controller gọi Service
   → dataService.searchDonorsByName("Nguyen")
   ↓
5. Service gọi DAO
   → donorDAO.searchByName("Nguyen")
   ↓
6. DAO thực thi SQL
   → SELECT * FROM Donors WHERE full_name LIKE '%Nguyen%'
   ↓
7. RowMapper chuyển ResultSet → List<Donor>
   ↓
8. DAO trả về List<Donor> cho Service
   ↓
9. Service trả về List<Donor> cho Controller
   ↓
10. Controller add vào Model
    → model.addAttribute("donors", list)
    ↓
11. Controller return "donors"
    ↓
12. Thymeleaf render donors.html với data
    ↓
13. HTML response gửi về browser
    ↓
14. User thấy kết quả tìm kiếm
```

---

## 8️⃣ Best Practices Trong Code

### 1. Separation of Concerns
- **Controller**: Chỉ xử lý HTTP requests/responses
- **Service**: Business logic
- **DAO**: Database access
- **Model**: Data structure

### 2. Dependency Injection
```java
@Autowired
private DataService dataService;
```
- Không `new DataService()` → Spring quản lý
- Dễ test, dễ thay đổi implementation

### 3. Null Safety
```java
if (search != null && !search.isEmpty()) {
    // Process search
}
```
- Luôn check null trước khi dùng
- Tránh NullPointerException

### 4. SQL Injection Prevention
```java
String sql = "SELECT * FROM Donors WHERE donors_id = ?";
jdbcTemplate.query(sql, new DonorRowMapper(), id);
```
- Dùng `?` placeholder
- JdbcTemplate tự động escape

### 5. JavaDoc Comments
```java
/**
 * Tìm donor theo ID
 * @param id Mã donor
 * @return Donor object hoặc null
 */
public Donor findById(String id) { ... }
```
- Giải thích mục đích method
- Document parameters và return value

---

## 9️⃣ Common Patterns

### Builder Pattern
```java
User.builder()
    .username(email)
    .password(password)
    .authorities(role)
    .build();
```

### Repository Pattern
```java
@Repository
public class DonorDAO {
    // Encapsulate database access
}
```

### Service Layer Pattern
```java
@Service
public class DataService {
    // Business logic
}
```

### MVC Pattern
```
Model (Entity) ← → Controller ← → View (Template)
                      ↓
                   Service
                      ↓
                     DAO
```

---

## 🔟 Debugging Tips

### 1. Check Logs
```java
System.out.println("Debug: donors size = " + donors.size());
```

### 2. SQL Debugging
```java
String sql = "SELECT * FROM Donors WHERE donors_id = ?";
System.out.println("SQL: " + sql + ", param: " + id);
```

### 3. Model Debugging
```html
<!-- In Thymeleaf template -->
<div th:text="${donors}"></div>
```

### 4. Browser DevTools
- Network tab: Check request/response
- Console: Check JavaScript errors

---

## 📚 Tài Liệu Tham Khảo

- **Spring Boot**: https://spring.io/projects/spring-boot
- **Spring Security**: https://spring.io/projects/spring-security
- **Thymeleaf**: https://www.thymeleaf.org/documentation.html
- **JdbcTemplate**: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
