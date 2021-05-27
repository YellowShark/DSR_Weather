package ru.yellowshark.dsr_weather.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single
import ru.yellowshark.dsr_weather.data.db.entity.TriggerEntity

@Dao
interface TriggersDao {
    @Insert
    fun insertTrigger(trigger: TriggerEntity): Completable

    @Query("SELECT * FROM trigger_entity")
    fun getTriggers(): Single<List<TriggerEntity>>

    @Update
    fun updateTrigger(trigger: TriggerEntity): Completable
}