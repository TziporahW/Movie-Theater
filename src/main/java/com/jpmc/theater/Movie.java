package com.jpmc.theater;

import java.time.Duration;
import java.util.Objects;

public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double getSpecialCode() { return specialCode; }

    public double calculateTicketPrice(Showing showing) {
        return Math.round((ticketPrice - getDiscount(showing)) * 100.0) / 100.0D;
    }

    private double getDiscount(Showing showing) {
        double specialDiscount = 0;
        if (MOVIE_CODE_SPECIAL == getSpecialCode()) {
            specialDiscount = getTicketPrice() * 0.2;  // 20% discount for special movie
            System.out.println("applied special" + specialDiscount);
        }

        double sequenceDiscount = 0;
        if (showing.isSequence(1)) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showing.isSequence(2)) {
            sequenceDiscount = 2; // $2 discount for 2nd show
            System.out.println("applied sequence" + sequenceDiscount);
        } else if (showing.isSequence(7)){
            sequenceDiscount = 1; //$1 discount for 7th show
        }

        double timeDiscount = 0;
        if(showing.getStartTime().getHour() >= 11 && showing.getStartTime().getHour() <= 16){
            timeDiscount = getTicketPrice() * .25; //25% discount for shows between 11 and 4
        }

        // biggest discount wins and gets returned
        return Math.max((Math.max(specialDiscount, sequenceDiscount)), timeDiscount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}