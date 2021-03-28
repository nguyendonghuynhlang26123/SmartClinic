package com.team13.patientclient;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.team13.patientclient.models.AccountModel;

import java.util.ArrayList;
import java.util.HashMap;

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

    public AccountModel getUserAccount() {
        return userAccount;
    }

    public String getName() {return userAccount.getUserInfor().getName();}

    public void setUserAccount(AccountModel userAccount) {
        this.userAccount = userAccount;
    }
}
