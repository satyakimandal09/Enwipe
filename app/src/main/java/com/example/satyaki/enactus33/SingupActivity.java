package com.example.satyaki.enactus33;

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

public class SingupActivity extends AppCompatActivity {

    EditText F_name,L_name, Email, Pswrd, Ph, Cswrd;
    Button Next;
    String  F_nameH,L_nameH, EmailH, PswrdH, PhH, CswrdH, AddH;
    ProgressDialog progressDialog;
    Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        F_name = (EditText) findViewById(R.id.fname);
        L_name = (EditText) findViewById(R.id.lname);
        Email = (EditText) findViewById(R.id.email);
        Pswrd = (EditText) findViewById(R.id.pswrd);
        Cswrd = (EditText) findViewById(R.id.cnfrm);
        Ph = (EditText) findViewById(R.id.ph);
        Next = (Button) findViewById(R.id.next);
        progressDialog = new ProgressDialog(SingupActivity.this);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    Intent intent = new Intent(SingupActivity.this, AddressActivity.class);
                    intent.putExtra("F_nameH",F_nameH);
                    intent.putExtra("L_nameH",L_nameH);
                    intent.putExtra("EmailH",EmailH);
                    intent.putExtra("PswrdH",PswrdH);
                    intent.putExtra("PhH",PhH);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SingupActivity.this,"Don't leave fields empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot() {
        F_nameH = F_name.getText().toString().trim();
        L_nameH = L_name.getText().toString().trim();
        EmailH = Email.getText().toString().trim();
        PswrdH = Pswrd.getText().toString().trim();
        CswrdH = Cswrd.getText().toString().trim();
        PhH = Ph.getText().toString().trim();
        if (TextUtils.isEmpty(F_nameH) || TextUtils.isEmpty(L_nameH) || TextUtils.isEmpty(EmailH) || TextUtils.isEmpty(PswrdH) || TextUtils.isEmpty(CswrdH) || TextUtils.isEmpty(PhH) && PswrdH.compareTo(CswrdH)!=0) {
            CheckEditText = false;
        }
        else{
            CheckEditText = true;
        }
    }

}
