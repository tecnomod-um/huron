package es.um.dis.tecnomod.huron.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.parameters.Imports;

import es.um.dis.tecnomod.huron.result_model.ResultModelInterface;
import es.um.dis.tecnomod.huron.utils.PropertiesFileParser;

/**
 * The Class Config.
 */
public class Config implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4121194238969339824L;

	private static final String DEFAULT_PROPERTIES_BY_TOPIC_FILE = "/defaultPropertiesByTopic.json";

	/** The imports. */
	private  Imports imports;

	/** The exporters. */
	private List<ResultModelInterface> resultModels;

	/** Map relating properties to the fact they describe (names, descriptions, synonyms... */
	private Map<String, List<IRI>> propertiesByTopic;

	public Config() {
		this.resultModels = new ArrayList<>();
		this.imports = Imports.EXCLUDED;
		this.resultModels = new ArrayList<ResultModelInterface> ();
		this.propertiesByTopic = getDefaultPropertiesByTopic();
	}
	/**
	 * Gets the imports.
	 *
	 * @return the imports
	 */
	public Imports getImports() {
		return imports;
	}

	/**
	 * Sets the imports.
	 *
	 * @param imports the new imports
	 */
	public void setImports(Imports imports) {
		this.imports = imports;
	}

	/**
	 * Gets the exporters.
	 *
	 * @return the exporters
	 */
	public List<ResultModelInterface> getResultModels() {
		return resultModels;
	}

	/**
	 * Sets the exporters.
	 *
	 * @param exporters the new exporters
	 */
	public void setResultModels(List<ResultModelInterface> exporters) {
		this.resultModels = exporters;
	}


	/**
	 * Adds the exporter.
	 *
	 * @param exporter the exporter
	 */
	public void addResultModel(ResultModelInterface exporter) {
		this.resultModels.add(exporter);
	}
	public Map<String, List<IRI>> getPropertiesByTopic() {
		return propertiesByTopic;
	}
	public void setPropertiesByTopic(Map<String, List<IRI>> propertiesByTopic) {
		this.propertiesByTopic = propertiesByTopic;
	}

	private static Map<String, List<IRI>> getDefaultPropertiesByTopic() {
		return PropertiesFileParser.parse(Config.class.getResourceAsStream(DEFAULT_PROPERTIES_BY_TOPIC_FILE));
	}
}
