package com.company.myapplication.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.company.myapplication.TimeZoneHelper
import com.company.myapplication.TimeZoneHelperImpl
import kotlinx.coroutines.delay

const val timeMillis = 1000 * 60L // 1 second

@ExperimentalMaterialApi
@Composable
fun TimeZoneScreen(
    currentTimezoneStrings: SnapshotStateList<String>,
    padding: PaddingValues
) {
    // Create an instance of your TimeZoneHelper clas
    val timezoneHelper: TimeZoneHelper = TimeZoneHelperImpl()

    // Remember the state of the list that will be defined later.
    val listState = rememberLazyListState()
    
    // Create a vertical column that takes up the full width and height
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        // Remember the current time
        var time by remember { mutableStateOf(timezoneHelper.currentTime()) }
        /**
         * Use Compose’s LaunchedEffect. It will be launched once but continue to run.
         * The method will get the updated time every minute. You pass Unit as a parameter
         * to LaunchedEffect so that it is not canceled and re-launched when LaunchedEffect
         * is recomposed.
         *
         */
        LaunchedEffect(Unit) {
            while (true) {
                time = timezoneHelper.currentTime()
                delay(timeMillis) // Every minute
            }
        }
        /**
         * Use the LocalTimeCard function you created earlier.
         * Use TimeZoneHelper’s methods to get the current time zone and current date.
         */
        LocalTimeCard(
            city = timezoneHelper.currentTimeZone(),
            time = time, date = timezoneHelper.getDate(timezoneHelper.currentTimeZone())
        )
        Spacer(modifier = Modifier.size(16.dp))
        /**
         * Use Compose’s LazyColumn function, which is like Android’s RecyclerView
         * or iOS’s UITableView
         */
        LazyColumn(
            state = listState
        ) {
            // Use LazyColumn’s items method to go through the list of time zones.
            items(currentTimezoneStrings,
                /**
                 * Use the key field to set the unique key for each row.
                 * This is important if you need to delete items.
                 */
                key = { timezone ->
                    timezone
                }) { timezoneString ->
                /**
                 * Use the included AnimatedSwipeDismiss class to handle swiping away a row.
                 */
                AnimatedSwipeDismiss(
                    item = timezoneString,
                    // Set the background that will show when swiping
                    background = { _ ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(50.dp)
                                .background(Color.Red)
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp
                                )
                        ) {
                            val alpha = 1f
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                tint = Color.White.copy(alpha = alpha)
                            )
                        }
                    },
                    content = {
                        // Set the content that will show over the background
                        TimeCard(
                            timezone = timezoneString,
                            hours = timezoneHelper.hoursFromTimeZone(timezoneString),
                            time = timezoneHelper.getTime(timezoneString),
                            date = timezoneHelper.getDate(timezoneString)
                        )
                    },
                    // When the row is swiped away, remove the time zone string from your list
                    onDismiss = { zone ->
                        if (currentTimezoneStrings.contains(zone)) {
                            currentTimezoneStrings.remove(zone)
                        }
                    }
                )
            }
        }
    }
}
