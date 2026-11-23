Hasil Pengerjaan Latihan 9
1. Menambahkan Permintaan Ketiga
Menambahkan fungsi getCourseCount() dengan delay 1,5 detik. Data dimuat menggunakan async ketiga.
Hasil: Waktu loading masih sekitar 2 detik karena semua proses berjalan secara bersamaan.
2. Tombol Retry
Menambahkan tombol Retry yang muncul ketika terjadi error. Tombol ini digunakan untuk mengulang proses loading data.
3. Menangkap Terlalu Banyak Exception
Mengganti catch (IOException) menjadi catch (Exception).
Hasil: Pembatalan coroutine dapat ikut tertangkap sebagai error karena CancellationException merupakan turunan dari Exception.
4. Di Mana Macetnya?
Melakukan percobaan menggunakan Thread.sleep(2000L).
Menggunakan Dispatchers.IO: layar tetap bisa digunakan karena proses berjalan di background thread.
Menghapus withContext: layar menjadi macet karena proses berjalan di Main Thread.
5. Pertanyaan Konsep
launch: Menjalankan coroutine tanpa mengembalikan hasil.
async: Menjalankan coroutine yang menghasilkan nilai dan diambil menggunakan await().
GlobalScope: Sebaiknya dihindari karena tidak terikat pada siklus hidup komponen.
coroutineScope: Jika getTasks() gagal, coroutine lain dalam scope yang sama, termasuk getAnnouncement(), ikut dibatalkan.
6. Tantangan – Sort
Menambahkan tombol Sort untuk mengurutkan daftar tugas berdasarkan judul secara alfabetis A–Z menggunakan Dispatchers.Default.
Fitur Tambahan
Menambahkan tugas baru.
Menghapus seluruh daftar tugas.
Tampilan aplikasi dengan tema warna sage green.
Menampilkan jumlah mata kuliah dan pengumuman.
