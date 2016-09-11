package com.example.utkarsh.simplilenddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
    String currentUserName;
    TextView welcomeText;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        welcomeText=(TextView)findViewById(R.id.welcomeText);
        logout=(Button)findViewById(R.id.logout);

        Intent intent=getIntent();
        currentUserName=intent.getStringExtra("name");

        welcomeText.setText("Hello, " + currentUserName + "!\n\nWelcome to SimpliLend NetBanking Page.");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
