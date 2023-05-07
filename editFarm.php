<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$id=$_GET['id'];
$farm_name=$_GET['farm_name'];
$location=$_GET['location'];
$land_area=$_GET['land_area'];
$unit=$_GET['unit'];


$sql = "UPDATE farm set name='$farm_name', location='$location',land_area='$land_area',la_measurement='$unit'  where id='$id'";

			if (mysqli_query($con,$sql))
			{
							echo "Update Successful";
            }
            
 mysqli_close($con);
	 

?>