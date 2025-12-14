package com.example.calculaimc

import android.app.Application
import com.example.calculaimc.data.AppDatabase

class ImcApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}