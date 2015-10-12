/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.json;

import com.compomics.toolbox.json.converters.SearchParameterConverter;
import com.compomics.util.experiment.identification.identification_parameters.SearchParameters;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author Kenneth
 */
public class Main {

    private static final JsonConverter jsonConverter = new SearchParameterConverter();

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        //loading the initial file
        ClassLoader classLoader = Main.class.getClassLoader();
        File parameterFile = new File(classLoader.getResource("tutorial.par").getFile());
        //create the original search parameters
        SearchParameters originalParameters = SearchParameters.getIdentificationParameters(parameterFile);
        //convert the parameters and save to a file
        File jsonFile = new File(parameterFile.getAbsolutePath() + ".json");
        jsonConverter.saveObjectToJson(originalParameters, jsonFile);
        //convert the jsonFile back to a parameterObject
        SearchParameters convertedParameters = (SearchParameters) jsonConverter.fromJson(SearchParameters.class, jsonFile);
        //check if the contects still match
        System.out.println("Conversion fasta matches original:\t" + originalParameters.getFastaFile().equals(convertedParameters.getFastaFile()));
        System.out.println("Conversion enzyme matches original:\t" + originalParameters.getEnzyme().getName().equals(convertedParameters.getEnzyme().getName()));
        System.out.println("Conversion frag ion acc matches original:\t" + (Objects.equals(originalParameters.getFragmentIonAccuracy(), convertedParameters.getFragmentIonAccuracy())));
    }

}
