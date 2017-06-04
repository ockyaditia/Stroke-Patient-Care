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

import com.yarsi.skripsi.R;

public class CheckCondition3Activity extends AppCompatActivity {
    private Button checkCondition3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_condition3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkCondition3 = (Button) findViewById(R.id.check_condition3);

        checkCondition3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = getIntent();

                Intent i = new Intent(getApplicationContext(),
                        CheckConditionResultActivity.class);

                i.putExtra("TekananDarah", intent.getStringExtra("TekananDarah"));
                i.putExtra("Diabetes", intent.getStringExtra("Diabetes"));
                i.putExtra("PenurunanTekananDarah", intent.getStringExtra("PenurunanTekananDarah"));
                i.putExtra("Stress", intent.getStringExtra("Stress"));
                i.putExtra("Makan", intent.getStringExtra("Makan"));
                i.putExtra("Aktivitas", intent.getStringExtra("Aktivitas"));
                i.putExtra("Merokok", intent.getStringExtra("Merokok"));
                i.putExtra("Alkohol", intent.getStringExtra("Alkohol"));
                i.putExtra("Height", intent.getStringExtra("Height"));
                i.putExtra("Weight", intent.getStringExtra("Weight"));

                startActivityForResult(i, 0);
//                if (mHeightTxt.getText().length() == 0 || mWeightTxt.getText().length() == 0) {
//                    showAlertDialog(view.getContext(), "Failed", "Isi semua field", false);
//                } else {
//                    Intent i = new Intent(getApplicationContext(),
//                            CheckCondition2Activity.class);
//                    i.putExtra("Height", mHeightTxt.getText());
//                    i.putExtra("Weight", mWeightTxt.getText());
//                    startActivity(i);
//                    finish();
//                }
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
