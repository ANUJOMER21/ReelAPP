package com.Vginfotech.reelapp.page

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.Vginfotech.reelapp.R
import compose.material.theme.GradientButton


@Composable
fun EditProfile(navController: NavController) {
EditProfileScreen(){
    when (it) {
        EditProfileComponent.SAVED -> {}
        EditProfileComponent.TC -> {}
        EditProfileComponent.PRIVACY -> {}
        EditProfileComponent.CHANGEPASSWORD -> {}
        EditProfileComponent.LOGOUT -> {}
        EditProfileComponent.DELETEACCOUNT -> {}
        EditProfileComponent.MONOTIZE -> {}
    }
}
}
enum class EditProfileComponent{
    SAVED,
    TC,
    PRIVACY,
    CHANGEPASSWORD,
    LOGOUT,
    DELETEACCOUNT,
    MONOTIZE
}
@Composable
fun EditProfileScreen(Click:(EditProfileComponent)->Unit) {
    var name by rememberSaveable { mutableStateOf(("John Doe")) }
    var email by rememberSaveable { mutableStateOf(("johndoe@example.com")) }
    var description by rememberSaveable { mutableStateOf(("A short description about John.")) }
    var imageUri by remember { mutableStateOf("https://www.example.com/profile.jpg") }
    var selectImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectImageUri = uri
    }
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = selectImageUri ?: imageUri)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)

        ) {
            Image(
                painter = painter, // Replace with your image resource
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color.Black, CircleShape)
                    .padding(4.dp)
                    .clickable {
                                launcher.launch("image/*")
                    }
            )


        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedEditText(
            "Name",
            onValueChange = {
              name=it
            }

        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedEditText(
            "Email",
            onValueChange = {
                email = it
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedEditText(
            "Bio",
            onValueChange = {
                description = it
            },
            isSingleLine =  true
        )
        Spacer(modifier = Modifier.height(16.dp))
        val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
        val cornerRadius = 16.dp
        GradientButton(
            gradientColors = gradientColor,
            cornerRadius = cornerRadius,
            nameButton = "Saved Details",
            roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp)
        ){
            Click(EditProfileComponent.SAVED)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color.Black, thickness = 1.dp)
        Column(
            modifier = Modifier
                .fillMaxSize()

                .background(Color.White)
               ,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(4.dp))


                androidx.compose.material3.TextButton(onClick = {
                    Click(EditProfileComponent.MONOTIZE)
                }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp) // Optional padding between the image and text
                    ) {
                        Image(
                            painter = painterResource(R.drawable.coin_stack_money_svgrepo_com),
                            contentDescription = "Earn Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp)
                            // Optional padding for the image
                        )
                    androidx.compose.material3.Text(
                        text = "Earn From Video",
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge,

                        )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.material3.TextButton(onClick = {
                Click(EditProfileComponent.TC)
            })
            {
                androidx.compose.material3.Text(
                    text = "Terms and Condition",
                    fontSize = 16.sp,
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.material3.TextButton(onClick = {
                Click(EditProfileComponent.PRIVACY)
            })
            {
                androidx.compose.material3.Text(
                    text = "Privacy Policy",
                    fontSize = 16.sp,
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.material3.TextButton(onClick =
            { Click(EditProfileComponent.CHANGEPASSWORD)

            })
            {
                androidx.compose.material3.Text(
                    text = "Change Password",
                    fontSize = 16.sp,
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.material3.TextButton(onClick = {
                Click(EditProfileComponent.LOGOUT)
            })
            {
                androidx.compose.material3.Text(
                    text = "Log Out",
                    fontSize = 16.sp,
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            androidx.compose.material3.TextButton(onClick = {
                Click(EditProfileComponent.DELETEACCOUNT)
            })
            {
                androidx.compose.material3.Text(
                    text = "Delete Account",
                    fontSize = 16.sp,
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
// Placeholder image URL
}
@Composable
fun OutlinedEditText(
    fieldName: String,
    initialValue: String = "",
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSingleLine: Boolean = true,
    isPassword: Boolean = false, // Optional flag for password input
) {
    var text by rememberSaveable { mutableStateOf(initialValue) }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it) // Notify parent about value changes
        },
        shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
        label = {
            Text(
                text = fieldName,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        placeholder = {
            Text(text = fieldName)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = isSingleLine,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide() // Hide keyboard on 'Done' action
            }
        )
    )
}

