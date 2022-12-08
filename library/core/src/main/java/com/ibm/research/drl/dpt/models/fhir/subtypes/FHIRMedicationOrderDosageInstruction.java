/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2021                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.models.fhir.subtypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.research.drl.dpt.models.fhir.FHIRReference;
import com.ibm.research.drl.dpt.models.fhir.datatypes.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FHIRMedicationOrderDosageInstruction {

    private String text;
    private FHIRCodeableConcept additionalInstructions;
    private FHIRTiming timing;
    private boolean asNeededBoolean;
    private FHIRCodeableConcept asNeededCodeableConcept;
    private FHIRCodeableConcept siteCodeableConcept;
    private FHIRReference siteReference;
    private FHIRCodeableConcept route;
    private FHIRCodeableConcept method;
    private FHIRRange doseRange;
    private FHIRQuantity doseQuantity;
    private FHIRRatio rateRatio;
    private FHIRRange rateRange;
    private FHIRRatio maxDosePerPeriod;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FHIRCodeableConcept getAdditionalInstructions() {
        return additionalInstructions;
    }

    public void setAdditionalInstructions(FHIRCodeableConcept additionalInstructions) {
        this.additionalInstructions = additionalInstructions;
    }

    public FHIRTiming getTiming() {
        return timing;
    }

    public void setTiming(FHIRTiming timing) {
        this.timing = timing;
    }

    public boolean isAsNeededBoolean() {
        return asNeededBoolean;
    }

    public void setAsNeededBoolean(boolean asNeededBoolean) {
        this.asNeededBoolean = asNeededBoolean;
    }

    public FHIRCodeableConcept getAsNeededCodeableConcept() {
        return asNeededCodeableConcept;
    }

    public void setAsNeededCodeableConcept(FHIRCodeableConcept asNeededCodeableConcept) {
        this.asNeededCodeableConcept = asNeededCodeableConcept;
    }

    public FHIRCodeableConcept getSiteCodeableConcept() {
        return siteCodeableConcept;
    }

    public void setSiteCodeableConcept(FHIRCodeableConcept siteCodeableConcept) {
        this.siteCodeableConcept = siteCodeableConcept;
    }

    public FHIRReference getSiteReference() {
        return siteReference;
    }

    public void setSiteReference(FHIRReference siteReference) {
        this.siteReference = siteReference;
    }

    public FHIRCodeableConcept getRoute() {
        return route;
    }

    public void setRoute(FHIRCodeableConcept route) {
        this.route = route;
    }

    public FHIRCodeableConcept getMethod() {
        return method;
    }

    public void setMethod(FHIRCodeableConcept method) {
        this.method = method;
    }

    public FHIRRange getDoseRange() {
        return doseRange;
    }

    public void setDoseRange(FHIRRange doseRange) {
        this.doseRange = doseRange;
    }

    public FHIRQuantity getDoseQuantity() {
        return doseQuantity;
    }

    public void setDoseQuantity(FHIRQuantity doseQuantity) {
        this.doseQuantity = doseQuantity;
    }

    public FHIRRatio getRateRatio() {
        return rateRatio;
    }

    public void setRateRatio(FHIRRatio rateRatio) {
        this.rateRatio = rateRatio;
    }

    public FHIRRange getRateRange() {
        return rateRange;
    }

    public void setRateRange(FHIRRange rateRange) {
        this.rateRange = rateRange;
    }

    public FHIRRatio getMaxDosePerPeriod() {
        return maxDosePerPeriod;
    }

    public void setMaxDosePerPeriod(FHIRRatio maxDosePerPeriod) {
        this.maxDosePerPeriod = maxDosePerPeriod;
    }

}
