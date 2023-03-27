package cinema.dto;

import cinema.entity.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnDTO {
    @JsonProperty ("returned_ticket")
    Seat seat;

    public ReturnDTO(Seat seat) {
        this.seat = seat;
    }
}
