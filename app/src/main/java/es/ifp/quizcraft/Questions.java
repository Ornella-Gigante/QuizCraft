package es.ifp.quizcraft;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "questions_table")
public class Questions {

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "question")
    private String question;
    @ColumnInfo(name = "opA")
    private String opA;
    @ColumnInfo(name = "opB")
    private String opB;
    @ColumnInfo(name = "opC")
    private String opC;
    @ColumnInfo(name = "opD")
    private String opD;
    @ColumnInfo(name = "answer")
    private int answer;

    public Questions(String question, String opA, String opB, String opC, String opD, int answer) {
        this.question = question;
        this.opA = opA;
        this.opB = opB;
        this.opC = opC;
        this.opD = opD;
        this.answer = answer;
    }


    public Questions() {
        this.id= id;
        this.question = question;
        this.opA = opA;
        this.opB = opB;
        this.opC = opC;
        this.opD = opD;
        this.answer = answer;
    }



    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOpA() {
        return opA;
    }

    public void setOpA(String opA) {
        this.opA = opA;
    }

    public String getOpB() {
        return opB;
    }

    public void setOpB(String opB) {
        this.opB = opB;
    }

    public String getOpC() {
        return opC;
    }

    public void setOpC(String opC) {
        this.opC = opC;
    }

    public String getOpD() {
        return opD;
    }

    public void setOpD(String opD) {
        this.opD = opD;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getCorrectOptionText() {
        switch (answer) {
            case 1: return opA;
            case 2: return opB;
            case 3: return opC;
            case 4: return opD;
            default: return "";
        }
    }

}
