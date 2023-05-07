<?php
 session_start(); 

  if (!isset($_SESSION['username'])) {
  header('location:login.php');
  }
include('connection.php'); 

$id=$_GET['id'];
$offset=$_GET['offset'];
$no_of_records_per_page=$_GET['no_of_records_per_page'];

$query = "SELECT * from crop where id='$id'";
$results = mysqli_query($con, $query);
while($row=mysqli_fetch_array($results)){

$type_other="";    
if($row['crop']=="rice")
{
    $type_other="onion";
}    
else
{
    $type_other="rice";
}

$season_other="";    
if($row['season']=="dry")
{
    $season_other="wet";
}    
else
{
    $season_other="dry";
}

echo " <div class='modal-body'>";
echo "<form>";
echo "<div class='form-group'>";
echo "<label>Type</label>";
echo "<select class='form-control' id='type_edit'>";
echo "<option>".$row['crop']."</option>";
echo "<option>".$type_other."</option>";
echo "</select>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Variety</label>";
echo "<input type='text' class='form-control' id='variety_edit' placeholder='Enter variety' value='".$row['variety']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Name</label>";
echo "<input type='text' class='form-control' id='crop_name_edit' placeholder='Enter crop name' value='".$row['crop_name']."'>";
echo "</div>";

echo "<div class='form-group'>";
echo "<label>Season</label>";
echo "<select class='form-control' id='season_edit'>";
echo "<option>".$row['season']."</option>";
echo "<option>".$season_other."</option>";
echo "</select>";
echo "</div>";
echo "<small id='edit_crop_res' class='form-text text-muted'></small>";
echo "</form>";
echo "</div>";

echo "<div class='modal-footer'>";
echo "<button type='button' class='btn btn-warning shadow' onclick='editCrop2(".$row['id'].",".$offset.",".$no_of_records_per_page.")'>Confirm</button>";
echo "</div>";

}
 mysqli_close($con);
	 

?>