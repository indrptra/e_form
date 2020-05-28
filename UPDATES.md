# PlayStore Version History
* 17 Jun 2017, build 40 - `v1.4.59`
  * Fixing Bug api assign edit
  * implement bucket list
  * Fixing bug retake foto
  * Fixing bug data pasangan in assign edit
  * Fixing bug sales method
  * Fixing bug sync data dump
  * Implement auto logout
  * Didn't show loading dialog in home
  * Use progress bar while downloading data master in login and pengajuan baru
  * Replace progres dialog "please wait" when open draft pengajuan to spinner in field kota
  * Fixing bug Refresh Token
  * Add feedback assign edit
  * Prepare limit 3 times assign edit (add toast)
  * Fixing bug sync data assign edit after draft
  * Fixing bug download bar pengajuan baru and login
  * Fixing bug no credit card in assign edit and detail

//# Unpublished Version History (APKs sent to client)
* 13-November-2019, 1.33.36.008(151)
* fix bug - Perbaikan Kamera blur di OS 5 dan 6
* Add - Menambahkan kode refferal
* Add - Memindahkan crashlytics ke Firebase
* Add - Penambahan image compressor untuk menggantikan crop image
* Delete - Lepas crop image
* Delete - lepas penggunaan storage, kontak di mobile

//# Unpublished Version History (APKs sent to client)
* 19-September-2019, 1.33.36.005(149)
* KMB

* Fix Bug - [FE dan BE] Save as Draft KMB, Saat save as draft, tambahkan mandatory pada status pernikahan, jenis kelamin, pendidikan, status rumah, agama (Done)
* Fix Bug - [FE] Tidak ada alert merah ketika field rekomendasi belum di pilih (Done)
* Fix Bug - [FE] [BE] Data RO masih banyak yang belum ke get (status rumah,alamat pemohon kota,econ hubungan kerabat & kota econ, pekerjaan rt,rw,kota,profesi, job type, job industry dll)
* Fix Bug - [FE] Assign edit untuk no hp pasangan tidak tampil didetail sehingga menyebabkan stopper karena terkena validasi, no hp pasangan wajib diisi
* Fix Bug - [FE] edit nilai pembiayaan tapi kemudian muncul pesan "Gagal Save draft"
* Fix Bug - [FE] Nilai pembiayaan berubah saat edit angsuran (melewatkan nilai administrasi)
* Fix Bug - [FE] Urutan foto mandatory semua dahulu baru non mandatory
* Fix Bug - [FE] [BE]Proses edit nilai pembiayaan, admin fee dan Angsuran.
- Saat pengguna melakukan edit terhadap nilai pembiayaan, admin fee dan angsuran. maka proses perhitungan dijalankan setelah pengguna menekan tombol ""Refresh Kalkulasi"". Nilai Angsuran yang tampil berdasarkan hasil kalkulasi. Tidak ada proses validasi jika nilai angsuran dibawah nilai ""Bottom Rate""
- Jika pengguna melakukan perubahan hanya pada nilai Angsuran dan nilai angsuran lebih besar dari nilai sebelum di edit, maka nilai yang terpengaruhi adalah Total Pinjaman, Bunga Pembiayaan dan Flat Pertahun.
- JIka pengguna melakukan perubahan hanya pada nilai Angsuran dan nilai angsuran lebih kecil dari nilai sebelum di edit maka akan terkena validasi bahwa Nilai Angsuran minimal ........ (sesuai nilai angsuran sebelum di edit)"
* Fix Bug - [FE]  nilai penghasilan ditambahkan validasi minimal 1 juta
* Fix Bug - [FE] Calculator perhitungan angsuran KMB
* Fix Bug - [FE dan BE] Save as Draft KMB
* Fix Bug - [FE] Blank putih saat take photo
* Fix Bug - [FE] Tahun Bekerja Sejak di data pekerjaan berubah ke 1900 kalo buka dr draft
* Fix Bug - [FE] Buka dari draft, status pernikahan yang sebelumnya Janda-Duda harus di pilih ulang
* Fix Bug - [FE] Tidak ada alert merah ketika field rekomendasi belum di pilih
* Fix Bug - [FE] DATA RO di tab econ RT/RW,area phone, dan hub kerabat kosong, dan di tab data pekerjaan RT/RW nya kosong
* Fix Bug - [FE] Status Janda salah harusnya Janda / Duda


* WG

* Fix Bug - [FE dan BE] Save as Draft WG, Saat save as draft, tambahkan mandatory pada metode penjualan, status pernikahan, jenis kelamin, pendidikan, status rumah, agama (Done)
* Fix Bug - [FE] Tidak ada alert merah ketika field status pernikahan, jenis kelamin, pendidikan, status rumah, agama blm di pilih (Done)
* Fix Bug - [FE] buka dari draft, Tidak ada alert merah ketika area phone blm d isi 
* Fix Bug - [FE] Edit Rate ke desimal dan edit DP (dua duanya diedit) Menjadi hang dan keybard tidak menutup
* Fix Bug - [FE] [BE] Data RO masih banyak yang belum ke get (status rumah,alamat pemohon kota,econ hubungan kerabat & kota econ, pekerjaan rt,rw,kota,profesi, job type, job industry dll)
* Fix Bug - [FE]  nilai penghasilan ditambahkan validasi minimal 1 juta
* Fix Bug - [FE] Hang saat edit dp
* Fix Bug - [FE dan BE] Save as Draft WG
* Fix Bug - [FE] Data RO muncul tanda seru di tab alamat pemohon padahal di cek sudah terisi semua tidak ada alert merah (setelah isi ulang kota baru bisa)
* Fix Bug - [FE] Tidak ada alert merah ketika field status pernikahan, jenis kelamin, pendidikan, status rumah, agama blm di pilih
* Fix Bug - [FE] Buka dari draft, status pernikahan yang sebelumnya Janda-Duda harus di pilih ulang
* Fix Bug - [FE] buka dari draft, Tidak ada alert merah ketika penghasilan tetap dan biaya hidupnya 0
* Fix Bug - [FE] buka dari draft, Tidak ada alert merah ketika area phone blm d isi
* Fix Bug - [FE] data RO, saat di submit, data kota tempat tinggal, kota econ, profesi, job type, job position dan industri jadi kosong lagi padahal sebelumnya udah diisi
* Fix Bug - [FE] saat retake foto, label foto close up konsumen masih "konsumen dan KTP"
* Fix Bug - [FE] Status Janda salah harusnya Janda / Duda
* Fix Bug - [FE] Kota di alamat pemohon selalu hilang ketika submit draft


//# Unpublished Version History (APKs sent to client)
* 19-September-2019, 1.33.36.004(148)
* fix bug - Data RO masih banyak yang belum ke get (status rumah,alamat pemohon kota,econ hubungan kerabat & kota econ, pekerjaan rt,rw,kota,profesi, job type, job industry dll)
* fix bug - Assign edit untuk no hp pasangan tidak tampil didetail sehingga menyebabkan stopper karena terkena validasi, no hp pasangan wajib diisi
* fix bug - edit nilai pembiayaan tapi kemudian muncul pesan "Gagal Save draft"
* fix bug - Nilai pembiayaan berubah saat edit angsuran (melewatkan nilai administrasi)
* fix bug - Menambahkan fitur kalkulasi button di data perhitungan KMB

//# Unpublished Version History (APKs sent to client)
* 19-September-2019, 1.33.36.004(148)
* fix bug - Data RO masih banyak yang belum ke get (status rumah,alamat pemohon kota,econ hubungan kerabat & kota econ, pekerjaan rt,rw,kota,profesi, job type, job industry dll)
* fix bug - Assign edit untuk no hp pasangan tidak tampil didetail sehingga menyebabkan stopper karena terkena validasi, no hp pasangan wajib diisi
* fix bug - edit nilai pembiayaan tapi kemudian muncul pesan "Gagal Save draft"
* fix bug - Nilai pembiayaan berubah saat edit angsuran (melewatkan nilai administrasi)
* fix bug - Menambahkan fitur kalkulasi button di data perhitungan KMB

//# Unpublished Version History (APKs sent to client)
* 4-September-2019, 1.33.36.003(147)
* fix bug - Field Foto Konsumen menjadi Foto close up konsumen
* fix bug - field ibu kandung dan Nama Legal di hide untuk sementara waktu
* fix bug - Foto close up, KTP dibuatkan frame
* fix bug - Untuk input nomor mesin diubah menjadi 9 karakter minimal
* fix bug - Assign Edit terkait dengan TTD yang sering tidak muncul 
* fix bug - data jenis kelamin data yang diinputkan tidak muncul saat proses edit
* fix bug - Pesan Pop up pada saat akan mengambil photo, kontennya diubah. 
* fix bug - Posisi urutan persetujuan dan image ditukar posisinya secara UI
* fix bug - Field NPWP, jika total belanja > 50 Juta maka akan muncul tab NPWP dibawah Detail Asset
* fix bug - panjang character nama saat ini minimal 3 tetapi ada kebutuhan dilapangan untuk nama dengan panjang char 2 jadi tidak bisa didaftarkan
* fix bug - Replace string " , " dan " . " pda kolom nama
* fix bug - master supplier pengajuan awal dilepas master sycn
* fix bug - tambah parameter Product id saat submit yg di dapat pada saat memilih product offering
* fix bug - Terjadi hang saat input DP dan rate ada decimal
* fix bug - Replace string " , " , " . " dan special char diubah menjadi spasi
* fix bug - text FAQ diubah menjadi Bantuan"

//# Unpublished Version History (APKs sent to client)
* 24-Juli-2019, 1.33.36.002(145)
* fix bug - jika kop belum diisi terus produk kosong maka munculkan message kop belum diisi
* fix bug - Pelepasan validasi mandatory POS + Pemasangan kembali validasi mandatory
* fix bug - Data Pekerjaan & Data Pasangan suami istri, RT dan RW   bisa submit menggunakan 0
* fix bug - Data Pekerjaan - Penghasilan tetap menampilkan double error message
* fix bug - Emergency Contact - Field Area Phone memiliki double error message
* fix bug - metode penjualan, direct or inhouse dilock sesuai dengan login, saat ini di FE masih bisa dipilih
* fix bug - product offering yang tampil berdasarkan metode penjualan 
* fix bug - First payment default (FPD) ,  Khusus untuk KMB, nama CMO yang terkena (FPD) tidak ditampilkan. CMO yang terkena FPD tidak bisa login. Dibuatkan notifikasi saat CMO tersebut sdg dalam status FPD. Proses kroscek status dilakukan pada saat save as draft, submit, login dan validasi awal 
* fix bug - data pasangan, hilangkan mandatory untuk field yang berhubungan dengan pekerjaan (profesi, job type, job position, industri, status pegawai, nama perusahaan, nomor telepon perusahaan, kode area)
* fix bug - data pekerjaan, penghasilan pasangan bisa 0 inputnya dan diberi default 0
* fix bug - masih bug d perhitungan pembiayaan, jika ganti angsuran maka hanya merubah bunga flat dan total. dan jika ganti tenor, semua jadi default
* fix bug - saat selesai take foto selfie, popup harus di dismiss di samsung
* fix bug - saat refresh list pengajuan data pengajuannya malah bertambah
* fix bug - Data Perhitungan, saat ambil data dari default, selalu kena validasi input tidak boleh 0

//# Unpublished Version History (APKs sent to client)
* 1-Mei-2019, 1.33.34.429(142)
* fix bug - [EFORM-KMB] - [LIST PENGAJUAN TAB] - [PENGAJUAN DETAIL PAGE] - [Menu Agunan] - [Field Nama Bpkb] - Data yang ditampilkan sedikit terpotong
* fix bug - tidak bisa input spasi di awal kalimat, dan ini berlaku untuk semua field
* fix bug - tidak dapat input 00 untuk semua field rt dan ktp
* fix bug - minimal 3 digit untuk setiap kode area telepon rumah
* fix bug - nama bpkb tidak bisa di edit jika di ubah ubah pada pengajuan baru
* fix bug - penambahan wording pada form data perhitungan
* fix bug - assign edit, untuk field yang tidak bisa di edit warna nya abu abu (untuk semua spinner juga)
* fix bug - ubah ubah pokok pembiayaan crash
* fix bug - ubah ubah biaya admin crash
* fix bug - ubah ubah angsuran perbulan crash
* fix bug - jika input value terlalu banyak dari jumlah yang di tentukan maka akan langsung crash, ini berlaku untuk field pokok pembiayaan, biaya admin, dan angsuran perbulan
* fix bug - open draft, pada jenis pembiayaan hilang / tidak terisi
* fix bug - jika radio button rekomendasi blm di isi, tulisan rekomendasi menjadi warna merah
* fix bug - white spase terlalu banyak sebelum kirim OTP ke FE
* fix bug - buat pengajuan baru, npwp hilang terlebih dahulu. akan muncul jika pokok pembiayaan di atas 50 jt
* fix bug - jika harga pokok lebih dari 50 juta npwp muncul, jika di bawah 50 jt field npwp hilang
* fix bug - crash jika klik submit karna penghasilan tetap, lain, pasangan, dan biaya hidup pada form data pekerjaan
* fix bug - crash jika klik buttom submit di karnakan terdapat value string pada variable integer
* fix bug - crash jika klik tombol submit di karnakan pokok pembiayaan null 
* 26-April-2019, 1.33.34.417(141)
* change feature - send po hilangin kalau CMO
* change feature - di freze/ tidak dapat edit field :
- StnkPerhitunganKendaraan
- BiayaNotarisPerhitunganKendaraan
- BiayaPnbpFidusiaPerhitunganKendaraan
- BiayaSurveyPerhitunganKendaraan
* fix bug - perbikan perhitungan angsuran perbulan (fe : default, kirim 0 untuk pokok kendaraan jika ubah angsuran perbulan)
* fix bug - perbikan perhitungan nilai pokok pembiayaan dan biaya admin (fe : default, kirim 0 untuk angsuran perbulan jika ubah biaya admin atau pokok kendaraan)
* fix bug - ganti ganti tenor
* fix bug - ubah value di data perhitungan ngehit api perhitungan terus terusan
* fix bug - ubah ubah tipe kendaraan, lalu kiri radio button bpkb nama sendiri itu tidak bisa
* fix bug - margin nama bpkb berantakan
* fix bug - hilangin popo up nama bpkb dan warna merah. ganti set error ke text input layout
* fix bug - penghasilan tetap nggak boleh 0 atau 00 atau 01, dan pasangan jika status pernikahan menikah, biaya hidup
* fix bug - email huruf besar semua
* fix bug - merk kendaraan dan semua spinner kudu dimulai dari pilih
* fix bug - hilangin hint nama bpkb
* fix bug - bisa ttd wajib isi semua kop
* fix bug - white space falet rate di bawah
* fix bug - jika pokok pembiayaan di bawah 50 juta, npwp masih bisa di isi dan di submit kurang dari 15 digit
* fix bug - hadnphone di data pekerjaan dan data pasangan muncul jika profesi wiraswasta, dan kode area dan no telfon hi hide
* 23-April-2019, 1.33.33.389(139)
    * fix bug - [EFORM-KMB] - [Menu : Alamat Pemohon - Emergency Contact - Data Pekerjaan] - [Field RT & RW] - field RT dan RW bisa di input dengan angka 01, 02, 03, dst
    * fix bug - [EFORM-KMB] - [Menu AGUNAN] - [BPKB atas nama] Muncul tanda seru pada field BPKB atas nama, kasih notif pop up
    * fix bug - di Informasi Perincian Pembiayaan untuk tipe pengajuan PSA yang muncul hanya field Angsuran Perbulan (Rp) dan Flat Pertahun
    * fix bug - [EFORM KMB] - [BACK END WEB] - Data New - Di Back End WEB dilakukan fungsi Assign Edit To CRO - Saat melakukan edit data di Data Pribadi muncul tanda seru di Detail Product, Agunan, Data Perhitungan dan Persetujuan
    * fix bug - field NPWP seharusnya tidak boleh diinput kurang dari 16digit jika pokok pembiayaan di atas 50 jt
    * fix bug - wording Nomor telepon rumah seharusnya diganti menjadi Phone
    * fix bug - seharusnya kode area wajib diisi minimal 3 digit
    * fix bug - Nomor KTP wajib diisi 16 digit
    * fix bug - Tipe Pengajuan Double
* 22-April-2019, 1.33.33.380(138)
    * Change Feature - perubahan pemilihan tipe pengajuan psa dan non psa ke form kop
    * change feature - perubahan view pada pilihan nama bpkb untuk pengajuan baru, draft, dan assign edit.
    * * fix bug - Pada tab List Pengajuan, pilih status droplist Reject. Kemudian klik salah satu data reject, muncul halaman PENGAJUAN DETAIL. Field Status yang muncul : On Process/Principal Approval. Seharusnya isi field Status : Reject
    * * fix bug - Pada tab List Pengajuan, pilih status droplist Reject. Kemudian klik salah satu data approved, muncul halaman PENGAJUAN DETAIL. Field Status yang muncul : Principal Approval. Seharusnya isi field Status : Approved
    * fix bug - Di halaman Pengajuan Detail, pada menu Data Perhitungan tidak muncul field Downpayment dan berdasarkan informasi dari Pak Best, field DownPayment harus ditampilkan.
    * fix bug -  Di tab List Pengajuan,  pada saat CRO klik salah satu data konsumen maka muncul halaman Pengajuan Detail. Di bagian Agunan, adanya salah penulisan hint.no_polisi. Seharusnya No polisi
    * fix bug - pada saat CRO klik salah satu data konsumen maka muncul halaman Pengajuan Detail. Di bagian Agunan, info Regional kosong. Sedangkan saat inputan pertama, Regional dan Cabang auto generated
    * fix bug - Di bagian Data Pekerjaan adanya urutan data yang kurang sesuai yaitu Area Phone-Mobile Phone Number-Phone. Seharusnya urutan yang benar : Area Phone-Phone-Mobile Phone Number.
    * fix bug - Pada field Jumlah Tanggungan bisa input angka lagi setelah angka nol (0)  contohnya saya input 01, 02 atau 03 bisa dan berhasil Submit. Pada PENGAJUAN DETAIL untuk Jumlah Tanggungan bernilai 1.
    * fix bug - edit pengajuan draft Muncul semua menu termasuk KOP menu (KOP menu tetap muncul)
    * fix bug - tidak bisa simpan data yang awalannya 01 02 dst untuk jumlah tanggungan
    * fix bug - list product offering untuk psa dan non psa
    * fix bug :
    1. user klik button Submit
    2. muncul notifikasi Mohon Periksa Kembali Data Perhitungan
    3. Screen tetap pada bagian Form Menu dan muncul tanda seru (!) pada menu DATA PERHITUNGAN
    4. Klik menu DATA PERHITUNGAN
    5. Klik button Submit
    6. Muncul popup message "Apakah Anda yakin untuk submit data?"
    7.. Klik tombol Submit
    8.. Muncul popup notifikasi "Pengajuan Berhasil Disubmit" pada screen List Pengajuan
    * fix bug - Ditemukan adanya beberapa data field Kota yang kosong pada menu :
    a. Alamat Pemohon, submenu Alamat Tinggal
    b. Data Pasangan Suami/Istri
    c. Emergency Contact
    Dimana seharusnya field Kota tersebut autofilled
* 10-April-2019, 1.33.35.356(133)
    * Fix Bug - mimimal dan maximal pembiayaan kredit untuk pokok pembiayaan, dp, angsuran perbulan, dan admin
* 27-Februari-2019, 1.33.35.348(127)
    * Fix Bug - rekomendasi 7 hari
    * Fix Bug - tenor
    * Fix Bug - hapus uplaod pengajuan draft yang tersimpan di DB
    * Fix Bug - autocompelete marketing supplier
    * Fix Bug - autocompelete product offering
    * Fix Bug - autocompelete pos
    * Fix Bug - autocompelete tenor
    * Fix Bug - autocompelete jenis kendaraan
    * Fix Bug - autocompelete merk kendaraan
    * Fix Bug - autocompelete tipe kendaraan
    * Fix Bug - autocompelete model kendaraan
    * Fix Bug - nama ibu kandung save draft	-> aman FE
    * Fix Bug - penghasilan pasangan pekerjaan save draft	-> aman FE
    * Fix Bug - terkadang CRO habis ttd dan take photo suka balik lagi ke awal
    * Fix Bug - simpan image save draft (form pengajuan baru dan form draft)
    * Fix Bug - nama ibu kandung open draft
    * Fix Bug - penghasilan pasangan pekerjaan open draft
    * Fix Bug - email jadi 50 char
    * Fix Bug - tidak ttd pengajuan jika open draft dengan tipe pengajuan psa
    * Fix Bug - tidak hitung ulang jika bpkb atas nama sendiri
* 27-Februari-2019, 1.33.33.324(124)
    * Fix Bug - jika status pernikahan tidak menikah, maka pisah harta tidak di tammpilkan
    * Fix Bug - pindah halaman form pengajuan assign edit dari push notif
    * Fix Bug - perbaikan relasi pilih kendaraan motor
    * change feature - untuk field kota semuanya search able
    * change feature - list down supplier, product offering, pos, marketing supplier, jenis kendaraan, merk kendaraan, tipe kendaraan, tahun kendaraan
* 28-Januari-2019, 1.33.33.324(124)
    * Fix Bug - push notif tidak terkirim jika CRO login di device lain dan kembali lagi ke device pertama
    * Fix Bug - tidak bisa klik tombol submit pengajuan kredit karna ada nama bpkb untuk KMB KMOB
* 28-Januari-2019, 1.33.33.322(123)
    * Migrasi dari GCM ke FCM
* 25-Januari-2019, 1.33.32.322(122)
    * Update ke android PIE
* 24-Januari-2019, 1.33.31.322(121)
    * Update ke android PIE
    * Change Fitur - search nama barang
* 3-Desember-2018, 1.33.30.311(111)
    * Fix Bug - Npwp tidak muncul ketika assign edit untuk price > Rp 50.000.000
* 30-November-2018, 1.33.30.310(110)
    * Fix Bug - Perbaikan message customer blacklist
* 28-November-2018, 1.33.30.309 (109)
    * Fix Bug - Perbaikan Kota
* 26-November-2018, 1.33.30.306 (106)
    * Fix Bug - Validasi NPWP saat save draft bisa jadi stopper jika user rubah nilai <50 jt
    * Fix Bug - Fix Bug - off data / handphone off untuk test autologout
    * Fix Bug - field Jumlah Tanggungan harusnya sensor (*) di data kreditmu
    * Fix Bug - Jika relogin, Foto Attachment di Draft akan hilang
* 15-November-2018, 1.33.30.302 (105)
    * Fix Bug - letak retake foto dari FE ke BE
    * Fix Bug - input just text and space
    * Fix Bug - validasi nomor handphone 08
    * Fix Bug - Hide feature kreditmu
* 15-November-2018, 1.33.30.298 (104)
    * Change Feature - Mengganti rumus NTF menjadi ikutin ntf di BE perhitungannya
    * Fix Bug - Crash jika CRO melakukan save draft kedua kali nya
    * Fix Bug - Jika multi asset berhasil input price yg lebih kecil daripada DP dan atau diskon. Seharusnya tidak bisa
    * Fix Bug - Kota pake | itu pemisahnya (jangan pake koma)
    * Fix Bug - Saat Assign Edit, jika status pernikahan dirubah jadi cerai/ lajang/ janda maka kolom Data Pasangan Suami/ Istri tidak hilang, harusnya tidak ada
    * Fix Bug - Data pasangan tidak berubah setelah assign edit
    * Fix Bug - Setelah sinkronisasi terkadang nggak bisa klik ini itu. harus di kill
    * Fix Bug - Saat Assign Edit CRO tidak bisa ubah Kota, Profesi dan Industri karena list pilihan tidak muncul saat di klik
    * Fix Bug - Upload Draft Jika logout, dan login kembali draft akan muncul kembali
    * Fix Bug - Tidak bisa sinkron jika menggunakan File Draft dan user relogin sebelumnya. Muncul error "Terjadi kesalahan pada server"
    * Fix Bug - Kadang-kadang Crash saat melakukan refresh di halaman Notification (coba lakukan beberapa kali)
    * Fix Bug - Kasih loading ketika cek version code (update apk)
    * Fix Bug - Jika buka pengajuan dari Save Draft (user relogin dahulu), biaya lainnya tidak otomatis 0 (jika user tidak isi)
* 10-November-2018, 1.33.30.285 (103) - Playstore
    * New Feature - Tambah image untuk fitur detail pencarian PO, delivered, invoice (image dari best)
    * New Feature - 1 device 1 akun (kecuali akun test untuk trial 9 dan maryati)
    * Fix Bug - simpan draft -> logout -> login -> buka draft -> klik data pribadi -> kumbang
    * Fix Bug - loading ketika logout dan login (loading meter horizontal)
    * Fix Bug - Tombol submit, bagian validasi untuk section detail asset.Kalo jumlah asset 0(belum di isi sama sekali) itu munculin juga segitiga validasi bahwa section itu masih ad yg kurang
    * Fix Bug - Pada saat penulisan karakter dibuatkan huruf kapital (sesuai dengan aplikasi sebelumnya)
    * Fix Bug - Ketika Auto Logout dan auto logout, upload draft
    * Fix Bug - Cabang akun sebelumnya masih nyangkut/muncul
    * Fix Bug - Mangganti ef number jika terdapat yang sama di back end
* 22-Oktober-2018, 1.31.30.278 (102) - Playstore
    * Fitur Mobil, Mobil Bekas, Motor -> hide
    * Dump ulang SP asset master
    * Fix Bug - Cek Tipe Asset apakah mandatory atau tidak
    * Fix Bug - Asset lolos jika masih kosong
    * Fix Bug - Menu Asuransi (Saat pilih YES-NO kemudian diganti menjadi YES-YES default 0 artinya asuransi bernilai 0 kemudian diganti menjadi YES-NO = asuransi tetap bernilai 0)
    * Fix Bug - Jika diinput dengan titik muncul kumbang pada data asuransi
    * Fix Bug - Number Phone dibuatkan menjadi 20 digit sesuai Confins
    * Fix Bug - Mandatory kolom kota
    * Fix Bug - Janda diubah menjadi Janda/Duda
    * Fix Bug - Lokasi masih sulit terdeteksi
    * Fix Bug - Form Draft dimunculkan kembali
    * Fix Bug - Cek plafon untuk api kreditmu
    * Fix Bug - Gagal update data untuk data asset dan foto
* 2-Oktober-2018, 1.31.30.268 (101) - Playstore
   * Fix Bug - FIx bug intent kamera untuk oreo
   * Pembulatan 1000 ke atas
   * Fix Bug - kirim asset code untuk multiple asset pengajuan kredit
   * Fix Bug - menyembukan tombol draft jika gagal sinkronisasi
   * Fix Bug - tinggal sejak tahun maksimal tahun 2018
   * Change Feature - input data dumb ke tabel kelurahan
* 1-Oktober-2018, Build 99 - Playstore
   * Membuat data dumg kelurahan
   * New Feature - Implement sp program bebas bunga
   * Fix Bug - show hide reason status customer
   * Fix Bug - Menambahkan product offering id di field product offering
   * Fix Bug - nama berubah di list draft, semua nama di yang pertama
* 1-Oktober-2018, Build 99 - Playstore
   * Fix Bug - message ntf minimal dari confins
   * Fix Bug - assign edit klik dari notif di hp, muncul kumbang
   * Fix Bug - sinkronisasi tidak ada longlat
   * Fix Bug - perhitungan bebas bunga
   * Fix Bug - Perhitungan premi asuransi
* 30-September-2018, Build 98 - Playstore
   * New Feature - program bebas bunga
   * New Feature - message apk lama wajib update ke apk yang terbaru
   * New Feature - single device = single akun (bisa login kembali ke device lain jika CRO sudah logout)
   * New Feature - Tenor selalu di setting Fix di confins
   * New Feature - NTF selalu di setting minimal di confins
   * New Feature - Admin selalu di setting minimal di confins
   * New Feature - Rate selalu di setting minimal di confins
   * New Feature - Notifikasi semua action status pengajuan belum semua masuk termasuk jika cancel by buyer
   * New Feature - pindah halaman reject dan cancel
   * New Feature - Alasan Status Cancel dan reject tidak dimuncul di Mobile App
   * Change Feature - Jumlah Asset dipindah ke detail asset dengan penambahan kolom dinamis + button
   * Change Feature - push notif reject dan cancel
   * Change Feature - Profesi, tipe, dan posisi pekerjaan data hanya dari confins (tidak menggunakan data dumb)
   * Fix Bug - Fixing EF Number
   * Fix Bug - Retake photo ganti menjadi kamera bawaan device
   * Fix Bug - retake foto nggak ke simpan di tempatnya
* 3-September-2018, Build 97 - Playstore
 * Change Feature - resend otp jangan  3 menit, tapi 1 menit
 * Change Feature - NTF selalu di setting minimal di confinns
 * Change Feature - Admin selalu di setting minimal di confins
 * Change Feature - Rate selalu di setting Fix di confins
 * Change Feature - Retake photo ganti menjadi kamera bawaan device
 * Fix Bug - KOP masih bisa kirim otp dan sinkron
 * Fix Bug - Fixing EF Number
* 25-Juli-2018, Build 96 - Playstore
 * Fix Bug - Add Easy trakcer for google analytics
* 20-Juli-2018, Build 95 - Playstore
 * Fix Bug - Re query field type Job
 * Fix Bug - Re query field job position
 * Fix Bug - value recomendaton in detail form
* 18-Juli-2018, Build 94 - Playstore
 * Change Feature - Remove mandatory field pos in detail product offering
* 10-Juli-2018, Build 93 - `v.1.20.21.246` - Playstore
 * Fix Bug - make new submissions between branches
 * Fix Bug - Failed Auto Logout
 * Fix Bug - dificult get longitude latitude when login
 * New Feature - Search by name, ktp, date submited.
* 4-Juli-2018, Build 91 - `v.1.19.21.243` - Playstore
 * Fix Bug - percentage succes when login and new submission (progress dialog)
 * Fix Bug - Double form draft when logout
* 4-Juli-2018, Build 91 - `v.1.19.21.241` - Playstore
 * Bug when klik button draft
* 29-Mei-2018, Build 91 - `v.1.19.21.240` - Playstore
 * Optimize daily sign in and sign out
* 30-Apr-2018, Build 90 - `v.1.19.20.237` - Playstore
 * New Feature - MOBILE-WORDING popup status kreditmu
 * New Feature - Auto logout dbuat pukul 24;00
 * New Feature - app bisa di paka hanya dari jami 07:00-24:00 (jam device) buat popup saat aplikasi tidak bs digunakan
 * New Feature - CHECK AND SHOW DATA KREDITMU OR NON KREDITMU
* 18-Apr-2018, Build 87 - `v.1.15.20.237` - Playstore
 * New Feature - Add longitude, latitude, and action in form submission
 * Fix bug - Optimze create and update data
* 12-Apr-2018, Build 86 - `v.1.14.20.236` - Playstore
 * Fix bug - Double Lable for data confins + kreditmu
 * Fix bug - Flaging to back end for apk jabar and non jabar
* 11-Apr-2018, Build 85 - `v.1.14.20.232` - Playstore
 * Fix bug - Calculating FR always change to default
 * Fix bug - Crash after klik submit button on assign edit
* 06-Apr-2018, Build 84 - `v.1.14.20.232` - Playstore
 * Fix bug - Ketika Login terkadang masih dapat get long lat 0.0
 * Fix bug - untuk customer blacklist masih dapat di edit di form draft dan edit
* 26-Mar-2018, Build 83 - `v.1.14.20.230` - Playstore
 * New Feature - Send Longitude Latitude and action when login logout
 * Change Feature - Get No Hp Blacklist
 * Fix bug - Calculating Manual Premi
* 22-Mar-2018, Build 82 - `v.1.13.19.229` - Playstore
 * Fix bug - data customer menjadi doble ketika assign edit (ketika sinkron)
 * Fix bug - terkadang suka salah get data rekomendasi jika pilih no reomendasi
 * Fix bug - perbaikan perhitungan manualpremi jika pilih no
* 20-Mar-2018, Build 81 - `v.1.13.19.226` - Playstore
 * Fix bug - Perhitungan manual asuransi
 * Fix bug - Send data jika tidak rekomendasi
* 15-Mar-2018, Build 80 - `v.1.13.19.224` - Playstore
 * Fix bug - validasi flow, syarat, dan kondisi fitur rekomendasi
* 15-Mar-2018, Build 79 - `v.1.13.19.223`
 * New Feature - Fitur rekomendasi
* 6-Feb-2018, Build 74 - `v.1.18.24.213`
 * New Feature - aplikasi hanya bisa dipakai jika loc service aktif, freeze layar dan show pop up nyalakan gps saat gps non aktif
 * Change Feature - tambah parameter activity untuk get aktivitas lokasi :
- Login
- Menu Refresh
- Button Next From Pengajuan Baru
- Save Draft, Dari Pengajuan Baru, Draft, & Edit
- Submit, Dari Pengajuan Baru, Draft, & Edit
- Signature, Dari Pengajuan Baru, Draft, & Edit
- Sinkronisasi
- Cancel Logout When Click Logout Button
- Succes Logout When Click Logout Button
 * New Feature - Set Location on button logout
* 29-Jan-2018, Build 73 - `v.1.17.23.213`
 * New Feature - Set Location on button login
 * New Feature - Set Location on button logout
 * New Feature - Field Dinamis for elektronik & kendaraan
 * Change Feature - Show QR code
 * Fix Bug - Fix flow elektronik & kendaraan
 * Fix Bug - Fix flow assign edit & retake photo form push notif
  02-Jan-2018, Build 72 - `v.1.14.22.211`
 * New Feature - Set GPS Tracker (Longitude & Latitude)
* 28-Des-2017, Build 71 - `v.1.13.22.211`
 * Fix Bug - Validasi dan message flow npwp untuk pengajuan baru, draft, dan edit NPWP
* 22-Des-2017, Build 70 - `v.1.13.22.210`
 * New Feature - Field Npwp jika minimal Rp 50.000.000
 * Change Features - Angsuran bisa di edit
* 17-Des-2017, Build 69 - `v.1.12.21.210`
 * Fix Bug - Jika DISCOUNT bernilai 0, tertulis Rp. Seharusnya tertulis Rp. 0.Dan price di detail asset harus ada rupiah
 * Fix Bug - Terkadang jika hanya isi di foto ke dua, lalu klik submit, muncul notif sukses (tapi langsug keluar aplikasi). Kalau bailk lagi masuk ke aplikasi lewat tombol home (samsung), data masih bisa di edit. Tapi klo buka aplikasi secara normal, data di notifikasi tidak (sukses diupload)
 * Fix Bug - User masih bisa isi Discount + DP >= dari pada price. Seharusnya Discount + DP tidak boleh lebih besar atau sama dengan price. 
* 13-Des-2017, Build 68 - `v.1.12.21.207`
 * Fix Bug - tidak bisa klik next pada form pengajuan elektronik jika price kurang dari 1,5 jt
 * Fix Bug - klik no pada radio button data asuransi tidak bisa sinkron
* 12-Des-2017, Build 67 - `v.1.12.21.205`
 * Fix Bug - Pada combo box KOP pilihan defaultnya adalah -Pilih Data
 * Fix Bug - Pilihan Asuransi menjadi Yes / No
 * Fix Bug - Harga minimum barang adalah 1.500.000
 * Fix Bug - Harga minimum barang adalah 1.500.000 (Edit dan Draft)
* 11-Des-2017, Build 66 - `v.1.12.21.201`
 * Change Features - Pada combo box KOP pilihan defaultnya adalah -Pilih Data
 * Change Features - Pilihan Asuransi menjadi Yes / No
 * Change Features - Harga minimum barang adalah 1.500.000
* 29-Nov-2017, Build 65 - `v.1.12.18.201`
 * Fixing Bug - File attachment in pengajuan baru
 * Fixing Bug - Re-layout OCR Camera to Landscape
 * Fixing Bug - Message and Validation DP and Discount equal to Price
 * Fixing Bug - Validation price equal to dp plus discount when klik next button
* 27-Nov-2017, Build 65 - `v.1.12.18.196`
 * Fixing Bug - Crash assign edit
 * Fixing Bug - Print pengajuan (remake 4.59)
 * Fixing Bug - Crash input nominal dp
 * New Features - Inquery, checking data from kreditmu
* 21-Nov-2017, Build 64 - `v.1.11.18.193`
 * Change Features - DP Validation 0 % (Zero Percent)
 * Change Features - Add Frame in OCR Camera and Delete Crop Library
 * Change Features - Change Value No Assurance to Zero
* 17-Nov-2017, Build 63 - `v.1.11.15.193`
 * Fixing Bug - Adding new data of city
 * Fixing Bug - Adding new data of supplier marketing
* 07-Nov-2017, Build 62 - `v.1.11.15.191`
 * Change Features - Delete dp validation
* 07-Nov-2017, Build 61 - `v.1.11.14.191`
 * Fixing Bug - OCR
 * Fixing Bug - Masking
 * Fixing Bug - Sonar
 * New Feature - List Kreditmu
 * Fixing Bug - Separator coma
 * Fixing Bug - Email validation
 * Fixing Bug - status pengajuan
* 01-Nov-2017, Build 60 - `v.1.10.14.185`
 * New Features - Ocr
 * New Features - Masking product offering
* 18-Okt-2017, Build 59 - `v.1.8.14.185`
 * Fixing Bug - Validation asset
 * Fixing Bug - Rp premi and credit card limit in detail
 * Fixing Bug - Rounded double
* 18-Okt-2017, Build 58 - `v.1.8.14.182`
 * Minor Changes - Wording warning and toast message
 * Fixing Bug - Dp price asset
 * Fixing Bug - Refresh attachment button in assign edit
 * Change Features - Add id family
* 16-Okt-2017, Build 57 - `v.1.8.13.179`
 * Change Features - Hide some features
 * Fixing Bug - Refresh failed load attachment in assign edit
 * Fixing Bug - validation input token
 * Minor Changes - Validation dp asset can't less then 10 % and 1000
* 12-Okt-2017, Build 56 - `v.1.8.12.176`
 * New Features - Flavor
 * Change Features - Calculation optimation
 * Change Features - Warning dp price after input dp
 * Change Features - Percentage dp based on first dp (asset more than one)
 * Fixing Bug - Send asset for sms token
 * Fixing Bug - Date input, when select october always appear 010 not 10
 * Change Features - Handling token expired
 * Change Features - Change minimum tanggal terbit ktp to 2011
 * Fixing Bug - Add default 0 to some perhitungan field
 * Change Features - Rearrange order of download master data
 * Change Features - Master sync in home only can be start if previous master sync have been done
* 26-Sep-2017, Build 55 - `v.1.7.5.173`
 * Fixing Bug - Kreditmu, cant hit api because have wrong key to get value
 * Fixing Bug - Wrong wording in value kelurahan in master sync
 * Fixing Bug - Change password for new cro
 * Fixing Bug - Field birthplace still can edited
 * Fixing Bug - Field dp and price didn't show RP sign in detail
 * Fixing Bug - Sonar
* 20-Sep-2017, Build 54 - `v.1.7.5.167`
 * Fixing Bug - Bunga calculation
 * Fixing Bug - Nomer and KPM
 * Fixing Bug - Spinner asset and city field in assign edit
 * Minor Changes - Wording progres dialog calculation
 * Fixing Bug - Condition tahun bulan tinggal and kerja
* 15-Sep-2017, Build 53 - `v.1.7.5.162`
 * Fixing Bug - Library compress picture
 * Fixing Bug - Wording list kreditmu
 * Fixing Bug - Add validaiton pengauan from kpm
 * Fixing Bug - Add uneditable for field ibu kandung
 * Fixing Bug - Dp and discount null in detail
 * Fixing Bug - Warning retake photo
 * Fixing Bug - Handling Dp price comparison
 * Fixing Bug - Wording warning token sms
 * Fixing Bug - Crash sync attachment
 * Fixing Bug - Place holder missing after select dropdown
 * Fixing Bug - Wording submitted in dropdown list status
 * Minor Changes - Wording place holder
 * Minor Changes - Add progress dialog shen click data perhitungan
 * Minor Changes - Validation calculating
* 12-Sep-2017, Build 52 - `v.1.7.5.148`
 * New Feature - Change password for new Cro
 * Minor Changes - presenter and mvpview qr scanner
 * Change Feature - Add validation if dp morethan price
 * Change Feature - Delete email validation
* 08-Sep-2017, Build 51 - `v.1.6.5.145`
 * Fixing Bug - library camera in kreditmu
 * Minor Changes - add excepetion type data and number format
 * Fixing Bug - Delete register eventbus retake photo
* 06-Sep-2017, Build 50 - `v.1.6.5.142`
 * Change Features - Notification disappear after done assign edit of take photo.
 * Fixing Bug - Data Draft Uncomplete
 * Fixing Bug - Title Premi manual didnt disappear when choose no in premi manual
 * Fixing Bug - Wrong send data of signature husband/wife
 * Fixing Bug - OVD information in blaclist customer
 * Minor Changes - New layout dialog for balcklist warning
 * Fixing Bug - Maximum input digit flate rate
 * Minor Changes - Loading appearance in notification
 * Fixing bug - Loading dialog text while hit api syarat ketentuan
 * Change Features - New camera library
* 30-Aug-2017, Build 49 - `v.1.6.3.134`
 * Fixing Bug - Email validation
 * Fixing Bug - Back button acivity signature
 * Fixing Bug - Credit card limit, maximum 9 digit
 * Fixing Bug - Flate rate, maximum 4 digit
 * Fixing Bug - Wording validation for alamat edit text
 * Change Feutures - Add handling failed when hit api because rto and unknownhost
 * Change Feutures - Wording Warning Save Draft
 * Change Feutures - Disable Edit text, make uneditable field like text view(unclickable and grey text color)
 * Fixing Bug - Wrong send data rt ktp
 * Fixing Bug - Crash when sending attachment, because wrong add list of attachment
 * Fixing Bug - Select year and month more than now
 * New Features - Menu list kreditmu and form kreditmu
 * Fixing Bug - Premi manual. Calculating and sending-receiving premi manual
 * Fixing Bug - Delete title date when choose tanggal bulan tinggal
 * Fixing Bug - change logic if sync data un ordered
 * Minor Changes - add condition for illegal statement
 * Fixing Bug - auto complete selection not automatically appear
 * Fixing Bug - lag when select marketing supplier
 * Minor Changes - Delete log chraslytic whne get response code 500
 * Fixing Bug - Uncomplete data when get data draft from login
 * Minor Changes - progress dialog view didnt reset in first time show up if re-hit master sync
 * New Fatures - QR Code
* 21-Aug-2017, Build 48 - `v.1.4.117`
 * Delete layout for tablet
 * Hide menu setting
* 14-Aug-2017, Build 48 - `v.1.4.115`
 * Fixing bug showing notification return rate
 * Fixing bug sign in, add condition if user sign in many times, data safe draft only save once
 * Add count in master sync presenter
 * Add validation to sending code
 * Fixing bug hit api generate pdf perjanjian kredit. add word api
 * Fixing counting sms in kreditmu
 * Implement usage percentage
 * Add update coloumn table in fucntion on upgrade database
 * un set vibrate for notification
 * add handling if failed to load image attachment from url in assign edit
 * add log exception and log to crashlytis in some presenter and assign edit
* 09-Aug-2017, Build 47 - `v.1.4.104`
 * Fixing bug sync, cannot sync becse false value of form type
 * Fixing bug delete button attachment
 * Fixing bug alert dialog saldo didn't appear
* 08-Aug-2017, Build 46 - `v.1.4.101`
 * Add build configs for stetho
 * set max field premi manual to 11
 * fixing bug premi manual in calculation
 * Fixing bug ovd didn't appear in pengajuan kpm
 * Fixing bug can't logout
 * Wording alert when click save draft
 * Return rate and precentage usage (precentage usage not ready yet)
 * Attachment and signature in kreditmu
 * Checking saldo kreditmu before next in pengajuan
 * Add progress bar in attachment image when loading image
 * set read time to 30 second
 * Generate pdf triggered form click download pdf
* 26-Jul-2017, Build 45 - `v.1.4.89`
 * Fixing bug refresh token
 * Prepare variable download pdf perjanjian kredit
 * Send Email Button move to menu option in detail activity
 * Progress dialog when click header perhitungan
 * Implement pop up dialog contains information about what data should be edited when open assign edit
 * Set read Timeout to 30 Seconds
 * Kreditmu
 * Bug progress bar. If re-update data with different branch, bar progres doesn't start form 0 but from the last progress
* 17-Jul-2017, Build 44 - `v.1.4.81`
  * fixing bug,in draft if pengajuan from kpm, photo ktp cannot be delete and change
  * fixing bug, unordered image when send it to backend
  * Change Validation attachment to 2 photos
  * Change label attachment
  * Fixing bug, Incorrectly sending data on the second and subsequent synchronization
  * Task Manual input premi
  * add customer id for akkk and vkkk
* 06-Jul-2017, Build 43 - `v.1.4.74`
  * Fixing bug undismiss progress dialog checking session
  * Delete effective rate in retake photo
  * Fixing bug lag when input price, dp and discount
* 05-Jul-2017, Build 42 - `v.1.4.71`
  * Fixing bug assign edit, product id
  * Delete effective rate
  * Fixing bug crash when sync attachment after kill app
  * Build Camera
  * Fixing bug image do not appear in ordered
* 20-Jun-2017, Build 41 - `v.1.4.66`
  * Prepare print pengajuan kredit
  * Fixing bug asynctask query kelurahan
  * Fixing bug token and dialog token
  * fixing bug savedinstance showing dialog fragment
  * Implement ovd in detail
  * Implement force update
  * fixing bug assignedit(in unsync list)
* 16-Jun-2017, Build 40 - `v.1.4.59`
  * Fixing bug download bar pengajuan baru and login
  * Fixing bug no credit card in assign edit and detail
* 13-Jun-2017, Build 39 - `v.1.4.57`
  * Use progress bar while downloading data master in login and pengajuan baru
  * Replace progres dialog "please wait" when open draft pengajuan to spinner in field kota
  * Fixing bug Refresh Token
  * Add feedback assign edit
  * Prepare limit 3 times assign edit (add toast)
  * Fixing bug sync data assign edit after draft
* 08-Jun-2017, Build 39 - `v.1.4.51`
  * Fixing Bug api assign edit
  * implement bucket list
  * Fixing bug retake foto
  * Fixing bug data pasangan in assign edit
  * Fixing bug sales method
  * Fixing bug sync data dump
  * Implement auto logout
  * Didn't show loading dialog in home
* 02-Jun-2017, Build 24 - `v.1.4.43`
* 30-May-2017, Build 23 - `v.1.4.38`
* 26-May-2017, Build 22 - `v.1.4.26`
* 24-May-2017, Build 21 -`v.1.4.23`
* 24-May-2017, Build 20 - `v1.4.19`
* 19-May-2017, Build 19 - `v1.4.9`
* 19-May-2017, Build 18 - `v1.4.7`
* 18-May-2017, Build 17 - `v1.4.2`
* 02-Apr-2017, Build 16 - `v1.2.2`
* 29-Mar-2017, Build 15 - `v1.2.1`
* 21-Mar-2017, Build 14 - `v1.1.9`
* 18-Mar-2017, Build 13 - `v1.1.8`
* 13-Mar-2017, Build 12 - `v1.1.7`
* 08-Mar-2017, Build 10 - `v1.1.6`
* 02-Mar-2017, Build 9 - `v1.1.5`
* 01-Mar-2017, Build 8 - `v1.1.4`
* 28-Feb-2017, Build 7 - `v1.1.3`
* 27-Feb-2017, Build 6 -`v1.1.2`
* 23-Feb-2017, Build 5 - `v1.1.1`
* 22-Feb-2017, Build 4 -`v1.1.0`
* 15-Feb-2017, Build 4 - `v1.0.9`
* 13-Feb-2017, Build 2 -`v1.0.8`
* 01-Feb-2017, Build 1 - `v1.0.7`

---

## For previous update history, click [here](UPDATES-LEGACY.md)
