package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grupoes.projetoes.R;

public class MyEvaluationActivity extends AppCompatActivity {

    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluation);

        getSupportActionBar().setTitle("My Evaluation");

        send = (Button) findViewById(R.id.sendButton);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),EvaluationsActivity.class);
                startActivity(i);
            }
        });
    }
}
