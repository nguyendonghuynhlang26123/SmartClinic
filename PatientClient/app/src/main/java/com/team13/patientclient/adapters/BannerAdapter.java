package com.team13.patientclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.team13.patientclient.activities.PharmacyActivity;
import com.team13.patientclient.activities.ServiceActivity;
import com.team13.patientclient.R;

import java.util.Objects;

public class BannerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    BannerListener[] listeners;

    public BannerAdapter(Context context, BannerListener[] listener1){
        this.context=context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listeners = new BannerListener[3];
        for(int i=0; i<3;++i){
            int finalI = i;
            listeners[i] = () -> listener1[finalI].onBannerClicked();
        }

    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view== object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.banner_item, container, false);
        ImageView bannerImage = itemView.findViewById(R.id.banner_image);
        switch (position){
            case 0:
                bannerImage.setImageResource(R.drawable.banner2);
                itemView.setOnClickListener(v->{
//                    Intent i = new Intent(context, ServiceActivity.class);
//                    context.startActivity(i);
                    listeners[position].onBannerClicked();
                });
                break;
            case 1:
                bannerImage.setImageResource(R.drawable.banner1);
                itemView.setOnClickListener(v->{
//                    Intent i = new Intent(context, PharmacyActivity.class);
//                    context.startActivity(i);
                    listeners[position].onBannerClicked();
                });
                break;
            case 2:
                bannerImage.setImageResource(R.drawable.banner3);
                itemView.setOnClickListener(v->{
                    listeners[position].onBannerClicked();
                });
                break;
        }
        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
    public interface BannerListener{
        void onBannerClicked();
    }

}
