package br.com.gva.demo.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static String formataValor(Double valor) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String numero;
        numero = format.format(valor);
        return numero;
    }
}
