package fr.im.salimi.projectmanager.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import fr.im.salimi.projectmanager.data.entities.Feature
import fr.im.salimi.projectmanager.data.entities.relations.FeatureWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FeatureDao: BaseDao<Feature> {

    @Query("SELECT * FROM features")
    abstract override fun getAll(): Flow<List<Feature>>

    @Query("SELECT * FROM features WHERE feature_id= :id")
    abstract override suspend fun getById(id: Long): Feature

    @Query("SELECT * FROM features WHERE project_id_fk = :projectId")
    abstract fun getAllByProjectId(projectId: Long): Flow<List<Feature>>

    @Query("SELECT * FROM features WHERE feature_id = :id")
    @Transaction
    abstract fun getByIdWithTasks(id: Long): Flow<FeatureWithTasks>

    @Query("SELECT * FROM features")
    @Transaction
    abstract fun getAllWithTasks(): Flow<FeatureWithTasks>
}