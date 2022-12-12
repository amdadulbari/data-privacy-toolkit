/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2020                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.risk;

import com.ibm.research.drl.dpt.datasets.IPVDataset;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrequencyAnalysisTest {
    
    @Test
    public void testMatches() throws IOException {
        
        IPVDataset maskedDataset = IPVDataset.load(this.getClass().getResourceAsStream("/freqAnalysisTest.csv"), false, ',', '"', false);
        Map<String, String> originalToMasked = new HashMap<>();
        originalToMasked.put("John", "abc");
        originalToMasked.put("Jack", "cde");
        originalToMasked.put("George", "efg");
        
        List<String> auxiliaryDataRanked = new ArrayList<>();
        auxiliaryDataRanked.add("John");
        auxiliaryDataRanked.add("Jack");
        auxiliaryDataRanked.add("Thomas");
        
        //abc appears 3 times and should match auxiliary John
        //cde appears 2 times and should match auxiliary Jack
        //efg appears 1 time and should not match auxiliary Thomas
        //total records reversed: 5
        FrequencyAnalysis frequencyAnalysis = new FrequencyAnalysis(originalToMasked, auxiliaryDataRanked);
        
        assertEquals(5, frequencyAnalysis.successfulMatches(maskedDataset, 0));
    }


    @Test
    @Disabled
    public void testFloridaExperiment() throws IOException {
        IPVDataset originalDataset = IPVDataset.load(this.getClass().getResourceAsStream("/names_1989.txt"), false, ',', '"', false);

        Map<String, String> originalToMasked = new HashMap<>();
        for(int i = 0; i < originalDataset.getNumberOfRows(); i++) {
            String name = originalDataset.get(i, 1).toLowerCase();
            String maskedName = "" + name.hashCode();
            originalToMasked.put(name, maskedName);
            originalDataset.set(i, 1, maskedName); //replace
        }

        List<String> auxiliaryDataRanked = new ArrayList<>();
        IPVDataset auxiliary = IPVDataset.load(this.getClass().getResourceAsStream("/names_auxiliary_ranked.txt"), false, ',', '"', false);
        for(int i = 0; i < auxiliary.getNumberOfRows(); i++) {
            auxiliaryDataRanked.add(auxiliary.get(i, 0));
        }
        
        FrequencyAnalysis frequencyAnalysis = new FrequencyAnalysis(originalToMasked, auxiliaryDataRanked);
        System.out.println(frequencyAnalysis.successfulMatches(originalDataset, 1));
    }

}

