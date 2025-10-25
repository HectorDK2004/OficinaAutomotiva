package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Carro;
import model.Condutor;
import model.Moto;
import model.Servico;
import model.Veiculo;

public class ServicoDAO {
    
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS servicos (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "descricao VARCHAR(200) NOT NULL, " +
                     "valor DECIMAL(10,2) NOT NULL, " +
                     "data_execucao TIMESTAMP, " +
                     "placa_veiculo VARCHAR(10), " +
                     "FOREIGN KEY (placa_veiculo) REFERENCES veiculos(placa))";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvar(Servico servico) {
        String sql = "INSERT INTO servicos (descricao, valor, data_execucao, placa_veiculo) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, servico.getDescricao());
            pstmt.setDouble(2, servico.getValor());
            pstmt.setTimestamp(3, Timestamp.valueOf(servico.getDataExecucao()));
            pstmt.setString(4, servico.getVeiculo().getPlaca());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Servico> listarTodos() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT s.*, v.marca, v.modelo, v.ano, v.cor, v.tipo " +
                     "FROM servicos s JOIN veiculos v ON s.placa_veiculo = v.placa";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Veiculo veiculo = criarVeiculoBasico(rs);
                
                Servico servico = new Servico(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data_execucao").toLocalDateTime(),
                    veiculo
                );
                
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return servicos;
    }

    public List<Servico> listarPorVeiculo(String placa) {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT s.*, v.marca, v.modelo, v.ano, v.cor, v.tipo " +
                     "FROM servicos s JOIN veiculos v ON s.placa_veiculo = v.placa " +
                     "WHERE s.placa_veiculo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, placa);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Veiculo veiculo = criarVeiculoBasico(rs);
                
                Servico servico = new Servico(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data_execucao").toLocalDateTime(),
                    veiculo
                );
                
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return servicos;
    }

    private Veiculo criarVeiculoBasico(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String placa = rs.getString("placa_veiculo");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        int ano = rs.getInt("ano");
        String cor = rs.getString("cor");

        Condutor proprietarioBasico = new Condutor(
            "Proprietário",
            "000.000.000-00", 
            "(00) 00000-0000", 
            "proprietario@email.com", 
            "00000000000", 
            "B" 
        );
        
        if ("Carro".equals(tipo)) {
            return new Carro(placa, marca, modelo, ano, cor, proprietarioBasico, 4, "Flex");
        } else {
            return new Moto(placa, marca, modelo, ano, cor, proprietarioBasico, 150, false);
        }
    }
}