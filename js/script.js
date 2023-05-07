function exportTables(id,field)
{
window.location.href='export_tables.php?id='+id+"&field="+field;
}

function showEvent2(id)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBody").innerHTML=this.responseText;
			$("#editFarmModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showEvent2.php?id="+id,true);
		xmlhttp.send();
}

function showEvent(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBody").innerHTML=this.responseText;
			$("#editFarmModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showEvent.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}


function editEvent2(id){
	
	var event=document.getElementById("event_edit").value;
	var date_start=document.getElementById("date_start_edit").value;
	var time_event=document.getElementById("time_event_edit").value;
	var amount=document.getElementById("amount_edit").value;


	if(event==""||date_start==""||time_event==""||amount=="")
	{
		document.getElementById("edit_event_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("edit_event_res").innerHTML ="";
			document.getElementById("response").innerHTML=this.responseText;
			$("#editFarmModal").modal("hide")
			$("#responseModal").modal("show")
			showEvents2();


			
		}
	}
		xmlhttp.open("GET","editEvent.php?id="+id+"&event="+event+"&time_event="+time_event+"&amount="+amount+"&date_start="+date_start,true);
		xmlhttp.send();
		
	}
}

function editEvent(id,offset,no_of_records_per_page){
	
	var event=document.getElementById("event_edit").value;
	var date_start=document.getElementById("date_start_edit").value;
	var time_event=document.getElementById("time_event_edit").value;
	var amount=document.getElementById("amount_edit").value;


	if(event==""||date_start==""||time_event==""||amount=="")
	{
		document.getElementById("edit_event_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("edit_event_res").innerHTML ="";
			document.getElementById("response").innerHTML=this.responseText;
			$("#editFarmModal").modal("hide")
			$("#responseModal").modal("show")
			showEvents(offset,no_of_records_per_page);

			
		}
	}
		xmlhttp.open("GET","editEvent.php?id="+id+"&event="+event+"&time_event="+time_event+"&amount="+amount+"&date_start="+date_start,true);
		xmlhttp.send();
		
	}
}

function showEvents(offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("user_table4").innerHTML=this.responseText;
	
			
		}
	}
		xmlhttp.open("GET","showEvents.php?offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
	
}

function showEvents2()
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   

			document.getElementById("user_table4").innerHTML=this.responseText;
			location.reload()
			//var table = $('#table_container2').DataTable();
			//table.ajax.reload(null,false);
			
		}
	}
		xmlhttp.open("GET","showEvents2.php",true);
		xmlhttp.send();
	
}

function removeEvent(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("response").innerHTML=this.responseText;
			$("#deleteModal").modal("hide")
			$("#responseModal").modal("show")
			showEvents(offset,no_of_records_per_page);
			
		}
	}
		xmlhttp.open("GET","deleteOneEvent.php?id="+id,true);
		xmlhttp.send();
}

function removeEvent2(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("response").innerHTML=this.responseText;
			$("#deleteModal").modal("hide")
			$("#responseModal").modal("show")
			showEvents2(offset,no_of_records_per_page);
			
		}
	}
		xmlhttp.open("GET","deleteOneEvent.php?id="+id,true);
		xmlhttp.send();
}

function deleteEvent2(id)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyDelete").innerHTML=this.responseText;
			$("#deleteModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","askDeleteEvent2.php?id="+id,true);
		xmlhttp.send();
}

function deleteEvent(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyDelete").innerHTML=this.responseText;
			$("#deleteModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","askDeleteEvent.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}


function showRecord2(id)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBody").innerHTML=this.responseText;
			$("#editFarmModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showRecord2.php?id="+id,true);
		xmlhttp.send();
}

function showRecord(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBody").innerHTML=this.responseText;
			$("#editFarmModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showRecord.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}



function removeRecord(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("response").innerHTML=this.responseText;
			$("#deleteModal").modal("hide")
			$("#responseModal").modal("show")
			showRecords(offset,no_of_records_per_page);
			
		}
	}
		xmlhttp.open("GET","deleteOneRecord.php?id="+id,true);
		xmlhttp.send();
}

function removeRecord2(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("response").innerHTML=this.responseText;
			$("#deleteModal").modal("hide")
			$("#responseModal").modal("show")
			showRecords2();
			
		}
	}
		xmlhttp.open("GET","deleteOneRecord.php?id="+id,true);
		xmlhttp.send();
}

function deleteRecord2(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyDelete").innerHTML=this.responseText;
			$("#deleteModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","askDeleteRecord2.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}


function deleteRecord(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyDelete").innerHTML=this.responseText;
			$("#deleteModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","askDeleteRecord.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}

function editRecord2(id){
	
	var area_harvested=document.getElementById("area_harvested_edit").value;
	var area_planted=document.getElementById("area_planted_edit").value;
	var no_of_cavans_harvested=document.getElementById("no_of_cavans_harvested_edit").value;
	var weight_per_cavan=document.getElementById("weight_per_cavan_edit").value;

	var e = document.getElementById("ah_measurement_edit");
	var unit1 = e.options[e.selectedIndex].text;
	
	var a = document.getElementById("ap_measurement_edit");
	var unit2 = a.options[a.selectedIndex].text;


	if(area_harvested==""||area_planted==""||no_of_cavans_harvested==""||weight_per_cavan=="")
	{
		document.getElementById("edit_record_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("edit_record_res").innerHTML ="";
			document.getElementById("response").innerHTML=this.responseText;
			$("#editFarmModal").modal("hide")
			$("#responseModal").modal("show")
			showRecords2();
			location.reload();
			//$("#table_container1").ajax.reload( null, false);


			
		}
	}
		xmlhttp.open("GET","editRecord.php?id="+id+"&area_harvested="+area_harvested+"&area_planted="+area_planted+"&no_of_cavans_harvested="+no_of_cavans_harvested+"&weight_per_cavan="+weight_per_cavan+"&unit1="+unit1+"&unit2="+unit2,true);
		xmlhttp.send();
		
	}
}

function editRecord(id,offset,no_of_records_per_page){
	
	var area_harvested=document.getElementById("area_harvested_edit").value;
	var area_planted=document.getElementById("area_planted_edit").value;
	var no_of_cavans_harvested=document.getElementById("no_of_cavans_harvested_edit").value;
	var weight_per_cavan=document.getElementById("weight_per_cavan_edit").value;

	var e = document.getElementById("ah_measurement_edit");
	var unit1 = e.options[e.selectedIndex].text;
	
	var a = document.getElementById("ap_measurement_edit");
	var unit2 = a.options[a.selectedIndex].text;


	if(area_harvested==""||area_planted==""||no_of_cavans_harvested==""||weight_per_cavan=="")
	{
		document.getElementById("edit_record_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("edit_record_res").innerHTML ="";
			document.getElementById("response").innerHTML=this.responseText;
			$("#editFarmModal").modal("hide")
			$("#responseModal").modal("show")
			showRecords(offset,no_of_records_per_page);

			
		}
	}
		xmlhttp.open("GET","editRecord.php?id="+id+"&area_harvested="+area_harvested+"&area_planted="+area_planted+"&no_of_cavans_harvested="+no_of_cavans_harvested+"&weight_per_cavan="+weight_per_cavan+"&unit1="+unit1+"&unit2="+unit2,true);
		xmlhttp.send();
		
	}
}



function showRecords(offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("user_table3").innerHTML=this.responseText;
	
			
		}
	}
		xmlhttp.open("GET","showRecords.php?offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
	
}

function showRecords2()
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("user_table3").innerHTML=this.responseText;
	
			
		}
	}
		xmlhttp.open("GET","showRecords2.php",true);
		xmlhttp.send();
	
}




function changePassword(username)
{
	var password1=document.getElementById("password_edit1").value;
	var password2=document.getElementById("password_edit2").value;
	var password3=document.getElementById("password_edit3").value;	
	if(password1==""||password2==""||password3=="")
	{
		document.getElementById("edit_password_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(password2!=password3)
		{
			document.getElementById("edit_password_res").innerHTML ="Password does not match";
		}
		else
		{
			if(window.XMLHttpRequest) 	
			{
			 xmlhttp=new XMLHttpRequest();
			} 
		  
			xmlhttp.onreadystatechange=function() {
			if (this.readyState==4 && this.status==200) {   
				var res=this.responseText;
				if(res=="failed")
				{
					document.getElementById("edit_password_res").innerHTML ="Incorrect Password";
				}
				else
				{
					document.getElementById("response").innerHTML=this.responseText;
					$("#changePasswordModal").modal("hide")
					$("#responseModal").modal("show")
				}
				
				
			}
		}
			xmlhttp.open("GET","changePassword.php?password1="+password1+"&password2="+password2+"&username="+username,true);
			xmlhttp.send();	
		}
	}
}


function showPasswordPane(id)
{
	$("#profileModal").modal("hide")
	$("#changePasswordModal").modal("show")


}


function editProfile(id)
{
	var name=document.getElementById("name_edit").value;
	var username=document.getElementById("username_edit").value;
	var email=document.getElementById("email_edit").value;
	
	if(username==""||name==""||email=="")
	{
		document.getElementById("edit_account_res").innerHTML ="Please complete the details";
	}
	else
	{
var str="";
var error=0;
		if (!validateUN(username)) {
			error=1;
			  str=str+"Username should not contain special characters<br>"
		   } 
		  
		 if (!validateEmail(email)) {
			error=1;
			str=str+"Invalid email<br>"
		   }
		   
		 if (!validateSC(name)) {
			error=1;
			str=str+"Name should not contain special characters<br>"
		   }
		   if(error==1)
		   {
			document.getElementById("edit_account_res").innerHTML=str;  
		   }
		  
		else
		{
			if(window.XMLHttpRequest) 	
			{
			 xmlhttp=new XMLHttpRequest();
			} 
		  
			xmlhttp.onreadystatechange=function() {
			if (this.readyState==4 && this.status==200) {   
				document.getElementById("edit_account_res").innerHTML ="";
				document.getElementById("response").innerHTML=this.responseText;
				$("#profileModal").modal("hide")
				$("#responseModal").modal("show")
				$("#responseModal").on("hidden.bs.modal", function () {
					document.location.reload()
				});
			
	
				
			}
		}
			xmlhttp.open("GET","editProfile.php?id="+id+"&name="+name+"&username="+username+"&email="+email,true);
			xmlhttp.send();
		}

		
	}
}

function showProfile(username)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyProfile").innerHTML=this.responseText;
			$("#profileModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showProfile.php?username="+username,true);
		xmlhttp.send();
}

function editFarm2(id,offset,no_of_records_per_page){
	
	var farm_name=document.getElementById("farm_name_edit").value;
	var location=document.getElementById("location_edit").value;
	var land_area=document.getElementById("land_area_edit").value;

	var e = document.getElementById("unit_edit");
	var unit = e.options[e.selectedIndex].text;


	if(farm_name==""||location==""||land_area==""||unit=="")
	{
		document.getElementById("edit_farm_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("edit_farm_res").innerHTML ="";
			document.getElementById("response").innerHTML=this.responseText;
			$("#editFarmModal").modal("hide")
			$("#responseModal").modal("show")
			showFarms2(offset,no_of_records_per_page);

			
		}
	}
		xmlhttp.open("GET","editFarm.php?id="+id+"&farm_name="+farm_name+"&location="+location+"&land_area="+land_area+"&unit="+unit,true);
		xmlhttp.send();
		
	}
}


function editFarm(id){
	
	var farm_name=document.getElementById("farm_name_edit").value;
	var location=document.getElementById("location_edit").value;
	var land_area=document.getElementById("land_area_edit").value;

	var e = document.getElementById("unit_edit");
	var unit = e.options[e.selectedIndex].text;


	if(farm_name==""||location==""||land_area==""||unit=="")
	{
		document.getElementById("edit_farm_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("edit_farm_res").innerHTML ="";
			document.getElementById("response").innerHTML=this.responseText;
			$("#editFarmModal").modal("hide")
			$("#responseModal").modal("show")
			showFarms(offset,no_of_records_per_page);

			
		}
	}
		xmlhttp.open("GET","editFarm.php?id="+id+"&farm_name="+farm_name+"&location="+location+"&land_area="+land_area+"&unit="+unit,true);
		xmlhttp.send();
		
	}
}


function removeFarm2(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("response").innerHTML=this.responseText;
			$("#deleteModal").modal("hide")
			$("#responseModal").modal("show")
			showFarms2(offset,no_of_records_per_page);
			
		}
	}
		xmlhttp.open("GET","deleteOneFarm.php?id="+id,true);
		xmlhttp.send();
}

function removeFarm(id)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("response").innerHTML=this.responseText;
			$("#deleteModal").modal("hide")
			$("#responseModal").modal("show")
			showFarms();

			
		}
	}
		xmlhttp.open("GET","deleteOneFarm.php?id="+id,true);
		xmlhttp.send();
}


function deleteFarm2(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyDelete").innerHTML=this.responseText;
			$("#deleteModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","askDeleteFarm2.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}

function deleteFarm(id)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyDelete").innerHTML=this.responseText;
			$("#deleteModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","askDeleteFarm.php?id="+id,true);
		xmlhttp.send();
}


function editFarm(id,offset,no_of_records_per_page){
	
	var farm_name=document.getElementById("farm_name_edit").value;
	var location=document.getElementById("location_edit").value;
	var land_area=document.getElementById("land_area_edit").value;

	var e = document.getElementById("unit_edit");
	var unit = e.options[e.selectedIndex].text;


	if(farm_name==""||location==""||land_area==""||unit=="")
	{
		document.getElementById("edit_farm_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("edit_farm_res").innerHTML ="";
			document.getElementById("response").innerHTML=this.responseText;
			$("#editFarmModal").modal("hide")
			$("#responseModal").modal("show")
			showFarms(offset,no_of_records_per_page);

			
		}
	}
		xmlhttp.open("GET","editFarm.php?id="+id+"&farm_name="+farm_name+"&location="+location+"&land_area="+land_area+"&unit="+unit,true);
		xmlhttp.send();
		
	}


	
}


function showFarm2(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBody").innerHTML=this.responseText;
			$("#editFarmModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showFarm2.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}

function showFarm(id)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBody").innerHTML=this.responseText;
			$("#editFarmModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showFarm.php?id="+id,true);
		xmlhttp.send();
}

function showFarms2(offset,no_of_records_per_page){
	
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("user_table2").innerHTML=this.responseText;
			location.reload();
	
			
		}
	}
		xmlhttp.open("GET","showFarms2.php?offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
	
	}

function showFarms(){
	
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("user_table").innerHTML=this.responseText;
			location.reload();

	
			
		}
	}
		xmlhttp.open("GET","showFarms.php",true);
		xmlhttp.send();
	
	}

	function editCrop2(id){
	
		var crop_name=document.getElementById("crop_name_edit").value;
		var variety=document.getElementById("variety_edit").value;
	
		var e = document.getElementById("type_edit");
		var type = e.options[e.selectedIndex].text;
	
		var a = document.getElementById("season_edit");
		var season = a.options[a.selectedIndex].text;
	
		if(type==""||variety==""||crop_name==""||season=="")
		{
			document.getElementById("edit_crop_res").innerHTML ="Please complete the details";
		}
		else
		{
			if(window.XMLHttpRequest) 	
			{
			 xmlhttp=new XMLHttpRequest();
			} 
		  
			xmlhttp.onreadystatechange=function() {
			if (this.readyState==4 && this.status==200) {   
				document.getElementById("edit_crop_res").innerHTML ="";
				document.getElementById("response").innerHTML=this.responseText;
				$("#editCropModal").modal("hide")
				$("#responseModal").modal("show")
				showCrops2();
	
				
			}
		}
			xmlhttp.open("GET","editCrop.php?id="+id+"&type="+type+"&variety="+variety+"&crop_name="+crop_name+"&season="+season,true);
			xmlhttp.send();
			
		}
	
	
		
	}


function editCrop(id,offset,no_of_records_per_page){
	
	var crop_name=document.getElementById("crop_name_edit").value;
	var variety=document.getElementById("variety_edit").value;

	var e = document.getElementById("type_edit");
	var type = e.options[e.selectedIndex].text;

	var a = document.getElementById("season_edit");
	var season = a.options[a.selectedIndex].text;

	if(type==""||variety==""||crop_name==""||season=="")
	{
		document.getElementById("edit_crop_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("edit_crop_res").innerHTML ="";
			document.getElementById("response").innerHTML=this.responseText;
			$("#editCropModal").modal("hide")
			$("#responseModal").modal("show")
			show_crops(offset,no_of_records_per_page);

			
		}
	}
		xmlhttp.open("GET","editCrop.php?id="+id+"&type="+type+"&variety="+variety+"&crop_name="+crop_name+"&season="+season,true);
		xmlhttp.send();
		
	}


	
}

function removeCrop(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("response").innerHTML=this.responseText;
			$("#deleteModal").modal("hide")
			$("#responseModal").modal("show")
			show_crops(offset,no_of_records_per_page);
			
		}
	}
		xmlhttp.open("GET","deleteOneCrop.php?id="+id,true);
		xmlhttp.send();
}

function removeCrop2(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("response").innerHTML=this.responseText;
			$("#deleteModal").modal("hide")
			$("#responseModal").modal("show")
			showCrops2();
			
		}
	}
		xmlhttp.open("GET","deleteOneCrop.php?id="+id,true);
		xmlhttp.send();
}

function deleteCrop2(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyDelete").innerHTML=this.responseText;
			$("#deleteModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","askDelete2.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}

function deleteCrop(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyDelete").innerHTML=this.responseText;
			$("#deleteModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","askDelete.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}


function showCrop(id)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyCrop").innerHTML=this.responseText;
			$("#editCropModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showCrop.php?id="+id,true);
		xmlhttp.send();
}

function showCrop2(id,offset,no_of_records_per_page)
{
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBodyCrop").innerHTML=this.responseText;
			$("#editCropModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showCrop2.php?id="+id+"&offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
}

function show_crops(offset,no_of_records_per_page){
	
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("user_table").innerHTML=this.responseText;
			location.reload();
	
			
		}
	}
		xmlhttp.open("GET","showCrops.php?offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
		xmlhttp.send();
	
	}

	function showCrops2(){
	
		if(window.XMLHttpRequest) 	
			{
			 xmlhttp=new XMLHttpRequest();
			} 
		  
			xmlhttp.onreadystatechange=function() {
			if (this.readyState==4 && this.status==200) {   
				document.getElementById("user_table").innerHTML=this.responseText;
				location.reload();
		
				
			}
		}
			xmlhttp.open("GET","showCrops2.php",true);
			xmlhttp.send();
		
		}

function editAccount(id,offset,no_of_records_per_page){
	
	var name=document.getElementById("name_edit").value;
	var username=document.getElementById("username_edit").value;
	var email=document.getElementById("email_edit").value;
	
	if(username==""||name==""||email=="")
	{
		document.getElementById("edit_account_res").innerHTML ="Please complete the details";
	}
	else
	{
		if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("edit_account_res").innerHTML ="";
			document.getElementById("response").innerHTML=this.responseText;
			$("#editAccountModal").modal("hide")
			$("#responseModal").modal("show")
			show_users(offset,no_of_records_per_page);

			
		}
	}
		xmlhttp.open("GET","editAccount.php?id="+id+"&name="+name+"&username="+username+"&email="+email,true);
		xmlhttp.send();
		
	}


	
}



function showAccount(id){
	
	if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			document.getElementById("modalContainerBody").innerHTML=this.responseText;
			$("#editAccountModal").modal("show")
			
		}
	}
		xmlhttp.open("GET","showAccount.php?id="+id,true);
		xmlhttp.send();
	
}

function show_users(offset,no_of_records_per_page){
	
if(window.XMLHttpRequest) 	
	{
     xmlhttp=new XMLHttpRequest();
	} 
  
	xmlhttp.onreadystatechange=function() {
    if (this.readyState==4 && this.status==200) {   
		document.getElementById("user_table").innerHTML=this.responseText;
		location.reload();

		
	}
}
	xmlhttp.open("GET","showUsers.php?offset="+offset+"&no_of_records_per_page="+no_of_records_per_page,true);
	xmlhttp.send();

}

function update_user_status(id,u_status)
{
	if(window.XMLHttpRequest) 	
	{
     xmlhttp=new XMLHttpRequest();
	} 
  
	xmlhttp.onreadystatechange=function() {
    if (this.readyState==4 && this.status==200) {   
		show_users();
	}
}
	xmlhttp.open("GET","updateUserStatus.php?id="+id+"&status="+u_status,true);
	xmlhttp.send();	

}


function cost_of_production(crop)
{
	var crop_id="";
	if(crop=="rice"){
	crop_id=document.getElementById("crop2").value;  

	
}
else{
	crop_id=document.getElementById("crop3").value;  
	
}

if(crop_id!="none")
{




if(window.XMLHttpRequest) 	
	{
     xmlhttp=new XMLHttpRequest();
	} 
  
	xmlhttp.onreadystatechange=function() {
    if (this.readyState==4 && this.status==200) {   

		var temp=this.responseText;
		var temp2=temp.split('#');
		var crop_type=temp2[0];
		var crop_variety=temp2[1];
		var season=temp2[2];
		
	
		export_cost(crop_id,crop_type,crop_variety,season);
		
		
	}
}
	xmlhttp.open("GET","getCost_id.php?id="+crop_id,true);
	xmlhttp.send();

}

}
	
	
function export_cost(id,crop_type,crop_variety,season)
{
window.location.href='export_cost.php?id='+id+"&crop_type="+crop_type+"&crop_variety="+crop_variety+"&season="+season;
}	
	



function show_forgot_password_pane()
{
document.getElementById("forgot_password_pane").style.display = "block";
document.getElementById("register_pane").style.display = "none";
document.getElementById("login_pane").style.display = "none";
document.getElementById("loginresponse").innerHTML="";
		
}	


function show_register_pane()
{
document.getElementById("register_pane").style.display = "block";
document.getElementById("login_pane").style.display = "none";
document.getElementById("forgot_password_pane").style.display = "none";
document.getElementById("loginresponse").innerHTML="";
		
}	

function show_login_pane()
{
document.getElementById("register_pane").style.display = "none";
document.getElementById("forgot_password_pane").style.display = "none";
document.getElementById("login_pane").style.display = "block";
document.getElementById("registerresponse").innerHTML="";
		
}	

function validateSC(name) {
        //Regex for Valid Characters i.e. Alphabets, Numbers and Space.
        var regex = /^[A-Za-z0-9 ]+$/
 		
		return regex.test(name);
    }
function validateUN(name) {
        //Regex for Valid Characters i.e. Alphabets, Numbers and Space.
        var regex = /^[A-Za-z0-9.]+$/
 		
		return regex.test(name);
    }	
function validateEmail(email) {
  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}
	
function register()
{
var str1="";
var str2="";
var str3="";
var str4="";
var error=0;
var username=document.getElementById("username3").value;	
var email=document.getElementById("email2").value;	
var name=document.getElementById("name2").value;	
var password1=document.getElementById("password3").value;	
var password2=document.getElementById("password4").value;



if (!validateUN(username)) {
   error=1;
	 str1="Username should not contain special characters"
  } 
 
if (!validateEmail(email)) {
   error=1;
	 str2="Invalid email"
  }
  
if (!validateSC(name)) {
   error=1;
	 str3="Name should not contain special characters"
  }
if(password1!=password2)
{
	error=1;
	 str4="Password does not match"
}	
  
  
  if(error==1)
  {
document.getElementById("registerresponse").innerHTML=str1+"\n"+str2+"\n"+str3+"\n"+str4;	  
  }
  
  else if(error==0)
	{
		if(username==""||email==""||name==""||password1==""||password2=="")
{
document.getElementById("registerresponse").innerHTML="Please compelete your details";
}
else
{

	if(window.XMLHttpRequest) 	
	{
     xmlhttp=new XMLHttpRequest();
	} 
  
	xmlhttp.onreadystatechange=function() {
    if (this.readyState==4 && this.status==200) {   
		var validation=this.responseText;
		if(validation=="Account creation failed")
		{
			document.getElementById("loginresponse").innerHTML=this.responseText;
		}

		else{
			window.location.href='index.php';
		}

	}
}
	xmlhttp.open("GET","register_acc.php?username="+username+"&email="+email+"&name="+name+"&password="+password1,true);
	xmlhttp.send();
}
} 

}


	
function login() { 
var username=document.getElementById("username1").value;	
var password=document.getElementById("password1").value;	
var validation="";
if(username==""||password=="")
{
	document.getElementById("loginresponse").innerHTML="Please complete your details";
}
	
else
{
if(window.XMLHttpRequest) 	
	{
     xmlhttp=new XMLHttpRequest();
	} 
  
	xmlhttp.onreadystatechange=function() {
    if (this.readyState==4 && this.status==200) {   
		var validation=this.responseText;
		//console.log(validation)
		if(validation=="Account does not exists!")
		{
			document.getElementById("loginresponse").innerHTML=this.responseText;
		}
		else if(validation=="Incorrect Password"){
			document.getElementById("loginresponse").innerHTML=this.responseText;
		}
		else{
			window.location.href='index.php';
		}

	}
}
	xmlhttp.open("GET","login_acc.php?username="+username+"&password="+password,true);
	xmlhttp.send();

} 
}

//graps and chart
    var violet = '#DF99CA',
        red    = '#F0404C',
        green  = '#7CF29C';

 // ------------------------------------------------------- //
    // Charts Gradients
    // ------------------------------------------------------ //
    var ctx1 = $("canvas").get(0).getContext("2d");
    var gradient1 = ctx1.createLinearGradient(150, 0, 150, 300);
    gradient1.addColorStop(0, 'rgba(210, 114, 181, 0.91)');
    gradient1.addColorStop(1, 'rgba(177, 62, 162, 0)');

    var gradient2 = ctx1.createLinearGradient(10, 0, 150, 300);
    gradient2.addColorStop(0, 'rgba(252, 117, 176, 0.84)');
    gradient2.addColorStop(1, 'rgba(250, 199, 106, 0.92)');
function generate_cropProd(crop){
var start_date="";
var end_date="";
var variety="";
var BARCHARTEXMPLE    = "";

if(crop=='rice'){
	start_date=document.getElementById("start_date1").value;  
	end_date=document.getElementById("end_date1").value; 
	variety=document.getElementById("rice_variety").value; 
	$('#groupedBarGraphR').remove();
	$('#rice').append('<canvas id="groupedBarGraphR"><canvas>');
	BARCHARTEXMPLE  = $('#groupedBarGraphR');
}
else{
	start_date=document.getElementById("start_date2").value;  
	end_date=document.getElementById("end_date2").value;
	variety=document.getElementById("onion_variety").value; 
	$('#groupedBarGraphO').remove();
	$('#onion').append('<canvas id="groupedBarGraphO"><canvas>');	
	BARCHARTEXMPLE  = $('#groupedBarGraphO');
}
console.log(start_date+"/"+end_date)
if(!start_date){
    $('#filterModal').modal('show');
      	document.getElementById("msg").innerHTML = "Start date empty. Please check date input.";
        
    } else if (!end_date ){
     	$('#filterModal').modal('show');
        document.getElementById("msg").innerHTML = "End date empty. Please check date input.";
       
} 
else{
	    if(new Date(end_date)< new Date(start_date)){
	    	$('#filterModal').modal('show');
	    	document.getElementById("msg").innerHTML = "Invalid date. Please check date input.";
	    	
	    }else{
	    	var xmlhttp = new XMLHttpRequest();
  			xmlhttp.onreadystatechange = function() {
			    if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
			    {
			      data = JSON.parse(this.responseText);
			      //console.log(this.responseText);
			         
			            var area_planted = [];
			            var area_harvested = [];
			            var no_of_sack = [];  
			            var average_weight=[]
			            var yields = [];
			            var crop_info = [];
			            for(var i in data) {
			              var area=0;
			              var area_h=0;
			            	if(data[i].ap_measurement=="Ha"){
			            		area=data[i].area_planted;
			            	}
			            	else{
			            		area=data[i].area_planted/10000;
			            	}
			            	area_planted.push(area);	
			            	if(data[i].ah_measurement=="Ha"){
								area_h=data[i].area_harvested;
			            	}
			            	else{
			            		area_h=data[i].area_harvested/10000;
			            		
			            	}
			            	area_harvested.push(area_h);
			            	var weight=data[i].ave_weight/1;
			              var yph=(data[i].total_weight)/1000/(area_h);
			              no_of_sack.push(data[i].no_of_cavans_harvested);
			              average_weight.push(weight.toFixed(2));
			              yields.push(yph.toFixed(2));
			              crop_info.push(data[i].crop_info);
			            }

			    // Bar Chart
			    // ------------------------------------------------------ //
			   
			    var barChartExample = new Chart(BARCHARTEXMPLE, {
			        type: 'bar',
			        options: {

			            scales: {
			                xAxes: [{
			                    display: true,
			                    gridLines: {
			                        color: '#fff'
			                    }
			                }],
			                yAxes: [{
			                	ticks: {
			                	beginAtZero: true
			            		},
			                    display: true,

			                    gridLines: {
			                        color: '#fff'
			                    }
			                }]
			                
    
			            },
			            legend: {
					    display: true,
					    position: 'right',
					    labels: {
					      boxWidth: 50,
					      fontColor: 'black'
					    }


			  }
			        },
			        data: {
			            labels: crop_info,
			            datasets: [
			               /* {
			                    label: "Area Planted(hectare/s)",
			                    backgroundColor:gradient2,
			                    hoverBackgroundColor:gradient2,
			                    borderColor:gradient2,
			                    borderWidth: 1,
			                    data: area_planted,
			                },
			                 {
			                    label: "Area Harvested(hectare/s)",
			                    backgroundColor:violet,
			                    hoverBackgroundColor:violet,
			                    borderColor:violet,
			                    borderWidth: 1,
			                    data: area_harvested,
			                },
			                {
			                    label: "No of Sack(cavan/buriki)",
			                    backgroundColor:green,
			                    hoverBackgroundColor:green,
			                    borderColor:green,
			                    borderWidth: 1,
			                    data: no_of_sack,
			                },
			                 {
			                    label: "Average Weight/Sack(kg)",
			                    backgroundColor:red,
			                    hoverBackgroundColor:red,
			                    borderColor:red,
			                    borderWidth: 1,
			                    data: average_weight,
			                },*/
			                 {
			                    label: "Yields(tons)/hectare",
			                    backgroundColor:"#3cba9f",
			                    hoverBackgroundColor:"#3cba9f",
			                    borderColor:"#3cba9f",
			                    borderWidth: 1,
			                    data: yields,
			                }
			            ]

			        }
			    });

			           
			         
			  }


			}
			  xmlhttp.open("GET","cropProdData.php?crop="+crop+"&date_start="+start_date+"&end_date="+end_date+"&variety="+variety,true);
			  xmlhttp.send();
    	}
    }
}




function showList(crop){
var start_date="";
var end_date="";
var variety=""
	if(crop=="rice"){
		variety=document.getElementById("rice_variety2").value; 
		start_date=document.getElementById("start_date3").value;  
		end_date=document.getElementById("end_date3").value; 
		document.getElementById("row2").style.display = "block";
	}
	else{
		variety=document.getElementById("onion_variety2").value; 
		start_date=document.getElementById("start_date4").value;  
		end_date=document.getElementById("end_date4").value; 
		document.getElementById("row3").style.display = "block";
	}

	if(!start_date){
    $('#filterModal').modal('show');
      	document.getElementById("msg").innerHTML = "Start date empty. Please check date input.";
        
    } else if (!end_date ){
     	$('#filterModal').modal('show');
        document.getElementById("msg").innerHTML = "End date empty. Please check date input.";
       
	} 
	else{
	    if(new Date(end_date)< new Date(start_date)){
	    	$('#filterModal').modal('show');
	    	document.getElementById("msg").innerHTML = "Invalid date. Please check date input.";
	    	
	    }else{
	    if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			console.log("huy"+this.responseText);  
			if(crop=="rice"){
				/*$('#row2').remove();
				$('#rice2').append('<div class="row" id="row2" style="display: block;"></div>');*/
				document.getElementById("row2").innerHTML=this.responseText;
			}
			else if(crop=="onion"){
				/*$('#row3').remove();
				$('#onion2').append('<div class="row" id="row3" style="display: block;"></div>');*/
				document.getElementById("row3").innerHTML=this.responseText;
			}

			
		}
	}
		xmlhttp.open("GET","showCropList.php?crop="+crop+"&date_start="+start_date+"&end_date="+end_date+"&variety="+variety,true);
		xmlhttp.send();
	}
		
}
}
function showListExport(crop){

var start_date="";
var end_date="";
var variety=""
	if(crop=="rice"){
		variety=document.getElementById("rice_variety2").value; 
		start_date=document.getElementById("start_date3").value;  
		end_date=document.getElementById("end_date3").value; 
		document.getElementById("row2").style.display = "block";
	}
	else{
		variety=document.getElementById("onion_variety2").value; 
		start_date=document.getElementById("start_date4").value;  
		end_date=document.getElementById("end_date4").value; 
		document.getElementById("row3").style.display = "block";
	}

	if(!start_date){
    $('#filterModal').modal('show');
      	document.getElementById("msg").innerHTML = "Start date empty. Please check date input.";
        
    } else if (!end_date ){
     	$('#filterModal').modal('show');
        document.getElementById("msg").innerHTML = "End date empty. Please check date input.";
       
	} 
	else{
	    if(new Date(end_date)< new Date(start_date)){
	    	$('#filterModal').modal('show');
	    	document.getElementById("msg").innerHTML = "Invalid date. Please check date input.";
	    	
	    }else{
	    if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			console.log("huy"+this.responseText);  
			if(crop=="rice"){
				/*$('#row2').remove();
				$('#rice2').append('<div class="row" id="row2" style="display: block;"></div>');*/
				document.getElementById("row2").innerHTML=this.responseText;
			}
			else if(crop=="onion"){
				/*$('#row3').remove();
				$('#onion2').append('<div class="row" id="row3" style="display: block;"></div>');*/
				document.getElementById("row3").innerHTML=this.responseText;
			}

			
		}
	}
		xmlhttp.open("GET","showCropListExport.php?crop="+crop+"&date_start="+start_date+"&end_date="+end_date+"&variety="+variety,true);
		xmlhttp.send();
	}
		
}
}

function generate_totalCropProd(crop_){
var crop="";
var PIECHART="";

if(crop_=="rice"){
	crop=document.getElementById("crop2").value;  

	
}
else{
	crop=document.getElementById("crop3").value;  
	
}
//console.log(crop);

var xmlhttp = new XMLHttpRequest();
  xmlhttp.onreadystatechange = function() {
    if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
    {
      data = JSON.parse(this.responseText);
      console.log(this.responseText);
           

            var land_prep=0;
            var land_prep1=0;
            var crop_ops=0;
            var water=0;
            var pest=0;
            var harvest=0;
            var other=0;
            var fertilizer=0;
            var total=[];
            var crop_ops1=0;
            var water1=0;
            var pest1=0;
            var harvest1=0;
            var other1=0;
            var fertilizer1=0;
            //var crop_info = [];
            for(var i in data) {
     
             // var total_cost1=data[i].total;
          	if(data[i].icon=="1"){

              land_prep=data[i].amount;
              land_prep1+=land_prep/1;
          	}
          	if(data[i].icon=="2"){
              crop_ops=data[i].amount;
              crop_ops1+=crop_ops/1;
          	}
          	if(data[i].icon=="3"){
              water=data[i].amount;
              water1+=water/1;
          	}
          	if(data[i].icon=="4"){
              pest=data[i].amount;
              pest1+=pest/1;
          	}
          	if(data[i].icon=="5"){
              harvest=data[i].amount;
              harvest1+=harvest/1;
          	}
          	if(data[i].icon=="6"){
              other=data[i].amount;
              other1+=other/1;
          	}
          	if(data[i].icon=="7"){
              fertilizer=data[i].amount;
              fertilizer1+=fertilizer/1;
          	}
              //crop_info.push(data[i].crop_info);
            }

            var overall=land_prep1+crop_ops1+water1+pest1+harvest1+other1+fertilizer1;
            //console.log(land_prep1);
          
            var percent_lp=(land_prep1/overall)*100;
            var percent_co=(crop_ops1/overall)*100;
            var percent_water=(water1/overall)*100;
            var percent_pest=(pest1/overall)*100;
            var percent_harvest=(harvest1/overall)*100;
            var percent_other=(other1/overall)*100;
            var percent_fertilizer=(fertilizer1/overall)*100;
          	total.push(land_prep1);
          	total.push(crop_ops1);
          	total.push(water1);
          	total.push(pest1);
          	total.push(fertilizer1);
          	total.push(harvest1);
          	total.push(other1);


   // Pie Chart
   // ------------------------------------------------------ //
var PIECHART ="";
if(crop_=="rice"){
	//$('#PieChart').remove();
	//$('#rice2').append('<canvas id="#PieChart"><canvas>');	
	 PIECHART=$('#PieChart1');
	
}
else{ 
	//$('#PieChart2').remove();
	//$('#onion2').append('<canvas id="#PieChart2"><canvas>');
	PIECHART = $('#PieChart2');
}

   var myPieChart = new Chart(PIECHART, {
       	type: 'doughnut',
    data: {
      labels: ["Land Preparation("+percent_lp.toFixed(2)+"%)",
               "Crop Operations("+percent_co.toFixed(2)+"%)",
               "Water and Maintenance("+percent_water.toFixed(2)+"%)",
               "Pest and Disease Control("+percent_pest.toFixed(2)+"%)",
               "Fertilizer("+percent_fertilizer.toFixed(2)+"%)",
               "Harvest and Post Harvest("+percent_harvest.toFixed(2)+"%)",
               "Other("+percent_other.toFixed(2)+"%)"],
      datasets: [
        {
          
          backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#889d9e"],
          data: total
        }
      ]
    },
    options: {
    	cutoutPercentage: 50,
      title: {
        display: true,
        position:'bottom',
        text: 'Total Cost: '+overall
      },
      legend: {
		    position: 'right'
		 
		    },
     
    }
       
   });

  }         
         
  }


  xmlhttp.open("GET","totalCropProdData.php?crop="+crop,true);
  xmlhttp.send();
}

function showListPublic(crop){
var start_date="";
var end_date="";
var variety=""
	if(crop=="rice"){
		variety=document.getElementById("rice_variety2").value; 
		start_date=document.getElementById("start_date3").value;  
		end_date=document.getElementById("end_date3").value; 
		document.getElementById("row2").style.display = "block";
	}
	else{
		variety=document.getElementById("onion_variety2").value; 
		start_date=document.getElementById("start_date4").value;  
		end_date=document.getElementById("end_date4").value; 
		document.getElementById("row3").style.display = "block";
	}

	if(!start_date){
    $('#filterModal').modal('show');
      	document.getElementById("msg").innerHTML = "Start date empty. Please check date input.";
        
    } else if (!end_date ){
     	$('#filterModal').modal('show');
        document.getElementById("msg").innerHTML = "End date empty. Please check date input.";
       
	} 
	else{
	    if(new Date(end_date)< new Date(start_date)){
	    	$('#filterModal').modal('show');
	    	document.getElementById("msg").innerHTML = "Invalid date. Please check date input.";
	    	
	    }else{
	    if(window.XMLHttpRequest) 	
		{
		 xmlhttp=new XMLHttpRequest();
		} 
	  
		xmlhttp.onreadystatechange=function() {
		if (this.readyState==4 && this.status==200) {   
			console.log("huy"+this.responseText);  
			if(crop=="rice"){
				/*$('#row2').remove();
				$('#rice2').append('<div class="row" id="row2" style="display: block;"></div>');*/
				document.getElementById("row2").innerHTML=this.responseText;
			}
			else if(crop=="onion"){
				/*$('#row3').remove();
				$('#onion2').append('<div class="row" id="row3" style="display: block;"></div>');*/
				document.getElementById("row3").innerHTML=this.responseText;
			}

			
		}
	}
		xmlhttp.open("GET","showCropListPublic.php?crop="+crop+"&date_start="+start_date+"&end_date="+end_date+"&variety="+variety,true);
		xmlhttp.send();
	}
		
}
}
function generate_cropProdPublic(crop){
var start_date="";
var end_date="";
var variety="";
var BARCHARTEXMPLE    = "";

if(crop=='rice'){
	start_date=document.getElementById("start_date1").value;  
	end_date=document.getElementById("end_date1").value; 
	variety=document.getElementById("rice_variety").value; 
	$('#groupedBarGraphR').remove();
	$('#rice').append('<canvas id="groupedBarGraphR"><canvas>');
	BARCHARTEXMPLE  = $('#groupedBarGraphR');
}
else{
	start_date=document.getElementById("start_date2").value;  
	end_date=document.getElementById("end_date2").value;
	variety=document.getElementById("onion_variety").value; 
	$('#groupedBarGraphO').remove();
	$('#onion').append('<canvas id="groupedBarGraphO"><canvas>');	
	BARCHARTEXMPLE  = $('#groupedBarGraphO');
}
console.log(start_date+"/"+end_date)
if(!start_date){
    $('#filterModal').modal('show');
      	document.getElementById("msg").innerHTML = "Start date empty. Please check date input.";
        
    } else if (!end_date ){
     	$('#filterModal').modal('show');
        document.getElementById("msg").innerHTML = "End date empty. Please check date input.";
       
} 
else{
	    if(new Date(end_date)< new Date(start_date)){
	    	$('#filterModal').modal('show');
	    	document.getElementById("msg").innerHTML = "Invalid date. Please check date input.";
	    	
	    }else{
	    	var xmlhttp = new XMLHttpRequest();
  			xmlhttp.onreadystatechange = function() {
			    if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
			    {
			      data = JSON.parse(this.responseText);
			      //console.log(this.responseText);
			         
			            var area_planted = [];
			            var area_harvested = [];
			            var no_of_sack = [];  
			            var average_weight=[]
			            var yields = [];
			            var crop_info = [];
			            for(var i in data) {
			              var area=0;
			              var area_h=0;
			            	if(data[i].ap_measurement=="Ha"){
			            		area=data[i].area_planted;
			            	}
			            	else{
			            		area=data[i].area_planted/10000;
			            	}
			            	area_planted.push(area);	
			            	if(data[i].ah_measurement=="Ha"){
								area_h=data[i].area_harvested;
			            	}
			            	else{
			            		area_h=data[i].area_harvested/10000;
			            		
			            	}
			            	area_harvested.push(area_h);
			            	var weight=data[i].ave_weight/1;
			              var yph=(data[i].total_weight)/1000/(area_h);
			              no_of_sack.push(data[i].no_of_cavans_harvested);
			              average_weight.push(weight.toFixed(2));
			              yields.push(yph.toFixed(2));
			              crop_info.push(data[i].crop_info);
			            }

			    // Bar Chart
			    // ------------------------------------------------------ //
			   
			    var barChartExample = new Chart(BARCHARTEXMPLE, {
			        type: 'bar',
			        options: {

			            scales: {
			                xAxes: [{
			                    display: true,
			                    gridLines: {
			                        color: '#fff'
			                    }
			                }],
			                yAxes: [{
			                	ticks: {
			                	beginAtZero: true
			            		},
			                    display: true,

			                    gridLines: {
			                        color: '#fff'
			                    }
			                }]
			                
    
			            },
			            legend: {
					    display: true,
					    position: 'right',
					    labels: {
					      boxWidth: 50,
					      fontColor: 'black'
					    }


			  }
			        },
			        data: {
			            labels: crop_info,
			            datasets: [
			                /*{
			                    label: "Area Planted(hectare/s)",
			                    backgroundColor:gradient2,
			                    hoverBackgroundColor:gradient2,
			                    borderColor:gradient2,
			                    borderWidth: 1,
			                    data: area_planted,
			                },
			                 {
			                    label: "Area Harvested(hectare/s)",
			                    backgroundColor:violet,
			                    hoverBackgroundColor:violet,
			                    borderColor:violet,
			                    borderWidth: 1,
			                    data: area_harvested,
			                },
			                {
			                    label: "No of Sack(cavan/buriki)",
			                    backgroundColor:green,
			                    hoverBackgroundColor:green,
			                    borderColor:green,
			                    borderWidth: 1,
			                    data: no_of_sack,
			                },
			                 {
			                    label: "Average Weight/Sack(kg)",
			                    backgroundColor:red,
			                    hoverBackgroundColor:red,
			                    borderColor:red,
			                    borderWidth: 1,
			                    data: average_weight,
			                },*/
			                 {
			                    label: "Yields(tons)/hectare",
			                    backgroundColor:"#3cba9f",
			                    hoverBackgroundColor:"#3cba9f",
			                    borderColor:"#3cba9f",
			                    borderWidth: 1,
			                    data: yields,
			                }
			            ]

			        }
			    });

			           
			         
			  }


			}
			  xmlhttp.open("GET","cropProdDataPublic.php?crop="+crop+"&date_start="+start_date+"&end_date="+end_date+"&variety="+variety,true);
			  xmlhttp.send();
    	}
    }
}

function generate_totalCropProdPublic(crop_){
var crop="";
var PIECHART="";

if(crop_=="rice"){
	crop=document.getElementById("crop2").value;  

	
}
else{
	crop=document.getElementById("crop3").value;  
	
}
//console.log(crop);

var xmlhttp = new XMLHttpRequest();
  xmlhttp.onreadystatechange = function() {
    if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
    {
      data = JSON.parse(this.responseText);
      console.log(this.responseText);
           

            var land_prep=0;
            var land_prep1=0;
            var crop_ops=0;
            var water=0;
            var pest=0;
            var harvest=0;
            var other=0;
            var fertilizer=0;
            var total=[];
            var crop_ops1=0;
            var water1=0;
            var pest1=0;
            var harvest1=0;
            var other1=0;
            var fertilizer1=0;
            //var crop_info = [];
            for(var i in data) {
     
             // var total_cost1=data[i].total;
          	if(data[i].icon=="1"){

              land_prep=data[i].amount;
              land_prep1+=land_prep/1;
          	}
          	if(data[i].icon=="2"){
              crop_ops=data[i].amount;
              crop_ops1+=crop_ops/1;
          	}
          	if(data[i].icon=="3"){
              water=data[i].amount;
              water1+=water/1;
          	}
          	if(data[i].icon=="4"){
              pest=data[i].amount;
              pest1+=pest/1;
          	}
          	if(data[i].icon=="5"){
              harvest=data[i].amount;
              harvest1+=harvest/1;
          	}
          	if(data[i].icon=="6"){
              other=data[i].amount;
              other1+=other/1;
          	}
          	if(data[i].icon=="7"){
              fertilizer=data[i].amount;
              fertilizer1+=fertilizer/1;
          	}
              //crop_info.push(data[i].crop_info);
            }

            var overall=land_prep1+crop_ops1+water1+pest1+harvest1+other1+fertilizer1;
            //console.log(land_prep1);
          
            var percent_lp=(land_prep1/overall)*100;
            var percent_co=(crop_ops1/overall)*100;
            var percent_water=(water1/overall)*100;
            var percent_pest=(pest1/overall)*100;
            var percent_harvest=(harvest1/overall)*100;
            var percent_other=(other1/overall)*100;
            var percent_fertilizer=(fertilizer1/overall)*100;
          	total.push(land_prep1);
          	total.push(crop_ops1);
          	total.push(water1);
          	total.push(pest1);
          	total.push(fertilizer1);
          	total.push(harvest1);
          	total.push(other1);


   // Pie Chart
   // ------------------------------------------------------ //
var PIECHART ="";
if(crop_=="rice"){
	//$('#PieChart').remove();
	//$('#rice2').append('<canvas id="#PieChart"><canvas>');	
	 PIECHART=$('#PieChart1');
	
}
else{ 
	//$('#PieChart2').remove();
	//$('#onion2').append('<canvas id="#PieChart2"><canvas>');
	PIECHART = $('#PieChart2');
}

   var myPieChart = new Chart(PIECHART, {
       	type: 'doughnut',
    data: {
      labels: ["Land Preparation("+percent_lp.toFixed(2)+"%)",
               "Crop Operations("+percent_co.toFixed(2)+"%)",
               "Water and Maintenance("+percent_water.toFixed(2)+"%)",
               "Pest and Disease Control("+percent_pest.toFixed(2)+"%)",
               "Fertilizer("+percent_fertilizer.toFixed(2)+"%)",
               "Harvest and Post Harvest("+percent_harvest.toFixed(2)+"%)",
               "Other("+percent_other.toFixed(2)+"%)"],
      datasets: [
        {
          
          backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#889d9e"],
          data: total
        }
      ]
    },
    options: {
    	cutoutPercentage: 50,
      title: {
        display: true,
        position:'bottom',
        text: 'Total Cost: '+overall
      },
      legend: {
		    position: 'right'
		 
		    },
     
    }
       
   });

  }         
         
  }


  xmlhttp.open("GET","totalCropProdDataPublic.php?crop="+crop,true);
  xmlhttp.send();
}
