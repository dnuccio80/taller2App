package com.example.taller2app.application.data.debitCreditBalance

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditDebitBalanceDao {

    @Query("SELECT COALESCE((SELECT amount FROM CreditDebitBalanceEntity LIMIT 1), 120)")
    fun getCurrentAmount():Flow<Int>

    @Query("INSERT INTO CreditDebitBalanceEntity (amount) SELECT 100 WHERE NOT EXISTS (SELECT 1 FROM CreditDebitBalanceEntity LIMIT 1) ")
    suspend fun insertInitialAmount()

    @Query("UPDATE CreditDebitBalanceEntity SET amount = amount + :value")
    suspend fun incrementAmount(value: Int)

    @Query("UPDATE CreditDebitBalanceEntity SET amount = amount - :value")
    suspend fun decrementAmount(value: Int)

    @Query("UPDATE CreditDebitBalanceEntity SET amount = 0")
    suspend fun clearAmount()

}