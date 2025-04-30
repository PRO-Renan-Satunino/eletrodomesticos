import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaEletrodomestico extends JFrame {

    private JTextField txtNome;
    private JTextField txtMarca;
    private JTextField txtVoltagem;
    private JTextField txtPreco;
    private JButton btnCadastrar;
    private JButton btnAtualizar;
    private JButton btnRemover;
    private JButton btnLimpar;
    private JTable tabelaEletrodomesticos;
    private DefaultTableModel modeloTabela;
    private EletrodomesticoControle controller;
    private Eletrodomestico eletrodomesticoSelecionado;

    public TelaEletrodomestico() {
        super("Cadastro de Eletrodomésticos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        controller = new EletrodomesticoControle();
        eletrodomesticoSelecionado = null;

        // Inicializar componentes
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblMarca = new JLabel("Marca:");
        JLabel lblVoltagem = new JLabel("Voltagem:");
        JLabel lblPreco = new JLabel("Preço:");

        txtNome = new JTextField(20);
        txtMarca = new JTextField(20);
        txtVoltagem = new JTextField(20);
        txtPreco = new JTextField(20);

        btnCadastrar = new JButton("Cadastrar");
        btnAtualizar = new JButton("Atualizar");
        btnRemover = new JButton("Remover");
        btnLimpar = new JButton("Limpar");

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Marca", "Voltagem", "Preço"}, 0);
        tabelaEletrodomesticos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaEletrodomesticos);

        // Layout
        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        painelFormulario.add(lblNome);
        painelFormulario.add(txtNome);
        painelFormulario.add(lblMarca);
        painelFormulario.add(txtMarca);
        painelFormulario.add(lblVoltagem);
        painelFormulario.add(txtVoltagem);
        painelFormulario.add(lblPreco);
        painelFormulario.add(txtPreco);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnLimpar);
        btnAtualizar.setEnabled(false); // Desabilitar inicialmente
        btnRemover.setEnabled(false);   // Desabilitar inicialmente

        setLayout(new BorderLayout());
        add(painelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Carregar dados iniciais
        atualizarTabela();

        // Adicionar listeners
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarEletrodomestico();
            }
        });

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarEletrodomestico();
            }
        });

        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerEletrodomestico();
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        tabelaEletrodomesticos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabelaEletrodomesticos.getSelectedRow();
                if (linhaSelecionada != -1) {
                    int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
                    String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
                    String marca = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
                    String voltagem = (String) modeloTabela.getValueAt(linhaSelecionada, 3);
                    double preco = (double) modeloTabela.getValueAt(linhaSelecionada, 4);

                    eletrodomesticoSelecionado = new Eletrodomestico(nome, marca, voltagem, preco);
                    eletrodomesticoSelecionado.setId(id);

                    txtNome.setText(nome);
                    txtMarca.setText(marca);
                    txtVoltagem.setText(voltagem);
                    txtPreco.setText(String.valueOf(preco));

                    btnAtualizar.setEnabled(true);
                    btnRemover.setEnabled(true);
                    btnCadastrar.setEnabled(false);
                }
            }
        });

        setVisible(true);
    }

    private void cadastrarEletrodomestico() {
        String nome = txtNome.getText();
        String marca = txtMarca.getText();
        String voltagem = txtVoltagem.getText();
        String precoTexto = txtPreco.getText();

        if (nome.isEmpty() || marca.isEmpty() || voltagem.isEmpty() || precoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double preco = Double.parseDouble(precoTexto);
            controller.cadastrarEletrodomestico(nome, marca, voltagem, preco);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Eletrodoméstico cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            btnCadastrar.setEnabled(true);
            btnAtualizar.setEnabled(false);
            btnRemover.setEnabled(false);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O preço deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarEletrodomestico() {
        if (eletrodomesticoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nome = txtNome.getText();
        String marca = txtMarca.getText();
        String voltagem = txtVoltagem.getText();
        String precoTexto = txtPreco.getText();

        if (nome.isEmpty() || marca.isEmpty() || voltagem.isEmpty() || precoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double preco = Double.parseDouble(precoTexto);
            controller.atualizarEletrodomestico(eletrodomesticoSelecionado.getId(), nome, marca, voltagem, preco);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Eletrodoméstico atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            eletrodomesticoSelecionado = null;
            btnCadastrar.setEnabled(true);
            btnAtualizar.setEnabled(false);
            btnRemover.setEnabled(false);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O preço deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerEletrodomestico() {
        if (eletrodomesticoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente remover este eletrodoméstico?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                controller.removerEletrodomestico(eletrodomesticoSelecionado.getId());
                atualizarTabela();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Eletrodoméstico removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                eletrodomesticoSelecionado = null;
                btnCadastrar.setEnabled(true);
                btnAtualizar.setEnabled(false);
                btnRemover.setEnabled(false);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtMarca.setText("");
        txtVoltagem.setText("");
        txtPreco.setText("");
        eletrodomesticoSelecionado = null;
        btnCadastrar.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnRemover.setEnabled(false);
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela
        try {
            List<Eletrodomestico> eletrodomesticos = controller.listarEletrodomesticos();
            for (Eletrodomestico eletro : eletrodomesticos) {
                modeloTabela.addRow(new Object[]{eletro.getId(), eletro.getNome(), eletro.getMarca(), eletro.getVoltagem(), eletro.getPreco()});
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaEletrodomestico();
            }
        });
    }
}