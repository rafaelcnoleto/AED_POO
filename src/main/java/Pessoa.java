public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String email;

    public Pessoa(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public abstract void exibirDados();

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
}