package bloodbank.controller;

import bloodbank.model.entity.Account;
import bloodbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Đánh dấu đây là REST Controller
@RequestMapping("/api/accounts") // Đường dẫn gốc cho các API của Account
public class AccountController {

    // Tiêm (inject) AccountService vào Controller
    @Autowired
    private AccountService accountService;

    // API: GET /api/accounts (Lấy tất cả tài khoản)
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    // API: GET /api/accounts/{id} (Lấy tài khoản theo ID)
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable String id) {
        // Trả về Account nếu tìm thấy, hoặc ném lỗi 404 nếu không tìm thấy
        return accountService.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    // API: POST /api/accounts (Tạo mới tài khoản)
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.save(account);
    }
}