/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2021                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.models.fhir.subtypes;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.research.drl.dpt.models.fhir.FHIRReference;
import com.ibm.research.drl.dpt.models.fhir.datatypes.FHIRCodeableConcept;
import com.ibm.research.drl.dpt.models.fhir.datatypes.FHIRCoding;
import com.ibm.research.drl.dpt.models.fhir.datatypes.FHIRIdentifier;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FHIRAuditEventParticipant {

    private Collection<FHIRCodeableConcept> role;
    private FHIRReference reference;
    private FHIRIdentifier userId;
    private String altId;
    private String name;
    private boolean requestor;
    private FHIRReference location;
    private Collection<String> policy;
    private FHIRCoding media;
    private FHIRAuditEventParticipantNetwork network;
    private Collection<FHIRCoding> purposeOfUse;

    public Collection<FHIRCodeableConcept> getRole() {
        return role;
    }

    public void setRole(Collection<FHIRCodeableConcept> role) {
        this.role = role;
    }

    public FHIRReference getReference() {
        return reference;
    }

    public void setReference(FHIRReference reference) {
        this.reference = reference;
    }

    public FHIRIdentifier getUserId() {
        return userId;
    }

    public void setUserId(FHIRIdentifier userId) {
        this.userId = userId;
    }

    public String getAltId() {
        return altId;
    }

    public void setAltId(String altId) {
        this.altId = altId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequestor() {
        return requestor;
    }

    public void setRequestor(boolean requestor) {
        this.requestor = requestor;
    }

    public FHIRReference getLocation() {
        return location;
    }

    public void setLocation(FHIRReference location) {
        this.location = location;
    }

    public Collection<String> getPolicy() {
        return policy;
    }

    public void setPolicy(Collection<String> policy) {
        this.policy = policy;
    }

    public FHIRCoding getMedia() {
        return media;
    }

    public void setMedia(FHIRCoding media) {
        this.media = media;
    }

    public FHIRAuditEventParticipantNetwork getNetwork() {
        return network;
    }

    public void setNetwork(FHIRAuditEventParticipantNetwork network) {
        this.network = network;
    }

    public Collection<FHIRCoding> getPurposeOfUse() {
        return purposeOfUse;
    }

    public void setPurposeOfUse(Collection<FHIRCoding> purposeOfUse) {
        this.purposeOfUse = purposeOfUse;
    }
}
