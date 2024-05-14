package br.com.gva.demo.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.StreamSupport;

import javax.management.InvalidAttributeValueException;

import org.apache.commons.collections4.list.TreeList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import br.com.gva.demo.model.Ganhadores;
import br.com.gva.demo.model.Ganhadores.TipoGanhador;
import br.com.gva.demo.model.QuantidadeVezesDezenaFoiSorteado;
import br.com.gva.demo.model.Sorteio;

@Service
public class PlanilhaService {

    private static final String FILE_PATH = "/home/gabriel/Dev/Mega-Sena.xlsx";

    public XSSFWorkbook lePlanilha() throws IOException {

        FileInputStream file = new FileInputStream(new File(FILE_PATH));
        XSSFWorkbook planilha = new XSSFWorkbook(file);

        return planilha;
    }

    public XSSFSheet leTabela() {
        try {
            return lePlanilha().getSheetAt(0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void imprimeTabela(){
        montaSorteioPorLinhaDaPlanilha().forEach(sorteio -> System.out.println(sorteio.toString() + "\n"));
    }

    public void imprimeListaSorteados(){
        verificaQuantidadeVezesNumeroFoiSorteado().stream().forEach(x -> System.out.println(x.toString()));
    }

    public List<Sorteio> montaSorteioPorLinhaDaPlanilha(){
        List<Row> linhas = new ArrayList<>();
        Iterable<Row> linhasIterable = () -> leTabela().rowIterator();
         DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        linhas = StreamSupport.stream(linhasIterable.spliterator(), false).toList();

        return linhas.stream().skip(1).map(linha -> {
            Sorteio sorteio = new Sorteio();
            sorteio.setConcurso( (long) linha.getCell(0).getNumericCellValue());
            sorteio.setDataSorteio(LocalDate.parse(linha.getCell(1).getStringCellValue(), formatadorDeData));
            sorteio.setDezenas(montaListaBolasSorteadas(linha));
            sorteio.setGanhadores(montaListaGanhadores(linha));
            sorteio.setAcumulado(Double.parseDouble(linha.getCell(15).getStringCellValue().replaceAll("[^0-9,]", "")
            .replaceAll(",", ".")));
            return sorteio;
        }).toList();
        
    }

    protected List<Integer> montaListaBolasSorteadas(Row linha) {
        List<Integer> bolas = new ArrayList<>();
        for (int i = 2; i < 8; i++) {
            bolas.add((int) linha.getCell(i).getNumericCellValue());
        }
        return bolas;
    }

    protected List<Ganhadores> montaListaGanhadores(Row linha) {
        List<Ganhadores> ganhadores = new ArrayList<>();
        //Cria e adiciona à lista os ganhadores com 6 acertos
        try {
            ganhadores.add(new Ganhadores(
                (int) linha.getCell(8).getNumericCellValue(),
                linha.getCell(9).getStringCellValue(),
                Double.parseDouble(linha.getCell(10).getStringCellValue().replaceAll("[^0-9,]", "")
                .replaceAll(",", ".")),
                verificaTipoGanhador(linha.getCell(8).getColumnIndex()))
            );
            //Cria e adiciona os ganhadores com 5 acertos
            ganhadores.add(new Ganhadores(
                (int) linha.getCell(11).getNumericCellValue(),
                null,
                Double.parseDouble(linha.getCell(12).getStringCellValue().replaceAll("[^0-9,]", "")
                .replaceAll(",", ".")),
                verificaTipoGanhador(linha.getCell(11).getColumnIndex()))
            );
            //Cria e adiciona os ganhadores com 4 acertos
            ganhadores.add(new Ganhadores(
                (int) linha.getCell(13).getNumericCellValue(),
                null,
                Double.parseDouble(linha.getCell(14).getStringCellValue().replaceAll("[^0-9,]", "")
                .replaceAll(",", ".")),
                verificaTipoGanhador(linha.getCell(13).getColumnIndex()))
            );
        } catch(InvalidAttributeValueException exception) {
            System.out.println(exception.getMessage());
        }
        return ganhadores;
    }

    public Set<QuantidadeVezesDezenaFoiSorteado> verificaQuantidadeVezesNumeroFoiSorteado() {
        Set<QuantidadeVezesDezenaFoiSorteado> lista = new TreeSet<>();
        montaSorteioPorLinhaDaPlanilha().stream().skip(1).forEach(sorteio -> sorteio.getDezenas().forEach(bola -> {
            if (lista.stream().anyMatch(x -> x.getDezena() == bola)) {
                QuantidadeVezesDezenaFoiSorteado qtdDezenaFoiSorteado = new QuantidadeVezesDezenaFoiSorteado();
                qtdDezenaFoiSorteado = lista.stream().filter(x -> x.getDezena() == bola).findFirst().get();
                qtdDezenaFoiSorteado.setQtd(qtdDezenaFoiSorteado.getQtd() + 1);
                lista.add(qtdDezenaFoiSorteado);
            }
            else {
                QuantidadeVezesDezenaFoiSorteado qtdDezenaFoiSorteado = new QuantidadeVezesDezenaFoiSorteado();
                qtdDezenaFoiSorteado.setDezena(bola);
                qtdDezenaFoiSorteado.setQtd(qtdDezenaFoiSorteado.getQtd() + 1);
                lista.add(qtdDezenaFoiSorteado);
            }
        }));
        return lista;
    }

    public int qtdConcursosSemVencedoresEmSeisDezenas(){
        return montaSorteioPorLinhaDaPlanilha().stream()
            .filter(sorteio -> sorteio.getGanhadores().get(0).getQtdAcertos() == 0)
            .toList().size();
    }

    public int qtdGanhadoresMegaTodosConcursos() {
        return montaSorteioPorLinhaDaPlanilha().stream()
            .map(sorteio -> sorteio.getGanhadores().get(0))
            .map(ganhador -> ganhador.getQtdAcertos())
            .reduce(0, Integer::sum);            
    }

    public int qtdGanhadoresQuinaTodosConcursos() {
        return montaSorteioPorLinhaDaPlanilha().stream()
            .map(sorteio -> sorteio.getGanhadores().get(1))
            .map(ganhador -> ganhador.getQtdAcertos())
            .reduce(0, Integer::sum);            
    }

    public int qtdGanhadoresQuadraTodosConcursos() {
        return montaSorteioPorLinhaDaPlanilha().stream()
            .map(sorteio -> sorteio.getGanhadores().get(2))
            .map(ganhador -> ganhador.getQtdAcertos())
            .reduce(0, Integer::sum);            
    }

    public Double verificaMenorValorPagoParaQuadra(){
        return montaSorteioPorLinhaDaPlanilha().stream()
            .map(sorteio -> sorteio.getGanhadores().get(2))
            .min(Comparator.comparingDouble(Ganhadores::getRateio)).get().getRateio();
    }

    public Double verificaMaiorValorPagoParaQuadra(){
        return montaSorteioPorLinhaDaPlanilha().stream()
            .map(sorteio -> sorteio.getGanhadores().get(2))
            .min(Comparator.comparingDouble(Ganhadores::getRateio)).get().getRateio();
    }

    public Double verificaMenorValorPagoParaQuina(){
        return montaSorteioPorLinhaDaPlanilha().stream()
            .map(sorteio -> sorteio.getGanhadores().get(1))
            .min(Comparator.comparingDouble(Ganhadores::getRateio)).get().getRateio();
    }

    public Double verificaMaiorValorPagoParaQuina(){
        return montaSorteioPorLinhaDaPlanilha().stream()
            .map(sorteio -> sorteio.getGanhadores().get(1))
            .max(Comparator.comparingDouble(Ganhadores::getRateio)).get().getRateio();
    }

    public Double verificaMenorValorPagoParaMega(){
        return montaSorteioPorLinhaDaPlanilha().stream()
            .map(sorteio -> sorteio.getGanhadores().get(0))
            .filter(ganhador -> ganhador.getRateio() > 0)
            .min(Comparator.comparingDouble(Ganhadores::getRateio)).get().getRateio();
    }

    public Double verificaMaiorValorPagoParaMega(){
        return montaSorteioPorLinhaDaPlanilha().stream()
            .map(sorteio -> sorteio.getGanhadores().get(0))
            .max(Comparator.comparingDouble(Ganhadores::getRateio)).get().getRateio();
    }

    protected TipoGanhador verificaTipoGanhador(int numeroIndiceColuna) throws InvalidAttributeValueException{
        switch (numeroIndiceColuna) {
            case 8:
                return TipoGanhador.SEIS_ACERTOS;        
            case 11:
                return TipoGanhador.CINCO_ACERTOS;
            case 13:
                return TipoGanhador.QUATRO_ACERTOS;
            default:
                throw new InvalidAttributeValueException("Valor passado não corresponde à uma célula válida");
        }
    }

    public void novaAposta(int operacao) {
        if (operacao == 1) {
            verificaSeApostaJaFoiSorteada(recebeApostaUsuario()) ;
        } else verificaSeApostaJaFoiSorteada(sorteioAleatorio());
    }

    public List<Integer> recebeApostaUsuario() {
        Scanner scan = new Scanner(System.in);
        System.out.println("**************** Nova aposta *****************");
        List<Integer> dezenas = new TreeList<>();
        while (dezenas.size() < 6) {
            System.out.println("Digite sua dezena de 1 - 60");
            dezenas.add(scan.nextInt());
        }
        scan.close();
        return dezenas;
    }

    public boolean verificaSeApostaJaFoiSorteada(List<Integer> aposta) {
        System.out.println("**************** Verificando aposta *****************");
        System.out.println("Sua aposta: " + aposta);
        var resultado = montaSorteioPorLinhaDaPlanilha().stream().anyMatch(sorteio -> sorteio.getDezenas().containsAll(aposta));
        if (resultado) {
            System.out.println("Sua aposta já foi sorteada.");
        }
        else System.out.println("Sua aposta foi registrada com sucesso.");
        return resultado;
    }

    public List<Integer> sorteioAleatorio() {
        List<Integer> numeros = new TreeList<>();
        while (numeros.size() < 6) {
            int num = (int) (Math.random() * 100);
            if (num <= 60 && num > 0) 
                numeros.add(num);
        }
        return numeros;
    }
}
