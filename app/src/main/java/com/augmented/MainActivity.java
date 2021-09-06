package com.augmented;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button btn_goto;
    ViewFlipper view_Flipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_Flipper = findViewById(R.id.flipView);

        int images[] = {R.drawable.main_slide_new2,
                R.drawable.main_slide_4,
                R.drawable.main_slide_7,
                R.drawable.main_slide_2};

        for (int image : images) {
            flipperImages(image);
        }



        showToastMessage();
        btn_goto = (Button) findViewById(R.id.camera_Btn);
        btn_goto.setOnClickListener(v -> {
            btn_goto = (Button) findViewById(R.id.camera_Btn);
            Intent in = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(in);
        });


    }


    public void flipperImages(int image) {
        ImageView imgView = new ImageView(this);
        imgView.setBackgroundResource(image);
        view_Flipper.addView(imgView);
        view_Flipper.setFlipInterval(4000);
        view_Flipper.setAutoStart(true);

        view_Flipper.setInAnimation(this, android.R.anim.slide_in_left);
        view_Flipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }


    public void showToastMessage() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.textView3);
        text.setText("Apply Loreal Matte Shades to discover the new you!!");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 20, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }


}