var parking ={
	name: "",
	floors: []
}
$(document).ready(function(){
	<!-- Hide the list when show the popup -->
    $("#popup").click(function(){
    	$("#listFloor").hide();
    	$("#create-btn").prop('disabled', true);

    });
    
    <!-- show the list when press submit button floor-->
    $("button.submit").click(function(){
    	var value = document.getElementById('Fnumber').value;
    	document.getElementById("list-floor").outerHTML='';
    	if(value<=0){
    		$("#listFloor").hide();
    		var string = '<div id="list-floor">' +'</div>';
    		$(".list-left").append(string);
    	}else{
    		$("#listFloor").show(500);
            $("#btn-create").prop('disabled', false);
            var string ='<div id="list-floor">';
            for (i = 1; i <= value; i++) { 
              string += '<a onclick="setFloor('+i+')" class="list-group-item">Floor '+i+'</a>';
            }
	        string +="</div>"
	        $(".list-left").append(string);	        
    	};
    	initListRight(value);
    });
    
    $("button#btn-create").click(function(){
    	var rows = [];
    	var cols = [];
    	$("input.rowNumber").each(function(index, element) {
    		var val = $(element).val();
    		rows.push(val);
    	});
    	$("input.colNumber").each(function(index, element) {
    		var val = $(element).val();
    		cols.push(val);
    	})
    	var value = document.getElementById('Fnumber').value;
    	handleCreate(rows, cols, value);
    })
});

function handleCreate(rows, cols, num){
	parking.name = document.getElementById('nameParking').value;
	var n = 0;
	while(n<num){
		var floor = {
				column: cols[n],
				row: rows[n],
				level: n+1,
				blanks: []
		};
		for(i=0; i<rows[n]; i++){
			for(j=0; j<cols[n]; j++){
				var blank = {
						column: j,
						row: i,
						status: "empty"
				};
				floor.blanks.push(blank);
			}
		}
		parking.floors.push(floor);
		n++;
	}
	ajaxPost_CreateParking();
}

function initListRight(number){
	document.getElementById("listInputRight").innerHTML = "";
	for (i = 1; i<=number; i++){
		var string = "";
		if(i!=1) string += '<div class="element">';
		else string += '<div class="element activated">';
		string += '<div class="row">' +
						'<label>Number of Rows </label>' +
						'<input type="number" class="rowNumber" min="1" step="1" value="1"/>' +
					'</div>' +
					'<div class="row">' +
						'<label>Number of Columns </label>' +
						'<input type="number" class="colNumber" min="1" step="1" value="1"/>' +
					'</div>' +
				  '</div>';
		$(".input-right").append(string);
	}
}



function setFloor(number){
	$("div.element").each(function(index, element) {
		if($(element).hasClass("activated")){
			$(element).removeClass("activated");
		}
		if(number == (index+1)){
			$(element).addClass("activated");
		}
	})
}

//Ajax 
function ajaxPost_CreateParking(){
	$.ajax({
		url: "/user/parking/create",
		type: 'POST',
		contentType : "application/json; charset=utf-8",
	    data : JSON.stringify(parking),
	    beforeSend : function(xhr) {
            xhr.setRequestHeader('X-CSRF-Token', $("input[name=_csrf]").val());
        },
	    success : function(data) {
	    	console.log(data);
	    	location.reload();
	    },
	    error : function(err){
	    	console.log(err);
	    	console.log("error");
	    }
	})
}