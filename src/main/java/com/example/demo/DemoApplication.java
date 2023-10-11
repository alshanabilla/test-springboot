package com.example.demo;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
// import org.springframework.context.event.EventListener;

// import com.example.demo.service.EmailService;

@SpringBootApplication
public class DemoApplication {
	// @Autowired
	// private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// @EventListener(ApplicationReadyEvent.class)
	// public void sendMail() {
	// 	emailService.sendEmail("alshanabilla08@gmail.com", "halo", "test");
	// } 


}
