package model;

public class Condutor extends Pessoa {
    private String numeroCNH;
    private String categoriaCNH;

    public Condutor(String nome, String cpf, String telefone, String email, 
                   String numeroCNH, String categoriaCNH) {
        super(nome, cpf, telefone, email);
        this.numeroCNH = numeroCNH;
        this.categoriaCNH = categoriaCNH;
    }

    public String getNumeroCNH() { return numeroCNH; }
    public void setNumeroCNH(String numeroCNH) { this.numeroCNH = numeroCNH; }
    
    public String getCategoriaCNH() { return categoriaCNH; }
    public void setCategoriaCNH(String categoriaCNH) { this.categoriaCNH = categoriaCNH; }

    @Override
    public String toString() {
        return "Condutor{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", numeroCNH='" + numeroCNH + '\'' +
                ", categoriaCNH='" + categoriaCNH + '\'' +
                '}';
    }
}