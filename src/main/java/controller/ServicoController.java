package controller;

import dao.ServicoDAO;
import model.Servico;
import model.Veiculo;

import java.time.LocalDateTime;
import java.util.List;

public class ServicoController {
    private ServicoDAO servicoDAO;

    public ServicoController() {
        this.servicoDAO = new ServicoDAO();
    }

    public void registrarServico(String descricao, double valor, Veiculo veiculo) {
        Servico servico = new Servico(descricao, valor, LocalDateTime.now(), veiculo);
        servicoDAO.salvar(servico);
    }

    public List<Servico> listarServicos() {
        return servicoDAO.listarTodos();
    }

    public List<Servico> listarServicosPorVeiculo(String placa) {
        return servicoDAO.listarPorVeiculo(placa);
    }

    public void gerarRelatorioServicosPorVeiculo(String placa) {
        List<Servico> servicos = servicoDAO.listarPorVeiculo(placa);
        double total = 0.0;
        
        System.out.println("\n=== RELATÓRIO DE SERVIÇOS - VEÍCULO: " + placa + " ===");
        for (Servico servico : servicos) {
            System.out.printf("Data: %s | Descrição: %s | Valor: R$ %.2f%n",
                    servico.getDataExecucao().toLocalDate(),
                    servico.getDescricao(),
                    servico.getValor());
            total += servico.getValor();
        }
        System.out.printf("TOTAL GASTO: R$ %.2f%n", total);
        System.out.println("=====================================");
    }
}