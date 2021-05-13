package com.team13.doctorclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.MedicineSuggestAdapter;
import com.team13.doctorclient.models.DrugDetail;
import com.team13.doctorclient.models.DrugModel;
import com.team13.doctorclient.repositories.OnSuccessResponse;
import com.team13.doctorclient.repositories.services.DrugService;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrugAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrugAddFragment extends BottomSheetDialogFragment {
    AutoCompleteTextView autoCompleteTextView;
    TextView drugName;
    TextInputEditText quantityMorning, quantityNoon, quantityEvening;
    EditText note;
    Button discard,save;
    AddDrugListener listener;

    final int MESSAGE_AUTOCOMPLETE_TRIGGERED = 136;
    private static final long AUTO_COMPLETE_DELAY = 200;

    DrugModel selectedDrug;
    Handler handler;
    DrugService service;
    MedicineSuggestAdapter adapter;

    public DrugAddFragment() {
        // Required empty public constructor
    }

    public static DrugAddFragment newInstance() {
        DrugAddFragment fragment = new DrugAddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new DrugService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_drug_add, container, false);
        autoCompleteTextView= view.findViewById(R.id.search_drug);
        drugName= view.findViewById(R.id.drug_name);

        // set value from search view to drug name
        quantityMorning = view.findViewById(R.id.input_morning_quality);
        quantityNoon = view.findViewById(R.id.input_noon_quality);
        quantityEvening = view.findViewById(R.id.input_evening_quality);
        note= view.findViewById(R.id.drug_note);
        discard= view.findViewById(R.id.drug_discardBtn);
        save= view.findViewById(R.id.drug_saveBtn);

        discard.setOnClickListener(v -> handleClear());
        save.setOnClickListener( v -> handleSave());

        setupAutoComplete();
        return view;
    }

    private void setupAutoComplete() {
        handler = new Handler(msg -> {
            if (msg.what == MESSAGE_AUTOCOMPLETE_TRIGGERED) {
                if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                    makeApiCall(autoCompleteTextView.getText().toString());
                }
            }
            return false;
        });
        //Adapters
        adapter = new MedicineSuggestAdapter(getContext(), R.layout.medicine_list_item);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(adapter);

        //Events
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDrug = adapter.getItem(position);
                drugName.setText(selectedDrug.getName());
            }
        });
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(MESSAGE_AUTOCOMPLETE_TRIGGERED);
                handler.sendEmptyMessageDelayed(MESSAGE_AUTOCOMPLETE_TRIGGERED,AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void handleSave() {
        if (selectedDrug == null) {
            Toast.makeText(getContext(), "Cannot leave medicine empty", Toast.LENGTH_LONG).show();
            return;
        }

        DrugDetail drugDetail = new DrugDetail();
        drugDetail.setDrugModel(selectedDrug);
        drugDetail.setQuantity(getTotalQuantity());
        drugDetail.setNote(getNote());

        if (listener != null) listener.onDrugSaved(drugDetail);
        dismiss();
    }

    private void handleClear() {
        drugName.setText("");
        quantityMorning.setText("");
        quantityEvening.setText("");
        quantityNoon.setText("");
        note.setText("");
        autoCompleteTextView.setText("");
    }


    private void makeApiCall(String string) {
        service.searchDrug(string, 5, new OnSuccessResponse<DrugModel[]>() {
            @Override
            public void onSuccess(DrugModel[] response) {
                adapter.setData(new ArrayList<>(Arrays.asList(response)));
            }
        });
    }

    public int getTotalQuantity(){
        String[] quantities = { quantityMorning.getText().toString(), quantityNoon.getText().toString(), quantityEvening.getText().toString()};
        int total=0;
        for (String s: quantities) {
            Log.w("quality", s);
            try {
                total+= Integer.parseInt(s);
            }
            catch (NumberFormatException e){

            }
        }
        return total;
    }
    public String getNote(){
        return  "Buổi sáng: " + quantityMorning.getText().toString()+"\n"
                + "Buổi trưa: " + quantityNoon.getText().toString()+"\n"
                + "Buổi tối: " + quantityEvening.getText().toString()+"\n"
                + note.getText().toString();
    }
    public interface AddDrugListener{
        void onDrugSaved(DrugDetail addDrug);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddDrugListener) {
            listener= (AddDrugListener)context;
        }
        else {
           throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener= null;
    }
}