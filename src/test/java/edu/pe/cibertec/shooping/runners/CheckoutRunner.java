package edu.pe.cibertec.shooping.runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,
        value = "edu.pe.cibertec.shooping.steps,edu.pe.cibertec.shooping.hooks")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME,
        value = "@checkout")   // Filtra solo escenarios con esta etiqueta
public class CheckoutRunner {
}