package ru.yellowshark.dsr_weather.data.db.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import ru.yellowshark.dsr_weather.data.db.entity.TriggerEntity

@Dao
interface TriggersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrigger(trigger: TriggerEntity): Completable

    @Query("SELECT * FROM triggers")
    fun getTriggers(): Single<List<TriggerEntity>>

    @Update
    fun updateTrigger(trigger: TriggerEntity): Completable

    @Query("DELETE FROM triggers WHERE id =:triggerId")
    fun deleteTrigger(triggerId: String): Completable
}