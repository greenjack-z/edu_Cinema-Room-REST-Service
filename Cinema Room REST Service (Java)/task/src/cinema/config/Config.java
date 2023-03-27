package cinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.IntUnaryOperator;

@Configuration
public class Config {
    private static final int CINEMA_ROOM_ROWS = 9;
    private static final int CINEMA_ROOM_COLUMNS = 9;
    private static final int HIGH_PRICE_END_ROW = 4;
    private static final int HIGH_PRICE = 10;
    private static final int LOW_PRICE = 8;

    private static final String PASSWORD = "super_secret";

    @Bean("rows")
    public int getRows() {
        return CINEMA_ROOM_ROWS;
    }

    @Bean("columns")
    public int getColumns() {
        return CINEMA_ROOM_COLUMNS;
    }

    @Bean("priceSetter")
    public IntUnaryOperator setPrice() {
        return x -> x < HIGH_PRICE_END_ROW ? HIGH_PRICE : LOW_PRICE;
    }

    @Bean("password")
    public String getPassword() {
        return PASSWORD;
    }
}
