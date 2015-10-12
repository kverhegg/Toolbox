/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.json;

import com.compomics.util.experiment.identification.identification_parameters.SearchParameters;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 *
 * @author Kenneth
 */
public class Main {

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        //loading the initial file
        ClassLoader classLoader = Main.class.getClassLoader();
        File parameterFile = new File(classLoader.getResource("tutorial.par").getFile());
        //create the original search parameters
        SearchParameters originalParameters = SearchParameters.getIdentificationParameters(parameterFile);
        //convert the parameters and save to a file
        File jsonFile = new File(parameterFile.getAbsolutePath() + ".json");
        saveParametersToJson(originalParameters, jsonFile);
        //convert the jsonFile back to a parameterObject
        SearchParameters convertedParameters = getParametersFromJson(jsonFile);
        //check if the contects still match
        System.out.println("Conversion fasta matches original:\t" + originalParameters.getFastaFile().equals(convertedParameters.getFastaFile()));
        System.out.println("Conversion enzyme matches original:\t" + originalParameters.getEnzyme().getName().equals(convertedParameters.getEnzyme().getName()));
        System.out.println("Conversion frag ion acc matches original:\t" + (Objects.equals(originalParameters.getFragmentIonAccuracy(), convertedParameters.getFragmentIonAccuracy())));
    }

    public static String getJsonFromFile(File jsonFile) throws FileNotFoundException, IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile)));
        String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    public static SearchParameters getParametersFromJson(File jsonFile) throws IOException {
        return getParametersFromJsonString(getJsonFromFile(jsonFile));
    }

    public static SearchParameters getParametersFromJsonString(String jsonString) {
        JsonConverter jsonConverter = JsonConverter.getInstance();
        return (SearchParameters) jsonConverter.fromJson(SearchParameters.class, jsonString);
    }

    public static void saveParametersToJson(SearchParameters parameters, File jsonFile) throws IOException {
        JsonConverter jsonConverter = JsonConverter.getInstance();
        String json = jsonConverter.toJson(parameters);
        try (
                FileWriter out = new FileWriter(jsonFile)) {
            out.append(json.replace("}", "}" + System.lineSeparator())).flush();
            System.out.println("Saved parameters as son at " + jsonFile.getAbsolutePath());
        }
    }

    public static void saveParametersToJson(File searchParameterFile, File jsonFile) throws IOException, FileNotFoundException, ClassNotFoundException {
        SearchParameters originalParameters = SearchParameters.getIdentificationParameters(searchParameterFile);
        saveParametersToJson(originalParameters, jsonFile);
    }

}
