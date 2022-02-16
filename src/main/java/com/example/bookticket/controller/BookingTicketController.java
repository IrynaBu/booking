package com.example.bookticket.controller;

import com.example.bookticket.dto.BookingTicketDto;
import com.example.bookticket.dto.OrderDto;
import com.example.bookticket.service.BookingTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookingTicketController {
    private final BookingTicketService bookingTicketService;

    @PostMapping("/booking")
    public OrderDto bookingTicket(@RequestBody @Valid BookingTicketDto bookingTicket) {
        return bookingTicketService.bookTicket(bookingTicket);
    }
}
