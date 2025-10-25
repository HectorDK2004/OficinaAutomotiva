package view;


import java.util.List;
import java.util.Scanner;

import controller.CondutorController;
import controller.OrdemServicoController;
import controller.ServicoController;
import controller.VeiculoController;
import model.Condutor;
import model.OrdemServico;
import model.Servico;
import model.Veiculo;
import service.FipeService;

public class ConsoleView {
    private Scanner scanner;
    private CondutorController condutorController;
    private VeiculoController veiculoController;
    private ServicoController servicoController;
    private OrdemServicoController ordemServicoController;
    private FipeService fipeService;

    public ConsoleView(CondutorController condutorController, VeiculoController veiculoController,
                      ServicoController servicoController, OrdemServicoController ordemServicoController) {
        this.scanner = new Scanner(System.in);
        this.condutorController = condutorController;
        this.veiculoController = veiculoController;
        this.servicoController = servicoController;
        this.ordemServicoController = ordemServicoController;
        this.fipeService = new FipeService();
    }

    public void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE OFICINA AUTOMOTIVA ===");
            System.out.println("1. Cadastrar Condutor");
            System.out.println("2. Cadastrar Veículo");
            System.out.println("3. Registrar Serviço");
            System.out.println("4. Criar Ordem de Serviço");
            System.out.println("5. Listar Condutores");
            System.out.println("6. Listar Veículos");
            System.out.println("7. Listar Serviços");
            System.out.println("8. Listar Ordens de Serviço");
            System.out.println("9. Relatório de Serviços por Veículo");
            System.out.println("10. Consultar Valor FIPE");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    cadastrarCondutor();
                    break;
                case 2:
                    cadastrarVeiculo();
                    break;
                case 3:
                    registrarServico();
                    break;
                case 4:
                    criarOrdemServico();
                    break;
                case 5:
                    listarCondutores();
                    break;
                case 6:
                    listarVeiculos();
                    break;
                case 7:
                    listarServicos();
                    break;
                case 8:
                    listarOrdensServico();
                    break;
                case 9:
                    relatorioServicosPorVeiculo();
                    break;
                case 10:
                    consultarFipe();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarCondutor() {
        System.out.println("\n=== CADASTRO DE CONDUTOR ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Número CNH: ");
        String numeroCNH = scanner.nextLine();
        System.out.print("Categoria CNH: ");
        String categoriaCNH = scanner.nextLine();
        
        condutorController.cadastrarCondutor(nome, cpf, telefone, email, numeroCNH, categoriaCNH);
        System.out.println("Condutor cadastrado com sucesso!");
    }

    private void cadastrarVeiculo() {
        System.out.println("\n=== CADASTRO DE VEÍCULO ===");
        System.out.print("CPF do Proprietário: ");
        String cpf = scanner.nextLine();
        
        Condutor proprietario = condutorController.buscarCondutorPorCpf(cpf);
        if (proprietario == null) {
            System.out.println("Condutor não encontrado! Cadastre o condutor primeiro.");
            return;
        }
        
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Cor: ");
        String cor = scanner.nextLine();
        
        System.out.print("Tipo (1-Carro, 2-Moto): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();
        
        if (tipo == 1) {
            System.out.print("Número de Portas: ");
            int numeroPortas = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Tipo de Combustível: ");
            String combustivel = scanner.nextLine();
            
            veiculoController.cadastrarCarro(placa, marca, modelo, ano, cor, 
                                           proprietario, numeroPortas, combustivel);
        } else {
            System.out.print("Cilindradas: ");
            int cilindradas = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Partida Elétrica (true/false): ");
            boolean partidaEletrica = scanner.nextBoolean();
            scanner.nextLine();
            
            veiculoController.cadastrarMoto(placa, marca, modelo, ano, cor, 
                                          proprietario, cilindradas, partidaEletrica);
        }
        System.out.println("Veículo cadastrado com sucesso!");
    }

    private void registrarServico() {
        System.out.println("\n=== REGISTRO DE SERVIÇO ===");
        System.out.print("Placa do Veículo: ");
        String placa = scanner.nextLine();
        
        Veiculo veiculo = veiculoController.buscarVeiculoPorPlaca(placa);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }
        
        System.out.print("Descrição do Serviço: ");
        String descricao = scanner.nextLine();
        System.out.print("Valor: R$ ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        
        servicoController.registrarServico(descricao, valor, veiculo);
        System.out.println("Serviço registrado com sucesso!");
    }

    private void criarOrdemServico() {
        System.out.println("\n=== CRIAÇÃO DE ORDEM DE SERVIÇO ===");
        System.out.print("Placa do Veículo: ");
        String placa = scanner.nextLine();
        
        Veiculo veiculo = veiculoController.buscarVeiculoPorPlaca(placa);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }
        
        ordemServicoController.criarOrdemServico(veiculo);
        System.out.println("Ordem de serviço criada com sucesso!");
    }

    private void listarCondutores() {
        System.out.println("\n=== LISTA DE CONDUTORES ===");
        List<Condutor> condutores = condutorController.listarCondutores();
        for (Condutor condutor : condutores) {
            System.out.println(condutor);
        }
    }

    private void listarVeiculos() {
        System.out.println("\n=== LISTA DE VEÍCULOS ===");
        List<Veiculo> veiculos = veiculoController.listarVeiculos();
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo);
        }
    }

    private void listarServicos() {
        System.out.println("\n=== LISTA DE SERVIÇOS ===");
        List<Servico> servicos = servicoController.listarServicos();
        for (Servico servico : servicos) {
            System.out.println(servico);
        }
    }

    private void listarOrdensServico() {
        System.out.println("\n=== LISTA DE ORDENS DE SERVIÇO ===");
        List<OrdemServico> ordens = ordemServicoController.listarOrdensServico();
        for (OrdemServico ordem : ordens) {
            System.out.println(ordem);
        }
    }

    private void relatorioServicosPorVeiculo() {
        System.out.println("\n=== RELATÓRIO DE SERVIÇOS POR VEÍCULO ===");
        System.out.print("Placa do Veículo: ");
        String placa = scanner.nextLine();
        
        servicoController.gerarRelatorioServicosPorVeiculo(placa);
    }

    private void consultarFipe() {
        System.out.println("\n=== CONSULTA FIPE ===");
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        

        System.out.println("Consulta FIPE - " + marca + " " + modelo + " " + ano);
        System.out.println("Valor aproximado: R$ " + (ano * 1000));
        System.out.println("(Implementação mock - integrar com API real da FIPE)");
    }
}