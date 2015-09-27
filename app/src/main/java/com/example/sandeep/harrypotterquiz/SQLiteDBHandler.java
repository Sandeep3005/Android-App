package com.example.sandeep.harrypotterquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class SQLiteDBHandler extends SQLiteAssetHelper
{
    private static final String TAG = "mytag";
    private static final String DATABASE_NAME = "harryPotterDB.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public  String[] getQuestion(int qCounter)
    {
        Log.d(TAG, "inside getQuestion");
        /**********VARIABLE SECTION STARTS************/
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String questionDetails[]=new String[6];
        int arrayPtr=0;
        /**********VARIABLE SECTION ENDS************/

        /****************************DATABASE ACCESS SECTION STARTS.****************************/

        String [] sqlSelect = {"0 _id","Question","Option1","Option2","Option3","Ans","QID"};
        String sqlTables = "PotterQuizCollection";

        qb.setTables(sqlTables);
        Log.d(TAG, "just before cursor");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        Log.d(TAG, "just after cursor");
        /****************************DATABASE ACCESS SECTION DONE. WE HAVE CURSOR C WITH US********************************************/

        c.moveToFirst();
        Log.d(TAG, c.getString(6));

        c.moveToFirst();
        while(!c.isAfterLast())
        {
            if(c.getString(1)!= null)
            {
                if(c.getString(6).compareTo(Integer.toString(qCounter))==0)
                {
                    questionDetails[arrayPtr] = c.getString(1);// c.getString(c.getColumnIndex("COLUMN_QUESTION"));
                    Log.d(TAG, c.getString(1));
                    arrayPtr++;
                    questionDetails[arrayPtr] = c.getString(2);
                    arrayPtr++;
                    questionDetails[arrayPtr] = c.getString(3);
                    arrayPtr++;
                    questionDetails[arrayPtr] = c.getString(4);
                    arrayPtr++;
                    questionDetails[arrayPtr] = c.getString(5);
                }
            }
            c.moveToNext();
            //Log.d(TAG, "outside if");
        }

        db.close();
        return questionDetails;
    }

    public int getFirstQuestionPointer()
    {
        /**********VARIABLE SECTION STARTS************/
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String questionDetails[]=new String[6];
        int quesPtr=0;
        /**********VARIABLE SECTION ENDS************/

        /****************************DATABASE ACCESS SECTION STARTS.****************************/

        String [] sqlSelect = {"0 _id","QID"};
        String sqlTables = "PotterQuizCollection";

        qb.setTables(sqlTables);
        Log.d(TAG, "just before cursor");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        Log.d(TAG, "just after cursor");
        /****************************DATABASE ACCESS SECTION DONE. WE HAVE CURSOR C WITH US********************************************/

        c.moveToFirst();
        if(c.getString(1)!= null)
        {
            quesPtr = Integer.parseInt(c.getString(1));// c.getString(c.getColumnIndex("COLUMN_QUESTION"));
        }

        db.close();

        return quesPtr;
    }

    public int getLastQuestionPtr()
    {
        /**********VARIABLE SECTION STARTS************/
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        int lastQuesPtr=0;
        /**********VARIABLE SECTION ENDS************/

        /****************************DATABASE ACCESS SECTION STARTS.****************************/

        String [] sqlSelect = {"0 _id","QID"};
        String sqlTables = "PotterQuizCollection";

        qb.setTables(sqlTables);
        Log.d(TAG, "just before cursor");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        Log.d(TAG, "just after cursor");
        /****************************DATABASE ACCESS SECTION DONE. WE HAVE CURSOR C WITH US********************************************/

        c.moveToLast();
        // Log.d(TAG, "after moveFirst");

        if(c.getString(1)!= null)
        {
            //Log.d(TAG, "inside if");
            lastQuesPtr = Integer.parseInt(c.getString(1));
        }
        db.close();

        return lastQuesPtr;
    }

}
