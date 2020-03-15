 <?php
 define('HOST','localhost');
 define('PASS','');
 define('USER','root');
 define('DB','crud_retro_trial');

 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');