package com.example.satyaki.enactus33;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.FileNameMap;

public class AddressActivity extends AppCompatActivity {

    TextView AddressView;
    EditText SocietyName, HouseNumber, StreetAddress, Landmark, Pincode;
    Button RegisterButton;
    Spinner City, State;
    String  F_nameH,L_nameH, EmailH, PswrdH, PhH;
    String SocietyNameH, HouseNumberH, StreetAddressH, LandmarkH, PincodeH;
    String HttpUrl = "http://18.188.5.169:8080/registration";
    Boolean CheckEditText;
    String StateH, CityH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        AddressView = (TextView) findViewById(R.id.address_view);
        SocietyName = (EditText) findViewById(R.id.society_name);
        HouseNumber = (EditText) findViewById(R.id.house_number);
        StreetAddress = (EditText) findViewById(R.id.street_address);
        Landmark = (EditText) findViewById(R.id.landmark);
        Pincode = (EditText) findViewById(R.id.pincode);
        F_nameH = getIntent().getStringExtra("F_nameH");
        L_nameH = getIntent().getStringExtra("L_nameH");
        EmailH = getIntent().getStringExtra("EmailH");
        PswrdH = getIntent().getStringExtra("PswrdH");
        PhH =  getIntent().getStringExtra("PhH");


        City = (Spinner) findViewById(R.id.city);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.City));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        City.setAdapter(myAdapter);

        State = (Spinner) findViewById(R.id.state);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.State));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        State.setAdapter(myAdapter1);

        //State = (Spinner) findViewById(R.id.state);
        RegisterButton = (Button) findViewById(R.id.register);




        RegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    try {
                        UserRegister2();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddressActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void UserRegister2() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(AddressActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("society_name", SocietyNameH);
            jsonObject.put("house_number", HouseNumberH);
            jsonObject.put("street_address", StreetAddressH);
            jsonObject.put("landmark", LandmarkH);
            jsonObject.put("pincode", PincodeH);
            jsonObject.put("f_name", F_nameH);
            jsonObject.put("l_name", L_nameH);
            jsonObject.put("email", EmailH);
            jsonObject.put("phone", PhH);
            jsonObject.put("password",PswrdH);
            jsonObject.put("city",CityH);
            jsonObject.put("state",StateH);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String str = response.getString("status");
                            if(str.equalsIgnoreCase("3")) {
                                Toast.makeText(AddressActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AddressActivity.this,OtpActivity.class);
                                intent.putExtra("phone",PhH);
                                intent.putExtra("id","2");
                                startActivity(intent);
                            }
                            else if(str.equalsIgnoreCase("1")){
                                Toast.makeText(AddressActivity.this, "Email already exists", Toast.LENGTH_LONG).show();
                            }
                            else if(str.equalsIgnoreCase("2")){
                                Toast.makeText(AddressActivity.this, "Phone number already exists", Toast.LENGTH_LONG).show();
                            }
                            else if(str.equalsIgnoreCase("4")){
                                Toast.makeText(AddressActivity.this, "Password cannot contain spaces", Toast.LENGTH_LONG).show();
                            }
                            else if(str.equalsIgnoreCase("5")){
                                Toast.makeText(AddressActivity.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(AddressActivity.this, "Facing internal server error sorry for the inconvenience", Toast.LENGTH_LONG).show();
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

        queue.add(jsonObjectRequest);

    }

    public void CheckEditTextIsEmptyOrNot() {
        SocietyNameH = SocietyName.getText().toString().trim();
        HouseNumberH = HouseNumber.getText().toString().trim();
        StreetAddressH = StreetAddress.getText().toString().trim();
        LandmarkH = Landmark.getText().toString().trim();
        PincodeH = Pincode.getText().toString().trim();
        StateH = State.getSelectedItem().toString();
        CityH = City.getSelectedItem().toString();
        if (TextUtils.isEmpty(SocietyNameH) || TextUtils.isEmpty(HouseNumberH) ||
            TextUtils.isEmpty(StreetAddressH) || TextUtils.isEmpty(LandmarkH) ||
            TextUtils.isEmpty(CityH) || TextUtils.isEmpty(StateH)) {
            CheckEditText = false;
        }
        else{
            CheckEditText = true;
        }
    }

}
