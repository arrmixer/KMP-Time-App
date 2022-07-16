package com.company.myapplication.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.company.myapplication.android.theme.primaryColor
import com.company.myapplication.android.theme.primaryDarkColor
import com.company.myapplication.android.theme.typography

@Composable
// Create a function named LocalTimeCard that takes a city, time and date string
fun LocalTimeCard(city: String, time: String, date: String) {
    /**
     * Use a Box function that fills the current width
     * and has a height of 140 dp and white background.
     * Box is a container that draws elements on top of one another
     */
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(Color.White)
            .padding(8.dp)
    ) {
        // Use a Card with rounded corners and a border. It also fills the width
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Black),
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            // Use a box to display the gradient background
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryColor,
                                primaryDarkColor,
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                // Create a row that fills the entire width
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Create a column for the left side of the card
                    Column(
                        horizontalAlignment = Alignment.Start

                    ) {
                        // Use a spacer with a weight modifier to push the text to the bottom
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(
                            "Your Location", style = typography.h4
                        )
                        Spacer(Modifier.height(8.dp))
                        // Display the city text with the given typography
                        Text(
                            city, style = typography.h2
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    // Push the right column over
                    Spacer(modifier = Modifier.weight(1.0f))
                    // Create the right column
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(modifier = Modifier.weight(1.0f))
                        // Show the time
                        Text(
                            time, style = typography.h1
                        )
                        Spacer(Modifier.height(8.dp))
                        // Show the date
                        Text(
                            date, style = typography.h3
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
