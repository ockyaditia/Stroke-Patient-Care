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

public class CheckCondition5Activity extends AppCompatActivity {

    private Button checkCondition5;
    private RadioGroup mTekananDarahRadioGroup, mDiabetesRadioGroup, mPenurunanTekananDarahRadioGroup;
    private RadioButton mTekananDarahRadioButton, mDiabetesRadioButton, mPenurunanTekananDarahRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_condition5);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkCondition5 = (Button) findViewById(R.id.check_condition2);

        mTekananDarahRadioGroup = (RadioGroup) findViewById(R.id.tekanan_darah);
        mDiabetesRadioGroup = (RadioGroup) findViewById(R.id.diabetes);
        mPenurunanTekananDarahRadioGroup = (RadioGroup) findViewById(R.id.penurun_tekanan_darah);

        // Login button Click Even
        checkCondition5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = getIntent();

                int selectedId1 = mTekananDarahRadioGroup.getCheckedRadioButtonId();
                mTekananDarahRadioButton = (RadioButton) findViewById(selectedId1);
                int selectedId2 = mDiabetesRadioGroup.getCheckedRadioButtonId();
                mDiabetesRadioButton = (RadioButton) findViewById(selectedId2);
                int selectedId3 = mPenurunanTekananDarahRadioGroup.getCheckedRadioButtonId();
                mPenurunanTekananDarahRadioButton = (RadioButton) findViewById(selectedId3);

                try {
                    Intent i = new Intent(getApplicationContext(),
                            CheckCondition3Activity.class);

                    i.putExtra("TekananDarah", mTekananDarahRadioButton.getText().toString());
                    i.putExtra("Diabetes", mDiabetesRadioButton.getText().toString());
                    i.putExtra("PenurunanTekananDarah", mPenurunanTekananDarahRadioButton.getText().toString());
                    i.putExtra("Stress", intent.getStringExtra("Stress"));
                    i.putExtra("Makan", intent.getStringExtra("Makan"));
                    i.putExtra("Aktivitas", intent.getStringExtra("Aktivitas"));
                    i.putExtra("Merokok", intent.getStringExtra("Merokok"));
                    i.putExtra("Alkohol", intent.getStringExtra("Alkohol"));
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
