package com.example.trabalhodm;

import com.example.trabalhodm.modelos.Cliente;
import com.example.trabalhodm.modelos.ItensVenda;

import java.util.ArrayList;

public class ControllerItensVenda {
    private static ControllerItensVenda instancia;
    private ArrayList<ItensVenda> listaItensVenda;

    public static ControllerItensVenda getInstance() {
        if (instancia == null) {
            return instancia = new ControllerItensVenda();
        } else {
            return instancia;
        }
    }

    ControllerItensVenda() {
        listaItensVenda = new ArrayList<>();
    }

    public void salvarItensVenda(ItensVenda itensVenda) {
        listaItensVenda.add(itensVenda);
    }

    public ArrayList<ItensVenda> retornarItens() {
        return listaItensVenda;
    }
}
