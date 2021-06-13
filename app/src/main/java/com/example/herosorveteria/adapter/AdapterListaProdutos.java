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
import com.example.herosorveteria.model.MovimentacaoReceitas;
import com.example.herosorveteria.model.Produtos;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterListaProdutos extends RecyclerView.Adapter<AdapterListaProdutos.ListaProdutosViewHolder> {

    List<Produtos> listaProdutos;
    Context context;

    public AdapterListaProdutos(List<Produtos> listaProdutos, ProdutoActivity produtoActivity) {
        this.listaProdutos = listaProdutos;
        this.context = context;

    }

    public ListaProdutosViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_produtos,parent,false);

        return new ListaProdutosViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListaProdutosViewHolder holder, int position) {
        holder.nomeProduto.setText("Floresta negra");
        holder.categoriaProduto.setText("Sorvete");
        holder.quantiadeProduto.setText("2");
        holder.unidadeProduto.setText("Caixas");
    }

    @Override
    public int getItemCount() {

        return 5;
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