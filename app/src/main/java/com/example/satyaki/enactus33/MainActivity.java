package com.example.satyaki.enactus33;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //f_write();
        //f_read();

    }

    public void f_write(){

        String content = " \n ";
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
            if(idH.equals(" ")|| phH.equals(" ")){
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run (){
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },SPLASH_TIME_OUT);
            }
            else{
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run (){
                    Intent intent = new Intent(MainActivity.this, AppHomeActivity.class);
                        intent.putExtra("idH", idH);
                        intent.putExtra("phH", phH);
                        startActivity(intent);
                    finish();
                    }
                },SPLASH_TIME_OUT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
