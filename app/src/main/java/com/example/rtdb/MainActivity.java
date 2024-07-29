package com.example.rtdb;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference HumedadRef, presionRef, VelocidadRef, TemperauraRef;
    private TextView txt_humedad, txt_presion, txt_velocidad, txt_temperatura;

    private EditText txt_temperatura_edit, txt_humedad_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_humedad = findViewById(R.id.valor_Humedad);
        txt_presion = findViewById(R.id.valor_Presion);
        txt_velocidad = findViewById(R.id.valor_velocidad);
        txt_temperatura = findViewById(R.id.valor_Temperatura);
        txt_temperatura_edit= findViewById(R.id.setvalor_Temperatura);
        txt_humedad_edit= findViewById(R.id.setvalor_Humedad);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        HumedadRef = database.getReference("sensores/humedad");
        presionRef = database.getReference("sensores/presion");
        VelocidadRef = database.getReference("sensores/velocidad");
        TemperauraRef = database.getReference("sensores/temperatura");

        HumedadRef.addValueEventListener(setListener(txt_humedad, "%"));
        presionRef.addValueEventListener(setListener(txt_presion, "hPa"));
        VelocidadRef.addValueEventListener(setListener(txt_velocidad, "km/h"));
        TemperauraRef.addValueEventListener(setListener(txt_temperatura, "°C"));
    }

    public ValueEventListener setListener(TextView txt, String UnidadMedida){

        return (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txt.setText(snapshot.getValue().toString() + " " + UnidadMedida);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txt.setText("");
            }
        });
    }

    public void clickBotonTemp(View view){
        TemperauraRef.setValue (Float.parseFloat(txt_temperatura_edit.getText().toString()));
    }
    public void clickBotonHumedad(View view){
        HumedadRef.setValue(Float.parseFloat(txt_humedad_edit.getText().toString()));
    }

}