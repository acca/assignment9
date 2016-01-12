/**
 * 
 */
$(document).ready(function(){

	var contents = $('.name').html();
	var prodId = $('.name').attr('prodId');
	$('.name').blur(function() {
		if (contents!=$(this).html()){			
			contents = $(this).html();			
			renameProduct($(this).text(), prodId);
		}
	});

	function renameProduct(newName, prodId){
		$.ajax({
			url: "Controller?op=changeName&prodId="+prodId+"&newName="+newName,
			context: document.body
		}).done(function() {
			updateLogs();
		});
		console.log('Product '+prodId+' renamed with '+newName);
	}
	
	function updateLogs(){
		$.ajax({
			url: "Controller?op=asyncUpdateLog",
			dataType: "text",
			type: "GET",
			statusCode :{
				200: function(response) {
					$(response).prependTo('#logs')
				}			
			}
		});
	}
	
});
