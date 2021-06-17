package com.example.herosorveteria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.herosorveteria.R;
import com.example.herosorveteria.model.Produto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.ProdutoViewHolder> {
    List<Produto> produtoList;
    Context context;

    public AdapterProduto(List<Produto> produtos, Context context) {
        this.produtoList = produtos;
        this.context = context;
    }

    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimentacao_produtos,parent,false);
        return new ProdutoViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProdutoViewHolder holder, int position) {
       Produto produto = produtoList.get(position);

       double valor = Double.parseDouble(produto.getValorCompra());
       String valorFormatado = String.format("R$ %.2f", valor);


       holder.produtoNome.setText(produto.getNome());
       holder.categoriaProduto.setText(produto.getCategoria());
       holder.valorProduto.setText(valorFormatado);
       holder.quantidadeProduto.setText(produto.getQuantiade());
       holder.unidadeProduto.setText(produto.getUnidade());

    }


    @Override
    public int getItemCount() {
        return produtoList.size();
    }


    public class ProdutoViewHolder extends RecyclerView.ViewHolder {

        TextView produtoNome;
        TextView categoriaProduto;
        TextView valorProduto;
        TextView quantidadeProduto;
        TextView unidadeProduto;

        public ProdutoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

           produtoNome = itemView.findViewById(R.id.textAdapterNomeProdutoListaProduto);
           categoriaProduto = itemView.findViewById(R.id.textAdapterCategoriaProdutoListaProduto);
           valorProduto = itemView.findViewById(R.id.textAdapterValorProdutoListaProduto);
           quantidadeProduto = itemView.findViewById(R.id.textAdapterQuantidadeProdutoListaProduto);
           unidadeProduto = itemView.findViewById(R.id.textAdapterUnidadeProdutoListaProduto);

        }
    }
}
