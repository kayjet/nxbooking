package com.booking.common.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

@ExcelTarget("ImportExcelProductDto")
public class ImportExcelProductDto {
    @Excel(name = "产品名", height = 20, width = 40, isImportField = "true_st")
    private String title;

    @Excel(name = "介绍", height = 20, width = 40, isImportField = "true_st")
    private String detail;

    @Excel(name = "单价(元)", height = 20, width = 40, isImportField = "true_st")
    private Double price;

    public ImportExcelProductDto() {
    }

    public ImportExcelProductDto(Double price, String detail, String title) {
        this.price = price;
        this.detail = detail;
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
