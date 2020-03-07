package com.example.akc.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean isShowingAnswers = false;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.see);
        findViewById(R.id.option1).setVisibility(View.INVISIBLE);
        findViewById(R.id.option2).setVisibility(View.INVISIBLE);
        findViewById(R.id.option3).setVisibility(View.INVISIBLE);
        isShowingAnswers = true;

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCardDisplayedIndex++;
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.see);
                findViewById(R.id.option1).setVisibility(View.INVISIBLE);
                findViewById(R.id.option2).setVisibility(View.INVISIBLE);
                findViewById(R.id.option3).setVisibility(View.INVISIBLE);
                isShowingAnswers = true;
            }
        });

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.option1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.option1).setBackgroundColor(getResources().getColor(R.color.wrongAnswer));
            }
        });

        findViewById(R.id.option2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.wrongAnswer));
            }
        });

        findViewById(R.id.option3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.option3).setBackgroundColor(getResources().getColor(R.color.rightAnswer));
            }
        });

        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("currQuestion", ((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                intent.putExtra("currAnswer", ((TextView) findViewById(R.id.flashcard_answer)).getText().toString());
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowingAnswers == false) {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.see);
                    findViewById(R.id.option1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.option2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.option3).setVisibility(View.INVISIBLE);
                    isShowingAnswers = true;
                } else {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.nosee);
                    findViewById(R.id.option1).setVisibility(View.VISIBLE);
                    findViewById(R.id.option2).setVisibility(View.VISIBLE);
                    findViewById(R.id.option3).setVisibility(View.VISIBLE);
                    isShowingAnswers = false;
                }
            }
        });

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if(data != null){
                String question = data.getExtras().getString("newQuestion");
                String answer = data.getExtras().getString("newAnswer");
                flashcardDatabase.insertCard(new Flashcard(question, answer));
                allFlashcards = flashcardDatabase.getAllCards();
                ((TextView)findViewById(R.id.flashcard_question)).setText(question);
                ((TextView)findViewById(R.id.flashcard_answer)).setText(answer);
                ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.see);
                findViewById(R.id.option1).setVisibility(View.INVISIBLE);
                findViewById(R.id.option2).setVisibility(View.INVISIBLE);
                findViewById(R.id.option3).setVisibility(View.INVISIBLE);
                isShowingAnswers = true;
            }
        }
    }
}
