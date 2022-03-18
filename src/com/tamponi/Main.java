package com.tamponi;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        System.out.println("Alcune disponibilità a Monza:");
        int[] idFarmacie = new int[]{7728, 8097, 7787};
        String[] nomiFarmacie = new String[]{"Via Stelvio 2", "Via Rota 31/A", "Via Ramazzotti 36"};
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("risultati.txt");
        } catch (IOException e) {
            System.err.println("Errore nell'apertura del file.");
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            String a = "https://agenda.farmacomspa.it/front/responsive/showDispoRisorsa.jsp?servizio=" + idFarmacie[i];
            URLConnection connection = null;
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                connection = new URL(a).openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                connection.connect();
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String page = sb.toString();
            int index = page.indexOf("HAI ESIGENZE PARTICOLARI?");
            page = page.substring(index + 1188, index + 1220) + "\n";
            index = page.indexOf("<");
            page = nomiFarmacie[i] + ": " + page.substring(0, index) + "\n";
            System.out.println(page);
            if(myWriter != null){
                try {
                    myWriter.write(page);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if(myWriter!=null) myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}