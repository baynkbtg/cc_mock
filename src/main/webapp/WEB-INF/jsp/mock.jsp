<%--
  Created by IntelliJ IDEA.
  User: qilong.chen
  Date: 2018/3/20
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>mock</title>
    <link href="<%=path%>/resource/css/animate.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/font-awesome.min.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/style.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/base.css" rel="stylesheet">
    <link href="<%=path%>/resource/css/jquery.numberedtextarea.css" rel="stylesheet">

    <script src="<%=path%>/resource/js/jquery.min.js"></script>
    <script src="<%=path%>/resource/js/bootstrap.min.js"></script>
    <script src="<%=path%>/resource/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
    <script src="<%=path%>/resource/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
    <script src="<%=path%>/resource/js/content.js"></script>
    <script src="<%=path%>/resource/js/plugins/peity/jquery.peity.min.js"></script>
    <script src="<%=path%>/resource/js/plugins/iCheck/icheck.min.js"></script>
    <!-- Switchery -->
    <script src="<%=path%>/resource/js/plugins/switchery/switchery.js"></script>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <a class="logo" href="#"><h2>欢迎使用Mock</h2></a>
                </div>
                <div class="ibox-content">
                    <div class="form-group" style="margin: 50px 10px 20px 5px;">
                        <div class="row">
                            <div class="col-sm-2">
                                <div class="input-group">
                                    <input class="form-control" placeholder="此处填写接口mock的名称" style="display: block" id="alias"/>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                    <input class="form-control" placeholder="标识字段名称" style="display: block" id="iden_key"/>
                            </div>
                            <div class="col-sm-2">
                                    <input class="form-control" placeholder="标识字段值" style="display: block" id="iden_val"/>
                            </div>
                        </div>
                        <br>
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="此处填写想要mock的接口URL" id="url">
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-primary" id="confirm">确认添加/更新</button>
                            </span>
                        </div>
                    </div>


                    <!-- Json填写框部分 -->
                    <header class="header">
                        <div class="row-fluid">
                            <div class="col-md-5" style="position:relative;">
                                <%--<a class="logo" href="#">Json<span style="color:#555;">格式期望值</span></a>--%>
                                <span class="text-primary" style="color:#555;"><h2>期望值设置</h2></span>
                            </div>
                            <nav class="col-md-7" style="padding:10px 0;" align="right">
                                <div class="navi">

                                </div>
                            </nav>
                            <br style="clear:both;"/>
                        </div>
                    </header>
                    <main class="row-fluid">
                        <div class="col-md-5" style="padding:0;">
                            <textarea id="json-src" placeholder="此处输入json格式期望值" class="form-control"
                                      style="height:553px;padding:0 10px 10px 20px;border:0;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none; outline:none;"></textarea>
                        </div>
                        <div class="col-md-7" style="padding:0;">
                            <div id="right-box"
                                 style="height:553px;border-right:solid 1px #ddd;border-bottom:solid 1px #ddd;border-radius:0;resize: none;overflow-y:scroll; outline:none;position:relative;">
                                <div id="line-num"
                                     style="background-color:#fafafa;padding:0px 8px;float:left;border-right:dashed 1px #eee;display:none;z-index:-1;color:#999;position:absolute;text-align:center;over-flow:hidden;">
                                    <div>0</div>
                                </div>
                                <div class="ro" id="json-target" style="padding:0px 25px;over:0">
                                </div>
                            </div>
                            <form id="form-save" method="POST">
                                <input type="hidden" value="" id="txt-content"
                                       name="content">
                            </form>
                        </div>

                        <br style="clear:both;"/>
                    </main>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- JSON 染色与格式化 -->
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.json.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.json2xml.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.message.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.numberedtextarea.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jquery.xml2json.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/json2.js"></script>
<script src="<%=path%>/resource/js/plugins/jsoncheck/jsonlint.js"></script>
<script type="text/javascript">
    $('textarea').numberedtextarea();
    var current_json = '';
    var current_json_str = '';
    var xml_flag = false;
    var zip_flag = false;
    var shown_flag = false;
    function init() {
        xml_flag = false;
        zip_flag = false;
        shown_flag = false;
        renderLine();
        $('.xml').attr('style', 'color:#999;');
        $('.zip').attr('style', 'color:#999;');
    }
    $('#json-src').keyup(function () {
        init();
        var content = $.trim($(this).val());
        var result = '';
        if (content != '') {
            //如果是xml,那么转换为json
            if (content.substr(0, 1) === '<' && content.substr(-1, 1) === '>') {
                try {
                    var json_obj = $.xml2json(content);
                    content = JSON.stringify(json_obj);
                } catch (e) {
                    result = '解析错误：<span style="color: #f1592a;font-weight:bold;">' + e.message + '</span>';
                    current_json_str = result;
                    $('#json-target').html(result);
                    return false;
                }
            }
            try {
                current_json = jsonlint.parse(content);
                current_json_str = JSON.stringify(current_json);
                //current_json = JSON.parse(content);
                result = new JSONFormat(content, 4).toString();
            } catch (e) {
                result = '<span style="color: #f1592a;font-weight:bold;">' + e + '</span>';
                current_json_str = result;
            }
            $('#json-target').html(result);
        } else {
            $('#json-target').html('');
        }
    });
    $('#json-src').click(function () {
        init();
        var content = $.trim($(this).val());
        var result = '';
        if (content != '') {
            //如果是xml,那么转换为json
            if (content.substr(0, 1) === '<' && content.substr(-1, 1) === '>') {
                try {
                    var json_obj = $.xml2json(content);
                    content = JSON.stringify(json_obj);
                } catch (e) {
                    result = '解析错误：<span style="color: #f1592a;font-weight:bold;">' + e.message + '</span>';
                    current_json_str = result;
                    $('#json-target').html(result);
                    return false;
                }
            }
            try {
                current_json = jsonlint.parse(content);
                current_json_str = JSON.stringify(current_json);
                //current_json = JSON.parse(content);
                result = new JSONFormat(content, 4).toString();
            } catch (e) {
                result = '<span style="color: #f1592a;font-weight:bold;">' + e + '</span>';
                current_json_str = result;
            }
            $('#json-target').html(result);
        } else {
            $('#json-target').html('');
        }
    });
    function renderLine() {
        var line_num = $('#json-target').height() / 20;
        $('#line-num').html("");
        var line_num_html = "";
        for (var i = 1; i < line_num + 1; i++) {
            line_num_html += "<div>" + i + "<div>";
        }
        $('#line-num').html(line_num_html);
    }
    $('#json-src').keyup();
</script>

<!-- ajax添加一个mock服务 -->
<script type="text/javascript">
    $("#confirm").click(function () {
        var alias = document.getElementById("alias").value;
        var url = document.getElementById("url").value;
        var iden_key = document.getElementById("iden_key").value;
        var iden_val = document.getElementById("iden_val").value;
        var identity = iden_key:iden_val;
        var json = current_json_str;//压缩后的JSON

        $.ajax({
            url: "<%=path%>/mock/insert",
            data: {
                alias: alias, url: url, json: json, identity: identity
            },
            type: "post",
            async: false,
            success: successFuc
        })
        function successFuc(data) {
            var json = eval(data);
            var result = json.errorMsg;
            alert(result);
            window.location.reload();
        }
    })
</script>

</body>
</html>