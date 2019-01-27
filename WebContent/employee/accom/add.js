// JavaScript Document
function init(){
	navScroll();
	listMenu();
	setInterval(showTime,1000);
	getUserInfo(0);
	initInfo();
	getShop(0);
}
function initInfo(){
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	var day=date.getDate();
	var hour=date.getHours();
	var minute=date.getMinutes();
	var second=date.getSeconds();
	if(month<10){
		month='0'+month;
	}
	if(day<10){
		day='0'+day;
	}
	if(second<10){
		second='0'+second;
	}
	var str='A'+year+month+day+hour+minute+second;
	$('#code').val(str);
	$('#intime').val(year+'-'+month+'-'+day);
	getRoom();
	getPass();
	outimeInit();	
}
function getRoom(){
	$.ajax({
		type:'post',
		url:'/MDHotel/list.room',
		dataType:'json',
		data:"type=0",
		success:function(data){
			$.each(data,function(index,obj){
				var html="";
				var str="";
				$.each(obj,function(key,value){
					if(key=="room_code"){
						str+=value;
					}
					if(key=="room_name"){
						html+=value;
					}
					if(key=="room_cash"){
						str+=','+value;
					}
					if(key=="room_price"){
						str+=','+value;
					}
				})
				var option=$('<option value='+str+'>'+html+'</option>');
				$('#room').append(option);
			})
		}
	})
	$('#room').change(function(){
		var room1=$(this).val();
		var cash1=room1.split(',')[1];
		$('#cash').val(cash1);
		if($('#outime').val()!=""&&room1!=""&&$('#intime').val()!=''){
			var begin=new Date($('#intime').val());
			var end=new Date($('#outime').val());
			var day=(end.getTime()-begin.getTime())/(1000*60*60*24);
			var price=parseInt(room1.split(',')[2])*day;
			$('#price').val(price);
		}else{
			$('#price').val("");
		}
		
	})
}
function getPass(){
	$.ajax({
		type:'post',
		url:'/MDHotel/info.pass',
		dataType:'json',
		success:function(data){
			$.each(data,function(index,obj){
				var html="";
				var str="";
				$.each(obj,function(key,value){
					if(key=="pass_code"){
						str+=value;
					}
					if(key=="pass_name"){
						html+=value;
					}
					if(key=="pass_idcard"){
						html+='--'+value;
					}
				})
				var option=$('<option value='+str+'>'+html+'</option>');
				$('#pass').append(option);
			})
		}
	})
}
function outimeInit(){
	$('#outime').change(function(){
		var room=$('#room').val();
		if($(this).val()!=""&&$('#intime').val()!=''){
			var begin1=new Date($('#intime').val());
			var end1=new Date($(this).val());
			var day1=(end1.getTime()-begin1.getTime())/(1000*60*60*24);
			if(day1<=0){
				showError("不能早于入住时间！");
				$(this).val('');
				$('#price').val('');
				return;
			}
		}
		if($(this).val()!=""&&room!=""&&$('#intime').val()!=''){
		var begin=new Date($('#intime').val());
		var end=new Date($(this).val());
		var day=(end.getTime()-begin.getTime())/(1000*60*60*24);
		
		if(day<=0){
			showError("不能早于入住时间！");
			$(this).val('');
			$('#price').val('');
			return;
		}
		var price=room.split(',')[2]*day;
		$('#price').val(price);
		}else{
			
			$('#price').val("");
		}
	})
}
function goodsList(){
	getList(1);
	$('#goodsModal').modal();
}
function getList(num){
	var search=$('#searchGoods').val();
	$.ajax({
		type:"post",
		url:"/MDHotel/list.goods",
		dataType:"json",
		data:"currentPage="+num+"&search="+search,
		 beforeSend: function () {
			 $('tbody').html("<tr><td colspan='3' class='text-center'><strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></td></tr>");
	       },
	       success: function (data) {
	    	   $('tbody').html("");
	    	   if(data){
	    			   $.each(data,function(index,obj){
			        	  var tr=$('<tr></tr>');
			        	   $.each(obj,function(key,value){	
			        			   var td=$('<td>'+value+'</td>');
			        			   tr.append(td);
			        	   })
			        	   var td1=$('<td><button class="btn btn-primary buy">供应</button></td>');
			        	   tr.append(td1);
			        	   $('tbody').append(tr);
			           })
			         buyGoods();
			          turnPage(num,search);
	    	   }else{
	    		   $('tbody').html("<tr><td colspan='3' class='text-center'>获取数据失败！</td></tr>");
	    	   }    
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   $('tbody').html("<tr><td colspan='3' class='text-center'>获取数据失败！</td></tr>");
	       }
	})
}
function turnPage(num,search){
	$.ajax({
		type:"post",
		url:"/MDHotel/page.goods",
		data:"search="+search,
		 beforeSend: function () {
			 $('#pagebox').html("<div class='text-center'><strong style='color:green'>正在获取分页</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></div>");
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
	    	   $('#pagebox').html("获取分页失败！");
	       }
	})
	
}
function searchGoods(){
	getList(1);
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
				getList(1);
				if($('#order').val()==""){
					$('#order').val(msg.split(',')[0]);
					$('#oprice').val(msg.split(',')[1]);
				}else{
					var str=$('#order').val()+","+msg.split(',')[0];
					$('#order').val(str);
					var price1=parseInt($('#oprice').val());
					$('#oprice').val(price1+parseInt(msg.split(',')[1]));
				}
				
			}	
		},
		error: function (XMLHttpRequest, textStatus, thrownError) {
	    	 showError("操作失败！！");
	    	 setTimeout(closeMessage,700);
	    }
	})
}
function addAccom(){
	if($('#room').val()==""){
		$('#room').css('border','1px solid red');
		return;
	}
	if($('#pass').val()==""){
		$('#pass').css('border','1px solid red');
		return;
	}
	if($('#outime').val()==""){
		$('#outime').css('border','1px solid red');
		return;
	}
	$('#room').css({'border':'0px','border-bottom':'1px solid #000'});
	$('#pass').css({'border':'0px','border-bottom':'1px solid #000'});
	$('#outime').css({'border':'0px','border-bottom':'1px solid #000'});
	var price=0;
	if($('#oprice').val()==""){
		price=parseInt($('#price').val())+parseInt($('#cash').val());
	}else{
		price=parseInt($('#oprice').val())+parseInt($('#price').val())+parseInt($('#cash').val());
	}
	if($('#intime').attr('readonly')!="readonly"){
		var data="code="+$('#code').val()+"&room="+$('#room').val().split(',')[0]+"&pass="+$('#pass').val()+"&intime="+$('#intime').val()+"&outime="+$('#outime').val()+"&order="+$('#order').val()+"&price="+price+"&staff="+$('#mainUser').attr('alt')+"&remark="+$('#remark').val()+"&state=-1";
	}else{
		var data="code="+$('#code').val()+"&room="+$('#room').val().split(',')[0]+"&pass="+$('#pass').val()+"&intime="+$('#intime').val()+"&outime="+$('#outime').val()+"&order="+$('#order').val()+"&price="+price+"&staff="+$('#mainUser').attr('alt')+"&remark="+$('#remark').val();
	}
	
	$.ajax({
		type:'post',
		url:'/MDHotel/add.accom',
		data:data,
		beforeSend:function(){
			showMessage("提示","<div class='text-center'><strong style='color:green'>正在登记</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></div>",null);
		},
		success:function(msg){
			if(msg=="success"){
				showMessage("提示","<h4 style='color:green'>登记成功！</h4>",null);
				setTimeout(closeMessage,700);
				setTimeout("window.location.reload()",700);
			}else{
				showError("<h4>登记失败！</h4>");
				setTimeout(closeMessage,700);
			}
		},
		error: function (XMLHttpRequest, textStatus, thrownError) {
	    	 showError("操作失败！！");
	    	 setTimeout(closeMessage,700);
	    }
	})
	
	
	
}
function bookAccom(){
	$('#intime').removeAttr('readonly');
	$('#intime').css('border-bottom','1px solid #333');	
	var dt=$('#intime').val();
	dt = dt.replace('-', '/');
    var t1 = new Date(new Date(dt).valueOf() +24*60*60*1000);
    var m=t1.getMonth() + 1;
    var d=t1.getDate();
    if(m<10){
    	m='0'+m;
    }
    if(d<10){
    	d='0'+d;
    }   
    var time=t1.getFullYear() + "-" + m+ "-" + d;
    $('#intime').val(time);
   
	$('#intime').change(function(){
		if($(this).val()!=''){
			var end=new Date($(this).val());
			var begin=new Date();
			if(end<begin){
				showError("至少提前一天预订！");
				$(this).val('');
				$('#price').val('');
				return;
			}
			var room=$('#room').val();
			if($(this).val()!=""&&room!=""&&$('#outime').val()!=''){
			var begin1=new Date($(this).val());
			var end1=new Date($('#outime').val());
			var day1=(end1.getTime()-begin1.getTime())/(1000*60*60*24);
			if(day1<=0){
				showError("时间选择错误！");
				$(this).val('');
				$('#price').val('');
				return;
			}
			var price=room.split(',')[2]*day;
			$('#price').val(price);
			}else{
				
				$('#price').val("");
			}
			
		}else{
			$('#price').val('');
		}
		
	})
}