<?php
require_once('dbConnect.php');
if($_SERVER['REQUEST_METHOD']=='POST') {

  $search = $_POST['search'];
  $sql = "SELECT * FROM transaksi where nama LIKE '%$search%' ORDER BY nama ASC";
  $res = mysqli_query($con,$sql);
  $result = array();
  while($row = mysqli_fetch_array($res)){
    array_push($result, array('nim'=>$row[1], 'nama'=>$row[2], 'kelas'=>$row[3], 'sesi'=>$row[4]));
  }
  echo json_encode(array("value"=>1,"mahasiswaList"=>$result));
  mysqli_close($con);
}