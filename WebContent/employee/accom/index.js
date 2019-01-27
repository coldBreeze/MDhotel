// JavaScript Document
var EDIT_ACCOM="";
var FLAG=0;
function init(){
	navScroll();
	listMenu();
	setInterval(showTime,1000);
	getUserInfo(0);
	getList(1);
	getShop(0);
}
function getList(num){
	var search=$('#searchAccom').val();
	$.ajax({
		type:"post",
		url:"/MDHotel/list.accom",
		dataType:"json",
		data:"currentPage="+num+"&search="+search+"&type="+0,
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
			        			   }else if(value==1){
			        				   FLAG=1;
			        				   var td=$('<td>已结算</td>');   
			        			   }else{
			        				   var td=$('<td>已预订</td>');
			        				   FLAG=2;
			        			   }
			        		   }else if(key=="accom_outime"){
			        				   var td=$('<td>'+value+' <a class="edit">编辑</a> </td>');  
			        		   }else{
			        			   var td=$('<td>'+value+'</td>');
			        		   } 
			        		   tr.append(td);
			        	   })
			        	   if(FLAG==1){
			        		   tr.append('<td><i class="fa fa-window-minimize"></i></td>');
			        		   tr.children().children('.edit').css('display','none');
			        	   }else if(FLAG==2){
			        		   tr.append('<td><button class="btn btn-danger inAccom">入住</button> <button  class="btn btn-success gongying">供应</button> </td>');
			        	   }else{
			        		   tr.append('<td><button class="btn btn-danger outAccom">退房</button> <button  class="btn btn-success gongying">供应</button> </td>');
			        	   }
			        	  
			        	    $('tbody').append(tr);
			        	    FLAG=0;
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
		data:"search="+search+"&type="+0,
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
	$('.edit').on('click',function(){
		var state=$(this).parent().prev().html();
		if(state=="已结算"){
			showError("已结算无法编辑");
		}
		$('#editModal').modal();
		var code=$(this).parent().prev().prev().prev().prev().children().html();
		$.ajax({
			type:"post",
			url:"/MDHotel/read.accom",
			data:"code="+code,
			dataType:"json",
		       success: function (data) {
		    	   if(data){
		    		   if(data=="fail"||data==""){
		    			   showError("读取数据失败！");   
		    		   }else{
		    		      $.each(data,function(index,obj){
		    			   $.each(obj,function(key,value){
		    				  if(key=="accom_code"){
		    					  $('#editCode').val(value);
		    				  }
		    				  if(key=="accom_room"){
		    					  $('#editRoom').val(value);
		    				  }
		    				  if(key=="room_price"){
		    					  $('#editRoom').attr('alt',value);
		    				  }
		    				  if(key=="accom_outime"){
		    					  $('#editTime').attr('alt',value);
		    					  $('#editTime').val(value);
		    				  }
		    				  if(key=="accom_intime"){
		    					  $('#editTime').attr('title',value);
		    				  }
		    				  if(key=="accom_price"){
		    					  $('#editPrice').val(value);
		    				  }
		    				  if(key=="accom_remark"){
		    					  $('#editRemark').val(value);
		    				  }
		    			   })
		    		   })
		    			   $('#editModal').modal();
		    		   }
		    	   }else{
		    		   showError("读取数据失败！");
		    	   }  
		    	   editTimeInit();
		       },
		       error: function (XMLHttpRequest, textStatus, thrownError) {
		    	   showError("读取数据失败！");
		       }
		})
		
	});	
	$('.gongying').on('click',function(){
		EDIT_ACCOM=$(this).parents('tr').children('td:first').children().html();
		goodList();
	})
	$('.outAccom').on('click',function(){
		EDIT_ACCOM=$(this).parents('tr').children('td:first').children().html();
		$.ajax({
			type:"post",
			url:"/MDHotel/read.accom",
			data:"code="+EDIT_ACCOM,
			dataType:"json",
			 beforeSend: function () {
				showMessage("提示","<strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>",null);	 
			 },
		       success: function (data) {
		    	   if(data){
		    		   var str="";
		    		   if(data=="fail"||data==""){
		    			   showError("fail读取数据失败！");   
		    		   }else{
		    		      $.each(data,function(index,obj){
		    			   $.each(obj,function(key,value){
		    				   if(key=="accom_code"){
		    					   str+="<p style='font-size:1.2em;color:#333'>订单编码："+value+"</p>";
		    				   }
		    				   if(key=="accom_room"){
		    					   str+="<p style='font-size:1.2em;color:#333'>房间号："+value+"</p>";
		    				   }
		    				   if(key=="accom_pass"){
		    					   str+="<p style='font-size:1.2em;color:#333'>旅客编码："+value+"</p>";
		    				   }
		    				   if(key=="pass_name"){
		    					   str+="<p style='font-size:1.2em;color:#333'>旅客姓名："+value+"</p>";
		    				   }
		    				   if(key=="accom_price"){
		    					   str+="<p style='font-size:1.2em;color:#333'>总价："+value+"</p>";
		    				   }
		    			   })
		    		   })
		    		   showMessage("提示",str,"<button class='btn btn-success' onclick='javascript:sureOutAccom();'>结算</button><button class='btn btn-default' data-dismiss='modal'>取消</button>");
		    		   }
		    	   }else{
		    		   showError("error读取数据失败！");
		    	   }  
		       },
		       error: function (XMLHttpRequest, textStatus, thrownError) {
		    	   showError("error1读取数据失败！");
		       }
		})
		var str="<ul class='list-unstyle'>"
			+"<li><p class='pull-left'></p><p class='pull-right'></p></li>";
	})
	$('.inAccom').on('click',function(){
		var code=$(this).parents('tr').children('td:first').children().html();
		$.ajax({
			type:"post",
			url:"/MDHotel/in.accom",
			data:"code="+code,
			 beforeSend: function () {
				showMessage("提示","<strong style='color:green'>正在修改</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>",null);	 
			 },
		       success: function (data) {
		    	 
		    	   if(data=="success"){
		    		  closeMessage();
		    		 getList(1);
		    	   }else{
		    		   showError("入住失败！");
		    	   }  
		       },
		       error: function (XMLHttpRequest, textStatus, thrownError) {
		    	   showError("error操作失败！");
		       }
		})
		var str="<ul class='list-unstyle'>"
			+"<li><p class='pull-left'></p><p class='pull-right'></p></li>";
	})
}
function sureOutAccom(){
	$.ajax({
		type:'post',
		url:'/MDHotel/out.accom',
		data:"code="+EDIT_ACCOM,
		 beforeSend: function () {
				showMessage("提示","<strong style='color:green'>正在结算</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>",null);	 
			 },
		       success: function (msg) {
		    	   if(msg=="success"){ 
		    		   showMessage("提示","<h4 style='color:green'>结算成功！</h4>",null);
		    		   setTimeout(closeMessage,700);
		    		   getList(1);
		    	   }else{
		    		   showError("结算失败！");
		    		   setTimeout(closeMessage,700);
		    	   }  
		       },
		       error: function (XMLHttpRequest, textStatus, thrownError) {
		    	   showError("error1操作失败！");
		    	   setTimeout(closeMessage,700);
		       }
	})
}
function searchAccom(){
	getList(1);
}
function editTimeInit(){
	$('#editTime').change(function(){
		if($(this).val()==""){
			$(this).css('border','1px solid red');
			return;
		}
		$(this).css({'border':'0px','border-bottom':'1px dashed #ccc'});
		var intime=new Date($(this).attr('title'));
		var old=new Date($(this).attr('alt'));
		var newT=new Date($(this).val());
		var now=new Date();
		var eday=newT.getTime()-intime.getTime();
		if(eday<=0){
			$('#editTime').val($(this).attr('alt'));
			showError("不能早于入住时间！");
			setTimeout(closeMessage,700);
			return;
		}
		if((newT.getTime()-now.getTime())<0){
			$('#editTime').val($(this).attr('alt'));
			showError("不能早于今天！");
			setTimeout(closeMessage,700);
			return;
		}
		$(this).attr('alt',$(this).val());
		var day=(newT.getTime()-old.getTime())/(1000*60*60*24);
		var rprice=$('#editRoom').attr('alt');
		var eprice=day*parseInt(rprice);
		var oldP=$('#editPrice').val();
		$('#editPrice').val(parseInt(oldP)+eprice);
	})
	
}
function editAccom(){
	if($('#editTime').val()==''){
		$('#editTime').css('border','1px solid red');
		return;
	}
	$('#editTime').css({'border':'0px','border-bottom':'1px dashed #ccc'});
	var data="code="+$('#editCode').val()+"&outime="+$('#editTime').val()+"&price="+$('#editPrice').val()+"&remark="+$('#editRemark').val();
	$.ajax({
		type:'post',
		url:'/MDHotel/update.accom',
		data:data,
		beforeSend:function(){
			showMessage("提示","<strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>",null);
		},
		success:function(msg){
			if(msg=="success"){
			$('#editModal').modal('hide');
			showMessage("提示","<strong style='color:green'>编辑成功！</strong>",null);
			setTimeout(closeMessage,700);
			getList(1);
			}else if(msg=="fail"){
				showError("编辑失败！");
			}else{
				showError("操作失败！");
			}
		},
		 error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   showError("操作失败error！");
	       }
	})
}
function goodList(){
	getgoodList(1);
	$('#goodsModal').modal();
}
function getgoodList(num){
	var search=$('#searchGoods').val();
	$.ajax({
		type:"post",
		url:"/MDHotel/list.goods",
		dataType:"json",
		data:"currentPage="+num+"&search="+search,
		 beforeSend: function () {
			 $('#goodTbody').html("<tr><td colspan='3' class='text-center'><strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></td></tr>");
	       },
	       success: function (data) {
	    	   $('#goodTbody').html("");
	    	   if(data){
	    			   $.each(data,function(index,obj){
			        	  var tr=$('<tr></tr>');
			        	   $.each(obj,function(key,value){	
			        			   var td=$('<td>'+value+'</td>');
			        			   tr.append(td);
			        	   })
			        	   var td1=$('<td><button class="btn btn-primary buy">供应</button></td>');
			        	   tr.append(td1);
			        	   $('#goodTbody').append(tr);
			           })
			         buyGoods();
			          goodturnPage(num,search);
	    	   }else{
	    		   $('#goodTbody').html("<tr><td colspan='3' class='text-center'>获取数据失败！</td></tr>");
	    	   }    
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   $('#goodTbody').html("<tr><td colspan='3' class='text-center'>获取数据失败！</td></tr>");
	       }
	})
}
function goodturnPage(num,search){
	$.ajax({
		type:"post",
		url:"/MDHotel/page.goods",
		data:"search="+search,
		 beforeSend: function () {
			 $('#goodpagebox').html("<div class='text-center'><strong style='color:green'>正在获取分页</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></div>");
	       },
	       success: function (msg) {
	    	   
	    	   if(msg=="empty"){
	    		   $('#goodTbody').html("<h4>没有数据！</h4>");
	    		   $('#goodpagebox').html("");
	    		   $('#goodtotalPage').html(0);
	    		   $('#goodcurrentPage').html(0);
	    	   }else{
	    		   var totalpage=msg-0;
	    		   $('#goodpagebox').pagination({
	                    pageCount:totalpage,
	                    current:num,
	                    callback:function(api){
	                    	var num=api.getCurrent();
	                        getgoodList(num);
	                    }
	                });
	    		   $('#goodtotalPage').html(totalpage);
	    		   $('#goodcurrentPage').html(num);
	    		  
	    	   }	 
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   $('#goodpagebox').html("获取分页失败！");
	       }
	})
	
}
function searchGoods(){
	getgoodList(1);
}
function buyGoods(){
	$('.buy').on('click',function(){
		var count=parseInt($(this).parent().prev().html());
		if(count<=0){
			showError("该日用品库存不足！");
			return;
		}
		var name=$(this).parent().prev().prev().prev().html();
		var price=$(this).parent().prev().prev().html();
		var code=$(this).parent().prev().prev().prev().prev().html();
		$('#goodsCode').html(code);
		$('#goodsName').html(name);
		$('#goodsPrice').html(price);
		$('#goodsCount').html(count);
		$('#buyModal').modal();
	})
}
function addGoods(){
	if($('#buyGoodNum').val()==""){
		$('#buyGoodNum').css('border','1px solid red');
		return;
	}
	$('#buyGoodNum').css({'border':'0px','border-bottom':'1px dashed #ccc'});
	if(parseInt($('#buyGoodNum').val())>parseInt($('#goodsCount').html())){
		showError("该日用品库存不足！");
		return;
	}
	sureGoods();
}
function sureGoods(){
	var price=parseInt($('#buyGoodNum').val())*parseInt($('#goodsPrice').html());
	var count=-parseInt($('#buyGoodNum').val());
	var data="goods="+$('#goodsCode').html()+"&num="+count+"&price="+price+"&type="+0;
	$.ajax({
		type:'post',
		url:'/MDHotel/update.order',
		data:data,
		success:function(msg){
			if(msg=="fail"){
				showError("供应失败！");
				setTimeout(closeMessage,700);
			}else if(msg=="error"){
				showError("供应失败！");
				setTimeout(closeMessage,700);
			}else{
				showMessage("提示","<h4 class='text-center' style='color:green'>供应成功！</h4>",null);
				setTimeout(closeMessage,700);
				$('#buyModal').modal('hide');
				getgoodList(1);
				var arr=msg.split(",");
				gongYing(EDIT_ACCOM,arr[0],arr[1]);
			}	
		},
		error: function (XMLHttpRequest, textStatus, thrownError) {
	    	 showError("操作失败！！");
	    	 setTimeout(closeMessage,700);
	    }
	})
}
function gongYing(code,order,price){
	var data="code="+code+"&order="+order+"&price="+price;
	$.ajax({
		type:'post',
		url:'/MDHotel/order.accom',
		data:data,
		beforeSend:function(){
			showMessage("提示","<strong style='color:green'>正在供应</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>",null);
		},
		success:function(msg){
			if(msg=="success"){
			showMessage("提示","<strong style='color:green'>供应成功！</strong>",null);
			setTimeout(closeMessage,700);
			}else if(msg=="fail"){
				showError("供应失败！");
			}else{
				showError("操作失败！");
			}
		},
		 error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   showError("操作失败error！");
	       }
	})
}
