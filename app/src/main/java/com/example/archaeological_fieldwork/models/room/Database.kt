package com.example.archaeological_fieldwork.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.archaeological_fieldwork.models.HillfortModel
import com.example.archaeological_fieldwork.models.room.HillfortDao

@Database(entities = arrayOf(HillfortModel::class), version = 1,  exportSchema = false)
abstract class Database : RoomDatabase() {

  abstract fun hillfortDao(): HillfortDao
}