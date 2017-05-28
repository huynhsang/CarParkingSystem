var ArrayFloors = [];
var Parking = {id: 0, name: "", price: "", floors: []};
var TicketInfo = {level: 0, position: "", blank: {}};
var Tickets = [];
var Out_Ticket = {};
var Out_Blank = {};

$(document).ready(function(){
	var parking = JSON.parse($("input#parking").val());

	handleJSONFloors(parking.floors);
	Parking.id = parking.id;
	Parking.name = parking.name;
	Parking.price = parking.price;
	
	
	handleJSONTickets($("input#tickets").val());

	$("#numOfFloor").on('change', function(){
		initListSpace();
	});
});

function handleJSONTickets(json){
	var tickets = JSON.parse(json);
	for(var i=0; i<tickets.length; i++){
		var ticket = JSON.parse(tickets[i]);
		Tickets.push(ticket);
	}

}

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
	var count = 0;
	var block = 0;
	for(var i=0; i<row; i++){
		for(var j=0; j<column; j++){
			var temp = (i*column +j);
			var b = blanks[temp];
			var status = b.status;
			if(status == "empty" || status == "full"){
				var A2 = "A2";
				str += '<div class="block '+status+'" onclick="updateBlank('+temp+', '+level+', this)">'+alphabet_row[i]+j+'</div>';
			}else if (status == "unavailable"){
				block++;
				str += '<div class="block '+status+'"></div>';
			}
			if(status == "full") count++;
		}
		str +='<div style="clear:left"></div>';
	}
	$("span#amountOfCar").text(count + "/" + (blanks.length - block));
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

//handle update space
function updateBlank(index, level, element){

	let obj = ArrayFloors[level-1];
	var blank = obj.blanks[index];
	var floor = {
			id: obj.id,
	}
	blank.floor = floor;

	if($(element).hasClass("empty")){
		$("div.empty").each(function(num, el) {
			if($(el).hasClass("activated")){
				$(el).removeClass("activated");
			}
		});
		$(element).addClass("activated");

		var alphabet_row = initRow(blank.row+1);
		TicketInfo.position = alphabet_row[blank.row] + blank.column;
		TicketInfo.level = level;
		TicketInfo.blank = blank;

	}else{
		var numOfFloor = $("#numOfFloor").val();
		var position = $(element).text();
		for(var i=0; i<Tickets.length; i++){
			var t = Tickets[i];
			if(t.floor == numOfFloor && t.position == position){
				$("#Out_BlankPosition").text(position);
				$("#Out_LicensePlate").text(t.licensePlate);
				$("#Out_TicketCode").text(t.code);
				$("#Out_TimeFrom").text(t.timeFrom);
				t.timeTo = new Date();
				t.timeFrom = new Date(t.timeFrom);
				t.status = "completed";
				Out_Blank = blank;
				Out_Ticket = t;
				$('#myModal2').modal('show');
				return;
			}
		}
		
	}


}

function generateTicketCode(){
	var a = Math.random()*9999;
    if(a<1000) a+= 1000;
    return Math.floor(a);
}

//handle when click on Print Ticket Button
function printTicket(){
	if(TicketInfo.level == 0){
		alert("Please!, Select Your Car Position.");
		return;
	}
	$('#myModal1').modal('show');
	$("#position").text(TicketInfo.position + " - F" + TicketInfo.level);
}

//handle print ticket
function getTicket(){
	var input = $("#licensePlate").val().trim();
	if(input == ""){
		alert("Please!, Input Your License Plate.");
		return;
	}
	var ticket = {};
	var code = TicketInfo.position + generateTicketCode();
	$("#ticketCode").text("Ticket code: "+ code);
	ticket.licensePlate = input;
	ticket.code = code;
	ticket.position = TicketInfo.position;
	ticket.floor = TicketInfo.level;
	ticket.timeFrom = new Date();
	ticket.fee = Parking.price;
	ticket.status = "using";
	ticket.parkingId = Parking.id;
	ticket.staffName = $("input#staffName").val();

	TicketInfo.blank.status = "full";
	ajaxPost_HandleTicket(ticket, TicketInfo.blank);
}

function returnTicket(){
	Out_Blank.status = "empty";
	ajaxPost_HandleTicket(Out_Ticket, Out_Blank);
	$('#myModal2').modal('hide');
}

function clear(){
	initListSpace();
	TicketInfo = {level: 0, position: "", blank: {}};
	Tickets = [];
}

function ajaxPost_HandleTicket(ticket, blank){
	var data = {ticket: ticket, blank: blank};
	
	$.ajax({
		url: "/staff/ticket",
		type: 'POST',
		contentType : "application/json; charset=utf-8",
	    data : JSON.stringify(data),
	    success : function(data) {
	    	clear();
	    	handleJSONTickets(data);
	    	alert("Success!!!");
	    },
	    error : function(err){
	    	console.log(err);
	    }
	})
}
