<?php

 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$id=$_GET['id'];
$event=$_GET['event'];
$time_event=$_GET['time_event'];
$amount=$_GET['amount'];
$date_start=$_GET['date_start'];


$sql = "UPDATE event set event='$event', date_start='$date_start',time_event='$time_event',amount='$amount'  where id='$id'";

			if (mysqli_query($con,$sql))
			{
							echo "Update Successful";
            }
            
 mysqli_close($con);
	 

?>