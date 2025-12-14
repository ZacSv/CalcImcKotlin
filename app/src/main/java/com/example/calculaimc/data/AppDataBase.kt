package com.example.calculaimc.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalculationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun calculationDao(): CalculationDao

    /* GEMINI PRO - START
 Prompt:
 Knowing the context of the project we're working on, act as a Senior Android Architect. I need to implement the `getDatabase` method within the companion object of my `AppDatabase` (Room) class.
Implementation Requirements:
    The method should receive a `Context` and return the `AppDatabase`.
    Implement the Singleton pattern to ensure a single instance.
    Use the best possible syntax.
    In the builder, use the database name as 'imc_database'.
    Make sure to use `applicationContext` to avoid memory leaks.
The code should be in pure Kotlin, without using dependency injection libraries like Hilt for now. Don't forget to eliminate any code smells.
 */
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "imc_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
    /* GEMINI PRO - END */
}