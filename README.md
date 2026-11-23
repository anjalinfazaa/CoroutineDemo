# Hasil Latihan 9

## 1. Menambahkan Permintaan Ketiga
Menambahkan fungsi `getCourseCount()` dengan delay 1,5 detik dan menjalankannya menggunakan `async` ketiga.

**Hasil:** Waktu loading tetap sekitar 2 detik karena ketiga proses berjalan secara bersamaan.

## 2. Tombol Retry
Menambahkan tombol **Retry** yang hanya muncul ketika terjadi error. Tombol ini digunakan untuk mengulang proses loading data.

## 3. Menangkap Terlalu Banyak Exception
Mengganti `catch (IOException)` menjadi `catch (Exception)`.

**Hasil:** Ketika proses loading dibatalkan, pembatalan bisa ikut tertangkap sebagai error karena `CancellationException` merupakan turunan dari `Exception`.

## 4. Di Mana Macetnya?
Melakukan percobaan menggunakan `Thread.sleep(2000L)`.

- **Menggunakan Dispatchers.IO:** Layar tidak macet karena proses berjalan di background thread.
- **Menghapus withContext:** Layar menjadi macet karena proses berjalan di Main Thread.

## 5. Pertanyaan Konsep

**a. Perbedaan launch dan async**
`launch` menjalankan coroutine tanpa mengembalikan hasil, sedangkan `async` menghasilkan nilai yang bisa diambil menggunakan `await()`.

**b. Kenapa GlobalScope dihindari?**
Karena coroutine tidak terikat pada siklus hidup komponen sehingga bisa tetap berjalan meskipun halaman sudah ditutup.

**c. Apa yang terjadi jika getTasks() gagal?**
`getAnnouncement()` ikut dibatalkan karena keduanya berjalan di dalam `coroutineScope`.

## 6. Tantangan: Sort
Menambahkan tombol **Sort** untuk mengurutkan daftar tugas berdasarkan judul secara alfabetis dari A sampai Z. Proses pengurutan menggunakan `Dispatchers.Default`.

**Hasil pengurutan:**
1. Prepare quiz
2. Read chapter 5
3. Write lab report

## Fitur Tambahan
- Menambahkan tugas baru.
- Menghapus seluruh daftar tugas.
- Tampilan aplikasi dengan tema **sage green**.
- Menampilkan jumlah mata kuliah dan pengumuman.
