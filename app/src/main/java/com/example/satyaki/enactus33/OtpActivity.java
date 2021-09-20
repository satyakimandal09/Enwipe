package com.example.satyaki.enactus33;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class OtpActivity extends AppCompatActivity {

    Button submit;
    EditText Otp;
    Intent intent;
    String phone ;
    String id;
    int user_otp,server_otp;
    String HttpUrl = "http://18.188.5.169:8080/otp";
    String HttpUrl1="http://18.188.5.169:8080/otpDelReg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        intent = this.getIntent();
        phone = intent.getStringExtra("phone");
        id = intent.getStringExtra("id");
        submit = (Button)findViewById(R.id.submit1);
        Otp    =  (EditText)findViewById(R.id.text);
        try {
            GetServerOtp();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            int count = 0;

            @Override
            public void onClick(View view) {
                GetUserOtp();
                if(id.equals("1")) {
                    if(user_otp==server_otp){
                        Intent intent1 = new Intent(OtpActivity.this,ConfirmPasswordActivity.class);
                        intent1.putExtra("phone",phone);
                        startActivity(intent1);
                    }
                    else{
                        count++;
                        String str="Only 3 chances"+ " "+(3-count)+" "+"Remains";
                        Toast.makeText(OtpActivity.this,str,Toast.LENGTH_LONG).show();
                        if(count==3){

                            Toast.makeText(OtpActivity.this,"Try again",Toast.LENGTH_LONG).show();
                            Intent intent2 = new Intent(OtpActivity.this,LoginActivity.class);
                            startActivity(intent2);
                        }
                    }

                }
                if(id.equals("2")){
                    if(user_otp==server_otp){
                        Toast.makeText(OtpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent4 =  new Intent(OtpActivity.this,LoginActivity.class);
                        startActivity(intent4);
                    }
                    else{
                        count++;
                        String str="Only 3 chances"+ " "+(3-count)+" "+"Remains";
                        Toast.makeText(OtpActivity.this,str,Toast.LENGTH_LONG).show();
                        if(count==3) {
                            try {
                                delreg();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    public void GetServerOtp()throws JSONException
    {
        RequestQueue queue = Volley.newRequestQueue(OtpActivity.this);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("phone",phone);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VolleyResponse",response.toString());
                        try{
                            String str=response.getString("otp");
                            server_otp=Integer.parseInt(str);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Volley",error.toString());
                Toast.makeText(OtpActivity.this, "Error", Toast.LENGTH_LONG).show();

            }
        });
        queue.add(jsonObjectRequest);
    }

    public void GetUserOtp() {
        user_otp= Integer.parseInt(Otp.getText().toString().trim());
    }

    public void delreg()throws JSONException{
        RequestQueue queue = Volley.newRequestQueue(OtpActivity.this);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("phone",phone);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl1, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VolleyResponse",response.toString());
                        try{
                            String str=response.getString("status");
                            if(str.equals("true")){
                                Toast.makeText(OtpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                Intent intent3 =  new Intent(OtpActivity.this,LoginActivity.class);
                                startActivity(intent3);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley",error.toString());
                Toast.makeText(OtpActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}
