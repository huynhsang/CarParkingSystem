var FloorSelected;
var ArrayFloors = [];
var CloneArrayFloors = [];
var IdListFloorDeleted = [];
var Parking = {id: 0, name: "", price: "", staff_id: 0, floors: []};

$(document).ready(function(){
	$('input[name=numberOfFloor]').prop('disabled', true);
	var parking = JSON.parse($("input#parking").val());
	Parking.id = parking.id;
	Parking.name = parking.name;
	Parking.staff_id = parking.staff_id;
	Parking.price = parking.price;
	console.log(Parking.staff_id);
	handleJSONFloors(parking.floors);
	
	
	
	$("#numOfFloor").on('change', function(){
		initListSpace();
	});
	
	
	$("a.edit").click(function(){
		CloneArrayFloors = ArrayFloors.map(floor => Object.assign({}, floor)); //clone array of object
		initListFloor();
		initListRight();
		$(".new-parkinglot-name").val(Parking.name);
	});
	
	//delete floor in update parking view
	$("a.minus").click(function(){
		handleDeleteFloor();
	});
	
	//add Floor in Update parking view
	$("button#btnAddFloor").click(function(){
		handleAddFloor();
	});
	
	//update parking
	$("button#btnUpdateParking").click(function(){
		handleUpdate();
	});
	
	//add a new column
	$("button.add-column").click(function() {
		handleAddColumn();
	});
	
	//add a new Row
	$("button.add-row").click(function() {
		handleAddRow();
	});
	
	//delete Parking
	$("a.delete").click(function(){
		if(confirm("Do you want to DELETE this parking?")){
			ajaxDeleteParking();
		}
	})
});

function handleJSONFloors(floors){
	for (var i=0; i<floors.length; i++){
		var f = JSON.parse(floors[i]);
		var blanks = f.blanks;
		var floor = {
				id: f.id,
				row: f.row,
				column: f.column,
				level: f.level,
				blanks: []
		}
		for(var j=0; j<blanks.length; j++){
			var b = JSON.parse(blanks[j]);
			var blank = {
					id: b.id,
					row: b.row,
					column: b.column,
					status: b.status
			}
			floor.blanks.push(blank);
		}
		ArrayFloors.push(floor);
	};
	
	initListSpace();
}

//main view parkingdetail

function initListSpace(){
	var numOfFloor = $("#numOfFloor").val();
	document.getElementById("listSpace").innerHTML = "";
	for (var i=0; i<ArrayFloors.length; i++){
		var f = ArrayFloors[i];
		if(f.level == numOfFloor){
			var listSpace = dynamicListByBlank(f.blanks, f.row, f.column, f.level);
			$("#listSpace").append(listSpace);
		}
	}
}

function dynamicListByBlank(blanks, row, column, level){
	var alphabet_row = initRow(row);
	var str = "";
	for(var i=0; i<row; i++){
		for(var j=0; j<column; j++){
			var temp = (i*column +j);
			var b = blanks[temp];
			var status = b.status;
			if(status == "empty" || status == "full"){
				str += '<div class="block '+status+'" onclick="updateBlank('+temp+', '+level+', this)">'+alphabet_row[i]+j+'</div>';
			}else if (status == "unavailable"){
				str += '<div class="block '+status+'" onclick="updateBlank('+temp+', '+level+', this)"><span class="glyphicon glyphicon-plus"></span></div>';
			}
		}
		str +='<div style="clear:left"></div>';
	}
	return str;
}


function initRow(num){
	var character = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
	                 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
	var row = [];
	var amount = character.length;
	for(var i=0; i<num; i++){
		if(i<amount){
			row[i] = character[i];
		}else{
			var temp = Math.floor((i-amount)/amount);
			row[i] = row[temp] + character[i%amount];
		}
	}
	return row;
}

function updateBlank(index, level, element){
	if(!$(element).hasClass("full")){
		var str = "Are you sure close it?";
		var status = "unavailable";
		if($(element).hasClass("unavailable")){
			str = "Are you sure open it?";
			status = "empty";
		}
		if(confirm(str)){
			var blank = ArrayFloors[level-1].blanks[index];
			blank.status = status;
			var b ={
					id: blank.id,
					row: blank.row,
					column: blank.column,
					status: status,
					floor: ArrayFloors[level-1]
			}
			initListSpace();
			ajaxPost_UpdateBlank(b);
		};
	}
}

function handleAddColumn(){
	var numOfFloor = $("#numOfFloor").val();
	var floor = ArrayFloors[numOfFloor-1];
	var f = {
			id: floor.id,
			row: floor.row,
			column: floor.column + 1,
			level: floor.level,
			blanks: [],
			parking: Parking
	}
	for(var i=0; i<f.row; i++){
		var b = {
				row: i,
				column: floor.column,
				status: "empty",
				floor: floor
				
		}
		f.blanks.push(b);
	}
	if(confirm("Are you sure about this?")){
		ajaxPost_UpdateFloor(f);
	}
}

function handleAddRow(){
	var numOfFloor = $("#numOfFloor").val();
	var floor = ArrayFloors[numOfFloor-1];
	var f = {
			id: floor.id,
			row: floor.row + 1,
			column: floor.column,
			level: floor.level,
			blanks: [],
			parking: Parking
	}
	for(var i=0; i<f.column; i++){
		var b = {
				row: floor.row,
				column: i,
				status: "empty",
				floor: floor
				
		}
		f.blanks.push(b);
	}
	if(confirm("Are you sure about this?")){
		ajaxPost_UpdateFloor(f);
	}
}
//end main view parking detail


//update view
//list left floor
function initListFloor(){
	var value = CloneArrayFloors.length;
	document.getElementById('Fnumber').value = value;
	document.getElementById("list-floor").innerHTML='';
	var string = "";
	for (i = 1; i <= value; i++) { 
		if(i!=1){
			string += '<a onclick="setFloor('+i+', this)" class="list-group-item">Floor '+i+'</a>';
		}else{
			FloorSelected = i;
			string += '<a onclick="setFloor('+i+', this)" class="list-group-item activated">Floor '+i+'</a>';
		}
        
    }
	$("#list-floor").append(string);	    
}

//list right space in update
function initListRight(){
	document.getElementById("list-right").innerHTML = "";
	for (var t=0; t<CloneArrayFloors.length; t++){
		var f = CloneArrayFloors[t];
		var blanks = f.blanks;
		var string = "";
		if(t!=0) string += '<div class="element">';
		else string += '<div class="element activated">';
		for(var i=0; i<f.row; i++){
			for(var j=0; j<f.column; j++){
				var b = blanks[(i*f.column +j)];
				var status = b.status;
				if(status == "empty" || status == "full"){
					string += '<div class="white black"></div>';
				}else if (status == "unavailable"){
					string += '<div class="white"></div>';
				}
			}
			string +='<div style="clear:left"></div>';
		}
		
		$("#list-right").append(string);
	}
}

function setFloor(number, item){
	
	$("div.element").each(function(index, element) {
		if($(element).hasClass("activated")){
			$(element).removeClass("activated");
		}
		if(number == (index+1)){
			$(element).addClass("activated");
		}
	})
	$("a.list-group-item").each(function(){
		if($(this).hasClass("activated")){
			$(this).removeClass("activated");
		}
	})
	$(item).addClass("activated");
	FloorSelected = number;
}


//handle when click button add a floor
function handleAddFloor(){
	addFloor();
	initListFloor();
	initListRight();
}

function addFloor(){
	var row = $("input#rowNumber").val();
	var col = $("input#colNumber").val();
	var level = CloneArrayFloors.length + 1;
	var floor = {
			row: row,
			column: col,
			level: level,
			blanks: []
	}
	for(i=0; i<row; i++){
		for(j=0; j<col; j++){
			var blank = {
					column: j,
					row: i,
					status: "empty"
			};
			floor.blanks.push(blank);
		}
	}
	CloneArrayFloors.push(floor);
	$("input#rowNumber").val(1);
	$("input#colNumber").val(1);
}
//handle when click button minus a floor
function handleDeleteFloor(){
	var check = false;
	for(var i=0; i<CloneArrayFloors.length; i++){
		if(check){
			CloneArrayFloors[i].level--;
		}else if(CloneArrayFloors[i].level == FloorSelected){
			check = true;
			if(CloneArrayFloors[i].id != undefined){
				IdListFloorDeleted.push(CloneArrayFloors[i].id);
			}
			CloneArrayFloors.splice(i, 1);
			i--;
		}
	}
	initListFloor();
	initListRight();
}

//Handle when click on save update
function handleUpdate(){
	if(checkChange()){
		ArrayFloors = CloneArrayFloors.map(floor => Object.assign({}, floor)); //clone array of object
		Parking.name = $(".new-parkinglot-name").val();
		initListSpace();
		ajaxPost_UpdateParking();
	}
}

//check change
function checkChange(){
	var t1 = JSON.stringify(ArrayFloors);
	var t2 = JSON.stringify(CloneArrayFloors);
	if(t1 != t2) return true;
	return false;
}

//Ajax 
function ajaxPost_UpdateParking(){
	Parking.floors = ArrayFloors;
	var updateParking = {
			idListFloorDeleted: IdListFloorDeleted,
			parking: Parking
	}
	$.ajax({
		url: "/user/parking/update",
		type: 'POST',
		contentType : "application/json; charset=utf-8",
	    data : JSON.stringify(updateParking),
	    success : function(data) {
	    	console.log(data);
	    	location.reload();
	    },
	    error : function(err){
	    	console.log(err);
	    }
	})
}

function ajaxPost_UpdateFloor(floor){
	$.ajax({
		url: "/user/floor/update",
		type: 'POST',
		contentType : "application/json; charset=utf-8",
	    data : JSON.stringify(floor),
	    success : function(data) {
	    	console.log(data);
	    	location.reload();
	    },
	    error : function(err){
	    	console.log(err);
	    }
	})
}

function ajaxPost_UpdateBlank(blank){
	$.ajax({
		url: "/user/blank/update",
		type: 'POST',
		contentType : "application/json; charset=utf-8",
	    data : JSON.stringify(blank),
	    success : function(data) {
	    	console.log(data);
	    },
	    error : function(err){
	    	console.log(err);
	    }
	})
}

function ajaxDeleteParking(){
	var location = "/user/parking/"+ Parking.id;
	$.ajax({
		url: location,
		type: 'DELETE',
		contentType : "application/json; charset=utf-8",
	    success : function(data) {
	    	console.log(data);
	    	window.location.replace("/user");
	    },
	    error : function(err){
	    	console.log(err);
	    }
	})
}