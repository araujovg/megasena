package br.com.gva.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.gva.demo.services.PlanilhaService;
import br.com.gva.demo.utils.Utils;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

    @Autowired
    private PlanilhaService planilhaService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //planilhaService.imprimeTabela();
        planilhaService.imprimeListaSorteados();
        System.out.println("Quantidade de vezes que não houve vencedores com 6 dezenas: " + planilhaService.qtdConcursosSemVencedoresEmSeisDezenas());
        System.out.println("Menor valor pago na quadra: " + Utils.formataValor(planilhaService.verificaMenorValorPagoParaQuadra()));
        System.out.println("Menor valor pago na quina: " + Utils.formataValor(planilhaService.verificaMenorValorPagoParaQuina()));
        System.out.println("Menor valor pago na mega: " + Utils.formataValor(planilhaService.verificaMenorValorPagoParaMega()));
        System.out.println("Maior valor pago na quadra: " + Utils.formataValor(planilhaService.verificaMaiorValorPagoParaQuadra()));
        System.out.println("Maior valor pago na quina: " + Utils.formataValor(planilhaService.verificaMaiorValorPagoParaQuina()));
        System.out.println("Maior valor pago na mega: " + Utils.formataValor(planilhaService.verificaMaiorValorPagoParaMega()));
        System.out.println("QTD Ganhadores da Mega em todos os concursos: " + planilhaService.qtdGanhadoresMegaTodosConcursos());
        System.out.println("QTD Ganhadores da Quina em todos os concursos: " + planilhaService.qtdGanhadoresQuinaTodosConcursos());
        System.out.println("QTD Ganhadores da Quadra em todos os concursos: " + planilhaService.qtdGanhadoresQuadraTodosConcursos());
        planilhaService.novaAposta(1);
        planilhaService.novaAposta(2);
    }    
   
}
