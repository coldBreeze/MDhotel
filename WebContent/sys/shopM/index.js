// JavaScript Document
function init(){
	navScroll();
	listMenu();
	setInterval(showTime,1000);
	getUserInfo(2);
	getList(1);
}
function getList(num){
	var search=$('#searchShop').val();
	$.ajax({
		type:"post",
		url:"/MDHotel/list.shop",
		dataType:"json",
		data:"currentPage="+num+"&search="+search+"&type=2",
		 beforeSend: function () {
			 $('tbody').html("<tr><td colspan='7' class='text-center'><strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></td></tr>");
	       },
	       success: function (data) {
	    	   $('tbody').html("");
	    	   if(data){
	    			   $.each(data,function(index,obj){
			        	   var tr=$('<tr></tr>');
			        	   $.each(obj,function(key,value){
			        		 if(key=="shop_state"){
			        			   if(value==0){
			        				   var td=$('<td>无记录</td>');
			        			   }else{
			        				   var td=$('<td>有记录</td>');
			        			   }
			        		   }else{
			        			   var td=$('<td>'+value+'</td>');
			        		   } 
			        		   tr.append(td);
			        	   })
			        	   tr.append('<td><button class="btn btn-success read">查看</button> <button class="btn btn-danger delete">删除</button></td>');
			        	    $('tbody').append(tr);
			           })
			            bindDo();
			          turnPage(num,search);
	    	   }else{
	    		   $('tbody').html("<strong>获取数据失败！</strong>");
	    	   }    
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   showError("获取数据失败！！");
	       }
	})
}
function turnPage(num,search){
	$.ajax({
		type:"post",
		url:"/MDHotel/page.shop",
		data:"search="+search+"&type=2",
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
	var arrColumn=new Array('分店编码','名称','地址','注册时间','状态','备注');
	$('.read').on('click',function(){
		var index=0;
		var code=$(this).parents('tr').children('td:first').html();
		$.ajax({
			type:"post",
			url:"/MDHotel/read.shop",
			data:"code="+code,
			dataType:"json",
			 beforeSend: function () {
				 $('#readModal ul').addClass('hidden');
				 $('#readMsg').html("<strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>");	 
			 },
		       success: function (data) {
		    	   if(data){
		    		   if(data=="fail"||data==""){
		    			   showError("读取数据失败！");   
		    		   }else{
		    			   $('#readModal ul').html("");
		    		      $.each(data,function(index,obj){
		    			   $.each(obj,function(key,value){  
		    				   if(key!="staff_pwd"){
		    					   var li=$('<li class="list-group-item  clearfix"></li>');
		    					   var p1=$("<p class='pull-left'>"+arrColumn[index++]+"</p>");
		    					   var p2;
		    					   if(key=="shop_state"){
		    						   if(value==0){
		    							   p2=$("<p class='pull-right'>无记录</p>");
		    						   }else{
		    							   p2=$("<p class='pull-right'>有记录</p>");
		    						   }			   
		    					   } else{
		    						   p2=$("<p class='pull-right'>"+value+"</p>");
		    					   }
		    					  
		    					   li.append(p1).append(p2);
			    				   $('#readModal .list-group').append(li);
		    				   }     
		    			   })
		    		   })
		    		   $('#readModal ul').removeClass('hidden');
		    			   $('#readMsg').addClass('hidden');
		    			   $('#readModal').modal();
		    		   }
		    	   }else{
		    		   showError("读取数据失败！");
		    	   }  
		       },
		       error: function (XMLHttpRequest, textStatus, thrownError) {
		    	   showError("读取数据失败！");
		       }
		})
	});	
	$('.delete').on('click',function(){
		var state=$(this).parent().prev().html();
		if(state=="有记录"){
			showError("<div class='text-center'>该信息不能删除！<div>");
			setTimeout(closeMessage,700);
			return;
		}else{
			var code=$(this).parents('tr').children('td:first').html();
			showMessage("提示","<h4 color='black'>确定删除该条分店信息？</h4>","<button class='btn btn-success' onclick='javascript:sureDelete(\""+code+"\");'>确定</button><button class='btn btn-default' data-dismiss='modal'>取消</button>");
		}
	});	
}
function sureDelete(code){
	$.ajax({
		type:"post",
		url:"/MDHotel/delete.shop",
		data:"code="+code,
	       success: function (msg) {
	    	  if(msg=="success"){
	    		  showMessage("提示","<strong style='color:green'>删除成功！</strong>",null);
	    	    	setTimeout(closeMessage,700);
	    	    	getList(1);
	    	   }else{
	    		   showError("删除失败！");
	    	   }  
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   showError("操作失败！");
	       }
	})
}

function searchShop(){
	getList(1);
}
