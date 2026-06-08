import java.util.Scanner; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Mini Project 1: Sistem Point of Sale (PoS)
 * Bisnis: Lokale Select - Pontianak
 * Kelompok 3:
 * - Rafli Gustiansyah (D1041241015) 
 * - Bintang Andhara Putra (D1041241051) 
 * - Gwenna Jasmine Farani (D1041241079)      
 *
 * Final Progress :  Implementasi Lengkap (4 Pilar OOP, Exception Handling, Varargs, Array 2D, Inner Class, Fitur Login)
 */

// [Materi: 4 Pilar OOP - Abstraction] (Class abstract yang tidak bisa diinstansiasi langsung)
abstract class EntitasPengguna {
    // [Materi: 4 Pilar OOP - Encapsulation] (Menyembunyikan data dengan access modifier protected/private)
    protected String id;
    protected String nama;

    public EntitasPengguna(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getNama() { return nama; }
    
    // [Materi: 4 Pilar OOP - Polymorphism] (Method abstract yang akan di-override di class turunan)
    public abstract void tampilkanInfo();
}

// 1. CLASS PRODUK
// [Materi: Class dan Object] (Pembuatan blueprint/Class)
class Produk {
    // [Materi: Naming Convention yang benar] (Menggunakan camelCase untuk nama variabel)
    private String kodeProduk;
    private String namaProduk;
    private double harga;
    
    // [Materi: Constructor, this, inisialisasi object]
    public Produk(String kodeProduk, String namaProduk, double harga){
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
    }

    public String getKodeProduk() { return kodeProduk; } 
    public String getNamaProduk() { return namaProduk; }
    public double getHarga() { return harga; }

    public void tampilProduk() {
        System.out.printf("%-6s - %-22s - Rp %,8.0f%n", kodeProduk, namaProduk, harga);
    }

    // [Materi: Static] (Method yang bisa dipanggil tanpa perlu membuat object class Produk)
    public static String getMetodePembayaran(int metode) {
        String namaMetode;
        // [Materi: Switch statement, default case]
        switch (metode) {
            case 1: 
                namaMetode = "Tunai"; 
                // [Materi: Break statement] (Menghentikan switch agar tidak mengeksekusi case bawahnya)
                break;
            case 2: 
            // [Materi: Fall through] (Case 2 sengaja tidak diberi break agar lanjut mengeksekusi case 99)
            case 99: 
                namaMetode = "QRIS"; 
                break;
            case 3: 
                namaMetode = "Kartu Debit"; 
                break;
            // [Materi: Default case] (Kondisi jika tidak ada case yang terpenuhi)
            default: 
                namaMetode = "Tidak Diketahui"; 
                break;
        }
        return namaMetode;
    }
}

// 2. CLASS MEMBER
// [Materi: 4 Pilar OOP - Inheritance] (Keyword extends menandakan pewarisan dari EntitasPengguna)
class Member extends EntitasPengguna {
    // [Materi: Tipe data primitif & non primitif] 
    // String adalah non-primitif (object), boolean & int adalah primitif
    private String nomorHp;
    private int poin;

    public Member(String idMember, String namaMember, String nomorHp) {
        super(idMember, namaMember);
        this.nomorHp = nomorHp;
        this.poin = 0; 
    }

    public String getNomorHp() { return nomorHp; } 
    
    public void tambahPoin(int poinBaru) { 
        // [Materi: Operator (Compound assignment operators)] (Sama dengan: this.poin = this.poin + poinBaru)
        this.poin += poinBaru; 
    }

    // [Materi: 4 Pilar OOP - Polymorphism] (Penerapan override method dari class induk)
    @Override
    public void tampilkanInfo() {
        System.out.println("Member: " + getNama() + " | Poin: " + this.poin);
    }
}

// 3. CLASS KASIR
class Kasir extends EntitasPengguna {
    public Kasir(String idKasir, String namaKasir) {
        super(idKasir, namaKasir);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Kasir bertugas: " + getNama());
    }
}

// 4. CLASS TRANSAKSI
class Transaksi {
    String noTransaksi, waktuTransaksi;
    double subTotal, nilaiPajak, nilaiDiskon, totalBayar;
    int metodeBayar;
    Kasir kasir;
    Member member;
    
    // [Materi: Array (1D)]
    Produk[] keranjang;
    int[] qty;
    int jumlahItem;

    // [Materi: Constructor] - Constructor ke-1
    public Transaksi(String noTransaksi, String waktuTransaksi, Kasir kasir, int metodeBayar) {
        this.noTransaksi = noTransaksi;
        this.waktuTransaksi = waktuTransaksi;
        this.kasir = kasir;
        this.metodeBayar = metodeBayar;
        this.keranjang = new Produk[10];
        this.qty = new int[10];
        this.jumlahItem = 0;
    }

    // [Materi: Constructor Overloading] - Constructor ke-2 dengan tambahan parameter Member
    public Transaksi(String noTransaksi, String waktuTransaksi, Kasir kasir, Member member, int metodeBayar) {
        this(noTransaksi, waktuTransaksi, kasir, metodeBayar);
        this.member = member;
    }

    public void tambahProduk(Produk p, int jumlah) {
        // [Materi: Operator (Relasional)] (Menggunakan < untuk membandingkan nilai)
        if (this.jumlahItem < this.keranjang.length) {
            this.keranjang[this.jumlahItem] = p;
            this.qty[this.jumlahItem] = jumlah;
            this.jumlahItem++;
        }
    }

    public void hitungSubtotal() {
        this.subTotal = 0;
        // [Materi: Loop (For)]
        for (int i = 0; i < this.jumlahItem; i++) {
            // [Materi: Loop (Continue)] (Melewati iterasi saat ini jika qty = 0 dan lanjut ke iterasi berikutnya)
            if (this.qty[i] == 0) continue;
            
            // [Materi: Casting (Widening)] (Tipe int pada qty[i] otomatis diperlebar menjadi double saat operasi perkalian)
            this.subTotal += this.keranjang[i].getHarga() * this.qty[i];
        }
    }

    public void hitungDiskon() {
        // [Materi: If else, if else-if, nested if] & [Materi: Operator (Logika)] (Menggunakan && / AND)
        if (this.member != null) {
            this.nilaiDiskon = this.subTotal * 0.10;
        } else {
            // Ini adalah contoh Nested If (If di dalam blok else) dan If Else-if
            if (this.subTotal > 100000) {
                this.nilaiDiskon = this.subTotal * 0.10;
            } else if (this.subTotal > 50000) {
                this.nilaiDiskon = this.subTotal * 0.05;
            } else {
                this.nilaiDiskon = 0;
            }
        }
    }

    public void hitungPajak() {
        // [Materi: Operator (Aritmatika dasar)] (Menggunakan * untuk perkalian)
        this.nilaiPajak = this.subTotal * 0.10;
    }

    public void hitungTotalBayar() {
        // [Materi: Operator (Aritmatika dasar)] (Menggunakan - dan + untuk pengurangan dan penjumlahan)
        this.totalBayar = this.subTotal - this.nilaiDiskon + this.nilaiPajak;
    }

    public int hitungPendapatanPoin() {
        // [Materi: Casting (Narrowing)] (Memaksa konversi tipe data double hasil pembagian menjadi int secara manual)
        return (int) (this.subTotal / 10000);
    }

    // [Materi: Inner Class] (Class yang dideklarasikan di dalam class lain)
    private class StrukPrinter {
        public void cetak() {
            System.out.println("\n=========== TRANSAKSI " + noTransaksi + " ==========");
            System.out.println("Tanggal: " + waktuTransaksi);
            System.out.println("Kasir  : " + kasir.getNama());
            if (member != null) {
                System.out.println("Member : " + member.getNama());
            }
            System.out.println("-------------------------------------");

            for (int i = 0; i < jumlahItem; i++) {
                if (qty[i] == 0) continue;
                double itemTotal = keranjang[i].getHarga() * qty[i];
                System.out.printf("%-20s %2dx Rp %,8.0f\n", keranjang[i].getNamaProduk(), qty[i], itemTotal);
            }

            System.out.println("-------------------------------------");
            System.out.printf("SUBTOTAL                Rp %,8.0f\n", subTotal);
            if (nilaiDiskon > 0) System.out.printf("DISKON                  Rp %,8.0f\n", nilaiDiskon);
            if (nilaiPajak > 0)  System.out.printf("PAJAK (10%%)             Rp %,8.0f\n", nilaiPajak);
            System.out.println("-------------------------------------");
            
            System.out.printf("TOTAL BAYAR             Rp %,8.0f\n", totalBayar);
            System.out.printf("PEMBAYARAN              %s\n", Produk.getMetodePembayaran(metodeBayar));
            
            if (member != null) {
                int dapatPoin = hitungPendapatanPoin();
                System.out.println("Tambahan Poin         : " + dapatPoin + " Poin");
                member.tambahPoin(dapatPoin);
            }
            System.out.println("=====================================\n");
        }
    }

    public void cetakStruk() {
        // Instansiasi dari Inner Class
        StrukPrinter printer = new StrukPrinter();
        printer.cetak();
    }
}

// 5. CLASS LAPORAN PENJUALAN
class LaporanPenjualan {
    Transaksi[] daftarTransaksi;
    double totalPendapatanShift;
    int jumlahTrx;

    public LaporanPenjualan(int kapasitas) {
        this.daftarTransaksi = new Transaksi[kapasitas];
        this.totalPendapatanShift = 0;
        this.jumlahTrx = 0;
    }

    // [Materi: Varargs] (Menerima parameter array Transaksi dengan jumlah dinamis menggunakan ...)
    public void tambahRiwayatBanyak(Transaksi... trxs) {
        // [Materi: Enhanced for loop] (Looping praktis untuk membaca setiap elemen dalam array/varargs)
        for (Transaksi t : trxs) {
            if (t != null && this.jumlahTrx < this.daftarTransaksi.length) {
                this.daftarTransaksi[this.jumlahTrx] = t;
                this.jumlahTrx++;
            }
        }
    }

    public void cetakLaporan() {
        this.totalPendapatanShift = 0;
        for (int i = 0; i < this.jumlahTrx; i++) {
            this.totalPendapatanShift += this.daftarTransaksi[i].totalBayar;
        }

        System.out.println("======== LAPORAN SHIFT ========");
        System.out.printf("Total Transaksi  : %d\n", this.jumlahTrx);
        System.out.printf("Total Pendapatan : Rp %,8.0f\n", this.totalPendapatanShift);
        System.out.println("===============================\n");
    }
}

// MAIN CLASS
public class Kelompok3_SistemPoS_Lokale_FinalProgress {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("==========================================");
        System.out.println("|       SISTEM POINT OF SALE (POS)       |");
        System.out.println("|       Coffeshop Lokale Pontianak       |");
        System.out.println("==========================================\n");

        // [Materi: Class dan Object] (Inisialisasi Object Array)
        Produk[] daftarProduk = new Produk[12];
        int jumlahProduk = 12; 

        daftarProduk[0] = new Produk("K001", "Kopi Susu Lokale", 19000);
        daftarProduk[1] = new Produk("K002", "Americano", 20000);
        daftarProduk[2] = new Produk("K003", "Aren Latte", 25000);
        daftarProduk[3] = new Produk("K004", "Teriyaki Beef", 38000);
        daftarProduk[4] = new Produk("K005", "Milkbun", 32000);
        daftarProduk[5] = new Produk("K006", "Nuttela Delight", 19000);
        daftarProduk[6] = new Produk("K007", "Butterscotch Latte", 28000);
        daftarProduk[7] = new Produk("K008", "Nasi Goreng Nusantara", 27000);
        daftarProduk[8] = new Produk("K009", "Cinnamon Roll", 33000);
        daftarProduk[9] = new Produk("K010", "Mie Kuah Lokale", 22100);
        daftarProduk[10] = new Produk("K011", "Yakiniku Beef", 39000);
        daftarProduk[11] = new Produk("K012", "Lokale Signature Macchiato", 30000);
        
        Kasir[] daftarKasir = {
            new Kasir("K01", "Bintang Andhara Putra"),
            new Kasir("K02", "Rafli Gustiansyah"),
            new Kasir("K03", "Gwenna Jasmine Farani")
        };

        Member[] daftarMember = {
            new Member("M01", "Muhammad Azizz Trinaldi", "0811223344"),
            new Member("M02", "Adjie Prasetya", "0899887766"),
            new Member("M03", "Amru Daffa Khoirullah", "0855443322")
        };

        LaporanPenjualan laporanHariIni = new LaporanPenjualan(20);
        
        boolean programJalan = true;
        int noTransaksi = 1;

        // Variabel untuk fitur Login
        boolean isLoggedIn = false;
        String roleAktif = "";

        // [Materi: Loop (While)] (Looping akan terus berjalan selama kondisi bernilai true)
        while (programJalan) {
            try {
                // =============== FITUR LOGIN ===============
                if (!isLoggedIn) {
                    System.out.println("=== LOGIN SISTEM LOKALE ===");
                    System.out.print("Username: ");
                    String inputUsername = input.nextLine();
                    System.out.print("Password: ");
                    String inputPassword = input.nextLine();

                    if (inputUsername.equals("kasir") && inputPassword.equals("123")) {
                        roleAktif = "Kasir";
                        isLoggedIn = true;
                        System.out.println("> Login Berhasil! Akses Kasir Diberikan.\n");
                    } else if (inputUsername.equals("admin") && inputPassword.equals("123")) {
                        roleAktif = "Admin";
                        isLoggedIn = true;
                        System.out.println("> Login Berhasil! Akses Admin Diberikan.\n");
                    } else {
                        System.out.println("> Username atau Password salah! Silakan coba lagi.\n");
                    }
                    continue; // Skip ke awal perulangan untuk nampilin menu jika berhasil login
                }
                // ===========================================

                System.out.println("=== MENU " + roleAktif.toUpperCase() + " LOKALE ===");
                
                // Menampilkan menu sesuai role
                if (roleAktif.equals("Kasir")) {
                    System.out.println("1. Lihat Daftar Menu");
                    System.out.println("2. Buat Transaksi Baru");
                } 
                if (roleAktif.equals("Admin")) {
                    System.out.println("3. Tutup Shift & Cetak Laporan");
                }
                System.out.println("4. Logout");
                
                System.out.print("Pilih menu: ");
                int pilihanMenu = input.nextInt();
                input.nextLine(); 

                // Proteksi Hak Akses (Mencegah Kasir akses menu 3, dan Admin akses menu 1 & 2)
                if (roleAktif.equals("Kasir") && pilihanMenu == 3) {
                    System.out.println("> [AKSES DITOLAK] Fitur ini khusus Admin.\n");
                    continue;
                }
                if (roleAktif.equals("Admin") && (pilihanMenu == 1 || pilihanMenu == 2)) {
                    System.out.println("> [AKSES DITOLAK] Fitur ini khusus Kasir.\n");
                    continue;
                }

                switch (pilihanMenu) {
                    case 1:
                        // [Materi: Array (2D / Jagged)] (Array 2D di mana panjang barisnya berbeda-beda)
                        String[][] kategoriMenu = {
                            {"Teriyaki Beef", "Nasi Goreng Nusantara", "Mie Kuah Lokale","Yakiniku Beef"},          
                            {"Milkbun", "Nuttela Delight", "Cinnamon Roll"},                      
                            {"Kopi Susu Lokale", "Americano", "Aren Latte", "Butterscotch Latte"} 
                        };
                
                        System.out.println("\n=========== KATEGORI MENU ===========");
                        String[] namaKategori = {"Main Dish", "Dessert", "Coffee"};
                        
                        // [Materi: Loop (Nested Loop)] (Loop for di dalam loop for lainnya)
                        for (int i = 0; i < kategoriMenu.length; i++) {
                            System.out.printf("%-10s: ", namaKategori[i]);
                            for (int j = 0; j < kategoriMenu[i].length; j++) {
                                System.out.print(kategoriMenu[i][j]); 
                                if (j < kategoriMenu[i].length - 1) {
                                    System.out.print(", ");
                                }
                            }
                            System.out.println(); 
                        }
                        System.out.println("=====================================\n");
                        
                        System.out.println("=========== DAFTAR HARGA ===========");
                        for (int i = 0; i < jumlahProduk; i++) {
                            daftarProduk[i].tampilProduk();
                        }
                        System.out.println("=====================================\n");
                        break;

                    case 2:
                        System.out.println("\n--- TRANSAKSI BARU ---");
                        
                        System.out.println("Daftar Kasir Bertugas:");
                        for (int i = 0; i < daftarKasir.length; i++) {
                            System.out.println((i + 1) + ". " + daftarKasir[i].getNama());
                        }
                        System.out.print("Pilih Kasir (1-" + daftarKasir.length + ") atau ketik '0' untuk BATAL: ");
                        int idKasir = input.nextInt();
                        input.nextLine(); 
                        
                        if (idKasir == 0) {
                            System.out.println("> Transaksi dibatalkan. Kembali ke menu utama.\n");
                            break;
                        }
                        if (idKasir < 1 || idKasir > daftarKasir.length) {
                            System.out.println("> Pilihan kasir tidak valid! Transaksi dibatalkan.\n");
                            break;
                        }

                        Kasir kasirAktif = daftarKasir[idKasir - 1];
                        DateTimeFormatter formatWaktu = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
                        String waktuSekarang = LocalDateTime.now().format(formatWaktu);

                        Transaksi trx = new Transaksi(String.valueOf(noTransaksi), waktuSekarang, kasirAktif, 1);

                        boolean tambahItem = true;
                        while (tambahItem) {
                            System.out.print("Masukkan Kode Menu (ex: K001) atau '0' untuk selesai: ");
                            String inputKode = input.nextLine();
                            
                            if (inputKode.equals("0")) {
                                // [Materi: Loop (Break)] (Keluar paksa dari perulangan while(tambahItem))
                                break;
                            } 
                            
                            int foundIndex = -1;
                            for (int i = 0; i < jumlahProduk; i++) {
                                if (daftarProduk[i].getKodeProduk().equalsIgnoreCase(inputKode)) {
                                    foundIndex = i;
                                    break;
                                }
                            }

                            if (foundIndex != -1) {
                                int qty;
                                // Perulangan validasi input quantity agar tidak bisa bernilai negatif / nol dan kebal huruf
                                while (true) {
                                    try {
                                        System.out.print("Masukkan Jumlah Qty: ");
                                        qty = input.nextInt();
                                        input.nextLine();
                                        if (qty > 0) {
                                            break; // Input valid, keluar dari loop validasi
                                        }
                                        System.out.println("input tidak valid! silahkan input qty > 0.");
                                    } catch (Exception e) {
                                        System.out.println("Input tidak valid! Harap masukkan angka.");
                                        input.nextLine(); // Membersihkan buffer error agar bisa ngulang input
                                    }
                                }
                                trx.tambahProduk(daftarProduk[foundIndex], qty);
                                System.out.println("> " + daftarProduk[foundIndex].getNamaProduk() + " ditambahkan!\n");
                            } else {
                                System.out.println("> Kode Menu tidak ditemukan!\n");
                            }
                        }

                        if (trx.jumlahItem == 0) {
                            System.out.println("> Tidak ada item yang ditambahkan. Transaksi dibatalkan.\n");
                            break;
                        }

                        // === BAGIAN VALIDASI MEMBER YANG BARU DIUPDATE ===
                        String tanyaMember = "";
                        while (true) {
                            System.out.print("\nApakah pelanggan memiliki member? (Y/N, atau '0' untuk BATAL): ");
                            tanyaMember = input.nextLine().toUpperCase();
                            
                            if (tanyaMember.equals("Y") || tanyaMember.equals("N") || tanyaMember.equals("0")) {
                                break; // Input valid, keluar dari loop
                            }
                            System.out.println("> Input tidak valid! Silakan masukkan Y, N, atau 0.");
                        }
                        
                        if (tanyaMember.equals("0")) {
                            System.out.println("> Transaksi dibatalkan.\n");
                            break;
                        } else if (tanyaMember.equals("Y")) {
                            System.out.print("Masukkan No HP Pelanggan: ");
                            String inputNoHp = input.nextLine();
                            
                            boolean ditemukan = false;
                            for (Member m : daftarMember) {
                                if (m.getNomorHp().equals(inputNoHp)) {
                                    trx.member = m;
                                    ditemukan = true;
                                    System.out.println("> Member Ditemukan: " + m.getNama());
                                    break;
                                }
                            }
                            if (!ditemukan) {
                                System.out.println("> Member tidak ditemukan. Melanjutkan sebagai pelanggan biasa.");
                            }
                        } else if (tanyaMember.equals("N")) {
                            System.out.println("> Melanjutkan sebagai pelanggan biasa.");
                        }
                        // =================================================

                        System.out.print("Pilih Metode Bayar (1.Tunai, 2.QRIS, 3.Debit): ");
                        trx.metodeBayar = input.nextInt();
                        input.nextLine();

                        // Proses Kalkulasi & Cetak Struk
                        trx.hitungSubtotal();
                        trx.hitungDiskon();
                        trx.hitungPajak();
                        trx.hitungTotalBayar();
                        trx.cetakStruk();

                        laporanHariIni.tambahRiwayatBanyak(trx);
                        noTransaksi++;
                        break;

                    case 3:
                        System.out.println("\nMenutup Shift Kasir...");
                        laporanHariIni.cetakLaporan();
                        programJalan = false;
                        break;

                    case 4:
                        System.out.println("\n> Berhasil Logout dari akun " + roleAktif + ".\n");
                        isLoggedIn = false;
                        roleAktif = "";
                        break;

                    default:
                        System.out.println("> Pilihan tidak valid!\n");
                        break;
                }
            } catch (Exception e) {
                System.out.println("\n[ERROR] Terjadi kesalahan input sistem. Mohon masukkan format data yang benar (Angka).");
                input.nextLine(); // Membersihkan buffer error agar loop tidak infinite
                System.out.println("=====================================\n");
            }
        }
        
        // [Materi: Loop (Do-while)] (Mengeksekusi blok kode minimal 1 kali sebelum mengecek kondisi)
        int closing = 1;
        do {
            System.out.println("   Terima kasih telah menggunakan sistem kasir Lokale!   ");
            closing++;
        } while (closing <= 1);

        input.close();
    }
}