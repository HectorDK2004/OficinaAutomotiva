package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Carro;
import model.Condutor;
import model.Moto;
import model.OrdemServico;
import model.Veiculo;

public class OrdemServicoDAO {
    
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS ordens_servico (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "data_abertura TIMESTAMP NOT NULL, " +
                     "data_conclusao TIMESTAMP, " +
                     "status VARCHAR(20) NOT NULL, " +
                     "placa_veiculo VARCHAR(10), " +
                     "valor_total DECIMAL(10,2), " +
                     "FOREIGN KEY (placa_veiculo) REFERENCES veiculos(placa))";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvar(OrdemServico ordem) {
        String sql = "INSERT INTO ordens_servico (data_abertura, data_conclusao, status, placa_veiculo, valor_total) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setTimestamp(1, Timestamp.valueOf(ordem.getDataAbertura()));
            if (ordem.getDataConclusao() != null) {
                pstmt.setTimestamp(2, Timestamp.valueOf(ordem.getDataConclusao()));
            } else {
                pstmt.setNull(2, Types.TIMESTAMP);
            }
            pstmt.setString(3, ordem.getStatus());
            pstmt.setString(4, ordem.getVeiculo().getPlaca());
            pstmt.setDouble(5, ordem.getValorTotal());
            
            pstmt.executeUpdate();
            
            // Obter o ID gerado
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                ordem.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OrdemServico> listarTodos() {
        List<OrdemServico> ordens = new ArrayList<>();
        String sql = "SELECT os.*, v.marca, v.modelo, v.ano, v.cor, v.tipo " +
                     "FROM ordens_servico os JOIN veiculos v ON os.placa_veiculo = v.placa";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Veiculo veiculo = criarVeiculoBasico(rs);
                
                OrdemServico ordem = new OrdemServico(
                    rs.getInt("id"),
                    rs.getTimestamp("data_abertura").toLocalDateTime(),
                    rs.getTimestamp("data_conclusao") != null ? 
                        rs.getTimestamp("data_conclusao").toLocalDateTime() : null,
                    rs.getString("status"),
                    veiculo
                );
                ordem.setValorTotal(rs.getDouble("valor_total"));
                
                ordens.add(ordem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ordens;
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