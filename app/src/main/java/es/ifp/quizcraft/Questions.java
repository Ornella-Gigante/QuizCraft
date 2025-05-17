package es.ifp.quizcraft;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "questions_table")
public class Questions {

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "id")
    private String question;
    @ColumnInfo(name = "id")
    private String opA;
    @ColumnInfo(name = "id")
    private String opB;
    @ColumnInfo(name = "id")
    private String opC;
    @ColumnInfo(name = "id")
    private int answer;



}
