package ru.pakarpichev.myapplication.presentation.useCase

import ru.pakarpichev.myapplication.domain.model.DatePickerModel
import ru.pakarpichev.myapplication.domain.repository.DbRepository
import javax.inject.Inject

class DbUseCase @Inject constructor(private val repository: DbRepository) {
    suspend fun upsertDate(model:DatePickerModel) = repository.upsertDate(model)
    suspend fun getDate() = repository.getDate()
}