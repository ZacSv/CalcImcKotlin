package com.example.calculaimc.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculationDao {
    @Insert
    suspend fun insert(calculation: CalculationEntity)

    // Traz a lista ordenada do mais recente para o mais antigo
    @Query("SELECT * FROM calculation_history ORDER BY dataCalculo DESC")
    fun getAll(): Flow<List<CalculationEntity>>

    @Query("SELECT * FROM calculation_history WHERE id = :id")
    fun getById(id: Int): Flow<CalculationEntity>
}