/*
 * Copyright (c)2018 Yue Ma - All Rights Reserved. Email Address: ym5@ualberta.ca
 *
 */

package com.example.ym5_subbook;
/**
 * Created on 2018-02-05.
 * @author: Yue Ma
 * @version:1.0
 * This activity is used to handle edit and view action of the subscription list.
 * After the user clicks the edit button, the current row will be passed in to this activity.
 * After the user finishes changing/viewing, this activity will call main activity to send
 * back the newest data.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class EditViewActivity extends AppCompatActivity {
    private int currentLocation;
    private static final String FILENAME = "file.sav";
    private EditText name,date,charge,comment,totalCharge;
    private ArrayList<subscription> subsList;

    /**
     *  Called when the activity is first created.
     *  Modified from Lonely-tweet class provided in lab
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editview);
        Bundle bd = this.getIntent().getExtras();
        if(bd!=null) {
            currentLocation = bd.getInt("position");
        }

        name = (EditText) findViewById(R.id.nameText2);
        date = (EditText) findViewById(R.id.dateText2);
        charge = (EditText) findViewById(R.id.chargeText2);
        comment = (EditText) findViewById(R.id.commentText2);
        final Button finishEdittingButton = (Button) findViewById(R.id.editFinishButton2);
        subsList = new ArrayList<subscription>();
        loadFromFile();

        //fill the blank lines with one record
        String currentName = subsList.get(currentLocation).getName();
        String currentDate = subsList.get(currentLocation).getDate();
        Float currentCharge = subsList.get(currentLocation).getCharge();
        String currentComments = subsList.get(currentLocation).getComments();
        name.setText(currentName);
        date.setText(currentDate);
        charge.setText(currentCharge.toString());
        comment.setText(currentComments);
        /**
         * save the changes and go back to mainActivity
         */
        finishEdittingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = name.getText().toString();
                String dateString = date.getText().toString();
                String chargeString = charge.getText().toString();
                String commentString = comment.getText().toString();

                if((checkUserInput(nameString,dateString,chargeString,commentString,name,
                        date,charge,comment) )== true) {

                    subsList.get(currentLocation).setName(name.getText().toString());
                    subsList.get(currentLocation).setDate(date.getText().toString());
                    subsList.get(currentLocation).setCharge(charge.getText().toString());
                    subsList.get(currentLocation).setComments(comment.getText().toString());
                    saveInFile();
                    Intent MainIntent = new Intent(EditViewActivity.this, MainActivity.class);
                    startActivity(MainIntent);

                }
                else{
                    Log.i("some input wrong","000");
                }

            }
        });
    }

    /**
     * Used to load data in memory
     * Modified from Lonely-tweet class provided in lab
     */
    private void loadFromFile(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<subscription>>() {}.getType();
            subsList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            subsList = new ArrayList<subscription>();
        }
    }
    /**
     * Used to save data in memory
     * Modified from Lonely-tweet class provided in lab
     */
    protected void saveInFile()  {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);//goes stream based on filename
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos)); //create buffer writer
            Gson gson = new Gson();
            gson.toJson(subsList, out);//convert java object to json string & save in output
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * check whether the user's input follows the format
     * @param name user input
     * @param date user input
     * @param charge user input
     * @param comments user input
     * @param nameEdit EditText
     * @param dateEdit EditText
     * @param chargeEdit EditText
     * @param commentEdit EditText
     * @return
     */
    public Boolean checkUserInput(String name,String date,String charge,String comments, EditText nameEdit,EditText
            dateEdit,EditText chargeEdit,EditText commentEdit){
        boolean isDate = false;
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";
        isDate = date.matches(datePattern);

        float chargeFloat = -0.1f;
        try {
            chargeFloat = Float.parseFloat(charge);
        }catch(NumberFormatException e){
            //check whether charge is an integer
            chargeEdit.setError("Not an integer!");
            return false;
        }

        if(name.length()>20){
            //check the length of the name
            nameEdit.setError("Name is too long!");
            return false;
        }
        else if(comments.length()>30){
            //check the length of the comments
            commentEdit.setError("Comment is too long!");
            return false;
        }
        else if(chargeFloat<0){
            //check charge is non-negative
            chargeEdit.setError("Charge shouldn't be negative!");
            return false;
        }
        else if(isDate == false){
            //check date format
            dateEdit.setError("Date format is not yyyy-mm-dd");
            return false;

        }
        return Boolean.TRUE;
    }
}





