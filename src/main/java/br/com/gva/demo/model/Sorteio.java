package br.com.gva.demo.model;

import java.time.LocalDate;
import java.util.List;

public class Sorteio {

    private Long concurso;
    private LocalDate dataSorteio;
    private List<Integer> dezenas;
    private List<Ganhadores> ganhadores;
    private Double acumulado;

    public Sorteio(){}

    public Sorteio(Long concurso, LocalDate dataSorteio, List<Integer> dezenas, List<Ganhadores> ganhadores,
            Double acumulado) {
        this.concurso = concurso;
        this.dataSorteio = dataSorteio;
        this.dezenas = dezenas;
        this.ganhadores = ganhadores;
        this.acumulado = acumulado;
    }

    public Long getConcurso() {
        return concurso;
    }

    public void setConcurso(Long concurso) {
        this.concurso = concurso;
    }

    public LocalDate getDataSorteio() {
        return dataSorteio;
    }

    public void setDataSorteio(LocalDate dataSorteio) {
        this.dataSorteio = dataSorteio;
    }

    public List<Integer> getDezenas() {
        return dezenas;
    }

    public void setDezenas(List<Integer> dezenas) {
        this.dezenas = dezenas;
    }

    public List<Ganhadores> getGanhadores() {
        return ganhadores;
    }

    public void setGanhadores(List<Ganhadores> ganhadores) {
        this.ganhadores = ganhadores;
    }

    public Double getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(Double acumulado) {
        this.acumulado = acumulado;
    }

    @Override
    public String toString() {
        return "Sorteio [concurso=" + concurso + ", dataSorteio=" + dataSorteio + ", dezenas=" + dezenas
                + ", ganhadores=" + ganhadores + ", acumulado=" + acumulado + "]";
    }

}
