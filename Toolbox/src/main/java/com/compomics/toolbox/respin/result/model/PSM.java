/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result.model;

/**
 *
 * @author Kenneth
 */
public class PSM {

    private final double confidence;
    private final double mass_error;
    private final int charge;
    private final String peptide;
    private final String spectrum;
    private final String assay;

    public PSM(double confidence, double mass_error, int charge, String peptide, String spectrum, String assay) {
        this.confidence = confidence;
        this.mass_error = mass_error;
        this.charge = charge;
        this.peptide = peptide;
        this.spectrum = spectrum;
        this.assay = assay;
    }

    public double getConfidence() {
        return confidence;
    }

    public double getMass_error() {
        return mass_error;
    }

    public int getCharge() {
        return charge;
    }

    public String getSequence() {
        return peptide;
    }

    public String getSpectrum() {
        return spectrum;
    }

    public String getAssay() {
        return assay;
    }

}
