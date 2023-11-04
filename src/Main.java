import java.io.*;
import java.util.Scanner;
import com.CRUD.operasi;
import com.CRUD.utility;


public class Main {
    public static void main(String[] args) throws IOException {

        Scanner userInput = new Scanner(System.in);
        String  inputan;
        boolean kondisi = true;

        while (kondisi)
        {
            utility.clearScreen();
            System.out.println("Database mahasiswa");
            System.out.println("1.\tLihat daftar mahasiswa");
            System.out.println("2.\tCari mahasiswa");
            System.out.println("3.\tTambah mahasiswa");
            System.out.println("4.\tUbah mahasiswa");
            System.out.println("5.\tHapus mahasiswa");

            System.out.println("\nPilihan anda: ");
            inputan = userInput.nextLine();

            switch (inputan)
            {
                case "1":
                    System.out.println("===============");
                    System.out.println("LIST MAHASISWA");
                    System.out.println("===============");
                    operasi.tampilkanNama();
                    break;
                case "2":
                    System.out.println("===============");
                    System.out.println("CARI MAHASISWA");
                    System.out.println("===============");
                    operasi.cariMahasiswa();
                    break;
                case "3":
                    System.out.println("===============");
                    System.out.println("TAMBAH MAHASISWA");
                    System.out.println("===============");
                    operasi.tambahData();
                    operasi.tampilkanNama();
                    break;
                case "4":
                    System.out.println("===============");
                    System.out.println("UBAH MAHASISWA");
                    System.out.println("===============");
                    operasi.updateData();
                    operasi.tampilkanNama();
                    break;
                case "5":
                    System.out.println("===============");
                    System.out.println("HAPUS MAHASISWA");
                    System.out.println("===============");
                    operasi.hapusData();
                    operasi.tampilkanNama();
                    break;
                default:
                    System.err.println("\nInput anda salah!!!");
            }
            kondisi = utility.konfirmasi("\nApakah anda ingin melanjutkan? (y/n) : ");
            System.out.print("\n");
        }


    }


}
