<?php

//setting header to json
header('Content-Type: application/json');


//database
define('DB_HOST', 'localhost');
define('DB_USERNAME', 'id9522307_yen');
define('DB_PASSWORD', 'shiro');
define('DB_NAME', 'id9522307_cms');

error_reporting(0);
  
session_start(); 
$username=$_SESSION['username'];
$crop=$_GET['crop'];
$date_start=$_GET['date_start'];
$end_date=$_GET['end_date'];
$variety=$_GET['variety'];
$query="";

$mysqli = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
if(!$mysqli){
  die("Connection failed: " . $mysqli->error);
}
else{

  
   if($variety=="all"){                          
	   $query = sprintf("SELECT area_planted,ah_measurement,area_harvested,ap_measurement,no_of_cavans_harvested,weight_per_cavan as total_weight,CONCAT(crop_name,'(',variety,')') as crop_info,season,weight_per_cavan/no_of_cavans_harvested as ave_weight,date_added FROM record_log INNER JOIN crop ON record_log.crop_id = crop.id where date_added between '$date_start' and '$end_date' and crop='$crop' and data_status_r='current' and privacy=1");
  }
  else if($variety!="other"&&$variety!="all"){
  $query = sprintf("SELECT area_planted,ah_measurement,area_harvested,ap_measurement,no_of_cavans_harvested,weight_per_cavan as total_weight,CONCAT(crop_name,'(',variety,')') as crop_info,season,weight_per_cavan/no_of_cavans_harvested as ave_weight,date_added FROM record_log INNER JOIN crop ON record_log.crop_id = crop.id where date_added between '$date_start' and '$end_date' and crop='$crop' and data_status_r='current' and variety='$variety' and privacy=1");
  }
  else if($variety=="other"){

      if($crop=="rice"){
       
      $query = sprintf("SELECT area_planted,ah_measurement,area_harvested,ap_measurement,no_of_cavans_harvested,weight_per_cavan as total_weight,CONCAT(crop_name,'(',variety,')') as crop_info,season,weight_per_cavan/no_of_cavans_harvested as ave_weight,date_added FROM record_log INNER JOIN crop ON record_log.crop_id = crop.id where date_added between '$date_start' and '$end_date' and crop='$crop' and data_status_r='current' and variety!='NSIC Rc9' and variety!='NSIC Rc14' and variety!='PSB Rc18' and variety!='PSB Rc68' and variety!='NSIC Rc194' and variety!='NSIC Rc222' and variety!='NSIC Rc300' and variety!='NSIC Rc160' and variety!='NSIC Rc238' and variety!='NSIC Rc216' and variety!='NSIC Rc402' and variety!='NSIC Rc354' and variety!='NSIC Rc218' and variety!='PSB Rc10' and privacy=1");    
  }
  else if($crop=="onion"){
   
      $query = sprintf("SELECT area_planted,ah_measurement,area_harvested,ap_measurement,no_of_cavans_harvested,weight_per_cavan as total_weight,CONCAT(crop_name,'(',variety,')') as crop_info,season,weight_per_cavan/no_of_cavans_harvested as ave_weight,date_added FROM record_log INNER JOIN crop ON record_log.crop_id = crop.id where date_added between '$date_start' and '$end_date' and crop='$crop' and data_status_r='current' and variety!='BGS 95' and variety!='Cal 120' and variety!='Cal 202' and variety!='Capri' and variety!='CX-12' and variety!='Grannex 429' and variety!='Hybrid Red Orient' and variety!='Liberty' and variety!='Red Creole' and variety!='Red Pinoy' and variety!='Rio Bravo' and variety!='Rio Hondo' and variety!='Rio Raji Red' and variety!='Rio Tinto' and variety!='Super Pinoy' and variety!='SuperX' and variety!='Texas Grano' and variety!='Yellow Grannex' and privacy=1");  
    
    }
  }

  
  
}

$result = $mysqli->query($query);

$data = array();
foreach ($result as $row) {
  $data[] = $row;
}

$result->close();

$mysqli->close();

print json_encode($data);