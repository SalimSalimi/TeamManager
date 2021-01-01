package fr.im.salimi.projectmanager.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "team_id")
        override var id: Long = 0,
        var name: String = "",
        @ColumnInfo(name = "leader_id")
        var leaderId: Long = 0,
        var description: String = ""
) : BaseEntity(id)