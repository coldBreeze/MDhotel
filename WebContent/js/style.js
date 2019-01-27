function listMenu(){
	$('.list-tooltip').tooltip();
		$("a.list-btn").click(function(e){
			e.preventDefault();
			if(!($(this).hasClass("active"))){
				$(".listMenu-list").animate({
					height: "135px",
					opacity: "1"
				});
				$(this).addClass("active");
			}
			else{
				$(".listMenu-list").animate({
					height: "0px",
					opacity: "0"
				});
				$(this).removeClass("active");
			}
		});
		$("#myCarousel").carousel({interval:4000});
}
function showTel(){
	$('#telWay').fadeToggle(200);
}
function openMessage(){
	$('#myModal').modal();
}
function showMessage(title,str,footer){
	$('#myModalLabel').html('');
	$('#myModalFooter').html('');
	if(title!=null){
		
		$('#myModalLabel').html(title);
	}
	
	if(footer!=null){	
		$('#myModalFooter').html(footer);
	}
	$('#myModalBody').html('');
	$('#myModalBody').html(str);
	openMessage();
}
function showError(str){
	$('#myModalLabel').html('');
	$('#myModalLabel').html('<h4>错误提示：</h4>');
	$('#myModalBody').html('');
	$('#myModalBody').html(str);
	$('#myModalFooter').html("<button type='button' class='btn btn-success' data-dismiss='modal'>确定</button>");
	$('#myModalBody').css('color','red');
	openMessage();
}

function closeMessage(){
	$('#myModal').modal('hide');
}
function navScroll(){
	var a = $("#nav").offset().top;
$(window).scroll(function() {	
    var obj_height = document.documentElement.scrollTop || document.body.scrollTop;
    if(obj_height >= a)
    {
        $("#nav").addClass('navbar-fixed-top');
    }
    else
    {
        $("#nav").removeClass('navbar-fixed-top');
    }
});
}
function showTime(){
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	var day=date.getDate();
	var week=date.getDay();
	var hour=date.getHours();
	var minute=date.getMinutes();
	var second=date.getSeconds();
	if(month<10){
		month='0'+month;
	}
	if(day<10){
		day='0'+day;
	}
	switch(week){
		case 0:week='星期日';break;
		case 1:week='星期一';break;
		case 2:week='星期二';break;
		case 3:week='星期三';break;
		case 4:week='星期四';break;
		case 5:week='星期五';break;
		case 6:week='星期六';break;
	}
	if(minute<10){
		minute='0'+minute;
	}
	if(second<10){
		second='0'+second;
	}
	$('#time').html(year+'年'+month+'月'+day+'日 '+week+' '+hour+'：'+minute+'：'+second);
}
function hiddenAlert(){
	$('#alert').css('display','none');
}
function getUserInfo(num){
	 $.ajax({
	       type: "post",
	       url: "/MDHotel/info.staff",
	       data:"type="+num,
	       success: function (msg) {
	           if(msg=="fail"){
	        	   $('#userInfo').html("未登录");
	        	   showMessage("提示","<h4 style='color:green'>未登录！自动跳转到登录页面...</h4>",null);
	        	   setTimeout("window.location.href=\"/MDHotel/login.html\"",1500);   
	           }else{
	        	   var arr=new Array();
		           arr=msg.split(",");
		           $('#userInfo').html(arr[1]);
		           $('#userInfo').attr("alt",arr[0]);
		           $('#mainUser').html(arr[1]);
		           $('#mainUser').val(arr[1]);
		           $('#mainUser').attr("alt",arr[0]);
	           }
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   $('#userInfo').html("未登录"); 
	    	   showMessage("提示","<h4 style='color:green'>未登录！自动跳转到登录页面...</h4>",null);
        	   setTimeout("window.location.href=\"/MDHotel/login.html\"",2000);  
	       }
	   });
}
function exit(num){
	showMessage("提示","确定退出？","<button class='btn btn-success' onclick='javascript:sureExit("+num+");'>确定</button><button class='btn btn-default' data-dismiss='modal'>取消</button>");
}
function sureExit(num){
	$.ajax({
		type:"post",
		url:"sessionDel.staff",
		data:"type="+num,
		 success: function (msg) {
	           if(msg=="fail"){
	        	   showError("<h4 style='color:red'>未退出！</h4>");  
	           }else if(msg=="success"){
	        	   window.location.href="/MDHotel/login.html";
	           }
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   showError("<h4 style='color:red'>未退出！</h4>"); 
	       }
	})
	window.location.href="/MDHotel/login.html";
}
function getShop(num){
	$.ajax({
		type:"post",
		url:"shop.staff",
		data:"type="+num,
		 success: function (msg) {
			 if(msg!='fail')
	          $('#shopName').html(msg.substr(msg.indexOf('-')+1));
	       },
	       error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   $('#shopName').html("获取失败"); 
	       }
	})
}