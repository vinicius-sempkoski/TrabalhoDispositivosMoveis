package com.example.trabalhodm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalhodm.modelos.ItensVenda;

public class CadastrarItensVendaActivity extends AppCompatActivity {
    private EditText edCodItem;
    private EditText edDescricao;
    private EditText edValorUnit;
    private Button btSalvarItensVenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_itens_venda);

        edCodItem = findViewById(R.id.edCodItem);
        edDescricao = findViewById(R.id.edDescricao);
        edValorUnit = findViewById(R.id.edValorUnit);
        btSalvarItensVenda = findViewById(R.id.btSalvarItensVenda);
        btSalvarItensVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarItensVenda();
            }
        });
    }

    private void salvarItensVenda() {
        double valor = 0.0;
        int codigo = 0;
        if (edValorUnit.getText().toString().isEmpty()) {
            edValorUnit.setError("O Valor Unitário deve ser informado!");
            return;
        } else {
            try {
                valor = Double.parseDouble(edValorUnit.getText().toString());
                if (valor <= 0) {
                    edValorUnit.setError("O Valor Unitário deve ser maior que 0!");
                    return;
                }
            } catch (Exception ex) {
                edValorUnit.setError("Informe um valor numérico válido!");
                return;
            }
        }

        if (edDescricao.getText().toString().isEmpty()) {
            edDescricao.setError("A descrição deve ser informada!");
            return;
        }
        if (edCodItem.getText().toString().isEmpty()) {
            edCodItem.setError("O Código deve ser informada!");
            return;
        } else {
            try {
                codigo = Integer.parseInt(edCodItem.getText().toString());
            } catch (Exception ex) {
                edCodItem.setError("Informe um código válido!");
                return;
            }
        }

        ItensVenda itensVenda = new ItensVenda();
        itensVenda.setCodigo(codigo);
        itensVenda.setDescricao(edDescricao.getText().toString());
        itensVenda.setValorUnitario(valor);
        ;
        ControllerItensVenda.getInstance().salvarItensVenda(itensVenda);

        Toast.makeText(CadastrarItensVendaActivity.this, "Item de Venda Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();

        finish();
    }
}
