package es.um.dis.tecnomod.huron.metrics;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

import es.um.dis.tecnomod.huron.main.Config;
import es.um.dis.tecnomod.huron.main.Prefixes;
import es.um.dis.tecnomod.huron.services.OntologyUtils;

/**
 * The Class AnnotationsPerEntityAbstractMetric.
 */
public abstract class AnnotationsPerEntityAbstractMetric extends Metric {
	public AnnotationsPerEntityAbstractMetric() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnnotationsPerEntityAbstractMetric(Config config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	private static final boolean INCLUDE_IMPORTED_ANNOTATIONS = true;



	/**
	 * Gets the usage in classes.
	 *
	 * @param annotationIRI the annotation IRI
	 * @return the usage in classes
	 */
	protected int getUsageInClasses(IRI annotationIRI) {
		int usage = 0;
		if (getOntology().containsAnnotationPropertyInSignature(annotationIRI, this.getConfig().getImports())) {
			OWLAnnotationProperty annotationProperty = getOntology().getOWLOntologyManager().getOWLDataFactory()
					.getOWLAnnotationProperty(annotationIRI);

			for (OWLAxiom axiom : getOntology().referencingAxioms(annotationProperty, this.getConfig().getImports()).collect(Collectors.toList())) {
				if (axiom.isOfType(AxiomType.ANNOTATION_ASSERTION)) {
					OWLAnnotationSubject subject = ((OWLAnnotationAssertionAxiom) axiom).getSubject();
					if (subject instanceof IRI && getOntology().containsClassInSignature((IRI) subject, this.getConfig().getImports())) {
						usage = usage + 1;
					}
				}
			}
		}
		return usage;
	}

	/**
	 * Gets the usage in data properties.
	 *
	 * @param annotationIRI the annotation IRI
	 * @return the usage in data properties
	 */
	protected int getUsageInDataProperties(IRI annotationIRI) {
		int usage = 0;
		if (getOntology().containsAnnotationPropertyInSignature(annotationIRI, this.getConfig().getImports())) {
			OWLAnnotationProperty annotationProperty = getOntology().getOWLOntologyManager().getOWLDataFactory()
					.getOWLAnnotationProperty(annotationIRI);
			for (OWLAxiom axiom : getOntology().referencingAxioms(annotationProperty, this.getConfig().getImports()).collect(Collectors.toList())) {
				if (axiom.isOfType(AxiomType.ANNOTATION_ASSERTION)) {
					OWLAnnotationSubject subject = ((OWLAnnotationAssertionAxiom) axiom).getSubject();
					if (subject instanceof IRI && getOntology().containsDataPropertyInSignature((IRI) subject, this.getConfig().getImports())) {
						usage = usage + 1;
					}
				}
			}
		}
		return usage;
	}

	/**
	 * Gets the usage in annotation properties.
	 *
	 * @param annotationIRI the annotation IRI
	 * @return the usage in annotation properties
	 */
	protected int getUsageInAnnotationProperties(IRI annotationIRI) {
		int usage = 0;
		if (getOntology().containsAnnotationPropertyInSignature(annotationIRI, this.getConfig().getImports())) {
			OWLAnnotationProperty annotationProperty = getOntology().getOWLOntologyManager().getOWLDataFactory()
					.getOWLAnnotationProperty(annotationIRI);
			for (OWLAxiom axiom : getOntology().referencingAxioms(annotationProperty, this.getConfig().getImports()).collect(Collectors.toList())) {
				if (axiom.isOfType(AxiomType.ANNOTATION_ASSERTION)) {
					OWLAnnotationSubject subject = ((OWLAnnotationAssertionAxiom) axiom).getSubject();
					if (subject instanceof IRI && getOntology().containsAnnotationPropertyInSignature((IRI) subject, this.getConfig().getImports())) {
						usage = usage + 1;
					}
				}
			}
		}
		return usage;
	}

	/**
	 * Gets the usage in properties.
	 *
	 * @param annotationIRI the annotation IRI
	 * @return the usage in properties
	 */
//	protected int getUsageInProperties(IRI annotationIRI) {
//		return this.getUsageInAnnotationProperties(annotationIRI) + this.getUsageInDataProperties(annotationIRI)
//				+ this.getUsageInObjectProperties(annotationIRI);
//	}

	/**
	 * Gets the number of annotations.
	 *
	 * @param entity the entity
	 * @param annotationsToCheck the annotations to check
	 * @return the number of annotations
	 */
	protected int getNumberOfAnnotations(OWLEntity entity, List<IRI> annotationsToCheck){
		int numberOfAnnotations = 0;
		Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms = OntologyUtils.getOWLAnnotationAssertionAxiom(entity, this.getOntology(), INCLUDE_IMPORTED_ANNOTATIONS);
		for(OWLAnnotationAssertionAxiom annotationAssertionAxiom : annotationAssertionAxioms){
			if(annotationsToCheck.contains(annotationAssertionAxiom.getProperty().getIRI())){
				numberOfAnnotations = numberOfAnnotations + 1;
			}
		}
		return numberOfAnnotations;
	}

	/**
	 * Gets the number of names.
	 *
	 * @param entity the entity
	 * @return the number of names
	 */
	protected int getNumberOfNames(OWLEntity entity){
		List <IRI> nameProperties = this.getConfig().getPropertiesByTopic().get("names");
		return getNumberOfAnnotations(entity, nameProperties);
	}

	/**
	 * Gets the number of synonyms.
	 *
	 * @param entity the entity
	 * @return the number of synonyms
	 */
	protected int getNumberOfSynonyms(OWLEntity entity){
		List <IRI> synonymProperties = this.getConfig().getPropertiesByTopic().get("synonyms");
		return getNumberOfAnnotations(entity, synonymProperties);
	}

	/**
	 * Gets the number of descriptions.
	 *
	 * @param entity the entity
	 * @return the number of descriptions
	 */
	protected int getNumberOfDescriptions(OWLEntity entity){
		List <IRI> descriptionProperties = this.getConfig().getPropertiesByTopic().get("descriptions");
		return getNumberOfAnnotations(entity, descriptionProperties);
	}
}
