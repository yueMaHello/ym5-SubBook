/*
 * Copyright (c)2018 Yue Ma - All Rights Reserved. Email Address: ym5@ualberta.ca
 *
 */

package com.example.ym5_subbook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


/*
 *UserCustomAdapter
 *@author: Yue Ma
 * Version 1.0
 *
 * Oct 1, 2017
 *
 * Acknowledgement:
 *  1. Class UserCustomAdapter are learned and modified from //http://www.androidhub4you.com/2013/02/muftitouch-listview-multi-click.html
 */

public class UserCustomAdapter extends ArrayAdapter<subscription>{
    private OnClickListenerEditOrDelete onClickListenerEditOrDelete;
    private Context context;
    private int layoutResourceId;
    private ArrayList<subscription> listData = new ArrayList<subscription>();

    public UserCustomAdapter(Context context, int layoutResourceId, ArrayList<subscription> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.listData = data;

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
        subscription sub = listData.get(position);
        holder.nameText.setText(sub.getName());
        holder.dateText.setText(sub.getDate());
        holder.chargeText.setText(String.valueOf(sub.getCharge()));
        holder.commentText.setText(sub.getComments());
        //listen to the click on buttons
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
    // a holder used to store xml components
    static class UserHolder {
        TextView nameText;
        TextView dateText;
        TextView chargeText;
        TextView commentText;
        Button btnEdit;
        Button btnDelete;
    }

    /**
     * interface to set on click event
     */
    public interface OnClickListenerEditOrDelete{
        void OnClickListenerEdit(int position);
        void OnClickListenerDelete(int position);
    }
    /**
     *the method to generate the method in the interface OnClickListenerEditOrDelete
     */
    public void setOnClickListenerEditOrDelete(OnClickListenerEditOrDelete onClickListenerEditOrDelete1){
        this.onClickListenerEditOrDelete=onClickListenerEditOrDelete1;
    }

}

