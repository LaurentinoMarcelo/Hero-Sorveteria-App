package com.example.herosorveteria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.herosorveteria.R;
import com.example.herosorveteria.model.Clientes;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterClientes extends RecyclerView.Adapter<AdapterClientes.ClienteViewHolder> {
   List<Clientes> clientesList;
   Context context;

   public AdapterClientes(List<Clientes> clientes, Context context){
       this.clientesList = clientes;
       this.context = context;
   }

    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_movimentacao_cliente, parent, false);
        return new ClienteViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClienteViewHolder holder, int position) {
        Clientes cliente = clientesList.get(position);

        holder.nome.setText(cliente.getNome());
        holder.telefone.setText(cliente.getTelefone());
        holder.redesocial.setText(cliente.getRedeSocial());
    }


    @Override
    public int getItemCount() {

       return clientesList.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder{
        TextView nome, telefone, redesocial;

        public ClienteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textAdapterNomeListaCliente);
            telefone = itemView.findViewById(R.id.textAdapterTelefoneListaCliente);
            redesocial = itemView.findViewById(R.id.textAdapterRedeSocialListaCliente);
        }
    }
}
