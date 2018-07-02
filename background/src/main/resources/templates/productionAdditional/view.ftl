<#include "../header.ftl"/>
<style>
    .el-table .warning-row {
        background: oldlace;
    }

    .el-table .success-row {
        background: #f0f9eb;
    }
</style>
</head>

<body>

<div id="app"></div>

<template id="myComponent">
    <el-container style="height: 100%; border: 1px solid #eee">
    <#include "../leftbar.ftl"/>

        <el-container>
        <#include "../nav.ftl"/>

            <el-main>

                <el-row>
                    <el-col :span="24">
                        <div class="grid-content bg-purple-dark">
                            <el-dropdown @command="handleCommand">
                                  <span class="el-dropdown-link" style="cursor: pointer;">
                                  选择门店: {{shopName}}<i class="el-icon-arrow-down el-icon--right"></i>
                                  </span>
                                <el-dropdown-menu slot="dropdown">
                                <#list shopList as item>
                                    <el-dropdown-item command="${item.id},${item.name}">${item.name}</el-dropdown-item>
                                </#list>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>

                        <div class="grid-content bg-purple-dark" style="margin-top: 14px;">
                            <el-button type="primary" icon="el-icon-plus" @click="onInsertTag">新增分类</el-button>
                            <el-button type="primary" icon="el-icon-delete" @click="onDeleteTag">删除分类</el-button>
                        </div>
                    </el-col>
                </el-row>

                <el-row style="margin-top: 14px;">
                    <el-col :span="24">
                        <el-table :data="tableData" border="true" stripe="true" @selection-change="onSelectTableData">

                            <el-table-column type="expand">
                                <template slot-scope="props">
                                    <el-form label-position="left" inline class="demo-table-expand">
                                        <div class="grid-content bg-purple-dark" style="margin-bottom: 14px;">
                                            <el-button type="info" size="mini" circle icon="el-icon-plus" @click="onInsertProduct">新增产品
                                            </el-button>
                                            <el-button type="info" size="mini" circle icon="el-icon-edit" @click="onUpdateProduct">编辑产品
                                            </el-button>
                                            <el-button type="info" size="mini" circle icon="el-icon-delete" @click="onDeleteProduct">删除产品
                                            </el-button>
                                        </div>
                                        <el-table :data="props.row.productList" border="false" stripe="false">

                                            <el-table-column
                                                    type="selection"
                                                    width="55">
                                            </el-table-column>

                                            <el-table-column label="产品">
                                                <template slot-scope="scope">
                                                    <span style="margin-left: 10px">{{  scope.row.title }}</span>
                                                </template>
                                            </el-table-column>

                                            <el-table-column label="原价（元）">
                                                <template slot-scope="scope">
                                                    <span style="margin-left: 10px">{{  scope.row.price }}</span>
                                                </template>
                                            </el-table-column>

                                            <el-table-column label="门店价格（元）">
                                                <template slot-scope="scope">
                                                    <span v-if="scope.row.spPrice != 0">{{ scope.row.spPrice }}</span>
                                                    <span v-if="scope.row.spPrice == 0">暂无设置</span>
                                                </template>
                                            </el-table-column>

                                            <el-table-column label="是否有货">
                                                <template slot-scope="scope">
                                                    有
                                                </template>
                                            </el-table-column>

                                        </el-table>
                                    </el-form>
                                </template>
                            </el-table-column>

                            <el-table-column
                                    type="selection"
                                    width="55">
                            </el-table-column>

                            <el-table-column label="产品分类" index="5">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.title }}</span>
                                </template>
                            </el-table-column>

                            <el-table-column label="创建时间" index="1">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.createTime |formatDate}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="修改时间" index="0">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">{{  scope.row.updateTime |formatDate }}</span>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-col>

                </el-row>

            <#--<el-row>
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        background
                        layout="prev, pager, next"
                        :total="totalPage">
                </el-pagination>
            </el-row>-->


                <#include 'dialog-tag.ftl'/>
                <#include 'dialog-product.ftl'/>
            </el-main>
        </el-container>


    </el-container>

</template>
<#include 'js.ftl'/>
</body>
</html>