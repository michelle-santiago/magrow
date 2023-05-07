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
	    $record_check = "SELECT * FROM record_log WHERE data_status='current' AND user_id='$userId'";
		$result4 = mysqli_query($con, $record_check);
	    while($row4 = mysqli_fetch_array($result4)) {
	        $recordId=$row4['id'];
	   		$sql =  "update record_log set data_status='previous' WHERE data_status='current' AND user_id='$userId'";
    	    if (!mysqli_query($con,$sql))
    		{
    		    die('Error querying database' . mysqli_error($con));
    		}
        	$costrecord_check = "SELECT * FROM cost WHERE data_status='current' AND record_log_id='$recordId'";
    		$result5 = mysqli_query($con, $costrecord_check);
    	    while($row5 = mysqli_fetch_array($result5)) {
    	   		$sql =  "update cost set data_status='previous' WHERE data_status='current' AND record_log_id='$recordId'";
        	    if (!mysqli_query($con,$sql))
        		{
        		    //die('Error querying database' . mysqli_error($con));
        		    $error=1;
        		}
    		
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
        }
        
        $sql = "INSERT INTO crop (user_id,m_id,crop_name,crop,variety,season,data_status) VALUES ('$userId','$c_id','$crop_name','$crop','$variety','$season','current')";
    
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
                if($col3=="yph"){
                    $yph=$value3;
                }
                if($col3=="yph_m"){
                    $yph_m=$value3;
                }
            
        }
        $record_check = "SELECT * FROM crop WHERE m_id='$r_cid' AND data_status='current' AND user_id='$userId'";
		$result4 = mysqli_query($con, $record_check);
	    while($row4 = mysqli_fetch_array($result4)) {
	    $cropId=$row4['id'];
        $sql = "INSERT INTO record_log (user_id,crop_id,m_id,m_crop_id,area_harvested,ah_measurement,yields_per_hectare,yph_measurement,data_status) VALUES ('$userId','$cropId','$r_id','$r_cid','$area','$ah_m','$yph','$yph_m','current')";
    
        	if(!mysqli_query($con,$sql))
        	{	
                //die('Error querying database' . mysqli_error($con));
                $error=1;
        	}
	    }
    }
    }    
    else if($key=="CostRecord"){
    foreach($arrays as $array){
        foreach($array as $col4 => $value4){
                if($col4=="cid"){
                    $cid=$value4;
                }
                if($col4=="c_rid"){
                    $c_rid=$value4;
                }
                if($col4=="seeds"){
                    $seeds=$value4;
                }
                if($col4=="irrig_fee"){
                    $irrig_fee=$value4;
                }
                if($col4=="fertilizer"){
                    $fertilizer=$value4;
                }
                if($col4=="pesticides"){
                    $pesticides=$value4;
                }
                if($col4=="labor"){
                    $labor=$value4;
                }
                if($col4=="rental"){
                    $rental=$value4;
                }
                if($col4=="transport"){
                    $transport=$value4;
                }
                if($col4=="other"){
                    $other=$value4;
                }
            
        }
        $record_check = "SELECT * FROM record_log WHERE m_id='$c_rid' AND data_status='current' AND user_id='$userId'";
		$result4 = mysqli_query($con, $record_check);
	    while($row4 = mysqli_fetch_array($result4)) {
	    $rId=$row4['id'];
	    }
        $sql = "INSERT INTO cost(record_log_id,m_id,m_record_log_id,seeds,irrigation_fee,fertilizer,pesticides,labor,rental,transport,other,data_status) VALUES ('$rId','$cid','$c_rid','$seeds','$irrig_fee','$fertilizer','$pesticides','$labor','$rental','$transport','$other','current')";
    
        	if(!mysqli_query($con,$sql))
        	{	
                //die('Error querying database' . mysqli_error($con));
                $error=1;
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