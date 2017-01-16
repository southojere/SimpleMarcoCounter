package com.example.jeremy.simplemarcocounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.*;


public class foodListActivity extends AppCompatActivity {
    private EditText foodNameET;
    private EditText carbET;
    private EditText proteinET;
    private EditText fatET;
    private ListView myListView;

    //KEY for saving data
    private String FOODLIST_PREFNAME; //used to get the file(sharedPref) where things are saved
    private String FOODLIST_KEY;      //used to get the acual list of values of food

    //Key values for Macros addition from FoodList -to-> MainActivity

    private final static String CARB_FROM_MEAL = "CARB_ADDED_FROM_FOOD_LIST_CLASS";
    private final static String PROTEIN_FROM_MEAL = "PROTEIN_ADDED_FROM_FOOD_LIST_CLASS";
    private final static String FAT_FROM_MEAL = "FAT_ADDED_FROM_FOOD_LIST_CLASS";

    Set<Macros> list;
    ArrayList<String> foodListListView;

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
            foodListListView = new ArrayList<String>(savedSet);
        else
            foodListListView = new ArrayList<String>();
        updateListView();

        foodNameET = (EditText) findViewById(R.id.FoodNameEditText);
        carbET = (EditText) findViewById(R.id.carbEditTextA2);
        proteinET = (EditText) findViewById(R.id.proteinEditTextA2);
        fatET = (EditText) findViewById(R.id.fatEditTextA2);
        myListView = (ListView) findViewById(R.id.foodListListView);

        //listView functionality/setting up Listener
        ListAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodListListView);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(position));

                        double carbs=0;
                        double protein=0;
                        double fat=0;
                        /**
                         * Creates an Intent which will add the selected food to the Macro count in the MainActivity.
                         * */
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        Scanner sc = new Scanner(item);

                        sc.next();//Skipping Name
                        carbs = sc.nextInt();
                        protein = sc.nextInt();
                        fat = sc.nextInt();

                        intent.putExtra(CARB_FROM_MEAL, carbs);
                        intent.putExtra(PROTEIN_FROM_MEAL, protein);
                        intent.putExtra(FAT_FROM_MEAL, fat);
                        startActivity(intent);
                        printToast("Added");
                    }
                }
        );

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

        String prevStringsCombined = foodName + " " + carbAmount + " " + proteinAmount + " " + fatAmount;

        double carb = makeDouble(carbAmount);
        double protein = makeDouble(proteinAmount);
        double fat = makeDouble(fatAmount);

        Macros newFood = new Macros(carb, protein, fat, foodName);
        list.add(newFood);
        foodListListView.add(prevStringsCombined);

        updateListView();
        clearEditTexts();
        printToast("Submitted");

    }

    public void updateListView() {
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foodListListView);
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
        Set<String> saveSet = new HashSet<String>(foodListListView);
        SharedPreferences savedPref = getSharedPreferences(FOODLIST_PREFNAME, 0);
        SharedPreferences.Editor editor = savedPref.edit();
        editor.putStringSet(FOODLIST_KEY, saveSet);
        editor.apply();
    }

    public void clearFoodList(View view) {
        foodListListView.clear();
        updateListView();

    }
}
