// JavaScript Document
function init(){
	navScroll();
	listMenu();
	setInterval(showTime,1000);
	getUserInfo(0);
	getShop(0);
}
function addPass(){
	if($('#name').val()==""){
		$('#name').css('border','1px solid red');
		return;
	}else{
		$('#name').css({'border':'0px','border-bottom':'1px dashed #ccc'});
	}
	if($('#idcard').val()==""){
		$('#idcard').css('border','1px solid red');
		return;
	}else{
		$('#idcard').css({'border':'0px','border-bottom':'1px dashed #ccc'});
	}
	if($('#tel').val()==""){
		$('#tel').css('border','1px solid red');
		return;
	}else{
		$('#tel').css({'border':'0px','border-bottom':'1px dashed #ccc'});
	}
	var str="<strong color='#333'>确定添加该旅客？</strong>";
	var foot=' <button type="button" class="btn btn-success" onClick="javascript:sureAdd();">确定</button><button class="btn btn-default" data-dismiss="modal">关闭</button>';
	showMessage('提示',str,foot);
}
function sureAdd(){
	var arr=$('input[type=radio]');
	var sex="";
	for(var i=0;i<arr.length;i++){
		if(arr[i].checked){
			sex=arr[i].value;
		}
	}
	var data="name="+$('#name').val()+"&age="+$('#age').val()+"&sex="+sex+"&idcard="+$('#idcard').val()+"&tel="+$('#tel').val()+"&remark="+$('#remark').val();
	$.ajax({
		type:"post",
		url:"/MDHotel/add.pass",
		data:data,
		 beforeSend: function () {
	    	   showMessage("提示","<strong style='color:green'>正在登记</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'>",null); //点击登录后显示loading，隐藏输入框
	    	   $('#myModalBody').addClass("text-center");
	       },
	       success: function (msg) {
	           if(msg=="success"){
	        	   showMessage("提示","<strong style='color:green'>添加成功！</strong>",null);  
	        	   setTimeout(closeMessage,700);
	        	   setTimeout("window.location.reload()",700);
	           }else if(msg == "fail"){
	        	   showError("添加失败！");
	        	   setTimeout(closeMessage,700);
	           }else if(msg =="error"){
	        	   showError("操作错误！");
	        	   setTimeout(closeMessage,700);
	           }
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   showError("操作失败！");
	    	   setTimeout(closeMessage,700);
	       }
	})
}