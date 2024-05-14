package br.com.gva.demo.model;

public class QuantidadeVezesDezenaFoiSorteado implements Comparable<QuantidadeVezesDezenaFoiSorteado> {

    private int dezena;
    private int qtd;

    public QuantidadeVezesDezenaFoiSorteado(){}

    public QuantidadeVezesDezenaFoiSorteado(int dezena, int qtd) {
        this.dezena = dezena;
        this.qtd = qtd;
    }
    public int getDezena() {
        return dezena;
    }
    public void setDezena(int dezena) {
        this.dezena = dezena;
    }
    public int getQtd() {
        return qtd;
    }
    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    @Override
    public String toString() {
        return "QuantidadeVezesDezenaFoiSorteado [dezena=" + dezena + ", qtd=" + qtd + "]";
    }

    @Override
    public int compareTo(QuantidadeVezesDezenaFoiSorteado o) {
        return Integer.compare(this.dezena, o.dezena);
    }
    

}
