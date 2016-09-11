package com.example.utkarsh.simplilenddemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utkarsh.simplilenddemo.backend.Syncing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    TextView error;
    Button loginSubmit;
    EditText username, password;
    SharedPreferences sp;
    JSONArray details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        error=(TextView)findViewById(R.id.error);
        loginSubmit=(Button)findViewById(R.id.loginSubmit);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        sp = getSharedPreferences("myPref", MODE_PRIVATE);

        try {
            details=new JSONArray(sp.getString("details","default"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (error.getVisibility() == View.VISIBLE)
                    error.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (error.getVisibility() == View.VISIBLE)
                    error.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().length()==0 || password.getText().length()==0){
                    error.setText("Please fill all the entries");
                    error.setVisibility(View.VISIBLE);
                }
                else{
                    String usernameHash=Hash(username.getText().toString());
                    String passwordHash=Hash(password.getText().toString());
                    if(!(validate(usernameHash,passwordHash).contentEquals("Invalid"))){

                        Intent intent=new Intent(Login.this,Welcome.class);
                        intent.putExtra("name", validate(usernameHash,passwordHash));
                        startActivity(intent);
                        finish();
                    }
                    else{
                        error.setText("Invalid Username or Password");
                        error.setVisibility(View.VISIBLE);
                    }


                }


            }
        });


    }


    private String Hash(String str){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(str.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();


    }

    private String validate(String hashedUsername, String hashedPassword){
        for(int i=0;i<details.length();i++){
            try {
                if(details.getJSONObject(i).getString("username").contentEquals(hashedUsername)){
                    if(details.getJSONObject(i).getString("password").contentEquals(hashedPassword)){
                        return details.getJSONObject(i).getString("name");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "Invalid";
    }
}
