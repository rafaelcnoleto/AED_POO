// Arquivo: Administrativo.java
public class Administrativo extends Pessoa {
    private String cargo;
    private String usuario;
    private String senha;

    public Administrativo(String nome, String cpf, String email, String cargo, String usuario, String senha) throws ValidacaoException {
        super(nome, cpf, email);
        if(usuario.length() < 3 || senha.length() < 3)
            throw new ValidacaoException("Usuário ou Senha muito curtos.");

        this.cargo = cargo;
        this.usuario = usuario;
        this.senha = senha;
    }

    public boolean autenticar(String user, String pass) {
        return this.usuario.equals(user) && this.senha.equals(pass);
    }

    @Override
    public void exibirDados() {
        System.out.println("ADMIN | Nome: " + nome + " | Cargo: " + cargo);
    }

    public String getUsuario() { return usuario; }
}