package com.example.satyaki.enactus33;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmPasswordActivity extends AppCompatActivity {


    EditText NewPassword, ConfirmNewPassword;
    Button ConfirmSubmit;
    String NewPswrdH,NewCswrdH;
    String HttpUrl = "http://18.188.5.169:8080/updatePass";
    String phH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        Intent intent = getIntent();
        phH = intent.getStringExtra("phone");

        NewPassword = (EditText) findViewById(R.id.newpswrd);
        ConfirmNewPassword = (EditText) findViewById(R.id.newcnfrm);
        ConfirmSubmit = (Button) findViewById(R.id.cnfrm_submit);

        NewPswrdH = NewPassword.getText().toString();
        NewCswrdH = ConfirmNewPassword.getText().toString();

        ConfirmSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    delreg();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void delreg() throws JSONException{
        RequestQueue queue = Volley.newRequestQueue(ConfirmPasswordActivity.this);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("phone",phH);
            jsonObject.put("password",NewPswrdH);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VolleyResponse",response.toString());
                        try{
                            String str=response.getString("status");
                            if(str.equals("true")){
                                Toast.makeText(ConfirmPasswordActivity.this, "Update occored", Toast.LENGTH_SHORT).show();
                                Intent intent3 =  new Intent(ConfirmPasswordActivity.this,LoginActivity.class);
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
                Toast.makeText(ConfirmPasswordActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}