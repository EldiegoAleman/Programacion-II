package com.ugb.myprimerproyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

public class registroUSS extends AppCompatActivity {

    FloatingActionButton btnregresar;
    String accion = "nuevo";
    Button btnagregar;
    DB miconexion;
    detectarInternet detectarInternet;
    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrouss);

        miconexion = new DB(getApplicationContext(),"",null,1);
        btnregresar = findViewById(R.id.btnregresar);
        btnagregar = findViewById(R.id.btnguardar);

        btnregresar.setOnClickListener(v -> {
            regresarmainactivity();
        });

        btnagregar.setOnClickListener(v -> {
            agregar();
        });


    }

    private void agregar() {
        try {
            temp = findViewById(R.id.txtnombre);
            String nombre = temp.getText().toString();

            temp = findViewById(R.id.txtdui);
            String dui = temp.getText().toString();

            temp = findViewById(R.id.txttelefono);
            String telefono = temp.getText().toString();

            temp = findViewById(R.id.txtcorreo);
            String correo = temp.getText().toString();

            temp = findViewById(R.id.txtpass);
            String pass = temp.getText().toString();

            String[] datos = {dui,nombre, dui, telefono, correo, pass};
            miconexion.agregar_usuario(accion, datos);


            JSONObject datosss = new JSONObject();
            datosss.put("_id",dui);
            datosss.put("nombre",nombre);
            datosss.put("dui",dui);
            datosss.put("telefono",telefono);
            datosss.put("correo",correo);
            datosss.put("pass",pass);
            detectarInternet = new detectarInternet(getApplicationContext());
            if (detectarInternet.hayConexionInternet()) {
            enviarusuarios guardarpelis = new enviarusuarios(getApplicationContext());
                String resp = guardarpelis.execute(datosss.toString()).get();
            }

            mensajes("Registro guardado en la nube.");
            regresarmainactivity();
        }catch (Exception w){
            mensajes(w.getMessage());
        }
    }

    private void regresarmainactivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private void mensajes(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
}