package ru.pakarpichev.myapplication.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import ru.pakarpichev.myapplication.presentation.screens.mainScreen.MainViewModel
import java.time.LocalDate
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.pakarpichev.myapplication.R
import ru.pakarpichev.myapplication.domain.model.DatePickerModel
import ru.pakarpichev.myapplication.presentation.navigation.Screens
import ru.pakarpichev.myapplication.presentation.ui.EventCard
import ru.pakarpichev.myapplication.presentation.ui.Loading
import ru.pakarpichev.myapplication.ui.theme.card
import ru.pakarpichev.myapplication.utils.Constants


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<MainViewModel>()
    val date = viewModel.paickedDate.collectAsState(listOf()).value
    val eventsList = viewModel.data.collectAsState(initial = listOf()).value
    val isError = viewModel.error.collectAsState().value
    val startDateDialogState = rememberMaterialDialogState()
    val endDateDialogState = rememberMaterialDialogState()
    LaunchedEffect(key1 = true) {
        viewModel.getPickedDate()
    }
    var isOpen by remember() {
        mutableStateOf(false)
    }
    var isOpenLeaguePicker by remember() {
        mutableStateOf(false)
    }
    if (date.isNotEmpty()) {
        var startDate by rememberSaveable() {
            mutableStateOf(date[0].startDate)
        }
        var endDate by rememberSaveable() {
            mutableStateOf(date[0].endDate)
        }
        var league by rememberSaveable() {
            mutableStateOf(date[0].league_id)
        }
        if (eventsList.isEmpty() && isError == null) {
            viewModel.getData(date[0].startDate, date[0].endDate, date[0].league_id, context)
        }
        if (eventsList.isEmpty() && isError == "Fine") {
            Loading()
        } else if (isError == "Error") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.noMatchesFound)
                            + "\n\n" + stringResource(id = R.string.chosenRange) +
                            " ${startDate} - ${endDate}"
                )
                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {
                        startDateDialogState.show()
                    }) {
                    Text(text = stringResource(id = R.string.dateSelection))

                }
            }
        } else {
            if (isOpen) {
                AlertDialog(
                    backgroundColor = card,
                    onDismissRequest = { isOpen = false },
                    text = {
                        Column() {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    isOpen = false
                                    startDateDialogState.show()
                                }) {
                                Text(text = stringResource(id = R.string.dateSelection))
                            }
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    isOpen = false
                                    navController.navigate(Screens.WebView.route)
                                }) {
                                Text(text = stringResource(id = R.string.goWebsite))
                            }
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    isOpen = false
                                    isOpenLeaguePicker = true
                                }) {
                                Text(text = stringResource(id = R.string.league_chose))
                            }
                        }
                    },
                    buttons = {}
                )
            }
            if (isOpenLeaguePicker) {
                AlertDialog(
                    backgroundColor = card,
                    onDismissRequest = { isOpen = false },
                    text = {
                        Column() {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {
                                    league = Constants.PREMIER_LEAGUE_RU_ID
                                    viewModel.upsertDate(
                                        DatePickerModel(
                                            1,
                                            startDate,
                                            endDate,
                                            league
                                        )
                                    )
                                    viewModel.getData(startDate, endDate, league, context)
                                    isOpenLeaguePicker = false
                                }) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(30.dp)
                                    ) {
                                        AsyncImage(
                                            model = Constants.PREMIER_LEAGUE_RU,
                                            contentDescription = ""
                                        )
                                    }
                                    Text(text = stringResource(id = R.string.premier_ru))
                                }
                            }
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {
                                    league = Constants.PREMIER_LEAGUE_ID
                                    viewModel.upsertDate(
                                        DatePickerModel(
                                            1,
                                            startDate,
                                            endDate,
                                            league
                                        )
                                    )
                                    viewModel.getData(startDate, endDate, league, context)
                                    isOpenLeaguePicker = false
                                }) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(30.dp)
                                    ) {
                                        AsyncImage(
                                            model = Constants.PREMIER_LEAGUE,
                                            contentDescription = ""
                                        )
                                    }
                                    Text(text = stringResource(id = R.string.premier))
                                }
                            }
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {
                                    league = Constants.FA_CUP_ID
                                    viewModel.upsertDate(
                                        DatePickerModel(
                                            1,
                                            startDate,
                                            endDate,
                                            league
                                        )
                                    )
                                    viewModel.getData(startDate, endDate, league, context)
                                    isOpenLeaguePicker = false
                                }) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(30.dp)
                                    ) {
                                        AsyncImage(
                                            model = Constants.FA_CUP,
                                            contentDescription = ""
                                        )
                                    }
                                    Text(text = stringResource(id = R.string.fa))
                                }
                            }
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {
                                    league = Constants.LA_LIGA_ID
                                    viewModel.upsertDate(
                                        DatePickerModel(
                                            1,
                                            startDate,
                                            endDate,
                                            league
                                        )
                                    )
                                    viewModel.getData(startDate, endDate, league, context)
                                    isOpenLeaguePicker = false
                                }) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(30.dp)
                                    ) {
                                        AsyncImage(
                                            model = Constants.LA_LIGA,
                                            contentDescription = ""
                                        )
                                    }
                                    Text(text = stringResource(id = R.string.la))
                                }
                            }
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {
                                    league = Constants.LIGUE_1_ID
                                    viewModel.upsertDate(
                                        DatePickerModel(
                                            1,
                                            startDate,
                                            endDate,
                                            league
                                        )
                                    )
                                    viewModel.getData(startDate, endDate, league, context)
                                    isOpenLeaguePicker = false
                                }) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(30.dp)
                                    ) {
                                        AsyncImage(
                                            model = Constants.LIGUE_1,
                                            contentDescription = ""
                                        )
                                    }
                                    Text(text = stringResource(id = R.string.league_1))
                                }
                            }
                        }
                    },
                    buttons = {}
                )
            }
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = card
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 14.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clickable {
                                isOpen = true
                            }
                            .padding(start = 5.dp, end = 5.dp),
                        shape = RoundedCornerShape(7.dp),
                        elevation = 10.dp,
                        backgroundColor = card
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = stringResource(id = R.string.additionalActions)
                            )
                        }

                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(eventsList) {
                            EventCard(model = it)
                        }
                    }
                }
            }
        }
        MaterialDialog(
            dialogState = startDateDialogState,
            buttons = {
                positiveButton(text = "Ок") {
                    endDateDialogState.show()
                }
            }
        ) {
            this.datepicker(
                initialDate = LocalDate.now(),
                title = stringResource(id = R.string.selectStartDate)
            ) {
                startDate = it.toString()
            }
        }
        MaterialDialog(
            dialogState = endDateDialogState,
            buttons = {
                positiveButton(text = "Ок") {
                    viewModel.upsertDate(
                        DatePickerModel(
                            id = 1,
                            startDate,
                            endDate,
                            league
                        )
                    )
                    viewModel.getData(startDate, endDate, league, context)
                }
            }
        ) {
            this.datepicker(
                initialDate = LocalDate.now(),
                title = stringResource(id = R.string.selectEndDate)
            ) {
                endDate = it.toString()
            }
        }
    }
}







