package com.tuongvi.movieexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tuongvi.movieexplorer.ui.theme.MovieExplorerTheme
import com.tuongvi.movieexplorer.model.Movie
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        grade(7)
        grade(15)

        safeLength("Hello")
        safeLength(null)

        fizzbuzz()
        setContent { Day03DrillScreen() }

    }
}


@Composable
fun MovieCard(movie: Movie){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(50))
                .background(if (movie.voteAverage >= 7.0) Color(0xFF4CAF50) else Color(0xFFFF9800)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = movie.voteAverage.toString() + "/10",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/// drill day 03
@Composable
fun Day03DrillScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        var counter by remember { mutableStateOf(0) }

        Column {
            Text("1. Counter: $counter", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { counter++ }) { Text("+") }
                Button(onClick = { if (counter > 0) counter-- }) { Text("–") }
                Button(onClick = { counter = 0 }) { Text("Reset") }
            }
        }


        var isColorOn by remember { mutableStateOf(false) }

        Column {
            Text("2. Toggle Button", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = { isColorOn = !isColorOn },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isColorOn) Color.Green else Color.Red
                )
            ) {
                Text(if (isColorOn) "ON" else "OFF", color = Color.White)
            }
        }

        var nameInput by remember { mutableStateOf("") }

        Column {
            Text("3. Greeting Realtime", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Nhập tên") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (nameInput.isEmpty()) "Vui lòng nhập tên..." else "Xin chào, $nameInput!",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieCardPreview() {
    val sampleMovie = Movie(
        id = 1,
        title = "Interstellar (2014)",
        overview = "Chuyện phim Interstellar kể về một nhóm nhà thám hiểm không gian sử dụng một hố đen mới được khám phá để vượt qua giới hạn du hành không gian thông thường của con người.",
        posterPath = "",
        voteAverage = 8.6,
        releaseDate = "1/1/2026"
    )
    MovieCard(movie = sampleMovie)
}


