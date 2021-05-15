package com.team13.patientclient.activities.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.team13.patientclient.Store;
import com.team13.patientclient.activities.BookAppointmentDashboard;
import com.team13.patientclient.activities.LocationActivity;
import com.team13.patientclient.activities.PharmacyActivity;
import com.team13.patientclient.R;
import com.team13.patientclient.activities.ServiceActivity;
import com.team13.patientclient.adapters.BannerAdapter;
import org.jetbrains.annotations.NotNull;


public class HomeFragment extends Fragment {

    HomeFragmentListener listener;
    ViewPager viewPager;
    BannerAdapter bannerAdapter;
    DotsIndicator dotsIndicator;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((TextView) view.findViewById(R.id.welcome_text)).setText(getString(R.string.welcome_text,  Store.get_instance().getName()));
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
                return false;
            }
        });
        viewPager = view.findViewById(R.id.news_banner);
        bannerAdapter = new BannerAdapter(view.getContext(),new BannerAdapter.BannerListener[]{() -> {
            Intent i = new Intent(view.getContext(), ServiceActivity.class);
            view.getContext().startActivity(i);
        }, () -> {
            Intent i = new Intent(view.getContext(), PharmacyActivity.class);
            view.getContext().startActivity(i);
        }, () -> listener.gotoBlog()});
        viewPager.setAdapter(bannerAdapter);
        dotsIndicator = view.findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(viewPager);

//      medicineRenderingHandle(view);

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

        view.findViewById(R.id.blog_shortcut).setOnClickListener(v-> listener.gotoBlog());
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
    public void onAttach(@NotNull Context context) {
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