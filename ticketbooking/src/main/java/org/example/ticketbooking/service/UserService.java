//package com.example.ticketbooking.service;
//
//import com.example.ticketbooking.model.User;
//import com.example.ticketbooking.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    // Tạo một người dùng mới
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    // Tìm người dùng theo email
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    // Tìm người dùng theo ID
//    public Optional<User> findUserById(Long id) {
//        return userRepository.findById(id);
//    }
//}
