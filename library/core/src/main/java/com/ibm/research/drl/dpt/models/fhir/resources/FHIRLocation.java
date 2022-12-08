/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2021                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.models.fhir.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.research.drl.dpt.models.fhir.FHIRBaseDomainResource;
import com.ibm.research.drl.dpt.models.fhir.FHIRReference;
import com.ibm.research.drl.dpt.models.fhir.datatypes.FHIRAddress;
import com.ibm.research.drl.dpt.models.fhir.datatypes.FHIRCodeableConcept;
import com.ibm.research.drl.dpt.models.fhir.datatypes.FHIRContactPoint;
import com.ibm.research.drl.dpt.models.fhir.datatypes.FHIRIdentifier;
import com.ibm.research.drl.dpt.models.fhir.subtypes.FHIRLocationPosition;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FHIRLocation extends FHIRBaseDomainResource {

    public Collection<FHIRIdentifier> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Collection<FHIRIdentifier> identifier) {
        this.identifier = identifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public FHIRCodeableConcept getType() {
        return type;
    }

    public void setType(FHIRCodeableConcept type) {
        this.type = type;
    }

    public Collection<FHIRContactPoint> getTelecom() {
        return telecom;
    }

    public void setTelecom(Collection<FHIRContactPoint> telecom) {
        this.telecom = telecom;
    }

    public FHIRAddress getAddress() {
        return address;
    }

    public void setAddress(FHIRAddress address) {
        this.address = address;
    }

    public FHIRCodeableConcept getPhysicalType() {
        return physicalType;
    }

    public void setPhysicalType(FHIRCodeableConcept physicalType) {
        this.physicalType = physicalType;
    }

    public FHIRLocationPosition getPosition() {
        return position;
    }

    public void setPosition(FHIRLocationPosition position) {
        this.position = position;
    }

    public FHIRReference getManagingOrganization() {
        return managingOrganization;
    }

    public void setManagingOrganization(FHIRReference managingOrganization) {
        this.managingOrganization = managingOrganization;
    }

    public FHIRReference getPartOf() {
        return partOf;
    }

    public void setPartOf(FHIRReference partOf) {
        this.partOf = partOf;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    private String resourceType;
    private Collection<FHIRIdentifier> identifier;
    private String status;
    private String name;
    private String description;
    private String mode;
    private FHIRCodeableConcept type;
    private Collection<FHIRContactPoint> telecom;
    private FHIRAddress address;
    private FHIRCodeableConcept physicalType;
    private FHIRLocationPosition position;
    private FHIRReference managingOrganization;
    private FHIRReference partOf;
}
