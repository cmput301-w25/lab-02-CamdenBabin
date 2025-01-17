package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //initialize views
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addCityButton, deleteCityButton, confirmAddButton;
    EditText cityInput;
    FrameLayout addCityInputView;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing views
        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city_button);
        deleteCityButton = findViewById(R.id.delete_city_button);
        confirmAddButton = findViewById(R.id.confirm_add_button);
        cityInput = findViewById(R.id.city_input);
        addCityInputView = findViewById(R.id.add_city_input_view);

        // Initialize data list
        String[] cities = {"Edmonton", "Sudbury", "London", "Vancouver"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // Set up the adapter with a custom item layout
        cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                // Highlight the selected item
                if (position == selectedPosition) {
                    view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));  // Darkened background
                } else {
                    view.setBackgroundColor(getResources().getColor(android.R.color.transparent));  // Transparent background
                }

                return view;
            }
        };

        // Set the adapter to the ListView
        cityList.setAdapter(cityAdapter);

        // Set the ListView to have no choice mode (no checkboxes)
        cityList.setChoiceMode(ListView.CHOICE_MODE_NONE);

        // Handle ListItem click to select and highlight the item
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityAdapter.notifyDataSetChanged();
        });

        // Add City Button - Show the input view
        addCityButton.setOnClickListener(v -> {
            addCityInputView.setVisibility(View.VISIBLE);  // Show the input view
        });

        // Confirm Add Button - Add the city and hide the input view
        confirmAddButton.setOnClickListener(v -> {
            String newCity = cityInput.getText().toString().trim();

            if (!newCity.isEmpty()) {
                dataList.add(newCity);  // Add city to the list
                cityAdapter.notifyDataSetChanged();  // Update the ListView
                cityInput.setText("");  // Clear the EditText

                addCityInputView.setVisibility(View.GONE);  // Hide the input view again
            } else {
                Toast.makeText(MainActivity.this, "Please enter a valid city", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete City Button - Remove the selected city
        deleteCityButton.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                dataList.remove(selectedPosition);  // Remove city from the list
                cityAdapter.notifyDataSetChanged();  // Update the ListView
                selectedPosition = -1;  // Reset the selected position
                Toast.makeText(MainActivity.this, "City removed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please select a city to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}