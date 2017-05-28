
var Users = [];
var UserSelected = {id: 0};

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
	document.getElementById("userTable").innerHTML = '';
	var string = "";
	for (i=0; i<users.length; i++){
		var packet = users[i].packetID == 1 ? "Month" : "Year";
		string += '<tr onclick="selectUser('+i+', this)">' +
					'<td class="text-center">'+(i+1)+'</td>' +
					'<td class="text-center">'+users[i].username+'</td>' +
					'<td class="text-center">'+users[i].email+'</td>' +
					'<td class="text-center">'+users[i].address+'</td>' +
					'<td class="text-center">'+users[i].phone+'</td>' +
					'<td class="text-center">'+packet+'</td>' +
				   '</tr>';
	}
	$("#userTable").append(string);
}

function selectUser(id, element){
	$("tr").each(function(index, ele){
		if($(ele).hasClass("activated")){
			$(ele).removeClass("activated");
		}
	});
	$(element).addClass("activated");
	UserSelected = Users[id];
}

function handleUpdateUser(element){
	if($(element).hasClass("edit")){
		if(UserSelected.id == 0){
			alert("Please, Select User to Edit!");
			return;
		}
		$(".modal-title").text("Edit User");
		$("#create-btn").text("Save");
		$("input#fullname").val(UserSelected.fullname);
		$("input#username").val(UserSelected.username);
		$("input#email").val(UserSelected.email);
		$("input#password").prop("disabled", true);
		$("input#phone").val(UserSelected.phone);
		$("input#address").val(UserSelected.address);
		var packet = UserSelected.packetID == 1 ? "Month" : "Year";
		$("#package").val(packet);
		$("#Modal").modal('show');
	}else{
		UserSelected = {id: 0};
		$(".modal-title").text("Create New User");
		$("#create-btn").text("Create");
		$("input#fullname").val("");
		$("input#username").val("");
		$("input#email").val("");
		$("input#password").val("");
		$("input#phone").val("");
		$("input#address").val("");
		$("#package").val();
	}
}

function submitUpdateUser(element){
	var password = UserSelected.id == 0? $("input#password").val() : UserSelected.password;
	var packetID = $("#package").val()== "Month"? 1: 2;
	console.log(new Date(UserSelected.lastPasswordResetDate));
	var user = {
				id: UserSelected.id,
				username: $("input#username").val(),
				password: password,
				email: $("input#email").val(),				
				fullname: $("input#fullname").val(),
				address: $("input#address").val(),
				phone: $("input#phone").val(),
				packetID: packetID,
				lastPasswordResetDate: new Date(UserSelected.lastPasswordResetDate),

			};

	ajaxPost_UpdateStaff(user);
}

function handleDelete(){

	if(UserSelected.id != 0){
		ajaxDeleteUser();
	}

}


function ajaxPost_UpdateStaff(data){
	
	$.ajax({
		url: "/admin/user/update",
		type: 'POST',
		contentType : "application/json; charset=utf-8",
	    data : JSON.stringify(data),
	    success : function(data) {
	    	location.reload();
	    	alert("Success!!!");
	    },
	    error : function(err){
	    	console.log(err);
	    }
	})
}

function ajaxDeleteUser(){
	var location = "/admin/user/"+ UserSelected.id;
	$.ajax({
		url: location,
		type: 'DELETE',
		contentType : "application/json; charset=utf-8",
	    success : function(data) {
	    	window.location.replace("/admin/user-management");
	    },
	    error : function(err){
	    	console.log(err);
	    }
	})
}