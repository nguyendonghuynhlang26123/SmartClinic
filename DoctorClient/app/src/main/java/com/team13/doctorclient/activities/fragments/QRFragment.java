package com.team13.doctorclient.activities.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.team13.doctorclient.R;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;

public class QRFragment extends Fragment {
    private CodeScanner mCodeScanner;

    public QRFragment() {
        // Required empty public constructor
    }

    public static QRFragment newInstance(String param1, String param2) {
        QRFragment fragment = new QRFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_q_r, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(result -> {
            handleQrScanned(result.getText());
        });
        mCodeScanner.startPreview();
        return root;

    }

    private void handleQrScanned(String text) {
        String[] tokens = text.split("-");
        String appointmentId = tokens[0], doctorId = tokens[1], patientId = tokens[2];

        AppointmentService service = new AppointmentService();
        service.getAppointmentById(appointmentId, new OnResponse<Appointment>() {
            @Override
            public void onRequestSuccess(Appointment response) {
                if (response.getDoctorId().equals(doctorId) && response.getPatientId().equals(patientId)){

                }
                else {

                }
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {

            }
        });
    }
}