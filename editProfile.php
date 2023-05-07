<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 




$id=$_GET['id'];
$name=$_GET['name'];
$username=$_GET['username'];
$email=$_GET['email'];


$_SESSION['username'] = $username;
$_SESSION['id'] = $id;
$_SESSION['name'] = $name;

$sql = "UPDATE user set name='$name', username='$username',email='$email'  where id='$id'";

			if (mysqli_query($con,$sql))
			{
							echo "Update Successful";
            }
            
 mysqli_close($con);
	 

?>