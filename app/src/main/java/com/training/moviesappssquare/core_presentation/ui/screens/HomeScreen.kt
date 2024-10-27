package com.training.moviesappssquare.core_presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.training.moviesappssquare.R
import com.training.moviesappssquare.core_data.remote.model.GenresItem
import com.training.moviesappssquare.core_data.remote.model.ResultsItem
import com.training.moviesappssquare.core_presentation.ui.viewmodels.HomeViewModel
import com.training.moviesappssquare.utils.APIConstants
import com.training.moviesappssquare.utils.DestinationUtils
import com.training.moviesappssquare.utils.MovieStates

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navcontroller:NavHostController
) {
    var popularMoviesState = homeViewModel.popularMoviesFlow.collectAsState()
    var categoriesState = homeViewModel.categoriesListFlow.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        var configuration = LocalConfiguration.current
        var screenWidthDp = configuration.screenWidthDp

        var searchState by remember {
            mutableStateOf("")
        }


        Spacer(modifier = Modifier.height(71.dp))
        Text(text = stringResource(id = R.string.search_for_content),
            modifier = Modifier.fillMaxWidth(.9f))

        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = searchState,
            onValueChange = {
                searchState = it
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_for_content))
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.light_gray),
                unfocusedContainerColor = colorResource(id = R.color.light_gray),
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth(.9f),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.categories),
            modifier = Modifier.fillMaxWidth(.9f))

        Spacer(modifier = Modifier.height(4.dp))

        // observing to  categoriesStates
        when(categoriesState.value){
            is MovieStates.Error -> {

            }
            MovieStates.Loading -> {

            }
            is MovieStates.Successful -> {
                // show data in catgories rv
                var  categories = (categoriesState.value as MovieStates.Successful).data
                CategoriesList(categories = categories, screenWidthDp = screenWidthDp, homeViewModel = homeViewModel,
                    modifier = Modifier
                        .fillMaxWidth(.95f)
                        .align(Alignment.End))
            }
            MovieStates.UnSpecified -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.movies),
            modifier = Modifier.fillMaxWidth(.9f))

        Spacer(modifier = Modifier.height(4.dp))

        // observing to popularMoviesStates
        when(popularMoviesState.value){
            is MovieStates.Error -> {
                Toast.makeText(LocalContext.current, (popularMoviesState.value as MovieStates.Error).error,
                    Toast.LENGTH_SHORT).show()
            }
            MovieStates.Loading -> {
            }
            is MovieStates.Successful ->{
                var movies = (popularMoviesState.value as MovieStates.Successful).data
                PopularMovies(movies = movies,
                    Modifier
                        .fillMaxWidth(.95f)
                        .fillMaxHeight()
                        .align(Alignment.End),
                    navcontroller)
            }
            MovieStates.UnSpecified -> {}
        }


        DisposableEffect(key1 = Unit){
            homeViewModel.getPopularMovies(1)
            onDispose {
                homeViewModel.resetPopularMovies()
            }
        }
    }

}


@Composable
fun CategoriesList(
    categories:List<GenresItem?>,
    modifier: Modifier = Modifier,
    screenWidthDp:Int,
    homeViewModel: HomeViewModel
){
    LazyRow(modifier = modifier,
        content = {
            items(categories.size){
                // design item of list
                CategoryItem(category = categories.get(it),
                    screenWidthDp.toFloat()){categoryId->
                    // show movies that depend on categoryId
                    // clear popular movies then show related movies based on clicked
                    categoryId?.let {
                        homeViewModel.getMoviesByCategoryId(it, 1)
                    }
                }
            }
        })
}

@Composable
fun PopularMovies(
    movies:List<ResultsItem?>,
    modifier :Modifier = Modifier,
    navcontroller: NavHostController
){
    LazyVerticalGrid(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        columns = GridCells.Fixed(3) , content = {
            items(movies){
                MovieItem(movie = it, navcontroller = navcontroller)
            }
        })
}

@Composable
fun CategoryItem(
    category:GenresItem?,
    screenWidthDp:Float,
    onCategoryClick:(categoryId:Int?)->Unit
){
    Row {
        Spacer(modifier = Modifier.width(4.dp))
        Card (
            border = BorderStroke(1.dp,Color.Black),
            modifier = Modifier
                .width((((screenWidthDp) / 3) - (4 * 3)).dp)
                .height(43.dp)
                .clickable {
                    onCategoryClick(category?.id)
                },
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = category?.name.toString())
            }
        }
    }
}

@Composable
fun MovieItem(
    movie:ResultsItem?,
    navcontroller: NavHostController
){
  Column(
      horizontalAlignment = Alignment.CenterHorizontally
  ) {
      Spacer(modifier = Modifier.height(8.dp))
      Card (
          modifier = Modifier
              .size(124.dp)
              .clip(shape = RoundedCornerShape(24.dp))
      ){
          Spacer(modifier = Modifier.height(8.dp))
          Image(painter = rememberAsyncImagePainter(model = APIConstants.POSTER_BASE_URL+movie?.posterPath,
              contentScale = ContentScale.Crop),
              contentDescription ="" ,
              modifier = Modifier
                  .fillMaxSize()
                  .clip(shape = RoundedCornerShape(24.dp))
                  .aspectRatio(6f / 9f)
                  .clickable {
                      // navigate to movieDetails screen
                      // send id of item clicked to movieDetail screen
                      navcontroller.navigate("${DestinationUtils.MOVIE_DETAILS_SCREEN}/${movie?.id}")
                  },
          )
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(text = movie?.originalTitle.toString(),
          maxLines = 1
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = movie?.releaseDate.toString(),
          maxLines = 1)
  }
}