<?php
include('connection.php');
     
$data = file_get_contents('php://input');
$j = json_decode($data , true);
$error=0;
if ($_SERVER['REQUEST_METHOD'] === 'POST') 
{
foreach($j as $key => $arrays){
    if($key=="Username"){
        $username=$arrays;
        $user_check_query1 = "SELECT * FROM user WHERE username='$username'";
        $result1 = mysqli_query($con, $user_check_query1);
        while($row1 = mysqli_fetch_array($result1)) {
        $userId=$row1['id'];
        }   
        $farm_record="none"; 
        $sql ="delete from farm  WHERE data_status='previous' AND user_id='$userId'";
        if (!mysqli_query($con,$sql))
        {
            //die('Error querying database' . mysqli_error($con));
            $error=1;
        }
        $sql ="delete from crop WHERE data_status='previous' AND user_id='$userId'";
        if (!mysqli_query($con,$sql))
        {
            //die('Error querying database' . mysqli_error($con));
            $error=1;
        }
        $farm_check = "SELECT * FROM farm WHERE data_status='current' AND user_id='$userId'";
        $result2 = mysqli_query($con, $farm_check);
        while($row2 = mysqli_fetch_array($result2)) {
            $farm_record="with";
            $sql =  "update farm set data_status='previous' WHERE data_status='current' AND user_id='$userId'";
            if (!mysqli_query($con,$sql))
            {
                //die('Error querying database' . mysqli_error($con));
                $error=1;
            }

        }
        $crop_check = "SELECT * FROM crop WHERE data_status='current' AND user_id='$userId'";
        $result3 = mysqli_query($con, $crop_check);
        while($row3 = mysqli_fetch_array($result3)) {
            $sql =  "update crop set data_status='previous' WHERE data_status='current' AND user_id='$userId'";
            if (!mysqli_query($con,$sql))
            {
                //die('Error querying database' . mysqli_error($con));
                $error=1;
            }
        }
        $record_check = "SELECT * FROM record_log WHERE data_status_r='current' AND user_id_r='$userId'";
        $result4 = mysqli_query($con, $record_check);
        while($row4 = mysqli_fetch_array($result4)) {
            $recordId=$row4['id'];
            $sql =  "update record_log set data_status_r='previous' WHERE data_status_r='current' AND user_id_r='$userId'";
            if (!mysqli_query($con,$sql))
            {
                die('Error querying database' . mysqli_error($con));
            }
   
        }
           $costrecord_check = "SELECT * FROM event WHERE data_status_e='current' AND user_id_e='$userId'";
            $result5 = mysqli_query($con, $costrecord_check);
            while($row5 = mysqli_fetch_array($result5)) {
                $sql =  "update event set data_status_e='previous' WHERE data_status_e='current' AND user_id_e='$userId'";
                if (!mysqli_query($con,$sql))
                {
                    //die('Error querying database' . mysqli_error($con));
                    $error=1;
                }
            
        }
    }
    else if($key=="Farm"){
    foreach($arrays as $col => $value){
           if($col=="fname"){
               $name=$value;
           }
           if($col=="flocation"){
               $location=$value;
              
           }
           if($col=="farea"){
               $area=$value;
           }
           if($col=="fmeasure"){
               $measurement=$value;
           }
          
        } 
         if($farm_record=="none"){
                $sql = "INSERT INTO farm (user_id,name,location,land_area,la_measurement,data_status) VALUES ('$userId','$name','$location','$area','$measurement','current')";
    
                if(!mysqli_query($con,$sql))
                {   
                  //die('Error querying database' . mysqli_error($con));
                  $error=1;
                }
           
           }
       else if($farm_record=="with"){
                $sql = "update farm set name='$name',location='$location',land_area='$area',la_measurement='$measurement',data_status='current' WHERE data_status='previous' AND user_id='$userId'";
                if(!mysqli_query($con,$sql))
                {   
                   // die('Error querying database' . mysqli_error($con));
                   $error=1;
                }
           }
    }
    else if($key=="Crop"){
    foreach($arrays as $array){
        
        foreach($array as $col2 => $value2){
           
                if($col2=="c_id"){
                    $c_id=$value2;
                }
                if($col2=="crop_name"){
                    $crop_name=$value2;
                }
                if($col2=="crop"){
                    $crop=$value2;
                }
                if($col2=="variety"){
                    $variety=$value2;
                }
                if($col2=="season"){
                    $season=$value2;
                }
                if($col2=="date_added"){
                    $date_added=$value2;
                }
                if($col2=="privacy"){
                    $privacy=$value2;
                }
        }
        
        $sql = "INSERT INTO crop (user_id,m_id,crop_name,crop,variety,season,data_status,date_added,privacy) VALUES ('$userId','$c_id','$crop_name','$crop','$variety','$season','current','$date_added','$privacy')";
    
        if(!mysqli_query($con,$sql))
        {   
            //die('Error querying database' . mysqli_error($con));
            $error=1;
        }
    }
    }
    else if($key=="Record"){
    foreach($arrays as $array){
        foreach($array as $col3 => $value3){
                if($col3=="r_id"){
                    $r_id=$value3;
                }
                if($col3=="r_cid"){
                    $r_cid=$value3;
                }
                if($col3=="area"){
                    $area=$value3;
                }
                if($col3=="ah_m"){
                    $ah_m=$value3;
                }

                if($col3=="area_planted"){
                    $area_planted=$value3;
                }
                if($col3=="ap_m"){
                    $ap_m=$value3;
                }
                if($col3=="no_of_cavans"){
                    $no_of_cavans=$value3;
                }
                if($col3=="weight"){
                    $weight=$value3;
                }
            
        }
        $record_check = "SELECT * FROM crop WHERE m_id='$r_cid' AND data_status='current' AND user_id='$userId'";
        $result4 = mysqli_query($con, $record_check);
        while($row4 = mysqli_fetch_array($result4)) {
        $cropId=$row4['id'];
        $sql = "INSERT INTO record_log (user_id_r,crop_id,m_id,m_crop_id,area_harvested,ah_measurement,area_planted,ap_measurement,no_of_cavans_harvested,weight_per_cavan,data_status_r) VALUES ('$userId','$cropId','$r_id','$r_cid','$area','$ah_m','$area_planted','$ap_m','$no_of_cavans','$weight','current')";
    
            if(!mysqli_query($con,$sql))
            {   
                //die('Error querying database' . mysqli_error($con));
                $error=1;
            }
        }
    }
    }    
    else if($key=="Event"){
    foreach($arrays as $array){
        foreach($array as $col4 => $value4){
                if($col4=="eid"){
                    $eid=$value4;
                }
                if($col4=="ecid"){
                    $ecid=$value4;
                }
                if($col4=="date_time"){
                    $date_time=$value4;
                }
                if($col4=="color"){
                    $color=$value4;
                }
                if($col4=="event"){
                    $event=$value4;
                }
                if($col4=="date_start"){
                    $date_start=$value4;
                }
                if($col4=="time_event"){
                    $time_event=$value4;
                }
                if($col4=="date_id"){
                    $date_id=$value4;
                }
                if($col4=="icon"){
                    $icon=$value4;
                }
                if($col4=="operation_id"){
                    $operation_id=$value4;
                }
                if($col4=="operation_status"){
                    $operation_status=$value4;
                }
                if($col4=="priority"){
                    $priority=$value4;
                }
                if($col4=="amount"){
                    $amount=$value4;
                }
                
            
        }
        $event_check = "SELECT * FROM crop WHERE m_id='$ecid' AND data_status='current' AND user_id='$userId'";
        $result4 = mysqli_query($con, $event_check);
        while($row4 = mysqli_fetch_array($result4)) {
        $cropId=$row4['id'];
        
        $sql = "INSERT INTO event(user_id_e,crop_id,m_id,m_crop_id,date_time,color,event,date_start,time_event,date_id,icon,operation_id,operation_status,priority,amount,data_status_e) VALUES ('$userId','$cropId','$eid','$ecid','$date_time','$color','$event','$date_start','$time_event','$date_id','$icon','$operation_id','$operation_status','$priority','$amount','current')";
    
            if(!mysqli_query($con,$sql))
            {   
                //die('Error querying database' . mysqli_error($con));
                $error=1;
            }
        
    }
    }
    }  
}
if($error==1){
    echo "An error occurred while syncing. Please Try again";
}
else{
    echo "Success";
}
}

?>