<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 


$id=$_GET['id'];

$sql = "DELETE from record_log  where id='$id'";

			if (mysqli_query($con,$sql))
			{
							echo "Delete Successful";
            }
            
 mysqli_close($con);

?>