package model;

public class Moto extends Veiculo {
    private int cilindradas;
    private boolean partidaEletrica;

    public Moto(String placa, String marca, String modelo, int ano, String cor, 
                Condutor proprietario, int cilindradas, boolean partidaEletrica) {
        super(placa, marca, modelo, ano, cor, proprietario);
        this.cilindradas = cilindradas;
        this.partidaEletrica = partidaEletrica;
    }

    public int getCilindradas() { return cilindradas; }
    public void setCilindradas(int cilindradas) { this.cilindradas = cilindradas; }
    
    public boolean isPartidaEletrica() { return partidaEletrica; }
    public void setPartidaEletrica(boolean partidaEletrica) { this.partidaEletrica = partidaEletrica; }

    @Override
    public String getTipo() {
        return "Moto";
    }

    @Override
    public String toString() {
        return "Moto{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", cor='" + cor + '\'' +
                ", cilindradas=" + cilindradas +
                ", partidaEletrica=" + partidaEletrica +
                ", proprietario=" + proprietario.getNome() +
                '}';
    }
}