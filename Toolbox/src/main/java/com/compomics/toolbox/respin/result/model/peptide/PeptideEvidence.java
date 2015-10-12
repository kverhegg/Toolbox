/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result.model.peptide;

import com.compomics.toolbox.respin.result.model.PSM;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

/**
 *
 * @author Kenneth
 */
public class PeptideEvidence implements Comparable {

    private final String sequence;
    private final DescriptiveStatistics confidences = new DescriptiveStatistics();
    private final DescriptiveStatistics mass_errors = new DescriptiveStatistics();
    private final HashMap<String, Integer> assayCount = new HashMap<>();
    private final List<PSM> psms = new ArrayList<>();
    private double globalFDRValue = 0.0;

    private int totalPSMCount = 0;

    public PeptideEvidence(String sequence) {
        this.sequence = sequence;
    }

    public void addPSM(PSM aPSM) {
        if (aPSM.getSequence().equalsIgnoreCase(getSequence())) {
            psms.add(aPSM);
            confidences.addValue(aPSM.getConfidence());
            mass_errors.addValue(aPSM.getMass_error());
            assayCount.putIfAbsent(aPSM.getAssay(), assayCount.getOrDefault(aPSM.getAssay(), 0) + 1);
        } else {
            throw new IllegalArgumentException("Sequences of the PSM and the peptide do not match !");
        }
    }

    public String getSequence() {
        return sequence;
    }

    public double getBestConfidence() {
        if (confidences.getMax() > 1) {
            return confidences.getMax() / 100;
        }
        return confidences.getMax();
    }

    public double getBestPEP() {
        return 1 - getBestConfidence();
    }

    public int getTotalPSMCount() {
        if (totalPSMCount == 0) {
            for (int aCount : assayCount.values()) {
                totalPSMCount += aCount;
            }
        }
        return totalPSMCount;
    }

    public DescriptiveStatistics getConfidences() {
        return confidences;
    }

    public DescriptiveStatistics getMass_errors() {
        return mass_errors;
    }

    public List<PSM> getPsms() {
        return psms;
    }

    public HashMap<String, Integer> getAssayCount() {
        return assayCount;
    }

    public double getGlobalFDRValue() {
        return globalFDRValue;
    }

    public void setGlobalFDRValue(double globalFDRValue) {
        this.globalFDRValue = globalFDRValue;
    }

    /*the sequence evidences must be sorted on :
     1. best_confidence
     2. #psms
     3. #assays
     4. sequence length
                
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof PeptideEvidence) {
            PeptideEvidence other = (PeptideEvidence) o;
            if (other.getBestPEP() > this.getBestPEP()) {
                return -1;
            } else if (other.getBestPEP() < this.getBestPEP()) {
                return 1;
            } else {
                if (other.getTotalPSMCount() > this.getTotalPSMCount()) {
                    return -1;
                } else if (other.getTotalPSMCount() < this.getTotalPSMCount()) {
                    return 1;
                } else {
                    if (other.getAssayCount().size() > this.getAssayCount().size()) {
                        return -1;
                    } else if (other.getAssayCount().size() < this.getAssayCount().size()) {
                        return 1;
                    } else {
                        if (other.getSequence().length() < this.getSequence().length()) {
                            return -1;
                        } else if (other.getSequence().length() > this.getSequence().length()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        } else {
            return -1;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.sequence);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PeptideEvidence other = (PeptideEvidence) obj;
        return Objects.equals(this.sequence, other.sequence);
    }

    @Override
    public String toString() {
        return getSequence() + "\t"
                + getPsms().size() + "\t"
                + getAssayCount().size() + "\t"
                + getAssayCount().toString().replace("{", "").replace("}", "") + "\t"
                + round(getBestConfidence()) + "\t"
                + round(getBestPEP()) + "\t"
                + +round(getGlobalFDRValue());
    }

    private double round(double value) {
        return new BigDecimal(value).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
}
