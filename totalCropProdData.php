<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
//setting header to json
header('Content-Type: application/json');

//database
define('DB_HOST', 'localhost');
define('DB_USERNAME', 'id9522307_yen');
define('DB_PASSWORD', 'shiro');
define('DB_NAME', 'id9522307_cms');
 error_reporting(0);
  

  $username=$_SESSION['username'];
  $crop=$_GET['crop'];
  //echo $crop;
//echo $username;
//$greenhouse=$_GET['greenhouse'];
$query="";
$mysqli = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
if(!$mysqli){
  die("Connection failed: " . $mysqli->error);
}
else{

$query2=sprintf("SELECT * from user where username='$username' or email='$username'");
 $result2= $mysqli->query($query2);              
                foreach ($result2 as $row2) {
  							 $res_id=$row2['id']; 
								}
                              




	$query = sprintf("SELECT icon,amount,CONCAT(crop_name,'(',variety,')') as crop_info,season FROM event INNER JOIN crop ON event.crop_id = crop.id where crop_id='$crop' and data_status_e='current' and user_id='$res_id'");
}

$result = $mysqli->query($query);

$data = array();
foreach ($result as $row) {
  $data[] = $row;
}

$result->close();

$mysqli->close();

print json_encode($data);
//echo $data;