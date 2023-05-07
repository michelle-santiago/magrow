<?php 

include('connection.php'); 

$id=$_GET['id'];
$field=$_GET['field'];
$str_query="";
if($field=='crops')
{
$str_query="SELECT crop as TYPE,VARIETY, CROP_NAME, SEASON, DATE_ADDED from crop where user_id='$id' and data_status='current'";	
}
else if($field=='farms')
{
$str_query="SELECT name as FARM_NAME, LOCATION,LAND_AREA,la_measurement as UNIT from farm where user_id='$id' and data_status='current'";	
}
else if($field=='records')
{
$str_query="SELECT CROP_NAME,crop as TYPE,VARIETY,AREA_HARVESTED,ah_measurement as UNIT,AREA_PLANTED,ap_measurement as UNIT,no_of_cavans_harvested as NO_OF_SACKS,weight_per_cavan as TOTAL_WEIGHT from crop,record_log where crop.id=record_log.crop_id and record_log.user_id_r='$id' and record_log.data_status_r='current'";	
}
else if($field=='events')
{
$str_query="SELECT CROP_NAME,crop as TYPE,VARIETY,EVENT,DATE_START,TIME_EVENT,AMOUNT from crop,event where crop.id=event.crop_id and event.user_id_e='$id' and  event.data_status_e='current'";	
}



$result = $con->query($str_query);
if (!$result) die('Couldn\'t fetch records');
$num_fields = mysqli_num_fields($result);
$headers = array();
for ($i = 0; $i < $num_fields; $i++) {
    $headers[] = mysqli_field_name($result , $i);
}
$fp = fopen('php://output', 'w');
if ($fp && $result) {
    header('Content-Type: text/csv');
    header('Content-Disposition: attachment; filename="'.$field.' export.csv"');
    header('Pragma: no-cache');
	header('Expires: 0');
	


    fputcsv($fp, $headers);
    while ($row = $result->fetch_array(MYSQLI_NUM)) {
        fputcsv($fp, array_values($row));
	}
	
    die;
}



function mysqli_field_name($result, $field_offset)
{
    $properties = mysqli_fetch_field_direct($result, $field_offset);
    return is_object($properties) ? $properties->name : null;
}
	
	
	?>