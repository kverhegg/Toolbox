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
public class SequenceCollator implements AbstractCollator {

    @Override
    public void collate(File outputFile, File inputFolder) throws IOException {
        SequenceEvidenceReader reader = new SequenceEvidenceReader();
        try (FileWriter out = new FileWriter(outputFile)) {
            System.out.println("Writing sequences to file...");
            //make a hashmap for all the sequences?
            for (SequenceEvidence evidence : reader.readFiles(inputFolder).values()) {
                out.append(evidence.getIdentifier() + "\t" + evidence.getUnique_peptides_count().toString().replace("{", "").replace("}", "")).append(System.lineSeparator()).flush();
            }
        }
    }

    @Override
    public String getHeaders() {
        return "ID\tSequence";
    }
}
