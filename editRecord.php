<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$id=$_GET['id'];
$area_harvested=$_GET['area_harvested'];
$area_planted=$_GET['area_planted'];
$no_of_cavans_harvested=$_GET['no_of_cavans_harvested'];
$weight_per_cavan=$_GET['weight_per_cavan'];

$unit1=$_GET['unit1'];
$unit2=$_GET['unit2'];


$sql = "UPDATE record_log set area_harvested='$area_harvested', ah_measurement='$unit1',area_planted='$area_planted',ap_measurement='$unit2',no_of_cavans_harvested='$no_of_cavans_harvested',weight_per_cavan='$weight_per_cavan'  where id='$id'";

			if (mysqli_query($con,$sql))
			{
							echo "Update Successful";
            }
            
 mysqli_close($con);
	 

?>