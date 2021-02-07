package com.next.api.utils;

import java.math.BigInteger;

public class Utils {

    public static boolean validar(String cuenta) {
        boolean esValido = false;
        int i = 2;
        int caracterASCII = 0;
        int resto = 0;
        int dc = 0;
        String cadenaDc = "";
        BigInteger cuentaNumero = new BigInteger("0");
        BigInteger modo = new BigInteger("97");

        if (cuenta.length() == 24 && cuenta.substring(0, 1).toUpperCase().equals("E")
                && cuenta.substring(1, 2).toUpperCase().equals("S")) {

            do {
                caracterASCII = cuenta.codePointAt(i);
                esValido = (caracterASCII > 47 && caracterASCII < 58);
                i++;
            }
            while (i < cuenta.length() && esValido);


            if (esValido) {
                cuentaNumero = new BigInteger(cuenta.substring(4, 24) + "142800");
                resto = cuentaNumero.mod(modo).intValue();
                dc = 98 - resto;
                cadenaDc = String.valueOf(dc);
            }

            if (dc < 10) {
                cadenaDc = "0" + cadenaDc;
            }

            if (cuenta.substring(2, 4).equals(cadenaDc)) {
                esValido = true;
            } else {
                esValido = false;
            }
        }

        return esValido;
    }
}
