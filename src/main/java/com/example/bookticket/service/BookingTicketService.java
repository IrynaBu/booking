package com.example.bookticket.service;

import com.example.bookticket.dto.BookingTicketDto;
import com.example.bookticket.dto.OrderDto;
import com.example.bookticket.entity.Order;
import com.example.bookticket.entity.Ticket;
import com.example.bookticket.entity.User;
import com.example.bookticket.repository.OrderRepository;
import com.example.bookticket.repository.TicketRepository;
import com.example.bookticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingTicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderDto bookTicket(BookingTicketDto bookingTicket) {
        return ticketRepository.findById(bookingTicket.getTicketId())
                .stream()
                .map(ticket -> createNewOrder(bookingTicket, ticket))
                .map(order -> new OrderDto()
                        .setTicketId(bookingTicket.getTicketId())
                        .setUserId(bookingTicket.getUserId())
                        .setOrderId(order.getId()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Unable to booking ticket"));
    }

    private Order createNewOrder(BookingTicketDto bookingTicket, Ticket ticket) {
        return userRepository.findById(bookingTicket.getUserId())
                .stream()
                .map(user -> createOrder(ticket, user))
                .findFirst()
                .orElseThrow(() -> {
                    String message = String.format("User with id %s not fount", bookingTicket.getUserId());
                    log.error(message);
                    return new IllegalArgumentException(message);
                });
    }

    private Order createOrder(Ticket ticket, User user) {
        Order order = new Order()
                .setTicket(ticket)
                .setUser(user);

        return orderRepository.save(order);
    }
}
