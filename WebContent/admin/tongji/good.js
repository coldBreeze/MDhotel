// JavaScript Document
function init(){
	navScroll();
	listMenu();
	setInterval(showTime,1000);
	getUserInfo(1);
	getGoodData();
	getShop(1);
}
function getGoodData(){
	$.ajax({
		type:'post',
		url:'/MDHotel/good.tongji',
		data:"type=1",
		beforeSend:function(){
			$('#canvas').html("<div class='text-center'><strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></div>");
		},
		success:function(data){
			if(data){
				$('#canvas').html("");
				var names=data.split("-")[0];
				var buyArr=data.split("-")[1];
				var sellArr=data.split("-")[2];
				
				var nameArr=names.substring(1,names.indexOf(']')).split(',');
				var buyCount=buyArr.substring(1,buyArr.indexOf(']')).split(',');
				var sellCount=sellArr.substring(1,sellArr.indexOf(']')).split(',');
				
				var data = {
						labels : nameArr,
						datasets : [
							{
								fillColor : "rgba(151,187,205,1)",
								strokeColor : "rgba(151,187,205,1)",
								data : buyCount
							},
							{
								fillColor : "rgba(220,220,220,1)",
								strokeColor : "rgba(220,220,220,1)",
								
								data : sellCount
							}
						]
					}
					var ctx = document.getElementById("canvas").getContext("2d");
						window.myLine = new Chart(ctx).Bar(data,{
							responsive: true
						});
			}else{
				$('#canvas').html("<h4>没有数据！</h4>");
			}
		},
		 error: function (XMLHttpRequest, textStatus, thrownError) {
	    	   $('#canvas').html("<h4>获取数据失败！</h4>");
	       }
	})
	
}