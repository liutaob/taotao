<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/nprogress.css" />
<script type="text/javascript" src="js/NProgress/nprogress.js"></script>
<div>
	<a class="easyui-linkbutton" onclick="importIndex()">一键导入商品数据到索引库</a>
</div>

<script type="text/javascript">
function importIndex(){
	NProgress.start();
	$.post("/index/import",function(data){
		NProgress.done();
		if(data.status == 200){
			$.messager.alert('提示','导入索引库成功！');
		}else{
			$.messager.alert('提示','导入索引库失败！');
		}
	});
}
</script>