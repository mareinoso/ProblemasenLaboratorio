package com.example.problemasenlaboratorio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateDeleteActivity extends AppCompatActivity {

    private UserModel userModel;
    private EditText etRut, etNombre, etDesc;
    private Button btnupdate, btndelete;
    private DatabaseHelper databaseHelper;

    private Spinner spinnerLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("user");

        databaseHelper = new DatabaseHelper(this);

        etRut = (EditText) findViewById(R.id.etRut);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etDesc = (EditText) findViewById(R.id.etDesc);
        btndelete = (Button) findViewById(R.id.btndelete);
        btnupdate = (Button) findViewById(R.id.btnupdate);
        spinnerLab = (Spinner) findViewById(R.id.spinnerLab);

        etRut.setText(userModel.getRut());
        etNombre.setText(userModel.getNombre());
        etDesc.setText(userModel.getDescripcion());
        spinnerLab.setSelection(getIndex(spinnerLab, (userModel.getLaboratorio())));

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            //Probablemente deba pasar null como fecha
            public void onClick(View v) {
                databaseHelper.updateUser(userModel.getId(),spinnerLab.getSelectedItem().toString(),etRut.getText().toString(),etNombre.getText().toString(),etDesc.getText().toString());
                Toast.makeText(UpdateDeleteActivity.this, "ACTUALIZACIÓN EXITOSA!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateDeleteActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteUSer(userModel.getId());
                Toast.makeText(UpdateDeleteActivity.this, "ELIMINADO CORRECTAMENTE!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateDeleteActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
}
