package com.getcard.completepinpadexample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.getcard.completepinpadexample.database.daos.HubSettingsDao
import com.getcard.completepinpadexample.database.daos.ScopeSettingsDao
import com.getcard.completepinpadexample.database.daos.SitefSettingsDao
import com.getcard.completepinpadexample.database.daos.TransactionsDao
import com.getcard.completepinpadexample.database.models.HubSettingsModel
import com.getcard.completepinpadexample.database.models.ScopeSettingsModel
import com.getcard.completepinpadexample.database.models.SitefSettingsModel
import com.getcard.completepinpadexample.database.models.TransactionsModel

@Database(
    entities = [HubSettingsModel::class, ScopeSettingsModel::class, SitefSettingsModel::class, TransactionsModel::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DatabaseConverters::class)
abstract class HubDatabase : RoomDatabase() {
    abstract fun settingsDao(): HubSettingsDao
    abstract fun sitefSettingsDao(): SitefSettingsDao
    abstract fun scopeSettingsDao(): ScopeSettingsDao
    abstract fun transactionsDao(): TransactionsDao

    companion object {
        @Volatile
        private var INSTANCE: HubDatabase? = null

        fun getInstance(context: Context): HubDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    HubDatabase::class.java, "payment_hub"
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}
