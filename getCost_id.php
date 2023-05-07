<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 
$id=$_GET['id'];
$record_id=0;
$cost_id=0;

$crop_type="";
$crop_variety="";
$season="";

$area_harvested="";
$ah_measurement="";
$yields_per_hectare="";
$yph_measurement="";



  $query1 = "SELECT * FROM crop WHERE id='$id'";
    $results1 = mysqli_query($con, $query1);
    while($row1=mysqli_fetch_array($results1)){

		$crop_type=$row1['crop'];
		$crop_variety=$row1['variety'];
		$season=$row1['season'];

    }
	
	echo $crop_type."#".$crop_variety."#".$season;



?>