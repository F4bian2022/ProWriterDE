package com.example.prowriter51;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText edTitulo, edContenido;
    Button bGuardar, bMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edTitulo = findViewById(R.id.txtTitulo);
        edContenido = findViewById(R.id.txtContenido);

        bGuardar = findViewById(R.id.btnGuardar);
        bMostrar = findViewById(R.id.btnMostrar);

        bMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Mostrar.class);
                startActivity(i);

            }
        });

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

    }

    public void insert()
    {
        try
        {
            String titulo = edTitulo.getText().toString();
            String contenido = edContenido.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS records (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR, contenido VARCHAR)");

            String sql = "insert into records (titulo, contenido)values(?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, titulo);
            statement.bindString(2, contenido);
            statement.execute();
            Toast.makeText(this, "Texto ingresado", Toast.LENGTH_LONG).show();

            edTitulo.setText("");
            edContenido.setText("");
            edTitulo.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Ingreso de texto fallido", Toast.LENGTH_LONG).show();

        }

    }

}