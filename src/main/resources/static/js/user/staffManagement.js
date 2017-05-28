
var Staffs = [];
var StaffSelected = {id: 0};

$(document).ready(function(){
	handleJSON(JSON.parse($("input#staffs").val()));
	initStaffTable(Staffs);

	$('input#txt_search').keyup(function(e){
		if(e.keyCode == 13){
			$(this).trigger("enterKey")
		}
	})
	$('input#txt_search').bind("enterKey",function(e){
		var CloneStaffs = [];
		var txt = $('input#txt_search').val();
		for(i=0; i<Staffs.length; i++){
			if(Staffs[i].fullname.indexOf(txt) != -1){
				CloneStaffs.push(Staffs[i]);
			}
		}
		initStaffTable(CloneStaffs);
	});
});

function handleJSON(data){
	for(i=0; i<data.length; i++){
		var s = JSON.parse(data[i]);
		Staffs.push(s);
	}
}

function initStaffTable(staffs){
	document.getElementById("staffTable").innerHTML = '';
	var string = "";
	for (i=0; i<staffs.length; i++){
		string += '<tr onclick="selectStaff('+i+', this)">' +
					'<td class="text-center">'+(i+1)+'</td>' +
					'<td class="text-center">'+staffs[i].fullname+'</td>' +
					'<td class="text-center">'+staffs[i].username+'</td>' +
					'<td class="text-center">'+staffs[i].email+'</td>' +
					'<td class="text-center">'+staffs[i].phone+'</td>' +
					'<td class="text-center">'+staffs[i].parkingname+'</td>' +
				   '</tr>';
	}
	$("#staffTable").append(string);
}

function selectStaff(id, element){
	$("tr").each(function(index, ele){
		if($(ele).hasClass("activated")){
			$(ele).removeClass("activated");
		}
	});
	$(element).addClass("activated");
	StaffSelected = Staffs[id];
}

function handleUpdateStaff(element){
	if($(element).hasClass("edit")){
		if(StaffSelected.id == 0){
			alert("Please, Select Staff to Edit!");
			return;
		}
		$(".modal-title").text("Edit Staff");
		$("#create-btn").text("Save");
		$("input#fullname").val(StaffSelected.fullname);
		$("input#username").val(StaffSelected.username);
		$("input#email").val(StaffSelected.email);
		$("input#phone").val(StaffSelected.phone);
		$("input#address").val(StaffSelected.address);
		$("#parking").val(StaffSelected.parkingId);
		$("#Modal").modal('show');
	}else{
		StaffSelected = {id: 0};
		$(".modal-title").text("Create New Staff");
		$("#create-btn").text("Create");
		$("input#fullname").val();
		$("input#username").val();
		$("input#email").val();
		$("input#password").val();
		$("input#phone").val();
		$("input#address").val();
		$("#parking").val();
	}
}

function submitUpdateStaff(element){
	var staff = {
				id: StaffSelected.id,
				fullname: $("input#fullname").val(),
				username: $("input#username").val(),
				email: $("input#email").val(),
				password: $("input#password").val(),
				phone: $("input#phone").val(),
				address: $("input#address").val(),
			};
	var parkingId = $("#parking").val() != null ? $("#parking").val(): 0;
	var request = {
		staff: staff,
		parkingId: parkingId
	}

	ajaxPost_UpdateStaff(request);
}

function handleDelete(){

	if(StaffSelected.id != 0){
		ajaxDeleteStaff();
	}

}


function ajaxPost_UpdateStaff(data){
	
	$.ajax({
		url: "/user/staff/update",
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

function ajaxDeleteStaff(){
	var location = "/user/staff/"+ StaffSelected.id;
	$.ajax({
		url: location,
		type: 'DELETE',
		contentType : "application/json; charset=utf-8",
	    success : function(data) {
	    	window.location.replace("/user/staff-management");
	    },
	    error : function(err){
	    	console.log(err);
	    }
	})
}