<%--
  Created by IntelliJ IDEA.
  User: Zimomo
  Date: 2019/8/18
  Time: 21:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String Number = request.getParameter("Number"); //java代码，获取参数 %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
    var Number = '<%=Number%>';//转成js变量
    $(function(){
        Get_Times();
    })
    function Get_Times(){
        $.ajax({
            url:"/CheckServlet",
            data:{'Number':Number},
            dataType:"json",
            type:"post",
            success:function(Times){
                $("#Times_Times").remove();
                $("#Times").after(
                    "<a id='Times_Times' style='font-size: large;font-weight: bold;'>"+Times+"</a>"
                )
            }
        });
    }
    function file_download(){
        $.ajax({
            url:"/Get_File",
            type:"post",
            data:$('#form1').serialize(),
            dataType:"json",
            success:function(result){
                if(result=="无效CDK"){
                    alert("无效CDK");
                }else if(result=="次数不足"){
                    alert("次数不足");
                }else{
                    alert("转存成功");
                    Get_Times();
                    $("#temp_temp").remove();
                    $("#temp").after(
                        "<a id='temp_temp' href='../download?filename="+result+"' >"+result+"</a>"
                    )
                }
            }
        });
    }
</script>
<body>

<div id="Times" >
    <a id="Times_Times" style="font-size: large;font-weight: bold;"></a>
</div>

<h1>资源下载:</h1>
<p> 单纯地使用a标签时，只有浏览器不能解析的文件才会是下载，否则将被浏览器直接解析。</p>
<a href="../resource/test.html" >test.html</a><br>


<p>因此，使用a标签结合servlet的response指示浏览器不解析这些待下载文件</p>
<div id="temp">
    <a id="temp_temp" href="../download?filename=test.html" >test.html</a>
</div>
<br>
<br>

<form action="../Get_File" id="form1">
    下载地址：<input class="text" name="url" />
    <input type="hidden" class="text" name="Number" id="Number" value="" />
    <input type="button" value="下载" onclick="file_download()" />
</form>
<br><br>
<script>
    document.getElementById("Number").value=Number;
</script>
</body>
</html>
