package ru.pakarpichev.myapplication.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dateTable")
data class DatePickerModel (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    @ColumnInfo(name = "end_date")
    val endDate: String,
    @ColumnInfo(name = "url")
    val league_id: String
)