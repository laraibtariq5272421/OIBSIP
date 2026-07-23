package com.example.unitconverter;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCategory, spinnerFromUnit, spinnerToUnit;
    EditText etValue;
    Button btnConvert;
    TextView tvResult;

    // Units in sequence: smallest to largest
    String[] lengthUnits = {"Centimetres (cm)", "Inches (in)", "Feet (ft)", "Metres (m)", "Kilometres (km)"};
    String[] weightUnits = {"Grams (g)", "Ounces (oz)", "Pounds (lb)", "Kilograms (kg)"};
    String[] volumeUnits = {"Millilitres (ml)", "Cubic cm (cm3)", "Litres (l)"};

    // Factors matching the same sequence (converts to base unit)
    double[] lengthFactors = {1, 2.54, 30.48, 100, 100000};
    double[] weightFactors = {1, 28.3495, 453.592, 1000};
    double[] volumeFactors = {1, 1, 1000};

    String[] categories = {"Length", "Weight", "Volume"};
    String currentCategory = "Length";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        etValue = findViewById(R.id.etValue);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        updateUnitSpinners("Length");

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentCategory = categories[position];
                updateUnitSpinners(currentCategory);
                tvResult.setText("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertValue();
            }
        });

        // Button press color-change effect
        btnConvert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start();
                    v.setBackgroundColor(getResources().getColor(R.color.teal_dark));
                    v.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY);
                } else if (event.getAction() == MotionEvent.ACTION_UP ||
                        event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    v.setBackgroundColor(getResources().getColor(R.color.teal_primary));
                }
                return false;
            }
        });
    }

    private void updateUnitSpinners(String category) {
        String[] units;
        if (category.equals("Length")) {
            units = lengthUnits;
        } else if (category.equals("Weight")) {
            units = weightUnits;
        } else {
            units = volumeUnits;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(adapter);
        spinnerToUnit.setAdapter(adapter);
    }

    private void convertValue() {
        String input = etValue.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value;
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        int fromIndex = spinnerFromUnit.getSelectedItemPosition();
        int toIndex = spinnerToUnit.getSelectedItemPosition();

        double[] factors;
        String[] units;

        if (currentCategory.equals("Length")) {
            factors = lengthFactors;
            units = lengthUnits;
        } else if (currentCategory.equals("Weight")) {
            factors = weightFactors;
            units = weightUnits;
        } else {
            factors = volumeFactors;
            units = volumeUnits;
        }

        double baseValue = value * factors[fromIndex];
        double result = baseValue / factors[toIndex];

        String resultText = String.format("%.2f %s", result, getShortUnit(units[toIndex]));
        tvResult.setText(resultText);
    }

    private String getShortUnit(String fullUnitName) {
        int start = fullUnitName.indexOf("(");
        int end = fullUnitName.indexOf(")");
        if (start != -1 && end != -1) {
            return fullUnitName.substring(start + 1, end);
        }
        return fullUnitName;
    }
}