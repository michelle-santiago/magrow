<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$id=$_GET['id'];
$offset=$_GET['offset'];
$no_of_records_per_page=$_GET['no_of_records_per_page'];

$query = "SELECT * from event where id='$id'";
$results = mysqli_query($con, $query);
while($row=mysqli_fetch_array($results)){


echo " <div class='modal-body'>";
echo "<form>";
echo "<div class='form-group'>";
echo "<label>Activity</label>";
echo "<input type='text' class='form-control' id='event_edit' placeholder='Enter farm name' value='".$row['event']."'>";
echo "</div>";


echo "<div class='form-group'>";
echo "<label>Date</label>";
echo "<input type='text' class='form-control' id='date_start_edit' placeholder='Enter land area' value='".$row['date_start']."'>";
echo "</div>";


echo "<div class='form-group'>";
echo "<label>Time</label>";
echo "<input type='text' class='form-control' id='time_event_edit' placeholder='Enter land area' value='".$row['time_event']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Amount</label>";
echo "<input type='number' class='form-control' id='amount_edit' placeholder='Enter land area' value='".$row['amount']."'>";
echo "<small id='edit_event_res' class='form-text text-muted'></small>";
echo "</div>";
echo "</form>";

echo "<div class='modal-footer'>";
echo "<button type='button' class='btn btn-warning shadow' onclick='editEvent(".$row['id'].",".$offset.",".$no_of_records_per_page.")'>Confirm</button>";
echo "</div>";


}
 mysqli_close($con);
	 



?>