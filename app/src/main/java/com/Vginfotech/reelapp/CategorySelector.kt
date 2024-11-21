package com.Vginfotech.reelapp

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.Vginfotech.reelapp.Navigation
import com.example.reels.ChipSelectorState
import com.example.reels.ChipsSelector
import com.example.reels.SelectionMode
import com.example.reels.rememberChipSelectorState
import compose.material.theme.GradientButton

@Composable
fun CategorySelector(navController: NavController) {
    val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
    val cornerRadius = 16.dp
    val categoryList = listOf(
        "Travel",
        "Food",
        "Lifestyle",
        "Fashion",
        "Fitness",
        "Technology",
        "Education",
        "Music",
        "Comedy",
        "DIY & Crafts",
        "Gaming",
        "Health & Wellness",
        "Sports",
        "Finance",
        "Photography",
        "Parenting",
        "Beauty",
        "Books & Literature",
        "Movies & TV",
        "Art & Design",
        "Science",
        "History",
        "Nature & Wildlife",
        "Personal Development",
        "Business & Entrepreneurship",
        "Automobiles",
        "Pets & Animals",
        "Social Issues",
        "Events & Occasions",
        "Relationships",
        "Home & Garden",
        "Spirituality",
        "Technology Reviews",
        "Podcasting",
        "News & Current Events"
    )


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
                val state2 = rememberChipSelectorState(chips = categoryList, mode = SelectionMode.Multiple)
                CategoriesChips(state = state2)
                Spacer(modifier = Modifier.height(40.dp))
                GradientButton(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Submit Categories",
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp),

                    ) {
                    val selectedcat=state2.selectedChips
                    if(selectedcat.isEmpty()){
                        Log.d("CategorySelector", "CategorySelector: Empty")
                       return@GradientButton
                    }
                    else {
                        Log.d("CategorySelector", "CategorySelector: $selectedcat")
                        navController.navigate(Navigation.Reels.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
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
