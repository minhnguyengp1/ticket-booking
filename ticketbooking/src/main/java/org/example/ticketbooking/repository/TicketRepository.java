package org.example.ticketbooking.repository;

import org.example.ticketbooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Lấy nhiều vé của một người dùng
    List<Ticket> findByUserIdOrderByUpdatedAtDesc(Long userId);

    // Lấy một vé của người dùng theo id
    Optional<Ticket> findByUserIdAndId(Long userId, Long ticketId);
}