package fr.im.salimi.projectmanager.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.im.salimi.projectmanager.data.entities.Task
import fr.im.salimi.projectmanager.data.entities.TaskAssignments
import fr.im.salimi.projectmanager.data.entities.relations.TaskWithDevelopers
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskDao: BaseDao<Task> {

    @Query("SELECT * FROM tasks")
    abstract override fun getAll(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE task_id = :id")
    abstract override suspend fun getById(id: Long): Task

    @Query("SELECT  t.task_id, t.name, t.description, t.finishing_date, t.function_id_fk, t.starting_date FROM tasks t " +
            "JOIN functions f ON f.function_id = t.function_id_fk " +
            "JOIN modules m ON m.module_id = f.module_id_fk " +
            "AND m.project_id_fk = :projectId")
    abstract fun getAllByProjectId(projectId: Long): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun assignTask(vararg taskAssignments: TaskAssignments)

    @Query("SELECT * FROM tasks WHERE task_id = :id")
    abstract suspend fun getTaskWithDevelopersByTaskId(id: Long): TaskWithDevelopers
}