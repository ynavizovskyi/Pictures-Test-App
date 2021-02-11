package com.ynavizovskyi.picturestestapp.datastore.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

abstract class BaseRoomDao<E> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: E): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entities: List<E>): List<Long>

    @Update
    abstract suspend fun update(entity: E)

    @Update
    abstract suspend fun update(entities: List<E>)

    @Delete
    abstract suspend fun delete(entities: List<E>)

    @Delete
    abstract suspend fun delete(entitiy: E)


}