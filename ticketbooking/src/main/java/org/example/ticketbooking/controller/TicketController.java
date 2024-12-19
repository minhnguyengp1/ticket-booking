package org.example.ticketbooking.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import org.example.ticketbooking.dto.TicketDTO;
import org.example.ticketbooking.model.Ticket;
import org.example.ticketbooking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:8081")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Lấy danh sách vé của người dùng
    @GetMapping
    public ResponseEntity<Map<String, Object>> getMany(@RequestParam("userId") Long userId) {
        List<TicketDTO> tickets = ticketService.getMany(userId);

        if (tickets.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(Map.of(
                            "status", "fail",
                            "message", "No tickets found for the user"
                    ));
        }

        System.out.println("tickets in Controller: " + tickets);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "status", "success",
                        "message", "Tickets retrieved successfully",
                        "data", tickets
                ));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Map<String, Object>> getOne(@RequestParam("userId") Long userId, @PathVariable Long ticketId) {
        Optional<TicketDTO> ticketDTO = ticketService.getOne(userId, ticketId);
        System.out.println("ticket in Controller: " + ticketDTO);
        System.out.println("userId: " + userId);

        if (ticketDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {

            // Tạo mã QR
            byte[] qrCode = generateQRCode(ticketId, userId);

            // Trả về thông tin vé cùng với mã QR
            return ResponseEntity.ok(Map.of(
                    "ticket", ticketDTO.get(),
                    "qrcode", qrCode
            ));
        } catch (WriterException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to generate QR code"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Tạo một vé mới cho người dùng
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOne(@RequestParam("userId") Long userId, @RequestBody Ticket ticket) {
        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "fail",
                            "message", "User ID is required"
                    ));
        }

        System.out.println("ticket trong req.body: " + ticket);

        try {
            // Tạo vé mới
            Ticket createdTicket = ticketService.createOne(userId, ticket);
            return ResponseEntity
                    .status(HttpStatus.CREATED)  // Mã trạng thái 201 (Created)
                    .body(Map.of(
                            "status", "success",
                            "message", "Ticket created",
                            "data", createdTicket
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "fail",
                            "message", e.getMessage()
                    ));
        }
    }

    // Cập nhật vé của người dùng
    @PutMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(
            @RequestParam("userId") Long userId,    // userId từ query parameter
            @PathVariable Long ticketId,           // ticketId từ URL
            @RequestBody Map<String, Object> updateData) {  // Dữ liệu cập nhật từ request body

        // Gọi service để cập nhật vé
        Optional<Ticket> updatedTicket = ticketService.updateOne(userId, ticketId, updateData);

        // Kiểm tra xem vé có tồn tại và đã được cập nhật không
        return updatedTicket
                .map(ticket -> new ResponseEntity<>(ticket, HttpStatus.OK))  // Nếu cập nhật thành công
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));  // Nếu không tìm thấy vé
    }

    // Tạo mã QR
    public byte[] generateQRCode(Long ticketId, Long userId) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String content = String.format("ticketId:%d,ownerId:%d", ticketId, userId);  // Corrected format specifiers
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 256, 256);

        // Chuyển BitMatrix thành byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        return baos.toByteArray();
    }
}
