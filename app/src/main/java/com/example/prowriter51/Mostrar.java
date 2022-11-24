package com.example.prowriter51;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Date;

public class Mostrar extends AppCompatActivity {

    Button bNuevoTexto;

    ListView lst;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null);
        lst = findViewById(R.id.list);
        final Cursor c = db.rawQuery("select * from records", null);
        int id = c.getColumnIndex("id");
        int titulo = c.getColumnIndex("titulo");
        int contenido = c.getColumnIndex("contenido");
        titles.clear();

        arrayAdapter = new ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, titles);
        lst.setAdapter(arrayAdapter);

        final ArrayList<textos> tex = new ArrayList<textos>();

        if(c.moveToFirst())
        {
            do{

                textos texto = new textos();
                texto.id = c.getString(id);
                texto.titulo = c.getString(titulo);
                texto.contenido = c.getString(contenido);

                tex.add(texto);

                titles.add(c.getString(id) + " \t " + c.getString(titulo));


            }while (c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            lst.invalidateViews();
        }

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String aa = titles.get(position).toString();
                textos texto = tex.get(position);

                Intent i = new Intent(getApplicationContext(), Editar.class);

                i.putExtra("id", texto.id);
                i.putExtra("titulo", texto.titulo);
                i.putExtra("contenido", texto.contenido);
                startActivity(i);

            }
        });

        bNuevoTexto = findViewById(R.id.btnCrearNuevoTexto);

        bNuevoTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }
}