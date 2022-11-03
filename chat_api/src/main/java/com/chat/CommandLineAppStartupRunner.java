package com.chat;


import com.chat.entities.User;
import com.chat.repositories.UserRepository;
import com.chat.user.UserLoginInformation;
import com.chat.user.UserLoginInformationRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Base64;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    UserLoginInformationRepository userLRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userLRepository.findByUsername("admin") == null) {
            UserLoginInformation admin = new UserLoginInformation();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("12345"));
            admin.setAuthority("ROLE_USER");
            admin.setEnabled(true);
            User admin_ = new User();
            admin_.setNickname("Naruto");
            admin_.setUserLoginInformation(admin);
            byte[] fileContent =
                    FileUtils.readFileToByteArray(new File("src/main/resources/avatars/avatar1.png"));
            String encodedImg = Base64.getEncoder().encodeToString(fileContent);
            admin_.setAvatarURL(encodedImg);
            userRepository.save(admin_);
        }
    }
}
