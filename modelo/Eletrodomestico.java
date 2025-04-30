public class Eletrodomestico {
    private int id;
    private String nome;
    private String marca;
    private String voltagem;
    private double preco;

    public Eletrodomestico() {
    }

    public Eletrodomestico(String nome, String marca, String voltagem, double preco) {
        this.nome = nome;
        this.marca = marca;
        this.voltagem = voltagem;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getVoltagem() {
        return voltagem;
    }

    public void setVoltagem(String voltagem) {
        this.voltagem = voltagem;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome; // Para facilitar a visualização em listas
    }
}