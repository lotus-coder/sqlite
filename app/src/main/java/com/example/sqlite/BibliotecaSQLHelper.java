package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BibliotecaSQLHelper extends SQLiteOpenHelper {
    //Sentencia SQL para crear la tabla de Usuarios
        String sqlCreate ="CREATE TABLE IF NOT EXISTS libros (id INTEGER PRIMARY KEY," +" nombre TEXT, autor TEXT, prestadoA TEXT)";
        public BibliotecaSQLHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
            super(contexto, nombre, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //Se ejecuta la sentencia SQL de creación de la tabla
            db.execSQL(sqlCreate);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            /*NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de eliminar la tabla anterior y crearla de nuevo vaciacon el nuevo formato.Sin embargo lo normal será que haya que migrar datos de la tabla antigua a la nueva, por lo que este método deberia ser más elaborado.*///Se elimina la versión anterior de la tabla
            db.execSQL(sqlCreate);
        }
}