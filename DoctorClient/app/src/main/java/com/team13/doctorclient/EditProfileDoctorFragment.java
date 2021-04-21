package com.team13.doctorclient;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileDoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileDoctorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int SpannedLength = 0,chipLength = 10;
    public EditProfileDoctorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileDoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileDoctorFragment newInstance(String param1, String param2) {
        EditProfileDoctorFragment fragment = new EditProfileDoctorFragment();
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
        View view= inflater.inflate(R.layout.fragment_edit_profile_doctor, container, false);
        FloatingActionButton addchip= view.findViewById(R.id.add_specialities);


        AppCompatEditText Phone = view.findViewById(R.id.phone);

        Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ChipDrawable chip = ChipDrawable.createFromResource(getContext(), R.xml.chip);
                chip.setText(editable.subSequence(SpannedLength,editable.length()-1));
                chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(chip);
                editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannedLength = editable.length();
                Log.w("length", String.valueOf(SpannedLength));

            }
        });
        return view;
    }
}