package com.team13.doctorclient.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
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

import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.team13.doctorclient.R;
import com.team13.doctorclient.adapters.MedicineSuggestAdapter;
import com.team13.doctorclient.models.DrugDetail;
import com.team13.doctorclient.models.DrugModel;

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
    MedicineSuggestAdapter adapter;

    final int MESSAGE_AUTOCOMPLETE_TRIGGERED = 136;
    DrugModel selectedDrug;
    Handler handler;

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

        discard.setOnClickListener(v -> {
            drugName.setText("");
            quantityMorning.setText("");
            quantityEvening.setText("");
            quantityNoon.setText("");
            note.setText("");
        });

        adapter = new MedicineSuggestAdapter(getContext(), R.layout.medicine_list_item);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(adapter);
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

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MESSAGE_AUTOCOMPLETE_TRIGGERED) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }

        });
        return view;
    }


    private void makeApiCall(String string) {
    }

    public int getTotalQuality(String[] qualities){
        int total=0;
        for (String s:qualities) {
            Log.w("quality", s);
            try {
                total+= Integer.parseInt(s);
                Log.w("total", String.valueOf(total));
            }
            catch (NumberFormatException e){

            }
        }
        return total;
    }
    public String getNote(String[] qualities){
        String note="";
        for (int i = 0; i <qualities.length ; i++) {
            if(!qualities[i].equals("")){
                switch (i){
                    case 0:
                        note+="Buổi sáng: "+qualities[i]+"\n";
                        break;
                    case 1:
                        note+="Buổi trưa: "+qualities[i]+"\n";
                        break;
                    case 2:
                        note+="Buổi tối: "+qualities[i]+"\n";
                        break;
                }
            }
        }
        return note;
    }
    public interface AddDrugListener{
        void onSaveDrug(DrugDetail addDrug);
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