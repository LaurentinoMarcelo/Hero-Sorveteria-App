package com.example.herosorveteria.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.herosorveteria.R;
import com.example.herosorveteria.menu.ProdutoActivity;
import com.example.herosorveteria.model.MovimentacaoProdutos;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterListaProdutos extends RecyclerView.Adapter<AdapterListaProdutos.ListaProdutosViewHolder> {

    List<MovimentacaoProdutos> listaProdutos;
    Context context;

    public AdapterListaProdutos(List<MovimentacaoProdutos> lista, Context context) {
        this.listaProdutos = lista;
        this.context = context;

    }

    @Override
    public ListaProdutosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_produtos,parent,false);
        return new ListaProdutosViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(ListaProdutosViewHolder holder, int position) {

        MovimentacaoProdutos movimentacaoProdutos = listaProdutos.get(position);

        holder.nomeProduto.setText(movimentacaoProdutos.getNomeProduto());
        holder.categoriaProduto.setText(movimentacaoProdutos.getCategoriaProduto());
        holder.quantiadeProduto.setText(movimentacaoProdutos.getQuantidadeProduto());
        holder.unidadeProduto.setText(movimentacaoProdutos.getUnidadeProduto());
    }

    @Override
    public int getItemCount() {

        return listaProdutos.size();
    }

    public class ListaProdutosViewHolder extends RecyclerView.ViewHolder{

        TextView nomeProduto, categoriaProduto, quantiadeProduto, unidadeProduto;

        public ListaProdutosViewHolder(View itemView) {
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.textAdapterNomeProduto);
            categoriaProduto = itemView.findViewById(R.id.textAdapterCategoriaProduto);
            quantiadeProduto = itemView.findViewById(R.id.textAdapterQuantidadeProduto);
            unidadeProduto = itemView.findViewById(R.id.textAdapterUnidadeProduto);
        }
    }
}