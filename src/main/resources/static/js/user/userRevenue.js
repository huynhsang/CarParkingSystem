
var Tickets = [];
var SumPrice = 0;

$(document).ready(function(){
	$("#create-btn").click(function(){
		var price = $("#price").val();
		if(price == ""){
			alert("Please, Input Your Parking Price!");
			return;
		}
		$("#parkingPrice").text(price + " VND");
		AjaxPost_UpdateParkingPrice(price);
	});

	$("a.edit").click(function(){
		$("#price").val($("#parkingPrice").text());
		$("#editModal").modal("show");
	})
	var temp = JSON.parse($("input#tickets").val());
	handleTicketsJSON(temp);
	init();
});

function handleTicketsJSON(tickets){
	for(i=0; i<tickets.length; i++){
		var temp = JSON.parse(tickets[i]);
		Tickets.push(temp);
	}
}

function init(){
	var d = new Date();
	var yesterday = new Date(Date.now() - 864e5);
	$("#day-from").val(yesterday.getDate());
	$("#month-from").val(yesterday.getMonth());
	$("#year-from").val(yesterday.getFullYear());
	$("#day-to").val(d.getDate());
	$("#month-to").val(d.getMonth());
	$("#year-to").val(d.getFullYear());
	for(i=0; i< Tickets.length; i++){
		var temp = new Date(Tickets[i].timeTo);
		if(temp.getTime() >= yesterday.getTime() && temp.getTime() <= d.getTime()){
			SumPrice += Tickets[i].fee;
		}
	}

	$("#revenue").text(SumPrice + " VND");
}

function RevenueSearching(){
	var from = new Date();
	var to = new Date();
	from.setDate($("#day-from").val());
	from.setMonth($("#month-from").val());
	from.setFullYear($("#year-from").val());

	to.setDate($("#day-to").val());
	to.setMonth($("#month-to").val());
	to.setFullYear($("#year-to").val());

	var SumPrice = 0;
	for(i=0; i< Tickets.length; i++){
		var temp = new Date(Tickets[i].timeTo);
		if(temp.getTime() >= from.getTime() && temp.getTime() <= to.getTime()){
			SumPrice += Tickets[i].fee;
		}
	}
	$("#revenue").text(SumPrice + " VND");
}

function AjaxPost_UpdateParkingPrice(price){
	$.ajax({
		url: "/user/revenue/update",
		type: 'POST',
		contentType : "application/json; charset=utf-8",
	    data : price,
	    success : function(data) {
	    	alert("Success!!!");
	    	$("#editModal").modal("hide");
	    },
	    error : function(err){
	    	console.log(err);
	    }
	})
}