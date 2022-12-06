/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2017                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.masking.excel;

import com.ibm.research.drl.dpt.configuration.*;
import com.ibm.research.drl.dpt.providers.ProviderType;
import com.ibm.research.drl.dpt.providers.masking.HashMaskingProvider;
import com.ibm.research.drl.dpt.providers.masking.MaskingProvider;
import com.ibm.research.drl.dpt.providers.masking.MaskingProviderFactory;
import com.ibm.research.drl.dpt.util.ExcelUtils;
import com.ibm.research.drl.dpt.util.FileUtils;
import com.ibm.research.drl.dpt.util.Tuple;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelMaskingProviderTest {
    
    @Test
    public void testXLSX() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/sampleXLS.xlsx");
        byte[] inputBytes = FileUtils.inputStreamToBytes(inputStream);

        String path = "/b/$D$7";
        
        String originalValue = ExcelUtils.getValue(inputBytes, DataTypeFormat.XLSX, path);
        
        Map<String, DataMaskingTarget> maskingProviders = new HashMap<>();
        maskingProviders.put(path, new DataMaskingTarget(ProviderType.EMAIL, path));
        
        ExcelMaskingProvider excelMaskingProvider = new ExcelMaskingProvider(new DefaultMaskingConfiguration(),
                DataTypeFormat.XLSX, maskingProviders, new MaskingProviderFactory(new ConfigurationManager(), maskingProviders));
        
        byte[] maskedData = excelMaskingProvider.mask(inputBytes);
       
        String maskedValue = ExcelUtils.getValue(maskedData, DataTypeFormat.XLSX, path);
       
        assertNotEquals(maskedValue, originalValue);
    }

    @Test
    public void testXLSXIgnoreNonExistentTrue() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/sampleXLS.xlsx");
        byte[] inputBytes = FileUtils.inputStreamToBytes(inputStream);

        String path = "/b2/$D$7";

        String originalValue = ExcelUtils.getValue(inputBytes, DataTypeFormat.XLSX, path);

        Map<String, DataMaskingTarget> maskingProviders = new HashMap<>();
        maskingProviders.put(path, new DataMaskingTarget(ProviderType.EMAIL, path));

        MaskingConfiguration maskingConfiguration = new DefaultMaskingConfiguration();
        maskingConfiguration.setValue("excel.mask.ignoreNonExistent", true);
        
        ExcelMaskingProvider excelMaskingProvider = new ExcelMaskingProvider(maskingConfiguration,
                DataTypeFormat.XLSX, maskingProviders, new MaskingProviderFactory(new ConfigurationManager(), maskingProviders));

        byte[] maskedData = excelMaskingProvider.mask(inputBytes);

        String maskedValue = ExcelUtils.getValue(maskedData, DataTypeFormat.XLSX, path);
        assertNull(maskedValue);
    }

    @Test
    public void testXLSXIgnoreNonExistentFalse() throws Exception {
        assertThrows(NullPointerException.class, () -> {
            InputStream inputStream = this.getClass().getResourceAsStream("/sampleXLS.xlsx");
            byte[] inputBytes = FileUtils.inputStreamToBytes(inputStream);

            String path = "/b2/$D$7";

            String originalValue = ExcelUtils.getValue(inputBytes, DataTypeFormat.XLSX, path);

            Map<String, DataMaskingTarget> maskingProviders = new HashMap<>();
            maskingProviders.put(path, new DataMaskingTarget(ProviderType.EMAIL, path));

            MaskingConfiguration maskingConfiguration = new DefaultMaskingConfiguration();
            maskingConfiguration.setValue("excel.mask.ignoreNonExistent", false);

            ExcelMaskingProvider excelMaskingProvider = new ExcelMaskingProvider(maskingConfiguration,
                    DataTypeFormat.XLSX, maskingProviders, new MaskingProviderFactory(new ConfigurationManager(), maskingProviders));

            byte[] maskedData = excelMaskingProvider.mask(inputBytes);

            String maskedValue = ExcelUtils.getValue(maskedData, DataTypeFormat.XLSX, path);
            assertNull(maskedValue);
        });
    }
    
    @Test
    public void testXLSXNumericCell() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/sampleXLS.xlsx");
        byte[] inputBytes = FileUtils.inputStreamToBytes(inputStream);

        String path = "/b/$B$7";

        String originalValue = ExcelUtils.getValue(inputBytes, DataTypeFormat.XLSX, path);
        assertEquals("12345", originalValue);

        Map<String, DataMaskingTarget> maskingProviders = new HashMap<>();
        maskingProviders.put(path, new DataMaskingTarget(ProviderType.EMAIL, path));

        ExcelMaskingProvider excelMaskingProvider = new ExcelMaskingProvider(new DefaultMaskingConfiguration(),
                DataTypeFormat.XLSX, maskingProviders, new MaskingProviderFactory(new ConfigurationManager(), maskingProviders));

        byte[] maskedData = excelMaskingProvider.mask(inputBytes);

        String maskedValue = ExcelUtils.getValue(maskedData, DataTypeFormat.XLSX, path);

        assertNotEquals(maskedValue, originalValue);
    }

    @Test
    public void testXLS() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/sampleXLS.xls");
        byte[] inputBytes = FileUtils.inputStreamToBytes(inputStream);

        String path = "/b/$D$7";

        String originalValue = ExcelUtils.getValue(inputBytes, DataTypeFormat.XLS, path);

        Map<String, DataMaskingTarget> maskingProviders = new HashMap<>();
        maskingProviders.put(path, new DataMaskingTarget(ProviderType.EMAIL, path));

        ExcelMaskingProvider excelMaskingProvider = new ExcelMaskingProvider(new DefaultMaskingConfiguration(),
                DataTypeFormat.XLS, maskingProviders, new MaskingProviderFactory(new ConfigurationManager(), maskingProviders));

        byte[] maskedData = excelMaskingProvider.mask(inputBytes);

        String maskedValue = ExcelUtils.getValue(maskedData, DataTypeFormat.XLS, path);

        assertNotEquals(maskedValue, originalValue);
    }

    @Test
    public void testXLSNumericCell() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/sampleXLS.xls");
        byte[] inputBytes = FileUtils.inputStreamToBytes(inputStream);
        
        String path = "/b/$B$7";

        String originalValue = ExcelUtils.getValue(inputBytes, DataTypeFormat.XLS, path);
        assertEquals("12345", originalValue);
        
        Map<String, DataMaskingTarget> maskingProviders = new HashMap<>();
        maskingProviders.put(path, new DataMaskingTarget(ProviderType.HASH, path));

        ExcelMaskingProvider excelMaskingProvider = new ExcelMaskingProvider(new DefaultMaskingConfiguration(),
                DataTypeFormat.XLS, maskingProviders, new MaskingProviderFactory(new ConfigurationManager(), maskingProviders));

        byte[] maskedData = excelMaskingProvider.mask(inputBytes);

        String maskedValue = ExcelUtils.getValue(maskedData, DataTypeFormat.XLS, path);

        assertNotEquals(maskedValue, originalValue);
    }

    @Test
    public void testXLSRanges() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/sampleXLS.xls");
        byte[] inputBytes = FileUtils.inputStreamToBytes(inputStream);

        String rangePath = "/d/$D$7:$E$10";
        String targetPath = "/d/$D$7:$D$10";
       
        List<Tuple<String, String>> toValidateList = Arrays.asList(
                new Tuple<>("/d/$D$7", "foo"),
                new Tuple<>("/d/$D$8", "goo"),
                new Tuple<>("/d/$D$9", "hoo"),
                new Tuple<>("/d/$D$10", "joo"),
                new Tuple<>("/d/$E$7", "foo"),
                new Tuple<>("/d/$E$8", "goo"),
                new Tuple<>("/d/$E$9", "hoo"),
                new Tuple<>("/d/$E$10", "joo")
        );
        
        Map<String, DataMaskingTarget> maskingProviders = new HashMap<>();
        maskingProviders.put(rangePath, new DataMaskingTarget(ProviderType.HASH, targetPath));

        ExcelMaskingProvider excelMaskingProvider = new ExcelMaskingProvider(new DefaultMaskingConfiguration(),
                DataTypeFormat.XLS, maskingProviders, new MaskingProviderFactory(new ConfigurationManager(), maskingProviders));
        
        MaskingProvider hashMaskingProvider = new HashMaskingProvider();

        byte[] maskedData = excelMaskingProvider.mask(inputBytes);
       
        for(Tuple<String, String> toValidate: toValidateList) {
            String path = toValidate.getFirst();
            String originalValue = toValidate.getSecond();
            
            assertEquals(originalValue, ExcelUtils.getValue(inputBytes, DataTypeFormat.XLS, path));
            
            String maskedValue = ExcelUtils.getValue(maskedData, DataTypeFormat.XLS, path);
            assertEquals(hashMaskingProvider.mask(originalValue), maskedValue);
        }

        IOUtils.write(maskedData, new FileOutputStream("/tmp/masked.xls"));
    }

    @Test
    public void testXLSRespectsTarget() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/sampleXLS.xls");
        byte[] inputBytes = FileUtils.inputStreamToBytes(inputStream);

        String path = "/b/$D$7";
        String targetPath = "/c/$D$14";

        String originalValue = ExcelUtils.getValue(inputBytes, DataTypeFormat.XLS, path);

        Map<String, DataMaskingTarget> maskingProviders = new HashMap<>();
        maskingProviders.put(path, new DataMaskingTarget(ProviderType.HASH, targetPath));

        ExcelMaskingProvider excelMaskingProvider = new ExcelMaskingProvider(new DefaultMaskingConfiguration(),
                DataTypeFormat.XLS, maskingProviders, new MaskingProviderFactory(new ConfigurationManager(), maskingProviders));

        byte[] maskedData = excelMaskingProvider.mask(inputBytes);
        
        assertEquals(originalValue, ExcelUtils.getValue(inputBytes, DataTypeFormat.XLS, path));
        
        MaskingProvider hashMaskingProvider = new HashMaskingProvider();
        
        String maskedValue = ExcelUtils.getValue(maskedData, DataTypeFormat.XLS, targetPath);
        assertEquals(hashMaskingProvider.mask(originalValue), maskedValue);

        IOUtils.write(maskedData, new FileOutputStream("/tmp/masked.xls"));
    }
}
