package com.compomics.toolbox.json;

import com.compomics.toolbox.json.converters.SearchParameterConverter;
import com.compomics.util.experiment.biology.Protein;
import com.compomics.util.experiment.identification.identification_parameters.SearchParameters;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * Demonstrative class
 * @author Kenneth Verheggen
 */
public class Main {

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        //loading the initial file
        ClassLoader classLoader = Main.class.getClassLoader();
        File parameterFile = new File(classLoader.getResource("tutorial.par").getFile());
        parameterFile = new File("C:\\Users\\Kenneth\\Desktop\\Requests\\Harald Barsnes\\JSON\\tutorial.par");
        //create the original search parameters
        SearchParameters originalParameters = SearchParameters.getIdentificationParameters(parameterFile);
        //convert the parameters and save to a file

        DefaultJsonConverter jsonConverter = new SearchParameterConverter();

        File jsonFile = new File(parameterFile.getAbsolutePath() + ".json");
        jsonFile.deleteOnExit();
        
        jsonConverter.saveObjectToJson(originalParameters, jsonFile);
        //convert the jsonFile back to a parameterObject
        SearchParameters convertedParameters = (SearchParameters) jsonConverter.fromJson(SearchParameters.class, jsonFile);

        //check if the contects still match
        System.out.println("Conversion fasta matches original:\t" + originalParameters.getFastaFile().equals(convertedParameters.getFastaFile()));
        System.out.println("Conversion enzyme matches original:\t" + originalParameters.getEnzyme().getName().equals(convertedParameters.getEnzyme().getName()));
        System.out.println("Conversion frag ion acc matches original:\t" + (Objects.equals(originalParameters.getFragmentIonAccuracy(), convertedParameters.getFragmentIonAccuracy())));

        //Also works on other objects !!!!
        Protein aProtein = new Protein("KV1988", "KENNETHISTHEMOSTAWESOMEJSONPARSERBUILDERINTHEWORLD", false);
        DefaultJsonConverter converter = new DefaultJsonConverter();
        String proteinAsJson = converter.toJson(aProtein);
        Protein bProtein = (Protein) converter.fromJson(Protein.class, proteinAsJson);
        System.out.println("Same protein : "
                + (bProtein.getAccession().equals(aProtein.getAccession())
                && (bProtein.getSequence().equals(aProtein.getSequence()))
                && (bProtein.isDecoy() == aProtein.isDecoy())
                && (bProtein.computeMolecularWeight() == aProtein.computeMolecularWeight())));
        System.out.println(proteinAsJson);
    }

}
