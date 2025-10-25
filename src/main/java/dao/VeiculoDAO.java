package dao;

import model.Veiculo;
import model.Carro;
import model.Moto;
import model.Condutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS veiculos (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "placa VARCHAR(10) UNIQUE NOT NULL, " +
                     "marca VARCHAR(50) NOT NULL, " +
                     "modelo VARCHAR(50) NOT NULL, " +
                     "ano INT NOT NULL, " +
                     "cor VARCHAR(20), " +
                     "tipo VARCHAR(10) NOT NULL, " +
                     "numero_portas INT, " +
                     "tipo_combustivel VARCHAR(20), " +
                     "cilindradas INT, " +
                     "partida_eletrica BOOLEAN, " +
                     "cpf_proprietario VARCHAR(14), " +
                     "FOREIGN KEY (cpf_proprietario) REFERENCES condutores(cpf))";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvar(Veiculo veiculo) {
        String sql = "INSERT INTO veiculos (placa, marca, modelo, ano, cor, tipo, " +
                     "numero_portas, tipo_combustivel, cilindradas, partida_eletrica, cpf_proprietario) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, veiculo.getPlaca());
            pstmt.setString(2, veiculo.getMarca());
            pstmt.setString(3, veiculo.getModelo());
            pstmt.setInt(4, veiculo.getAno());
            pstmt.setString(5, veiculo.getCor());
            pstmt.setString(6, veiculo.getTipo());
            
            if (veiculo instanceof Carro) {
                Carro carro = (Carro) veiculo;
                pstmt.setInt(7, carro.getNumeroPortas());
                pstmt.setString(8, carro.getTipoCombustivel());
                pstmt.setNull(9, Types.INTEGER);
                pstmt.setNull(10, Types.BOOLEAN);
            } else if (veiculo instanceof Moto) {
                Moto moto = (Moto) veiculo;
                pstmt.setNull(7, Types.INTEGER);
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setInt(9, moto.getCilindradas());
                pstmt.setBoolean(10, moto.isPartidaEletrica());
            }
            
            pstmt.setString(11, veiculo.getProprietario().getCpf());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Veiculo> listarTodos() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT v.*, c.nome, c.telefone, c.email, c.numero_cnh, c.categoria_cnh " +
                     "FROM veiculos v JOIN condutores c ON v.cpf_proprietario = c.cpf";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Condutor proprietario = new Condutor(
                    rs.getString("nome"),
                    rs.getString("cpf_proprietario"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("numero_cnh"),
                    rs.getString("categoria_cnh")
                );
                
                String tipo = rs.getString("tipo");
                Veiculo veiculo;
                
                if ("Carro".equals(tipo)) {
                    veiculo = new Carro(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getString("cor"),
                        proprietario,
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel")
                    );
                } else {
                    veiculo = new Moto(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getString("cor"),
                        proprietario,
                        rs.getInt("cilindradas"),
                        rs.getBoolean("partida_eletrica")
                    );
                }
                
                veiculos.add(veiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return veiculos;
    }

    public Veiculo buscarPorPlaca(String placa) {
        String sql = "SELECT v.*, c.nome, c.telefone, c.email, c.numero_cnh, c.categoria_cnh " +
                     "FROM veiculos v JOIN condutores c ON v.cpf_proprietario = c.cpf " +
                     "WHERE v.placa = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, placa);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Condutor proprietario = new Condutor(
                    rs.getString("nome"),
                    rs.getString("cpf_proprietario"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("numero_cnh"),
                    rs.getString("categoria_cnh")
                );
                
                String tipo = rs.getString("tipo");
                
                if ("Carro".equals(tipo)) {
                    return new Carro(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getString("cor"),
                        proprietario,
                        rs.getInt("numero_portas"),
                        rs.getString("tipo_combustivel")
                    );
                } else {
                    return new Moto(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getString("cor"),
                        proprietario,
                        rs.getInt("cilindradas"),
                        rs.getBoolean("partida_eletrica")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}