package com.example.grupoes.projetoes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grupoes.projetoes.R;

public class EvaluationsActivity extends AppCompatActivity {

    private Button comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluations);

        getSupportActionBar().setTitle("Evaluations");

        comment = (Button) findViewById(R.id.commentEButton);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyEvaluationActivity.class);
                startActivity(i);
            }
        });

    }
}
