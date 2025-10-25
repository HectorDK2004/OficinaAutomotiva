package model;

public abstract class Veiculo {
    protected String placa;
    protected String marca;
    protected String modelo;
    protected int ano;
    protected String cor;
    protected Condutor proprietario;

    public Veiculo(String placa, String marca, String modelo, int ano, String cor, Condutor proprietario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.proprietario = proprietario;
    }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    
    public Condutor getProprietario() { return proprietario; }
    public void setProprietario(Condutor proprietario) { this.proprietario = proprietario; }

    public abstract String getTipo();
}