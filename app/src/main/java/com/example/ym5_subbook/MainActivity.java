/*
 * Copyright (c)2018 Yue Ma - All Rights Reserved. Email Address: ym5@ualberta.ca
 *
 */

package com.example.ym5_subbook;
/**
 * Represents monthly subscription
 * @author: Yue Ma
 * @version:1.0
 *
 */

import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity{

    private static final String FILENAME = "file.sav";
    private int currentLocation=-1;
    private ListView listView;
    private EditText name,date,charge,comment,totalCharge;
    private ArrayList<subscription> subsList;
    private UserCustomAdapter userAdapter;

    /**
     *  Called when the activity is first created.
     *  Modified from Lonely-tweet class provided in lab
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.nameText);
        date = (EditText) findViewById(R.id.dateText);
        charge = (EditText) findViewById(R.id.chargeText);
        comment = (EditText) findViewById(R.id.commentText);
        totalCharge = (EditText) findViewById(R.id.totalMonthlyCharge);
        final Button finishEdittingButton = (Button) findViewById(R.id.editFinishButton);
        final Button addButton = (Button) findViewById(R.id.addButton);
        listView = (ListView) findViewById(R.id.oldSubsList);
        subsList = new ArrayList<subscription>();

        loadFromFile();
        userAdapter = new UserCustomAdapter(MainActivity.this,R.layout.row,subsList);
        listView.setItemsCanFocus(false);
        listView.setAdapter(userAdapter);
        //calculate total charge when loaded
        totalCharge.setText(totalMonthlyCharge(subsList));
        //listen to userAdapter, if there is a click on button, it will fetch the event
        userAdapter.setOnClickListenerEditOrDelete(new UserCustomAdapter.OnClickListenerEditOrDelete() {
            @Override
            public void OnClickListenerEdit(int position) {
                Log.i("Edit button is cliked","_____");
                currentLocation = position;
                String currentName = subsList.get(position).getName();
                String currentDate = subsList.get(position).getDate();
                Float currentCharge = subsList.get(position).getCharge();
                String currentComments = subsList.get(position).getComments();
                name.setText(currentName);
                date.setText(currentDate);
                charge.setText(currentCharge.toString());
                comment.setText(currentComments);
                addButton.setVisibility(View.INVISIBLE);
                finishEdittingButton.setVisibility(View.VISIBLE);

            }

            @Override
            public void OnClickListenerDelete(int position) {

                subsList.remove(position);
                userAdapter.notifyDataSetChanged();
                totalCharge.setText(totalMonthlyCharge(subsList));
                saveInFile();

            }
        });
        /**
         * add new subscription
         */
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                setResult(RESULT_OK);
                String nameString = name.getText().toString();
                String dateString = date.getText().toString();
                String chargeString = charge.getText().toString();
                String commentString = comment.getText().toString();

                if((checkUserInput(nameString,dateString,chargeString,commentString,name,
                        date,charge,comment) )== true){
                    subscription subscription = new subscription(nameString, dateString, chargeString, commentString);
                    subsList.add(subscription);
                    totalCharge.setText(totalMonthlyCharge(subsList));

                    userAdapter.notifyDataSetChanged();
                    name.setText(null);
                    date.setText(null);
                    charge.setText(null);
                    comment.setText(null);

                    saveInFile();
                }

            }
        });
        /**
         * apply new change
         */
        finishEdittingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                    totalCharge.setText(totalMonthlyCharge(subsList));

                    userAdapter.notifyDataSetChanged();
                    saveInFile();
                    finishEdittingButton.setVisibility(View.INVISIBLE);
                    addButton.setVisibility(View.VISIBLE);
                    name.setText(null);
                    date.setText(null);
                    charge.setText(null);
                    comment.setText(null);


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
    public String totalMonthlyCharge(ArrayList<subscription> allSubs){
        float totalCost = 0;
        for(int i = 0;i<allSubs.size();i++){
            totalCost+=allSubs.get(i).getCharge();
        }
        totalCost = round(totalCost,2);

        return Float.toString(totalCost);

    }

    /**
     * round float to some decimal length
     * @param d floatnumber
     * @param decimalPlace number of decimal bits
     * @return
     */
    public static float round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace,BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * destory the application
     */
    @Override
    protected void onDestroy() {
        Log.i("Lifecycle", "onDestroy is called");
        super.onDestroy();
        subsList = null;
        listView = null;
        setContentView(null);

    }

}

