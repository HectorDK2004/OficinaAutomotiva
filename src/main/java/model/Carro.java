package model;

public class Carro extends Veiculo {
    private int numeroPortas;
    private String tipoCombustivel;

    public Carro(String placa, String marca, String modelo, int ano, String cor, 
                 Condutor proprietario, int numeroPortas, String tipoCombustivel) {
        super(placa, marca, modelo, ano, cor, proprietario);
        this.numeroPortas = numeroPortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    public int getNumeroPortas() { return numeroPortas; }
    public void setNumeroPortas(int numeroPortas) { this.numeroPortas = numeroPortas; }
    
    public String getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }

    @Override
    public String getTipo() {
        return "Carro";
    }

    @Override
    public String toString() {
        return "Carro{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", cor='" + cor + '\'' +
                ", numeroPortas=" + numeroPortas +
                ", tipoCombustivel='" + tipoCombustivel + '\'' +
                ", proprietario=" + proprietario.getNome() +
                '}';
    }
}