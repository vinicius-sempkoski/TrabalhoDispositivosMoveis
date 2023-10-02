package com.example.trabalhodm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btCadastroClientes;
    private Button btCadastroVenda;
    private Button btLancamentoPedidos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCadastroClientes = findViewById(R.id.btCadastroClientes);
        btCadastroClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroClientesActivity.class);
            }
        });

        btCadastroVenda = findViewById(R.id.btCadastroVenda);
        btCadastroVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastrarItensVendaActivity.class);
            }
        });

        btLancamentoPedidos = findViewById(R.id.btLancamentoPedidos);
        btLancamentoPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(LancamentoPedidoActivity.class);
            }
        });
    }

    private void abrirActivity(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}