package ru.pakarpichev.myapplication.presentation.screens.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.pakarpichev.myapplication.domain.model.DatePickerModel
import ru.pakarpichev.myapplication.presentation.useCase.DbUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val useCase: DbUseCase) : ViewModel() {
    fun upsertDate(model: DatePickerModel) {
        viewModelScope.launch {
            useCase.upsertDate(model)
        }
    }

    private val _dateModel = MutableStateFlow<List<DatePickerModel>>(listOf())
    val dateModel = _dateModel.asStateFlow()

    private val _isEmpty = MutableStateFlow<Boolean?>(null)
    val isEmpty = _isEmpty.asStateFlow()

    fun getDate() {
        viewModelScope.launch {
            useCase.getDate().let {
                _dateModel.value = it
                _isEmpty.value = it.isEmpty()
            }
        }
    }
}