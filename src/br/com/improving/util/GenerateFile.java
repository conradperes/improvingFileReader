package br.com.improving.util;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenerateFile {
    private static final long FILE_SIZE = 1L * 1_024 * 1_024 * 1_024;

    private static final List<String> NAMES = Arrays.asList(
            "Monteiro Lobato",
            "Machado de Assis",
            "Paulo Coelho",
            "Zibia Gaspareto",
            "Clarice Lispector",
            "Jose de Alencar",
            "Drummond de Andrade",
            "Pedro Bandeira",
            "Cecilia Meirelles",
            "Mario Quintana",
            "Jorge Amado",
            "Mauricio de Souza",
            "Vinicius de Moraes",
            "Augusto Cury",
            "Erico Verissimo",
            "Ziraldo",
            "Manuel Bandeira",
            "Fernando Pessoa",
            "Ariano Suassuna",
            "Graciliano Ramos"
    );

    private static final String TYPES = "CCCCDDDDE";
    private static final String DIGIT_SEPARATOR = ".... @";
    private static final char COLUMN_SEPARATOR = '\t';

    public static void main(String[] args) throws FileNotFoundException, IOException {
        class CountingBufferedWriter extends BufferedWriter {
            long written;

            CountingBufferedWriter(Writer out) {
                super(out, 64 * 1_024);
            }

            @Override
            public void write(String s, int off, int len) throws IOException {
                super.write(s, off, len);
                written += len;
            }

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                super.write(cbuf, off, len);
                written += len;
            }

            @Override
            public void write(int c) throws IOException {
                super.write(c);
                written++;
            }
        }

        final File f = new File("clients.csv");
        final Random random = new Random(FILE_SIZE);
        final int namesSize = NAMES.size();
        final int typesSize = TYPES.length();
        final int digitSeparatorSize = DIGIT_SEPARATOR.length();
        boolean first = true;

        try (FileWriter fw = new FileWriter(f);
                CountingBufferedWriter bw = new CountingBufferedWriter(fw)) {
            while (bw.written <= FILE_SIZE) {
                if (!first) {
                    bw.write('\n');
                }

                first = false;

                bw.write(NAMES.get(random.nextInt(namesSize)));
                bw.write(COLUMN_SEPARATOR);
                bw.write(String.valueOf(random.nextInt(10_000)));
                bw.write(DIGIT_SEPARATOR.charAt(random.nextInt(digitSeparatorSize)));
                bw.write(String.valueOf(random.nextInt(100)));
                bw.write(COLUMN_SEPARATOR);
                bw.write(TYPES.charAt(random.nextInt(typesSize)));
                bw.write(COLUMN_SEPARATOR);
            }

            System.out.println("File generated at " + f.getAbsolutePath());
        }
    }
}

