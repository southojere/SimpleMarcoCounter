package com.example.jeremy.simplemarcocounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

//http://www.android-examples.com/use-addtextchangedlistener-in-edittext-android/
public class MainActivity extends AppCompatActivity {
    //Save data if exited, keys
    private String CARB_TOTAL = "carb_total";
    private static final String PROTEIN_TOTAL = "protein_total";
    private static final String FAT_TOTAL = "fat_total";

    //Data&Values of said Keys
    private double carbTotal;
    private double proteinTotal;
    private double fatTotal;

    //Defining views --> save & get values into those views
    private EditText carbET;
    private EditText proteinET;
    private EditText fatET;

    //textViews
    private TextView carbTextView;
    private TextView proteinTextView;
    private TextView fatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) { //if this activity has been previously destroyed and restarted --> get old state info
            carbTotal = savedInstanceState.getDouble(CARB_TOTAL);
            proteinTotal = savedInstanceState.getDouble(PROTEIN_TOTAL);
            fatTotal = savedInstanceState.getDouble(FAT_TOTAL);

            //Updating TextViews

            carbTextView = (TextView) findViewById(R.id.carbTextView);
            proteinTextView = (TextView) findViewById(R.id.proteinTextView);
            fatTextView = (TextView) findViewById(R.id.fatTextView);

            carbTextView.setText(Double.toString(carbTotal));
            proteinTextView.setText(Double.toString(proteinTotal));
            fatTextView.setText(Double.toString(fatTotal));

        } else {
            //just started
            carbTotal = 0.0;
            proteinTotal = 0.0;
            fatTotal = 0.0;

            carbTextView = (TextView) findViewById(R.id.carbTextView);
            proteinTextView = (TextView) findViewById(R.id.proteinTextView);
            fatTextView = (TextView) findViewById(R.id.fatTextView);

            carbTextView.setText(Double.toString(0));
            proteinTextView.setText(Double.toString(0));
            fatTextView.setText(Double.toString(0));

        }
        // Initialize ET
        carbET = (EditText) findViewById(R.id.carbEditText);
        proteinET = (EditText) findViewById(R.id.proteinEditText);
        fatET = (EditText) findViewById(R.id.fatEditText);

    }


    //Saving Additional state info that the user would expect to be present when they navigate back to your activity.
    //Make sure to override super.

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putDouble(CARB_TOTAL, carbTotal);
        savedInstanceState.putDouble(PROTEIN_TOTAL, proteinTotal);
        savedInstanceState.putDouble(FAT_TOTAL, fatTotal);


        //always call on super class
        super.onSaveInstanceState(savedInstanceState);

    }

    public void clearAll(View view) {

        carbTotal = fatTotal = proteinTotal = 0;

        //Re-set/get which fixes some error
        carbTextView = (TextView) findViewById(R.id.carbTextView);
        proteinTextView = (TextView) findViewById(R.id.proteinTextView);
        fatTextView = (TextView) findViewById(R.id.fatTextView);

        //updating
        carbTextView.setText(Double.toString(carbTotal));
        proteinTextView.setText(Double.toString(proteinTotal));
        fatTextView.setText(Double.toString(fatTotal));
    }

    public void addMarcos(View view) {
        String fatValue = fatET.getText().toString();

        addToCarb();
        addToProtein();
        if (!fatValue.equals("")) {
            Double finalValue = Double.parseDouble(fatValue);
            fatTotal += finalValue;

            //displays new total
            TextView textView = (TextView) findViewById(R.id.fatTextView);
            textView.setText(Double.toString(fatTotal));

            fatET.setText("");
        }

          carbTextView.setText(Double.toString(carbTotal));

    }

    public void addToProtein() {
        String pValue = proteinET.getText().toString();

        if (!pValue.equals("")) {
            Double finalValue = Double.parseDouble(pValue);
            proteinTotal += finalValue;

            //Display updated on TextView
            TextView textView = (TextView) findViewById(R.id.proteinTextView);
            textView.setText(Double.toString(proteinTotal));

            proteinET.setText("");
        }
    }

    public void addToCarb() {
        String cValue = carbET.getText().toString();

        if (!cValue.equals("")) {
            Double finalValue = Double.parseDouble(cValue);
            carbTotal += finalValue;

            TextView textView = (TextView) findViewById(R.id.carbTextView);
            textView.setText(Double.toString(carbTotal));

            carbET.setText("");
        }
    }

}