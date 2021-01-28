package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BibliotecaActivity extends AppCompatActivity {

    private  SQLiteDatabase db;
    private ListView lvLibros;
    private Button btnCrear, btnEditar,btnEliminar,btnEjecutarEditar;
    private EditText etNombre,etAutor;
    private Boolean editar,eliminar;
    private int idLibro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);//Abrimos la base de datos "DBUsuarios" en modo de escritura
        lvLibros = findViewById(R.id.lvBiblioteca);
        btnCrear = findViewById(R.id.btnInsert);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        etAutor = findViewById(R.id.et1);
        etNombre = findViewById(R.id.et2);
        btnEjecutarEditar = findViewById(R.id.btnEditarTrue);
        editar = false;
        eliminar = false;
        BibliotecaSQLHelper usdbh = new BibliotecaSQLHelper(this, "DBUsuarios", null, 1);
        db = usdbh.getWritableDatabase();//Si hemos abierto correctamente la base de datos
        Cursor c = db.rawQuery("SELECT id, nombre, autor, prestadoA FROM libros " , null);
        if (db != null && c.getCount() < 1) {
            //Insertamoa 5 usuarios de ejemplo
            db.execSQL("DELETE FROM libros");
            for (int i = 1; i <= 5; i++) {//Generamos los datos
                int codigo = i;
                String nombre = "Libro " + i;
                String autor = "autor" + i ;
                String prestadoA = "biblioteca";
                //Insertamos los datos en latabla de Usuarios nombre TEXT, autor TEXT, prestadoA
                db.execSQL("INSERT INTO libros (id, nombre, autor,prestadoA)" + " VALUES (" + codigo + " ,'" + nombre + "','" + autor +  "','"+prestadoA+"')");
            }//Cerramos la base de datos
        }
        listar();
        eventos();
    }



    public void listar(){
        Cursor c =db.rawQuery("SELECT id, nombre, autor, prestadoA FROM libros " , null);
        String [] contactos = new String[c.getCount()];
        int j = 0;
        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                int codigo = c.getInt(0);
                String nombre =c.getString(1);
                String autor = c.getString(2);
                String prestado = c.getString(2);
                contactos[j] = (codigo +" - "+nombre + " - " +autor+" - "+ prestado);
                j++;
            }while (c.moveToNext());
        }
        ArrayAdapter<String> adaptadorListView = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, contactos);
        lvLibros.setAdapter(adaptadorListView);
    }

    public  void eventos(){
        lvLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Alternativa 1:
                String opcionSeleccionada = (String) parent.getItemAtPosition(position);
                long opcion = parent.getItemIdAtPosition(position);
                String []separadito =  opcionSeleccionada.split("-");
                idLibro = Integer.parseInt(opcionSeleccionada.substring(0,opcionSeleccionada.indexOf("-")).trim());
                if(eliminar){
                    db.execSQL("DELETE FROM libros where id = "+idLibro);
                    listar();
                }else if(editar){
                    etNombre.setText(separadito[1].trim());
                    etAutor.setText(separadito[2].trim());
                }
                //parent.getSelectedView();
                //lvLibros.setB
                Toast.makeText(BibliotecaActivity.this,"Opcion selecciona ("+(opcion+1) +") : "+ opcionSeleccionada,Toast.LENGTH_SHORT).show();
            }
        });
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAutor.getText().toString().equals("") || etNombre.getText().toString().equals("")){
                    Toast.makeText(BibliotecaActivity.this,"Rellena todos los campos",Toast.LENGTH_SHORT).show();
                }else{
                    db.execSQL("INSERT INTO libros (nombre, autor)" + " VALUES ('"+  etNombre.getText().toString() + "','" + etAutor.getText().toString() + "')");
                    Toast.makeText(BibliotecaActivity.this,"Registro creado correctamente",Toast.LENGTH_SHORT).show();
                    listar();
                    etNombre.setText("");
                    etAutor.setText("");
                }
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar = true;
                eliminar = false;
                btnEjecutarEditar.setVisibility(View.VISIBLE);
                btnEditar.setVisibility(View.INVISIBLE);
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar = false;
                eliminar = true;
            }
        });
        btnEjecutarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("update  libros set nombre ='"+etNombre.getText().toString()+"', autor = '"+etAutor.getText().toString()+"' where id = "+idLibro);
                btnEjecutarEditar.setVisibility(View.INVISIBLE);
                btnEditar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        db.close();
    }

}