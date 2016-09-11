package com.example.utkarsh.simplilenddemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.utkarsh.simplilenddemo.backend.Retrieve;
import com.example.utkarsh.simplilenddemo.backend.Syncing;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Retrieve {

    TextView server;
    Button register, login;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        server=(TextView)findViewById(R.id.server);
        server.setVisibility(View.VISIBLE);
        register=(Button)findViewById(R.id.register);
        register.setVisibility(View.GONE);
        login=(Button)findViewById(R.id.login);
        login.setVisibility(View.GONE);

        if(!haveNetworkConnection()){
            server.setText("No Internet Connection. Please connect and restart the app.");
        }
        else{
            Syncing sync=new Syncing(MainActivity.this);
            sync.execute("http://hrrealvalue.com/sldemo/index.php/controllerauth/getdetails");
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        sp = getSharedPreferences("myPref", MODE_PRIVATE);




    }

    @Override
    public void retrieving(JSONObject param) {
        if(param.has("details")){

            try {
                editor = sp.edit();
                editor.putString("details",param.getJSONArray("details").toString());
                editor.apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            server.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);

        }

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnectedOrConnecting())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnectedOrConnecting())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
