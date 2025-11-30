public class Aluno extends Pessoa {
    private String matricula;
    private String curso;

    public Aluno(String nome, String cpf, String email, String matricula, String curso) {
        super(nome, cpf, email);
        this.matricula = matricula;
        this.curso = curso;
    }

    @Override
    public void exibirDados() {
        System.out.println("Aluno: " + nome + " | Matrícula: " + matricula);
    }

    public String getMatricula() { return matricula; }

    @Override
    public String toString() { return nome + " (" + matricula + ")"; }
}