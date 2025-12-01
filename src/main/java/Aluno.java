// Arquivo: Aluno.java
public class Aluno extends Pessoa {
    private static int contadorMatricula = 2025000;

    private String matricula;
    private Curso curso; // Associação direta com a classe Curso

    public Aluno(String nome, String cpf, String email, Curso curso) throws ValidacaoException {
        super(nome, cpf, email);
        if(curso == null) throw new ValidacaoException("O aluno deve estar vinculado a um curso.");

        this.curso = curso;
        this.matricula = String.valueOf(++contadorMatricula);
    }

    @Override
    public void exibirDados() {
        System.out.println("ALUNO | Mat: " + matricula + " | Nome: " + nome + " | Curso: " + curso.getNome());
    }

    public String getMatricula() { return matricula; }
    public Curso getCurso() { return curso; }
}