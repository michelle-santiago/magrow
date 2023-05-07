<?php 

include('connection.php'); 

$id=$_GET['id'];
$crop_type=$_GET['crop_type'];
$crop_variety=$_GET['crop_variety'];
$season=$_GET['season'];




$result = $con->query("SELECT EVENT as ACTIVITY,DATE_START as DATE,TIME_EVENT as TIME,AMOUNT FROM event where crop_id='$id' and data_status_e='current'");
if (!$result) die('Couldn\'t fetch records');
$num_fields = mysqli_num_fields($result);
$headers = array();
for ($i = 0; $i < $num_fields; $i++) {
    $headers[] = mysqli_field_name($result , $i);
}
$fp = fopen('php://output', 'w');
if ($fp && $result) {
    header('Content-Type: text/csv');
    header('Content-Disposition: attachment; filename="'.$crop_variety.' export.csv"');
    header('Pragma: no-cache');
	header('Expires: 0');
	
	$list = array (
		array('CROP INFORMATION'),
		//array('Crop Name',$name),
		array('Type',$crop_type),
		array('Variety',$crop_variety),
		array('Season',$season),
		array(' ',' '),
	);
	
	
	foreach ($list as $fields) {
		fputcsv($fp, $fields);
	}
	
	$query4 = "SELECT area_harvested,ah_measurement,area_planted,ap_measurement,no_of_cavans_harvested,weight_per_cavan FROM record_log where crop_id='$id'";
	$result4 = mysqli_query($con,$query4);
	while($row4 = mysqli_fetch_row($result4)) {
	
	$list = array (
	array('RECORD INFORMATION'),
    array('Area Harvested',$row4[0]." ".$row4[1]),
    array('Area Planted',$row4[2]." ".$row4[3]),	
    array('No. of Cavans',$row4[4]),
    array('Weight',$row4[5]),
	array(''),	
);

foreach ($list as $fields) {
    fputcsv($fp, $fields);
}}

    fputcsv($fp, $headers);
    while ($row = $result->fetch_array(MYSQLI_NUM)) {
        fputcsv($fp, array_values($row));
	}
	
	$query1 = "SELECT SUM(amount) FROM event where crop_id='$id'";
	$result3 = mysqli_query($con,$query1);
	while($row3 = mysqli_fetch_row($result3)) {
	
	$list = array (
	array(''),
    array('TOTAL COST','P '.$row3[0]),
);

foreach ($list as $fields) {
    fputcsv($fp, $fields);
}}





    die;
}



function mysqli_field_name($result, $field_offset)
{
    $properties = mysqli_fetch_field_direct($result, $field_offset);
    return is_object($properties) ? $properties->name : null;
}
	
	
	?>