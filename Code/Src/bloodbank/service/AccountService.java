package bloodbank.service;

import bloodbank.model.entity.Account;
import bloodbank.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Annotation quan trọng để Spring Boot nhận diện đây là một Service component
public class AccountService {

    // Tiêm (inject) AccountRepository vào Service để tương tác DB
    @Autowired 
    private AccountRepository accountRepository;

    // 1. Lấy tất cả tài khoản
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    // 2. Lấy tài khoản theo ID
    public Optional<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    // 3. Lưu (Tạo mới hoặc Cập nhật) tài khoản
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    // 4. Xóa tài khoản theo ID
    public void deleteById(String id) {
        accountRepository.deleteById(id);
    }
}