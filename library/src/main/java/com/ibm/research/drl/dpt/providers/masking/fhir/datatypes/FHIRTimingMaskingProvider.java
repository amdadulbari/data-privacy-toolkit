/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2021                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.masking.fhir.datatypes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.ibm.research.drl.dpt.configuration.MaskingConfiguration;
import com.ibm.research.drl.dpt.models.fhir.datatypes.FHIRTiming;
import com.ibm.research.drl.dpt.providers.masking.AbstractComplexMaskingProvider;
import com.ibm.research.drl.dpt.providers.masking.MaskingProvider;
import com.ibm.research.drl.dpt.providers.masking.MaskingProviderFactory;
import com.ibm.research.drl.dpt.providers.masking.fhir.FHIRMaskingUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class FHIRTimingMaskingProvider extends AbstractComplexMaskingProvider<JsonNode> {

    private final boolean maskEvent;
    private final boolean maskCode;

    private final MaskingProvider eventMaskingProvider;
    private final FHIRCodeableConceptMaskingProvider codeMaskingProvider;

    private final String EVENT_PATH;
    private final String CODE_PATH;

    public FHIRTimingMaskingProvider(MaskingConfiguration maskingConfiguration, Set<String> maskedFields, String fieldPath, MaskingProviderFactory factory) {
        super("fhir", maskingConfiguration, maskedFields, factory);

        this.EVENT_PATH = fieldPath + "/event";
        this.CODE_PATH = fieldPath + "/code";

        this.maskEvent = maskingConfiguration.getBooleanValue("fhir.timing.maskEvent");
        this.maskCode = maskingConfiguration.getBooleanValue("fhir.timing.maskCode");

        this.eventMaskingProvider = getMaskingProvider(EVENT_PATH, maskingConfiguration, this.factory);
        this.codeMaskingProvider = new FHIRCodeableConceptMaskingProvider(getConfigurationForSubfield(CODE_PATH, maskingConfiguration),
                maskedFields, CODE_PATH, this.factory);
    }

    public JsonNode mask(JsonNode node) {
        try {
            FHIRTiming obj = FHIRMaskingUtils.getObjectMapper().treeToValue(node, FHIRTiming.class);
            FHIRTiming maskedObj = mask(obj);
            return FHIRMaskingUtils.getObjectMapper().valueToTree(maskedObj);
        } catch (Exception e) {
            return NullNode.getInstance();
        }
    }

    private Collection<String> maskEvents(Collection<String> events) {
        Collection<String> maskedEvents = new ArrayList<>();

        for (String event : events) {
            if (event != null) {
                maskedEvents.add(eventMaskingProvider.mask(event));
            }
        }

        return maskedEvents;
    }

    public FHIRTiming mask(FHIRTiming timing) {

        if (this.maskEvent && !isAlreadyMasked(EVENT_PATH)) {
            Collection<String> events = timing.getEvent();
            if (events != null) {
                timing.setEvent(maskEvents(events));
            }
        }

        if (this.maskCode && !isAlreadyMasked(CODE_PATH)) {
            timing.setCode(codeMaskingProvider.mask(timing.getCode()));
        }

        return timing;
    }
}


