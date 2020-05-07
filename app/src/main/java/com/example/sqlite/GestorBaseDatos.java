package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
// Nombre: Jose Rodrigo Fuentes Ramirez
// DNI: 03241996P
public class GestorBaseDatos extends SQLiteOpenHelper {

    public GestorBaseDatos(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE agenda (nombre TEXT,Apellido TEXT,Direccion TEXT,Telefono TEXT,Email TEXT)");
    }
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva){
        db.execSQL("DROP TABLE IF EXISTS agenda");
        db.execSQL("CREATE TABLE agenda (nombre TEXT,Apellido TEXT,Direccion TEXT,Telefono TEXT,Email TEXT)");
        //db.execSQL("CREATE TABLE agenda (nombre TEXT, Apellido TEXT,Direccion TEXT,Telefono TEXT,Email TEXT)");
    }

}