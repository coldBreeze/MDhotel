// JavaScript Document
function init(){
	navScroll();
	listMenu();
	setInterval(showTime,1000);
	getUserInfo(2);
	getInitShop();
	$('#shops').on('change',function(){
		var shop=$(this).val();
		getPassData(shop);
	})
}
function getPassData(shop){
	$.ajax({
		type:'post',
		url:'/MDHotel/pass.tongji',
		data:"shop="+shop,
		beforeSend:function(){
			$('#canvas').html("<div class='text-center'><strong style='color:green'>正在获取数据</strong>&nbsp;&nbsp;<img src='../../img/login.gif' style='width:40px;height:40px'></div>");
		},
		success:function(data){
			if(data){
				$('#canvas').html("");
				var date=data.split("-")[0];
				var count=data.split("-")[1];
				var dateArr=date.substring(1,date.indexOf(']')).split(',').reverse();
				var countArr=count.substring(1,count.indexOf(']')).split(',').reverse();
				var lineChartData = {
				//X坐标数据
				//labels : ["2","4","6","8","10","12","14","16","18","20","22","24"],
				labels : dateArr,
				datasets : [
					{	
						//统计表的背景颜色
						fillColor : "rgba(200,0,300,0.5)",
						//统计表画笔颜色
						strokeColor : "#000",
						//点的颜色
						pointColor : "rgba(200,0,300,1);",
						//点边框的颜色
						pointStrokeColor : "#fff",
						//鼠标触发时点的颜色
						pointHighlightFill : "red",
						//鼠标触发时点边框的颜色
						pointHighlightStroke : "red",
						//Y坐标数据
						data : countArr
					}
				]

			}
			var ctx = document.getElementById("canvas").getContext("2d");
				window.myLine = new Chart(ctx).Line(lineChartData, {
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
function getInitShop(){
		$.ajax({
			type:"post",
			url:"/MDHotel/list.shop",
			dataType:"json",
		       success: function (data) {
		    	   if(data){
		    			   $.each(data,function(index,obj){
		    				   var option=$('<option></option>');
				        	   $.each(obj,function(key,value){
				        		 if(key=="shop_code"){
				        			   option.attr('value',value);
				        		   }
				        		if(key=="shop_name"){
				        			option.html(value);
				        		}
				        	   })
				        	   $('#shops').append(option);
				           })
		    	   }else{
		    		 
		    	   }    
		       },
		       error: function (XMLHttpRequest, textStatus, thrownError) {
		    	   showError("获取数据失败！！");
		       }
		})
}