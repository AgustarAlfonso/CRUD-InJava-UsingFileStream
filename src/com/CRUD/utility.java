package com.CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class utility {
    public static void clearScreen()
    {
        try {
            if (System.getProperty("os.name").contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.println("\033\143");
            }
        }
        catch (Exception e)
        {
            System.err.println("Tidak bisa clear screen!");
        }
    }

    public static boolean konfirmasi(String message) {
        Scanner userInput = new Scanner(System.in);
        String inputan;

        do {
            System.out.println(message);
            inputan = userInput.nextLine();

            if (inputan.equalsIgnoreCase("y")) {
                return true;
            } else if (inputan.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.err.println("Pilihan anda salah! Masukkan y/n.");
            }
        } while (true);
    }

    protected static boolean cekfile(String[] keyword, boolean tampilkan) throws IOException
    {
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean ada = false;

        if (tampilkan)
        {
            System.out.println("\n| NIM         | Nama Mahasiswa              | Jenis Kelamin | Jurusan            | Tahun Masuk | IPK  |");
            System.out.println("---------------------------------------------------------------------------------------------------------");
        }

        while (data!=null){
            ada = true;

            for (String keywords:keyword) {
                ada = ada && data.toLowerCase().contains(keywords.toLowerCase());
            }

            if (ada) {
                if (tampilkan) {
                    StringTokenizer stringTokenizer = new StringTokenizer(data, ",");

                    System.out.printf("| %-11s | %-25s   | %-13s | %-18s | %-11s | | %-4s |%n",
                            stringTokenizer.nextToken(),
                            stringTokenizer.nextToken(),
                            stringTokenizer.nextToken(),
                            stringTokenizer.nextToken(),
                            stringTokenizer.nextToken(),
                            stringTokenizer.nextToken() );
                } else {
                    break;
                }
            }

            data = bufferInput.readLine();
        }

        return ada;
    }


    protected static String generatePrimaryKey(String a, String b, String c, String d)
    {

        String kodeUniversitas = "122";

        return kodeUniversitas+ a + b + c + d;
    }

    protected static String getKodeFakultas(String a)
    {
        return switch (a.toLowerCase()) {
            case "teknik informatika" -> "01";
            case "hukum" -> "02";
            case "teknik peternakan" -> "03";
            case "ilmu komunikasi" -> "04";
            case "sistem informasi" -> "05";
            default -> "";
        };
    }

    protected static String getKodeMasuk(String kodeFakultas, String kodeTahun) throws IOException
    {
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        int maksentry = 0;

        while (data!=null)
        {
            String[] bagian = data.split(",");
            if (bagian.length >= 6)
            {
                String primaryKey = bagian[0];
                String primaryKeyFakultas = primaryKey.substring(5,7);
                String primaryKeyTahun = primaryKey.substring(7,9);

                if (primaryKeyFakultas.equals(kodeFakultas) && primaryKeyTahun.equals(kodeTahun))
                {
                    String kodeMasukTerbaru = primaryKey.substring(9, 11);
                    try {
                        int kodemasuk = Integer.parseInt(kodeMasukTerbaru);
                        if (kodemasuk > maksentry)
                        {
                            maksentry = kodemasuk;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(e);

                    }
                }
            }
            data = bufferInput.readLine();
        }
        bufferInput.close();
        fileInput.close();

        String kode = String.format("%02d", maksentry+1);
        return kode;
    }

    protected static String ambilIpk() throws IOException
    {
        Scanner userInput = new Scanner(System.in);
        String ipkInput;
        while (true)
        {
            System.out.println("Masukkan IPK (0.00 - 4.00): ");
            ipkInput = userInput.nextLine();

            if (validasiIpk(ipkInput))
            {
                return ipkInput;
            }
            else {
                System.out.println("Inputan anda tidak valid! Format menerima (0.00 - 4.00)");
            }

        }
    }

    protected static boolean validasiIpk(String ipk)
    {
        try {
            float nilaiIpk = Float.parseFloat(ipk);
            return nilaiIpk >= 0.00 && nilaiIpk <= 4.00;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    protected static String ambilJK() throws IOException
    {
        boolean kondisi = false;
        Scanner userInput = new Scanner(System.in);
        String  jkInput = null;
        String JK = null;

        while (!kondisi)
        {
            System.out.println("Masukkan Jenis kelamin mahasiswa: (lk/pr) ");
            jkInput = userInput.nextLine();

            if (jkInput.equalsIgnoreCase("lk"))
            {
                JK = "Laki-laki";
                break;
            }
            else if (jkInput.equalsIgnoreCase("pr"))
            {
                JK = "Perempuan";
                break;
            }
            else {
                System.out.println("Input anda tidak valid! Forman menerima (lk/pr)");
                kondisi = false;
            }
        }
        return JK;
    }

    protected static String ambilJurusan() throws IOException
    {
        boolean kondisi = false;
        Scanner userInput = new Scanner(System.in);
        String jurusanInput = null;

        System.out.println("Masukkan jurusan mahasiswa: (1-5)");
        System.out.println("List jurusan teknik informatika ");
        System.out.println("1. Teknik informatika");
        System.out.println("2. Hukum");
        System.out.println("3. Teknik peternakan");
        System.out.println("4. Ilmu komunikasi");
        System.out.println("5. Sistem informasi");
        jurusanInput = userInput.nextLine();

        while (!kondisi)
        {
            switch (jurusanInput)
            {
                case "1":
                    jurusanInput = "Teknik informatika";
                    break;
                case "2":
                    jurusanInput = "Hukum";
                    break;
                case "3":
                    jurusanInput = "Teknik peternakan";
                    break;
                case "4":
                    jurusanInput = "Ilmu komunikasi";
                    break;
                case "5":
                    jurusanInput = "Sistem informasi";
                    break;
                default:
                    System.out.println("Inputan anda tidak valid! Format menerima (1-5)");
                    System.out.println("Masukkan jurusan mahasiswa: (1-5)");
                    jurusanInput = userInput.nextLine();
            }
            kondisi = true;
        }

        return jurusanInput;
    }

    protected static boolean validasiTahun(String tahun)
    {
        try {
            int nilaiTahun = Integer.parseInt(tahun);
            if (tahun.length() != 4)
            {
                System.out.println("Tahun anda tidak valid!");
                return false;
            }
            Year.parse(String.valueOf(nilaiTahun));
            return true;
        }
        catch (NumberFormatException | DateTimeException e)
        {
            System.out.println("Tahun anda tidak valid!");
            return false;
        }
    }

    protected static String[] ambilTahun() throws IOException
    {
        Scanner userInput = new Scanner(System.in);
        String tahunInput;
        while (true)
        {
            System.out.println("Masukkan tahun masuk mahasiswa: ");
            tahunInput = userInput.nextLine();
            if(validasiTahun(tahunInput))
            {
                String duadigit = tahunInput.substring(tahunInput.length() - 2);
                return new String[] {tahunInput, duadigit};
            }
            else {
                System.out.println("Tahun tidak valid. Harap masukkan tahun dengan format 4 digit (e.g., 2021).");
            }
        }
    }
}
