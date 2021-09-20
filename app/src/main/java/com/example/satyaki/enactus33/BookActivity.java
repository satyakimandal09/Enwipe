package com.example.satyaki.enactus33;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class BookActivity extends AppCompatActivity {

    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();

    TextView text;
    Button date;
    Button time;
    TextView textDate,textTime;
    Spinner CarSelect;
    Button BookNow;
    String idH,phH,carH,dateH,timeH,HttpUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        text = (TextView)findViewById(R.id.text);
        date = (Button)findViewById(R.id.date);
        time = (Button)findViewById(R.id.time);
        textDate = (TextView)findViewById(R.id.dateviewer);
        textTime = (TextView)findViewById(R.id.timeviewer);
        CarSelect = (Spinner)findViewById(R.id.car_selection);
        BookNow = (Button)findViewById(R.id.book);

        HttpUrl = "http://18.188.5.169:8080/booking";

        Intent intent = getIntent();
        idH = intent.getStringExtra("idH");
        phH = intent.getStringExtra("phH");

        CarSelect = (Spinner) findViewById(R.id.car_selection);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BookActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Car_Type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        CarSelect.setAdapter(myAdapter);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDate();

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTime();
            }
        });

        updateTextLabel();

        BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    book();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            dateTime.set(Calendar.YEAR,year);
            dateTime.set(Calendar.MONTH,monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            textDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);

            updateTextLabel();

        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dateTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            dateTime.set(Calendar.MINUTE,minute);

            textTime.setText(hourOfDay + ":" + minute);

            updateTextLabel();

        }
    };


    private void updateTextLabel(){
        text.setText(formatDateTime.format(dateTime.getTime()));
    }

    public void book() throws JSONException {
        carH = CarSelect.getSelectedItem().toString().trim();
        dateH = textDate.getText().toString().trim();
        timeH = textTime.getText().toString().trim();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",idH);
        jsonObject.put("phone",phH);
        jsonObject.put("type",carH);
        jsonObject.put("date",dateH);
        jsonObject.put("time",timeH);

        RequestQueue requestQueue = Volley.newRequestQueue(BookActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if(status.equals("true")){
                                Intent intent = new Intent(BookActivity.this,AppHomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(BookActivity.this,"Booking Completed",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(BookActivity.this,"Booking was not completed",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}

