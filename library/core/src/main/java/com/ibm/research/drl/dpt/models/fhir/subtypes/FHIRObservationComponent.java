/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2021                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.models.fhir.subtypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.research.drl.dpt.models.fhir.datatypes.*;

import java.util.Collection;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FHIRObservationComponent {

    private FHIRCodeableConcept code;
    private FHIRQuantity valueQuantity;
    private FHIRCodeableConcept valueCodeableConcept;
    private String valueString;
    private FHIRRange valueRange;
    private FHIRRatio valueRatio;
    private FHIRSampledData valueSampledData;
    private FHIRAttachment valueAttachment;
    private String valueTime;
    private String valueDateTime;
    private FHIRPeriod valuePeriod;
    private FHIRCodeableConcept dataAbsentReason;
    private Collection<FHIRObservationReferenceRange> referenceRange;

    public FHIRCodeableConcept getCode() {
        return code;
    }

    public void setCode(FHIRCodeableConcept code) {
        this.code = code;
    }

    public FHIRQuantity getValueQuantity() {
        return valueQuantity;
    }

    public void setValueQuantity(FHIRQuantity valueQuantity) {
        this.valueQuantity = valueQuantity;
    }

    public FHIRCodeableConcept getValueCodeableConcept() {
        return valueCodeableConcept;
    }

    public void setValueCodeableConcept(FHIRCodeableConcept valueCodeableConcept) {
        this.valueCodeableConcept = valueCodeableConcept;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public FHIRRange getValueRange() {
        return valueRange;
    }

    public void setValueRange(FHIRRange valueRange) {
        this.valueRange = valueRange;
    }

    public FHIRRatio getValueRatio() {
        return valueRatio;
    }

    public void setValueRatio(FHIRRatio valueRatio) {
        this.valueRatio = valueRatio;
    }

    public FHIRSampledData getValueSampledData() {
        return valueSampledData;
    }

    public void setValueSampledData(FHIRSampledData valueSampledData) {
        this.valueSampledData = valueSampledData;
    }

    public FHIRAttachment getValueAttachment() {
        return valueAttachment;
    }

    public void setValueAttachment(FHIRAttachment valueAttachment) {
        this.valueAttachment = valueAttachment;
    }

    public String getValueTime() {
        return valueTime;
    }

    public void setValueTime(String valueTime) {
        this.valueTime = valueTime;
    }

    public String getValueDateTime() {
        return valueDateTime;
    }

    public void setValueDateTime(String valueDateTime) {
        this.valueDateTime = valueDateTime;
    }

    public FHIRPeriod getValuePeriod() {
        return valuePeriod;
    }

    public void setValuePeriod(FHIRPeriod valuePeriod) {
        this.valuePeriod = valuePeriod;
    }

    public FHIRCodeableConcept getDataAbsentReason() {
        return dataAbsentReason;
    }

    public void setDataAbsentReason(FHIRCodeableConcept dataAbsentReason) {
        this.dataAbsentReason = dataAbsentReason;
    }

    public Collection<FHIRObservationReferenceRange> getReferenceRange() {
        return referenceRange;
    }

    public void setReferenceRange(Collection<FHIRObservationReferenceRange> referenceRange) {
        this.referenceRange = referenceRange;
    }

}


