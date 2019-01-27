// JavaScript Document
function init(){
	navScroll();
	setInterval(showTime,1000);
	listMenu();
	getUserInfo(1);
	getList(1);
	getShop(1);
}
function getList(num){
	var search=$('#searchOrder').val();
	$.ajax({
		type:"post",
		url:"/MDHotel/list.order",
		dataType:"json",
		data:"currentPage="+num+"&search="+search+"&type=1",
		 beforeSend: function () {
			 $('tbody').html("<tr><td colspan='7' class='text-center'><strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></td></tr>");
	       },
	       success: function (data) {
	    	   $('tbody').html("");
	    	   if(data){
	    			   $.each(data,function(index,obj){
			        	   var tr=$('<tr></tr>');
			        	   $.each(obj,function(key,value){
			        		   if(key=="order_type"){
			        			   if(value==0){
			        				   var td=$('<td>供应</td>');
			        			   }else{
			        				   var td=$('<td>购置</td>');
			        			   }
			        		   }else if(key=="order_price"){
			        			   var td=$('<td>'+value+' RMB</td>');
			        		   }else{
			        			   var td=$('<td>'+value+'</td>');
			        		   } 
			        		   tr.append(td);
			        	   })
			        	    $('tbody').append(tr);
			           })
			          turnPage(num,search);
	    	   }else{
	    		   $('tbody').html("<strong>获取数据失败！</strong>");
	    	   }    
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   $('tbody').html("<h4>获取数据失败！</h4>");
	       }
	})
}
function turnPage(num,search){
	$.ajax({
		type:"post",
		url:"/MDHotel/page.order",
		data:"search="+search+"&type=1",
		 beforeSend: function () {
			 $('#pagebox').html("<tr><td colspan='7' class='text-center'><strong style='color:green'>正在获取分页</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></td></tr>");
	       },
	       success: function (msg) {
	    	   
	    	   if(msg=="empty"){
	    		   $('tbody').html("<h4>没有数据！</h4>");
	    		   $('#pagebox').html("");
	    		   $('#totalPage').html(0);
	    		   $('#currentPage').html(0);
	    	   }else{
	    		   var totalpage=msg-0;
	    		   $('#pagebox').pagination({
	                    pageCount:totalpage,
	                    current:num,
	                    callback:function(api){
	                    	var num=api.getCurrent();
	                        getList(num);
	                    }
	                });
	    		  
	    		   $('#totalPage').html(totalpage);
	    		   $('#currentPage').html(num);
	    		  
	    	   }	 
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   $('tfoot').html("获取分页失败！");
	       }
	})
	
}
function searchOrder(){
	getList(1);
}
function readInfo(){
	$('#readModal').modal();	
}