package com.training.moviesappssquare.core_presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.training.moviesappssquare.R
import com.training.moviesappssquare.utils.DestinationUtils

@Composable
fun OnBoardingScreen(
    navController:NavHostController
){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp

        val textSize = when{
            screenWidthDp < 360 -> 16.sp
            screenWidthDp < 480 -> 18.sp
            screenWidthDp < 600 -> 20.sp
            else -> 22.sp
        }

        Spacer(modifier = Modifier.height(89.dp))
        Image(painter = painterResource(id = R.drawable.splash_image),
            contentDescription = "splash image",
            modifier = Modifier
                .width(514.dp)
                .height(492.dp))

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(id = R.string.on_boarding),
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(id = R.string.watch_movies_free),
            modifier = Modifier.width(218.dp),
            style = TextStyle(
                fontSize = textSize,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            navController.navigate(
                DestinationUtils.HOME_SCREEN
            )
        },
            modifier = Modifier
                .width(198.dp)) {
            Text(text = stringResource(id = R.string.enter_now))
        }
    }
}
