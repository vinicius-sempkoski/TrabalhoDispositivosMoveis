package com.example.trabalhodm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalhodm.modelos.Cliente;
import com.example.trabalhodm.modelos.ItensVenda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class LancamentoPedidoActivity extends AppCompatActivity {
    private Spinner spClientesAdd;
    private Spinner spItensAdd;
    private ArrayList<Cliente> clientes;
    private ArrayList<ItensVenda> itens;
    private TextView tvErroCliente;
    private TextView tvErroItens;
    private TextView tvItensAdicionados;
    private EditText edValorUnit;
    private Button btAdicionarItem;
    private TextView edValorTotal;
    private TextView edQuantidadeTotal;
    private EditText edQuantidade;
    private int posicaoItemSelecionado = -1;
    private int quantidadeTotal = 0;
    private double precoTotal = 0.0;
    private RadioButton rbAvista;
    private RadioButton rbAprazo;
    private Button btSalvarPedido;
    private TextView tvPrecoFinal;
    private TextView tvParcelas;
    private EditText edParcelas;
    private TextView parcelaText;
    private Button btSalvarPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento_pedido);

        tvErroItens = findViewById(R.id.tvErroItens);
        tvErroCliente = findViewById(R.id.tvErroCliente);
        spClientesAdd = findViewById(R.id.spClientesAdd);
        spItensAdd = findViewById(R.id.spItensAdd);
        tvItensAdicionados = findViewById(R.id.tvItensAdicionados);
        edValorUnit = findViewById(R.id.edValorUnit);
        btAdicionarItem = findViewById(R.id.btAdicionarItem);
        edValorTotal = findViewById(R.id.edValorTotal);
        edQuantidadeTotal = findViewById(R.id.edQuantidadeTotal);
        edQuantidade = findViewById(R.id.edQuantidade);
        rbAvista = findViewById(R.id.rbAvista);
        rbAprazo = findViewById(R.id.rbAprazo);
        btSalvarPedido = findViewById(R.id.btSalvarPedido);
        tvPrecoFinal = findViewById(R.id.tvPrecoFinal);
        tvParcelas = findViewById(R.id.tvParcelas);
        edParcelas = findViewById(R.id.edParcelas);
        parcelaText = findViewById(R.id.parcelaText);
        btSalvarPagamento = findViewById(R.id.btSalvarPagamento);


        spClientesAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                if (posicao > 0) {
                    posicaoItemSelecionado = posicao;
                    tvErroCliente.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        popularClientes();

        spItensAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                if (posicao > 0) {
                    posicaoItemSelecionado = posicao;
                    tvErroItens.setVisibility(View.GONE);
                    edValorUnit.setText(String.valueOf(itens.get(posicao - 1).getValorUnitario()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        popularItens();

        btAdicionarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizarListaVenda();
            }
        });

        btSalvarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(LancamentoPedidoActivity.this, "Pedido de venda cadastrado com sucesso! Pedido: " + gerarNumeroPedido(), Toast.LENGTH_SHORT).show();
                limparTela();
            }
        });
        btSalvarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meioPagamento();
            }
        });
        rbAvista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meioPagamento();
            }
        });

        rbAprazo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meioPagamento();
            }
        });
    }

    public void popularClientes() {
        clientes = ControllerCliente.getInstance().retornarClientes();
        String[] vetorClients = new String[clientes.size() + 1];
        vetorClients[0] = "";
        for (int i = 1; i < vetorClients.length; i++) {
            Cliente cliente = clientes.get(i - 1);
            vetorClients[i] = "Nome: " + cliente.getNome() + " CPF: " + cliente.getCPF();
        }

        ArrayAdapter adapter = new ArrayAdapter(LancamentoPedidoActivity.this, android.R.layout.simple_dropdown_item_1line, vetorClients);

        spClientesAdd.setAdapter(adapter);
    }

    public void popularItens() {
        itens = ControllerItensVenda.getInstance().retornarItens();
        String[] vetorItens = new String[itens.size() + 1];
        vetorItens[0] = "";
        for (int i = 1; i < vetorItens.length; i++) {
            ItensVenda item = itens.get(i - 1);
            vetorItens[i] = "Cod: " + item.getCodigo() + " Descrição: " + item.getDescricao();
        }

        ArrayAdapter adapter = new ArrayAdapter(LancamentoPedidoActivity.this, android.R.layout.simple_dropdown_item_1line, vetorItens);

        spItensAdd.setAdapter(adapter);
    }

    private void atualizarListaVenda() {
        if (posicaoItemSelecionado > 0) {
            ItensVenda itemSelecionado = itens.get(posicaoItemSelecionado - 1);

            String quantidadeString = edQuantidade.getText().toString();

            if (!quantidadeString.isEmpty()) {
                int quantidadeItem = Integer.parseInt(quantidadeString);

                double precoItem = itemSelecionado.getValorUnitario() * quantidadeItem;
                quantidadeTotal += quantidadeItem;
                precoTotal += precoItem;

                edQuantidadeTotal.setText(String.valueOf(quantidadeTotal));
                edValorTotal.setText(String.valueOf(precoTotal));

                String novoItem = "Código do Item: " + itemSelecionado.getCodigo() + "\nDescrição: " + itemSelecionado.getDescricao() + "\nValor Unitário: " + itemSelecionado.getValorUnitario() + "\n\n";

                String texto = tvItensAdicionados.getText().toString();
                texto += novoItem;
                tvItensAdicionados.setText(texto);
            }
        }
    }

    public void meioPagamento() {
        double valorTotal = precoTotal;

        if (rbAvista.isChecked()) {
            valorTotal *= 0.95;
            findViewById(R.id.parcelaText).setVisibility(View.INVISIBLE);
            findViewById(R.id.tvParcelas).setVisibility(View.INVISIBLE);
            findViewById(R.id.edParcelas).setVisibility(View.INVISIBLE);
            findViewById(R.id.btSalvarPagamento).setVisibility(View.INVISIBLE);
        } else if (rbAprazo.isChecked()) {
            valorTotal *= 1.05;
            findViewById(R.id.parcelaText).setVisibility(View.VISIBLE);
            findViewById(R.id.tvParcelas).setVisibility(View.VISIBLE);
            findViewById(R.id.edParcelas).setVisibility(View.VISIBLE);
            findViewById(R.id.btSalvarPagamento).setVisibility(View.VISIBLE);
            totalParcelas();
        }
        tvPrecoFinal.setText(String.valueOf(valorTotal));
    }

    private void totalParcelas() {
        EditText edParcelas = findViewById(R.id.edParcelas);
        if (!edParcelas.getText().toString().isEmpty()) {
            int quantidadeParcelas = Integer.parseInt(edParcelas.getText().toString());
            double valorParcela = (precoTotal * 1.05) / quantidadeParcelas;

            TextView tvParcelas = findViewById(R.id.tvParcelas);
            tvParcelas.setText(String.format(Locale.getDefault(), "Valor da Parcela: %.2f", valorParcela));
        } else {
            TextView tvParcelas = findViewById(R.id.tvParcelas);
            tvParcelas.setText("");
        }
    }

    private String gerarNumeroPedido() {
        String prefixo = "PED";
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyyMMdd");
        String dataAtual = formatoData.format(new Date());
        Random random = new Random();
        int numeroAleatorio = random.nextInt(100000); // Número aleatório de 0 a 99999

        String numeroFormatado = String.format("%05d", numeroAleatorio);

        return prefixo + dataAtual + "-" + numeroFormatado;
    }

    private void limparTela() {

        edQuantidade.setText("");
        edValorUnit.setText("");
        edQuantidadeTotal.setText("");
        edValorTotal.setText("");
        edParcelas.setText("");

        tvErroItens.setVisibility(View.GONE);
        tvErroCliente.setVisibility(View.GONE);
        tvItensAdicionados.setText("");
        tvPrecoFinal.setText("");
        tvParcelas.setText("");

        posicaoItemSelecionado = 0;
        quantidadeTotal = 0;
        precoTotal = 0;

        rbAvista.setChecked(false);
        rbAprazo.setChecked(false);

        spClientesAdd.setSelection(0);
        spItensAdd.setSelection(0);
    }
}