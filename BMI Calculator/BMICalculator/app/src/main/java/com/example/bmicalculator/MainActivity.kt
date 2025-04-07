package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bmicalculator.ui.theme.BMICalculatorTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMICalculatorApp()
        }
    }
}


@Composable
fun BMICalculatorScreen() {
    var weightInput by rememberSaveable { mutableStateOf("") }
    var heightInput by rememberSaveable { mutableStateOf("") }
    var bmiResult by rememberSaveable { mutableStateOf("") }
    var interpretation by rememberSaveable { mutableStateOf("") }

    val isValidInput = weightInput.toFloatOrNull() != null && heightInput.toFloatOrNull() != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("BMI Calculator", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = weightInput,
            onValueChange = { weightInput = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = heightInput,
            onValueChange = { heightInput = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                val weight = weightInput.toFloat()
                val heightInMeters = heightInput.toFloat() / 100
                val bmi = weight / (heightInMeters * heightInMeters)
                bmiResult = "Your BMI is %.2f".format(bmi)
                interpretation = interpretBMI(bmi)
            },
            enabled = isValidInput
        ) {
            Text("Calculate BMI")
        }

        if (bmiResult.isNotEmpty()) {
            Text(bmiResult, style = MaterialTheme.typography.titleMedium)
            Text(interpretation, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

fun interpretBMI(bmi: Float): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi < 24.9 -> "Normal"
        bmi < 29.9 -> "Overweight"
        else -> "Obese"
    }
}

@Composable
fun BMICalculatorApp() {
    BMICalculatorTheme {
        BMICalculatorScreen()
    }
}



@Preview(showBackground = true)
@Composable
fun BMICalculatorPreview() {
    BMICalculatorTheme {
        BMICalculatorScreen()
    }
}
