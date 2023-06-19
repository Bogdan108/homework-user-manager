package ru.bsluckyanchuk.dto;


public class WorkoutRequest {
    private String name;
    private String descr;
    private long duration;
    private String coachName;

    public String getName() {
        return name;
    }

    public String getDescipt() {
        return descr;
    }

    public long getDuration() {
        return duration;
    }

    public String getCoachName() {
        return coachName;
    }

}
