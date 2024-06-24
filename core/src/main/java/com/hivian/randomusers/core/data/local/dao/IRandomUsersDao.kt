package com.hivian.randomusers.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hivian.randomusers.core.data.models.Name
import com.hivian.randomusers.core.data.models.RandomUserDTO

@Dao
interface IRandomUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg randomUserEntity: RandomUserDTO)

    @Update
    suspend fun update(vararg randomUserEntity: RandomUserDTO)

    @Delete
    suspend fun delete(vararg randomUserEntity: RandomUserDTO)

    @Query("DELETE FROM random_user_entity")
    suspend fun deleteAll()

    @Query("SELECT * FROM random_user_entity LIMIT :limit OFFSET :index")
    suspend fun getAllRandomUsers(index: Int, limit: Int) : List<RandomUserDTO>

    @Query("SELECT * FROM random_user_entity WHERE localId = :id")
    suspend fun getRandomUserById(id : Int) : RandomUserDTO

    @Query("SELECT * FROM random_user_entity WHERE name = :name LIMIT 1")
    suspend fun getRandomUserByServerId(name: Name): RandomUserDTO?

    @Transaction
    suspend fun upsert(randomUserDTO: RandomUserDTO) {
        val isRandomUser = getRandomUserByServerId(randomUserDTO.name)
        isRandomUser?.let {
            update(randomUserDTO.apply {
                localId = isRandomUser.localId
            })
        } ?: run {
            insert(randomUserDTO)
        }
    }

    @Transaction
    suspend fun upsert(randomUsersList: List<RandomUserDTO>) {
        randomUsersList.forEach { upsert(it) }
    }

}
