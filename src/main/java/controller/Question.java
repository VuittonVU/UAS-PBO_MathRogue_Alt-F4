package controller;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.*;

public class Question {
    private String questionText;
    private int correctAnswer;

    public Question(String questionText, int correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return questionText;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }

    public static Question fromTemplate(String template, int id) {
        Random rand = new Random();
        String question = "";
        int answer = 0;

        switch (id) {
            case 1: { //Seorang pedagang buah memiliki  {a} keranjang buah jeruk, 
                //dan setiap keranjang berisi {y} buah jeruk. Kemudian, 
                //ia berhasil menjual {z} buah jeruk. 
                //Berapakah sisa buah  jeruk yang dimiliki pedagang tersebut sekarang?
                int a = rand.nextInt(10) + 1;
                int y = rand.nextInt(10) + 1;
                int z = rand.nextInt(10) + 1;
                question = template.replace("{a}", Integer.toString(a)).replace("{y}", Integer.toString(y)).replace("{z}", Integer.toString(z));
                answer = (a * y) - z;
                break;
            }
            case 2: { // Ibu ingin membuat kue kering untuk Lebaran. 
                //Satu resep kue kering membutuhkan {x} gram tepung terigu dan {y} 
                //gram gula pasir. Jika Ibu ingin membuat {z} resep kue kering, 
                //berapa total gram tepung terigu dan gula pasir yang dibutuhkan Ibu?
                int x = rand.nextInt(5) + 1;
                int y = rand.nextInt(5) + 1;
                int z = rand.nextInt(10) + 1;
                question = template.replace("{x}", Integer.toString(x)).replace("{y}", Integer.toString(y)).replace("{z}", Integer.toString(z));
                answer = z * (x + y);
                break;
            }
            case 3: { // Tiket Kebun Binatang
                int x = rand.nextInt(51) * 2000;
                int y = rand.nextInt(31) * 1000;
                int a = rand.nextInt(5) + 1;
                int b = rand.nextInt(5) + 1;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y))
                                   .replace("{a}", Integer.toString(a))
                                   .replace("{b}", Integer.toString(b));
                answer = a * x + b * y;
                break;
            }
            case 4: { // Permen dari Guru
                int x = rand.nextInt(100) + 31;
                int y = rand.nextInt(30) + 1;
                question = template.replace("{x}", Integer.toString(x)).replace("{y}", Integer.toString(y));
                answer = x % y;
                break;
            }
            case 5: { // Galon Air
                int x = rand.nextInt(10) + 5;   // Kapasitas galon
                int y = rand.nextInt(4) + 1;    // Kapasitas botol
                int z = rand.nextInt(10) + 1;   // Jumlah botol
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y))
                                   .replace("{z}", Integer.toString(z));
                answer = y * z; 
                break;
            }
            case 6: { // Pengamat Menara
                int a = 45;   // Sudut elevasi
                int d = rand.nextInt(90) + 10;  // Jarak ke menara
                int h = rand.nextInt(2) + 1;    // Tinggi mata pengamat
                question = template.replace("{a}", Integer.toString(a))
                                   .replace("{d}", Integer.toString(d))
                                   .replace("{h}", Integer.toString(h));
                answer = (int)(Math.tan(Math.toRadians(a)) * d) + h; // Harus dihitung dengan trigonometri: tan(a) * d + h
                break;
            }
            case 7: { //Sebuah taman berbentuk gabungan dari persegi panjang berukuran 
                //{p} m x {l}
                //Hitung luas total taman tersebut.
                int p = rand.nextInt(10) + 1;
                int l = rand.nextInt(10) + 1;
                question = template.replace("{p}", Integer.toString(p)).replace("{l}", Integer.toString(l));
                answer = p * l;
                break;
            }
            case 8: { //Sebuah tangki air berbentuk balok dengan panjang {p} cm, lebar {l} cm
                //dan tinggi {t} cm akan diisi air sampai penuh, 
                //berapa liter kah air yang dapat diisi ke tangki tersebut
                int p = 2 * rand.nextInt(5) + 1;
                int l = 2 * (rand.nextInt(5) + 1);
                int t = 2 * (rand.nextInt(5) + 1);
                question = template.replace("{p}", Integer.toString(p)).replace("{l}", Integer.toString(l)).replace("{t}", Integer.toString(t));
                answer = p * l * t;
                break;
            }
            case 9: { // Umur Ani dan Budi
                int x = rand.nextInt(20) + 20;  // Total umur
                int a = rand.nextInt(10) + 5;    // Selisih umur dulu
                question = template.replace("{x}", Integer.toString(x)).replace("{a}", Integer.toString(a));
                answer = a; // Dihitung dengan sistem persamaan linear
                break;
            }
            case 10: { // Olimpiade Sains
                int n = rand.nextInt(6) + 6; // peserta (5-10)
                int k = rand.nextInt(n - 6) + 3; // kursi (3 sampai n)
                question = template.replace("{n}", Integer.toString(n)).replace("{k}", Integer.toString(k));
                long total = 1;
                for (int i = 0; i < k; i++) {
                    total *= (n - i);
                }

                // forbidden jika ABC duduk berdampingan
                long forbidden = 0;
                if (k >= 3) {
                    long permRest = 1;
                    for (int i = 0; i < k - 3; i++) {
                        permRest *= (n - 3 - i);
                    }
                    forbidden = (k - 2) * permRest * 6; // 6 = 3! permutasi ABC
                }

                answer = (int) (total - forbidden); // Butuh aturan kombinasi dan pengecualian
                break;
            }
            case 11: { // Hitung: a x b + c
                int a = rand.nextInt(10) + 1;
                int b = rand.nextInt(3) + 1;
                int c = rand.nextInt(10) + 1;
                question = template.replace("{a}", Integer.toString(a))
                                   .replace("{b}", Integer.toString(b))
                                   .replace("{c}", Integer.toString(c));
                answer = a * b + c;
                break;
            }
            case 12: { // Hitung: (x + y) / z
                int x, y, z;
                do {
                    x = rand.nextInt(20)+12;           
                    y = rand.nextInt(20) * 3;       
                    z = (rand.nextInt(5) + 1) * 2;  
                } while ((x + y) % z != 0);         
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y))
                                   .replace("{z}", Integer.toString(z));
                answer = (x + y) / z;
                break;
            }
            case 13: { // Jika sebuah bilangan dikalikan {m} lalu dikurangi {n}, hasilnya adalah {r}.
                    // Berapakah bilangan awal tersebut?

                int m, n, x, r;

                do {
                    m = rand.nextInt(5) + 1;          
                    n = rand.nextInt(20) + 1;         
                    x = rand.nextInt(10) + 1;         
                    r = m * x - n;                    
                } while ((r + n) % m != 0);           

                question = template.replace("{m}", Integer.toString(m))
                                   .replace("{n}", Integer.toString(n))
                                   .replace("{r}", Integer.toString(r));

                answer = x;
                break;
            }
            case 14: { // x^y
                int x = rand.nextInt(10);
                int y = rand.nextInt(3) + 1;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y));
                answer = (int)Math.pow(x, y);
                break;
            }
            case 15: { // Dibagi d hasilnya r
                int r = rand.nextInt(10) + 1;
                int d = rand.nextInt(10) + 1;
                int bil = r * d;
                question = template.replace("{d}", Integer.toString(d))
                                   .replace("{r}", Integer.toString(r));
                answer = bil;
                break;
            }

            case 16: { // Fungsi linier f(x) = mx + n
                int x = rand.nextInt(10) + 1;
                int m = rand.nextInt(5) + 1;
                int n = rand.nextInt(10);
                int fx = m * x + n;
                question = template.replace("{m}", Integer.toString(m))
                                   .replace("{n}", Integer.toString(n))
                                   .replace("{k}", Integer.toString(fx));
                answer = x;
                break;
            }
            case 17: { // Memilih 2 bola berbeda warna
                int x = rand.nextInt(10) + 2;
                int y = rand.nextInt(10) + 2;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y));
                answer = x * y;
                break;
            }
            case 18: { // Sudut antar jarum jam dan menit
                int h, m;
                double sudut;

                do {
                    h = rand.nextInt(12);        
                    m = rand.nextInt(30) * 2;    
                    sudut = Math.abs(30 * h - 5.5 * m);
                } while (sudut != Math.floor(sudut));

                question = template.replace("{h}", String.format("%02d", h))
                                   .replace("{m}", String.format("%02d", m));

                answer = (int)Math.min(sudut, 360 - sudut); // sudut terkecil
                break;
            }
            case 19: { // Urutkan angka dari terbesar ke terkecil                
                int s = rand.nextInt(5) + 1; 
                int factorP = rand.nextInt(5) + 2; 
                int factorL = rand.nextInt(5) + 2; 

                int p = s * factorP; // panjang lahan, kelipatan s
                int l = s * factorL; // lebar lahan, kelipatan s

                question = template.replace("{p}", Integer.toString(p))
                                   .replace("{l}", Integer.toString(l))
                                   .replace("{s}", Integer.toString(s));

                answer = (p * l) / (s * s); // jumlah petak, dijamin bulat
                break;
            }
            case 20: { // Kepala dan kaki ayam-kambing
                int ayam = rand.nextInt(10) + 1;
                int kambing = rand.nextInt(5) + 1;
                int x = ayam + kambing;        // kepala
                int y = ayam * 2 + kambing * 4; // kaki
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y));
                answer = ayam + kambing;
                break;
            }
            case 21: { // Kelereng Rina
                int x = rand.nextInt(20) + 10;
                int y = rand.nextInt(10) + 1;
                int z = rand.nextInt(10) + 1;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y))
                                   .replace("{z}", Integer.toString(z));
                answer = x - y + z;
                break;
            }
            case 22: { // Tabungan Dodi
                int x = (rand.nextInt(9) + 2) * 1000;
                int y = rand.nextInt(30) + 1;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y));
                answer = x * y;
                break;
            }
            case 23: { // Jumlah kursi di aula
                int x = rand.nextInt(10) + 1;
                int y = rand.nextInt(11) + 1;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y));
                answer = x * y;
                break;
            }
            case 24: { // Jarak dalam z jam
                int y, v, x, z;

                do {
                    y = rand.nextInt(5) + 1;               
                    v = rand.nextInt(20) + 10;          
                    x = y * v;                         
                    z = rand.nextInt(5) + 1;                    
                } while ((v * z) % 1 != 0);             

                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y))
                                   .replace("{z}", Integer.toString(z));

                answer = v * z;
                break;                          
            }
            case 25: { // Bahan untuk kotak hadiah
                int x = rand.nextInt(3) + 1;
                int y = rand.nextInt(5) + 1;
                int z = rand.nextInt(10) + 1;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y))
                                   .replace("{z}", Integer.toString(z));
                answer = (x + y) * z;
                break;
            }
            case 26: { // Luas persegi panjang
                int x = rand.nextInt(10) + 1;
                int y = rand.nextInt(10) + 1;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y));
                answer = x * y;
                break;
            }
            case 27: { // Uang kembalian
                int x = rand.nextInt(5) + 1;
                int y = (rand.nextInt(100)+1) * 1000;
                int z = y * x + (rand.nextInt(100)+1)*1000; // pembayaran > total
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y))
                                   .replace("{z}", Integer.toString(z));
                answer = z - (x * y);
                break;
            }
            case 28: { // Potongan kain
                int y = rand.nextInt(9) + 2;
                int x = y * (rand.nextInt(5) + 1); // kelipatan y
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y));
                answer = x / y;
                break;
            }
            case 29: { // Total harga wortel
                int x = rand.nextInt(100) + 1;
                int y = (rand.nextInt(10) + 1) * 1000;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y));
                answer = x * y;
                break;
            }
            case 30: { // Total air ditampung ember
                int x = rand.nextInt(20) + 1;
                int y = rand.nextInt(10) + 1;
                question = template.replace("{x}", Integer.toString(x))
                                   .replace("{y}", Integer.toString(y));
                answer = x * y;
                break;
            }


        
            // Tambahkan lebih banyak case sesuai jumlah soal

            default: {
                question = "Soal tidak tersedia.";
                answer = 0;
                break;
            }
        }

        return new Question(question, answer);
    }

}