package br.com.gva.demo.model;

public class Bola {

    private String id;
    private int dezena;

    public Bola(){}

    public Bola(String id, int dezena) {
        this.id = id;
        this.dezena = dezena;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getDezena() {
        return dezena;
    }
    public void setDezena(int dezena) {
        this.dezena = dezena;
    }

    @Override
    public String toString() {
        return "Bola [id=" + id + ", dezena=" + dezena + "]";
    }   

    
}
