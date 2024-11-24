package com.Vginfotech.reelapp.page

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.Vginfotech.reelapp.API.ViewModel.ApiViewModel
import com.Vginfotech.reelapp.API.model.Category
import com.Vginfotech.reelapp.Misc
import com.Vginfotech.reelapp.Navigation.Navigation
import compose.material.theme.GradientButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategorySelector(navController: NavController) {


    val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
    val cornerRadius = 16.dp
    val viewModel: ApiViewModel =  koinViewModel()
    viewModel.getCategories()

    var cateories by remember { mutableStateOf<List<Category>>(emptyList()) }

    val misc = Misc(LocalContext.current)

    val NavigationResult by viewModel.NavigationResult.collectAsStateWithLifecycle()
    val Error by viewModel.Error.collectAsStateWithLifecycle()
    val categoriesData by viewModel.categoriesData.collectAsStateWithLifecycle()
    LaunchedEffect(NavigationResult) {
        NavigationResult?.let { navController.navigate(it.route){
            popUpTo(navController.currentDestination?.id ?: return@navigate) {
                inclusive = true
            }
        } }

        viewModel.setNavigationNull()

    }
    LaunchedEffect(Error) {
        if (Error != null) {
            misc.showToast(Error.toString())

        }
    }
    LaunchedEffect(categoriesData) {
            cateories=categoriesData?.categories ?: emptyList()

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
                .padding(top = 10.dp)
                .align(Alignment.TopStart),
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material3.Text(
                    text = "Select Category",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(20.dp))
                if(cateories.isEmpty()){
                    Text("NO Categories")
                }
                else {
                    val state2 = rememberChipSelectorState(
                        chips = cateories,

                        mode = SelectionMode.Multiple)
                    CategoriesChips(state = state2)
                    Spacer(modifier = Modifier.height(40.dp))
                    GradientButton(
                        gradientColors = gradientColor,
                        cornerRadius = cornerRadius,
                        nameButton = "Submit Categories",
                        roundedCornerShape = RoundedCornerShape(
                            topStart = 30.dp,
                            bottomEnd = 30.dp
                        ),

                        ) {
                        val selectedcat = state2.selectedChips
                        if (selectedcat.isEmpty()) {
                            Log.d("CategorySelector", "CategorySelector: Empty")
                            return@GradientButton
                        } else {
                            Log.d("CategorySelector", "CategorySelector: $selectedcat")

                            viewModel.submitcategory(selectedcat)
                            /* navController.navigate(Navigation.Reels.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }*/
                        }


                    }
                }
            }
        }
    }
}

@Composable
fun CategoriesChips(state: ChipSelectorState) {

    ChipsSelector(
        state = state,

        )

}
