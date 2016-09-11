package com.example.utkarsh.simplilenddemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utkarsh.simplilenddemo.backend.Retrieve;
import com.example.utkarsh.simplilenddemo.backend.Syncing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by utkarsh on 11/9/16.
 */
public class Register extends AppCompatActivity implements Retrieve {

    TextView error;
    Button registerSubmit;
    EditText name, username, password;
    SharedPreferences sp;
    JSONArray details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        error=(TextView)findViewById(R.id.error);
        registerSubmit=(Button)findViewById(R.id.registerSubmit);
        name=(EditText)findViewById(R.id.name);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        sp = getSharedPreferences("myPref", MODE_PRIVATE);

        try {
            details=new JSONArray(sp.getString("details","default"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (error.getVisibility() == View.VISIBLE)
                    error.setVisibility(View.INVISIBLE);
                return false;
            }
        });

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

        registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().length()==0 || username.getText().length()==0 || password.getText().length()==0){
                    error.setText("Please fill all the entries");
                    error.setVisibility(View.VISIBLE);
                }
                else{
                    String usernameHash=Hash(username.getText().toString());
                    if(usernameAlreadyExists(usernameHash)){
                        error.setText("Username Already Exists");
                        error.setVisibility(View.VISIBLE);
                    }
                    else{
                        Syncing sync=new Syncing(Register.this);
                        String url="http://hrrealvalue.com/sldemo/index.php/controllerauth/register?name="+name.getText().toString()+
                                "&username="+username.getText().toString()+
                                "&password="+password.getText().toString();
                        url=url.replace(" ","%20");
                        sync.execute(url);
                        Toast.makeText(Register.this, "Please wait...",
                                Toast.LENGTH_LONG).show();
                        registerSubmit.setVisibility(View.GONE);
                        error.setVisibility(View.GONE);
                    }

                }


            }
        });


    }

    @Override
    public void retrieving(JSONObject param) {
        if(param.has("registeredUserId")){
            Toast.makeText(Register.this, "Registered Successfully",
                    Toast.LENGTH_LONG).show();
            Intent intent=new Intent(Register.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

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

    private boolean usernameAlreadyExists(String hashedUsername){
        for(int i=0;i<details.length();i++){
            try {
                if(details.getJSONObject(i).getString("username").contentEquals(hashedUsername)){
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
