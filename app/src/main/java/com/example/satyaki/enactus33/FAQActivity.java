package com.example.satyaki.enactus33;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FAQActivity extends AppCompatActivity {

    Button FAQ1,FAQ2,FAQ3,FAQ4,FAQ5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);


        FAQ1 = (Button)findViewById(R.id.qes1);
        FAQ2 = (Button)findViewById(R.id.qes2);
        FAQ3 = (Button)findViewById(R.id.qes3);
        FAQ4 = (Button)findViewById(R.id.qes4);
        FAQ5 = (Button)findViewById(R.id.qes5);


        FAQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "www.i.imgur.com/fiVABTq.gif";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+url));
            }
        });

        FAQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url1 = "www.i.imgur.com/FzfwLOi.gifv";
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+url1));
            }
        });

        FAQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url2 = "www.i.imgur.com/mietqxN.gif";
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+url2));
            }
        });

        FAQ4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url3 = "www.i.imgur.com/mXJJbSs.gifv";
                Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+url3));
            }
        });

        FAQ5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url4 = "www.i.imgur.com/wr6dDwH.gif";
                Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+url4));
            }
        });



    }
}
