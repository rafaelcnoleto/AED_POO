public class Aluno extends Pessoa {
    // Contador estático para gerar matrículas (ex: 2025001...)
    private static int contadorMatricula = 1;

    private String matricula;
    private String nomeCurso;

    public Aluno(String nome, String cpf, String email, String nomeCurso) {
        super(nome, cpf, email);
        this.nomeCurso = nomeCurso;
        this.matricula = gerarNovaMatricula();
    }

    // Construtor auxiliar para login simulado
    public Aluno(String matricula, String nome, String email, String nomeCurso, boolean isLogin) {
        super(nome, "000", email);
        this.matricula = matricula;
        this.nomeCurso = nomeCurso;
    }

    private String gerarNovaMatricula() {
        int ano = 2025;
        return String.format("%d%03d", ano, contadorMatricula++);
    }

    @Override
    public void exibirDados() {
        System.out.println(">> ALUNO: " + nome + " | Matrícula: " + matricula + " | Curso: " + nomeCurso);
    }

    public String getMatricula() { return matricula; }
}