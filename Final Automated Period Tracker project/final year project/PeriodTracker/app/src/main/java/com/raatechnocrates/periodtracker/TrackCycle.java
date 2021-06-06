package com.raatechnocrates.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TrackCycle extends AppCompatActivity {

    TextView tv_previous, tv_nextstart, tv_nextend, tv_days;

    Calendar st_dt;
    Calendar end_dt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_cycle);

        getSupportActionBar().setTitle("Track my Cycle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tv_previous = (TextView)findViewById(R.id.lblPreviousStartDate);
        tv_nextstart = (TextView)findViewById(R.id.lblNextStartDate);
        tv_nextend = (TextView)findViewById(R.id.lblNextEndDate);
        tv_days = (TextView)findViewById(R.id.lblNoOfDays);

        st_dt = Calendar.getInstance();
        end_dt = Calendar.getInstance();

        Bundle bundle = getIntent().getExtras();
        String previous = bundle.getString("previous");
        String next = bundle.getString("next");
        String days = bundle.getString("days");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(previous));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        tv_previous.setText(sdf1.format(c.getTime()));
        try {
            c.setTime(sdf.parse(next));
            st_dt.setTime(sdf.parse(next));
            end_dt.setTime(sdf.parse(next));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tv_nextstart.setText(sdf1.format(c.getTime()));
        c.add(Calendar.DATE, 4);  // number of days to add
        end_dt.add(Calendar.DATE, 4);  // number of days to add

        tv_nextend.setText(sdf1.format(c.getTime()));
        tv_days.setText(days);
    }

    public void savetocalendar_click(View v)
    {

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, st_dt.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end_dt.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Expected Period Dates");
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}