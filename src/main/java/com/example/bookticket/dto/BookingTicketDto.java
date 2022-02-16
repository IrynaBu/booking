package com.example.bookticket.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class BookingTicketDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long ticketId;
}
