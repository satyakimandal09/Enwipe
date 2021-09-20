package com.example.satyaki.enactus33;

/*import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText Phone;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        Phone = (EditText)findViewById(R.id.phone);
        Submit = (Button)findViewById(R.id.send);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this,OtpActivity.class);
            }
        });

    }
}

*/

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText  Phone;
    Button Send;
    //RequestQueue requestQueue;
    String   PhH;
    ProgressDialog progressDialog;
    String HttpUrl = "http://18.188.5.169:8080/chkPhone";
    Boolean CheckEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Phone = (EditText) findViewById(R.id.phone);
        Send = (Button) findViewById(R.id.send);
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    try {
                        CheckPhone();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter valid Phone Number", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void CheckPhone() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(ForgotPasswordActivity.this);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("phone",PhH);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VolleyResponse",response.toString());
                        try {
                            String str = response.getString("status");
                            if(str.equalsIgnoreCase("true")) {
                                Intent intent = new Intent(ForgotPasswordActivity.this,OtpActivity.class);
                                intent.putExtra("phone",PhH);
                                intent.putExtra("id","1");
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(ForgotPasswordActivity.this, "Number Does not Exist",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley",error.toString());
                Toast.makeText(ForgotPasswordActivity.this, "Problem", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void CheckEditTextIsEmptyOrNot() {
        PhH = Phone.getText().toString().trim();
        if (TextUtils.isEmpty(PhH)) {
            CheckEditText = false;
        }
        else{
            CheckEditText = true;
        }
    }
}
