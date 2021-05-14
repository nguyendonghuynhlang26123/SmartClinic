package com.team13.doctorclient.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.team13.doctorclient.R;
import com.team13.doctorclient.Utils;
import com.team13.doctorclient.activities.PatientDetailActivity;
import com.team13.doctorclient.models.Appointment;
import com.team13.doctorclient.models.ErrorResponse;
import com.team13.doctorclient.repositories.OnResponse;
import com.team13.doctorclient.repositories.services.AppointmentService;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QRFragment extends Fragment {
    private CodeScanner mCodeScanner;
    Listener listener;

    public QRFragment() {
        // Required empty public constructor
    }

    public interface Listener{
        void onVerifySucceed(Appointment appointment);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    public static QRFragment newInstance(String param1, String param2) {
        QRFragment fragment = new QRFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCodeScanner != null) mCodeScanner.startPreview();
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
           getActivity().runOnUiThread(() ->  handleQrScanned(result.getText()));
        });

        mCodeScanner.startPreview();
        return root;

    }

    boolean checkValidQRTicket(Appointment response, String doctorId, String patientId) {
        //Check appointment description
        if (!response.getDoctorId().equals(doctorId)  || !response.getPatientId().equals(patientId)) return false;

        //Check time is valid
        @SuppressLint("SimpleDateFormat") String time1 = new SimpleDateFormat(Utils.DATETIME_PATTERN).format(new Date());
        String time2 = response.getTime() + " " + response.getDate();
        long diff = Utils.diffBetween2StringDate(time1, time2);

        if (diff == -1) return false;
        //if (diff != 0) return false; //On the same day


        if (!response.getStatus().equals(Utils.STATUS_PENDING)) return false;

        return true;
    }

    private void handleQrScanned(String text) {
        String[] tokens = text.split("-");
        String appointmentId = tokens[0], doctorId = tokens[1], patientId = tokens[2];
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("Processing data! Please wait a minute.");
        dialog.show();

        AppointmentService service = new AppointmentService();
        service.getAppointmentById(appointmentId, new OnResponse<Appointment>() {
            @Override
            public void onRequestSuccess(Appointment response) {
                if (checkValidQRTicket(response, doctorId, patientId)){
                    Intent intent = new Intent(getContext(), PatientDetailActivity.class);
                    intent.putExtra("appointment", response);
                    intent.putExtra("mode", Utils.PATIENTDETAIL_CHECKIN_MODE);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), "INVALID QR CODE! Please try again", Toast.LENGTH_LONG).show();
                    mCodeScanner.startPreview();
                }
                dialog.dismiss();
            }

            @Override
            public void onRequestFailed(ErrorResponse response) {
                Toast.makeText(getContext(), "INVALID QR CODE! Please try again", Toast.LENGTH_LONG).show();
                mCodeScanner.startPreview();
                dialog.dismiss();
            }
        });
    }
}