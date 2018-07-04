<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="${proxyContext}${context}/dist/cssnormalize.css">
    <link rel="stylesheet" type="text/css" href="${proxyContext}${context}/dist/element-ui.css">
    <script src="${proxyContext}${context}/dist/vue.js"></script>
    <script src="${proxyContext}${context}/dist/axios.js"></script>
    <script src="${proxyContext}${context}/dist/element-ui.js"></script>
    <script>
        window.ctxPath = "${proxyContext}${context}";
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
            if (time == undefined || time == '') {
                return '';
            }
            var date = new Date(time);
            return date.format('yyyy-MM-dd hh:mm');
        });
        Vue.filter('avatar', function (avatar) {
            if (avatar != '' || avatar != undefined) {
                <#--return "${proxyContext}/image/image/getAvatar?avatarName=" + avatar;-->
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
        window.wsAddress = "ws://localhost:8080/background/springws/websocket.ws";
        var logout = Vue.extend({
            template: "<el-dropdown @command=\"handleLogout\">\n" +
            "            <i class=\"el-icon-setting\" style=\"margin-right: 15px;cursor: pointer;\"></i>\n" +
            "            <el-dropdown-menu slot=\"dropdown\">\n" +
            "                <el-dropdown-item >\n" +
            "                   <span>登出</span>\n" +
            "                </el-dropdown-item>\n" +
            "            </el-dropdown-menu>\n" +
            "        </el-dropdown>",
            methods: {
                handleLogout:function () {
                    window.location.href = "${proxyContext}${context}/common/logout/action";
                }
            }
        });
        var wslogout = Vue.extend({
            template: "<el-dropdown @command=\"handleLogout\">\n" +
            "            <i class=\"el-icon-setting\" style=\"margin-right: 15px;cursor: pointer;\"></i>\n" +
            "            <el-dropdown-menu slot=\"dropdown\">\n" +
            "                <el-dropdown-item >\n" +
            "                   <span>登出</span>\n" +
            "                </el-dropdown-item>\n" +
            "            </el-dropdown-menu>\n" +
            "        </el-dropdown>",
            methods: {
                handleLogout:function () {
                    window.location.href = "${proxyContext}${context}/websocket/logout/action";
                }
            }
        });
        var imageCenter = Vue.extend({
            template: "<el-row>\n" +
            "  <el-button type='small' @click='innerVisible = true'>从图库选择</el-button>" +
            "  <el-dialog\n" +
            "      width=\"640px\"\n" +
            "      title=\"内层 Dialog\"\n" +
            "      :visible.sync=\"innerVisible\"\n" +
            "      append-to-body>\n" +
                "<el-row><el-col :span=\"6\" v-for=\"o in images\" style='padding: 6px;' >\n" +
                "    <el-card :body-style=\"{ padding: '0px' }\">\n" +
                "      <img :src=\"o|avatar\" class=\"image\">\n" +
                "      <div style=\"padding: 14px;\">\n" +
                "        <span>{{o}}</span>\n" +
                "        <div class=\"bottom clearfix\">\n" +
                "          <el-button type=\"text\" class=\"button\" style='padding: 0;' @click='addImage'>添加</el-button>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </el-card>\n" +
                "  </el-col><el-row>" +
            "    </el-dialog>" +
            "</el-row>",
            props: {
                images:{
                    type: Array,
                    default: []
                },
                innerVisible: {
                    type: Boolean,
                    default: false
                }
            },
            beforeCreate(){
               var that = this;
                axios.get('/image/image/listAllAvatarNames').then(function(response){
                    that.images = response.data.data;
                });
            },
            methods: {
                addImage: function (evt) {
                    console.log('addImage',evt);
                }
            }
        });
        Vue.component('logout', logout);
        Vue.component('wslogout', wslogout);
        Vue.component('imagecenter', imageCenter);
    </script>
    <style>
        html, body {
            height: 100%;
        }

        @font-face {
            font-family: element-icons;
            src: url('${proxyContext}${context}/dist/6f0a76321d30f3c8120915e57f7bd77e.ttf'),
            url('${proxyContext}${context}/dist/6f0a76321d30f3c8120915e57f7bd77e.ttf'); /* IE9+,可以是具体的实际链接 */
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

        .el-pagination.is-background .el-pager li.active {
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

        .el-breadcrumb__inner, .el-breadcrumb__inner a {
            color: #ffffff;
        }

        .el-breadcrumb__separator {
            color: inherit;
        }

        a {
            text-decoration: none;
        }

        .el-checkbox:first-child {
            margin-right: 30px;
        }

        .el-checkbox + .el-checkbox {
            margin-left: 0px;
            margin-right: 30px;
        }

        table .el-checkbox:first-child {
            margin: 0px;
        }

        .el-radio:first-child {
            margin-right: 30px;
            line-height: 30px;
        }

        .el-radio + .el-radio {
            margin-left: 0px;
            margin-right: 30px;
            line-height: 30px;
        }

        .el-breadcrumb__item:last-child .el-breadcrumb__inner {
            color: #ffffff;
            font-weight: bold;
            font-size: 22px;
        }

        .el-form-item__label {
            font-weight: bold;
        }
        .time {
            font-size: 13px;
            color: #999;
        }

        .bottom {
            margin-top: 13px;
            line-height: 12px;
        }


        .image {
            width: 100%;
            display: block;
        }

        .clearfix:before,
        .clearfix:after {
            display: table;
            content: "";
        }

        .clearfix:after {
            clear: both
        }
    </style>
    <title>
    <#list navList as nav>
            ${nav}
        </#list>
    </title>