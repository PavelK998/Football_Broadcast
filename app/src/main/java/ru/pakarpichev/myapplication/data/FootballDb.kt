package ru.pakarpichev.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.pakarpichev.myapplication.domain.model.DatePickerModel

@Database(entities = [DatePickerModel::class], version = 1)
abstract class FootballDb: RoomDatabase() {
    abstract fun FootballDao(): FootballDao
}