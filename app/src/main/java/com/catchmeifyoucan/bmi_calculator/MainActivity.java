package com.catchmeifyoucan.bmi_calculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tvResult;
    EditText etName;
    EditText etHeight;
    EditText etWeight;
    Button bntCalculate;

    SharedPreferences sharedPreferences;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.textView);
        etName = findViewById(R.id.et_name);
        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        bntCalculate = findViewById(R.id.button);

        // 取sharedPreferences的資料
        text = "";
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        try {
            JSONArray jsonArray = new JSONArray(sharedPreferences.getString("DATA", ""));

            for (int i = 0 ; i < jsonArray.length(); i++) {
                text += jsonArray.getString(i).toString();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvResult.setText(text);

        bntCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("test", "name" + etName.getText().toString());

                if (etName.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("資料不足")
                            .setMessage("請輸入您的名字")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .show();

                    return;
                }
                if (etHeight.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("資料不足")
                            .setMessage("請輸入您的身高(公分)")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .show();

                    return;
                }
                if (etWeight.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("資料不足")
                            .setMessage("請輸入您的體重(公斤)")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .show();

                    return;
                }

                int height = Integer.parseInt(etHeight.getText().toString());
                int weight = Integer.parseInt(etWeight.getText().toString());

                Log.d("Enter height:", ""+height);
                Log.d("Enter weight:", ""+weight);

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("name", etName.getText().toString());
                bundle.putInt("height", height);
                bundle.putInt("weight", weight);
                bundle.putString("bmi", calculate(height, weight));

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

    }

    private String calculate(int height, int weight) {
        float heightMeter = (height/100f) * (height/100f);
        float bmi = weight/ heightMeter;
        String result = String.format("%.1f", bmi); // 取小數點後一位
        return result;
    }
}
