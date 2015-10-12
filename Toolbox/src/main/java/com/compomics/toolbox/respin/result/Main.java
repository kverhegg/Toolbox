/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result;

import com.compomics.toolbox.respin.result.collator.AbstractCollator;
import com.compomics.toolbox.respin.result.collator.impl.SequenceCollator;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Kenneth
 */
public class Main {

    public static void main(String[] args) throws IOException {
        File inputFolder = new File("C:\\Users\\Kenneth\\Desktop\\Requests\\Gerben\\smORF_reports_OKT_2015\\sorfs_psm_corr");
        File outputFile = new File(inputFolder.getParentFile(), inputFolder.getName() + "_sequences.tsv");
        AbstractCollator collator = new SequenceCollator();
        collator.collate(outputFile, inputFolder);
    }
}
