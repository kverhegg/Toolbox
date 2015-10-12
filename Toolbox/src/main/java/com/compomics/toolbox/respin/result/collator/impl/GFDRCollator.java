/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result.collator.impl;

import com.compomics.toolbox.respin.result.collator.AbstractCollator;
import com.compomics.toolbox.respin.result.model.PSM;
import com.compomics.toolbox.respin.result.model.SequenceEvidence;
import com.compomics.toolbox.respin.result.model.peptide.PeptideEvidence;
import com.compomics.toolbox.respin.result.reader.SequenceEvidenceReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author Kenneth
 */
public class GFDRCollator implements AbstractCollator {

    private final HashMap<String, PeptideEvidence> peptideEvidenceMap = new HashMap<>();

    @Override
    public void collate(File outputFile, File inputFolder) throws IOException {
        SequenceEvidenceReader reader = new SequenceEvidenceReader();
        HashMap<String, SequenceEvidence> sequenceEvidences = reader.readFiles(inputFolder);
        //load and sort all evidence
        for (SequenceEvidence sequenceEvidence : sequenceEvidences.values()) {
            List<PSM> psms = sequenceEvidence.getPsms();
            for (PSM aPSM : psms) {
                PeptideEvidence peptideEvidence = peptideEvidenceMap.getOrDefault(aPSM.getSequence(), new PeptideEvidence(aPSM.getSequence()));
                peptideEvidence.addPSM(aPSM);
                peptideEvidenceMap.put(peptideEvidence.getSequence(), peptideEvidence);
            }
        }
        //calculate the global fdr
        System.out.println("Calculating gfdr per peptide...");
        TreeSet<PeptideEvidence> peptideEvidences = calculateGlobalFDR();
        //todo write !
        System.out.println("Writing results to file...");
        try (FileWriter out = new FileWriter(outputFile)) {
            out.append(getHeaders()).append(System.lineSeparator()).flush();
            for (PeptideEvidence evidence : peptideEvidences) {
                out.append(evidence.toString()).append(System.lineSeparator()).flush();
            }
        }
    }

    @Override
    public String getHeaders() {
        return "peptide\t#psms\t#assays\tassay_count\tmax_confidence\tmin_PEP\tgFDR";
    }

    public TreeSet<PeptideEvidence> calculateGlobalFDR() {
        //sort the peptideEvidences
        TreeSet<PeptideEvidence> peptideEvidences = new TreeSet<>(new Comparator<PeptideEvidence>() {

            @Override
            public int compare(PeptideEvidence o1, PeptideEvidence o2) {
                if (o1.getBestConfidence() > o2.getBestConfidence()) {
                    return 1;
                } else if (o1.getBestConfidence() < o2.getBestConfidence()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        for (PeptideEvidence evidence : peptideEvidenceMap.values()) {
            peptideEvidences.add(evidence);
        }
        //calculate per line what the estimated amount of wrong peptides is so far
        int currentAmountOfPeptidesEncountered = 0;
        int totalAmountOfPeptides = peptideEvidences.size();
        for (PeptideEvidence evidence : peptideEvidences) {
            currentAmountOfPeptidesEncountered++;
            double fdr = (evidence.getBestPEP() * currentAmountOfPeptidesEncountered) / totalAmountOfPeptides;
            evidence.setGlobalFDRValue(fdr);
        }
        return peptideEvidences;
    }

}
