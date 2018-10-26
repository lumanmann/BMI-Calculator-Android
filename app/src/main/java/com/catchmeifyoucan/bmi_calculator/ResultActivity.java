package com.catchmeifyoucan.bmi_calculator;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    TextView tvResult;
    Button btnSave;
    Button btnClear;
    SharedPreferences sharedPreferences;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = findViewById(R.id.textView_result);
        btnSave = findViewById(R.id.button_save);
        btnClear = findViewById(R.id.button_clear);

        Bundle bundle = getIntent().getExtras();
        final String name = bundle.getString("name");
        final int height = bundle.getInt("height");
        final int weight = bundle.getInt("weight");
        final String bmi = bundle.getString("bmi");

        Log.d("RA", "bmi: "+ bmi);


//的身高是" + height + "cm、體重是" + weight + "kg，" +
        final String resultStr = "BMI計算結果是"+ bmi;
        tvResult.setText(resultStr);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
                if(sharedPreferences.getString("DATA","") == ""){
                    // 如果是空的話 new empty json array
                    jsonArray = new JSONArray();
                } else {
                    // 不是空->拿到舊資料
                    try {
                        jsonArray = new JSONArray(sharedPreferences.getString("DATA",""));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                // 新的一筆資料
                JSONObject newJsonObj = new JSONObject();
                try {
                    newJsonObj.put("Name", name);
                    newJsonObj.put("Height", height);
                    newJsonObj.put("Weight", weight);
                    newJsonObj.put("Bmi", bmi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(newJsonObj);

                sharedPreferences.edit().putString("DATA", jsonArray.toString()).commit();

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
                sharedPreferences.edit().putString("DATA", "").commit();
            }
        });



    }
}
