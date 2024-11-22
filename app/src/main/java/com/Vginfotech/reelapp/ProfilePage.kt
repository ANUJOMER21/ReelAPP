package com.Vginfotech.reelapp

import android.annotation.SuppressLint
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ProfilePage (navController: NavController){
InstagramProfilePage()
}
// File: ProfilePage.kt





@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InstagramProfilePage() {
    // Using Scaffold as the base layout
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) { paddingValues -> // Get scaffold's padding values for correct layout
        // LazyColumn to organize UI components vertically with scroll support
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = paddingValues.calculateBottomPadding()), // Adjust for bottom bar
            contentPadding = PaddingValues(top = 16.dp) // Add padding for top content
        ) {
            // Add ProfileHeaderSection as an item
            item {
                ProfileHeaderSection()
            }

            // Add TabSection as an item
            item {
                TabSection()
            }

            // Spacer between TabSection and PostGridSection
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Add PostGridSection as an item
            item {
                PostGridSection()
            }

            // Uncomment and add FollowStatsSection as an item if needed
            // item {
            //     FollowStatsSection()
            // }
        }
    }
}


@Composable
fun ProfileHeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture with Gradient Background
        Box(
            modifier = Modifier
                .size(100.dp)
                /*.background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFDE0046), Color(0xFFF7A34B)),
                        radius = 200f
                    ),
                    shape = CircleShape
                )*/
               // .padding(4.dp)
        ) {
            // Profile image
            Image(
                painter = painterResource(id = R.drawable.user_3), // Replace with your image resource
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color.Black, CircleShape)
                    .padding(4.dp)
            )

            // Edit Icon
            Icon(
                imageVector = Icons.Default.Edit, // You can use any icon here
                contentDescription = "Edit",
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Position at the bottom right
                    .padding(1.dp) // Adjust padding to control the position
                    .size(24.dp) // Size of the edit icon
                    .background(Color.White, CircleShape) // Optional: Add background to make it stand out
                    .border(1.dp, Color.Black, CircleShape) // Optional: Border around the icon
                    .clickable {
                        // Handle edit icon click here
                    }
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // User Info
        Text(
            text = "John Doe",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Photographer | Traveler",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Vertical Action Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between buttons
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp) // Adds padding on the sides
        ) {
            Button(
                onClick = { /* Wallet action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3897F0)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(2f) // Ensures this button takes 50% width
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wallet_svgrepo_com_2), // Replace with your wallet icon
                    contentDescription = "Wallet Icon",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Wallet", color = Color.White)
            }

            OutlinedButton(
                onClick = { /* Refer & Earn action */ },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(2f) // Ensures this button takes 50% width
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.share_circle_svgrepo_com), // Replace with your refer icon
                    contentDescription = "Refer Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Refer & Earn")
            }
        }



    }
}


@Composable
fun FollowStatsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem("Posts", 120)

    }
}

@Composable
fun StatItem(label: String, count: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$count", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun TabSection() {
    var selectedTab by remember { mutableStateOf(0) }
    TabRow(
        selectedTabIndex = selectedTab,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                color = Color.Black
            )
        }
    ) {
        Tab(
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 },
            text = {
                Text(
                    text = "30 Posts",
                    color = if (selectedTab == 0) Color.Black else Color.Gray,
                    fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                )
            }
        )


    }
}

@Composable
fun PostGridSection() {
    val totalItems = 30 // The total number of items to display
    val rows = totalItems / 3 // We will have 3 items per row
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Loop through rows
        for (rowIndex in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                // Loop through columns (3 items per row)
                for (columnIndex in 0 until 3) {
                    val itemIndex = rowIndex * 3 + columnIndex
                    if (itemIndex < totalItems) {
                        Box(
                            modifier = Modifier
                                .weight(1f) // Equal distribution of space
                                .aspectRatio(1f) // Keep the aspect ratio square
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Post $itemIndex", textAlign = TextAlign.Center, color = Color.DarkGray)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview(){
    InstagramProfilePage()
}