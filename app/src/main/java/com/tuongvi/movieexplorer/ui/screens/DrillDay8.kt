package com.tuongvi.movieexplorer.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.request.Disposable

@Composable
fun Day08DrillScreen() {
    //test remember
//    var counter by remember { mutableStateOf(0) }
//    var isColorOn by remember { mutableStateOf(false) }
//    var nameInput by remember { mutableStateOf("") }
    //fix rememberSaveable
    var counter by rememberSaveable { mutableStateOf(0) }
    var isColorOn by rememberSaveable { mutableStateOf(false) }
    var nameInput by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        Log.d("ComposableLifecycle", "LaunchedEffect: screen entered")
    }

    DisposableEffect(Unit) {
        Log.d("ComposableLifecycle", "DisposableEffect: composable vừa hiển thị")
        onDispose {
            Log.d("ComposableLifecycle", "DisposableEffect: screen left")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Counter8(
            count = counter,
            onIncrement = { counter++ },
            onDecrement = { if (counter > 0) counter-- },
            onReset = { counter = 0 }
        )

        Toggle8(
            isOn = isColorOn,
            onToggle = { isColorOn = !isColorOn }
        )

        Greeting8(
            name = nameInput,
            onNameChange = { nameInput = it }
        )
    }
}

@Composable
fun Counter8(
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("1. Counter: $count", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onIncrement) { Text("+") }
            Button(onClick = onDecrement) { Text("-") }
            Button(onClick = onReset) { Text("Reset") }
        }
    }
}

@Composable
fun Toggle8(
    isOn: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("2. Toggle Button", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onToggle,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isOn) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        ) {
            Text(if (isOn) "ON" else "OFF", color = Color.White)
        }
    }
}

@Composable
fun Greeting8(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("3. Greeting Realtime", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nhập tên") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = if (name.isEmpty()) "Vui lòng nhập tên..." else "Xin chào, $name!",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}