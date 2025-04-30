import java.sql.SQLException;
import java.util.List;

public class EletrodomesticoControle {

    private EletrodomesticoDAO eletrodomesticoDAO;

    public EletrodomesticoControle() {
        this.eletrodomesticoDAO = new EletrodomesticoDAO();
    }

    public void cadastrarEletrodomestico(String nome, String marca, String voltagem, double preco) {
        Eletrodomestico eletrodomestico = new Eletrodomestico(nome, marca, voltagem, preco);
        try {
            eletrodomesticoDAO.cadastrar(eletrodomestico);
        } catch (SQLException e) {
            // Tratar o erro na camada de apresentação (Tela)
            throw new RuntimeException("Erro ao cadastrar eletrodoméstico.", e);
        }
    }

    public List<Eletrodomestico> listarEletrodomesticos() {
        try {
            return eletrodomesticoDAO.listar();
        } catch (SQLException e) {
            // Tratar o erro na camada de apresentação (Tela)
            throw new RuntimeException("Erro ao listar eletrodomésticos.", e);
        }
    }

    public void atualizarEletrodomestico(int id, String nome, String marca, String voltagem, double preco) {
        Eletrodomestico eletrodomestico = new Eletrodomestico(nome, marca, voltagem, preco);
        eletrodomestico.setId(id);
        try {
            eletrodomesticoDAO.atualizar(eletrodomestico);
        } catch (SQLException e) {
            // Tratar o erro na camada de apresentação (Tela)
            throw new RuntimeException("Erro ao atualizar eletrodoméstico.", e);
        }
    }

    public void removerEletrodomestico(int id) {
        try {
            eletrodomesticoDAO.remover(id);
        } catch (SQLException e) {
            // Tratar o erro na camada de apresentação (Tela)
            throw new RuntimeException("Erro ao remover eletrodoméstico.", e);
        }
    }
}