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

public class Editar extends AppCompatActivity {

    EditText edTitulo, edContenido, edId;
    Button bEditar, bBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        edTitulo = findViewById(R.id.txtTitulo);
        edContenido = findViewById(R.id.txtContenido);
        edId = findViewById(R.id.txtID);

        bEditar = findViewById(R.id.btnEditar);
        bBorrar = findViewById(R.id.btnBorrar);

        Intent i = getIntent();
        String eID = i.getStringExtra("id").toString();
        String eTitulo = i.getStringExtra("titulo").toString();
        String eContenido = i.getStringExtra("contenido").toString();

        edId.setText(eID);
        edTitulo.setText(eTitulo);
        edContenido.setText(eContenido);

        bBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                Intent i = new Intent(getApplicationContext(), Mostrar.class);
                startActivity(i);
            }
        });

        bEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
                Intent i = new Intent(getApplicationContext(), Mostrar.class);
                startActivity(i);
            }
        });
    }

    public void delete()
    {
        try
        {
            String id = edId.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null);

            String sql = "delete from records where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, id);
            statement.execute();
            Toast.makeText(this, "Texto borrado", Toast.LENGTH_LONG).show();

            edTitulo.setText("");
            edContenido.setText("");
            edTitulo.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Borrado de texto fallido", Toast.LENGTH_LONG).show();
        }
    }

    public void edit()
    {
        try
        {
            String titulo = edTitulo.getText().toString();
            String contenido = edContenido.getText().toString();
            String id = edId.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null);

            String sql = "update records set titulo = ?, contenido = ? where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, titulo);
            statement.bindString(2, contenido);
            statement.bindString(3, id);
            statement.execute();
            Toast.makeText(this, "Texto editado", Toast.LENGTH_LONG).show();

            edTitulo.setText("");
            edContenido.setText("");
            edTitulo.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Edición de texto fallido", Toast.LENGTH_LONG).show();

        }

    }
}