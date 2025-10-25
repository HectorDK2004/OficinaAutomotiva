package dao;

import model.Condutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CondutorDAO {
    
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS condutores (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "nome VARCHAR(100) NOT NULL, " +
                     "cpf VARCHAR(14) UNIQUE NOT NULL, " +
                     "telefone VARCHAR(15), " +
                     "email VARCHAR(100), " +
                     "numero_cnh VARCHAR(20), " +
                     "categoria_cnh VARCHAR(2))";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvar(Condutor condutor) {
        String sql = "INSERT INTO condutores (nome, cpf, telefone, email, numero_cnh, categoria_cnh) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, condutor.getNome());
            pstmt.setString(2, condutor.getCpf());
            pstmt.setString(3, condutor.getTelefone());
            pstmt.setString(4, condutor.getEmail());
            pstmt.setString(5, condutor.getNumeroCNH());
            pstmt.setString(6, condutor.getCategoriaCNH());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Condutor> listarTodos() {
        List<Condutor> condutores = new ArrayList<>();
        String sql = "SELECT * FROM condutores";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Condutor condutor = new Condutor(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("numero_cnh"),
                    rs.getString("categoria_cnh")
                );
                condutores.add(condutor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return condutores;
    }

    public Condutor buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM condutores WHERE cpf = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Condutor(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("numero_cnh"),
                    rs.getString("categoria_cnh")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}