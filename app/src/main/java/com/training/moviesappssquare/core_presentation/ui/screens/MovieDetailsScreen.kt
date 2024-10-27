package com.training.moviesappssquare.core_presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.training.moviesappssquare.core_presentation.ui.viewmodels.MovieDetailsViewModel
import com.training.moviesappssquare.utils.APIConstants
import com.training.moviesappssquare.utils.MovieDetailsStates

@Composable
fun MovieDetailsScreen(
    movieId:Int,
    movieDetailsViewModel: MovieDetailsViewModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ){
        val (movieImage, movieName, movieRating, movieVoteCount,
            movieOverview,companiesGrid) = createRefs()

        LaunchedEffect(key1 = movieId, block = {
            movieDetailsViewModel.getMovieDetails(movieId)
        })

        DisposableEffect(key1 = Unit){
            onDispose {
                movieDetailsViewModel.resetMovieDetailsState()
            }
        }

        var movieDetailsState = movieDetailsViewModel.movieDetailsFlow.collectAsState()

        when(movieDetailsState.value){
            is MovieDetailsStates.Error -> {}
            MovieDetailsStates.Loading -> {}
            is MovieDetailsStates.Successful -> {
                var movieResponse  = (movieDetailsState.value as MovieDetailsStates.Successful).data
                // show details
                MovieImage(
                    modifier = Modifier
                        .height(349.dp)
                        .constrainAs(movieImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    posterPath = APIConstants.POSTER_BASE_URL+movieResponse?.posterPath)

                Text(text = movieResponse?.originalTitle?:"",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight(700)
                    ),
                    maxLines = 1,
                    modifier = Modifier
                        .width(130.dp)
                        .constrainAs(movieName) {
                            start.linkTo(parent.start, margin = 16.dp)
                            top.linkTo(movieImage.bottom, margin = 12.dp)
                        }
                )

                val rating = ((movieResponse?.voteAverage as? Double)?.coerceIn(0.0,10.0)?:0.0)/2.0
                RatingBar(
                    modifier = Modifier.constrainAs(movieRating){
                        top.linkTo(movieName.top)
                        end.linkTo(parent.end, margin = 16.dp)
                    },
                    rating = rating.toFloat()
                )

                Text(text = "from ${movieResponse?.voteCount.toString()} users",
                    fontSize = 10.sp,
                    modifier = Modifier
                        .constrainAs(movieVoteCount){
                            start.linkTo(movieRating.start)
                            end.linkTo(movieRating.end)
                            top.linkTo(movieRating.bottom)
                        })

                Text(text = movieResponse?.overview.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth(fraction = .95f)
                        .constrainAs(movieOverview) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(movieName.bottom, margin = 24.dp)
                        })

            }
            MovieDetailsStates.UnSpecified -> {}
        }
    }
}

@Composable
fun MovieImage(
    modifier :Modifier= Modifier,
    posterPath:String
){
    Image(painter = rememberAsyncImagePainter(model =posterPath),
        contentDescription = "",
        modifier = modifier
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth
       )
}


@Composable
fun RatingBar(
    modifier :Modifier = Modifier,
    rating:Float = 0.0f,
){
    Row (
        modifier = modifier
    ){
        for (i in 1..5){
            Icon(imageVector = if (i < rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "",
                modifier = Modifier.size(16.dp),
                tint = if (i <= rating) Color.Yellow else Color.Gray)
        }
    }
}