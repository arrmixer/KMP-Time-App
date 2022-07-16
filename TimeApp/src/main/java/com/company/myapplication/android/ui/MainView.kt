package com.company.myapplication.android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.company.myapplication.android.theme.AppTheme

// defines two screens with their titles
sealed class Screen(val title: String) {
    object TimeZonesScreen : Screen("Timezones")
    object FindTimeScreen : Screen("Find Time")
}

// This defines a route, icon for that route and a content description
data class BottomNavigationItem(
    val route: String,
    val icon: ImageVector,
    val iconContentDescription: String
)

// This uses the material icons and the titles from the screen class
val bottomNavigationItems = listOf(
    BottomNavigationItem(
        Screen.TimeZonesScreen.title,
        Icons.Filled.Language,
        "Timezones"
    ),
    BottomNavigationItem(
        Screen.FindTimeScreen.title,
        Icons.Filled.Place,
        "Find Time"
    )
)

// Define this function as a composable
@OptIn(ExperimentalMaterialApi::class)
@Composable
// This function takes a function that can provide a top bar (toolbar on Android)
// and defaults to an empty composable
fun MainView(actionBarFun: topBarFun = { EmptyComposable() }) {
    // Hold the state for showing the add dialog
    val showAddDialog = remember { mutableStateOf(false) }

    // Hold the state containing a list of current time zone strings
    val currentTimezoneStrings = remember { SnapshotStateList<String>() }

    // Use the compose remember and mutableStateOf functions
    // to remember the state of the currently selected index
    val selectedIndex = remember { mutableStateOf(0) }

    // Use the theme
    AppTheme {
        // Scaffold implements the basic material design visual layout structure.
        Scaffold(
            topBar = {
                actionBarFun(selectedIndex.value)
            },
            floatingActionButton = {
                if (selectedIndex.value == 0) {
                    // For the first page, create a FloatingActionButton
                    FloatingActionButton(
                        // Use Compose’s Modifier function to add padding
                        modifier = Modifier
                            .padding(16.dp),
                        // Set a click listener. Set the variable to show the add dialog screen.
                        // Changing this value will cause a redraw of the screen
                        onClick = {
                            showAddDialog.value = true
                        }
                    ) {
                        // Use the Add icon for the FAB
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            },
            bottomBar = {
                // Create a BottomNavigation composable
                BottomNavigation(
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    // Use forEachIndexed to go through each item in your list of navigation items
                    bottomNavigationItems.forEachIndexed { i, bottomNavigationItem ->
                        // Create a new BottomNavigationItem
                        BottomNavigationItem(
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.Black,
                            label = {
                                Text(bottomNavigationItem.route, style = MaterialTheme.typography.h4)
                            },
                            // Set the icon field to the icon in your list
                            icon = {
                                Icon(
                                    bottomNavigationItem.icon,
                                    contentDescription = bottomNavigationItem.iconContentDescription
                                )
                            },
                            // Is this screen selected? Only if the selectedIndex value is the current index
                            selected = selectedIndex.value == i,
                            // Set the click listener. Change the selectedIndex value and the screen will redraw.
                            onClick = {
                                selectedIndex.value = i
                            }
                        )
                    }
                }
            }
        ) { padding ->
            if (showAddDialog.value) {
                AddTimeZoneDialog(
                    // 2
                    onAdd = { newTimezones ->
                        showAddDialog.value = false
                        for (zone in newTimezones) {
                            // 3
                            if (!currentTimezoneStrings.contains(zone)) {
                                currentTimezoneStrings.add(zone)
                            }
                        }
                    },
                    onDismiss = {
                        // 4
                        showAddDialog.value = false
                    },
                )
            }
            when (selectedIndex.value) {
                0 -> TimeZoneScreen(currentTimezoneStrings, padding)
                1 -> FindMeetingScreen(currentTimezoneStrings, padding)
            }
        }
    }
}
