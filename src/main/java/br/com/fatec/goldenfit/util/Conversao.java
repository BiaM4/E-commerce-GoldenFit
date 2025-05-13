package br.com.fatec.goldenfit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Conversao {
    public static Integer parseStringToInt (String parameter) {
        int number=0;
        if(parameter!=""){
            number = Integer.parseInt(parameter);
        }
        return number;
    }

    //parse que devolve um Integer � valueOf

    public static Double parseStringToDouble (String parameter) {
        Double number= 0.0;
        if(parameter!=""){
            number = Double.parseDouble(parameter);
        }
        return number;
    }

    public static Date parseStringToDate (String parameter) throws ParseException {
        Date data = null;
        System.out.println(parameter);
        if(parameter!=""){
            SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
            data = sdf.parse(parameter);
        }
        return data;
    }

    public static Date parseStringToDate (String parameter, String formatoParametro) throws ParseException {
        Date data = null;
        if(parameter != null && !parameter.isEmpty()
                && formatoParametro != null && !formatoParametro.isEmpty()){
            SimpleDateFormat sdf = new SimpleDateFormat (formatoParametro);
            data = sdf.parse(parameter);
        }
        return data;
    }

    public static String parseDateToString (Date parameter, String formatoParametro) throws ParseException {
        String data = null;
        if(parameter != null && formatoParametro != null && !formatoParametro.isEmpty()){
            System.out.println(parameter);
            SimpleDateFormat sdf = new SimpleDateFormat (formatoParametro);
            data = sdf.format(parameter);
            System.out.println(data);
        }
        return data;
    }

    public static Boolean parseStringToBoolean (String parameter) {
        Boolean b= true;
        if(parameter!=""){
            b = Boolean.parseBoolean(parameter);
        }
        return b;
    }

    public static Integer parseDoubleToInteger (Double parameter) {
        Integer i = null;
        if(parameter != null){
            i = parameter.intValue();
        }
        return i;
    }
}
