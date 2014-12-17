import it.uniroma1.lcl.babelfy.Babelfy;
import it.uniroma1.lcl.babelfy.Babelfy.Matching;
import it.uniroma1.lcl.babelfy.Babelfy.AccessType;
import it.uniroma1.lcl.babelfy.data.Annotation;
import it.uniroma1.lcl.babelfy.data.BabelSynsetAnchor;
import it.uniroma1.lcl.jlt.util.Language;

import java.io.IOException;

public class Example {
	public static void main(String[] args) throws Exception {
		Babelfy bfy = Babelfy.getInstance(AccessType.ONLINE);
		String inputText = "hello world, I'm a computer scientist";
		Annotation annotations = bfy.babelfy("", inputText,
			Matching.EXACT, Language.EN);
		System.out.println("inputText: "+inputText+"\nannotations:");
		for(BabelSynsetAnchor annotation : annotations.getAnnotations())
			System.out.println(annotation.getAnchorText()+"\t"+
				annotation.getBabelSynset().getId()+"\t"+
				annotation.getBabelSynset());
	}
}
