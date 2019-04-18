package com.booking.api;

import com.booking.common.service.IProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;

@Transactional
public class ProductImportTest extends AbstractTransactionalTestService {

    @Autowired
    IProductService productService;

    @Test
    public void testImport(){
        try {
            FileInputStream inputStream = new FileInputStream(new File("F:\\1555550427705.xls"));
            productService.importExcel(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
