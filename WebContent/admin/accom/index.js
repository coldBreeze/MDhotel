// JavaScript Document
function init(){
	navScroll();
	listMenu();
	setInterval(showTime,1000);
	getUserInfo(1);
	getList(1);
	getShop(1);
}
function getList(num){
	
	var search=$('#searchAccom').val();
	$.ajax({
		type:"post",
		url:"/MDHotel/list.accom",
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
			        		   if(key=="accom_code"){
			        			   var td=$('<td><a class="read" title="查看">'+value+'</a></td>');
			        		   }else if(key=="accom_state"){
			        			   if(value==0){
			        				   var td=$('<td>未结算</td>');
			        			   }else{
			        				   var td=$('<td>已结算</td>');
			        			   }
			        		   }else if(key=="accom_outime"){
			        			   var td=$('<td>'+value+'</td>');
			        		   }else{
			        			   var td=$('<td>'+value+'</td>');
			        		   } 
			        		   tr.append(td);
			        	   })
			        	    $('tbody').append(tr);
			           })
			            bindDo();
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
		url:"/MDHotel/page.accom",
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
function bindDo(){
	var arrColumn=new Array('订单编码','房间号','房间名','房间价格','旅客编号','旅客姓名','入住时间','退房时间','订单编号','押金','总价','备注');
	$('.read').on('click',function(){
		var index=0;
		var code=$(this).html();
		$.ajax({
			type:"post",
			url:"/MDHotel/read.accom",
			data:"code="+code,
			dataType:"json",
			 beforeSend: function () {
				 $('#readModal ul').addClass('hidden');
				 $('#readMsg').html("<strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>");	 
			 },
		       success: function (data) {
		    	   if(data){
		    		   if(data=="fail"||data==""){
		    			   showError("fail读取数据失败！");   
		    		   }else{
		    			   $('#readModal ul').html("");
		    		      $.each(data,function(index,obj){
		    			   $.each(obj,function(key,value){
		    				   var li=$('<li class="list-group-item  clearfix"></li>');
		    				   var p1=$("<p class='pull-left'>"+arrColumn[index++]+"</p>");
		    				   var p2=$("<p class='pull-right'>"+value+"</p>");
		    				   li.append(p1).append(p2);
		    				   $('#readModal .list-group').append(li);
		    			   })
		    		   })
		    		   $('#readModal ul').removeClass('hidden');
		    			   $('#readMsg').addClass('hidden');
		    			   $('#readModal').modal();
		    		   }
		    	   }else{
		    		   showError("error读取数据失败！");
		    	   }  
		       },
		       error: function (XMLHttpRequest, textStatus, thrownError) {
		    	   showError("error1读取数据失败！");
		       }
		})
	});	
}
function searchAccom(){
	getList(1);
}