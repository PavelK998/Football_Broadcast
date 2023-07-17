package ru.pakarpichev.myapplication.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.pakarpichev.myapplication.domain.model.DatePickerModel

@Dao
interface FootballDao {
    @Upsert
    suspend fun upsertDate (datePickerModel: DatePickerModel)
    @Query("SELECT * FROM dateTable")
    suspend fun getDate(): List<DatePickerModel>
}