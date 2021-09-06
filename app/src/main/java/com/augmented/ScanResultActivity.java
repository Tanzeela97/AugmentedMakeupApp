package com.augmented;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.widget.Toast.LENGTH_LONG;

public class ScanResultActivity extends AppCompatActivity {

    private TextView textView;
    private Button btn_scn;
    private Button apply;
    private Button back_BTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        textView = findViewById(R.id.textViewScan);
        btn_scn = findViewById(R.id.button_scn);
        apply = findViewById(R.id.apply_btn);
        back_BTN = findViewById(R.id.backButton);
        back_BTN.setOnClickListener(v -> {

            Intent in = new Intent(this, MakeupActivity.class);
            startActivity(in);
        });


    }

    //barcode
    public void ScanButton(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String cont = intentResult.getContents();
            if (cont == null) {
                textView.setVisibility(View.VISIBLE);
                btn_scn.setVisibility(View.VISIBLE);
                textView.setText("Cancelled");

            } else if (cont.equals("Rouge-Signature-432")) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("The scanned shade is available..\n Tap button to apply");
                apply.setVisibility(View.VISIBLE);
                btn_scn.setVisibility(View.INVISIBLE);
                apply.setOnClickListener(v -> {

                    Intent in = new Intent(this, MakeupActivity.class);
                    startActivity(in);
                });
            } else if (cont.equals("Rouge-Signature-410")) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("The scanned shade is available..\n Tap button to apply");
                apply.setVisibility(View.VISIBLE);
                btn_scn.setVisibility(View.INVISIBLE);
                apply.setOnClickListener(v -> {

                    Intent in = new Intent(this, MakeupActivity2.class);
                    startActivity(in);
                });
            }

//
              else if (cont.equals("Rouge-Signature-424")) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("The scanned shade is available..\n Tap button to apply");
                apply.setVisibility(View.VISIBLE);
                btn_scn.setVisibility(View.INVISIBLE);
                apply.setOnClickListener(v -> {

                    Intent in = new Intent(this, MakeupActivity3.class);
                    startActivity(in);
                });
            } else if (cont.equals("Rouge-Signature-422")) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("The scanned shade is available..\n Tap button to apply");
                apply.setVisibility(View.VISIBLE);
                btn_scn.setVisibility(View.INVISIBLE);
                apply.setOnClickListener(v -> {

                    Intent in = new Intent(this, MakeupActivity4.class);
                    startActivity(in);
                });
            } else if (cont.equals("Parisian-Sunset-446")) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("The scanned shade is available..\n Tap button to apply");
                apply.setVisibility(View.VISIBLE);
                btn_scn.setVisibility(View.INVISIBLE);
                apply.setOnClickListener(v -> {

                    Intent in = new Intent(this, MakeupActivity5.class);
                    startActivity(in);
                });
            } else if (cont.equals("Rouge-Signature-420")) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("The scanned shade is available..\n Tap button to apply");
                apply.setVisibility(View.VISIBLE);
                btn_scn.setVisibility(View.INVISIBLE);
                apply.setOnClickListener(v -> {

                    Intent in = new Intent(this, MakeupActivity6.class);
                    startActivity(in);
                });
            } else if (cont.equals("Parisian-Sunset-434")) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("The scanned shade is available..\n Tap button to apply");
                apply.setVisibility(View.VISIBLE);
                btn_scn.setVisibility(View.INVISIBLE);
                apply.setOnClickListener(v -> {

                    Intent in = new Intent(this, MakeupActivity7.class);
                    startActivity(in);
                });


            } else {
                textView.setVisibility(View.VISIBLE);
               textView.setText("The Scanned Product is not available..");
                Toast toast = Toast.makeText(getApplicationContext(), "Scanned Product is not available", Toast.LENGTH_SHORT);
                toast.show();

                btn_scn.setVisibility(View.VISIBLE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}