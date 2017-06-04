package com.yarsi.skripsi.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yarsi.skripsi.R;

public class CheckCondition1Activity extends AppCompatActivity {

    private Button checkCondition1;
    private EditText mHeightTxt, mWeightTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_condition1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkCondition1 = (Button) findViewById(R.id.check_condition1);
        mHeightTxt = (EditText) findViewById(R.id.height);
        mWeightTxt = (EditText) findViewById(R.id.weight);

        // Login button Click Event
        checkCondition1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (mHeightTxt.getText().length() == 0 || mWeightTxt.getText().length() == 0) {
                    showAlertDialog(view.getContext(), "Failed", "Isi semua field", false);
                } else {
                    Intent i = new Intent(getApplicationContext(),
                            CheckCondition2Activity.class);
                    i.putExtra("Height", mHeightTxt.getText().toString());
                    i.putExtra("Weight", mWeightTxt.getText().toString());
                    startActivityForResult(i, 0);
                }
            }
        });
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
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
}
