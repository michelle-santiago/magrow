<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$username=$_GET['username'];


$query = "SELECT * from user where username='$username' or email='$username'";
$results = mysqli_query($con, $query);
while($row=mysqli_fetch_array($results)){

    
echo " <div class='modal-body'>";
echo "<form>";
echo "<div class='form-group'>";
echo "<label>Name</label>";
echo "<input type='text' class='form-control' id='name_edit' placeholder='Enter Name' value='".$row['name']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Username</label>";
echo "<input type='text' class='form-control' id='username_edit' placeholder='Enter username' value='".$row['username']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Email</label>";
echo "<input type='text' class='form-control' id='email_edit' placeholder='Enter email' value='".$row['email']."'>";
echo "</div>";
echo "<small id='edit_account_res' class='form-text text-muted'></small>";
echo "</form>";
echo "</div>";

echo "<div class='modal-footer'>";
echo "<button type='button' class='btn btn-secondary shadow' onclick='showPasswordPane(".$row['id'].")'>Change Password</button>";
echo "<button type='button' class='btn btn-warning shadow' onclick='editProfile(".$row['id'].")'>Confirm</button>";
echo "</div>";

}
 mysqli_close($con);
	 

?>