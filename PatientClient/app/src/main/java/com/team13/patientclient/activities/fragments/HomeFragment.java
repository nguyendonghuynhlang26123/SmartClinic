package com.team13.patientclient.activities.fragments;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.team13.patientclient.Store;
import com.team13.patientclient.activities.BookAppointmentDashboard;
import com.team13.patientclient.activities.LocationActivity;
import com.team13.patientclient.activities.PharmacyActivity;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.ServiceActivity;
import com.team13.patientclient.adapters.BannerAdapter;
import com.team13.patientclient.adapters.PharmacyItemAdapter;
import com.team13.patientclient.models.DrugModel;
import com.team13.patientclient.repository.OnSuccessResponse;
import com.team13.patientclient.repository.services.DrugService;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    HomeFragmentListener listener;
    ViewPager viewPager;
    BannerAdapter bannerAdapter;
    DotsIndicator dotsIndicator;
    RecyclerView pharmacyList;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((TextView) view.findViewById(R.id.welcome_text)).setText("Hi, " + Store.get_instance().getName());
        SearchView searchView = view.findViewById(R.id.home_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(view.getContext(), ServiceActivity.class);
                i.putExtra(SearchManager.QUERY, query);
                i.setAction(Intent.ACTION_SEARCH);
                view.getContext().startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                String query = searchView.getQuery().toString();
//                if(!query.isEmpty()){
//                    test.setText(query);
//                    return true;
//                }
                return false;
            }
        });
        viewPager = view.findViewById(R.id.news_banner);
        bannerAdapter = new BannerAdapter(view.getContext());
        viewPager.setAdapter(bannerAdapter);
        dotsIndicator = view.findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(viewPager);

//        medicineRenderingHandle(view);

        view.findViewById(R.id.pharmacy_button).setOnClickListener(v -> {
            Intent i = new Intent(view.getContext(), PharmacyActivity.class);
            startActivity(i);
        });

        view.findViewById(R.id.location_button).setOnClickListener(v -> {
            Intent i = new Intent(view.getContext(), LocationActivity.class);
            startActivity(i);
        });

        view.findViewById(R.id.appointment_shortcut).setOnClickListener(v->{
            Intent i = new Intent(view.getContext(), BookAppointmentDashboard.class);
            startActivity(i);
        });

        view.findViewById(R.id.blog_shortcut).setOnClickListener(v->{
            listener.gotoBlog();
        });
        return view;
    }

//    private void medicineRenderingHandle(View view) {
//        DrugService service = new DrugService();
//        DrugModel[] emptyModels = getEmptyModel(4);
//
//        //Rendering dumb data first while waiting responses from apis
//        pharmacyList = view.findViewById(R.id.pharmacy_list);
//        PharmacyItemAdapter pharmacyItemAdapter = new PharmacyItemAdapter(view.getContext(), new ArrayList<>(Arrays.asList(emptyModels)), false);
//        pharmacyList.setAdapter(pharmacyItemAdapter);
//        pharmacyList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
//
//        service.getMinimizedData(new OnSuccessResponse<DrugModel[]>() {
//            @Override
//            public void onSuccess(DrugModel[] list) {
//                pharmacyItemAdapter.setData(new ArrayList<>(Arrays.asList(list)));
//            }
//        });
//    }

//    private DrugModel[] getEmptyModel(int n) {
//        DrugModel[] returnData = new DrugModel[n];
//
//        for (int i = 0; i < n; i++) {
//            returnData[i] = new DrugModel();
//        }
//        return returnData;
//    }


    public interface HomeFragmentListener{
        void gotoBlog();
   }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentListener) {
            listener = (HomeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}