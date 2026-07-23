# Laraib_Task1 - Unit Converter Application

## Objective
An Android application built using Java and XML that converts values between common units of measurement based on user input.

## Tech Stack
- Android Studio
- Java
- XML

## Features
- Category selector (Spinner) to choose between Length, Weight, and Volume
- Input field to enter the numeric value
- From Unit and To Unit spinners to select source and target units
- Convert button that computes and displays the result
- Result displayed in a styled TextView with proper unit label
- Input validation:
  - Shows a Toast message "Please enter a value" if the input field is empty
  - Shows a Toast message "Enter a valid number" if the input is non-numeric
- Category dropdown dynamically updates the From/To unit spinners when switched

## Supported Categories & Units
**Length:** Centimetres, Metres, Kilometres, Inches, Feet
**Weight:** Grams, Kilograms, Pounds, Ounces
**Volume:** Millilitres, Litres, Cubic Centimetres

## How It Works
1. User selects a measurement category (Length / Weight / Volume)
2. User enters a numeric value
3. User selects the "From Unit" and "To Unit"
4. User taps the Convert button
5. The app validates the input and displays the converted result

## How to Run
1. Clone this repository
2. Open the project in Android Studio
3. Let Gradle sync complete
4. Run the app on an emulator or a physical Android device

## Author
  Laraib Tariq
