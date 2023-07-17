package ru.pakarpichev.myapplication.data

import ru.pakarpichev.myapplication.domain.model.DatePickerModel
import ru.pakarpichev.myapplication.domain.repository.DbRepository
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(private val dao: FootballDao): DbRepository {
    override suspend fun upsertDate(model: DatePickerModel) {
        return dao.upsertDate(model)
    }
    override suspend fun getDate(): List<DatePickerModel> {
        return dao.getDate()
    }
}