package model;

import java.time.LocalDateTime;

public class Servico {
    private int id;
    private String descricao;
    private double valor;
    private LocalDateTime dataExecucao;
    private Veiculo veiculo;

    public Servico(int id, String descricao, double valor, LocalDateTime dataExecucao, Veiculo veiculo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataExecucao = dataExecucao;
        this.veiculo = veiculo;
    }

    public Servico(String descricao, double valor, LocalDateTime dataExecucao, Veiculo veiculo) {
        this.descricao = descricao;
        this.valor = valor;
        this.dataExecucao = dataExecucao;
        this.veiculo = veiculo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    
    public LocalDateTime getDataExecucao() { return dataExecucao; }
    public void setDataExecucao(LocalDateTime dataExecucao) { this.dataExecucao = dataExecucao; }
    
    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }

    @Override
    public String toString() {
        return "Servico{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", dataExecucao=" + dataExecucao +
                ", veiculo=" + veiculo.getPlaca() +
                '}';
    }
}