package cinema.dto;

import cinema.entity.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FreeSeatsDTO {
    @JsonProperty ("total_rows")
    int rows;
    @JsonProperty ("total_columns")
    int columns;

    @JsonProperty ("available_seats")
    List<Seat> seats;

    public FreeSeatsDTO(int rows, int columns, List<Seat> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }
}
