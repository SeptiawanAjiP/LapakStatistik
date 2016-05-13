-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 06, 2016 at 05:38 PM
-- Server version: 5.6.24
-- PHP Version: 5.5.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `lapak`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE IF NOT EXISTS `barang` (
  `id_barang` int(6) NOT NULL,
  `nama_barang` varchar(32) NOT NULL,
  `harga` varchar(12) NOT NULL,
  `keterangan` varchar(256) NOT NULL,
  `id_penjual` int(4) NOT NULL,
  `path_foto1` varchar(64) NOT NULL,
  `path_foto2` varchar(64) DEFAULT 'kosong',
  `path_foto3` varchar(64) DEFAULT 'kosong',
  `path_foto4` varchar(64) DEFAULT 'kosong',
  `id_kategori` int(3) NOT NULL,
  `status` enum('aktif','tidak aktif','tagline') NOT NULL DEFAULT 'tidak aktif',
  `liked` int(10) NOT NULL DEFAULT '0',
  `dilihat` int(6) DEFAULT '0',
  `stok` int(6) NOT NULL,
  `terjual` int(6) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id_barang`, `nama_barang`, `harga`, `keterangan`, `id_penjual`, `path_foto1`, `path_foto2`, `path_foto3`, `path_foto4`, `id_kategori`, `status`, `liked`, `dilihat`, `stok`, `terjual`) VALUES
(4, 'Komputer Jadul', '120000000', 'Komputer Jaman dulu Pas dipake kemana saja', 5, 'http://toko.komputasi.net/assets/images/msd1.jpg', 'http://toko.komputasi.net/assets/images/fd1.jpg', 'http://toko.komputasi.net/assets/images/fd3.jpg', 'http://toko.komputasi.net/assets/images/hd1.jpg', 100, 'aktif', 27, 25, 0, 0),
(5, 'Komputer Tahun 1', '12500000', 'Komputer Saya Beli dengan Uang sendiri', 5, 'http://toko.komputasi.net/assets/images/msd5.jpg', 'http://toko.komputasi.net/assets/images/fd2.jpg', 'http://toko.komputasi.net/assets/images/fd4.jpg', 'http://toko.komputasi.net/assets/images/fd5.jpg', 100, 'aktif', 23, 52, 0, 0),
(6, 'Kenangan Masa Lalu', '1000000', 'Lorem Ipsum', 5, 'http://toko.komputasi.net/assets/images/msd2.jpg', 'kosong', 'kosong', 'kosong', 100, 'aktif', 2, 43, 0, 0),
(7, 'Komnet 2', '125000', 'Barang dijual', 5, 'http://toko.komputasi.net/assets/images/msd3.jpg', 'http://toko.komputasi.net/assets/images/msd5.jpg', 'http://toko.komputasi.net/assets/images/fd6.jpg', 'http://toko.komputasi.net/assets/images/hd2.jpg', 100, 'tagline', 18, 8, 0, 0),
(8, 'Komnet 3', '1234567', 'Barang dijual 2', 5, 'http://toko.komputasi.net/assets/images/msd4.jpg', 'http://toko.komputasi.net/assets/images/fd4.jpg', 'http://toko.komputasi.net/assets/images/fd2.jpg', 'http://toko.komputasi.net/assets/images/hd3.jpg', 100, 'aktif', 21, 37, 0, 0),
(9, 'Komnet 1', '7654345', 'Jancuk Bro', 5, 'http://toko.komputasi.net/assets/images/fd5.jpg', 'http://toko.komputasi.net/assets/images/fd3.jpg', NULL, NULL, 100, 'aktif', 12, 22, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE IF NOT EXISTS `kategori` (
  `id_kategori` int(11) NOT NULL,
  `kategori` varchar(32) NOT NULL,
  `path_foto_kategori` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `kategori`, `path_foto_kategori`) VALUES
(100, 'Komputer', 'http://toko.komputasi.net/icon/komputer.png'),
(101, 'Fashion', 'http://toko.komputasi.net/icon/pakaian.png'),
(102, 'Smartphone', 'http://toko.komputasi.net/icon/smartphone.png'),
(103, 'Aksesoris Komputer', 'http://toko.komputasi.net/icon/komputer_aksesoris.png'),
(104, 'Makanan', 'http://toko.komputasi.net/icon/makanan.png'),
(105, 'Alat Tulis', 'http://toko.komputasi.net/icon/alat_tulis.png'),
(106, 'Kecantikan', 'http://toko.komputasi.net/icon/kecantikan.png'),
(107, 'Medis dan Obat', 'http://toko.komputasi.net/icon/medis.png');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL,
  `nama` varchar(32) NOT NULL,
  `email` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `jenis_kelamin` enum('L','P') NOT NULL,
  `path_foto` varchar(64) DEFAULT NULL,
  `tanggal_buat` datetime NOT NULL,
  `no_telp` varchar(16) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nama`, `email`, `password`, `jenis_kelamin`, `path_foto`, `tanggal_buat`, `no_telp`) VALUES
(1, 'aji', 'asfa', 'sdf', 'L', NULL, '0000-00-00 00:00:00', 'no_telp'),
(2, 'aji', 'asfa', 'sdf', 'L', NULL, '2016-04-13 06:51:58', 'no_telp'),
(3, 'aji', 'asfa', 'sdf', 'L', NULL, '2016-04-13 06:52:31', '032842'),
(4, 'ae', 'y', 'ja', 'L', NULL, '2016-04-13 07:23:49', '979'),
(5, 'Septiawan Aji Pradana', 'septiawanajipradana@gmail.com', 'lagidanlagi', 'L', NULL, '2016-04-07 06:00:00', '081215749494');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_barang`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `id_barang` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
