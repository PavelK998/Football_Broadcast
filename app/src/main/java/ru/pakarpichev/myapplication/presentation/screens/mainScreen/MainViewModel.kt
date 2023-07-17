package ru.pakarpichev.myapplication.presentation.screens.mainScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.pakarpichev.myapplication.R
import ru.pakarpichev.myapplication.domain.model.DatePickerModel
import ru.pakarpichev.myapplication.domain.model.EventsModel
import ru.pakarpichev.myapplication.presentation.useCase.DbUseCase
import ru.pakarpichev.myapplication.presentation.useCase.MainUseCase
import ru.pakarpichev.myapplication.utils.Constants
import java.net.SocketTimeoutException
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MainUseCase, private val dbUseCase: DbUseCase
) : ViewModel() {

    private val _data = MutableStateFlow<List<EventsModel>>(listOf())
    val data = _data.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun getData(startDate: String, endDate: String, id: String, context: Context) {
        viewModelScope.launch {
            try {
                _error.value = "Fine"
                useCase.getData(Constants.ACTION, startDate, endDate, id, Constants.API_KEY).let {
                    _data.value = it
                }
            } catch (e: java.lang.IllegalStateException) {
                _error.value = "Error"
            } catch (e: SocketTimeoutException) {
                Toast.makeText(context, context.getString(R.string.no_internet), Toast.LENGTH_LONG)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(context, context.getString(R.string.no_internet), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private val _pickedDate = MutableStateFlow<List<DatePickerModel>>(listOf())
    val paickedDate = _pickedDate.asStateFlow()
    fun getPickedDate() {
        viewModelScope.launch {
            dbUseCase.getDate().let {
                _pickedDate.value = it
            }
        }
    }

    fun upsertDate(model: DatePickerModel) {
        viewModelScope.launch {
            dbUseCase.upsertDate(model)
        }
    }
}