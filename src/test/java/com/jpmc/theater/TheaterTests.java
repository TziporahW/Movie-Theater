package com.jpmc.theater;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TheaterTests {
    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 2, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        assertEquals(reservation.totalFeeBeforeDiscount(), 50);
    }

    //try to make reservation for showing that doesn't exist
    @Test
    void createInvalidReservation() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            theater.reserve(john, 15, 4);
        });
    }

    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }

    @Test
    void printMovieScheduleInJSON() throws JSONException {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printJSONSchedule();
    }
}
