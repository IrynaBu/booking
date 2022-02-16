package com.example.bookticket.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderDto {
    private Long orderId;
    private Long userId;
    private Long ticketId;
}
