<el-dialog title="添加产品" :visible.sync="dialogProductVisible">
    <el-row>
        <el-form :model="productSearch" :inline="true">
            <el-form-item label="搜索产品名" style="margin-bottom: 0px;">
                <el-input v-model="productSearch.title"
                          placeholder="搜索产品名"></el-input>
            </el-form-item>

            <el-form-item style="margin-bottom: 0px;">
                <el-button style="float: left;" icon="el-icon-search" size="mini" type="primary" circle
                           @click="searchProduct"></el-button>

                <el-button  style="float: left;"  icon="el-icon-refresh" size="mini" type="primary" circle @click="refreshSearchProduct"></el-button>
            </el-form-item>

        </el-form>
    </el-row>
    <el-row style="margin-top: 14px;">

        <el-col :span="24">
            <div class="grid-content bg-purple-dark">
                <div style="padding-bottom: 14px;">
                    以下是可添加分类：
                </div>
            </div>
        </el-col>
    </el-row>
    <el-table :data="productList" border="true" stripe="true"  @selection-change="onSelectAddProduct">

        <el-table-column
                type="selection"
                width="55">
        </el-table-column>

        <el-table-column label="产品名" index="6">
            <template slot-scope="scope">
                <span style="margin-left: 10px">{{  scope.row.title }}</span>
            </template>
        </el-table-column>

        <el-table-column label="介绍" index="5">
            <template slot-scope="scope">
                <span style="margin-left: 10px">{{  scope.row.detail }}</span>
            </template>
        </el-table-column>

        <el-table-column label="单价" index="2" width="68">
            <template slot-scope="scope">
                <span style="margin-left: 10px">{{  scope.row.price }}</span>
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
    <el-row>
        <el-pagination
                @size-change="handleProductSizeChange"
                @current-change="handleProductCurrentChange"
                background
                layout="prev, pager, next"
                :current-page="currentProductPage"
                :total="totalProductPage">
        </el-pagination>
    </el-row>
    <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addProduct">确定添加</el-button>
    </div>
</el-dialog>
