<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$id=$_GET['id'];
$offset=$_GET['offset'];
$no_of_records_per_page=$_GET['no_of_records_per_page'];

$query = "SELECT * from farm where id='$id'";
$results = mysqli_query($con, $query);
while($row=mysqli_fetch_array($results)){


echo "<div class='form-group'><br>";
echo "<label>&nbsp;&nbsp;&nbsp;&nbsp;Are you sure you want to delete this farm?</label>";
echo "</div>";

echo "<div class='modal-footer'>";
echo "<button type='button' class='btn btn-warning shadow' data-dismiss='modal'>No</button>";
echo "<button type='button' class='btn btn-danger shadow' onclick='removeFarm2(".$row['id'].",".$offset.",".$no_of_records_per_page.")'>Yes</button>";
echo "</div>";

}
 mysqli_close($con);
	 

?>