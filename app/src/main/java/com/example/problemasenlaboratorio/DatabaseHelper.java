package com.example.problemasenlaboratorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_FECHA = "fecha";
    private static final String KEY_LABORATORIO = "laboratorio";
    private static final String KEY_RUT = "rut";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_DESCRIPCION = "descripcion";


    private static final String CREATE_TABLE_PROBLEMAS = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FECHA + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "+ KEY_LABORATORIO + " TEXT, "+ KEY_RUT + " TEXT, "+ KEY_NOMBRE + " TEXT, "+ KEY_DESCRIPCION + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_PROBLEMAS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_TABLE_PROBLEMAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        onCreate(db);
    }

    public long addUserDetail(String laboratorio, String rut, String nombre, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_LABORATORIO, laboratorio);
        values.put(KEY_RUT, rut);
        values.put(KEY_NOMBRE, nombre);
        values.put(KEY_DESCRIPCION, descripcion);
       // insert row in students table
        long insert = db.insert(TABLE_USER, null, values);

        return insert;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                userModel.setFecha(c.getString(c.getColumnIndex(KEY_FECHA)));
                userModel.setLaboratorio(c.getString(c.getColumnIndex(KEY_LABORATORIO)));
                userModel.setRut(c.getString(c.getColumnIndex(KEY_RUT)));
                userModel.setNombre(c.getString(c.getColumnIndex(KEY_NOMBRE)));
                userModel.setDescripcion(c.getString(c.getColumnIndex(KEY_DESCRIPCION)));
               // adding to Students list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
         }
        return userModelArrayList;
    }

    public int updateUser(int id, String laboratorio, String rut, String nombre, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_LABORATORIO, laboratorio);
        values.put(KEY_RUT, rut);
        values.put(KEY_NOMBRE, nombre);
        values.put(KEY_DESCRIPCION, descripcion);
       // update row in students table base on students.is value
        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteUSer(int id) {

        // delete row in students table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

}

