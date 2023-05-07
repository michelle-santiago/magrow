<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$id=$_GET['id'];
$status=$_GET['status'];


if($status=="1")
{
    $new_stats="0";
}
else
{
    $new_stats="1";
}

$sql = "UPDATE user set status='$new_stats' where id='$id'";

			if (!mysqli_query($con,$sql))
							{
							echo "failed";
							}
 mysqli_close($con);
	 

?>