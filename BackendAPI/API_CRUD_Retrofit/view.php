<?php
require_once('dbConnect.php');
if($_SERVER['REQUEST_METHOD']=='GET') {
  $sql = "SELECT * FROM transaksi ORDER BY id ASC";
  $res = mysqli_query($con,$sql);
  $result = array();
  while($row = mysqli_fetch_array($res)){
    array_push($result, array('id'=>$row[0], 'nim'=>$row[1], 'nama'=>$row[2], 'kelas'=>$row[3], 'sesi'=>$row[4]));
  }
  echo json_encode(array("value"=>1,"mahasiswaList"=>$result));
  mysqli_close($con);
}