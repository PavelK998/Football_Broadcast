package ru.pakarpichev.myapplication.domain.repository

import ru.pakarpichev.myapplication.domain.model.DatePickerModel

interface DbRepository {
    suspend fun upsertDate(model: DatePickerModel)
    suspend fun getDate(): List<DatePickerModel>
}