package com.example.houseapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DatabaseRequest::class, DatabaseUser::class], version = 4)
abstract class LocalDatabase : RoomDatabase() {

    abstract val requestDaoLocal: RequestDaoLocal
    abstract val userDaoLocal: UserDaoLocal

    companion object {
        @Volatile
        private var _instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase =
            _instance ?: synchronized(this) {
                _instance ?: buildDatabase(context).also { _instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java,
                "localdatabase"
            ).build()
    }
}