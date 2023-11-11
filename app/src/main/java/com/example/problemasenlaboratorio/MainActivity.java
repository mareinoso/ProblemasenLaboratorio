package com.example.problemasenlaboratorio;

import android.content.Intent;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Button btnStore, btnGetall;
    private Spinner spinnerLab;
    private EditText etRut, etNombre , etDesc;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) //verificamos que exista acelerometro
        {
            //no se puede agregar mas delay que los especificados
            sm.registerListener((SensorEventListener) this, sensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }

        databaseHelper = new DatabaseHelper(this);

        btnStore = (Button) findViewById(R.id.btnstore);
        btnGetall = (Button) findViewById(R.id.btnget);
        etRut = (EditText) findViewById(R.id.etRut);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etDesc = (EditText) findViewById(R.id.etDesc);
        spinnerLab = (Spinner) findViewById(R.id.spinnerLab);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.laboratorios, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLab.setAdapter(adapter);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtiene si los campos son nulos
                if (TextUtils.isEmpty(etRut.getText()) || TextUtils.isEmpty(etNombre.getText()) || TextUtils.isEmpty(etDesc.getText())) {
                    Toast.makeText(MainActivity.this, "No se ha ingresado un valor", Toast.LENGTH_SHORT).show();

                } else if (validarRut(String.valueOf(etRut.getText())) == false) {
                    Toast.makeText(MainActivity.this, "Hay un problema con el rut", Toast.LENGTH_SHORT).show();

                } else {
                    databaseHelper.addUserDetail(spinnerLab.getSelectedItem().toString(),etRut.getText().toString(), etNombre.getText().toString(), etDesc.getText().toString());
                    spinnerLab.setSelection(0);
                    etRut.setText("");
                    etNombre.setText("");
                    etDesc.setText("");
                    Toast.makeText(MainActivity.this, "Datos grabados!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,GetAllUsersActivity.class);
                startActivity(intent);
            }
        });

    }
    public static boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut = rut.toUpperCase();

            //obtiene el rut como int removiendo el digito verificador
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        btnStore = (Button) findViewById(R.id.btnstore);
        float y = event.values[SensorManager.DATA_Y];
        //mas exacto para que no se active tan facilmente
        if (y > 9.4){
            //simula un click en el boton para iniciar el grabado
            btnStore.performClick();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
