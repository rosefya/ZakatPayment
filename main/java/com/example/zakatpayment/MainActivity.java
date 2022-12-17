package com.example.zakatpayment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button calculate, clear;
    EditText weight, currentvalue;
    RadioButton keep, wear;
    TextView totalval, totalgold, totalzakat;
    RadioGroup radioGroup;
    float outweight, outcurrentvalue, goldtype, outval, outgold, outzakat;


    String Shared_pref = "sharedPrefs";
    String gold_weight = "weight";
    String gold_value = "currentvalue";
    private Bundle savedInstanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = (EditText) findViewById(R.id.weight);
        currentvalue = (EditText) findViewById(R.id.currentvalue);
        keep = (RadioButton) findViewById(R.id.keep);
        wear = (RadioButton) findViewById(R.id.wear);
        calculate = (Button) findViewById(R.id.calculate);
        clear = (Button) findViewById(R.id.clear);
        totalval = (TextView) findViewById(R.id.totalval);
        totalgold = (TextView) findViewById(R.id.totalgold);
        totalzakat = (TextView) findViewById(R.id.totalzakat);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        boolean keepChecked = keep.isChecked();
        boolean wearChecked = wear.isChecked();
        calculate.setOnClickListener(this);
        clear.setOnClickListener(this);

        calculate.setBackgroundColor(Color.BLACK);
        clear.setBackgroundColor(Color.BLACK);

        SharedPreferences sharedPreferences = getSharedPreferences(Shared_pref, MODE_PRIVATE);
        String weightgold = sharedPreferences.getString(gold_weight, "");
        weight.setText(weightgold);
        String goldvalue = sharedPreferences.getString(gold_value, "");
        currentvalue.setText(goldvalue);

    }

    @Override
    public void onClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(Shared_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(gold_weight, weight.getText().toString());
        editor.putString(gold_value, currentvalue.getText().toString());
        editor.apply();

        //Toast.makeText(this, "Data Saved", Toast.LENGTH_LONG).show();

        switch (view.getId()) {

            case R.id.calculate:
                CalculateOutput();
                break;

            case R.id.clear:
                Clear();
                Clearbutton();
                break;
        }
    }

    public void CalculateOutput() {
        try {
            outweight = Float.parseFloat(weight.getText().toString());
            if (keep.isChecked())
                goldtype = 85;
            else if (wear.isChecked())
                goldtype = 200;
            outcurrentvalue = Float.parseFloat(currentvalue.getText().toString());
            outval = outweight * outcurrentvalue;
            outgold = (outweight - goldtype) * outcurrentvalue;
            outzakat = (float) (outgold * 0.025);

            totalval.setText("The total value of the gold is " + String.format("RM%.2f", outval));
            totalgold.setText("Total gold value that is zakat payable is " + String.format("RM%.2f", outgold));
            totalzakat.setText("The total zakat is " + String.format("RM%.2f", outzakat));

        } catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }

    public void Clear() {
        weight.setText("");
        currentvalue.setText("");
        totalval.setText("The total value of the gold");
        totalgold.setText("Total gold value that is payable");
        totalzakat.setText("Total zakat");
    }

    public void Clearbutton() {
        //keep.setChecked(false);
        //wear.setChecked(false);
        radioGroup.clearCheck();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
                case R.id.home:
                //Toast.makeText(this, "This is home", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;

                case R.id.about:
                    //Toast.makeText(this, "This is about", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, AboutActivity.class);
                    startActivity(intent);
                    break;
    }
return super.onOptionsItemSelected(item);
}
}
