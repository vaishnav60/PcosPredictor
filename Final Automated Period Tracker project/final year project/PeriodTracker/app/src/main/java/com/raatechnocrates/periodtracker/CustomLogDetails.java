package com.raatechnocrates.periodtracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomLogDetails extends ArrayAdapter {

    private final Activity context;
    private final String[] date;
    private final String[] A1;
    private final String[] A2;
    private final String[] status;

    public CustomLogDetails(Activity context, String [] date,String [] A1,String [] A2,String [] status){
        super(context, R.layout.list_view_log,date);
        this.context = context;
        this.date=date;
        this.A1=A1;
        this.A2=A2;
        this.status=status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_view_log, null, true);

        TextView txtDate= (TextView) rowView.findViewById(R.id.txtDate);
        TextView txtA1= (TextView) rowView.findViewById(R.id.A1);
        TextView txtA2= (TextView) rowView.findViewById(R.id.A2);
        TextView txtStatus= (TextView) rowView.findViewById(R.id.txtStatus);

        txtDate.setText(date[position]);
        txtA1.setText(A1[position]);
        txtA2.setText(A2[position]);
        txtStatus.setText("Status: "+ status[position]);

        return rowView;
    }
}
