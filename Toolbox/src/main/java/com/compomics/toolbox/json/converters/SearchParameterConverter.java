package com.compomics.toolbox.json.converters;

/**
 *
 * @author Kenneth Verheggen
 */
import com.compomics.toolbox.json.DefaultJsonConverter;
import com.compomics.util.experiment.biology.Atom;
import com.compomics.util.experiment.identification.identification_parameters.IdentificationAlgorithmParameter;
import com.compomics.util.experiment.identification.identification_parameters.SearchParameters;
import java.io.File;
import java.io.IOException;

/**
 * This class is a convenience class to have a DefaultJsonConverter with the
 * search parameter interfaces
 *
 * @author Kenneth Verheggen
 */
public class SearchParameterConverter extends DefaultJsonConverter {

    /**
     * Default constructor
     */
    public SearchParameterConverter() {
        super(IdentificationAlgorithmParameter.class, Atom.class);
    }

    /**
     *
     * @param objectType The class the object belongs to
     * @param jsonFile a json file
     * @return an instance of the objectType containing the json information
     * @throws IOException
     */
    @Override
    public Object fromJson(Class objectType, File jsonFile) throws IOException {
        String jsonString = super.getJsonStringFromFile(jsonFile);
        SearchParameters param = (SearchParameters) gson.fromJson(jsonString, objectType);
        param.setParametersFile(jsonFile);
        return param;
    }
}
