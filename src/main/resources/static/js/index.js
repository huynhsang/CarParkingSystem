$(document).ready(function(){
	
});

function RegisterUser(){
	var user = {
			username: $("input#username").val(),
			password: $("input#password").val(),
			email: $("input#email").val(),				
			fullname: $("input#fullname").val(),
			address: $("input#address").val(),
			phone: $("input#phone").val(),
			packetID: $("input#package").val(),
			lastPasswordResetDate: new Date(),

		};

	ajaxPost_Register(user);
}

function ajaxPost_Register(data){
	
	$.ajax({
		url: "/register",
		type: 'POST',
		contentType : "application/json; charset=utf-8",
	    data : JSON.stringify(data),
	    success : function(data) {
	    	alert("Success!!!");
	    	window.location = "/";
	    },
	    error : function(err){
	    	alert("Error");
	    }
	})
}