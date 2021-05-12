package com.team13.patientclient;

import com.team13.patientclient.models.AccountModel;
import com.team13.patientclient.models.HospitalModel;

public class Store {
    //Singleton
    private static volatile Store _instance;

    public static Store get_instance() {
        if(_instance == null){
            synchronized (Store.class){
                if(_instance==null){
                    _instance = new Store();
                }
            }
        }
        return _instance;
    }

    private AccountModel userAccount;
    private HospitalModel hospital;

    private Store(){
    }

    public boolean isFullyLoaded() { return userAccount != null && hospital != null; }

    public AccountModel getUserAccount() {
        return userAccount;
    }

    public String getName() {return userAccount.getUserInfor().getName();}
    public String getPatientId() {return userAccount.getUserInfor().getId();}

    public void setUserAccount(AccountModel userAccount) {
        this.userAccount = userAccount;
    }

    public HospitalModel getHospital() {
        return hospital;
    }

    public void setHospital(HospitalModel hospital) {
        this.hospital = hospital;
    }

    public String getCurrentAppointmentId(){
        return this.userAccount.getUserInfor().getCurrentAppointmentId();
    }

    public boolean isHavingAnAppointment(){
        //return false;
        return this.getCurrentAppointmentId() != null;
    }

    public void bookingAnAppointment(String appointmentId){
        this.userAccount.getUserInfor().setCurrentAppointmentId(appointmentId);
    }
}
