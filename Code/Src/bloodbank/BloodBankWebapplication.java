package bloodbank; // Đảm bảo package này khớp với package gốc của các file khác

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BloodBankWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloodBankWebApplication.class, args);
    }

}