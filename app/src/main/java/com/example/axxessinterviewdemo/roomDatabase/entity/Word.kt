package com.example.axxessinterviewdemo.roomDatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commentsimage_table")
class Word(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "imageId") val imageID: String,
    @ColumnInfo(name = "comment") val comment:String)