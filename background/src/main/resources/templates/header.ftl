<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="${context}/dist/cssnormalize.css">
        <link rel="stylesheet" href="${context}/dist/element-ui.css">
        <script src="${context}/dist/vue.js"></script>
        <script src="${context}/dist/axios.js"></script>
        <script src="${context}/dist/element-ui.js"></script>
        <script >
            window.ctxPath = "${context}";
            Date.prototype.format = function (format) {
                var o = {
                    "M+": this.getMonth() + 1, //month
                    "d+": this.getDate(),    //day
                    "h+": this.getHours(),   //hour
                    "m+": this.getMinutes(), //minute
                    "s+": this.getSeconds(), //second
                    "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
                    "S": this.getMilliseconds() //millisecond
                };
                if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
                        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                for (var k in o) if (new RegExp("(" + k + ")").test(format))
                    format = format.replace(RegExp.$1,
                            RegExp.$1.length == 1 ? o[k] :
                                    ("00" + o[k]).substr(("" + o[k]).length));
                return format;
            };
            Vue.filter('formatDate', function (time) {
                if(time == undefined || time == ''){
                    return '';
                }
                var date = new Date(time);
                return date.format('yyyy-MM-dd hh:mm');
            });
            Vue.filter('avatar', function (avatar) {
                if (avatar != '' || avatar != undefined) {
                    return "http://localhost:8080/image/image/getAvatar?avatarName=" + avatar;
                }
                return '';
            });
            Vue.filter('orderStauts', function (val) {
                if (val == 1) {
                    return "等待付款";
                }
                if (val == 2) {
                    return "付款成功";
                }
                if (val == 3) {
                    return "取消";
                }
                return "";
            });
            Vue.filter('isHandler', function (val) {
                if (val == 1) {
                    return "未处理";
                }
                if (val == 2) {
                    return "处理成功";
                }
                return "";
            });
            Vue.filter('orderType', function (val) {
                if (val == 1) {
                    return "即时";
                }
                if (val == 2) {
                    return "预约";
                }
                return "";
            });
            Vue.filter('gender', function (val) {
                if (val == 1) {
                    return "男";
                }
                if (val == 2) {
                    return "女";
                }
                return "未知";
            });
            Vue.filter('isOnSale', function (val) {
                if (val == 1) {
                    return "在售";
                }
                if (val == 2) {
                    return "售罄";
                }
                return "";
            });
            function logout() {
                alert(1);
            }
        </script>
        <style>
            html, body {
                height: 100%;
            }

            @font-face {
                font-family: element-icons;
                src: url('${context}/dist/6f0a76321d30f3c8120915e57f7bd77e.ttf'),
                url('${context}/dist/6f0a76321d30f3c8120915e57f7bd77e.ttf'); /* IE9+,可以是具体的实际链接 */
            }

            .el-header {
                background-color: #91c1ff;
                color: #333;
                line-height: 60px;
            }
            .el-button--primary {
                color: #fff;
                background-color: #7BAEFD;
                border-color: #7BAEFD;
            }
            .el-pagination.is-background .el-pager li.active{
                background-color: #7BAEFD;
                border-color: #7BAEFD;
            }

            .el-aside {
                color: #333;
            }

            #app {
                font-family: Helvetica, sans-serif;
                text-align: center;
            }
            .el-breadcrumb__inner, .el-breadcrumb__inner a{
                color: #ffffff;
            }
            .el-breadcrumb__separator{
                color: inherit;
            }
            a{
                text-decoration: none;
            }
            .el-checkbox:first-child{
                margin-right: 30px;
            }
            .el-checkbox+.el-checkbox{
                margin-left: 0px;
                margin-right: 30px;
            }
            table .el-checkbox:first-child{
                margin: 0px;
            }
            .el-radio:first-child{
                margin-right: 30px;
                line-height: 30px;
            }
            .el-radio+.el-radio{
                margin-left: 0px;
                margin-right: 30px;
                line-height: 30px;
            }
            .el-breadcrumb__item:last-child .el-breadcrumb__inner{
                color: #ffffff;
                font-weight: bold;
                font-size: 22px;
            }
            .el-form-item__label{
             font-weight: bold;
            }
        </style>
        <title>
        <#list navList as nav>
            ${nav}
        </#list>
        </title>