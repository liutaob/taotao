<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath %>js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<table class="easyui-datagrid" id="orderList" title="订单列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/order/list.action',method:'get',pageSize:30,toolbar:toolbar">
    <thead>
        <tr>
        	<th data-options="field:'orderId',width:60">订单ID</th>
        	<th data-options="field:'userId',width:60">用户ID</th>
        	<th data-options="field:'status',width:80">支付状态</th>
        	<th data-options="field:'payment',width:100">金额</th>
        	<th data-options="field:'postFee',width:100">邮费</th>
        	<th data-options="field:'paymentType',width:100">支付类型</th>
            <th data-options="field:'createTime',width:100">创建时间</th>
            <th data-options="field:'updateTime',width:100">修改时间</th>
        </tr>
    </thead>
</table>
</body>
</html>
