package com.augmented;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInf;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.model_bg_original,
            R.drawable.main_slide_2_2,
            R.drawable.back_5

    };

    int headings[] = {
            R.string.first_slide_title,
            R.string.third_slide_title,
            R.string.fourth_slide_title,
    };

    int descriptions[]={
            R.string.first_slide_desc,
            R.string.third_slide_desc,
            R.string.fourth_slide_desc
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInf.inflate(R.layout.welcom_screen, container, false);

        ImageView img = v.findViewById(R.id.slider_image);
        TextView txt = v.findViewById(R.id.slider_heading);
        TextView txt2 = v.findViewById(R.id.slider_desc);



        img.setImageResource(images[position]);
        txt.setText(headings[position]);

        txt2.setText(descriptions[position]);
        container.addView(v);


        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
