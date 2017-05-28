
var Tickets = [];
var ParkingTickets = [];

$(document).ready(function(){
	var temp = JSON.parse($("input#tickets").val());
	handleTicketsJSON(temp);
	
	var parkingId = $("#ParkingName").val();
	getTicketsByParkingId(parkingId);
	$("#ParkingName").on('change', function(){
		ParkingTickets = [];
		parkingId = $("#ParkingName").val();
		getTicketsByParkingId(parkingId);
	});
	
	
	$('input#txt_search').keyup(function(e){
		if(e.keyCode == 13){
			$(this).trigger("enterKey")
		}
	})
	$('input#txt_search').bind("enterKey",function(e){
		var CloneParkingTickets = [];
		var txt = $('input#txt_search').val();
		for(i=0; i<ParkingTickets.length; i++){
			if(ParkingTickets[i].code.indexOf(txt) != -1){
				CloneParkingTickets.push(ParkingTickets[i]);
			}
		}
		init(CloneParkingTickets);
	});
});
function handleTicketsJSON(tickets){
	for(i=0; i<tickets.length; i++){
		var temp = JSON.parse(tickets[i]);
		Tickets.push(temp);
	}
}

function getTicketsByParkingId(parkingId){
	for(i=0; i<Tickets.length; i++){
		if(Tickets[i].parkingId == parkingId){
			ParkingTickets.push(Tickets[i]);
		}
	}
	init(ParkingTickets);
}

function init(tickets){
	document.getElementById("ListDiary").innerHTML = '';
	var string = "";
	for (i=0; i<tickets.length; i++){
		string += '<tr onclick="selectElement('+i+', this)">' +
					'<td class="text-center">'+(i+1)+'</td>' +
					'<td class="text-center">'+tickets[i].licensePlate+'</td>' +
					'<td class="text-center">'+tickets[i].code+'</td>' +
					'<td class="text-center">'+tickets[i].timeFrom+'</td>' +
					'<td class="text-center">'+tickets[i].timeTo+'</td>' +
					'<td class="text-center">'+tickets[i].position+'</td>' +
					'<td class="text-center">'+tickets[i].status+'</td>' +
				   '</tr>';
	}
	$("#ListDiary").append(string);
}