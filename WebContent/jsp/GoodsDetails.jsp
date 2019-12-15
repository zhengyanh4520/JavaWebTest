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
<title>商品详情</title>
<link rel="stylesheet" type="text/css" href="css/GoodsDetail.css">


<!--引入文件  -->
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.0"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<!-- 引入样式 -->
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<!-- 引入组件库 -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>

<script type="text/javascript">
function jumpLogin(){
	window.location.href="jsp/Login.jsp";
}

function changeInfo(){
	window.location.href="jsp/ChangeInfo.jsp";
}

function addCart()
{
	
	$.ajax({
		url: 'SearchGoodsServlet',
		type: 'GET',
		data:
		{
			gId: gId
		},
		dataType: 'json',
		success:function(data)
		{
			window.location.href="jsp/GoodsDetails.jsp";
		},
		error:function()
		{
			alert("未知错误，请刷新重试！");
		}   		
	});
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
    	<li><a href="jsp/userInfo.jsp">个人中心</a></li>
    	<li><a href="jsp/cart.jsp">购物车</a></li>
    	<li><a href="jsp/GoodsCenter.jsp">商城</a></li>
    	<li><a href="jsp/buyRecoding.jsp">购买记录</a></li>
    	<li><a href="jsp/browseRecoding.jsp">浏览记录</a></li>
    	<li>
    		<button class="button1" type="button" onclick="jumpLogin()">注销</button>
    	</li>    
    </ul>
    
    
</div>


<div class="goods">
	 	<img src="${sessionScope.goodsDetail.gUrl}" class="gImg">
		<h3 id="gName">${sessionScope.goodsDetail.gName}</h3>
		<p id="gBelong">所属商家：${sessionScope.goodsDetail.gBelong}</p>
		<p id="gDescribe">商品描述：${sessionScope.goodsDetail.gDescribe}</p>
		<p id="gClass">商品类别：${sessionScope.goodsDetail.gClass}</p>
		<P id="gPrice">单价：${sessionScope.goodsDetail.gPrice}</P>
		<p id="gNumber">余量：${sessionScope.goodsDetail.gNumber}</p>
		
		<el-button class="allButton" type="button" @click="judge1=true">加入购物车</el-button>
		<el-dialog
  			title="加入购物车提示":visible.sync="judge1" width="30%">
  			<span style="font-size:16px;">单价：${sessionScope.goodsDetail.gPrice}</span><br>
  			<span style="font-size:16px;">余量：${sessionScope.goodsDetail.gNumber}</span><br>
  			<span style="font-size:16px;">输入加入数量：</span>
  			<input type="text" class="input1" id="addNumber" placeholder="输入数字"><br>
  			<span slot="footer" class="dialog-footer">
    			<el-button @click="judge1 = false">取 消</el-button>
   				<el-button type="primary" @click="addCart()">确 定</el-button>
  			</span>
		</el-dialog>
		
		<el-button class="allButton" type="button" @click="judge2=true">直接购买</el-button>
		<el-dialog
  			title="购买提示":visible.sync="judge2" width="30%">
  			<span style="font-size:16px;">单价：${sessionScope.goodsDetail.gPrice}</span><br>
  			<span style="font-size:16px;">余量：${sessionScope.goodsDetail.gNumber}</span><br>
  			<span style="font-size:16px;">输入购买数量：</span>
  			<input type="text" class="input1" id="buyNumber" placeholder="输入数字"><br>
  			<span slot="footer" class="dialog-footer">
    			<el-button @click="judge2 = false">取 消</el-button>
   				<el-button type="primary" @click="buy()">确 定</el-button>
  			</span>
		</el-dialog>
</div>


</body>

<script>
  new Vue({
	el:".goods",
    data() {
      return {
       judge1: false,
       judge2: false
      };
    },
    methods: {
    	buy()
    	{
    		var uId="${sessionScope.user.uId}";
    		var gId="${sessionScope.goodsDetail.gId}";
    		//var gName="${sessionScope.goodsDetail.gName}";
    		var gPrice=${sessionScope.goodsDetail.gPrice};
    		var buyNumber=$("#buyNumber").val();
    		buyNumber=parseInt(buyNumber);
    		var gNumber=${sessionScope.goodsDetail.gNumber};
    		var uMoney=${sessionScope.user.uMoney};
    		var bType=2;
    		if(!(/(^[0-9]*[1-9][0-9]*$)/.test(buyNumber))){
    			alert("数量必须为正整数！");
    			return false;
    		}else if(buyNumber>gNumber){
    			alert("商品余量不足，请减少购买数量！");
    			return false;
    		}else if(gPrice*buyNumber>uMoney){
    			alert("余额不足！请减少购买量或充值");
    			return false;
    		}
    		alert("本次购买共花费："+gPrice*buyNumber+"元，点击确定付款！");
    		$.ajax({
    			url: 'BuyOrCartServlet',
    			type: 'GET',
    			data:
    			{
    				uId: uId,
    				gId: gId,
    				//gName: gName,
    				gPrice: gPrice,
    				buyNumber: buyNumber,
    				bType: bType
    			},
    			dataType: 'json',
    			success:function(data)
    			{
    				if(data==1){
    					alert("购买成功！");
    					window.location.href="jsp/GoodsDetails.jsp";
    				}else{
    					alert("未知错误，请刷新重试！");
    				}
    			},
    			error:function()
    			{
    				alert("未知错误，请刷新重试！");
    			}   		
    		});
    	},
    	
    	addCart(){
    		var uId="${sessionScope.user.uId}";
    		var gId="${sessionScope.goodsDetail.gId}";
    		//var gName="${sessionScope.goodsDetail.gName}";
    		var gPrice=${sessionScope.goodsDetail.gPrice};
    		var buyNumber=$("#addNumber").val();
    		buyNumber=parseInt(buyNumber);
    		var gNumber=${sessionScope.goodsDetail.gNumber};
    		var bType=1;
    		var th=this;
    		if(!(/(^[0-9]*[1-9][0-9]*$)/.test(buyNumber))){
    			alert("数量必须为正整数！");
    			return false;
    		}if(buyNumber>gNumber){
    			alert("商品余量不足，请减少加入数量！");
    			return false;
    		}
    		$.ajax({
    			url: 'BuyOrCartServlet',
    			type: 'GET',
    			data:
    			{
    				uId: uId,
    				gId: gId,
    				//gName: gName,
    				gPrice: gPrice,
    				buyNumber: buyNumber,
    				bType: bType
    			},
    			dataType: 'json',
    			success:function(data)
    			{
    				if(data==1){
    					alert("加入购物车成功！");
    					th.judge1 = false;
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
    }
  });
</script>

</html>