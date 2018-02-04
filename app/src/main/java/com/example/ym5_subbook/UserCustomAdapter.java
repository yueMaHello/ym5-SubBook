/*
 * MainActivity
 *
 * Version 1.0
 *
 * Oct 1, 2017
 *
 * Acknowledgement:
 *  1. Function onCreate, onStart, loadFromFile, saveInFile used in all activity classes are modified from Lonely-tweet class provided in lab
 *  2. Class CustomAdapter are learned and modified from https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
 */


//http://www.androidhub4you.com/2013/02/muftitouch-listview-multi-click.html
package com.example.ym5_subbook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

//http://www.androidhub4you.com/2013/02/muftitouch-listview-multi-click.html
public class UserCustomAdapter extends ArrayAdapter<subscription> {
    private OnClickListenerEditOrDelete onClickListenerEditOrDelete;
    Context context;
    int layoutResourceId;
    ArrayList<subscription> data = new ArrayList<subscription>();
    private EditText name,date,charge,comment;
    UserHolder mainHolder = null;
    private OnClickListener myButtonListener = null;
    public void setItemCheckButtonListener(OnClickListener myButtonListener){
        this.myButtonListener = myButtonListener;
    }




    public UserCustomAdapter(Context context, int layoutResourceId, ArrayList<subscription> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

    }



    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.nameText = (TextView) row.findViewById(R.id.nameText1);
            holder.dateText = (TextView) row.findViewById(R.id.dateText1);
            holder.chargeText = (TextView) row.findViewById(R.id.chargeText1);
            holder.commentText = (TextView) row.findViewById(R.id.commentText1);

            holder.btnEdit = (Button) row.findViewById(R.id.button1);
            holder.btnDelete = (Button) row.findViewById(R.id.button2);

            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        subscription sub = data.get(position);
        holder.nameText.setText(sub.getName());
        holder.dateText.setText(sub.getDate());
        holder.chargeText.setText(String.valueOf(sub.getCharge()));
        holder.commentText.setText(sub.getComments());

        holder.btnEdit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                if(onClickListenerEditOrDelete!=null){
                    onClickListenerEditOrDelete.OnClickListenerEdit(position);

                }

            }



        });

        holder.btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(onClickListenerEditOrDelete!=null){
                    onClickListenerEditOrDelete.OnClickListenerDelete(position);

                }

            }
        });
        return row;

    }

    static class UserHolder {
        TextView nameText;
        TextView dateText;
        TextView chargeText;
        TextView commentText;
        Button btnEdit;
        Button btnDelete;
    }
    public interface OnClickListenerEditOrDelete{
        void OnClickListenerEdit(int position);
        void OnClickListenerDelete(int position);
    }

    public void setOnClickListenerEditOrDelete(OnClickListenerEditOrDelete onClickListenerEditOrDelete1){
        this.onClickListenerEditOrDelete=onClickListenerEditOrDelete1;
    }

}

