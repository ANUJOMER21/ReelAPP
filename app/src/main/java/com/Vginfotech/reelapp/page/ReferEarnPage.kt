package com.Vginfotech.reelapp.page

import androidx.compose.runtime.Composable
import androidx.navigation.NavController


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Vginfotech.reelapp.R

@Composable
fun ReferEarn(navController: NavController){
    ReferAndEarnPage("ReferalProgram", LocalContext.current)
}

@Composable
fun ReferAndEarnPage(referralCode: String, context: Context) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF4E54C8), Color(0xFF8F94FB))
                    )
                )

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Header Image
                val banner: Painter = painterResource(id = R.drawable.referearn) // Replace with your image
                Image(
                    painter = banner,
                    contentDescription = "Refer & Earn Banner",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Card with referral code
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Your Referral Code",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4E54C8)
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFF3F4FB),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 12.dp, horizontal = 32.dp)
                        ) {
                            Text(
                                text = referralCode,
                                style = MaterialTheme.typography.displaySmall.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.Black
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Share Button
                Button(
                    onClick = {
                        shareReferralCode(context, referralCode)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4E54C8)),
                    shape = CircleShape,
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(0.8f)
                        .shadow(8.dp, CircleShape)
                ) {
                    Text(
                        text = "Share & Earn Rewards",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }


fun shareReferralCode(context: Context, referralCode: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TEXT,
            "Hey! Join this amazing app and earn rewards using my referral code: $referralCode. Download now!"
        )
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}

@Preview
@Composable
fun previewReferearn(){
    ReferAndEarnPage("ReferalProgram", LocalContext.current)
}