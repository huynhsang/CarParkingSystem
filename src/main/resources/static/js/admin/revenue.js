
var Users = [];

$(document).ready(function(){
	handleJSON(JSON.parse($("input#users").val()));
	initUserTable(Users);

	$('input#txt_search').keyup(function(e){
		if(e.keyCode == 13){
			$(this).trigger("enterKey")
		}
	})
	$('input#txt_search').bind("enterKey",function(e){
		var CloneUsers = [];
		var txt = $('input#txt_search').val();
		for(i=0; i<Users.length; i++){
			if(Users[i].username.indexOf(txt) != -1){
				CloneUsers.push(Users[i]);
			}
		}
		initUserTable(CloneUsers);
	});
});

function handleJSON(data){
	for(i=0; i<data.length; i++){
		var u = JSON.parse(data[i]);
		Users.push(u);
	}
}

function initUserTable(users){
	document.getElementById("ListRevenue").innerHTML = '';
	var string = "";
	for (i=0; i<users.length; i++){
		var packet = users[i].packetID == 1 ? "Month" : "Year";
		var cost = packet == "Month"? "100.000 VND" : "1.000.000 VND";
		string += '<tr onclick="selectUser('+i+', this)">' +
					'<td class="text-left">'+(i+1)+'</td>' +
					'<td class="text-left">'+users[i].username+'</td>' +
					'<td class="text-left">'+users[i].phone+'</td>' +
					'<td class="text-left">'+users[i].lastPasswordResetDate+'</td>' +
					'<td class="text-left">'+packet+'</td>' +
					'<td class="text-left">'+cost+'</td>' +
				   '</tr>';
	}
	$("#ListRevenue").append(string);
}

function RevenueSearching(){
	var from = new Date();
	var to = new Date();
	from.setDate($("#day-from").val());
	from.setMonth($("#month-from").val()-1);
	from.setFullYear($("#year-from").val());

	to.setDate($("#day-to").val());
	to.setMonth($("#month-to").val()-1);
	to.setFullYear($("#year-to").val());

	var SumPrice = 0;
	for(i=0; i< Users.length; i++){
		var temp = new Date(Users[i].lastPasswordResetDate);
		if(temp.getTime() >= from.getTime() && temp.getTime() <= to.getTime()){
			var cost = Users[i].packetID == 1? 100000 : 1000000;
			SumPrice += cost;
		}
		
	}
	console.log("AAAAA");
	$("#PriceTotal").val(SumPrice + " VND");
}
