package com.example.jeremy.simplemarcocounter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

//TODO Might be cool to implement a dataBase to store/keep track of all the Macro intry
//http://www.android-examples.com/use-addtextchangedlistener-in-edittext-android/
public class MainActivity extends AppCompatActivity {

    //Data&Values of said Keys
//    private double carbTotal;
//    private double proteinTotal;
//    private double fatTotal;
    Macros totalMacros;

    //Defining views --> save & get values into those views
    private EditText carbET;
    private EditText proteinET;
    private EditText fatET;

    //textViews
    private TextView carbTextView;
    private TextView proteinTextView;
    private TextView fatTextView;

    //Saving data
    //KEYS
    private String PREF_NAME;
    private String PREF_CARB;
    private String PREF_PROTEIN;
    private String PREF_FAT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalMacros = new Macros(0, 0, 0);

        //Iniliaztion of  SharedPrefs stuff
        PREF_NAME = getString(R.string.preference_name);
        PREF_CARB = getString(R.string.carb_preference_key);
        PREF_PROTEIN = getString(R.string.protein_preference_key);
        PREF_FAT = getString(R.string.fat_preference_key);

        SharedPreferences savedPref = getSharedPreferences(PREF_NAME, 0);

        //initializes them with saved data from file
        initializeTextViews(savedPref.getFloat(PREF_PROTEIN, 0), savedPref.getFloat(PREF_CARB, 0), savedPref.getFloat(PREF_FAT, 0));


        // Initialize ET
        carbET = (EditText) findViewById(R.id.carbEditText);
        proteinET = (EditText) findViewById(R.id.proteinEditText);
        fatET = (EditText) findViewById(R.id.fatEditText);


    }

    /**
     * Called on onCreate()
     * initializes the TextViews based on if there was a previously destroyed instance else the app was just started
     */
    public void initializeTextViews(double p, double c, double f) {
        carbTextView = (TextView) findViewById(R.id.carbTextView);
        proteinTextView = (TextView) findViewById(R.id.proteinTextView);
        fatTextView = (TextView) findViewById(R.id.fatTextView);

        totalMacros.setCarbs(c);
        totalMacros.setProtein(p);
        totalMacros.setFat(f);

        //Updating TextViews
        carbTextView.setText(Double.toString(totalMacros.getCarbs()));
        proteinTextView.setText(Double.toString(totalMacros.getProtein()));
        fatTextView.setText(Double.toString(totalMacros.getFat()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences savedPref = getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = savedPref.edit();
        editor.putFloat(PREF_CARB, (float) totalMacros.getCarbs());
        editor.putFloat(PREF_PROTEIN, (float) totalMacros.getProtein());
        editor.putFloat(PREF_FAT, (float) totalMacros.getFat());
        // Commit the edits!
        editor.apply();

    }

    public void clearAll(View view) {
        totalMacros.setAll(0);

        //Re-initializing/which fixes some error
        carbTextView = (TextView) findViewById(R.id.carbTextView);
        proteinTextView = (TextView) findViewById(R.id.proteinTextView);
        fatTextView = (TextView) findViewById(R.id.fatTextView);

        //updating
        carbTextView.setText(Double.toString(0));
        proteinTextView.setText(Double.toString(0));
        fatTextView.setText(Double.toString(0));
    }

    public void addMarcos(View view) {
        //adding Protein and carbs
        addToCarb();
        addToProtein();
        addToFat();
    }

    public void addToFat() {
        String fatValue = fatET.getText().toString();

        if (!fatValue.equals("")) {
            Double finalValue = Double.parseDouble(fatValue);
            totalMacros.addFat(finalValue);

            //displays new total
            TextView textView = (TextView) findViewById(R.id.fatTextView);
            textView.setText(Double.toString(totalMacros.getFat()));

            fatET.setText("");
        }

    }

    public void addToProtein() {
        String pValue = proteinET.getText().toString();

        if (!pValue.equals("")) {
            Double finalValue = Double.parseDouble(pValue);
            totalMacros.addProtein(finalValue);

            //Display updated on TextView
            TextView textView = (TextView) findViewById(R.id.proteinTextView);
            textView.setText(Double.toString(totalMacros.getProtein()));

            proteinET.setText("");
        }
    }

    public void addToCarb() {
        String cValue = carbET.getText().toString();

        if (!cValue.equals("")) {
            Double finalValue = Double.parseDouble(cValue);
            totalMacros.addCarbs(finalValue);

            TextView textView = (TextView) findViewById(R.id.carbTextView);
            textView.setText(Double.toString(totalMacros.getCarbs()));

            carbET.setText("");
        }
    }

//    OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(MainActivity.this){
//        @Override
//        public void onSwipeLeft(){
//            //fatET.setText("101");
//
//        }
//
//    };


}