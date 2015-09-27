package com.example.sandeep.harrypotterquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import java.text.ParseException;


public class quizResult extends AppCompatActivity {


    TextView quizResultDisplayText;
    ImageView youAreImage,titleImage;
    private static double score=0.0,totalQuestion=0.0;
    double percentageScore=0.0;
    int testX=0;
    private static final int POTTERHEAD =80;
    private static final int SQUIB =40;
    private static final String TAG = "mytag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        quizResultDisplayText = (TextView)findViewById(R.id.showScore);
        youAreImage = (ImageView)findViewById(R.id.youare);
        titleImage = (ImageView)findViewById(R.id.title);

        displayResult();
    }
    public void displayResult()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value1 = extras.getString("Score");
            String value2 = extras.getString("TOTAL");
            score = Double.parseDouble(value1);
            totalQuestion = Double.parseDouble(value2);

            Log.d(TAG,Double.toString(score));
            Log.d(TAG, Double.toString(totalQuestion));

            percentageScore = (score/totalQuestion);
            Log.d(TAG,Double.toString(percentageScore));


            percentageScore = percentageScore * 100;


            Log.d(TAG,Double.toString(percentageScore));
            if(percentageScore >= POTTERHEAD)
            {
                youAreImage.setImageResource(R.drawable.youarepotterhead);
                titleImage.setImageResource(R.drawable.pottehead);
            }
            if((percentageScore >= SQUIB) && (percentageScore < POTTERHEAD))
            {
                youAreImage.setImageResource(R.drawable.youaresquib);
                titleImage.setImageResource(R.drawable.squib);
            }
            if(percentageScore < SQUIB)
            {
                youAreImage.setImageResource(R.drawable.youaremuggle);
                titleImage.setImageResource(R.drawable.muggle);
            }
            quizResultDisplayText.setText("You have scored " + value1+"/"+totalQuestion);
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Intent intent = new Intent(this, launchActivity.class);
        startActivity(intent);
        finish();
    }
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}
