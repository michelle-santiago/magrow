<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$id=$_GET['id'];
$offset=$_GET['offset'];
$no_of_records_per_page=$_GET['no_of_records_per_page'];

$query = "SELECT * from record_log where id='$id'";
$results = mysqli_query($con, $query);
while($row=mysqli_fetch_array($results)){

$unit1="";
$unit2="";

if($row['ah_measurement']=="Ha")
{
$unit1="m²";
}

if($row['ap_measurement']=="Ha")
{
$unit2="m²";
}

echo " <div class='modal-body'>";
echo "<form>";
echo "<div class='form-group'>";
echo "<label>Area Harvested</label>";
echo "<input type='text' class='form-control' id='area_harvested_edit' placeholder='Enter farm name' value='".$row['area_harvested']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Unit</label>";
echo "<select class='form-control' id='ah_measurement_edit'>";
echo "<option>".$row['ah_measurement']."</option>";
echo "<option>".$unit1."</option>";
echo "</select>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Area Planted</label>";
echo "<input type='text' class='form-control' id='area_planted_edit' placeholder='Enter land area' value='".$row['area_planted']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Unit</label>";
echo "<select class='form-control' id='ap_measurement_edit'>";
echo "<option>".$row['ap_measurement']."</option>";
echo "<option>".$unit2."</option>";
echo "</select>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>No. of Cavans</label>";
echo "<input type='number' class='form-control' id='no_of_cavans_harvested_edit' placeholder='Enter land area' value='".$row['no_of_cavans_harvested']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Weight</label>";
echo "<input type='number' class='form-control' id='weight_per_cavan_edit' placeholder='Enter land area' value='".$row['weight_per_cavan']."'>";
echo "<small id='edit_record_res' class='form-text text-muted'></small>";
echo "</div>";
echo "</form>";

echo "<div class='modal-footer'>";
echo "<button type='button' class='btn btn-warning shadow' onclick='editRecord(".$row['id'].",".$offset.",".$no_of_records_per_page.")'>Confirm</button>";
echo "</div>";


}
 mysqli_close($con);
	 



?>