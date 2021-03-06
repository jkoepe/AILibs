package defaultEval.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import hasco.model.Component;
import hasco.model.ComponentInstance;
import hasco.model.Parameter;

/**
 * Ok i know this is not really an optimizer ;)
 * 
 * @author Joshua
 *
 */
public class DefaultOptimizer extends Optimizer{

	
	public DefaultOptimizer(Component searcher, Component evaluator, Component classifier, String dataSet, File environment, File dataSetFolder, int seed, int maxRuntimeParam, int maxRuntime) {
		super(searcher, evaluator, classifier, dataSet, environment, dataSetFolder, seed, maxRuntimeParam, maxRuntime);
	}
	
	@Override
	public void optimize() {
		Map<String, String> searcherParameter = new HashMap<>();
		if(searcher != null) {
			// add parameter
			for (Parameter p : searcher.getParameters()) {
				searcherParameter.put(p.getName(), correctParameterSyntax( p.getDefaultValue().toString(), p.getDefaultDomain()));
			}
		}
		finalSearcher = new ComponentInstance(searcher, searcherParameter, new HashMap<>());
		
		Map<String, String> evaluatorParameter = new HashMap<>();
		if(evaluator != null) {
			// add parameter
			for (Parameter p : evaluator.getParameters()) {
				evaluatorParameter.put(p.getName(), correctParameterSyntax( p.getDefaultValue().toString(), p.getDefaultDomain()));
			}
		}
		finalEvaluator = new ComponentInstance(evaluator, evaluatorParameter, new HashMap<>());
		
		Map<String, String> classifierParameter = new HashMap<>();
		if(classifier != null) {
			// add parameter
			for (Parameter p : classifier.getParameters()) {
				classifierParameter.put(p.getName(), correctParameterSyntax( p.getDefaultValue().toString(), p.getDefaultDomain()));
			}
		}
		finalClassifier = new ComponentInstance(classifier, classifierParameter, new HashMap<>());
	}
	
	
	
	
}
