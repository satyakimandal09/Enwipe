package com.example.satyaki.enactus33;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogOutClass extends AppCompatActivity {

    String HttpUrl = "http://18.188.5.169:8080/logout";
    Context ctx ;
    File cachedir ;
    public int  logout(String idH , String phH,Context ctx,File file){
        this.ctx=ctx;
        this.cachedir=file;
        return query(idH,phH);

    }

    public int query(String idH, String phH)
    {
        JSONObject jsonObject = new JSONObject();
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        try {
            jsonObject.put("id",idH);
            jsonObject.put("phone",phH);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status").trim();
                            Log.d("STATUSLOGOUT",status);
                            if(status.equals("true")){
                                f_write();
                            }
                            else{
                                Toast.makeText(ctx, "Log Out Un successful", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ctx, "Internal Server Error Click Again", Toast.LENGTH_LONG).show();
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
        return 1;
    }

    public void f_write(){
        String content = " \n ";
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(cachedir, "MyCache");
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
            Intent intent = new Intent(ctx,LoginActivity.class);
            ctx.startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
