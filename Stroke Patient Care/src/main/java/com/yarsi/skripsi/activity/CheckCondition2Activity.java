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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yarsi.skripsi.R;

public class CheckCondition2Activity extends AppCompatActivity {

    private Button checkCondition2;
    private RadioGroup mMerokokRadioGroup, mAlkoholRadioGroup;
    private RadioButton mMerokokRadioButton, mAlkoholRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_condition2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkCondition2 = (Button) findViewById(R.id.check_condition2);

        mMerokokRadioGroup = (RadioGroup) findViewById(R.id.merokok);
        mAlkoholRadioGroup = (RadioGroup) findViewById(R.id.alkohol);

        // Login button Click Event
        checkCondition2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = getIntent();

                int selectedId1 = mMerokokRadioGroup.getCheckedRadioButtonId();
                mMerokokRadioButton = (RadioButton) findViewById(selectedId1);
                int selectedId2 = mAlkoholRadioGroup.getCheckedRadioButtonId();
                mAlkoholRadioButton = (RadioButton) findViewById(selectedId2);

                try {
                    Intent i = new Intent(getApplicationContext(),
                            CheckCondition4Activity.class);

                    i.putExtra("Merokok", mMerokokRadioButton.getText().toString());
                    i.putExtra("Alkohol", mAlkoholRadioButton.getText().toString());
                    i.putExtra("Height", intent.getStringExtra("Height"));
                    i.putExtra("Weight", intent.getStringExtra("Weight"));

                    startActivityForResult(i, 0);
                } catch(Exception e) {
                    showAlertDialog(view.getContext(), "Failed", "Isi semua field", false);
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
