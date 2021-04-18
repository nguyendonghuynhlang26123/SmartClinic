package com.team13.patientclient.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.team13.patientclient.R;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String verificationId;
    EditText inputOtp;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    String testVerificationCode = "123456";
    String testPhone = "+84932138120";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);
        mAuth.getFirebaseAuthSettings().setAutoRetrievedSmsCodeForPhoneNumber(testPhone,testVerificationCode);
        Intent i = getIntent();
        String phone = i.getStringExtra("phone");
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v->finish());
        inputOtp = findViewById(R.id.input_otp);
        generateOtp(phone);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            FirebaseUser user = task.getResult().getUser();
                            Intent i = new Intent(OtpActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void generateOtp(String phone){
        phone = phone.substring(1);
        phone = "+84"+phone;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(testPhone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // Save verification ID and resending token so we can use them later
            OtpActivity.this.verificationId = verificationId;
            resendingToken = token;
//            OtpActivity.this.enab
        }
        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
//            final String code = phoneAuthCredential.getSmsCode();
//            if (code != null) {
//                inputOtp.setText(code);
//                verifyCode(code);
//            }
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}