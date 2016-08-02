package com.example.silvia.dbproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silvia on 30/07/2016.
 */
public class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "personInfo";
    private static final String TABLE_PERSONE = "persone";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_IMAGE = "image";

    public DB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate (SQLiteDatabase db){
        String CREATE_PERSONE_TABLE = "" + "CREATE TABLE " + TABLE_PERSONE + "(" + KEY_ID + "INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT, " +
                KEY_SURNAME + " TEXT, " +
                KEY_NUMBER + " TEXT, " +
                KEY_ADDRESS + " TEXT, " +
                KEY_IMAGE + " TEXT " + ")";
        db.execSQL(CREATE_PERSONE_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PERSONE);
        onCreate(db);
    }

    public void cancella (){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE "+ TABLE_PERSONE);
        onCreate(db);
    }
    public void addPersona(Persona persona){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, persona.getName());
        values.put(KEY_SURNAME, persona.getSurname());
        values.put(KEY_NUMBER, persona.getNumber());
        values.put(KEY_ADDRESS, persona.getAddress());
        values.put(KEY_IMAGE, persona.getImage());

        db.insert(TABLE_PERSONE, null, values);
        db.close();
    }
    public Persona getPersona(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSONE,
                new String[] {KEY_ID, KEY_NAME, KEY_SURNAME, KEY_NUMBER, KEY_ADDRESS,KEY_IMAGE}, KEY_ID + "=?",
                new String [] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Persona persona = new Persona (Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );
        return persona;
    }
//    public void deletePersona(Persona persona){
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.delete(TABLE_PERSONE, KEY_ID + " = ?", new String[]{String.valueOf(persona.getId())});
//        db.close();
//    }
    public void addColumn (String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("ALTER TABLE " + TABLE_PERSONE + " ADD COLUMN "+ name + " STRING");
    }
    public void deletePersona(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_PERSONE, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteRow()
    {
        SQLiteDatabase db =  this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_PERSONE);
        db.close();
    }
    public int updatePersona(Persona persona){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, persona.getName());
        values.put(KEY_SURNAME, persona.getSurname());
        values.put(KEY_NUMBER, persona.getNumber());
        values.put(KEY_ADDRESS, persona.getAddress());
        values.put(KEY_IMAGE, persona.getImage());

        return db.update(TABLE_PERSONE, values, KEY_ID + " = ?",
                new String[] {String.valueOf(persona.getId())});
    }
    public List<Persona> getAllPersona(){
        List<Persona> personaList = new ArrayList<Persona>();
        String select = "SELECT * FROM " + TABLE_PERSONE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        Log.d("count", cursor.getCount() + " post rows");

        //Log.v("LOG",cursor.getString(1));
        if(cursor != null && cursor.moveToFirst()){
            do {
                Persona persona = new Persona();
                //persona.setId(Integer.parseInt(cursor.getString(0)));
                persona.setName(cursor.getString(1));
                persona.setSurname(cursor.getString(2));
                persona.setNumber(cursor.getString(3));
                persona.setAddress(cursor.getString(4));
                persona.setImage(cursor.getString(5));
                personaList.add(persona);
            } while (cursor.moveToNext());
        }
        return personaList;
    }
}
