package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Datas {

    private static final String PADRAO_TELA = "dd/MM/yyyy";
    private static final String PADRAO_BANCO = "yyyy-MM-dd";

    public static Date converter(String texto) throws ParseException {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat formato = new SimpleDateFormat(PADRAO_TELA);
        formato.setLenient(false); // sem isso uma data como 31/13/2026 seria aceita e convertida
        return formato.parse(texto.trim());
    }

    public static String formatar(Date data) {
        if (data == null) {
            return "";
        }
        return new SimpleDateFormat(PADRAO_TELA).format(data);
    }

    public static String paraBanco(Date data) {
        if (data == null) {
            return null;
        }
        return new SimpleDateFormat(PADRAO_BANCO).format(data);
    }

    public static Date doBanco(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat(PADRAO_BANCO).parse(texto.trim());
        } catch (ParseException erro) {
            return null;
        }
    }
}
