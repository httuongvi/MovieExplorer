package com.tuongvi.movieexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.tuongvi.movieexplorer.model.Movie
import com.tuongvi.movieexplorer.ui.theme.MovieExplorerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        grade(7)
        grade(15)

        safeLength("Hello")
        safeLength(null)

        fizzbuzz()
        setContent {
            MovieExplorerTheme {
                //MovieListScreen()
                //ListFoodScreen()
                AppNavigation()
                //Navigation()
                //Day06DrillScreen()
            }
        }

    }
}





@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = onClick

        ) {
        Row(
            modifier = Modifier.fillMaxWidth() .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = "Ảnh poster",
                placeholder = painterResource(R.drawable.ic_error),
                error = painterResource(R.drawable.ic_error),
                modifier = Modifier.width(90.dp).height(135.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.width(60.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.voteAverage.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "rating",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}




val sampleMovies = listOf(
    Movie(
        id = 1,
        title = "Interstellar",
        overview = "Một nhóm các nhà thám hiểm du hành qua hố đen để tìm ngôi nhà mới cho nhân loại.",
        posterPath = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
        voteAverage = 8.4,
        releaseDate = "2014-11-07"
    ),
    Movie(
        id = 2,
        title = "Inception",
        overview = "Một tên trộm chuyên đánh cắp bí mật trong tiềm thức nhận nhiệm vụ gieo ý tưởng vào đầu mục tiêu.",
        posterPath = "/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
        voteAverage = 8.4,
        releaseDate = "2010-07-16"
    ),
    Movie(
        id = 3,
        title = "The Dark Knight",
        overview = "Batman đối đầu với Joker, kẻ muốn gieo rắc sự hỗn loạn tại Gotham.",
        posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        voteAverage = 8.5,
        releaseDate = "2008-07-18"
    ),
    Movie(
        id = 4,
        title = "Avatar: The Way of Water",
        overview = "Gia đình Jake Sully phải chiến đấu để bảo vệ Pandora trước những kẻ xâm lược.",
        posterPath = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
        voteAverage = 7.6,
        releaseDate = "2022-12-16"
    ),
    Movie(
        id = 5,
        title = "Spider-Man: No Way Home",
        overview = "Peter Parker tìm đến Doctor Strange để khôi phục danh tính bí mật của mình.",
        posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        voteAverage = 8.0,
        releaseDate = "2021-12-17"
    ),
    Movie(
        id = 6,
        title = "Dune",
        overview = "Paul Atreides cùng gia đình đến hành tinh Arrakis, nơi chứa nguồn tài nguyên quý giá nhất vũ trụ.",
        posterPath = "/d5NXSklXo0qyIYkgV94XAgMIckC.jpg",
        voteAverage = 7.8,
        releaseDate = "2021-10-22"
    ),
    Movie(
        id = 7,
        title = "Oppenheimer",
        overview = "Câu chuyện về J. Robert Oppenheimer và quá trình phát triển bom nguyên tử.",
        posterPath = "/ptpr0kGAckfQkJeJIt8st5dglvd.jpg",
        voteAverage = 8.1,
        releaseDate = "2023-07-21"
    ),
    Movie(
        id = 8,
        title = "The Shawshank Redemption",
        overview = "Một người đàn ông vô tội bị kết án tù chung thân và tìm thấy hy vọng sau song sắt.",
        posterPath = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
        voteAverage = 8.7,
        releaseDate = "1994-09-23"
    ),
    Movie(
        id = 9,
        title = "The Godfather",
        overview = "Gia đình mafia Corleone đối mặt với những biến động quyền lực và phản bội.",
        posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
        voteAverage = 8.7,
        releaseDate = "1972-03-24"
    ),
    Movie(
        id = 10,
        title = "Spirited Away",
        overview = "Chihiro lạc vào thế giới linh hồn và phải tìm cách cứu cha mẹ mình.",
        posterPath = "/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
        voteAverage = 8.5,
        releaseDate = "2001-07-20"
    )
)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieListScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MovieListViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Movie Explorer",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Làm mới danh sách"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center

        ){
            when (val state = uiState){
                is MovieListUiState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                is MovieListUiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = 0.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.movies,
                            key = { movie -> movie.id }
                        ) { movie ->
                            MovieCard(movie, onClick = {onMovieClick(movie.id)})
                        }
                    }
                }
                is MovieListUiState.Error ->{
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.loadMovies() }) {
                            Text("Thử lại")
                        }
                    }
                }
            }
        }


    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieDetailScreen(
    movieId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val movie = remember(movieId, sampleMovies) {
        sampleMovies.find { it.id == movieId }
    }


    if (movie == null) {
        Box(
            modifier = modifier.fillMaxSize().padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Không tìm thấy phim")
        }
    } else{
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "MovieExplorer",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Quay về trang trước"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                    contentDescription = "Poster phim ${movie.title}",
                    placeholder = painterResource(R.drawable.ic_loading),
                    error = painterResource(R.drawable.ic_error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.voteAverage.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "rating"
                    )
                }

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        }
    }

}









