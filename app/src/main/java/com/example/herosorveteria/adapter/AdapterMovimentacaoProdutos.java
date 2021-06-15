package com.example.herosorveteria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.herosorveteria.R;
import com.example.herosorveteria.model.MovimentacaoProdutos;

import java.util.List;

public class AdapterMovimentacaoProdutos extends RecyclerView.Adapter<AdapterMovimentacaoProdutos.MyViewHolder> {
    List<MovimentacaoProdutos> movimentacaoProdutosList;
    Context context;

    public AdapterMovimentacaoProdutos(List<MovimentacaoProdutos> movimentacaoProdutos, Context context){
        this.movimentacaoProdutosList = movimentacaoProdutos;
        this.context = context;
    }

   @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimentacao_produtos,
                                                                             parent,
                                                                            false);
        return new  MyViewHolder(itemLista);
    }


    public void onBindViewHolder( MyViewHolder holder, int position) {
        MovimentacaoProdutos movimentacaoProdutos = movimentacaoProdutosList.get(position);

        holder.nomeProduto.setText(movimentacaoProdutos.getNomeProduto());
        holder.categoriaProduto.setText(movimentacaoProdutos.getCategoriaProduto());
        holder.quantidadeProduto.setText(movimentacaoProdutos.getQuantidadeProduto());
        holder.unidadeProduto.setText(movimentacaoProdutos.getUnidadeProduto());
        holder.valorCompraProduto.setText(movimentacaoProdutos.getValorProduto());
        holder.valorVendaProduto.setText(movimentacaoProdutos.getValorVenda());
        //holder.valorVendaProduto.setText("R$ 20, 00");

    }

    @Override
    public int getItemCount() {
        //return 10;
        return movimentacaoProdutosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeProduto, categoriaProduto, quantidadeProduto, unidadeProduto, valorCompraProduto, valorVendaProduto;

        public MyViewHolder(View itemView){
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.textAdapterListProdutoNome);
            categoriaProduto = itemView.findViewById(R.id.textAdapterListCategoriaProduto);
            quantidadeProduto = itemView.findViewById(R.id.textAdapterListQuantidadeProduto);
            unidadeProduto = itemView.findViewById(R.id.textAdapterListUnidadeProduto);
            valorVendaProduto = itemView.findViewById(R.id.textAdapterListValorVendaProduto);
            valorCompraProduto = itemView.findViewById(R.id.textAdapterListValorPagoProduto);

        }
    }
}
