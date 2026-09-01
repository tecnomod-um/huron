package es.um.dis.tecnomod.huron.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

public class PropertiesFileParserTest {
	private static final String PROPERTIES_FILE_PATH = "/customPropertiesFile.json";

	@Test
	public void test() {
		Map<String, List<IRI>> x = PropertiesFileParser.parse(this.getClass().getResourceAsStream(PROPERTIES_FILE_PATH));
		assertNotNull(x);
		assertEquals(3, x.keySet().size());
		assertTrue(x.containsKey("names"));
		assertTrue(x.containsKey("synonyms"));
		assertTrue(x.containsKey("descriptions"));
		assertTrue(x.get("names").contains(IRI.create("http://www.w3.org/2004/02/skos/core#prefLabel")));
		assertTrue(x.get("synonyms").contains(IRI.create("http://www.w3.org/2004/02/skos/core#altLabel")));
		assertTrue(x.get("descriptions").contains(IRI.create("http://purl.obolibrary.org/obo/IAO_0000115")));

		assertFalse(x.get("names").contains(IRI.create("http://www.w3.org/2004/02/skos/core#altLabel")));
		assertFalse(x.get("synonyms").contains(IRI.create("http://www.w3.org/2004/02/skos/core#prefLabel")));
		assertFalse(x.get("descriptions").contains(IRI.create("http://www.w3.org/2004/02/skos/core#prefLabel")));

		assertEquals(3, x.get("names").size());
		assertEquals(3, x.get("synonyms").size());
		assertEquals(3, x.get("descriptions").size());
	}

}
