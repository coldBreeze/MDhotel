// JavaScript Document
function init(){

    //记住密码功能
    var stre = getCookie("emp"); 
    var stra=getCookie("admin"); 
    var strs=getCookie("sys"); 
    var name="";
    var pwd="";
    if(stre!=null&&stre!=""){
    	name = stre.split("%2C")[0];
    	pwd = stre.split("%2C")[1];
    	 //自动填充用户名和密码
	    $('#login').val(name);
	   // alert(userPwd)
	    if((typeof pwd)!="undefined")
	    $('#pwd').val(pwd);
    }
    if(stra!=null&&stra!=""){
    	name = stra.split("%2C")[0];
    	pwd = stra.split("%2C")[1];
    	 //自动填充用户名和密码
	    $('#alogin').val(name);
	   // alert(userPwd)
	    if((typeof pwd)!="undefined")
	    $('#apwd').val(pwd);
    }
    if(strs!=null&&strs!=""){
    	name = strs.split("%2C")[0];
    	pwd = strs.split("%2C")[1];
    	 //自动填充用户名和密码
	    $('#slogin').val(name);
	   // alert(userPwd)
	    if((typeof pwd)!="undefined")
	    $('#spwd').val(pwd);
    }
    initPwd();
    
}
function refresh(obj) {
    obj.src = "/MDHotel/ImageServlet?"+Math.random();
}
function staffLogin(num){
	var data="";
	if(num==0){
		 if($("#login").val() == ""){
		        showError('请输入用户名！');
		        return;
		   }
		    if( $("#pwd").val() == ""){
		   	 showError('请输入密码！');
		        return;
		   }  
		   if( $("#randomC").val() == ""){
		   	showError('请输入验证码！');
		        return;
		   }  
		   if($("#remember").is(':checked')){
			   data="login=" + $('#login').val()+ "&pwd=" + $('#pwd').val()+"&randomC="+$("#randomC").val()+"&type="+"emp"+"&remember=1";
		   }else{
			   data="login=" + $('#login').val()+ "&pwd=" + $('#pwd').val()+"&randomC="+$("#randomC").val()+"&type="+"emp";
		   }
	 }
	else if(num == 1){
		 if($("#alogin").val() == ""){
		       showError('请输入用户名！');
		       return;
		  }
		   if( $("#apwd").val() == ""){
		  	 showError('请输入密码！');
		       return;
		  }  
		  if( $("#arandomC").val() == ""){
		  	showError('请输入验证码！');
		       return;
		  }  
		  if($("#aremember").is(':checked')){
			  data="login=" + $('#alogin').val()+ "&pwd=" + $('#apwd').val()+"&randomC="+$("#arandomC").val()+"&type="+"admin"+"&remember=1";
		  }else{
			  data="login=" + $('#alogin').val()+ "&pwd=" + $('#apwd').val()+"&randomC="+$("#arandomC").val()+"&type="+"admin";
			   }
		 
	}
	else if(num == 2){
		 if($("#slogin").val() == ""){
		       showError('请输入用户名！');
		       return;
		  }
		   if( $("#spwd").val() == ""){
		  	 showError('请输入密码！');
		       return;
		  }  
		  if( $("#srandomC").val() == ""){
		  	showError('请输入验证码！');
		       return;
		  }  
		  if($("#sremember").is(':checked')){
			  data="login=" + $('#slogin').val()+ "&pwd=" + $('#spwd').val()+"&randomC="+$("#srandomC").val()+"&type="+"sys"+"&remember=1";
		  }else{
			  data="login=" + $('#slogin').val()+ "&pwd=" + $('#spwd').val()+"&randomC="+$("#srandomC").val()+"&type="+"sys";
			   }
		 
	}
   $.ajax({
       type: "POST",
       url: "LoginServlet",
       data: data,
       beforeSend: function () {
    	   showMessage("提示","<strong style='color:green'>正在登录</strong>&nbsp;&nbsp;<img src='img/login.gif' style='width:40px;height:40px'>",null); //点击登录后显示loading，隐藏输入框
    	   $('#myModalBody').addClass("text-center");
       },
       success: function (msg) {
           if (msg == "success") {
        	   if(num==0){
        		   showMessage("提示","<strong style='color:green'>登录成功！</strong>",null); //如果登录成功则跳到管理界面  
        		   setTimeout("window.location.href=\"employee/index.html\"",1200);
        	   }else if(num==1){
        		   showMessage("提示","<strong style='color:green'>登录成功！</strong>",null); 
        		   setTimeout("window.location.href=\"admin/index.html\"",1200);
        	   }else if(num==2){
        		   showMessage("提示","<strong style='color:green'>登录成功！</strong>",null); 
        		   setTimeout("window.location.href=\"sys/index.html\"",1200);
        	   }
           }
           if(msg == "randomNo"){
        	   showError("验证码错误！");
        	   setTimeout(closeMessage,700);
           }
           if (msg == "fail") {
               showError("用户名或密码错误！"); 
               setTimeout(closeMessage,700);
           }
           if(msg == "error"){
        	   showError("操作失败！");  
        	   setTimeout(closeMessage,700);
           }
       },
       error: function (XMLHttpRequest, textStatus, thrownError) {
    	   showError("操作失败！");
       }
   });
}
function sureLogin(str){
	if(str==0)
	window.location.href="employee/index.html";
	else if(str==1){
		window.location.href="admin/index.html";
	}
}
function getCookie(cname) {
	var name = cname + "=";
	
    var ca = document.cookie.split(';')
    
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ')
        	c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
function register(){
	if(cantEmpty()){
		showMessage("提示","确定注册？","<button class='btn btn-success' onclick='javascript:sureRegister();'>确定</button><button class='btn btn-default' data-dismiss='modal'>关闭</button>");
	}
}
function sureRegister(){
	 $.ajax({
	       type: "POST",
	       url: "RegisterServlet",
	       data: "login="+$('#staffLogin').val()+"&name="+$('#staffName').val()+"&pwd="+$('#staffPwd').val()+"&type="+$('#staffType').val(),
	       beforeSend: function () {
	    	   showMessage("提示","<strong style='color:green'>正在注册</strong>&nbsp;&nbsp;<img src='img/login.gif' style='width:40px;height:40px'>",null); //点击登录后显示loading，隐藏输入框
	    	   $('#myModalBody').addClass("text-center");
	       },
	       success: function (msg) {
	           if (msg == "success") {
	        		   showMessage("提示",null);
	        		   setTimeout(closeMessage,700);
	           }
	           if (msg == "exist") {
	               showError("该用户名重复！");   
	               setTimeout(closeMessage,700);
	           }
	           if (msg == "fail") {
	               showError("注册失败！"); 
	               setTimeout(closeMessage,700);
	           }
	           if(msg == "error"){
	        	   showError("操作失败！");  
	        	   setTimeout(closeMessage,700);
	           }
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   showError("操作失败！");
	       }
	   });
	$('#registerModal').modal('hide');
}
function cantEmpty(){
	var flag=true;
	if($('#staffLogin').val()==""){
		$('#staffLogin').css('border','1px solid red');
		flag=false;
	}
	if($('#staffName').val()==""){
		$('#staffName').css('border','1px solid red');
		flag=false;
	}
	if($('#staffPwd').val()==""){
		$('#staffPwd').css('border','1px solid red');
		flag=false;
	}
	if($('#staffPwd2').val()==""){
		$('#staffPwd2').css('border','1px solid red');
		flag=false;
	}
	return flag;
}
function initPwd(){
	$('#staffPwd').keyup(function(){
		if($(this).val()!=null&&$(this).val()!=''){
			if($(this).val()!=$('#staffPwd2').val()){
				$('#staffPwd2').css('border','1px solid red');
			}
			else{
				$('#staffPwd2').css('border','1px solid #ccc');
			}
		}
	})
	$('#staffPwd2').keyup(function(){
		if($(this).val()!=null&&$(this).val()!=''){
			if($(this).val()!=$('#staffPwd').val()){
				$('#staffPwd2').css('border','1px solid red');
			}
			else{
				$('#staffPwd2').css('border','1px solid #ccc');
			}
		}
	})
}