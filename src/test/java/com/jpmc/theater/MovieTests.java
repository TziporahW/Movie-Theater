package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
    //test if all specials apply will it apply the bigger discount (starting between 11-4)
    @Test
    void specialMovieWith25PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0)));
        assertEquals(9.38, spiderMan.calculateTicketPrice(showing));

        System.out.println(Duration.ofMinutes(90));
    }

    //test if movie special code discount is applied
    @Test
    void specialMovieWith20PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
        assertEquals(10.00, spiderMan.calculateTicketPrice(showing));

        System.out.println(Duration.ofMinutes(90));
    }

    //test if movie sequence code discount is applied
    @Test
    void specialMovieWith1DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 7, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
        assertEquals(11.50, spiderMan.calculateTicketPrice(showing));

        System.out.println(Duration.ofMinutes(90));
    }


}
