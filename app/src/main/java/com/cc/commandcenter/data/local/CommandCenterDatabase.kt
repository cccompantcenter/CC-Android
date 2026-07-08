package com.cc.commandcenter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CommandCenterDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}
