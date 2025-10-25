package controller;

import java.util.List;

import dao.CondutorDAO;
import model.Condutor;

public class CondutorController {
    private CondutorDAO condutorDAO;

    public CondutorController() {
        this.condutorDAO = new CondutorDAO();
    }

    public void cadastrarCondutor(String nome, String cpf, String telefone, 
                                 String email, String numeroCNH, String categoriaCNH) {
        Condutor condutor = new Condutor(nome, cpf, telefone, email, numeroCNH, categoriaCNH);
        condutorDAO.salvar(condutor);
    }

    public List<Condutor> listarCondutores() {
        return condutorDAO.listarTodos();
    }

    public Condutor buscarCondutorPorCpf(String cpf) {
        return condutorDAO.buscarPorCpf(cpf);
    }
}