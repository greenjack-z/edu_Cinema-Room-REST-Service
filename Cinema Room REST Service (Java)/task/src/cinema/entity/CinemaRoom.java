package cinema.entity;

import cinema.dto.FreeSeatsDTO;
import cinema.dto.ReturnDTO;
import cinema.dto.StatsDTO;
import cinema.dto.TicketDTO;
import cinema.exceptions.PasswordException;
import cinema.exceptions.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.IntUnaryOperator;

@Service
public class CinemaRoom {
    private final int rows;
    private final int columns;
    private final IntUnaryOperator priceSetter;
    private Object[][] seats;
    private final Map<UUID, Seat> bookedSeats;
    private final String password;
    private int income = 0;

    @Autowired
    public CinemaRoom(int rows, int columns, IntUnaryOperator priceSetter, String password) {
        this.rows = rows;
        this.columns = columns;
        this.priceSetter = priceSetter;
        this.password = password;
        this.bookedSeats = new HashMap<>();
    }

    @PostConstruct
    void init() {
        seats = new Object[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                    seats[row][col] = new Seat(row + 1, col +1, priceSetter.applyAsInt(row));
            }
        }
    }

    public FreeSeatsDTO getFreeSeats() {
        List<Seat> seatsList = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                var current = seats[row][col];
                if (current instanceof Seat seat) {
                    seatsList.add(seat);
                }
            }
        }
        return new FreeSeatsDTO(rows, columns, seatsList);
    }

    public synchronized TicketDTO buySeat(int row, int column) throws RequestException {
        try {
            var current = seats[row][column];
            if (current instanceof UUID) {
                throw new RequestException("The ticket has been already purchased!");
            }
            UUID token = UUID.randomUUID();
            seats[row][column] = token;
            Seat seat = (Seat) current;
            income += seat.price();
            bookedSeats.put(token, seat);
            return new TicketDTO(token, (Seat) current);
        } catch (IndexOutOfBoundsException e) {
            throw new RequestException("The number of a row or a column is out of bounds!");
        }
    }

    public synchronized ReturnDTO returnSeat(UUID token) {
        Seat seat = bookedSeats.remove(token);
        if (seat == null) {
            throw new RequestException("Wrong token!");
        }
        income -= seat.price();
        seats[seat.row() - 1][seat.column() - 1] = seat;
        return new ReturnDTO(seat);
    }

    public StatsDTO stats(String password) {
        if (password == null || !password.equals(this.password)) {
            throw new PasswordException("The password is wrong!");
        }
        return new StatsDTO(income, rows * columns - bookedSeats.size(), bookedSeats.size());
    }
}
