package com.yarsi.skripsi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.yarsi.skripsi.R;

public class FAST3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmButtonYes(View view) {
        Intent intent = getIntent();
        Intent i = new Intent(getApplicationContext(),
                FAST4Activity.class);
        i.putExtra("FAST1", intent.getStringExtra("FAST1"));
        i.putExtra("FAST2", intent.getStringExtra("FAST2"));
        i.putExtra("FAST3", "Yes");
        startActivityForResult(i, 0);
    }

    public void confirmButtonNo(View view) {
        Intent intent = getIntent();
        Intent i = new Intent(getApplicationContext(),
                FAST4Activity.class);
        i.putExtra("FAST1", intent.getStringExtra("FAST1"));
        i.putExtra("FAST2", intent.getStringExtra("FAST2"));
        i.putExtra("FAST3", "No");
        startActivityForResult(i, 0);
    }
}
