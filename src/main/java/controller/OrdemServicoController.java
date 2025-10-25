package controller;

import dao.OrdemServicoDAO;
import model.OrdemServico;
import model.Veiculo;
import model.Servico;

import java.util.List;

public class OrdemServicoController {
    private OrdemServicoDAO ordemServicoDAO;

    public OrdemServicoController() {
        this.ordemServicoDAO = new OrdemServicoDAO();
    }

    public void criarOrdemServico(Veiculo veiculo) {
        OrdemServico ordem = new OrdemServico(veiculo);
        ordemServicoDAO.salvar(ordem);
    }

    public List<OrdemServico> listarOrdensServico() {
        return ordemServicoDAO.listarTodos();
    }

    public void visualizarOrdemServico(int id) {
        List<OrdemServico> ordens = ordemServicoDAO.listarTodos();
        for (OrdemServico ordem : ordens) {
            if (ordem.getId() == id) {
                System.out.println("\n=== ORDEM DE SERVIÇO #" + id + " ===");
                System.out.println("Veículo: " + ordem.getVeiculo().getPlaca());
                System.out.println("Data Abertura: " + ordem.getDataAbertura());
                System.out.println("Status: " + ordem.getStatus());
                System.out.println("Valor Total: R$ " + ordem.getValorTotal());
                System.out.println("Serviços:");
                for (Servico servico : ordem.getServicos()) {
                    System.out.println("  - " + servico.getDescricao() + " (R$ " + servico.getValor() + ")");
                }
                System.out.println("===============================");
                return;
            }
        }
        System.out.println("Ordem de serviço não encontrada!");
    }
}