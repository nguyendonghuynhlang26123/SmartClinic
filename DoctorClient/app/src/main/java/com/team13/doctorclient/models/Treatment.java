package com.team13.doctorclient.models;

public class Treatment {
    String id;
    Appointment appointment;
    Prescription prescription;

    public Treatment(Appointment appointment, Prescription prescription) {
        this.appointment = appointment;
        this.prescription = prescription;
    }

    public Treatment(String serviceName, String date,String time, String doctorId, String doctorName, String status) {
        this.appointment = new Appointment();
        appointment.setService(new ServicePack(serviceName, "", 0, "1"));
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setDoctor(new Doctor(doctorId, doctorName));
        appointment.setStatus(status);

        this.prescription = null;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Prescription getPrescription() {
        return prescription;
    }


    public String getServicePack() {
        return appointment.getService().getName();
    }

    public String getDate() {
        return appointment.getDate();
    }

    public String getDoctorName() {
        return appointment.getDoctor().getDoctorName();
    }

    public String getTime() {
        return appointment.getTime();
    }

    public String getTreatmentId() {
        return getTreatmentId();
    }

    public String getStatus(){return appointment.getStatus();}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

