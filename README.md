# Sistem Informasi Transaksi Kasir Lokale
Untuk Memenuhi Tugas Ujian Akhir Semester Pemrograman Berorientasi Objek B<br>
Program Studi Informatika – Fakultas Teknik – Universitas Tanjungpura<br>
## Anggota Kelompok 3 :

Rafli Gustiansyah (D1041241015)<br>
Bintang Andhara Putra (D1041241051)<br>
Gwenna Jasmine Farani (D1041241079)<br>
# Dokumentasi Sistem Informasi Transaksi Lokale

Program ini merupakan sistem kasir berbasis *console* yang dibangun menggunakan bahasa pemrograman Java dengan pendekatan *Object-Oriented Programming* (OOP). Sistem ini dirancang untuk mengelola pesanan, menghitung transaksi (diskon, pajak, poin), dan mencetak laporan akhir shift secara efisien dengan penanganan error yang optimal.

## Struktur Class

* **`Akun`:** Class enkapsulasi untuk menyimpan kredensial login (username & password) guna memastikan keamanan akses sistem.
* **`EntitasPengguna` (Abstract):** *Base class* untuk aktor (Kasir dan Member). Menggunakan prinsip *Abstraction*.
* **`Produk`:** Blueprint data menu. Menyimpan kode, nama, dan harga produk. Memiliki metode *static* untuk manajemen metode pembayaran.
* **`Member`:** Turunan dari `EntitasPengguna`. Mengelola data pelanggan dan sistem akumulasi poin.
* **`Kasir`:** Turunan dari `EntitasPengguna`. Berfungsi sebagai identitas petugas yang melakukan transaksi.
* **`Transaksi`:** Class utama untuk logika bisnis. Menangani input produk (dengan optimasi penggabungan item ganda), kalkulasi (subtotal, diskon, pajak, total), dan cetak struk menggunakan **Inner Class** (`StrukPrinter`).
* **`LaporanPenjualan`:** Mengelola riwayat transaksi dalam satu shift menggunakan **Varargs** untuk input data secara dinamis.

## Fitur Unggulan

* **Sistem Login Aman:** Username dan password terlindungi oleh enkapsulasi Class `Akun`, dengan input password yang tersembunyi (*masked*) saat diketik.
* **Validasi Input *Bulletproof*:** Menggunakan perulangan *try-catch* dan `Integer.parseInt` untuk memastikan program tidak *crash* atau kembali ke menu awal saat pengguna salah menginput karakter/huruf.
* **Optimasi Struk:** Logika penambahan produk secara otomatis menggabungkan *quantity* jika kode menu yang diinput sama, sehingga struk lebih ringkas dan hemat baris.
* **Role-Based Access:** Hak akses menu yang dibatasi; Kasir hanya dapat mengakses fungsi operasional transaksi, sementara Admin memiliki hak khusus untuk menutup *shift* dan mencetak laporan.

## Cara Menjalankan di VS Code

1. **Persiapan:** Pastikan *Extension Pack for Java* sudah terinstal di VS Code.
2. **Impor File:** Simpan kode dengan nama file: `Kelompok3_SistemPoS_Lokale_FinalProgress.java`.
3. **Compile:** Buka terminal di VS Code pada direktori folder tersebut dan ketik:
`javac Kelompok3_SistemPoS_Lokale_FinalProgress.java`
4. **Jalankan:** Ketik `java Kelompok3_SistemPoS_Lokale_FinalProgress` lalu tekan **Enter**.

## Cara Penggunaan Program

1. **Login:** Masukkan *username* dan *password* sesuai tabel kredensial di atas.
2. **Menu Kasir (Opsi 1-3):**
* **Lihat Menu:** Menampilkan kategori dan harga produk.
* **Buat Transaksi:** * Pilih ID Kasir.
* Masukkan kode menu (misal: `K001`) dan *quantity*. Masukkan `0` jika selesai menambah item.
* Konfirmasi status Member untuk mendapatkan diskon otomatis.
* Pilih metode pembayaran (1.Tunai, 2.QRIS, 3.Debit).


* **Logout:** Keluar dari sesi akun saat ini.


3. **Menu Admin (Opsi 1-2):**
* **Tutup Shift:** Mencetak ringkasan total pendapatan dan jumlah transaksi secara keseluruhan, kemudian program akan berhenti (*exit*).
* **Logout:** Keluar dari sesi akun admin.

## Data Akun Login

Berikut adalah password dan username yang dapat digunakan untuk mengakses sistem:

| Role | Username | Password | Hak Akses |
| --- | --- | --- | --- |
| **Kasir** | `kasir` | `123` | Lihat Menu, Buat Transaksi |
| **Admin** | `admin` | `123` | Tutup Shift & Cetak Laporan |