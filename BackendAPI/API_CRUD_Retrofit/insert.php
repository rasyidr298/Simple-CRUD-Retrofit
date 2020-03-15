<?php

if($_SERVER['REQUEST_METHOD']=='POST') {

   $response = array();
   //mendapatkan data
   $nim = $_POST['nim'];
   $nama = $_POST['nama'];
   $kelas = $_POST['kelas'];
   $sesi = $_POST['sesi'];

   require_once('dbConnect.php');
   //Cek nim sudah terdaftar apa belum
   $sql = "SELECT * FROM transaksi WHERE nim ='$nim'";
   $check = mysqli_fetch_array(mysqli_query($con,$sql));
   if(isset($check)){
     $response["value"] = 0;
     $response["message"] = "oops! NIM sudah terdaftar!";
     echo json_encode($response);
   } else {
     $sql = "INSERT INTO transaksi (nim,nama,kelas,sesi) VALUES('$nim','$nama','$kelas','$sesi')";
     if(mysqli_query($con,$sql)) {
       $response["value"] = 1;
       $response["message"] = "Sukses mendaftar";
       echo json_encode($response);
     } else {
       $response["value"] = 0;
       $response["message"] = "oops! Coba lagi!";
       echo json_encode($response);
     }
   }
   // tutup database
   mysqli_close($con);
} else {
  $response["value"] = 0;
  $response["message"] = "oops! Coba lagi!";
  echo json_encode($response);
}