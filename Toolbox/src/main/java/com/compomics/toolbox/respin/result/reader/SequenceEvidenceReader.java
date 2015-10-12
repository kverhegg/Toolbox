/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result.reader;

import com.compomics.toolbox.respin.result.model.PSM;
import com.compomics.toolbox.respin.result.model.SequenceEvidence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Kenneth
 */
public class SequenceEvidenceReader {

    private HashMap<String, SequenceEvidence> sequenceEvidenceMap = new HashMap<>();
    int psmCounter = 0;

    public HashMap<String, SequenceEvidence> readFiles(File inputFolder) {
        for (File aFile : inputFolder.listFiles()) {
            try {
                System.out.println("Processing " + aFile.getName());
                readFile(aFile, true);
            } catch (IOException ex) {
                System.err.print("Warning " + aFile.getAbsolutePath() + " coult not be read");
            }
        }
        System.out.println("PSM AFTER FILTER : " + psmCounter);
        return sequenceEvidenceMap;
    }

    private void readFile(File inputFile, boolean fileHasHeaders) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(inputFile));
        String line;
        if (fileHasHeaders) {
            in.readLine();
        }
        //Human_Sorf_Search_34944_1_Default_PSM_Report
        String assay = inputFile.getName().toLowerCase().replace("human_sorf_search_", "").replace("_1_default_psm_report.txt", "");
        while ((line = in.readLine()) != null) {
            String[] split = line.split("\t");
            String[] sequenceIdentifiers = split[1].split(", ");
            double confidence = Double.parseDouble(split[19]);
            double mass_error = Double.parseDouble(split[14]);
            int charge = Integer.parseInt(split[11].replace("+", ""));
            String sequence = split[2];
            String spectrum = split[6];

            PSM psm = new PSM(confidence, mass_error, charge, sequence, spectrum, assay);
            psmCounter++;
            for (String sequenceIdentifier : sequenceIdentifiers) {
                SequenceEvidence sequenceEvidence = sequenceEvidenceMap.getOrDefault(sequenceIdentifier, new SequenceEvidence(sequenceIdentifier));
                sequenceEvidence.addPSM(psm);
                sequenceEvidenceMap.put(sequenceIdentifier, sequenceEvidence);
            }
        }
    }

}
