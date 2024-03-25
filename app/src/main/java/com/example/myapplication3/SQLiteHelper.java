package com.example.myapplication3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    public static final String key = null;
    public static final String table = null;


    SQLiteDatabase sqLiteDatabase;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void InsertAL(String tableIAL, List InputAL) {
        SQLiteDatabase db = this.getWritableDatabase();
        String inputStrAL = new String();
        inputStrAL = "";
        for (int i = 0; i < InputAL.size(); i++) {
            if (i > 0) {
                inputStrAL = inputStrAL + ",";
            }
            inputStrAL = inputStrAL + "'" + String.valueOf(InputAL.get(i)) + "'";

        }

        System.out.println(inputStrAL);

        db.execSQL("INSERT INTO " + tableIAL + " VALUES (" + inputStrAL + ");");

        return;
    }

    public void InsertRoutine(String RNAME) {
        List list2 = null;
        Integer SN = 0;
        for (int i = 0; i < 10; i++) {
            SN = getLastNum("Routine");
            list2 = List.of(String.valueOf(SN), String.valueOf(RNAME), String.valueOf(i), "중량", "세트수", "운동시간", "휴식시간", "횟수");
            InsertAL("Routine", list2);
        }
        return;
    }

    public void UpdateData(String tableUD, String UpdateCL, String UpdateDT, String KeyCU, String KeyDU) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + tableUD + " SET " + UpdateCL + "='" + UpdateDT + "' WHERE " + KeyCU + "='" + KeyDU + "';");

        return;
    }

    public void UpdateData2(String tableUD, String UpdateCL, String UpdateDT, String KeyCU1, String KeyDU1, String KeyCU2, String KeyDU2) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + tableUD + " SET " + UpdateCL + "='" + UpdateDT + "' WHERE " + KeyCU1 + "='" + KeyDU1 + " , " + KeyCU2 + "='" + KeyDU2 + "';");

        return;
    }

    public void UpdateRoutineTemp(ArrayList<ArrayList<String>> inputList){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> inputLine = new ArrayList<>();

        this.DeleteRoutineTemp();

        for (int i = 0; i < inputList.size(); i++){
            inputLine = inputList.get(i);
            inputLine.add(0, this.getSaveNum("RoutineTemp").toString());
            this.InsertAL("RoutineTemp",inputLine);
        }

    }

    public void DeleteLine(String tableDL, String KeyCD, String KeyDD) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableDL + " WHERE " + KeyCD + " = '" + KeyDD + "';");

        return;
    }

    public void DeleteRoutineTemp(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  RoutineTemp;");

        return;
    }

    public ArrayList<String> getLineStr(String tableL, String ColumnNameL, String strL, Integer MaCoL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableL + " WHERE " + ColumnNameL + "='" + strL + "'", null);
        // Members 테이블에서 Name 컬럼이 변수 'abc'의 값과 같은 행 지정 (변수를 쓸려면 변수 앞뒤에 작은 따옴표 필요)
        ArrayList<String> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            for (int i = 0; i < MaCoL; i++) {
                result.add(cursor.getString(i));
            }
            // 반복문을 통해 해당 행의 모든 데이터를 뽑아옴
            // table은 abc는 키값, MaCo는 테이블의 컬럼 수
            // for문의 조건식이 i<MaCo 가 되도록 함, 크면 오류 발생
        }
        cursor.close();
        return result;
    }

    public String getData(String tableD, String columnName, String keyColumn, String keyData){
        SQLiteDatabase db = this.getReadableDatabase();
        String returnString = new String();
        Cursor cursor = db.rawQuery("SELECT " +columnName+ " FROM " +tableD+ " WHERE " +keyColumn+ " = '" +keyData+ "'", null);
        while (cursor.moveToNext()){
            returnString = cursor.getString(0);
        }

        return returnString;
    }

    public ArrayList<String> getRowStr(String tableR, String ColumnNameR) {
        SQLiteDatabase db = this.getReadableDatabase();
        //DB 선언
        Cursor cursor = db.rawQuery("SELECT " + ColumnNameR + " FROM " + tableR + "", null);
        // select 쿼리문
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }

    public ArrayList<List> getAllStr(String tableA, Integer MaCoA) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableA + "", null);
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> result1 = new ArrayList<>();
        ArrayList<List> result2 = new ArrayList<>();

        while (cursor.moveToNext()) {

            for (int i = 0; i < MaCoA; i++) {
                result.add(cursor.getString(i));
            }
        }

        cursor.close();

        for (int i = 0; i < (result.size() / MaCoA); i++) {
            result2.add(result.subList(i * MaCoA, i * MaCoA + MaCoA));
        }

        return result2;
    }

    public Integer getSaveNum(String tableSN) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer SaveNum = 0;
        ArrayList<String> SNList = new ArrayList<>();

        SNList = this.getRowStr(tableSN, "ID");
        // 위의 메소드의 입력값 "mID"는 사용할 테이블의 유일키에 맞게 수정(이후 "ID"로 통일 예정)
        System.out.println(SNList);

        for (int i = 0; i < SNList.size(); i++) {
            if (Integer.valueOf(SNList.get(i)) != i + 1) {
                return i + 1;
            }
        }

        return SNList.size() + 1;
    }

    public Integer getLastNum(String tableLN){
        SQLiteDatabase db = this.getReadableDatabase();
        Integer LastNum = 0;
        ArrayList<String> LNList = new ArrayList<>();

        LNList = this.getRowStr(tableLN, "ID");
        LastNum = Integer.valueOf(LNList.get(LNList.size()-1)) + 1;
        System.out.println(LastNum);
        return LastNum;
    }

    public ArrayList<String> getRowRoutine(String ColumnR, String RRNAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        //DB 선언
        Cursor cursor = db.rawQuery("SELECT " + ColumnR + " FROM Routine WHERE RNAME='" +RRNAME+ "'", null);
        // select 쿼리문
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }

    public ArrayList<String> getRowRoutineTemp(String ColumnR, String RTNAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        //DB 선언
        Cursor cursor = db.rawQuery("SELECT " + ColumnR + " FROM RoutineTemp WHERE RNAME ='" + RTNAME + "'", null);
        // select 쿼리문
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }

    public ArrayList<String> getAllRowRoutine() {
        SQLiteDatabase db = this.getReadableDatabase();
        //DB 선언
        Cursor cursor = db.rawQuery("SELECT RNAME FROM Routine", null);
        // select 쿼리문
        ArrayList<String> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        cursor.close();

        ArrayList<String> result2 = (ArrayList<String>) result.stream().distinct().collect(Collectors.toList());

        return result2; // 중복을 제거한 상태로 출력됨
    }

    public ArrayList<String> getRoutine(String RRNAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        //DB 선언
        Cursor cursor = db.rawQuery("SELECT *  FROM Routine WHERE RNAME='" +RRNAME+ "'", null);
        // select 쿼리문
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> result1 = new ArrayList<>();
        ArrayList<List> result2 = new ArrayList<>();
        ArrayList<String> result3 = new ArrayList<>();

        while (cursor.moveToNext()) {

            for (int i = 0; i < 8; i++) {
                result.add(cursor.getString(i));
            }
        }

        cursor.close();

        for (int i = 0; i < (result.size() / 8); i++) {
            result2.add(result.subList(i * 8, i * 8 + 8));
        }

        for (int i = 0; i < result2.size(); i++){
            List listWork = new ArrayList();
            listWork = result2.get(i);
            result3.add(listWork.get(0)+
                    ", 운동종목: "  +listWork.get(2)+
                    ", 중량: " +listWork.get(3)+
                    ", 세트: " +listWork.get(4)+
                    ", 반복: " +listWork.get(7)+
                    ", 운동시간: " +listWork.get(5)+
                    ", 휴식시간: " +listWork.get(6)
                    );
            }


        return result3;
    }

    public ArrayList<List> getRoutineList(String RRNAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        //DB 선언
        Cursor cursor = db.rawQuery("SELECT *  FROM Routine WHERE RNAME='" +RRNAME+ "'", null);
        // select 쿼리문
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> result1 = new ArrayList<>();
        ArrayList<List> result2 = new ArrayList<>();
        ArrayList<String> result3 = new ArrayList<>();

        while (cursor.moveToNext()) {

            for (int i = 0; i < 8; i++) {
                result.add(cursor.getString(i));
            }
        }

        cursor.close();

        for (int i = 0; i < (result.size() / 8); i++) {
            result2.add(result.subList(i * 8, i * 8 + 8));
        }

        return result2;
    }

    public ArrayList<List> getAllAListList(String tableName,Integer MaxCol) {
        SQLiteDatabase db = this.getReadableDatabase();
        //DB 선언
        Cursor cursor = db.rawQuery("SELECT * FROM " +tableName, null);
        // select 쿼리문
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> result1 = new ArrayList<>();
        ArrayList<List> result2 = new ArrayList<>();
        ArrayList<String> result3 = new ArrayList<>();

        while (cursor.moveToNext()) {

            for (int i = 0; i < MaxCol; i++) {
                result.add(cursor.getString(i));
            }
        }

        cursor.close();

        for (int i = 0; i < (result.size() / MaxCol); i++) {
            result2.add(result.subList(i * MaxCol, i * MaxCol + MaxCol));
        }

        return result2;
    }

    public ArrayList<List> StrConverter(ArrayList<String> inputList){
        ArrayList<List> returnList = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++){
            returnList.add(List.of(inputList.get(i).split(", ")));
        }

        return returnList;
    }

    public ArrayList<String> getColumnsFromAchieveInfo() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("PRAGMA table_info(AchieveInfo)", null);

        ArrayList<String> columnNames = new ArrayList<>();

        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex("name");
            if (columnIndex != -1) {
                columnNames.add(cursor.getString(columnIndex));
            }
        }

        cursor.close();

        return columnNames;
    }
}

