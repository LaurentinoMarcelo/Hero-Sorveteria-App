package com.example.herosorveteria.adapter;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.herosorveteria.R;
import com.example.herosorveteria.model.MovimentacaoReceitas;

import java.util.List;

public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.MyViewHolder> {

    List<MovimentacaoReceitas> movimentacoes;
    Context context;

    public AdapterMovimentacao(List<MovimentacaoReceitas> movimentacoes, Context context) {
        this.movimentacoes = movimentacoes;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimentacao, parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MovimentacaoReceitas movimentacaoReceitas = movimentacoes.get(position);

        double valor = movimentacaoReceitas.getValor();
        String valorFormatado = String.format("R$ %.2f", valor);

        holder.titulo.setText(movimentacaoReceitas.getCategoria());
        holder.valor.setText(valorFormatado);
        holder.formaPagamento.setText(movimentacaoReceitas.getFormaPagamento());

        if (movimentacaoReceitas.getTipo().equals("d")) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.red));
            holder.valor.setText("-" + movimentacaoReceitas.getValor());
        }
    }


    @Override
    public int getItemCount() {

        return movimentacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, valor, formaPagamento;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textAdapterNomeProduto);
            valor = itemView.findViewById(R.id.textAdapterQuantidadeProduto);
            formaPagamento = itemView.findViewById(R.id.textAdapterCategoriaProduto);
        }

    }

}
