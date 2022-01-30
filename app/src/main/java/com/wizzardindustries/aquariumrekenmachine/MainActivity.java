package com.wizzardindustries.aquariumrekenmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

public class MainActivity extends AppCompatActivity {

    EditText InputKH;
    EditText InputVolume;
    TextView OutputAquadur;
    TextView OutputGH;
    TextView Outputuscm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForUpdate();
        InputKH = (EditText) findViewById(R.id.InputKH);
        InputVolume = (EditText) findViewById(R.id.InputVolume);
        OutputAquadur = findViewById(R.id.OutputAquadur);
        OutputGH = findViewById(R.id.OutputGH);
        Outputuscm = findViewById(R.id.Outputuscm);
        InputKH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Calculate();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        InputVolume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Calculate();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ImageButton close = findViewById(R.id.btn_Close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageButton reset = findViewById(R.id.btn_Reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputKH.setText("");
                InputVolume.setText("");
                OutputAquadur.setText("");
            }
        });

    }


    private void Calculate() {

        if(!(isEmpty(InputKH) || isEmpty(InputVolume))) {
            double kh = Double.parseDouble(String.valueOf(InputKH.getText().toString().replace(',', '.')));
            double volume = Double.parseDouble(String.valueOf(InputVolume.getText().toString().replace(',', '.')));

            //(18.75*L*Kh)/250 = X g Poeder
            double Aquadur = (18.75 * volume * kh) / 250;
            double GH = Aquadur * (3.2/18.75);
            double uscm = Aquadur * (210/18.75);

            String valaquadur = String.format("%.2f", Aquadur);
            String valGH = String.format("%.1f", GH);
            String valuscm = String.format("%.0f", uscm);

            OutputAquadur.setText(valaquadur + " g");
            OutputGH.setText(valGH + " °");
            Outputuscm.setText(valuscm + " ppm");
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }


    private void checkForUpdate() {
        AppUpdater appUpdate = new AppUpdater(this);
        appUpdate.setDisplay(Display.DIALOG)
                .setUpdateFrom(UpdateFrom.GITHUB)
                .setGitHubUserAndRepo("imawizzard", "AquariumRekenmachine")
                .setTitleOnUpdateAvailable("Update beschikbaar!")
                .setCancelable(false)
                .start();
    }
}