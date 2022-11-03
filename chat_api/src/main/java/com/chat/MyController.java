package com.chat;


import com.chat.entities.*;
import com.chat.repositories.InviteCodeRepository;
import com.chat.repositories.MessageRepository;
import com.chat.repositories.UserRepository;
import com.chat.user.UserLoginInformation;
import com.chat.user.UserLoginInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.chat.util.AvatarURLGetter;
import com.chat.util.InviteCodeGenerator;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

@CrossOrigin
@Controller
public class MyController {

    Map<String, User> currentUsers = new HashMap<>();

    @Autowired
    UserLoginInformationRepository userLoginInformationRepository;

    @Autowired
    UserRepository userRepository;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private InviteCodeRepository inviteCodeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AvatarURLGetter avatarURLGetter;

    @CrossOrigin
    @MessageMapping("/message")
    @SendTo("/chatroom")
    public SimpleMessage receiveMessage(@Payload SimpleMessage message, Authentication authentication, Principal principal){
        addUserIfNotExists(principal);
        System.out.println(message.toString());
        executorService.submit(() -> {
            Message msg = new Message();
            msg.setUser(currentUsers.get(principal.getName()));
            msg.setBody(message.getBody());
            msg.setCreatedAt(new Date());
            messageRepository.save(msg);
        });
        return message;
    }

    @CrossOrigin
    public void userUpdate(SimpleUser simpleUser){
        simpMessagingTemplate.convertAndSend("/users", simpleUser);
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/getUsers")
    public Iterable<SimpleUser> getCurrentUsers(Principal principal) {
        addUserIfNotExists(principal);
        return currentUsers.values().parallelStream().map(SimpleUser::UserToSimpleUser).toList();
    }

    @GetMapping(path="")
    @ResponseBody
    public SimpleUser index(Principal principal){
        addUserIfNotExists(principal);
        return SimpleUser.UserToSimpleUser(currentUsers.get(principal.getName()));
    }

    private void addUserIfNotExists(Principal principal){
        String username = principal.getName();
        if(!currentUsers.containsKey(username)){
            UserLoginInformation userInfo = userLoginInformationRepository.findByUsername(username);
            if(userInfo == null) throw new RuntimeException("Can't add user to hashmap, userInfo == null");
            currentUsers.put(principal.getName(), userInfo.getUser());
            System.out.println("Add new user with username: " + username);
            userUpdate(SimpleUser.UserToSimpleUser(userInfo.getUser()));
        }
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/lastMsg")
    private Iterable<SimpleMessage> getLastMsg(Principal principal){
        System.out.println(principal.getName());
        addUserIfNotExists(principal);
        return StreamSupport.stream(messageRepository.
                findMessagesByCreatedAtAfter(new Date(LocalDateTime.now()
                        .minusDays(1).toEpochSecond(ZoneOffset.UTC))).spliterator(), true)
                        .map(SimpleMessage::messageToSimpleMessage).toList();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/invite")
    private String generateInviteCode(Principal principal){
        addUserIfNotExists(principal);
        InviteCode existingInviteCode = inviteCodeRepository.findByInvitedBy(currentUsers.get(principal.getName()));
        if (existingInviteCode != null)
            return existingInviteCode.getCode();
        String code = InviteCodeGenerator.generateInviteCode();
        InviteCode inviteCode = new InviteCode();
        inviteCode.setCode(code);
        inviteCode.setInvitedBy(currentUsers.get(principal.getName()));
        inviteCodeRepository.save(inviteCode);
        return code;
    }

    @CrossOrigin
    @PostMapping("/inviteCode")
    private ResponseEntity<String> isInviteCodeValid(@RequestBody String inviteCode){
        System.out.println("here!!!!");
        System.out.println(inviteCode);
        return inviteCodeRepository.findByCode(inviteCode) != null
                ? new ResponseEntity<>("Valid code", HttpStatus.ACCEPTED)
                : new ResponseEntity<>("Invalid code", HttpStatus.CONFLICT);
    }

    @CrossOrigin
    @PostMapping("/regAAA")
    private ResponseEntity<String> registerUser(@RequestBody RegistrationForm registrationForm){
        if(userLoginInformationRepository.findByUsername(registrationForm.getUsername()) != null)
            return new ResponseEntity<>("User with this username already exists", HttpStatus.CONFLICT);
        if(inviteCodeRepository.findByCode(registrationForm.getInviteCode()) == null)
            return new ResponseEntity<>("Invalid invite code", HttpStatus.BAD_REQUEST);
        UserLoginInformation userLoginInformation = new UserLoginInformation();
        userLoginInformation.setUsername(registrationForm.getUsername());
        userLoginInformation.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        userLoginInformation.setEnabled(true);
        userLoginInformation.setAuthority("ROLE_USER");
        userLoginInformation.setRegisteredAt(new Date());
        User user = new User();
        user.setNickname(registrationForm.getNickname());
        try {
            user.setAvatarURL(avatarURLGetter.getRandomAvatarURL());
        }catch(Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>("Can't create avatar for User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userLoginInformation.setUser(user);
        user.setUserLoginInformation(userLoginInformation);
        userRepository.save(user);
        return new ResponseEntity<>("User is created", HttpStatus.CREATED);
    }

}
