// JavaScript Document
function init(){
	navScroll();
	listMenu();
	setInterval(showTime,1000);
	getUserInfo(2);
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
	if(minute<10){
		minute='0'+minute;
	}
	if(second<10){
		second='0'+second;
	}
	var time=year+month+day+hour+minute+second;
	$('#code').val("S"+time);
	$('#stime').val(year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second);
}
function addShop(){
	if($('#name').val()==""){
		$('#name').css('border','1px solid red');
		return;
	}
	if($('#address').val()==""){
		$('#address').css('border','1px solid red');
		return;
	}
	$('#name').css({'border':'0px','border-bottom':'1px dashed #ccc'});
	$('#address').css({'border':'0px','border-bottom':'1px dashed #ccc'});
	var str="<strong style='color:#333'>确定添加该分店？</strong>";
	var foot=' <button type="button" class="btn btn-success" onClick="javascript:sureAdd();">确定</button><button class="btn btn-default" data-dismiss="modal">取消</button>';
	showMessage('提示',str,foot);
}
function sureAdd(){
	var data="code="+$('#code').val()+"&name="+$('#name').val()+"&address="+$('#address').val()+"&time="+$('#stime').val()+"&remark="+$('#remark').val();
	$.ajax({
		type:"post",
		url:"/MDHotel/add.shop",
		data:data,
		 beforeSend: function () {
	    	   showMessage("提示","<strong style='color:green'>正在添加</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>",null);
	    	   $('#myModalBody').addClass("text-center");
	       },
	       success: function (msg) {
	           if(msg == "success"){
	        	   showMessage("提示","<strong style='color:green'>添加成功！</strong>",null);  
	        	   setTimeout("window.location.reload()",700);
	           }else if(msg == "fail"){
	        	   showError("添加失败！");
	        	   setTimeout(closeMessage,700);
	           }else if(msg=="error"){
	        	   showError("操作错误！");
	        	   setTimeout(closeMessage,700);
	           }
	           setTimeout(closeMessage,700);
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   showError("操作失败！"+XMLHttpRequest.status);
	       }
	})
}