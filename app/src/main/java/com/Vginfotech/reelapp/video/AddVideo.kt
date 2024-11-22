package com.Vginfotech.reelapp.video

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.Vginfotech.reelapp.R

@Composable
fun AddVideo(navController: NavController){
    StylishUploadVideoScreen()
}@Composable
fun StylishUploadVideoScreen() {
    val context = LocalContext.current
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        videoUri = uri
    }



            Box(
                modifier = Modifier
                    .fillMaxSize()

                    .background(
                       Color.White
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Upload Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Color(0x661A1A1A)
                            )
                            .clickable { launcher.launch("video/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (videoUri == null) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(R.drawable.upload_image),
                                    contentDescription = "Upload",
                                    modifier = Modifier.size(100.dp)


                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Tap to upload video",
                                    style = MaterialTheme.typography.body1.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }
                        } else {
                            Text(
                                text = "Video Selected",
                                style = MaterialTheme.typography.h6.copy(color = Color.White)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Description
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                           ,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF8E24AA),
                            cursorColor = Color(0xFF8E24AA)
                        ),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Location
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location") },
                        modifier = Modifier
                            .fillMaxWidth()
                           ,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF8E24AA),
                            cursorColor = Color(0xFF8E24AA)
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Upload Button
                    Button(
                        onClick = {
                            Toast.makeText(context, "Video Uploaded!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8E24AA),
                            contentColor = Color.White,

                        ),

                    ) {
                        Text(
                            text = "Upload Video",
                            style = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }



@Preview
@Composable
fun UploadVideoScreenPreview() {
    StylishUploadVideoScreen()
}