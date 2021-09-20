package com.example.satyaki.enactus33;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InfoDisplay extends AppCompatActivity {

    String HttpUrl="http://18.188.5.169/regsitration";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display);
        ListView list=(ListView)findViewById(R.id.InfoList);
        final List<StoreInfo>infoList=new ArrayList<>();
        RequestQueue queue= Volley.newRequestQueue(InfoDisplay.this);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("phone","7735667006");
            jsonObject.put("id","2");
        }catch (JSONException e){

        }
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(jsonObject);
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.POST, HttpUrl, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject information = response.getJSONObject(i);
                                StoreInfo si=new StoreInfo();
                                si.setAddrees(information.getString("Address"));
                                si.setCust_Name(information.getString("Cust_Name"));
                                si.setCust_Phone(information.getString("Cust_Phone"));
                                si.setDate(information.getString("date"));
                                si.setTime(information.getString("time"));
                                infoList.add(si);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyAdapter adapter=new MyAdapter(this, (ArrayList<StoreInfo>) infoList);
        list.setAdapter(adapter);

    }
    public class StoreInfo{
        String Cust_Name;
        String Cust_Phone;
        String Addrees;
        String date;
        String time;

        public String getCust_Name() {
            return Cust_Name;
        }

        public void setCust_Name(String cust_Name) {
            Cust_Name = cust_Name;
        }

        public String getCust_Phone() {
            return Cust_Phone;
        }

        public void setCust_Phone(String cust_Phone) {
            Cust_Phone = cust_Phone;
        }

        public String getAddrees() {
            return Addrees;
        }

        public void setAddrees(String addrees) {
            Addrees = addrees;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
