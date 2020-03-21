package com.example.akc.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activity);

        String s1 = getIntent().getStringExtra("currQuestion");
        String s2 = getIntent().getStringExtra("currAnswer");
        String s3 = getIntent().getStringExtra("newOption1");
        String s4 = getIntent().getStringExtra("newOption2");
        ((EditText)findViewById(R.id.NewQuestion)).setText(s1);
        ((EditText)findViewById(R.id.NewAnswer)).setText(s2);
        ((EditText)findViewById(R.id.wrongAnswer)).setText(s3);
        ((EditText)findViewById(R.id.otherWrongAsnwer)).setText(s4);

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((((EditText) findViewById(R.id.NewQuestion)).getText().toString().isEmpty()) ||
                        (((EditText) findViewById(R.id.NewAnswer)).getText().toString().isEmpty()) ||
                        (((EditText) findViewById(R.id.wrongAnswer)).getText().toString().isEmpty()) ||
                        (((EditText) findViewById(R.id.otherWrongAsnwer)).getText().toString().isEmpty())){
                    Toast.makeText(getApplicationContext(), "Enter all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent data = new Intent();
                data.putExtra("newQuestion", ((EditText) findViewById(R.id.NewQuestion)).getText().toString());
                data.putExtra("newAnswer", ((EditText) findViewById(R.id.NewAnswer)).getText().toString());
                data.putExtra("newOption1", ((EditText) findViewById(R.id.wrongAnswer)).getText().toString());
                data.putExtra("newOption2", ((EditText) findViewById(R.id.otherWrongAsnwer)).getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
