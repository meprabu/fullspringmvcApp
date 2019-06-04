

$(document).ready(function () {
	var selectedProvider = '';
	var confirmText = "Are you sure you want to submit ? \n Please Note all values should be entered. Any blank value will be entered with a zero. \n "
	
	 $('#dropdown-location').on( 'click', 'li a', function() {
	console.log("in select dropdown");
    var text = $(this).html();
    console.log(text);
    if(text.trim() == 'All Locations'){
    	console.log(selectedProvider);
    	buildAllLocationDropdown();
    }else{
    	$("table tr:gt(2)").remove();
    	var htmlText = text + ' <span class="caret"></span>';
     	console.log(htmlText);
    	$(this).closest('.dropdown').find('.dropdown-toggle').html(htmlText);
        var idValue  = $(this).data('location-dropdown-value');		console.log(idValue);
		var valueArray =  idValue.split("-");
		// ARRAY.FIND() NOT SUPPORTED IN IE
		/* var licenseBed = licenseBedLocations.find(function(location){
			return location.locationCode.trim() == valueArray[0].trim() && location.orgCode.trim() == valueArray[1].trim();
		}); */
		var licenseBed = {};
		var selectedLocation = {};
		licenseBedLocations.forEach(function(location){
			if( location.locationCode.trim() == valueArray[0].trim() && location.orgCode.trim() == valueArray[1].trim()){
				selectedLocation = location;
				return true;
			}
		});
		
		console.log(selectedLocation);
		console.log(licenseBed);
		licenseBed = selectedLocation;
		$('.table tbody').append(buildTable(licenseBed.orgCode, licenseBed.bedTypeDesc,licenseBed.locationCode, licenseBed.licBedMale, licenseBed.licBedFeMale, licenseBed.licBedCoed, licenseBed.actBedMale, licenseBed.actBedFeMale,licenseBed.actBedCoed, licenseBed.locationName)); 
		
    }
   }); 
   
   
  //submit function
  
   $('#submit').click(function(){
 	
   console.log("heloooooooooooooooooooooooooooo");
 	var providers = [];
 	var licBedsList = [];
 	var valuesEntered = false;
 	 $('.table input:text').each(function(){
 		 if($(this).val()){
 	 	  valuesEntered = true;
 	 	//  $("#bsError").hide();
    	 } 
     });
     
     if(!valuesEntered){
    // 	$("#bsError").show();
     	return false;
     }else{
     	if(confirm(confirmText)){
       	 	
    	}
    	else{
        return false;
    	}
     }
 	console.log("2heloooooooooooooooooooooooooooo");
 	$('.table').find('> tbody > tr:gt(2) ').each(function () {
 		
 		var csuValues = {};
 		
	   	var newJSON = {};
    	var serviceType= '';
    	var licBed = {};
    	var activeBed = {};
    	var adultPsy = {};
    	var adolPsy = {};
    	var i = 0;
    	
	   	$.each(this.cells, function(){
	   	
	   	if($(this).is('td')){
	   		var mytd = $(this);
	   		if(mytd.children().length> 0){
	   			//console.log("in if if " + $(mytd.find("> input")).val());
	   			var tdValue = $(mytd.find("> input")).val();
	   			if(i<=3){
	   				/* if(i==2) licBed.male = tdValue;
	   				if(i==3) licBed.female = tdValue; */
	   				//if(i==4)  licBed.coed = tdValue;
	   				if(i==2) csuValues.licBedMale = tdValue;
	   				if(i==3) csuValues.licBedFeMale = tdValue;
	   			}else{
	   				/* if(i==4) activeBed.male = tdValue;
	   				if(i==5) activeBed.female = tdValue; */
	   				//if(i==7)  activeBed.coed = tdValue;
	   				if(i==4) csuValues.actBedMale = tdValue;
	   				if(i==5) csuValues.actBedFemale = tdValue;
	   			}
	   		}	
	   		else{
	   			console.log("in else length >0" + mytd.html().trim());
	   			/* if(i==6) newJSON.provider = mytd.html().trim();
	   			if(i==7) newJSON.location = mytd.html().trim(); */
	   			if(i==6) csuValues.orgCode = mytd.html().trim();
	   			if(i==7) csuValues.locationCode = mytd.html().trim();
	   		}
	   		
	   	  			
	   	}else if($(this).is('th')){					  
	   		serviceType = ( $(this).html().trim() === 'Adult Psychiatric' ) ? 'AP1' : 'AP2';
	   		csuValues.bedtype = serviceType;
	   		console.log("in else td:"+serviceType + $(this).html().trim());
	   	} 
	   	//console.log(i);
	   	
	   i++;
     });
    if(serviceType == 'adultPsy'){
    	adultPsy.licBed = licBed;
 		adultPsy.activeBed = activeBed;
    	newJSON.adultPsy = 	adultPsy;
    }else{
    	adolPsy.licBed = licBed;
 		adolPsy.activeBed = activeBed;
    	newJSON.adolPsy = 	adolPsy;
    }
    providers.push(newJSON);
    console.log(csuValues);
    licBedsList.push(csuValues); 	
  });
  	console.log(licBedsList);
    provs = JSON.stringify(licBedsList)
    console.log(provs);
   document.getElementById("voodoo").value = provs;
   // document.getElementById("licBeds").value= providers;
   /*  $.post( "/csu/submitCsuAdmin", provs).done(function( data ) {
    		 console.log(data);
  	}); */

 }); 
   
});

console.log(" -PEREMIT- " + permit);
function populateLocations(){
 	console.log(" ------------ ");
	 var provider = $("#provider").val();
	  $("table tr:gt(2)").remove();
   	    $("table tr:nth-child(4) td:nth-child(2)").html(provider);
   	    $("table tr:nth-child(5) td:nth-child(2)").html(provider);
	    $("table tr:nth-child(4) td:nth-child(3)").html('');
   	    $("table tr:nth-child(5) td:nth-child(3)").html('');
	 console.log(provider);
	  console.log(locationCodeUrl);
	  $.get( locationCodeUrl + "?provider=" + provider, function( data ) {
        $("#region").empty();
        console.log(data);
        buildServicesDropdown(data, provider);
       
    });
}

function emptyLocationDropdown(){
 	$('#dropdown-location').empty();
 	var text = 'Select a Location';
    var htmlText = text + ' <span class="caret"></span>';
    $('#dropdown-location').closest('.dropdown').find('.dropdown-toggle').html(htmlText);
 }
	
 function oncgangeLocation(){
 	
 }	

  function buildTable(provId,bedtype, locationId, licMale, licFemale, licCoed, actMale, actFemale, actCoed, locName){
   console.log("-------------------- In build Table");
  	var tablerow = '';
  	if(permit == "999"){
		tablerow = tablerow + '<tr>';
		tablerow = tablerow + '<th scope="row" nowrap align="left">' + bedtype + '</th>';
		//tablerow = tablerow + '<td id="prov-row1">' + provId + '</td>';
		tablerow = tablerow +'<td colspan="2" id="location-row1"> <b>' + locName + ' </b></td>';
		tablerow = tablerow +'<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ></input>' + '</td>';
		tablerow = tablerow + '<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);"></input>' + '</td>';
		//tablerow = tablerow + '<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ></input>' + '</td>';
		tablerow = tablerow + '<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ></input>' + '</td>';
		tablerow = tablerow + '<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ></input>' + '</td>';
		//tablerow = tablerow +'<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ></input>' + '</td>';
		tablerow = tablerow + '<td style="display:none;">' + provId + '</td>';
		tablerow = tablerow + '<td style="display:none;">' + locationId + '</td>';
		tablerow = tablerow + '</tr>'
  	}else{
  		tablerow = tablerow + '<tr>';
		tablerow = tablerow + '<th scope="row" nowrap align="left">' + bedtype + '</th>';
		//tablerow = tablerow + '<td id="prov-row1">' + provId + '</td>';
		tablerow = tablerow +'<td colspan="2" id="location-row1"> <b>' + locName + '</b></td>';
		tablerow = tablerow +'<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ' + 'value="'+ licMale + '" disabled></input>' + '</td>';
		tablerow = tablerow + '<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);"' + 'value="'+ licFemale + '" disabled></input>' + '</td>';
		//tablerow = tablerow + '<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ' + 'value="'+ licCoed + '" disabled></input>' + '</td>';
		tablerow = tablerow + '<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ' + 'value="'+ actMale + '" disabled></input>' + '</td>';
		tablerow = tablerow + '<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ' + 'value="'+ actFemale + '" disabled></input>' + '</td>';
		//tablerow = tablerow +'<td><input type="text" size="2" maxlength="4" onkeyup="checkNumbers(event, this);" ' + 'value="'+ actCoed + '" disabled></input>' + '</td>';
		tablerow = tablerow + '<td style="display:none;">' + provId + '</td>';
		tablerow = tablerow + '<td style="display:none;">' + locationId + '</td>';
		tablerow = tablerow + '</tr>'
  	}	
		return tablerow;
		
  }
  
	function buildServicesDropdown(response, providerId){
	 console.log("-------------------- In build location function");
	 if(null != response || undefined != response){
	 	emptyLocationDropdown();
	    if(response.length>1){
	    	licenseBedLocations = response;
	    	response.forEach(function(licenseBed){	
	 			//var locationValues = location.split("-");
	 			var idvalue = licenseBed.locationCode + '-' + licenseBed.orgCode;
	 			var locationName = licenseBed.locationCode + '-' + licenseBed.locationName;
	 			$('#dropdown-location').append('<li><a href="#" data-location-dropdown-value=" ' + idvalue + '" >' + locationName + '</a></li>');
	 		});
	 		$('#dropdown-location').append(' <li class="divider"></li> ');
		  	$('#dropdown-location').append('  <li><a href="#" data-location-dropdown-value="all">All Locations</a></li> ');
	    }else{
	    	//var valueArray =  response[0].split("-");
	    	//var locationId = valueArray[0];
	    	var licenseBed = response[0];
	    	console.log(licenseBed);
	    	if(licenseBed){
	    		var text = licenseBed.locationCode + '-' + licenseBed.locationName ;
	   			var htmlText = text + ' <span class="caret"></span>';
	    		$('#dropdown-location').closest('.dropdown').find('.dropdown-toggle').html(htmlText);
		   		$('.table tbody').append(buildTable(licenseBed.orgCode, licenseBed.bedTypeDesc,licenseBed.locationCode, licenseBed.licBedMale, licenseBed.licBedFemale, licenseBed.licBedCoed, licenseBed.actBedMale, licenseBed.actBedFemale,licenseBed.actBedCoed, licenseBed.locationName));
	    	}
	    	 
	    }
	 }
	}
	
 function  allProviders(licenseBeds){
 
 	//var providerId = [];
 	//var providerValue = [];
 	//var locationId = [];
 	console.log(providers);
 	console.log('----------------------------');
 	var tablerow = '';
	 $("table tr:gt(2)").remove();
	 licenseBeds.forEach(function(licenseBed){
	 	tablerow = tablerow + buildTable(licenseBed.orgCode, licenseBed.bedTypeDesc,licenseBed.locationCode, licenseBed.licBedMale, licenseBed.licBedFeMale, licenseBed.licBedCoed, licenseBed.actBedMale, licenseBed.actBedFeMale,licenseBed.actBedCoed,licenseBed.locationName);
	 });
	 
	$('.table tbody').append(tablerow); 
		
 }	

 function checkNumbers(e, obj)
	{
      if (/\D/g.test(obj.value)) obj.value = obj.value.replace(/\D/g,'');
       $("#bsError").hide();
    }
    
   function buildAllLocationDropdown(){
 	console.log('----------------------------');
 	console.log(licenseBedLocations);
 	$("table tr:gt(2)").remove(); 
 		var tablerow = '';
 		licenseBedLocations.forEach(function(licenseBed){
 		console.log(licenseBed);
 		tablerow = tablerow + buildTable(licenseBed.orgCode, licenseBed.bedTypeDesc,licenseBed.locationCode, licenseBed.licBedMale, licenseBed.licBedFemale, licenseBed.licBedCoed, licenseBed.actBedMale, licenseBed.actBedFemale,licenseBed.actBedCoed, licenseBed.locationName);
	 		
	});
	$('.table tbody').append(tablerow); 
 }