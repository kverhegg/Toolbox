package com.compomics.toolbox.json.converters;

/**
 *
 * @author Kenneth Verheggen
 */
import com.compomics.toolbox.json.DefaultJsonConverter;
import com.compomics.util.experiment.biology.Atom;
import com.compomics.util.experiment.identification.identification_parameters.IdentificationAlgorithmParameter;

/**
 * This class is a convenience class to have a DefaultJsonConverter with the search parameter interfaces
 * @author Kenneth Verheggen
 */
public class SearchParameterConverter extends DefaultJsonConverter {

    /**
     * Default constructor
     */
    public SearchParameterConverter() {
        super(IdentificationAlgorithmParameter.class, Atom.class);
    }

}
