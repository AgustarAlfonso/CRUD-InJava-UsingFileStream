package com.CRUD;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class operasi
{
    public static void tambahData() throws IOException {
        FileWriter fileOutput = new FileWriter("database.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        Scanner userInput = new Scanner(System.in);
        String nama, jenisKelamin, jurusan, ipk;
        String[] tahun;

        System.out.println("Masukkan Nama mahasiswa: ");
        nama = userInput.nextLine();
        jenisKelamin = utility.ambilJK();
        jurusan = utility.ambilJurusan();
        tahun = utility.ambilTahun();
        System.out.println("Masukkan Ipk: ");
        ipk = utility.ambilIpk();


        String kodeJK = jenisKelamin.equalsIgnoreCase("Laki-laki") ? "01" : "02";
        String kodeFakultas = utility.getKodeFakultas(jurusan);
        String kodeTahun = tahun[1];
        String kodeMasuk = utility.getKodeMasuk(kodeFakultas, tahun[1]);
        String primaryKey = utility.generatePrimaryKey(kodeJK,kodeFakultas,kodeTahun,kodeMasuk);
        String dataMahasiswa = String.format("%s,%s,%s,%s,%s,%s", primaryKey,nama,jenisKelamin,jurusan,tahun[0],ipk);
        String[] keywords = {nama+","+jenisKelamin+","+jurusan+","+tahun[0]+","+ipk};


        if(!utility.cekfile(keywords, false))
        {
            System.out.println("Data yang anda masukkan adalah: ");
            System.out.println("---------------------------------");
            System.out.println("Nim           = " + primaryKey);
            System.out.println("Nama          = " + nama);
            System.out.println("Jenis kelamin = " + jenisKelamin);
            System.out.println("Jurusan       = " + jurusan);
            System.out.println("Tahun         = " + tahun[0]);
            System.out.println("Ipk           = " + ipk);

            boolean konfirmasi = utility.konfirmasi("Apakah anda ingin menambah data tersebut? (y/n)");
            if (konfirmasi)
            {
                bufferOutput.write(dataMahasiswa);
                bufferOutput.newLine();
                bufferOutput.flush();
            }

        }
        else {
            System.out.println("Data mahasiswa sudah ada di database!");
            utility.cekfile(keywords,true);
        }

        bufferOutput.close();
        fileOutput.close();
    }

    public static void updateData() throws IOException{
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        System.out.println("List mahasiswa");
        tampilkanNama();
        boolean ditemukan = false;

        Scanner userInput = new Scanner(System.in);
        System.out.println("Masukkan NIM mahasiswa yang ingin diupdate: ");
        String nim = userInput.nextLine();

        String data = bufferInput.readLine();

        while (data != null)
        {
            StringTokenizer stringTokenizer = new StringTokenizer(data, ",");
            String primaryKey = stringTokenizer.nextToken();
            if (nim.equalsIgnoreCase(primaryKey))
            {
                System.out.println("Data yang ingin anda update adalah: ");
                System.out.println("---------------------------------");
                System.out.println("Nim           = " + primaryKey);
                System.out.println("Nama          = " + stringTokenizer.nextToken());
                System.out.println("Jenis kelamin = " + stringTokenizer.nextToken());
                System.out.println("Jurusan       = " + stringTokenizer.nextToken());
                String tahuns = stringTokenizer.nextToken();
                System.out.println("Tahun         = " + tahuns);
                String tahunduadigit = tahuns.substring(2);
                System.out.println("IPK           = " + stringTokenizer.nextToken());
                System.out.println("---------------------------------");
                ditemukan = true;

                String[] field = {"Nama", "Jenis kelamin", "Jurusan", "Tahun", "IPK"};
                String[] tempData = new String[5];

                stringTokenizer = new StringTokenizer(data, ",");
                String original = stringTokenizer.nextToken();
                for (int i=0; i<field.length; i++)
                {
                    boolean isUpdate = utility.konfirmasi("Apakah anda ingin mengupdate " + field[i] + "? (y/n)");
                    original = stringTokenizer.nextToken();
                    if (isUpdate)
                    {
                        userInput = new Scanner(System.in);
                        if(i == 0)
                        {
                            System.out.print("Masukkan " + field[i] + " baru: ");
                            tempData[i] = userInput.nextLine();
                        } else if (i == 1) {
                            tempData[i] = utility.ambilJK();
                        } else if (i == 2) {
                            tempData[i] = utility.ambilJurusan();
                        } else if (i == 3) {
                            String[] tahun = utility.ambilTahun();
                            tempData[i] = tahun[0];
                            tahunduadigit = tahun[1];
                        } else if (i == 4) {
                            tempData[i] = utility.ambilIpk();
                        }

                    }
                    else {
                        tempData[i] = original;
                    }

                }

                stringTokenizer = new StringTokenizer(data, ",");
                stringTokenizer.nextToken();
                System.out.println("Data baru anda adalah: ");
                System.out.println("---------------------------------");
                System.out.println("Nama          = " + stringTokenizer.nextToken() + " -> " + tempData[0]);
                System.out.println("Jenis kelamin = " + stringTokenizer.nextToken() + " -> " + tempData[1]);
                System.out.println("Jurusan       = " + stringTokenizer.nextToken() + " -> " + tempData[2]);
                System.out.println("Tahun         = " + stringTokenizer.nextToken() + " -> " + tempData[3]);
                System.out.println("IPK           = " + stringTokenizer.nextToken() + " -> " + tempData[4]);
                System.out.println("---------------------------------");

                boolean isUpdate = utility.konfirmasi("Apakah anda yakin ingin mengupdate data ini? (y/n)");
                if (isUpdate)
                {
                    boolean isExist = utility.cekfile(tempData, false);
                    if (isExist)
                    {
                        System.out.println("Data sudah ada di database");
                        bufferOutput.newLine();
                    }
                    else {
                        String kodeJK = tempData[1].equalsIgnoreCase("Laki-laki") ? "01" : "02";
                        String kodeFakultas = utility.getKodeFakultas(tempData[2]);
                        String kodeTahun = tahunduadigit;
                        String kodeMasuk = utility.getKodeMasuk(kodeFakultas, kodeTahun);
                        String pk = utility.generatePrimaryKey(kodeJK,kodeFakultas,kodeTahun,kodeMasuk);
                        String dataMahasiswa = String.format("%s,%s,%s,%s,%s,%s", pk,tempData[0],tempData[1],tempData[2],tempData[3],tempData[4]);
                        bufferOutput.write(dataMahasiswa);
                        System.out.println("Data berhasil di update!");
                    }

                }
                else {
                    bufferOutput.write(data);

                }
            }
            else {
                bufferOutput.write(data);

            }
            bufferOutput.newLine();
            data = bufferInput.readLine();

        }

        if (!ditemukan)
        {
            System.err.println("Data tidak ditemukan!");
        }

        bufferOutput.flush();
        bufferOutput.close();
        fileOutput.close();
        bufferInput.close();
        fileInput.close();

        System.gc();
        database.delete();
        tempDB.renameTo(database);


    }

    public static void hapusData() throws IOException{
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        System.out.println("List mahasiswa");
        tampilkanNama();

        Scanner userInput = new Scanner(System.in);
        System.out.println("Masukkan NIM mahasiswa yang ingin dihapus: ");
        String nim = userInput.nextLine();
        boolean ditemukan = false;

        String data = bufferInput.readLine();
        while (data != null)
        {
            boolean hapus = false;
            StringTokenizer stringTokenizer = new StringTokenizer(data, ",");
            String primaryKey = stringTokenizer.nextToken();
            if (nim.equalsIgnoreCase(primaryKey))
            {
                System.out.println("Data yang ingin anda hapus adalah: ");
                System.out.println("---------------------------------");
                System.out.println("Nim           = " + primaryKey);
                System.out.println("Nama          = " + stringTokenizer.nextToken());
                System.out.println("Jenis kelamin = " + stringTokenizer.nextToken());
                System.out.println("Jurusan       = " + stringTokenizer.nextToken());
                System.out.println("Tahun         = " + stringTokenizer.nextToken());
                System.out.println("Ipk           = " + stringTokenizer.nextToken());

                hapus = utility.konfirmasi("Apakah anda yakin ingin menghapus data ini?");
                ditemukan = true;
            }

            if (hapus)
            {
                System.out.println("Data berhasil dihapus!");
            }
            else {
                bufferOutput.write(data);
                bufferOutput.newLine();

            }

            data = bufferInput.readLine();
        }

        if (!ditemukan) {
            System.out.println("Data mahasiswa tidak ditemukan!");
        }

        bufferOutput.flush();
        bufferOutput.close();
        fileOutput.close();
        bufferInput.close();
        fileInput.close();

        System.gc();
        database.delete();
        tempDB.renameTo(database);

    }

    public static void cariMahasiswa() throws IOException
    {

        try {
            File file = new File("database.txt");
        }
        catch (Exception e)
        {
            System.err.println("Database tidak ditemukan!");
            tambahData();
            return;
        }

        Scanner userInput = new Scanner(System.in);
        System.out.println("Masukkan data yang ingin dicari: ");
        String data = userInput.nextLine();
        String[] keyword = data.split("\s+");

        utility.cekfile(keyword, true);


    }

    public static void tampilkanNama() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;
        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e)
        {
            System.err.println("Database tidak ditemukan");
            System.err.println("Masukkan data terlebih dahulu");
            tambahData();
            return;
        }
        String data = bufferInput.readLine();
        System.out.println("\n| NIM         | Nama Mahasiswa              | Jenis Kelamin  | Jurusan            | Tahun Masuk | IPK  |");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        while (data != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(data, ",");
            System.out.printf("| %-11s | %-25s   | %-13s | %-18s | %-11s  | %-4s |%n",
                    stringTokenizer.nextToken(),
                    stringTokenizer.nextToken(),
                    stringTokenizer.nextToken(),
                    stringTokenizer.nextToken(),
                    stringTokenizer.nextToken(),
                    stringTokenizer.nextToken() );
            data = bufferInput.readLine();
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
        bufferInput.close();
        fileInput.close();



    }
}
