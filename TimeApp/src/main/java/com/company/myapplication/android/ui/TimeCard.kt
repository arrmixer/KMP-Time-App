package com.company.myapplication.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
// This function takes a time zone, hours, time and date
fun TimeCard(timezone: String, hours: Double, time: String, date: String) {
    // Use a box to take up the full width and give it a white background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(120.dp)
            .background(Color.White)
            .padding(8.dp)
    ) {
        // Create a nice-looking card
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray),
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            // Use a box to set the background to white
            Box(
                modifier = Modifier
                    .background(
                        color = Color.White
                    )
                    .padding(16.dp)
            ) {
                // Create a row that fills the width
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Create a column on the left side
                    Column(
                        horizontalAlignment = Alignment.Start

                    ) {
                        // Show the time zone
                        Text(
                            timezone, style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.weight(1.0f))
                        // Create a row underneath the previous one
                        Row {
                            // Show the hours in bold
                            Text(
                                hours.toString(), style = TextStyle(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                            // Show the text “hours from local.”
                            Text(
                                " hours from local", style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    // Create a column on the right side
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        // Show the time
                        Text(
                            time, style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.weight(1.0f))
                        // Show the date
                        Text(
                            date, style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }

        }
    }
}
