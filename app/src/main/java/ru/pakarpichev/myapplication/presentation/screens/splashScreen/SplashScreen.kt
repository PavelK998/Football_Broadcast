package ru.pakarpichev.myapplication.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import ru.pakarpichev.myapplication.R
import ru.pakarpichev.myapplication.domain.model.DatePickerModel
import ru.pakarpichev.myapplication.presentation.navigation.Screens
import ru.pakarpichev.myapplication.presentation.screens.splashScreen.SplashViewModel
import ru.pakarpichev.myapplication.utils.Constants
import java.time.LocalDate


@Composable
fun SplashScreen(navController: NavController) {
    val startDateDialogState = rememberMaterialDialogState()
    val endDateDialogState = rememberMaterialDialogState()
    val viewModel = hiltViewModel<SplashViewModel>()
    val date = viewModel.dateModel.collectAsState(initial = listOf()).value
    val isEmpty = viewModel.isEmpty.collectAsState().value
    var startDate by rememberSaveable() {
        mutableStateOf("")
    }
    var endDate by rememberSaveable() {
        mutableStateOf("")
    }
    var isOpen by remember {
        mutableStateOf(false)
    }
    viewModel.getDate()

    if (date.isEmpty() && isEmpty == true) {
        isOpen = true
    } else {
        isOpen = false
        LaunchedEffect(key1 = true) {
            delay(1000)
            navController.navigate(Screens.MainScreen.route) {
                popUpTo(Screens.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
    }
    if (isOpen) {
        AlertDialog(onDismissRequest = { isOpen = false },
            title = { Text(text = stringResource(id = R.string.dateSelection)) },
            text = { Text(text = stringResource(id = R.string.whyNeedDate)) },
            buttons = {
                Button(onClick = {
                    startDateDialogState.show()
                    isOpen = false
                }) {
                    Text(text = "OK")

                }
            })
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.soccer_palyer), contentDescription = ""
        )
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 15.dp),
            fontSize = 20.sp,
            text = stringResource(id = R.string.welcome)
        )
    }
    MaterialDialog(dialogState = startDateDialogState, buttons = {
        positiveButton(text = "Ок") {
            endDateDialogState.show()
        }
    }) {
        this.datepicker(
            initialDate = LocalDate.now(), title = stringResource(id = R.string.selectStartDate)
        ) {
            startDate = it.toString()
        }
    }
    MaterialDialog(dialogState = endDateDialogState, buttons = {
        positiveButton(text = "Ок") {
            Log.d("MYLOGASD", "MainScreen: $startDate, $endDate")
            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                isOpen = false
                viewModel.upsertDate(
                    DatePickerModel(
                        id = 1, startDate, endDate, league_id = Constants.PREMIER_LEAGUE_RU_ID
                    )
                )
            }
        }
    }) {
        this.datepicker(
            initialDate = LocalDate.now(), title = stringResource(id = R.string.selectEndDate)
        ) {
            endDate = it.toString()
        }
    }
}
