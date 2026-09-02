package es.um.dis.tecnomod.huron.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.semanticweb.owlapi.model.IRI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PropertiesFileParser {

    private static final String[] EXPECTED_KEYS = {"names", "synonyms", "descriptions"};

    public static Map<String, List<IRI>> parse(InputStream inputStream) {
    	ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading properties file.", e);
        }
        Map<String, List<IRI>> result = new LinkedHashMap<>();

        for (String key : EXPECTED_KEYS) {
            JsonNode value = root.get(key);

            if (value == null) {
                throw new IllegalArgumentException(
                        "Expected key '" + key + "' missing in the properties file.");
            }
            if (!value.isArray()) {
                throw new IllegalArgumentException(
                        "Array expected for the key '" + key + "'");
            }

            List<IRI> iris = new ArrayList<>();
            for (JsonNode iriNode : value) {
                if (!iriNode.isTextual()) {
                    throw new IllegalArgumentException(
                            "IRI string expected in '" + key + "', found: " + iriNode);
                }
                iris.add(IRI.create(iriNode.asText()));
            }

            result.put(key, iris);
        }

        return result;
    }

    public static Map<String, List<IRI>> parse(File file) throws FileNotFoundException {
        try (InputStream inputStream = new FileInputStream(file)) {
            return parse(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error closing properties file.", e);
        }
    }

    public static  Map<String, List<IRI>> parse(String jsonContent) {
    	try (InputStream inputStream = IOUtils.toInputStream(jsonContent, StandardCharsets.UTF_8)) {
    		return parse(inputStream);
    	} catch (IOException e) {
            throw new RuntimeException("Error closing properties file.", e);
        }
    }
}