package com.team13.doctorclient.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.team13.doctorclient.R;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;

import java.util.HashMap;
import java.util.Map;

public class QRReader extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private String appointmentId;
    private String patientId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_reader);
        appointmentId = getIntent().getStringExtra("appointment");
        patientId = getIntent().getStringExtra("patient");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(QRReader.this, new String[] { Manifest.permission.CAMERA}, 1);
        } else startScanner();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startScanner();
        }
    }

    private void startScanner() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> {
            runOnUiThread(() -> {
                handleOnResult(result.getText());
            });
        });
        scannerView.setOnClickListener(v -> mCodeScanner.startPreview());
    }

    private void handleOnResult(String text) {
        String[] tokens = text.split("-");
        if (tokens[0].equals(appointmentId) && tokens[1].equals(patientId)){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("appointment", appointmentId);
            AppointmentService service = new AppointmentService();
            Map<String, String> params = new HashMap<>();
            params.put("status", Utils.STATUS_PROCESSING);
            service.updateAnAppointment(appointmentId, params, new OnResponse<Void>() {
                @Override
                public void onRequestSuccess(Void response) {
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }

                @Override
                public void onRequestFailed(ErrorResponse response) {
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            });
        } else {
            Toast.makeText(QRReader.this, "Invalid QR Code", Toast.LENGTH_LONG).show();
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

}