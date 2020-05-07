package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
// Nombre: Jose Rodrigo Fuentes Ramirez
// DNI: 03241996P

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);
    }

    public void GuardarDatos(View v){
        EditText etNombre = (EditText) findViewById(R.id.etNombre);//Leo el nombre de la etiqueta
        EditText etApellido = (EditText) findViewById(R.id.etApellido);
        EditText etTelefono = (EditText) findViewById(R.id.etTelefono);
        EditText etDireccion = (EditText) findViewById(R.id.etDireccion);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        String Nombre = etNombre.getText().toString();// Extraigo Nombre
        String Apellido = etApellido.getText().toString();// Extraigo Apellido
        String Direccion = etDireccion.getText().toString();// Extraigo Direccion
        String Telefono = etTelefono.getText().toString();// Extraigo Ciudad
        String Email= etEmail.getText().toString();// Extraigo Codigo Postal
        try {
            GestorBaseDatos gbd = new GestorBaseDatos(getApplicationContext(), "agenda", null, 1);
            SQLiteDatabase bd = gbd.getWritableDatabase();
            bd.execSQL("INSERT INTO agenda (nombre, Apellido, Direccion, Telefono, Email) VALUES ('"+Nombre+"','"+Apellido+"','"+Direccion+"','"+Telefono+"','"+Email+"')");
            bd.close();
            etNombre.setText("");
            etApellido.setText("");
            etTelefono.setText("");
            etDireccion.setText("");
            etEmail.setText("");
            Toast.makeText(this, "¡DATOS PERSONALES GUARDADOS!", Toast.LENGTH_LONG).show();
        }catch(SQLException sqlError) {
            Toast.makeText(this, "No se puede insertar" + sqlError.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void GuardarDatosComprobando(final View v){
        EditText etNombre = (EditText) findViewById(R.id.etNombre);//Leo el nombre de la etiqueta
        EditText etApellido = (EditText) findViewById(R.id.etApellido);//Leo el nombre de la etiqueta
        EditText etTelefono = (EditText) findViewById(R.id.etTelefono);//Leo el nombre de la etiqueta
        EditText etDireccion = (EditText) findViewById(R.id.etDireccion);//Leo el nombre de la etiqueta
        EditText etEmail = (EditText) findViewById(R.id.etEmail);//Leo el nombre de la etiqueta

        String Nombre = etNombre.getText().toString();// Extraigo Nombre
        String Apellido = etApellido.getText().toString();// Extraigo Apellido
        String Direccion = etDireccion.getText().toString();// Extraigo Direccion
        String Telefono = etTelefono.getText().toString();// Extraigo Ciudad
        String Email= etEmail.getText().toString();// Extraigo Codigo Postal

        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Cuadro de dialogo
        builder.setTitle("LOS DATOS NO EXISTEN: ¿Desea guardarlos?"); // Titulo
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Nombre: " + Nombre + '\n' +"Apellido: "+ Apellido +'\n'+"Direccion: "+ Direccion +'\n'+"Telefono: "+ Telefono +'\n'+"Email: "+Email);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        GuardarDatos(v);
                    }
                }
        );
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                }
        );
        builder.show();
    }

    public void Modificar(View v){
        EditText etNombre = (EditText) findViewById(R.id.etNombre); // leo el nombre
        EditText etApellido = (EditText) findViewById(R.id.etApellido);
        EditText etTelefono = (EditText) findViewById(R.id.etTelefono);
        EditText etDireccion = (EditText) findViewById(R.id.etDireccion);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        String Nombre  = etNombre.getText().toString();
        String Apellido = etApellido.getText().toString();// Extraigo Apellido
        String Direccion = etDireccion.getText().toString();// Extraigo Direccion
        String Telefono = etTelefono.getText().toString();// Extraigo Ciudad
        String Email= etEmail.getText().toString();// Extraigo Codigo Postal
        try {
            GestorBaseDatos gbd = new GestorBaseDatos(getApplicationContext(), "agenda", null, 1);
            SQLiteDatabase bd = gbd.getReadableDatabase();
            Cursor c = bd.rawQuery("SELECT nombre FROM agenda where nombre like '"+Nombre+"'", null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    Nombre = c.getString(0);
                    bd.execSQL("UPDATE agenda SET nombre = '"+Nombre+"', Apellido = '"+Apellido+"',Direccion = '"+Direccion+"',Telefono = '"+Telefono+"',Email = '"+Email+"' WHERE nombre = '"+Nombre+"';");
                    bd.close();
                    Toast.makeText(this, "Los datos de " + Nombre + " se ACTUALIZARON!", Toast.LENGTH_LONG).show();
                    etNombre.setText("");
                    etApellido.setText("");
                    etTelefono.setText("");
                    etDireccion.setText("");
                    etEmail.setText("");
                } while(c.moveToNext());
            }else {
                GuardarDatosComprobando(v);
            }
        }catch(SQLException sqlError) {
            Toast.makeText(this, "No se puede leer: " + sqlError.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void LeerDatos(View v){
        EditText etNombre = (EditText) findViewById(R.id.etNombre); // leo el nombre
        EditText etApellido = (EditText) findViewById(R.id.etApellido);
        EditText etTelefono = (EditText) findViewById(R.id.etTelefono);
        EditText etDireccion = (EditText) findViewById(R.id.etDireccion);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        String Nombre  = etNombre.getText().toString();
        try {
            GestorBaseDatos gbd = new GestorBaseDatos(getApplicationContext(), "agenda", null, 1);
            SQLiteDatabase bd = gbd.getReadableDatabase();
            Cursor c = bd.rawQuery("SELECT * FROM agenda where nombre like '"+Nombre+"'", null);
            //Nos aseguramos de que existe al menos un registro
             if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    etNombre.setText(c.getString(0));
                    etApellido.setText(c.getString(1));
                    etDireccion.setText(c.getString(2));
                    etTelefono.setText(c.getString(3));
                    etEmail.setText(c.getString(4));
                    Toast.makeText(this,"Los datos de "+Nombre+" se muestran en pantalla", Toast.LENGTH_LONG).show();
                } while(c.moveToNext());
             }else{
                    Toast.makeText(this,"Los datos de "+Nombre+" NO están en la base de datos!", Toast.LENGTH_LONG).show();
             }
        }catch(SQLException sqlError) {
            Toast.makeText(this, "No se puede leer" + sqlError.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void BorrarDatosComprobando(View v){
        EditText etNombre = (EditText) findViewById(R.id.etNombre); // leo el nombre
        String Nombre  = etNombre.getText().toString();
        try {
            GestorBaseDatos gbd = new GestorBaseDatos(getApplicationContext(), "agenda", null, 1);
            SQLiteDatabase bd = gbd.getReadableDatabase();
            Cursor c = bd.rawQuery("SELECT * FROM agenda where nombre like '"+Nombre+"'", null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    BorrarNombre(v);
                } while(c.moveToNext());
            }else{
                Toast.makeText(this,"Los datos de "+Nombre+" NO están en la base de datos!", Toast.LENGTH_LONG).show();
            }
        }catch(SQLException sqlError) {
            Toast.makeText(this, "No se puede leer" + sqlError.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void BorrarNombre(View v){
        EditText etNombre = (EditText) findViewById(R.id.etNombre); // leo el nombre
        EditText etApellido = (EditText) findViewById(R.id.etApellido);
        EditText etTelefono = (EditText) findViewById(R.id.etTelefono);
        EditText etDireccion = (EditText) findViewById(R.id.etDireccion);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        String Nombre  = etNombre.getText().toString();
        try {
            GestorBaseDatos gbd = new GestorBaseDatos(getApplicationContext(), "agenda", null, 1);
            SQLiteDatabase bd = gbd.getWritableDatabase();
            bd.execSQL("DELETE FROM agenda "+ "WHERE nombre LIKE '"+Nombre+"'");
            bd.close();
            etNombre.setText("");
            etApellido.setText("");
            etTelefono.setText("");
            etDireccion.setText("");
            etEmail.setText("");
            Toast.makeText(this, "Los datos de " + Nombre + " fueron ELIMINADOS.", Toast.LENGTH_LONG).show();
            Log.d("LOG","Nombre borrado de la base de datos");
        }catch(SQLException sqlError) {
            Toast.makeText(this, "No se puede leer: " +sqlError.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void salirWit (View vista){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Cuadro de dialogo
        builder.setTitle("Mensaje para salir"); // Titulo
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("¿Desea salir?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Principal.this.finish();
                    }
                }
        );
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                }
        );
        builder.show();
    }
}
