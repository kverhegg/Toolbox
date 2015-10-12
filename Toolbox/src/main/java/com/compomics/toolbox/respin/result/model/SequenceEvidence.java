/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kenneth
 */
public class SequenceEvidence {

    private final String identifier;
    private String sequence = "";
    private LinkedList<PSM> psms = new LinkedList<>();
    private LinkedHashMap<String, Integer> assayCountMap = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> unique_peptides_count = new LinkedHashMap<String, Integer>();
    private int totalPSMCount;

    public SequenceEvidence(String identifier) {
        this.identifier = identifier;
    }

    public void addPSM(PSM aPSM) {
        totalPSMCount++;
        psms.add(aPSM);
        assayCountMap.put(aPSM.getAssay(), assayCountMap.getOrDefault(aPSM.getAssay(), 0) + 1);
        unique_peptides_count.put(aPSM.getSequence(), unique_peptides_count.getOrDefault(aPSM.getSequence(), 0) + 1);
    }

    @Override
    public String toString() {
        StringBuilder confidences = new StringBuilder();
        StringBuilder mass_errors = new StringBuilder();
        StringBuilder charges = new StringBuilder();
        for (PSM aPSM : psms) {
            confidences.append(aPSM.getConfidence()).append(";");
            mass_errors.append(aPSM.getMass_error()).append(";");
            charges.append(aPSM.getCharge()).append(";");
        }
        return identifier + "\t"
                + assayCountMap.size() + "\t"
                + assayCountMap.toString().replace("]", "").replace("[", "") + "\t"
                + unique_peptides_count.size() + "\t"
                + getTotalPSMCount() + "\t"
                + confidences.substring(0, confidences.lastIndexOf(";")) + "\t"
                + mass_errors.substring(0, mass_errors.lastIndexOf(";")) + "\t"
                + charges.substring(0, charges.lastIndexOf(";")) + "\t";
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getSequence() {
        return sequence;
    }

    public List<PSM> getPsms() {
        return psms;
    }

    public HashMap<String, Integer> getAssayCountMap() {
        return assayCountMap;
    }

    public HashMap<String, Integer> getUnique_peptides_count() {
        return unique_peptides_count;
    }

    public int getTotalPSMCount() {
        return totalPSMCount;
    }

}
