<el-aside width="200px" style="background-color:#e9ebeb;">
    <el-menu :default-openeds="['1']">
        <el-submenu index="1">
            <template slot="title"><i class="el-icon-info"></i>后台导航</template>
        </el-submenu>
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title"><a href="/booking/${context}/user/view">用户记录</a></span>
        </el-menu-item>
        <el-menu-item index="3">
            <i class="el-icon-menu"></i>
            <span slot="title"><a href="/booking/${context}/userFavShopRel/view">用户收藏</a></span>
        </el-menu-item>
        <el-submenu index="4">
            <span slot="title"> <i class="el-icon-menu"></i>门店管理</span>
            <el-menu-item index="4-1"><a href="/booking/${context}/shop/view">详情记录</a></el-menu-item>
        </el-submenu>
        <el-submenu index="5">
            <span slot="title"> <i class="el-icon-menu"></i>产品管理</span>
            <el-menu-item index="5-1"><a href="/booking/${context}/product/view">详情记录</a></el-menu-item>
            <el-menu-item index="5-2"><a href="/booking/${context}/tag/view">标签</a></el-menu-item>
            <el-menu-item index="5-3"><a href="/booking/${context}/productSpec/view">规格</a></el-menu-item>
        </el-submenu>

        <el-submenu index="6">
            <span slot="title"> <i class="el-icon-menu"></i>订单</span>
            <el-menu-item index="6-1"><a href="/booking/${context}/order/view">详情记录</a></el-menu-item>
        </el-submenu>
        <el-menu-item index="7">
            <i class="el-icon-menu"></i>
            <span slot="title"><a href="/booking/${context}/advertisement/view">广告走马灯</a></span>
        </el-menu-item>
        <el-menu-item index="8">
            <i class="el-icon-menu"></i>
            <span slot="title"><a href="/booking/${context}/websocket/view">即时订单</a></span>
        </el-menu-item>
    </el-menu>
</el-aside>