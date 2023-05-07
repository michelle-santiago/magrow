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

$unit_other="";    
if($row['la_measurement']=="m²")
{
    $unit_other="ha";
}    
else
{
    $unit_other="m²";
}


echo " <div class='modal-body'>";
echo "<form>";
echo "<div class='form-group'>";
echo "<label>Farm Name</label>";
echo "<input type='text' class='form-control' id='farm_name_edit' placeholder='Enter farm name' value='".$row['name']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Location</label>";
echo "<input type='text' class='form-control' id='location_edit' placeholder='Enter location' value='".$row['location']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Land Area</label>";
echo "<input type='number' class='form-control' id='land_area_edit' placeholder='Enter land area' value='".$row['land_area']."'>";
echo "</div>";



echo "<div class='form-group'>";
echo "<label>Unit</label>";
echo "<select class='form-control' id='unit_edit'>";
echo "<option>".$row['la_measurement']."</option>";
echo "<option>".$unit_other."</option>";
echo "</select>";
echo "</div>";
echo "<small id='edit_farm_res' class='form-text text-muted'></small>";
echo "</form>";
echo "</div>";

echo "<div class='modal-footer'>";
echo "<button type='button' class='btn btn-warning shadow' onclick='editFarm2(".$row['id'].",".$offset.",".$no_of_records_per_page.")'>Confirm</button>";
echo "</div>";

}
 mysqli_close($con);
	 



?>