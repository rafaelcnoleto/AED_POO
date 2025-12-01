// Arquivo: Pessoa.java
public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String email;

    public Pessoa(String nome, String cpf, String email) throws ValidacaoException {
        validarDados(nome, cpf, email);
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    private void validarDados(String nome, String cpf, String email) throws ValidacaoException {
        if (nome.length() < 3)
            throw new ValidacaoException("O nome deve ter no mínimo 3 caracteres.");

        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        if (cpfNumerico.length() != 11)
            throw new ValidacaoException("CPF deve conter exatamente 11 dígitos numéricos.");

        if (!email.contains("@"))
            throw new ValidacaoException("E-mail inválido.");
    }

    public abstract void exibirDados();

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
}