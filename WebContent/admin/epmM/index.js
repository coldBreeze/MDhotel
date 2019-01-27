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
	var search=$('#searchStaff').val();
	$.ajax({
		type:"post",
		url:"/MDHotel/list.staff",
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
			        		   if(key=="staff_type"){
			        			   if(value==0){
			        				   var td=$('<td>员工</td>');
			        			   }else{
			        				   var td=$('<td>管理员</td>');
			        			   }
			        		   }else if(key=="staff_state"){
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
			        	   tr.append('<td><button class="btn btn-success read">查看</button> <button  class="btn btn-warning edit">编辑</button> <button class="btn btn-danger delete">删除</button></td>');
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
		url:"/MDHotel/page.staff",
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
	var arrColumn=new Array('用户名','姓名','身份','年龄','性别','入职时间','电话','地址','状态','备注','所属分店');
	$('.read').on('click',function(){
		var index=0;
		var login=$(this).parents('tr').children('td:first').html();
		$.ajax({
			type:"post",
			url:"/MDHotel/read.staff",
			data:"login="+login,
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
		    					   if(key=="staff_type"){
		    						   if(value==0){
		    							   p2=$("<p class='pull-right'>员工</p>");
		    						   }else{
		    							   p2=$("<p class='pull-right'>管理员</p>");
		    						   }			   
		    					   }else if(key=="staff_state"){
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
	$('.edit').on('click',function(){
		$('#editModal').modal();
		var login=$(this).parents('tr').children('td:first').html();
		$.ajax({
			type:"post",
			url:"/MDHotel/read.staff",
			data:"login="+login,
			dataType:"json",
		       success: function (data) {
		    	   if(data){
		    		   if(data=="fail"||data==""){
		    			   showError("读取数据失败！");   
		    		   }else{
		    		      $.each(data,function(index,obj){
		    			   $.each(obj,function(key,value){
		    				  if(key=="staff_login"){
		    					  $('#login').val(value);
		    				  }
		    				  if(key=="staff_name"){
		    					  $('#name').val(value);
		    				  }
		    				  if(key=="staff_age"){
		    					  $('#age').val(value);
		    				  }
		    				  if(key=="staff_sex"){
		    					  var sex=$('input[type=radio]');
		    					  for(var i=0;i<sex.length;i++){
		    						  if(sex[i].value==value){
		    							  sex[i].checked=true;
		    						  }
		    					  } 
		    				  }
		    				  if(key=="staff_time"){
		    					  $('#intime').val(value);
		    				  }
		    				  if(key=="staff_tel"){
		    					  $('#tel').val(value);
		    				  }
		    				  if(key=="staff_address"){
		    					  $('#address').val(value);
		    				  }
		    				  if(key=="staff_remark"){
		    					  $('#remark').val(value);
		    				  }
		    			   })
		    		   })
		    			   $('#editModal').modal();
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
		var type=$(this).parent().prev().prev().prev().html();
		if(type=="管理员"||state=="有记录"){
			showError("<div class='text-center'>该信息不能删除！<div>");
			setTimeout(closeMessage,700);
			return;
		}else{
			var login=$(this).parents('tr').children('td:first').html();
			showMessage("提示","<h4 color='black'>确定删除该条员工信息？</h4>","<button class='btn btn-success' onclick='javascript:sureDelete(\""+login+"\");'>确定</button><button class='btn btn-default' data-dismiss='modal'>取消</button>");
		}
	});	
}
function sureDelete(login){
	$.ajax({
		type:"post",
		url:"/MDHotel/delete.staff",
		data:"login="+login,
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
function editStaff(){
	if($('#name').val()==""){
		$('#name').css('border','1px solid red');
		return;
	}
	$('#name').css({'border':'0px','border-bottom':'1px dashed #ccc'});
	var str="<strong style='color:#333'>确定修改该员工信息？</strong>";
	var foot=' <button type="button" class="btn btn-success" onClick="javascript:sureEdit();">确定</button><button class="btn btn-default" data-dismiss="modal">取消</button>';
	showMessage('提示',str,foot);
}
function sureEdit(){
	var sex="";
	var arr=$("input[type=radio]");
	 for(var i=0;i<arr.length;i++){
		  if(arr[i].checked){
			  sex=arr[i].value;
		  }
	  } 
	var data="login="+$('#login').val()+"&name="+$('#name').val()+"&age="+$('#age').val()+"&sex="+sex+"&time="+$('#intime').val()+"&tel="+$('#tel').val()+"&address="+$('#address').val()+"&remark="+$('#remark').val()+"&type=1";
	$.ajax({
		type:"post",
		url:"/MDHotel/update.staff",
		data:data,
		 beforeSend: function () {
			 showMessage("提示","<strong style='color:green'>正在修改</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>",null);	 
		 },
	       success: function (msg) {
	    	    if(msg=="success"){
	    	    	showMessage("提示","<strong style='color:green'>修改成功！</strong>",null);
	    	    	$('#editModal').modal('hide');
	    	    	setTimeout(closeMessage,700);
	    	    	getList(1);
	    	    }else if(msg=="fail"){
	    	    	showError("修改失败！");
	    	    	setTimeout(closeMessage,700);
	    	    }else if(msg=="error"){
	    	    	showError("操作错误！");
	    	    	setTimeout(closeMessage,700);
	    	    }
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   showError("操作失败！");
	       }
	})	
}
function searchStaff(){
	getList(1);
}
