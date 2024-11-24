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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.Vginfotech.reelapp.API.ViewModel.ApiViewModel
import com.Vginfotech.reelapp.API.model.Category
import com.Vginfotech.reelapp.Misc
import com.Vginfotech.reelapp.R
import compose.material.theme.GradientButton
import org.koin.androidx.compose.koinViewModel


@Composable
fun EditProfile(navController: NavController) {
    val viewModel: ApiViewModel =  koinViewModel()
    val misc = Misc(LocalContext.current)
    val Error by viewModel.Error.collectAsStateWithLifecycle()
    val message by viewModel.message.collectAsStateWithLifecycle()
    EditProfileScreen(viewModel){

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
    LaunchedEffect(Error) {
        if (Error != null) {
            Log.d("ERROR",Error.toString())
            misc.showToast(Error.toString())

        }
    }
    LaunchedEffect (message){
        if(message.isNotEmpty()){
            misc.showToast(message)
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
fun EditProfileScreen(viewModel:ApiViewModel,Click:(EditProfileComponent)->Unit) {

    viewModel.getCategories()
    viewModel.fetchusercategories()
    var cateories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var selected_categories by remember { mutableStateOf<List<Category>>(emptyList()) }

    var state by remember { mutableStateOf<ChipSelectorState?>(null) }
    val categoriesData by viewModel.categoriesData.collectAsStateWithLifecycle()
    val selected_categoriesData by viewModel.selectedcategoriesData.collectAsStateWithLifecycle()

    LaunchedEffect(categoriesData,selected_categoriesData) {

        cateories=categoriesData?.categories ?: emptyList()
        selected_categories = selected_categoriesData?.categories?: emptyList()
        Log.d("categories",cateories.toString())
        Log.d("Selected_cat",cateories.toString())
    }



    var name by rememberSaveable { mutableStateOf(("")) }
    var email by rememberSaveable { mutableStateOf(("")) }
    var description by rememberSaveable { mutableStateOf(("")) }
    var imageUri by remember { mutableStateOf("") }
    var selectImageUri by remember { mutableStateOf<Uri?>(null) }
    var gender by rememberSaveable { mutableStateOf(("")) }


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
        OutlinedEditText(
            "Gender",
            onValueChange = {
              gender=it
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
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Text(
            text = "Select Categories",
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        if(cateories.isEmpty()){
            androidx.compose.material3.Text("NO Categories")
        }
        else if(
            selected_categories.isEmpty()
        )
        androidx.compose.material3.Text(
            text = "NO Categories",
            fontSize = 12.sp,
        )
        else{
            Log.d("selected_categories",selected_categories.toString())

             state = rememberChipSelectorState(
                chips = cateories,
                selectedChips = selected_categories ,
                mode = SelectionMode.Multiple)



            CategoriesChips(state = state!!)
        }
        Spacer(modifier = Modifier.height(8.dp))





        val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
        val cornerRadius = 16.dp
        val misc = Misc(LocalContext.current)
        GradientButton(
            gradientColors = gradientColor,
            cornerRadius = cornerRadius,
            nameButton = "Update",
            roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp)
        ){
           // Click(EditProfileComponent.SAVED)
            var cat:String=""
            if(state==null){
                misc.showToast("Please select categories")
                return@GradientButton
            }
            else{
                val selctcat= state!!.selectedChips
                selctcat.forEach {
                    cat=cat+it.id.toString()+","
                }
                cat=cat.removeSuffix(",")
            }
            if(name.isEmpty()||gender.isEmpty()||email.isEmpty()||cat.isEmpty()){
                misc.showToast("Please all field")
                return@GradientButton
            }
            else{
                viewModel.updateProfile(imageUri,name,gender,email,cat)
            }







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

