package compose.material.theme


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.Vginfotech.reelapp.API.ApiResult
import com.Vginfotech.reelapp.API.ViewModel.ApiViewModel

import com.Vginfotech.reelapp.API.model.signupmodel
import com.Vginfotech.reelapp.R
import com.Vginfotech.reelapp.LoginRegister.Visibility
import com.Vginfotech.reelapp.LoginRegister.VisibilityOff
import com.Vginfotech.reelapp.Misc
import com.Vginfotech.reelapp.Navigation.Navigation
import com.Vginfotech.reelapp.SharedPrefManager
import com.Vginfotech.reelapp.page.OutlinedEditText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.inject


@Composable
fun LoginPage(
    navController: NavController
) {
    val viewModel: ApiViewModel =  koinViewModel()
    val misc =Misc(LocalContext.current)


    var number by remember   { mutableStateOf("") }
    var password by remember   { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val NavigationResult by viewModel.NavigationResult.collectAsStateWithLifecycle()
    val Error by viewModel.Error.collectAsStateWithLifecycle()

    LaunchedEffect(NavigationResult) {
        NavigationResult?.let { navController.navigate(it.route){
            popUpTo(navController.currentDestination?.id ?: return@navigate) {
                inclusive = true
            }
        } }
        viewModel.setNavigationNull()

    }
    LaunchedEffect(Error){
        if(Error!=null){
            misc.showToast(Error.toString())
            loading=false
        }


    }


/*    LaunchedEffect (loginResult){
         when (loginResult) {
             is ApiResult.Failure -> {
                 val fail =(loginResult as ApiResult.Failure).error
                 Log.d("ERROR",fail.toString())
             }
             is ApiResult.Success ->{
                 var it=(loginResult as ApiResult.Success).data
                 if (it != null) {
                     misc.showToast(it.message)
                     if(it.status.equals("success")&& !isOtpPageVisited){
                         Log.d("OTP",it.otp.toString())
                         viewModel.setLoginresultNull()
                         navController.navigate(Navigation.OtpPage.route)
                     }

                 }


             }
         }
     }*/




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
                .align(Alignment.BottomCenter),
        ) {

            Image(
                painter = painterResource(id = R.drawable.user_sign_in),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),

                )
            Column(
                modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ,

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //.........................Spacer
                Spacer(modifier = Modifier.height(50.dp))

                //.........................Text: title
                androidx.compose.material3.Text(
                    text = "Sign In",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedEditText(
                    fieldName = "Mobile Number",
                    onValueChange = { number = it },
                    isSingleLine = true

                )

                Spacer(modifier = Modifier.padding(3.dp))
                OutlinedEditText(
                    fieldName = "Password",
                    onValueChange = { password = it },
                    isSingleLine = true,
                    isPassword = true

                )


                val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
                val cornerRadius = 16.dp


                Spacer(modifier = Modifier.padding(10.dp))
         if(loading){
             CircularProgressIndicator()
         }
                else{
             GradientButton(
                 gradientColors = gradientColor,
                 cornerRadius = cornerRadius,
                 nameButton = "Login",
                 roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp),

                 ){
                    if(number.isEmpty() || password.isEmpty()){
                        misc.showToast("Please fill all details")

                    }
                 else{



                        viewModel.login(number, password)
                        loading=true

                    }

                 /*
                  }*/
             }
         }


                Spacer(modifier = Modifier.padding(10.dp))
                androidx.compose.material3.TextButton(onClick = {

                    navController.navigate(Navigation.Register.route)

                }) {
                    androidx.compose.material3.Text(
                        text = "Create An Account",
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }


                Spacer(modifier = Modifier.padding(5.dp))

                Spacer(modifier = Modifier.padding(20.dp))

            }


        }

    }


}


//...........................................................................
@Composable
fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape,
    onClick:()->Unit
) {

    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
                    onClick()
        },

        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)
                /*.background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )*/
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
