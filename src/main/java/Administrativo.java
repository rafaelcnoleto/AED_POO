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

    public boolean autenticar(String user, String pass) {
        return this.usuario.equals(user) && this.senha.equals(pass);
    }

    @Override
    public void exibirDados() {
        System.out.println(">> STAFF: " + nome + " | Cargo: " + cargo);
    }
}