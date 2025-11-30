// Arquivo: SistemaAcademicoGUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SistemaAcademicoGUI extends JFrame {

    private Pessoa usuarioLogado; // Genérico, pode ser Admin, Aluno ou Prof

    // Construtor alterado para receber quem logou
    public SistemaAcademicoGUI(Pessoa usuario) {
        this.usuarioLogado = usuario;

        setTitle("Sistema Acadêmico SOL - Logado como: " + usuario.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Alunos", criarPainelAlunos());
        tabbedPane.addTab("Professores", criarPainelProfessores());
        tabbedPane.addTab("Turmas", criarPainelTurmas());

        add(tabbedPane);
    }

    // --- TELA DE LOGIN ---
    public static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login - Sistema SOL");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new GridLayout(5, 1, 10, 10));

        // Componentes
        String[] tipos = {"Administrativo", "Professor", "Aluno"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);

        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JButton btnEntrar = new JButton("ENTRAR");

        // Paineis de organização
        JPanel pnlTipo = new JPanel(new FlowLayout());
        pnlTipo.add(new JLabel("Tipo de Usuário:"));
        pnlTipo.add(comboTipo);

        JPanel pnlUser = new JPanel(new GridLayout(2, 1));
        pnlUser.add(new JLabel("Usuário / Matrícula:"));
        pnlUser.add(txtUser);

        JPanel pnlPass = new JPanel(new GridLayout(2, 1));
        pnlPass.add(new JLabel("Senha (apenas Admin):"));
        pnlPass.add(txtPass);

        // Lógica do Botão
        btnEntrar.addActionListener(e -> {
            String tipo = (String) comboTipo.getSelectedItem();
            String usuario = txtUser.getText();
            String senha = new String(txtPass.getPassword());

            Pessoa userEncontrado = null;

            if (tipo.equals("Administrativo")) {
                // Validação de Admin (com senha)
                List<Administrativo> admins = BaseDeDadosMemoria.getInstancia().getTodosAdmins();
                for (Administrativo ad : admins) {
                    if (ad.getUsuario().equals(usuario) && ad.getSenha().equals(senha)) {
                        userEncontrado = ad;
                        break;
                    }
                }
                if (userEncontrado == null) {
                    JOptionPane.showMessageDialog(loginFrame, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } else {
                // Simulação de login para Aluno/Professor (sem senha, apenas checa existência ou nome)
                // Para simplificar o teste, vamos criar um usuário temporário se for Aluno/Prof
                // pois não temos base de dados de senhas para eles no diagrama original.
                if(usuario.isEmpty()) {
                    JOptionPane.showMessageDialog(loginFrame, "Digite seu nome ou matrícula para entrar.");
                    return;
                }
                userEncontrado = (tipo.equals("Aluno")) ?
                        new Aluno(usuario, "", "", usuario, "Geral") :
                        new Professor(usuario, "", "", usuario, "PhD");
            }

            // Abrir sistema principal
            new SistemaAcademicoGUI(userEncontrado).setVisible(true);
            loginFrame.dispose(); // Fecha tela de login
        });

        loginFrame.add(pnlTipo);
        loginFrame.add(pnlUser);
        loginFrame.add(pnlPass);
        loginFrame.add(new JLabel("")); // Espaçador
        loginFrame.add(btnEntrar);

        loginFrame.setVisible(true);
    }

    // --- PAINÉIS DO SISTEMA (Lógica adaptada para verificar permissão) ---

    private JPanel criarPainelAlunos() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] colunas = {"Nome", "CPF", "Matrícula", "Curso"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable table = new JTable(model);
        atualizarTabelaAlunos(model);

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtMatricula = new JTextField();
        JComboBox<Curso> comboCursos = new JComboBox<>();

        for(Curso c : BaseDeDadosMemoria.getInstancia().getTodosCursos()) {
            comboCursos.addItem(c);
        }

        formPanel.add(new JLabel("Nome:")); formPanel.add(txtNome);
        formPanel.add(new JLabel("CPF:")); formPanel.add(txtCpf);
        formPanel.add(new JLabel("Email:")); formPanel.add(txtEmail);
        formPanel.add(new JLabel("Matrícula:")); formPanel.add(txtMatricula);
        formPanel.add(new JLabel("Curso:")); formPanel.add(comboCursos);

        JButton btnSalvar = new JButton("Cadastrar Aluno");

        // Só habilita o botão se for Administrativo
        if (!(usuarioLogado instanceof Administrativo)) {
            btnSalvar.setEnabled(false);
            btnSalvar.setText("Apenas Admin pode cadastrar");
        }

        btnSalvar.addActionListener(e -> {
            try {
                if (usuarioLogado instanceof Administrativo) {
                    Administrativo admin = (Administrativo) usuarioLogado;
                    String nomeCurso = comboCursos.getSelectedItem().toString();
                    Aluno novoAluno = new Aluno(txtNome.getText(), txtCpf.getText(), txtEmail.getText(), txtMatricula.getText(), nomeCurso);

                    admin.cadastrarAluno(novoAluno);
                    atualizarTabelaAlunos(model);
                    JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
                    txtNome.setText(""); txtCpf.setText(""); txtEmail.setText(""); txtMatricula.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(btnSalvar, BorderLayout.SOUTH);
        return panel;
    }

    private void atualizarTabelaAlunos(DefaultTableModel model) {
        model.setRowCount(0);
        for (Aluno a : BaseDeDadosMemoria.getInstancia().getTodosAlunos()) {
            model.addRow(new Object[]{a.getNome(), a.getCpf(), a.getMatricula(), "..."});
        }
    }

    private JPanel criarPainelProfessores() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] colunas = {"Nome", "Titulação", "Matrícula Func."};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable table = new JTable(model);

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtMatricula = new JTextField();
        JTextField txtTitulacao = new JTextField();

        formPanel.add(new JLabel("Nome:")); formPanel.add(txtNome);
        formPanel.add(new JLabel("CPF:")); formPanel.add(txtCpf);
        formPanel.add(new JLabel("Email:")); formPanel.add(txtEmail);
        formPanel.add(new JLabel("Matrícula F.:")); formPanel.add(txtMatricula);
        formPanel.add(new JLabel("Titulação:")); formPanel.add(txtTitulacao);

        JButton btnSalvar = new JButton("Cadastrar Professor");
        if (!(usuarioLogado instanceof Administrativo)) {
            btnSalvar.setEnabled(false);
            btnSalvar.setText("Apenas Admin pode cadastrar");
        }

        btnSalvar.addActionListener(e -> {
            if (usuarioLogado instanceof Administrativo) {
                Administrativo admin = (Administrativo) usuarioLogado;
                Professor novoProf = new Professor(txtNome.getText(), txtCpf.getText(), txtEmail.getText(), txtMatricula.getText(), txtTitulacao.getText());
                admin.cadastrarProfessor(novoProf);
                model.addRow(new Object[]{novoProf.getNome(), txtTitulacao.getText(), novoProf.getMatriculaFuncional()});
                JOptionPane.showMessageDialog(this, "Professor cadastrado!");
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(btnSalvar, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel criarPainelTurmas() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea areaTurmas = new JTextArea();
        areaTurmas.setEditable(false);

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JTextField txtCodigo = new JTextField();
        JTextField txtDisciplina = new JTextField();

        formPanel.add(new JLabel("Cód Turma:")); formPanel.add(txtCodigo);
        formPanel.add(new JLabel("Nome Disciplina:")); formPanel.add(txtDisciplina);

        JButton btnCriar = new JButton("Criar Turma");
        if (!(usuarioLogado instanceof Administrativo)) {
            btnCriar.setEnabled(false);
            btnCriar.setText("Apenas Admin pode criar");
        }

        btnCriar.addActionListener(e -> {
            if (usuarioLogado instanceof Administrativo) {
                Administrativo admin = (Administrativo) usuarioLogado;
                Disciplina d = new Disciplina(txtDisciplina.getText(), "D01", 60);
                Turma t = new Turma(txtCodigo.getText(), 2025, d, null);
                admin.criarTurma(t);
                areaTurmas.append("Turma Criada: " + t.toString() + "\n");
            }
        });

        panel.add(new JScrollPane(areaTurmas), BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(btnCriar, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}

        // Inicia pela tela de Login
        SwingUtilities.invokeLater(() -> {
            showLoginScreen();
        });
    }
}