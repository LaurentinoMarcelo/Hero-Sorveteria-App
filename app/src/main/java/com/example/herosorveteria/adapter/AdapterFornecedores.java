package com.example.herosorveteria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.herosorveteria.R;
import com.example.herosorveteria.model.Fornecedor;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterFornecedores extends RecyclerView.Adapter<AdapterFornecedores.FornecedorViewHolder> {
    List<Fornecedor> fornecedorList;
    Context context;

    public AdapterFornecedores(List<Fornecedor> fornecedores, Context context){
        this.fornecedorList = fornecedores;
        this.context = context;
    }

    public FornecedorViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimentacao_fornecedor, parent,false);
        return new FornecedorViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FornecedorViewHolder holder, int position) {
        Fornecedor fornecedor = fornecedorList.get(position);

        holder.nome.setText(fornecedor.getNome());
        holder.telefone.setText(fornecedor.getTelefone());
        holder.endereco.setText(fornecedor.getEndereço());
        holder.produtos.setText(fornecedor.getProdutoFornecido());

    }


    @Override
    public int getItemCount() {
        return fornecedorList.size();
    }

    public class FornecedorViewHolder extends RecyclerView.ViewHolder{
        TextView nome, telefone, endereco, produtos;

        public FornecedorViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textAdapterNomeFornecedor);
            telefone = itemView.findViewById(R.id.textAdapterTelefoneFornecedor);
            endereco = itemView.findViewById(R.id.textAdapterEnderecoFornecedor);
            produtos = itemView.findViewById(R.id.textAdapterProdutosFornecedor);
        }
    }
}


