package org.example.ticketbooking.repository;

import org.example.ticketbooking.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Các phương thức tự động có sẵn từ JpaRepository
}

//// src/main/java/com/example/ticketbooking/repositories/EventRepository.java
//package com.example.ticketbooking.repository;
//
//import com.example.ticketbooking.model.Event;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface EventRepository extends JpaRepository<Event, Long> {
//
//    // Lấy nhiều sự kiện, sắp xếp theo updatedAt giảm dần
//    List<Event> findAllByOrderByUpdatedAtDesc();
//
//    // Lấy một sự kiện theo ID
//    Optional<Event> findById(Long id);
//
//    // Cập nhật sự kiện bằng query
//    @Modifying
//    @Query("UPDATE Event e SET e.name = :name, e.location = :location, e.date = :date WHERE e.id = :eventId")
//    void updateEvent(Long eventId, String name, String location, String date);
//}
