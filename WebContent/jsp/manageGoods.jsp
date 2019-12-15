<%@ page language="java" import="javax.servlet.http.HttpSession" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title>管理商品</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/GoodsCenter.css">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.0"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<script type="text/javascript">


function jumpLogin(){
	window.location.href="jsp/Login.jsp";
}

function changeInfo(){
	window.location.href="jsp/ChangeInfo.jsp";
}

function judge(){
	var i=${sessionScope.user.uType};
	if(i==1){
		window.location.href="jsp/adminInfo.jsp";
	}else{
		window.location.href="jsp/userInfo.jsp";
	}
}
</script>

</head>
<body>

<div class="top">
    <ul>
    	<li><a onclick="judge()">个人中心</a></li>
    	<li>
    		<button class="button1" type="button" onclick="jumpLogin()">注销</button>
    	</li>    
    </ul>
</div>

<!-- 商品 -->
<div id="gPart" style="margin-top: 58px">
	<div class="gList" v-for="(i,index) in goods">
		<img v-bind:src='i.gUrl' class="gImg">
		<div class="gInfo" >
			<h2 class="gName">{{i.gName}}</h2>
			<p class="gText">{{i.gDescribe}}</p>
			<P class="gText">{{i.gPrice}}</P>
		</div>
		<button class="allButton" type="button" @click="changeGoods(index)" style="margin-bottom: 5px">修改</button><br>
		<button class="allButton" type="button" @click="deleteGoods(index)">删除</button>
	</div>
</div>



</body>

<script type="text/javascript">
   new Vue({
        el: '#gPart',
        data() {
            return{
            	goods: null,
            	readMore: false
            } 
        },
        methods: {
        	changeGoods(index)
        	{
    			var uId="${sessionScope.user.uId}";
    			var gId=this.goods[index].gId;
    			$.ajax({
    				url: 'SearchGoodsServlet',
    				type: 'GET',
    				data:
    				{
    					uId: uId,
    					gId: gId
    				},
    				dataType: 'json',
    				success:function(data)
    				{
    					window.location.href="jsp/changeGoods.jsp";
    				},
    				error:function()
    				{
    					alert("未知错误，请刷新重试！");
    				}   		
    			});
        	},
        	deleteGoods(index)
        	{
    			var gId=this.goods[index].gId;
    			$.ajax({
    				url: 'DeleteGoodsServlet',
    				type: 'GET',
    				data:
    				{
    					gId: gId
    				},
    				dataType: 'json',
    				success:function(data)
    				{
    					if(data==1){
    						alert("删除成功！")
    						window.location.href="jsp/manageGoods.jsp";
    					}else{
    						alert("未知错误，请刷新重试！");
    					}
    				},
    				error:function()
    				{
    					alert("未知错误，请刷新重试！");
    				}   		
    			});
        	}
        },
        mounted() {
        	var uId="${sessionScope.user.uId}";
        	var th = this;
        	$.ajax({
    			url: 'LoadGoodsServlet',
    			type: 'GET',
    			data:
    			{
    				uId: uId
    			},
    			dataType: 'json',
    			success:function(data){
    				th.goods=data;
    			},
    			error:function()
    			{
    				alert("未知错误，请刷新重试！");
    			}   		
    		});
        }
    })
</script>

</html>