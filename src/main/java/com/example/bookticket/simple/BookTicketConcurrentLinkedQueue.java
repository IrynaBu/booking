package com.example.bookticket.simple;


import com.example.bookticket.simple.models.Ticket;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class BookTicketConcurrentLinkedQueue {
    private Queue<Ticket> queue = new ConcurrentLinkedQueue<>();
    private volatile boolean cycle = true;

    public BookTicketConcurrentLinkedQueue() {
        ExecutorService producerService = Executors.newFixedThreadPool(5);
        producerService.execute(() -> {
            log.info("Started add tickets");
            try {
                for (int i = 1; i <= 10; i++) {
                    Ticket ticket = new Ticket()
                            .setId(i)
                            .setFilm("Film " + i)
                            .setPlace(i)
                            .setFilmDate(LocalDateTime.now());

                    queue.add(ticket);
                    log.info("Ticket added : {}", ticket);
                    Thread.sleep(200);
                }
                cycle = false;
            } catch (Exception ex) {
                log.error("Exception in thread executor", ex);
            }
        });

        ExecutorService consumerService = Executors.newFixedThreadPool(5);
        consumerService.execute(() -> {
            log.info("Started getting tickets");
            Ticket ticket;
            while (cycle || queue.size() > 0) {
                if ((ticket = queue.poll()) != null)
                    log.info("Ticket removed from queue: {}", ticket);
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                    log.error("Exception in thread executor ", ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        new BookTicketConcurrentLinkedQueue();
    }
}
