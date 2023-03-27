package cinema.dto;

import cinema.entity.Seat;

import java.util.UUID;

public record TicketDTO (UUID token, Seat ticket) {
}
