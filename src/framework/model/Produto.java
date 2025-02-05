package framework.model;

import framework.SerializableEntity;

public class Produto implements SerializableEntity<Produto> {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String serialize() {
        return nome + "," + preco;
    }

    @Override
    public Produto deserialize(String data) {
        String[] fields = data.split(",");
        String nome = fields[0];
        double preco = Double.parseDouble(fields[1]);
        return new Produto(nome, preco);
    }

    @Override
    public String toString() {
        return "Produto{nome='" + nome + "', preco=" + preco + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return nome.equals(produto.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}