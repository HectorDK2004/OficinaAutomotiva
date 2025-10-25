package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemServico {
    private int id;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataConclusao;
    private String status;
    private Veiculo veiculo;
    private List<Servico> servicos;
    private double valorTotal;

    public OrdemServico(int id, LocalDateTime dataAbertura, LocalDateTime dataConclusao, 
                       String status, Veiculo veiculo) {
        this.id = id;
        this.dataAbertura = dataAbertura;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.veiculo = veiculo;
        this.servicos = new ArrayList<>();
        this.valorTotal = 0.0;
    }

    public OrdemServico(Veiculo veiculo) {
        this.dataAbertura = LocalDateTime.now();
        this.status = "ABERTA";
        this.veiculo = veiculo;
        this.servicos = new ArrayList<>();
        this.valorTotal = 0.0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }
    
    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    
    public List<Servico> getServicos() { return servicos; }
    public void setServicos(List<Servico> servicos) { this.servicos = servicos; }
    
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public void adicionarServico(Servico servico) {
        this.servicos.add(servico);
        this.valorTotal += servico.getValor();
    }

    public void calcularValorTotal() {
        this.valorTotal = servicos.stream()
                .mapToDouble(Servico::getValor)
                .sum();
    }

    @Override
    public String toString() {
        return "OrdemServico{" +
                "id=" + id +
                ", dataAbertura=" + dataAbertura +
                ", dataConclusao=" + dataConclusao +
                ", status='" + status + '\'' +
                ", veiculo=" + veiculo.getPlaca() +
                ", servicos=" + servicos.size() +
                ", valorTotal=" + valorTotal +
                '}';
    }
}