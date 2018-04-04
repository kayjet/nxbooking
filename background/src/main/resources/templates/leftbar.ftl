<el-aside width="200px" style="background-color:#e9ebeb;">
    <el-menu :default-openeds="['1']">
        <el-submenu index="1">
            <template slot="title"><i class="el-icon-info"></i>后台导航</template>
        </el-submenu>
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title"><a href="${context}/user/view">用户记录</a></span>
        </el-menu-item>
        <el-menu-item index="3">
            <i class="el-icon-menu"></i>
            <span slot="title"><a href="${context}/userFavShopRel/view">用户收藏</a></span>
        </el-menu-item>
        <el-submenu index="4">
            <span slot="title"> <i class="el-icon-menu"></i>门店管理</span>
            <el-menu-item index="4-1"><a href="${context}/shop/view">详情记录</a></el-menu-item>
        </el-submenu>
        <el-submenu index="5">
            <span slot="title"> <i class="el-icon-menu"></i>产品管理</span>
            <el-menu-item index="5-1"><a href="${context}/product/view">详情记录</a></el-menu-item>
            <el-menu-item index="5-2"><a href="${context}/tag/view">标签</a></el-menu-item>
            <el-menu-item index="5-3"><a href="${context}/productSpec/view">规格</a></el-menu-item>
        </el-submenu>

        <el-submenu index="6">
            <span slot="title"> <i class="el-icon-menu"></i>订单</span>
            <el-menu-item index="6-1"><a href="${context}/order/view">详情记录</a></el-menu-item>
        </el-submenu>

    </el-menu>
</el-aside>