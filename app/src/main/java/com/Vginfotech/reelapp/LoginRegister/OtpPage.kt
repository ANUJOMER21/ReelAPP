package com.Vginfotech.reelapp.LoginRegister

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.Vginfotech.reelapp.API.ViewModel.ApiViewModel
import com.Vginfotech.reelapp.Misc
import com.Vginfotech.reelapp.Navigation.Navigation
import com.Vginfotech.reelapp.R
import compose.material.theme.GradientButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun OtpPage(navController: NavController){
    val viewModel: ApiViewModel =  koinViewModel()
    val misc = Misc(LocalContext.current)
    val NavigationResult by viewModel.NavigationResult.collectAsStateWithLifecycle()
    val Error by viewModel.Error.collectAsStateWithLifecycle()
    val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
    val cornerRadius = 16.dp
    var otpValue by remember {
        mutableStateOf("")
    }

    LaunchedEffect(NavigationResult) {
        NavigationResult?.let {
            navController.navigate(it.route) {
                popUpTo(navController.currentDestination?.id ?: return@navigate) {
                    inclusive = true
                }
            }
        }

        viewModel.setNavigationNull()
    }

    LaunchedEffect(Error){
        if(Error!=null){
            misc.showToast(Error.toString())
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.White,
            )
    ) {
        Box(
            modifier = Modifier
                /*.background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                )*/
                .padding(top = 30.dp)
                .align(Alignment.TopCenter),
        ) {

            Image(
                painter = painterResource(id = R.drawable.otpimage  ),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),

                )
            Column(
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))

                //.........................Text: title
                androidx.compose.material3.Text(
                    text = "OTP Verification",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(16.dp))
                OtpTextField(
                    otpText = otpValue,
                    onOtpTextChange = { value, otpInputFilled ->
                        otpValue = value
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                GradientButton(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Verify OTP",
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp),

                    ){
                      viewModel.verify_otp(otpValue)

                }
            }

        }
    }
}