// JavaScript Document
function init(){
	navScroll();
	listMenu();
	setInterval(showTime,1000);
	getUserInfo(2);
	getInitShop();
	$('#shops').on('change',function(){
		var shop=$(this).val();
		getGoodData(shop);
	})
	$('#refresh').on('click',function(){
		var shop=$('#shops').val();
		getGoodData(shop);
	})
}
function getGoodData(shop){
	$.ajax({
		type:'post',
		url:'/MDHotel/good.tongji',
		data:"&shop="+shop,
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