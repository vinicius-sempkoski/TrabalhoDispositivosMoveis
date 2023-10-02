package com.example.trabalhodm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalhodm.modelos.Cliente;

public class CadastroClientesActivity extends AppCompatActivity {
    private EditText edNomeCliente;
    private EditText edCpfCliente;
    private Button btSalvarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_clientes);
        edNomeCliente = findViewById(R.id.edNomeCliente);
        edCpfCliente = findViewById(R.id.edCpfCliente);
        btSalvarCliente = findViewById(R.id.btSalvarCliente);
        btSalvarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCliente();
            }
        });
    }

    private void salvarCliente() {

        if (edNomeCliente.getText().toString().isEmpty()) {
            edNomeCliente.setError("O Nome do Cliente deve ser informado!");
            return;
        }
        if (edCpfCliente.getText().toString().isEmpty()) {
            edCpfCliente.setError("O CPF do Cliente deve ser informado!");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(edNomeCliente.getText().toString());
        cliente.setCPF(edCpfCliente.getText().toString());

        ControllerCliente.getInstance().salvarCliente(cliente);

        Toast.makeText(CadastroClientesActivity.this, "Cliente Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();

        finish();
    }
}