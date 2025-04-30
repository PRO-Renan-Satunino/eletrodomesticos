import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EletrodomesticoDAO {

    public void cadastrar(Eletrodomestico eletrodomestico) throws SQLException {
        String sql = "INSERT INTO eletrodomestico (nome, marca, voltagem, preco) VALUES (?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = ConexaoMySQL.getConnection();
            pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, eletrodomestico.getNome());
            pstmt.setString(2, eletrodomestico.getMarca());
            pstmt.setString(3, eletrodomestico.getVoltagem());
            pstmt.setDouble(4, eletrodomestico.getPreco());
            pstmt.executeUpdate();
            System.out.println("Eletrodoméstico cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar eletrodoméstico: " + e.getMessage());
            throw e;
        } finally {
            ConexaoMySQL.closeConnection(conexao);
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public List<Eletrodomestico> listar() throws SQLException {
        String sql = "SELECT id, nome, marca, voltagem, preco FROM eletrodomestico";
        List<Eletrodomestico> eletrodomesticos = new ArrayList<>();
        Connection conexao = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoMySQL.getConnection();
            stmt = conexao.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Eletrodomestico eletrodomestico = new Eletrodomestico();
                eletrodomestico.setId(rs.getInt("id"));
                eletrodomestico.setNome(rs.getString("nome"));
                eletrodomestico.setMarca(rs.getString("marca"));
                eletrodomestico.setVoltagem(rs.getString("voltagem"));
                eletrodomestico.setPreco(rs.getDouble("preco"));
                eletrodomesticos.add(eletrodomestico);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar eletrodomésticos: " + e.getMessage());
            throw e;
        } finally {
            ConexaoMySQL.closeConnection(conexao);
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return eletrodomesticos;
    }

    public void atualizar(Eletrodomestico eletrodomestico) throws SQLException {
        String sql = "UPDATE eletrodomestico SET nome = ?, marca = ?, voltagem = ?, preco = ? WHERE id = ?";
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = ConexaoMySQL.getConnection();
            pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, eletrodomestico.getNome());
            pstmt.setString(2, eletrodomestico.getMarca());
            pstmt.setString(3, eletrodomestico.getVoltagem());
            pstmt.setDouble(4, eletrodomestico.getPreco());
            pstmt.setInt(5, eletrodomestico.getId());
            pstmt.executeUpdate();
            System.out.println("Eletrodoméstico atualizado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar eletrodoméstico: " + e.getMessage());
            throw e;
        } finally {
            ConexaoMySQL.closeConnection(conexao);
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM eletrodomestico WHERE id = ?";
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = ConexaoMySQL.getConnection();
            pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Eletrodoméstico removido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao remover eletrodoméstico: " + e.getMessage());
            throw e;
        } finally {
            ConexaoMySQL.closeConnection(conexao);
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}