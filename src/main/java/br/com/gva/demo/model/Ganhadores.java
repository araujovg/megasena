package br.com.gva.demo.model;

public class Ganhadores implements Comparable<Ganhadores>{

    private int qtdAcertos;
    private String uf;
    private Double rateio;
    private TipoGanhador tipoGanhador;

    public Ganhadores(){}
    
    public Ganhadores(int qtdAcertos, String uf, Double rateio, TipoGanhador tipoGanhador) {
        this.qtdAcertos = qtdAcertos;
        this.uf = uf;
        this.rateio = rateio;
        this.tipoGanhador = tipoGanhador;
    }


    public int getQtdAcertos() {
        return qtdAcertos;
    }


    public void setQtdAcertos(int qtdAcertos) {
        this.qtdAcertos = qtdAcertos;
    }


    public String getUf() {
        return uf;
    }


    public void setUf(String uf) {
        this.uf = uf;
    }


    public Double getRateio() {
        return rateio;
    }


    public void setRateio(Double rateio) {
        this.rateio = rateio;
    }
    


    @Override
    public String toString() {
        return "Ganhadores [qtdAcertos=" + qtdAcertos + ", uf=" + uf + ", rateio=" + rateio + ", tipoGanhador="
                + tipoGanhador + "]";
    }



    public enum TipoGanhador {
        SEIS_ACERTOS,
        CINCO_ACERTOS,
        QUATRO_ACERTOS
    }



    public TipoGanhador getTipoGanhador() {
        return tipoGanhador;
    }

    public void setTipoGanhador(TipoGanhador tipoGanhador) {
        this.tipoGanhador = tipoGanhador;
    }

    @Override
    public int compareTo(Ganhadores o) {
        return Integer.valueOf(this.rateio.compareTo(o.rateio));
    }

}
