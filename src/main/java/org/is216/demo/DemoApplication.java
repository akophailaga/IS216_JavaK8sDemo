package org.is216.demo;

import org.is216.demo.model.Order;
import org.is216.demo.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(OrderRepository repository) {
        return args -> {
            if (!repository.existsById("1")) {
                repository.save(new Order("1", "Thắng", "Phở Bò", "ĐANG GIAO"));
            }
            if (!repository.existsById("2")) {
                repository.save(new Order("2", "Win Pear", "Cà Phê Muối", "HOÀN THÀNH"));
            }
            
            System.out.println(">>> Kiểm tra và đồng bộ dữ liệu mồi thành công!");
        };
    }
}