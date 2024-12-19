package org.example.ticketbooking.dto;

import java.time.LocalDateTime;

public class TicketDTO {

    private Long id;
    private EventDTO event;
    private Boolean entered;
    private LocalDateTime updatedAt;

    // Constructor
    public TicketDTO(Long id, EventDTO event, Boolean entered, LocalDateTime updatedAt) {
        this.id = id;
        this.event = event;
        this.entered = entered;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public Boolean getEntered() {
        return entered;
    }

    public void setEntered(Boolean entered) {
        this.entered = entered;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
