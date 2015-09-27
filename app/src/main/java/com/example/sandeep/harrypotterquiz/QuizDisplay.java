package com.example.sandeep.harrypotterquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class QuizDisplay extends AppCompatActivity {

    TextView quizDisplayText;
    Button optBtn1,optBtn2,optBtn3,continueBtn;
    String choice1,choice2,choice3;
    private static final String TAG = "mytag";
    private SQLiteDBHandler db;
    private static int qCounter,startPtr,endPtr,noOfQues=1,getThisQuestion=1;
    private static boolean oneTimeRandom = true;
    private int score =0;


    private static final int TOTAL_QUESTION = 10;
    String questionAndOption[]=new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_display);

        db = new SQLiteDBHandler(this);

        quizDisplayText  = (TextView) findViewById(R.id.txtQuestionDisplay);
        optBtn1 = (Button)findViewById(R.id.option1Btn);
        optBtn2 = (Button)findViewById(R.id.option2Btn);
        optBtn3 = (Button)findViewById(R.id.option3Btn);
        continueBtn = (Button)findViewById(R.id.continueBtn);
        printQuestionSection();
    }

    public void printQuestionSection()
    {
        Log.d(TAG, "inside printQuestionSection");
        if(oneTimeRandom)
        {
            getThisQuestion = getRandomNo();
            oneTimeRandom = false;
        }
        Log.d(TAG, "Get This Question  = "+Integer.toString(getThisQuestion));

        if(noOfQues <= TOTAL_QUESTION)
        {
            questionAndOption = db.getQuestion(getThisQuestion);
            quizDisplayText.setText(questionAndOption[0]);
            optBtn1.setText(questionAndOption[1]);
            optBtn2.setText(questionAndOption[2]);
            optBtn3.setText(questionAndOption[3]);
            noOfQues++;
            getThisQuestion++;


        }
        else
        {
            Intent intent = new Intent(this, quizResult.class);
            intent.putExtra("Score", Integer.toString(score));
            intent.putExtra("TOTAL", Integer.toString(TOTAL_QUESTION));
            startActivity(intent);
            finish();
        }

    }

    public int getRandomNo() {
        Log.d(TAG, "inside getRandomNo");
        int randomNumber = 0, x = 0;
        Boolean isUniqueRandomNum = true;
        Boolean duplicateExist = false;
        Log.d(TAG, "End  = ");
        int START = db.getFirstQuestionPointer();
        int END = db.getLastQuestionPtr();
        //Log.d(TAG, "Start  = " + Integer.toString(START));
        //Log.d(TAG, "End  = " + Integer.toString(END));

        END = END-TOTAL_QUESTION;
        Log.d(TAG, "START  = "+Integer.toString(START));
        Log.d(TAG, "END  = "+Integer.toString(END));
        Random random = new Random();
        randomNumber = getSingleRandomNo(START, END, random);

        return randomNumber;

    }

    public int getSingleRandomNo(int aSTART,int aEND,Random aRANDOM)
    {
        int randomNumber;
        if (aSTART > aEND) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEND - (long)aSTART + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRANDOM.nextDouble());
        randomNumber  =  (int)(fraction + aSTART);

        return randomNumber;

    }

    public void checkAnswer(View v)
    {
        //Toast.makeText(this, "YO YO Honey Singh", Toast.LENGTH_SHORT).show();
        optBtn1.setBackgroundResource(R.drawable.btn_disable_shade);
        optBtn2.setBackgroundResource(R.drawable.btn_disable_shade);
        optBtn3.setBackgroundResource(R.drawable.btn_disable_shade);

        String optionClicked = ((Button)v).getText().toString();
        String correctAnswer = questionAndOption[4];
        if(optionClicked.compareTo(correctAnswer)==0)
        {
            // quizDisplayText.setText("Equal");
            score++;
            v.setBackgroundResource(R.drawable.correct_green_shade);
        }
        else
        {
            //quizDisplayText.setText("NOT Equal");
            v.setBackgroundResource(R.drawable.wrong_red_shade);
        }
        choice1 = optBtn1.getText().toString();
        choice2 = optBtn2.getText().toString();
        choice3 = optBtn3.getText().toString();

        if(choice1.compareTo(correctAnswer)==0)
        {
            optBtn1.setBackgroundResource(R.drawable.correct_green_shade);
        }
        if(choice2.compareTo(correctAnswer)==0)
        {
            optBtn2.setBackgroundResource(R.drawable.correct_green_shade);
        }
        if(choice3.compareTo(correctAnswer)==0)
        {
            optBtn3.setBackgroundResource(R.drawable.correct_green_shade);
        }


        optBtn1.setEnabled(false);
        optBtn2.setEnabled(false);
        optBtn3.setEnabled(false);

        continueBtn.setVisibility(v.VISIBLE);

    }

    public void checkContinue(View v)
    {
        continueBtn.setVisibility(v.GONE);
        printQuestionSection();
        optBtn1.setEnabled(true);
        optBtn2.setEnabled(true);
        optBtn3.setEnabled(true);


        optBtn1.setBackgroundResource(R.drawable.boundary_btn);
        optBtn2.setBackgroundResource(R.drawable.boundary_btn);
        optBtn3.setBackgroundResource(R.drawable.boundary_btn);

    }


    public void onBackPressed()
    {
        Intent intent = new Intent(this, launchActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

        db.close();
    }

}

