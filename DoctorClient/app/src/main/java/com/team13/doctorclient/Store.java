package com.team13.doctorclient;

import com.team13.doctorclient.models.AccountModel;

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

    private Store(){
    }

    public boolean isFullyLoaded() { return userAccount != null; }

    public AccountModel getUserAccount() {
        return userAccount;
    }

    public String getName() {return userAccount.getUserInfor().getDoctorName();}

    public void setUserAccount(AccountModel userAccount) {
        this.userAccount = userAccount;
    }
}
