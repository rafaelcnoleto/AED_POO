// Arquivo: Administrativo.java
public class Administrativo extends Pessoa {
    private String cargo;
    private String usuario;
    private String senha;

    public Administrativo(String nome, String cpf, String email, String cargo, String usuario, String senha) {
        super(nome, cpf, email);
        this.cargo = cargo;
        this.usuario = usuario;
        this.senha = senha;
    }

    public void cadastrarAluno(Aluno a) {
        BaseDeDadosMemoria.getInstancia().salvar(a);
    }

    public void cadastrarProfessor(Professor p) {
        BaseDeDadosMemoria.getInstancia().salvar(p);
    }

    public void criarTurma(Turma t) {
        BaseDeDadosMemoria.getInstancia().salvar(t);
    }

    // --- Novos Getters para o Login ---
    public String getUsuario() { return usuario; }
    public String getSenha() { return senha; }

    @Override
    public void exibirDados() {
        System.out.println("Admin: " + nome + " | Cargo: " + cargo);
    }
}