/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.toolbox.json;

/**
 *
 * @author Kenneth
 */
import com.compomics.util.experiment.biology.Atom;
import com.compomics.util.experiment.identification.identification_parameters.IdentificationAlgorithmParameter;
import com.compomics.toolbox.json.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {

    private static Gson gson = new Gson();
    private static JsonConverter convert;

    private JsonConverter() {
        GsonBuilder builder = new GsonBuilder();
        //register required interfaceAdapters
        builder.registerTypeAdapter(IdentificationAlgorithmParameter.class, new InterfaceAdapter<>());
        builder.registerTypeAdapter(Atom.class, new InterfaceAdapter<>());
        gson = builder.create();
    }

    public static JsonConverter getInstance() {
        if (convert == null) {
            convert = new JsonConverter();
        }
        return convert;
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public Object fromJson(Class objectType, String jsonString) {
        return gson.fromJson(jsonString, objectType);
    }
}
