package com.example.oficina_automotiva;

import controller.CondutorController;
import controller.OrdemServicoController;
import controller.ServicoController;
import controller.VeiculoController;
import dao.CondutorDAO;
import dao.OrdemServicoDAO;
import dao.ServicoDAO;
import dao.VeiculoDAO;
import view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE OFICINA AUTOMOTIVA ===");
        
        try {
            inicializarBancoDados();
            
            CondutorController condutorController = new CondutorController();
            VeiculoController veiculoController = new VeiculoController();
            ServicoController servicoController = new ServicoController();
            OrdemServicoController ordemServicoController = new OrdemServicoController();
            
            ConsoleView view = new ConsoleView(condutorController, veiculoController, 
                                             servicoController, ordemServicoController);
            
            view.exibirMenuPrincipal();
            
        } catch (Exception e) {
            System.out.println("Erro ao iniciar a aplicação: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void inicializarBancoDados() {
        try {
            System.out.println("Inicializando banco de dados...");
            
            CondutorDAO condutorDAO = new CondutorDAO();
            VeiculoDAO veiculoDAO = new VeiculoDAO();
            ServicoDAO servicoDAO = new ServicoDAO();
            OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
            
            condutorDAO.criarTabela();
            veiculoDAO.criarTabela();
            servicoDAO.criarTabela();
            ordemServicoDAO.criarTabela();
            
            System.out.println(" Banco de dados inicializado com sucesso!");
            
        } catch (Exception e) {
            System.out.println(" Erro ao inicializar banco de dados: " + e.getMessage());
            System.out.println("Verifique se:");
            System.out.println("1. O MySQL está instalado e rodando");
            System.out.println("2. O banco 'oficina_automotiva' foi criado");
            System.out.println("3. As credenciais no DatabaseConnection.java estão corretas");
        }
    }
}