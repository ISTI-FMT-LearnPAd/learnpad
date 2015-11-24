package eu.learnpad.ca.analysis.contentclarity.plugin;

import eu.learnpad.ca.analysis.Plugin;
import eu.learnpad.ca.gate.GateThread;
import eu.learnpad.ca.rest.data.Annotation;
import eu.learnpad.ca.rest.data.Node;
import gate.DocumentContent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.languagetool.Language;

public class ActorUnclear extends Plugin {
	
	protected static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ActorUnclear.class);
	
	public ActorUnclear(Language lang,  DocumentContent docContent, List<Node> listnode){
		this.language=lang;
		this.docContent = docContent;
		this.listnode = listnode;
	}
	

	public void check(GateThread gateu, List<Annotation> listannotations, Set<gate.Annotation> listSentenceDefected, Set<gate.Annotation> listSentence){
		
		HashSet<String> hs = new HashSet<String>();
		hs.add("PassiveVoice");
		Set<gate.Annotation> SetActorUnclear = gateu.getAnnotationSet(hs);
		
		
		String rac = "The sentence does not specify the subject. Please include who is performing the action.";
		
		String type = "Actor Unclear";
		gatevsleanpadAnnotation(SetActorUnclear, listannotations,listSentenceDefected,listnode,docContent,type ,rac,log,listSentence );
	}

}
