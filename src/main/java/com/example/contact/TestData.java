//package com.example.contact;
//
//import com.example.contact.authentication.user.entity.Authority;
//import com.example.contact.authentication.user.entity.UserInfo;
//import com.example.contact.authentication.user.repo.AuthorityRepo;
//import com.example.contact.authentication.user.repo.UserRepo;
//import com.example.contact.contact.repo.ContactRepo;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TestData {
//    private final UserRepo userRepo;
//    private final AuthorityRepo authorityRepo;
//    private final ContactRepo contactRepo;
//    private final PasswordEncoder encoder;
//
//    public TestData(UserRepo userRepo, AuthorityRepo authorityRepo, ContactRepo contactRepo, PasswordEncoder encoder) {
//        this.userRepo = userRepo;
//        this.authorityRepo = authorityRepo;
//        this.contactRepo = contactRepo;
//        this.encoder = encoder;
//        this.fixUser();
//    }
//
//    public void fixUser(){
//        Authority roleUser = authorityRepo.findByRole("ROLE_USER").orElseThrow();
//        Authority roleAdmin = authorityRepo.findByRole("ROLE_ADMIN").orElseThrow();
//        UserInfo admin = UserInfo.builder()
//                .username("luna010209")
//                .password(encoder.encode("Luna@01"))
//                .name("Luna Do")
//                .avatar("/static/1/avatar.png")
//                .build();
//        admin.getAuthorities().add(roleUser);
//        admin.getAuthorities().add(roleAdmin);
//        userRepo.save(admin);
//
//        UserInfo user2 = UserInfo.builder()
//                .username("user2")
//                .password(encoder.encode("Luna@01"))
//                .name("렁찬")
//                .avatar("/static/2/avatar.png")
//                .build();
//        user2.getAuthorities().add(roleUser);
//        userRepo.save(user2);
//
//        UserInfo user3 = UserInfo.builder()
//                .username("user3")
//                .password(encoder.encode("Luna@01"))
//                .name("시안")
//                .avatar("/static/3/avatar.png")
//                .build();
//
//        user3.getAuthorities().add(roleUser);
//        userRepo.save(user3);
//
//        UserInfo user4 = UserInfo.builder()
//                .username("user4")
//                .password(encoder.encode("Luna@01"))
//                .name("채연")
//                .avatar("/static/4/avatar.png")
//                .build();
//
//        user4.getAuthorities().add(roleUser);
//        userRepo.save(user4);
//
//        UserInfo user5 = UserInfo.builder()
//                .username("user5")
//                .password(encoder.encode("Luna@01"))
//                .name("서우")
//                .avatar("/static/5/avatar.png")
//                .build();
//
//        user5.getAuthorities().add(roleUser);
//        userRepo.save(user5);
//    }
//}
