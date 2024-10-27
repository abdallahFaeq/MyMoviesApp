package com.training.moviesappssquare.core_presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.training.moviesappssquare.core_presentation.ui.screens.HomeScreen
import com.training.moviesappssquare.core_presentation.ui.screens.MovieDetailsScreen
import com.training.moviesappssquare.core_presentation.ui.screens.OnBoardingScreen
import com.training.moviesappssquare.core_presentation.ui.viewmodels.HomeViewModel
import com.training.moviesappssquare.core_presentation.ui.viewmodels.MovieDetailsViewModel
import com.training.moviesappssquare.core_presentation.ui.theme.MoviesAppsSquareTheme
import com.training.moviesappssquare.utils.DestinationUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val homeViewModel : HomeViewModel by viewModels()
    val movieDetailsViewModel : MovieDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppsSquareTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   var navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = DestinationUtils.ONBOARDING_SCREEN,
                        builder ={
                            composable(DestinationUtils.ONBOARDING_SCREEN){
                                OnBoardingScreen(
                                    navController
                                )
                            }

                            composable(DestinationUtils.HOME_SCREEN){
                                HomeScreen(homeViewModel,navController)
                            }

                            composable("${DestinationUtils.MOVIE_DETAILS_SCREEN}/{movieId}"){backStackEntry->
                                // retriving movieId
                                var movieId = backStackEntry.arguments?.getString("movieId")?.toInt()?:0
                                MovieDetailsScreen(movieId, movieDetailsViewModel)
                            }
                        } )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesAppsSquareTheme {

    }
}