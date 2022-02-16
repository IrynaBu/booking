package com.example.bookticket.simple.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Ticket {
    private int id;
    private Integer place;
    private LocalDateTime filmDate;
    private String film;
}
