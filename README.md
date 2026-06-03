# Dokumentasi Sistem Informasi Transaksi Lokale 

Program ini merupakan sistem kasir berbasis *console* yang dibangun menggunakan bahasa pemrograman Java. Sistem ini dirancang untuk mengelola pesanan, menghitung transaksi (diskon, pajak, poin), dan mencetak laporan akhir shift.

## Struktur Class

* **`EntitasPengguna` (Abstract):** *Base class* untuk semua aktor (Kasir dan Member). Menggunakan prinsip *Abstraction* agar tidak bisa diinstansiasi langsung.
* **`Produk`:** Blueprint data menu. Menyimpan kode, nama, dan harga produk. Memiliki metode *static* untuk manajemen metode pembayaran.
* **`Member`:** Turunan dari `EntitasPengguna`. Mengelola data pelanggan, status keaktifan, dan sistem akumulasi poin.
* **`Kasir`:** Turunan dari `EntitasPengguna`. Berfungsi sebagai identitas petugas yang melakukan transaksi.
* **`Transaksi`:** Class utama untuk logika bisnis. Menangani input produk, kalkulasi (subtotal, diskon, pajak, total), dan cetak struk menggunakan **Inner Class** (`StrukPrinter`).
* **`LaporanPenjualan`:** Mengelola riwayat transaksi dalam satu shift menggunakan **Varargs** untuk memudahkan input data secara dinamis.

## Cara Menjalankan di VS Code

1. **Persiapan:** Pastikan Extension Pack for Java sudah terinstal di VS Code.
2. **Impor File:** Simpan kode di atas dengan nama file yang sama dengan nama class publik: `Kelompok3_SistemPoS_Lokale_FinalProgress.java`.
3. **Compile:** Buka terminal di VS Code pada folder tersebut dan ketik `javac Kelompok3_SistemPoS_Lokale_FinalProgress.java`.
4. **Jalankan:** Ketik `java Kelompok3_SistemPoS_Lokale_FinalProgress` lalu tekan **Enter**.

## Cara Penggunaan Program

1. **Lihat Menu (Input 1):** Pilih opsi ini untuk melihat daftar kategori produk serta daftar harga lengkap dari menu Lokale Select.
2. **Buat Transaksi Baru (Input 2):**
* Pilih ID Kasir yang bertugas.
* Masukkan kode menu (misal: `K001`) dan jumlah (*quantity*) yang dipesan. Masukkan `0` jika selesai menambah item.
* Jika pelanggan memiliki member, konfirmasi dengan `Y` dan masukkan nomor HP yang terdaftar untuk mendapatkan diskon member.
* Pilih metode pembayaran (Tunai/QRIS/Debit). Struk akan otomatis tercetak.


3. **Tutup Shift (Input 3):** Pilih ini untuk mengakhiri program sekaligus mencetak ringkasan total pendapatan dan jumlah transaksi selama shift berlangsung.

