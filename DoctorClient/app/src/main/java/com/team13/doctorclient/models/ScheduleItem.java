package com.team13.doctorclient.models;

import java.io.Serializable;

public class ScheduleItem implements Serializable {
    public boolean isSeized;
    String time;
    Appointment appointment;

    public ScheduleItem(Appointment appointment, String time) {
        this.appointment = appointment;
        this.time = time;
        isSeized = this.appointment != null;
    }

    public String getTime() {
        return time;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
