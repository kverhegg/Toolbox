/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.respin.result.collator;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Kenneth
 */
public interface AbstractCollator {

public void collate(File outputFile, File inputFolder) throws IOException;

public String getHeaders();

}
