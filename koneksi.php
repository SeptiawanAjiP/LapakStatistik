<?php
/*
    LAPAK STATISTIK

0. HALAMAN UTAMA
1. DAFTAR USER
2. LOGIN
3. JUAL BARANG
4. DAFTAR BARANG
5. PEMBELIAN
6. KERANJANG BELANJA
7. DETAIL BARANG
8. BARANG FAVORIT
9. KATEGORI BARANG

*/
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "lapak";
date_default_timezone_set("Asia/Bangkok");


try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if(isset($_POST['a'])){
        $kode = $_POST['a'];

        if($kode == 1){

            $nama           = $_POST['nama'];
            $email          = $_POST['email'];
            $password       = $_POST['password'];
            $jenis_kelamin  = $_POST['jenis_kelamin'];        
            $tanggal_buat   = date("Y-m-d h:i:sa");
            $no_telp        = $_POST['no_telp'];

            $query = "SELECT id FROM user WHERE email= '$email'";
            $stmt = $conn->prepare($query); 
            $stmt->execute();
            // set the resulting array to associative
            $stmt->setFetchMode(PDO::FETCH_ASSOC); 
            $result = $stmt->fetchAll();

            if(count($result)==0){
                $query1 = "INSERT INTO user (nama,email,password,jenis_kelamin,tanggal_buat,no_telp) VALUES ('$nama','$email','$password','$jenis_kelamin','$tanggal_buat','$no_telp')";
           
                            // use exec() because no results are returned
                $conn->exec($query1);
                $respon['status'] = 1;
                echo json_encode($respon);
            }else{
                $respon['status'] = 0;
                echo json_encode($respon);

            }            
        }else if($kode == 4){
            $id_kategori = $_POST['id_kategori'];

            $query = "SELECT b.id_barang,b.nama_barang, b.harga, b.path_foto1, u.nama, u.no_telp FROM barang b INNER JOIN user u ON b.id_penjual = u.id WHERE b.id_kategori='$id_kategori' AND status='AKTIF' ";

            $stmt = $conn->prepare($query);
            $stmt->execute();
            $stmt->setFetchMode(PDO::FETCH_ASSOC);
            $result = $stmt->fetchAll();

            echo json_encode(array('ayo'=>$result));

        }else if($kode == 7){
        	$id_barang = $_POST['id_barang'];

        	$query = "SELECT b.keterangan, b.path_foto2, b.path_foto3, b.path_foto4, b.dilihat, b.stok, b.terjual FROM barang b INNER JOIN user u ON b.id_penjual = u.id WHERE b.id_barang='$id_barang' ";
        	$query1	= "UPDATE barang SET dilihat= dilihat + '1' WHERE id_barang='$id_barang'";

        	$conn->exec($query1);

        	$stmt = $conn->prepare($query);
            $stmt->execute();
            $stmt->setFetchMode(PDO::FETCH_ASSOC);
            $result = $stmt->fetchAll();

            echo json_encode(array('ayo'=>$result));
        }else if( $kode == 0){
            /*Mengembelikan keterangan barang yang akan ditampilkan pada halaman utama, aku set ada 21 jenis barang  yang akan ditampilkan(ada 1 barang yang jadi tagline, yaitu yang status barangnya diubah menjadi "taglinekomnet", barang ini akan ditampilkan pada header)
            */
            
            $query = "SELECT b.id_barang,b.nama_barang, b.harga, b.path_foto1, b.path_foto2, b.path_foto3,k.kategori,b.status, b.path_foto4, u.nama, u.no_telp FROM barang b JOIN user u ON b.id_penjual = u.id JOIN kategori k ON b.id_kategori = k.id_kategori  WHERE status='aktif' OR status='tagline' ORDER BY b.id_barang DESC LIMIT 5";

            
            $stmt = $conn->prepare($query);
            $stmt->execute();
            $stmt->setFetchMode(PDO::FETCH_ASSOC);            
            $result = $stmt->fetchAll();
            
      
            echo json_encode(array('ayo'=>$result));

            

        }else if( $kode == 8){
            $query1 = "SELECT b.id_barang,b.nama_barang, b.harga, b.path_foto1, b.path_foto2, b.path_foto3,k.kategori, b.path_foto4, u.nama, u.no_telp FROM barang b JOIN user u ON b.id_penjual = u.id JOIN kategori k ON b.id_kategori = k.id_kategori  WHERE status='AKTIF' ORDER BY liked DESC LIMIT 20";

            $stmt = $conn->prepare($query1);
            $stmt->execute();
            $stmt->setFetchMode(PDO::FETCH_ASSOC);
            $result = $stmt->fetchAll();
        
            
            echo json_encode(array('ayo'=>$result));
        }else if($kode == 9){
            $query = "SELECT id_kategori, kategori, path_foto_kategori FROM kategori";
            $stmt = $conn->prepare($query);
            $stmt->execute();
            $stmt->setFetchMode(PDO::FETCH_ASSOC);
            $result = $stmt->fetchAll();
        
            
            echo json_encode(array('ayo'=>$result));

        }
    }
}
    
catch(PDOException $e)
    {
    echo $query . "<br>" . $e->getMessage();
    }

$conn = null;
?> 




