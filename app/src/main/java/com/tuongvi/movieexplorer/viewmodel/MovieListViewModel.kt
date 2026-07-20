package com.tuongvi.movieexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuongvi.movieexplorer.data.api.RetrofitClient
import com.tuongvi.movieexplorer.data.dto.toMovie
import com.tuongvi.movieexplorer.model.Movie
import com.tuongvi.movieexplorer.model.MovieListUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


//val sampleMovies = listOf(
//    Movie(
//        id = 1,
//        title = "Interstellar",
//        overview = "Một nhóm các nhà thám hiểm du hành qua hố đen để tìm ngôi nhà mới cho nhân loại.",
//        posterPath = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
//        voteAverage = 8.4,
//        releaseDate = "2014-11-07"
//    ),
//    Movie(
//        id = 2,
//        title = "Inception",
//        overview = "Một tên trộm chuyên đánh cắp bí mật trong tiềm thức nhận nhiệm vụ gieo ý tưởng vào đầu mục tiêu.",
//        posterPath = "/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
//        voteAverage = 8.4,
//        releaseDate = "2010-07-16"
//    ),
//    Movie(
//        id = 3,
//        title = "The Dark Knight",
//        overview = "Batman đối đầu với Joker, kẻ muốn gieo rắc sự hỗn loạn tại Gotham.",
//        posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
//        voteAverage = 8.5,
//        releaseDate = "2008-07-18"
//    ),
//    Movie(
//        id = 4,
//        title = "Avatar: The Way of Water",
//        overview = "Gia đình Jake Sully phải chiến đấu để bảo vệ Pandora trước những kẻ xâm lược.",
//        posterPath = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
//        voteAverage = 7.6,
//        releaseDate = "2022-12-16"
//    ),
//    Movie(
//        id = 5,
//        title = "Spider-Man: No Way Home",
//        overview = "Peter Parker tìm đến Doctor Strange để khôi phục danh tính bí mật của mình.",
//        posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
//        voteAverage = 8.0,
//        releaseDate = "2021-12-17"
//    ),
//    Movie(
//        id = 6,
//        title = "Dune",
//        overview = "Paul Atreides cùng gia đình đến hành tinh Arrakis, nơi chứa nguồn tài nguyên quý giá nhất vũ trụ.",
//        posterPath = "/d5NXSklXo0qyIYkgV94XAgMIckC.jpg",
//        voteAverage = 7.8,
//        releaseDate = "2021-10-22"
//    ),
//    Movie(
//        id = 7,
//        title = "Oppenheimer",
//        overview = "Câu chuyện về J. Robert Oppenheimer và quá trình phát triển bom nguyên tử.",
//        posterPath = "/ptpr0kGAckfQkJeJIt8st5dglvd.jpg",
//        voteAverage = 8.1,
//        releaseDate = "2023-07-21"
//    ),
//    Movie(
//        id = 8,
//        title = "The Shawshank Redemption",
//        overview = "Một người đàn ông vô tội bị kết án tù chung thân và tìm thấy hy vọng sau song sắt.",
//        posterPath = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
//        voteAverage = 8.7,
//        releaseDate = "1994-09-23"
//    ),
//    Movie(
//        id = 9,
//        title = "The Godfather",
//        overview = "Gia đình mafia Corleone đối mặt với những biến động quyền lực và phản bội.",
//        posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
//        voteAverage = 8.7,
//        releaseDate = "1972-03-24"
//    ),
//    Movie(
//        id = 10,
//        title = "Spirited Away",
//        overview = "Chihiro lạc vào thế giới linh hồn và phải tìm cách cứu cha mẹ mình.",
//        posterPath = "/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
//        voteAverage = 8.5,
//        releaseDate = "2001-07-20"
//    )
//)
class MovieListViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        loadMovies()
    }
    fun loadMovies(){
        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading
            try {
                val responseDto = RetrofitClient.movieApi.getPopularMovies(
                    apiKey = RetrofitClient.API_KEY
                )

                val movies = responseDto.results.map { dto -> dto.toMovie() }

                _uiState.value = MovieListUiState.Success(movies)

            } catch (
                e: Exception
            ){
                android.util.Log.e("API_ERROR", "Lỗi gọi API hoặc Parse JSON: ${e.message}", e)
                _uiState.value = MovieListUiState.Error(e.localizedMessage ?: "Lỗi tải phim")
            }
        }
    }

    fun searchMovies(query: String){
        _searchQuery.value = query
        if (_searchQuery.value.isBlank()) {
            loadMovies()
            return
        }

        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading
            try {
                val responseDto = RetrofitClient.movieApi.getSearchMovies(
                    apiKey = RetrofitClient.API_KEY,
                    query = _searchQuery.value
                )
                val movies = responseDto.results.map { dto -> dto.toMovie() }

                _uiState.value = MovieListUiState.Success(movies)
            }catch (
                e: Exception
            ){
                _uiState.value = MovieListUiState.Error(e.localizedMessage ?: "Lỗi tìm kiếm phim")
            }
        }
    }

    fun refresh(){
        val currentQuery = _searchQuery.value
        if (currentQuery.isNotEmpty()){
            searchMovies(currentQuery)
        } else{
            loadMovies()
        }
    }

    fun getMovieById(movieId: Int): Movie?{
        val currentState = _uiState.value
        if(currentState is MovieListUiState.Success){
            return currentState.movies.find { it.id == movieId }
        }
        return null
    }

//    fun onSearchQueryChanged(newQuery: String){
//        _searchQuery.value = newQuery
//        applyFilter()
//    }
//
//    fun applyFilter(){
//        val query = _searchQuery.value.trim()
//        if(query.isEmpty()){
//            _uiState.value = MovieListUiState.Success(sampleMovies)
//        }else{
//            val filteredList = sampleMovies.filter { movie ->
//                movie.title.contains(query, ignoreCase = true)
//            }
//
//            _uiState.value = MovieListUiState.Success(filteredList)
//        }
//    }

    fun simulateError() {
        _uiState.value = MovieListUiState.Error("Lỗi giả lập: Máy chủ không phản hồi!")
    }

    fun simulateLoading() {
        _uiState.value = MovieListUiState.Loading
    }
}