<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/nprogress.css" />
<script type="text/javascript" src="js/NProgress/nprogress.js"></script>
<div>
	<a class="easyui-linkbutton" onclick="clearCache()">清理缓存</a>
</div>

<script type="text/javascript">
function clearCache(){
	$.messager.confirm('确认','此操作可能造成数据库压力引发崩溃,请确认数据库性能良好,确定清空缓存吗？',function(r){
		if(r){
			NProgress.start();
			$.post("/cache/clear",function(data){
				NProgress.done();
				if(data.status == 200){
					$.messager.alert('提示','清理成功！');
				}else{
					$.messager.alert('提示',data.msg);
				}
			});
		}
	});
}
</script>