package com.example.jeremy.simplemarcocounter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.*;


public class foodListActivity extends AppCompatActivity {
    private EditText foodNameET;
    private EditText carbET;
    private EditText proteinET;
    private EditText fatET;
    private ListView listView;

    //KEY for saving data
    private String FOODLIST_PREFNAME; //used to get the file(sharedPref) where things are saved
    private String FOODLIST_KEY;      //used to get the acual list of values of food

    Set<Macros> list;
    ArrayList<String> foodn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        FOODLIST_PREFNAME = getString(R.string.preference_name_A2);
        FOODLIST_KEY = getString(R.string.food_list_key);
        SharedPreferences savedPref = getSharedPreferences(FOODLIST_PREFNAME, 0);

        list = new HashSet<Macros>();
        Set<String> savedSet = savedPref.getStringSet(FOODLIST_KEY, null);
        if (savedSet != null)
            foodn = new ArrayList<String>(savedSet);
        else
            foodn = new ArrayList<String>();
        updateListView();

        foodNameET = (EditText) findViewById(R.id.FoodNameEditText);
        carbET = (EditText) findViewById(R.id.carbEditTextA2);
        proteinET = (EditText) findViewById(R.id.proteinEditTextA2);
        fatET = (EditText) findViewById(R.id.fatEditTextA2);
        listView = (ListView) findViewById(R.id.foodListListView);

    }


    public void onSubmit(View view) {
        String foodName = foodNameET.getText().toString();
        String carbAmount = carbET.getText().toString();
        String proteinAmount = proteinET.getText().toString();
        String fatAmount = fatET.getText().toString();

        //if nothing was entered in default it to 0
        if (carbAmount.equals("")) carbAmount = "0";
        if (proteinAmount.equals("")) proteinAmount = "0";
        if (fatAmount.equals("")) fatAmount = "0";

        double carb = makeDouble(carbAmount);
        double protein = makeDouble(proteinAmount);
        double fat = makeDouble(fatAmount);

        Macros newFood = new Macros(carb, protein, fat, foodName);
        list.add(newFood);
        foodn.add(foodName);

        updateListView();
        clearEditTexts();
        printToast("Submitted");

    }

    public void updateListView() {
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodn);
        ListView A2ListView = (ListView) findViewById(R.id.foodListListView);
        A2ListView.setAdapter(listAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        save();

    }

    public double makeDouble(String string) {
        double d = Double.parseDouble(string);
        return d;
    }

    public void printToast(String text) {
        Context context = getApplicationContext();
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void clearEditTexts() {
        foodNameET.setText("");
        carbET.setText("");
        proteinET.setText("");
        fatET.setText("");

    }

    public void save() {
        Set<String> saveSet = new HashSet<String>(foodn);
        SharedPreferences savedPref = getSharedPreferences(FOODLIST_PREFNAME, 0);
        SharedPreferences.Editor editor = savedPref.edit();
        editor.putStringSet(FOODLIST_KEY, saveSet);
        editor.apply();
    }
}
