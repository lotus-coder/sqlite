package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AgendaActivity extends AppCompatActivity {

    private ListView lvAgenda ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        lvAgenda = findViewById(R.id.lvContactos);
        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        AgendaSQLHelper usdbh = new AgendaSQLHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();//Si hemos abierto correctamente la base de datos
        if (db != null){
            //Insertamoa 5 usuarios de ejemplo
            for (int i =1; i<=5; i++){//Generamos los datos
                int codigo = i;
                String nombre ="Usuario "+i;
                String telefono = ""+(945123489+i);
                //Insertamos los datos en latabla de Usuarios
                db.execSQL("INSERT INTO contactos (id, nombre, telefono)" +" VALUES ("+ codigo +",'"+ nombre +"','" + telefono +"')");
            }//Cerramos la base de datos
            Cursor c =db.rawQuery("SELECT id, nombre, telefono FROM contactos " , null);
            String [] contactos = new String[c.getCount()];
            int j = 0;
            if (c.moveToFirst()){
                //Recorremos el cursor hasta que no haya más registros.
                do {
                    int codigo = c.getInt(0);
                    String nombre =c.getString(1);
                    String telefono = c.getString(2);
                    contactos[j] = (codigo +" -"+nombre + " -" +telefono );
                    j++;
                }while (c.moveToNext());
            }
            ArrayAdapter<String> adaptadorListView = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, contactos);
            lvAgenda.setAdapter(adaptadorListView);
        }


        db.close();
        }
}


