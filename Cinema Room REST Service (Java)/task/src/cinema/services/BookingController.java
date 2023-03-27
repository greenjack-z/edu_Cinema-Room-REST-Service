package cinema.services;

import cinema.dto.FreeSeatsDTO;
import cinema.dto.ReturnDTO;
import cinema.dto.StatsDTO;
import cinema.dto.TicketDTO;
import cinema.entity.CinemaRoom;
import cinema.entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    private final CinemaRoom cinemaRoom;

    public BookingController(@Autowired CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    @GetMapping("/seats")
    public FreeSeatsDTO getAvailableSeats() {
        return cinemaRoom.getFreeSeats();
    }

    @PostMapping("/purchase")
    public synchronized TicketDTO showSeat(@RequestBody Seat seat) {
        return cinemaRoom.buySeat(seat.row() - 1, seat.column() - 1);
    }

    @PostMapping("/return")
    public synchronized ReturnDTO returnTicket(@RequestBody TicketDTO ticket) {
        return cinemaRoom.returnSeat(ticket.token());
    }

    @PostMapping("/stats")
    public StatsDTO showStats(@RequestParam(required = false) String password) {
        return cinemaRoom.stats(password);
    }
}
