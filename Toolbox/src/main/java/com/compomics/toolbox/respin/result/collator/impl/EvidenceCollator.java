/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result.collator.impl;

import com.compomics.toolbox.respin.result.collator.AbstractCollator;
import com.compomics.toolbox.respin.result.model.SequenceEvidence;
import com.compomics.toolbox.respin.result.reader.SequenceEvidenceReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Kenneth
 */
public class EvidenceCollator implements AbstractCollator {

    @Override
    public void collate(File outputFile, File inputFolder) throws IOException {
        SequenceEvidenceReader reader = new SequenceEvidenceReader();
        try (FileWriter out = new FileWriter(outputFile)) {
            System.out.println("Writing evidence to file...");
            out.append(getHeaders()).append(System.lineSeparator()).flush();
            for (SequenceEvidence evidence : reader.readFiles(inputFolder).values()) {
                out.append(evidence.toString()).append(System.lineSeparator()).flush();
            }
        }
    }

    @Override
    public String getHeaders() {
        return "ID\t#assay\tassays\t#peptide\t#psm\tconf\tmass_errors\tcharges";
    }
}
