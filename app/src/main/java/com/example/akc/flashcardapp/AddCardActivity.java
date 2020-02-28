package com.example.akc.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activity);

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("newQuestion", ((EditText) findViewById(R.id.NewQuestion)).getText().toString());
                data.putExtra("newAnswer", ((EditText) findViewById(R.id.NewAnswer)).getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
