package com.example.akc.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }


    // -------------------- Just a function to make options invisible
    public void makeOptionsInvisible(){
        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
    }

    // -------------------- Reset the answers color to default
    public void resetOptionColors(){
        findViewById(R.id.rightAnswer).setBackgroundColor(getResources().getColor(R.color.answerColor));
        findViewById(R.id.option1).setBackgroundColor(getResources().getColor(R.color.answerColor));
        findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.answerColor));
    }

    // -------------------- Variables
    int ADD_CARD_REQUEST_CODE = 100;
    int EDIT_CARD_REQUEST_CODE = 200;
    boolean isShowingAnswers = false;
    FlashcardDatabase flashcardDatabase;
    Flashcard flashcardToEdit;
    List<Flashcard> allFlashcards;


    // -------------------- onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database to store our flashcards
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();
        makeOptionsInvisible();
        resetOptionColors();

        // Populate
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.rightAnswer)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.option1)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView) findViewById(R.id.option2)).setText(allFlashcards.get(0).getWrongAnswer2());
        }

        // Go to the next question
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.isEmpty()){
                    return;
                } else {
                    Flashcard temp = allFlashcards.get(getRandomNumber(0,allFlashcards.size() - 1));
                    ((TextView) findViewById(R.id.flashcard_question)).setText(temp.getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(temp.getAnswer());
                    ((TextView) findViewById(R.id.rightAnswer)).setText(temp.getAnswer());
                    ((TextView) findViewById(R.id.option1)).setText(temp.getWrongAnswer1());
                    ((TextView) findViewById(R.id.option2)).setText(temp.getWrongAnswer2());
                    makeOptionsInvisible();
                    resetOptionColors();
                }
            }
        });

        // Click question -> Turn the card to show answer
        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
            }
        });

        // Click answer -> Turn the card to show question
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }
        });

        // Clicking options (Red for wrong answers and green for right answers)
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
        findViewById(R.id.rightAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rightAnswer).setBackgroundColor(getResources().getColor(R.color.rightAnswer));
            }
        });

        // Edit the question
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                while (!allFlashcards.get(i).getQuestion().equals(((TextView) findViewById(R.id.flashcard_question)).getText().toString())){
                    i++;
                }
                flashcardToEdit = allFlashcards.get(i);
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("currQuestion", ((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                intent.putExtra("currAnswer", ((TextView) findViewById(R.id.flashcard_answer)).getText().toString());
                intent.putExtra("newOption1", ((TextView) findViewById(R.id.option1)).getText().toString());
                intent.putExtra("newOption2", ((TextView) findViewById(R.id.option2)).getText().toString());
                MainActivity.this.startActivityForResult(intent, EDIT_CARD_REQUEST_CODE);
            }
        });

        // Delete a question
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                if (allFlashcards.isEmpty()){
                    ((TextView) findViewById(R.id.flashcard_question)).setText("Add a question!");
                    ((TextView) findViewById(R.id.flashcard_answer)).setText("Add a question and it's answer!");
                    ((TextView) findViewById(R.id.rightAnswer)).setText("Add the answer!");
                    ((TextView) findViewById(R.id.option1)).setText("Add a wrong option!");
                    ((TextView) findViewById(R.id.option2)).setText("Add another wrong option!");
                } else {
                    Flashcard temp = allFlashcards.get(getRandomNumber(0,allFlashcards.size() - 1));
                    ((TextView) findViewById(R.id.flashcard_question)).setText(temp.getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(temp.getAnswer());
                    ((TextView) findViewById(R.id.rightAnswer)).setText(temp.getAnswer());
                    ((TextView) findViewById(R.id.option1)).setText(temp.getWrongAnswer1());
                    ((TextView) findViewById(R.id.option2)).setText(temp.getWrongAnswer2());
                    makeOptionsInvisible();
                    resetOptionColors();
                }
            }
        });

        // Click the eye icon to show or hide options
        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowingAnswers == false) {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.see);
                    findViewById(R.id.option1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.option2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.rightAnswer).setVisibility(View.INVISIBLE);
                    isShowingAnswers = true;
                } else {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.nosee);
                    findViewById(R.id.option1).setVisibility(View.VISIBLE);
                    findViewById(R.id.option2).setVisibility(View.VISIBLE);
                    findViewById(R.id.rightAnswer).setVisibility(View.VISIBLE);
                    isShowingAnswers = false;
                }
            }
        });

        // Add a new card
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, ADD_CARD_REQUEST_CODE);
            }
        });

    }


    // -------------------- On Activity for adding/editing a card
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card successfully created",
                    Snackbar.LENGTH_SHORT)
                    .show();
            String question = data.getExtras().getString("newQuestion");
            String answer = data.getExtras().getString("newAnswer");
            String option1 = data.getExtras().getString("newOption1");
            String option2 = data.getExtras().getString("newOption2");
            flashcardDatabase.insertCard(new Flashcard(question, answer, option1, option2));
            allFlashcards = flashcardDatabase.getAllCards();
        } else if (requestCode == EDIT_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            String question = data.getExtras().getString("newQuestion");
            String answer = data.getExtras().getString("newAnswer");
            String option1 = data.getExtras().getString("newOption1");
            String option2 = data.getExtras().getString("newOption2");
            flashcardToEdit.setQuestion(question);
            flashcardToEdit.setAnswer(answer);
            flashcardToEdit.setWrongAnswer1(option1);
            flashcardToEdit.setWrongAnswer2(option2);
            flashcardDatabase.updateCard(flashcardToEdit);
        }
    }
}
