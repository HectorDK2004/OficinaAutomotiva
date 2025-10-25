package controller;

import java.util.List;

import dao.VeiculoDAO;
import model.Carro;
import model.Condutor;
import model.Moto;
import model.Veiculo;

public class VeiculoController {
    private VeiculoDAO veiculoDAO;

    public VeiculoController() {
        this.veiculoDAO = new VeiculoDAO();
    }

    public void cadastrarCarro(String placa, String marca, String modelo, int ano, String cor,
                              Condutor proprietario, int numeroPortas, String tipoCombustivel) {
        Carro carro = new Carro(placa, marca, modelo, ano, cor, proprietario, numeroPortas, tipoCombustivel);
        veiculoDAO.salvar(carro);
    }

    public void cadastrarMoto(String placa, String marca, String modelo, int ano, String cor,
                             Condutor proprietario, int cilindradas, boolean partidaEletrica) {
        Moto moto = new Moto(placa, marca, modelo, ano, cor, proprietario, cilindradas, partidaEletrica);
        veiculoDAO.salvar(moto);
    }

    public List<Veiculo> listarVeiculos() {
        return veiculoDAO.listarTodos();
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return veiculoDAO.buscarPorPlaca(placa);
    }
}