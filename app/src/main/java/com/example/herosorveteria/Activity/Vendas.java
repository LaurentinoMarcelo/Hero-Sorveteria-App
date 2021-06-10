package com.example.herosorveteria.Activity;

public class Vendas {

    private double valorCompra;
    private double valorPago;
    private double valorTroco;
    private String formaPagamento;


    public Vendas(double valorCompra, double valorPago, double valorTroco, String formaPagamento) {
        this.valorPago = valorPago;
        this.valorCompra = valorCompra;
        this.valorTroco = valorTroco;
        this.formaPagamento = formaPagamento;

    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public double getValorTroco() {
        return valorTroco;
    }

    public void setValorTroco(double valorTroco) {
        this.valorTroco = valorTroco;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
