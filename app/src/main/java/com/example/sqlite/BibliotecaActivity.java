package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class BibliotecaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//Abrimos la base de datos "DBUsuarios" en modo de escritura
        BibliotecaSQLHelper usdbh = new BibliotecaSQLHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();//Si hemos abierto correctamente la base de datos
        if (db != null) {
            //Insertamoa 5 usuarios de ejemplo
            for (int i = 1; i <= 5; i++) {//Generamos los datos
                int codigo = i;
                String nombre = "Libro " + i;
                String autor = "autor" + i ;
                String prestadoA = "biblioteca";
                //Insertamos los datos en latabla de Usuarios
                db.execSQL("INSERT INTO libros (id, nombre, autor,prestadoA)" + " VALUES (" + codigo + " ,'" + nombre + "','" + autor +  "','"+prestadoA+"')");
            }//Cerramos la base de datos
            db.close();
        }
    }
}