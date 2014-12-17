import it.uniroma1.lcl.babelfy.Babelfy;
import it.uniroma1.lcl.babelfy.Babelfy.Matching;
import it.uniroma1.lcl.babelfy.Babelfy.AccessType;
import it.uniroma1.lcl.babelfy.data.Annotation;
import it.uniroma1.lcl.babelfy.data.BabelSynsetAnchor;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.wordnet.WordNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelGloss;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.iterators.*;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.util.ScoredItem;
import java.io.IOException;
import java.util.*;
import com.google.common.collect.Multimap;
import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.POS;

import java.io.IOException;

public class ExampleCAIO {

	public static void main(String[] args) throws Exception {
		Babelfy bfy = Babelfy.getInstance(AccessType.ONLINE);
		String inputText = "Thanks my fans"; //args[0];
		System.out.println("Input: " + inputText);
    BabelNet bn = BabelNet.getInstance();
    Language source;

      source = Language.EN;
/*    if (args[1].equals("en")) {
      source = Language.EN;
    } else if (args[1].equals("ar")) {
      source = Language.AR;
    } else if (args[1].equals("pt")) {
      source = Language.PT;
    } else if (args[1].equals("es")) {
      source = Language.ES;
    } else {
      throw new Exception();
    }
  */

   
  
    int limit = 1; //Integer.parseInt(args[2]);
    
    System.out.println("Source language is " + source.toString());
		
    Annotation annotations = bfy.babelfy("", inputText, Matching.EXACT, source);

    Set<Language> languages = new HashSet<Language>();
    languages.add(Language.ES);
    languages.add(Language.PT);
    languages.add(Language.EN);
    languages.add(Language.AR);
    languages.add(Language.HI);
    List<Language> allowedLanguages = Arrays.asList(languages.toArray(new Language[languages.size()]));
		
    for (BabelSynsetAnchor annotation : annotations.getAnnotations()) {
      // System.out.println("TERM ---------------------------------------------");
      // System.out.println(annotation.getAnchorText() + "\t" + annotation.getBabelSynset().getId() + "\t" + annotation.getBabelSynset());

      // System.out.println("TRANSLATIONS -------------------------------------");
      // Multimap<Language, ScoredItem<String>> translations = bn.getTranslations(source, annotation.getAnchorText());
      // for (Language language : translations.keySet()) {
      //   if (allowedLanguages.contains(language)) {
      //     System.out.print(""+language);
      //     for (ScoredItem<String> item : translations.get(language)) {
      //       System.out.print("\t"+item);
      //     }
      //     System.out.println();
      //   }
      // }

      System.out.println("TERM ---------------------------------------------\n");
      System.out.println(annotation.getAnchorText() + "\n");
      
      System.out.println("SENSES -------------------------------------------\n");
      for (Language l : allowedLanguages) {
        System.out.println("Language: " + l.toString() + "\n");
        List<BabelSense> senses = annotation.getBabelSynset().getSenses(l);
        int count = 0;
        for (BabelSense sense : senses) {
          if (count < limit) {
            System.out.println("  " + sense.getLemma() + "\n");
          }
          count++;
        }
      }

      System.out.println("GLOSSES ------------------------------------------\n");
      for (Language l : allowedLanguages) {
        System.out.println("Language: " + l.toString() + "\n");
        List<BabelGloss> glosses = annotation.getBabelSynset().getGlosses(l);
        int count = 0;
        for (BabelGloss gloss : glosses) {
          if (count < limit) {
            System.out.println("  " + gloss.getGloss() + "\n");
          }
          count++;
        }
      }

      System.out.println("######################################################################\n");

      // System.out.println("TRANSLATIONS FROM SYNSETS ------------------------");
      // Multimap<BabelSense, BabelSense> t = annotation.getBabelSynset().getTranslations();
      // for (BabelSense sense1 : t.keySet()) {
      //   if (allowedLanguages.contains(sense1.getLanguage())) {
      //     Collection<BabelSense> senses2 = t.get(sense1);
      //     System.out.println(sense1.getLemma());
      //     for (BabelSense sense2 : senses2) {
      //       if (allowedLanguages.contains(sense2.getLanguage())) {
      //         System.out.println("   " + sense2.getSenseString());
      //       }
      //     }
      //   }
      // }
    }
	}
}
