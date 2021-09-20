package com.example.satyaki.enactus33;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AppHomeActivity extends AppCompatActivity {

    Button booknow,logout,AboutUs,FAQs;
    String idH,phH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);

        booknow = (Button)findViewById(R.id.book);
        logout = (Button)findViewById(R.id.logout);

        Intent intent = getIntent();

        idH=intent.getStringExtra("idH");
        phH=intent.getStringExtra("phH");

        AboutUs = (Button)findViewById(R.id.us);
        FAQs = (Button)findViewById(R.id.faq);


        FAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(AppHomeActivity.this,FAQActivity.class);
            }
        });


        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AppHomeActivity.this,AboutUsActivity.class);
            }
        });


        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppHomeActivity.this, BookActivity.class);
                intent.putExtra("idH",idH);
                intent.putExtra("phH",phH);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOutClass logOutClass = new LogOutClass();
                logOutClass.logout(idH,phH,AppHomeActivity.this,getCacheDir());

            }
        });

    }
}
