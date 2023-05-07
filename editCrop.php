<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$id=$_GET['id'];
$type=$_GET['type'];
$variety=$_GET['variety'];
$crop_name=$_GET['crop_name'];
$season=$_GET['season'];


$sql = "UPDATE crop set crop='$type', variety='$variety',crop_name='$crop_name',season='$season'  where id='$id'";

			if (mysqli_query($con,$sql))
			{
							echo "Update Successful";
            }
            
 mysqli_close($con);
	 

?>