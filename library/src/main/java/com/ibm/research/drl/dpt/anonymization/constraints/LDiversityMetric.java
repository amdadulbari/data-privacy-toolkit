/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2020                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.anonymization.constraints;


import com.ibm.research.drl.dpt.anonymization.PrivacyMetric;
import com.ibm.research.drl.dpt.util.Histogram;

import java.util.ArrayList;
import java.util.List;

public class LDiversityMetric extends KAnonymityMetric {
    private List<Histogram> histograms;

    public List<Histogram> getHistograms() {
        return histograms;
    }

    public LDiversityMetric() {
        super();
        this.histograms = new ArrayList<>();
    }

    public LDiversityMetric(List<String> sensitiveValues) {
        super(1);
        this.histograms = new ArrayList<>();

        for(int i = 0; i < sensitiveValues.size(); i++) {
            Histogram histogram = new Histogram();
            histogram.put(sensitiveValues.get(i), 1L);

            this.histograms.add(histogram);
        }
    }

    @Override
    public PrivacyMetric getInstance(List<String> sensitiveValues) {
        return new LDiversityMetric(sensitiveValues);
    }

    @Override
    public void update(PrivacyMetric metric) {
        super.update(metric);

        LDiversityMetric lDiversityMetric = (LDiversityMetric)metric;
        List<Histogram> metricHistograms = lDiversityMetric.getHistograms();

        for(int i = 0; i < metricHistograms.size(); i++) {
            if (histograms.size() <= i) {
                histograms.add(new Histogram());
            }

            histograms.get(i).update(metricHistograms.get(i));
        }
    }
}
