package com.tuongvi.movieexplorer

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuongvi.movieexplorer.viewmodel.CounterViewModel

@Composable
fun Day06DrillScreen(
    viewModel: CounterViewModel = viewModel()
) {
    val counter by viewModel.count.collectAsStateWithLifecycle()
    //var counter by remember { mutableStateOf(0) }
    var isColorOn by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Counter6(
            count = counter,
            onIncrement = { viewModel.increment() },
            onDecrement = { viewModel.decrement() },
            onReset = { viewModel.reset()}
        )

        Toggle6(
            isOn = isColorOn,
            onToggle = { isColorOn = !isColorOn }
        )

        Greeting6(
            name = nameInput,
            onNameChange = { nameInput = it }
        )
    }
}

@Composable
fun Counter6(
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
fun Toggle6(
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
fun Greeting6(
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