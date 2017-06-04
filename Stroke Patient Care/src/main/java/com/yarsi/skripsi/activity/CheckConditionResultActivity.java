package com.yarsi.skripsi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.yarsi.skripsi.R;

import java.text.DecimalFormat;

public class CheckConditionResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_condition_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        double i = 0;

        Intent intent = getIntent();

        String tekananDarah = intent.getStringExtra("TekananDarah");
        String diabetes = intent.getStringExtra("Diabetes");
        String penurunanTekananDarah = intent.getStringExtra("PenurunanTekananDarah");
        String stress = intent.getStringExtra("Stress");
        String makan = intent.getStringExtra("Makan");
        String aktivitas = intent.getStringExtra("Aktivitas");
        String merokok = intent.getStringExtra("Merokok");
        String alkohol = intent.getStringExtra("Alkohol");
        String height = intent.getStringExtra("Height");
        String weight = intent.getStringExtra("Weight");

        if (merokok.equals("Yes"))
            i += 1;
        if (alkohol.equals("Yes"))
            i += 1;
        if (aktivitas.equals("Yes"))
            i += 1;
        if (makan.equals("Yes"))
            i += 1;
        if (stress.equals("Yes"))
            i += 1;
        if (diabetes.equals("Yes"))
            i += 1;
        if (tekananDarah.equals("Yes"))
            i += 1;

        double total = i / 7;

        DecimalFormat df = new DecimalFormat("####0.00");

        TextView persen1Txt = (TextView) findViewById(R.id.persen1);
        TextView persen2Txt = (TextView) findViewById(R.id.persen2);
        TextView view1Txt = (TextView) findViewById(R.id.view1);
        TextView view2Txt = (TextView) findViewById(R.id.view2);
        TextView view3Txt = (TextView) findViewById(R.id.view3);

        persen1Txt.setText("" + df.format(total) + "%");
        persen2Txt.setText("" + df.format((total * 100)) + "%");

        view1Txt.setText("The risk of having a stroke for someone your age and sex with no risk factors is " + df.format(total) + "%" + " in 100 (" + df.format(total) + "%)");
        view2Txt.setText("Your risk of having a stroke is " + df.format((total * 100)) + " in 100 (" + df.format((total * 100)) + "%)");
        view3Txt.setText("Your absolute 5-years risk is " + df.format((total * 100)) + "% - this means your relative risk of stroke is approximately 11.00 times that of someone your age and sex without any contributing risk factors");

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
