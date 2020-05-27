package com.example.walter.projects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Variable declarations
    private TextInputEditText mEmail, mPassword;
    private TextView mSignUp;
    private Button mSignIn;
    private ProgressBar mProgress;

    // Volley Variables
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting UI views from our xml file
        mEmail = findViewById(R.id.emailAddress_EditText);
        mPassword = findViewById(R.id.password_EditText);
        mSignIn = findViewById(R.id.signin_button);
        mSignUp = findViewById(R.id.signup_textView);
        mProgress = findViewById(R.id.progress);


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,SignUpActivity.class));

            }
        });



        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra("email",mEmail.getText().toString());
                startActivity(intent);

                // signIn(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });

    }


    private void signIn( final String email, final String password) {

        mProgress.setVisibility(View.VISIBLE);
        // Initializing Request queue
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);

        mStringRequest = new StringRequest(Request.Method.POST,
                getBaseUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");

                    if (success.equals("1")) {

                        mProgress.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                        // Finish
                        finish();
                        // Start activity dashboard
                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    }
                    if (success.equals("0")) {

                        mProgress.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    mProgress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mProgress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);

                return params;
            }
        };

        mStringRequest.setShouldCache(false);
        mRequestQueue.add(mStringRequest);
    }


    private String getBaseUrl (){
        return "http://"+getResources().getString(R.string.machine_ip_address)+"/android/sign_in.php";
    }


}
