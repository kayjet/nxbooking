
<el-header style=" font-size: 12px">
    <div style="float: left;">
        <el-breadcrumb separator-class="el-icon-arrow-right" style="line-height: inherit;color: #ffffff;" >
            <el-breadcrumb-item>后台</el-breadcrumb-item>
            <#--<#list navList as nav>-->
                <#--<el-breadcrumb-item>${nav}</el-breadcrumb-item>-->
            <#--</#list>-->
        </el-breadcrumb>
    </div>

    <div style="float: right;">
        <span style="color: #ffffff;margin-right: 8px;">Admin</span>

        <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px;cursor: pointer;"></i>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>登出</el-dropdown-item>
                <el-dropdown-item>新增</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</el-header>
