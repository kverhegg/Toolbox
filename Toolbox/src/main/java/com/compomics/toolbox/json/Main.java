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

/**
 *
 * @author Kenneth
 */
public class Main {

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        //loading the initial file
        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("tutorial.par").getFile());
        //creat the original search parameters
        SearchParameters originalParameters = SearchParameters.getIdentificationParameters(file);
        //get the converter instance
        JsonConverter jsonConverter = JsonConverter.getInstance();
        String json = jsonConverter.toJson(originalParameters);
        //save the json to a file if needed...
        File outputFile = new File(System.getProperty("user.home") + "/searchparameters/" + file.getName() + ".json");
        outputFile.getParentFile().mkdirs();
        try (
                FileWriter out = new FileWriter(outputFile)) {
            out.append(json.replace("}","}"+System.lineSeparator())).flush();
            System.out.println("Saved parameters as son at " + outputFile.getAbsolutePath());
        }
        //convert the json string back to search parameters (this can also be read from a file...)
        SearchParameters convertedParameters = (SearchParameters) jsonConverter.fromJson(SearchParameters.class, getJsonFromFile(outputFile));
        System.out.println("Conversion matches original:\t" + originalParameters.getFastaFile().equals(convertedParameters.getFastaFile()));
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

}
