package com.example.satyaki.enactus33;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class RatingActivity extends AppCompatActivity {

    String HttpUrl="http://18.188.5.169:8080/feedback";
    RatingBar mRatingBar;
    TextView mRatingScale;
    EditText mFeedback;
    Button mSendFeedback;
    final String[] stars = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mRatingScale = (TextView) findViewById(R.id.tvRatingScale);
        mFeedback = (EditText) findViewById(R.id.comment);
        mSendFeedback = (Button) findViewById(R.id.btnSubmit);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            }
        });

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1: mRatingScale.setText("Very bad");
                        stars[0] ="1";break;
                    case 2: mRatingScale.setText("Need some improvement");
                        stars[0] ="2";break;
                    case 3: mRatingScale.setText("Good");
                        stars[0] ="3";break;
                    case 4: mRatingScale.setText("Great");
                        stars[0] ="4";break;
                    case 5: mRatingScale.setText("Awesome. I love it");
                        stars[0] ="5";break;
                    default: mRatingScale.setText("");
                }
            }
        });

       mSendFeedback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(mFeedback.getText().toString().isEmpty()){
                   Toast.makeText(RatingActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
               } else {
                   feedback();
                   mFeedback.setText("");
                   mRatingBar.setRating(0);
                   stars[0]="";
                   Toast.makeText(RatingActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
               }
           }
       });


    }

    public void feedback(){

        String feedback = mFeedback.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(RatingActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("feedback",feedback);
            jsonObject.put("star",stars);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if(status.equals("true")){
                                Toast.makeText(RatingActivity.this,"Thank You for submitting feedback",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RatingActivity.this,AppHomeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RatingActivity.this,"Please re submit feedback",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RatingActivity.this,"Please re submit feedback sorry for inconvenience",Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
