package com.jpmc.theater;

public class Reservation {
    private Customer customer;
    private Showing showing;
    private int audienceCount;

    public Reservation(Customer customer, Showing showing, int audienceCount) {
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }

    public double totalFeeBeforeDiscount() {
        return showing.getMovieFee() * audienceCount;
    }

    public double totalFeeAfterDiscount() {
        return showing.calculateFee(audienceCount);
    }
}