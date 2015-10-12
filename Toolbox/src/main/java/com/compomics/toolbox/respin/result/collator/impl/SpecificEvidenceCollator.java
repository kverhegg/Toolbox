/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result.collator.impl;

import com.compomics.toolbox.respin.result.model.SequenceEvidence;
import com.compomics.toolbox.respin.result.reader.SequenceEvidenceReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Kenneth
 */
public class SpecificEvidenceCollator extends EvidenceCollator {

    private final Collection<String> proteinAccessionsToMonitor;

    public SpecificEvidenceCollator(Collection<String> proteinAccessionsToMonitor) {
        this.proteinAccessionsToMonitor = proteinAccessionsToMonitor;
    }

    @Override
    public void collate(File outputFile, File inputFolder) throws IOException {
        SequenceEvidenceReader reader = new SequenceEvidenceReader();
        try (FileWriter out = new FileWriter(outputFile)) {
            System.out.println("Writing evidence to file...");
            out.append(getHeaders()).append(System.lineSeparator()).flush();
            for (SequenceEvidence evidence : reader.readFiles(inputFolder).values()) {
                System.out.println(evidence.getIdentifier());
                if (proteinAccessionsToMonitor.contains(evidence.getIdentifier())) {
                    out.append(evidence.toString()).append(System.lineSeparator()).flush();
                }
            }
        }
    }

}
