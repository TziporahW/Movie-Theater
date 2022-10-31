package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;


public class Theater {

    private LocalDateProvider provider;
    private List<Showing> schedule;

    public Theater(LocalDateProvider provider) {
        this.provider = provider;
        this.schedule = createSchedule();
    }

    private List<Showing> getSchedule(){
        return schedule;
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) throws ArrayIndexOutOfBoundsException {
        Showing showing;
        try {
            showing = getSchedule().get(sequence - 1);
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
            throw new ArrayIndexOutOfBoundsException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    public void printSchedule() {
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee())
        );
        System.out.println("===================================================");
    }

    public void printJSONSchedule() throws JSONException {
        JSONObject theater = new JSONObject();
        schedule.forEach(s ->
                {
                    try {
                        theater.put(String.valueOf(s.getSequenceOfTheDay()), new HashMap<String, String>(){{
                            put("start time", s.getStartTime().toString());
                            put("movie", s.getMovie().getTitle());
                            put("duration", humanReadableFormat(s.getMovie().getRunningTime()));
                            put("cost", " $" + s.getMovieFee());
                        }});
                    } catch (JSONException e) {
                        throw new RuntimeException("Cannot format schedule into JSON");
                    }
                }
        );
        theater.put("Date", provider.currentDate());
        System.out.println(theater);
    }

    private String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private List<Showing> createSchedule(){
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        List<Showing> theaterSchedule = List.of(
                new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
        return theaterSchedule;
    }

    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public static void main(String[] args) throws JSONException {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
        theater.printJSONSchedule();
    }
}
