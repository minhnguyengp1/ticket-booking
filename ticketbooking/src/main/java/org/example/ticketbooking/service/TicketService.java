package org.example.ticketbooking.service;

import org.example.ticketbooking.dto.EventDTO;
import org.example.ticketbooking.dto.TicketDTO;
import org.example.ticketbooking.model.Event;
import org.example.ticketbooking.model.Ticket;
import org.example.ticketbooking.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.ticketbooking.repository.TicketRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Lấy danh sách vé của người dùng
    public List<TicketDTO> getMany(Long userId) {
        List<Ticket> tickets = ticketRepository.findByUserIdOrderByUpdatedAtDesc(userId);

        // Chuyển từ Ticket Entity sang TicketDTO
        return tickets.stream().map(ticket -> {
            Event event = ticket.getEvent();
            EventDTO eventDTO = new EventDTO(event.getId(), event.getName(), event.getLocation(), event.getDate().toString());
            return new TicketDTO(ticket.getId(), eventDTO, ticket.getEntered(), ticket.getUpdatedAt());
        }).collect(Collectors.toList());
    }

    // Lấy một vé của người dùng
    public Optional<TicketDTO> getOne(Long userId, Long ticketId) {
        // Lấy vé từ repository
        Optional<Ticket> ticketOpt = ticketRepository.findByUserIdAndId(userId, ticketId);

        // Nếu vé tồn tại, chuyển đổi thành TicketDTO
        return ticketOpt.map(ticket -> {
            Event event = ticket.getEvent();
            EventDTO eventDTO = new EventDTO(event.getId(), event.getName(), event.getLocation(), event.getDate().toString());
            return new TicketDTO(ticket.getId(), eventDTO, ticket.getEntered(), ticket.getUpdatedAt());
        });    }

    // Tạo một vé mới cho người dùng
    public Ticket createOne(Long userId, Ticket ticket) {
        if (ticket.getEventId() == null) {
            throw new IllegalArgumentException("Event ID is required");
        }

        // Truy vấn event từ cơ sở dữ liệu theo eventId
        Event event = eventRepository.findById(ticket.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Thiết lập event cho ticket
        ticket.setEvent(event);

        ticket.setUserId(userId);

        System.out.println("ticket: " + ticket);
        return ticketRepository.save(ticket);
    }

    // Cập nhật vé cho người dùng
    public Optional<Ticket> updateOne(Long userId, Long ticketId, Map<String, Object> updateData) {
        Optional<Ticket> ticketOpt = ticketRepository.findByUserIdAndId(userId, ticketId);

        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            // Cập nhật dữ liệu
            updateData.forEach((key, value) -> {
                // Áp dụng các cập nhật cho ticket
                // Có thể sử dụng reflection hoặc setter method tùy vào trường hợp
            });
            return Optional.of(ticketRepository.save(ticket));
        }
        return Optional.empty();
    }
}
