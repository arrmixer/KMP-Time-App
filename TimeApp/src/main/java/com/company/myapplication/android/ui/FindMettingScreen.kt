package com.company.myapplication.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.myapplication.TimeZoneHelper
import com.company.myapplication.TimeZoneHelperImpl

// Create a composable that takes a list of time zone strings
@Composable
fun FindMeetingScreen(
    timezoneStrings: List<String>,
    padding: PaddingValues
) {
    val listState = rememberLazyListState()
    // Create some variables to hold the start and end hours. Default to 8 a.m. and 5 p.m
    // 8am
    val startTime = remember {
        mutableStateOf(8)
    }
    // 5pm
    val endTime = remember {
        mutableStateOf(17)
    }
    // Remember the selected time zones
    val selectedTimeZones = remember {
        val selected = SnapshotStateMap<Int, Boolean>()
        for (i in timezoneStrings.indices) selected[i] = true
        selected
    }
    // Create your time zone helper and remember some variables
    val timezoneHelper: TimeZoneHelper = TimeZoneHelperImpl()
    val showMeetingDialog = remember { mutableStateOf(false) }
    val meetingHours = remember { SnapshotStateList<Int>() }

    // If the boolean for this is true, show the MeetingDialog results
    if (showMeetingDialog.value) {
        MeetingDialog(
            hours = meetingHours,
            onDismiss = {
                showMeetingDialog.value = false
            }
        )
    }

    // Create a column that takes up the full width
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        // Add a Time Range header
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Time Range",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.size(16.dp))
        // Add a row that is centered horizontally
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),

            ) {
            // Add two NumberTimeCards with their labels and hour
            Spacer(modifier = Modifier.size(16.dp))
            NumberTimeCard("Start", startTime)
            Spacer(modifier = Modifier.size(32.dp))
            NumberTimeCard("End", endTime)
        }
        Spacer(modifier = Modifier.size(16.dp))
        // Add a row that takes up the full width and has a “Time Zones” header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = "Time Zones",
                style = MaterialTheme.typography.h6
            )
        }
        Spacer(modifier = Modifier.size(8.dp))

        /**
         * Add a LazyColumn for the list of selected time zones. Give it a weight and padding
         */
        LazyColumn(
            modifier = Modifier
                .weight(0.5F)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            state = listState,
        ) {
            // For each selected time zone, create a surface and row
            itemsIndexed(timezoneStrings) { i, timezone ->
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        // Create a checkbox that sets the selected map when clicked
                        Checkbox(checked = isSelected(selectedTimeZones, i),
                            onCheckedChange = {
                                selectedTimeZones[i] = it
                            })
                        Text(timezone, modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            }
        }
        Spacer(Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 4.dp, end = 4.dp)
        ) {
            // Create a button to start the search process and show the meeting dialog
            OutlinedButton(onClick = {
                meetingHours.clear()
                meetingHours.addAll(
                    timezoneHelper.search(
                        startTime.value,
                        endTime.value,
                        getSelectedTimeZones(timezoneStrings, selectedTimeZones)
                    )
                )
                showMeetingDialog.value = true
            }) {
                Text("Search")
            }
        }
        Spacer(Modifier.size(16.dp))
    }
}

/**
 * This is a helper function that will
 * return a list of selected time zones based on the selected state map
 */
fun getSelectedTimeZones(
    timezoneStrings: List<String>,
    selectedStates: Map<Int, Boolean>
): List<String> {
    val selectedTimezones = mutableListOf<String>()
    selectedStates.keys.map {
        val timezone = timezoneStrings[it]
        if (isSelected(selectedStates, it) && !selectedTimezones.contains(timezone)) {
            selectedTimezones.add(timezone)
        }
    }
    return selectedTimezones
}
