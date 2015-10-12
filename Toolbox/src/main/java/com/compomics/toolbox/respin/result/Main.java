/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result;

import com.compomics.toolbox.respin.result.collator.AbstractCollator;
import com.compomics.toolbox.respin.result.collator.impl.SpecificEvidenceCollator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Kenneth
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<String> identifiers = new ArrayList<>();
        identifiers.add("Q8NCU8");
        identifiers.add("Q56G89");
        
        File inputFolder = new File("C:\\Users\\Kenneth\\Desktop\\Requests\\Gerben\\isoforms_12_okt_psm");
        File outputFile = new File(inputFolder.getParentFile(), inputFolder.getName() + "_evidence.tsv");
        AbstractCollator collator = new SpecificEvidenceCollator(identifiers);
        collator.collate(outputFile, inputFolder);
    }
}
