package com.example.satyaki.enactus33;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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
import com.android.volley.toolbox.Volley;
import com.example.satyaki.enactus33.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText PhoneNumber, Password;
    Button LogIn, SignUp, ForgotPassword ;
    String PasswordH, PhoneH;
    String HttpURL = "http://18.188.5.169:8080/login";
    Boolean CheckEditText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PhoneNumber = (EditText) findViewById(R.id.phonenumber);
        Password = (EditText) findViewById(R.id.password);
        LogIn = (Button) findViewById(R.id.login);
        SignUp = (Button) findViewById(R.id.signup);
        ForgotPassword = (Button) findViewById(R.id.frgt);

        f_read();

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                        UserLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SingupActivity.class);
                startActivity(intent);
            }
        });
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        f_read();
        super.onResume();
    }

    public void UserLogin(){
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("phone",PhoneH);
            jsonObject.put("password",PasswordH);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HttpURL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VolleyResponse",response.toString());
                        try {
                            String str = response.getString("id");
                            int a = Integer.parseInt(str);
                            if(a>0) {
                                String idH = Integer.toString(a);
                                f_write(idH,PhoneH);
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this,AppHomeActivity.class);
                                intent.putExtra("idH",idH);
                                intent.putExtra("phH",PhoneH);
                                startActivity(intent);
                            }
                            else if(a==0){
                                Toast.makeText(LoginActivity.this, "User not registered", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Facing internal server error sorry for the inconvenience", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Volley",error.toString());
                Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void CheckEditTextIsEmptyOrNot(){
        PhoneH = PhoneNumber.getText().toString();
        PasswordH = Password.getText().toString();
        if(TextUtils.isEmpty(PasswordH) || TextUtils.isEmpty(PhoneH)) {
            CheckEditText = false;
        }
        else {
            CheckEditText = true ;
        }
    }

    public void f_write(String a, String b){

        String content = a + "\n" + b;
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(getCacheDir(), "MyCache");
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void f_read() {

        BufferedReader input = null;
        File file1 = null;
        try {
            file1 = new File(getCacheDir(), "MyCache");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
            final String idH,phH;
            idH = input.readLine();
            phH = input.readLine();
            if(idH.equals(" ")||phH.equals(" ")){}
            else{
                Intent intent = new Intent(LoginActivity.this, AppHomeActivity.class);
                intent.putExtra("idH",idH);
                intent.putExtra("phH",phH);
                startActivity(intent);
                finish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
