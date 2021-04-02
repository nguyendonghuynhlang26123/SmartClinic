package com.team13.doctorclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.team13.doctorclient.models.Drug;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrugAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrugAddFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SearchView searchView;
    TextView drugName;
    TextInputEditText qualityMorning,qualityNoon,qualityEvening;
    EditText note;
    Button discard,save;
    AddDrugListener listener;
    public DrugAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrugAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrugAddFragment newInstance(String param1, String param2) {
        DrugAddFragment fragment = new DrugAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_drug_add, container, false);
        searchView= view.findViewById(R.id.search_drug);

        drugName= view.findViewById(R.id.drug_name);
        qualityMorning= view.findViewById(R.id.input_morning_quality);
        qualityNoon= view.findViewById(R.id.input_noon_quality);
        qualityEvening= view.findViewById(R.id.input_evening_quality);
        note= view.findViewById(R.id.drug_note);
        discard= view.findViewById(R.id.drug_discardBtn);
        save= view.findViewById(R.id.drug_saveBtn);
        String[] qualities={qualityMorning.getText().toString(),qualityNoon.getText().toString(),qualityEvening.getText().toString()};
        discard.setOnClickListener(v -> {
            drugName.setText("");
            qualityMorning.setText("");
            qualityEvening.setText("");
            qualityNoon.setText("");
            note.setText("");
        });
        save.setOnClickListener(v -> {

            Drug addDrug= new Drug("001",drugName.toString(),String.valueOf(getTotalQuality(qualities)),note.getText().toString()) ;
            listener.onSaveDrug(addDrug);
            //TODO

        });
        return view;
    }



    public int getTotalQuality(String[] qualities){
        int total=0;
        for (String s:qualities) {
            try {
                total+= Integer.parseInt(s);
            }
            catch (NumberFormatException e){

            }
        }
        return total;
    }
    public interface AddDrugListener{
        void onSaveDrug(Drug addDrug);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddDrugListener) {
            listener= (AddDrugListener)context;
        }
        else {
//            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener= null;
    }
}