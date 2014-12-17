package it.uniroma1.lcl.babelnet;

import it.uniroma1.lcl.babelnet.iterators.*;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.util.ScoredItem;

import java.io.*;
import java.util.*;

import com.google.common.collect.Multimap;

import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.POS;

/**
 * A demo class to test {@link BabelNet}'s various features.
 * 
 * @author ponzetto
 */
public class BabelNetDemo
{
	/**
	 * A demo to see the senses of a word.
	 * 
	 * @param lemma
	 * @param languageToSearch
	 * @param includeRedirections
	 * @param languagesToPrint
	 * @throws IOException
	 */
	public static void testDictionary(String lemma, Language languageToSearch,
			  						  boolean includeRedirections,
			  						  Language... languagesToPrint) throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		System.out.println("SENSES FOR \"" + lemma + "\"");
		List<BabelSense> senses =
			bn.getSenses(languageToSearch, lemma, POS.NOUN, includeRedirections);
		Collections.sort(senses, new BabelSenseComparator());
		for (BabelSense sense : senses)
			System.out.println("\t=>"+sense.getSenseString());
		System.out.println();
		System.out.println("SYNSETS WITH \"" + lemma + "\"");
		List<BabelSynset> synsets =
			bn.getSynsets(languageToSearch, lemma, POS.NOUN, includeRedirections);
		Collections.sort(synsets, new BabelSynsetComparator(lemma));
		for (BabelSynset synset : synsets)
			System.out.println(
					"\t=>(" +synset.getId() + 
					") SOURCE: " + synset.getSynsetSource() +
					") TYPE: " + synset.getSynsetType() +
					"; WN SYNSET: " + synset.getWordNetOffsets() +
					"; MAIN SENSE: " + synset.getMainSense() +
					"; SENSES: "+ synset.toString(languagesToPrint));
		System.out.println();
	}

	/**
	 * A demo to see the senses of a word.
	 * 
	 * @param lemma
	 * @param languageToSearch
	 * @param includeRedirections
	 * @throws IOException
	 */
	public static void testDictionary(String lemma, Language languageToSearch,
			  						  boolean includeRedirections,
			  						  BabelSenseSource... allowedSources) throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		System.out.println("SENSES FOR \"" + lemma + "\"");
		List<BabelSense> senses =
			bn.getSenses(languageToSearch, lemma, POS.NOUN, includeRedirections,
						 allowedSources);
		Collections.sort(senses, new BabelSenseComparator());
		for (BabelSense sense : senses)
			System.out.println("\t=>"+sense.getSenseString());
		System.out.println();
		System.out.println("SYNSETS WITH \"" + lemma + "\"");
		List<BabelSynset> synsets =
			bn.getSynsets(languageToSearch, lemma, POS.NOUN,
						  includeRedirections, allowedSources);
		Collections.sort(synsets, new BabelSynsetComparator(lemma));
		for (BabelSynset synset : synsets)
			System.out.println(
					"\t=>(" +synset.getId() + 
					") SOURCE: " + synset.getSynsetSource() +
					") TYPE: " + synset.getSynsetType() +
					"; WN SYNSET: " + synset.getWordNetOffsets() +
					"; MAIN SENSE: " + synset.getMainSense() +
					"; SENSES: "+ synset.toString());
		System.out.println();
	}
	
	/**
	 * A demo to explore the BabelNet graph.
	 * 
	 * @param id
	 * @throws IOException
	 */
	public static void testGraph(String id) throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		BabelSynset synset = bn.getSynsetFromId(id);
		
		testGraph(synset);		
	}

	/**
	 * A demo to explore the BabelNet graph.
	 * 
	 * @param lemma
	 * @param language
	 * @throws IOException
	 */
	public static void testGraph(String lemma, Language language) throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		List<BabelSynset> synsets = bn.getSynsets(language, lemma);
		Collections.sort(synsets, new BabelSynsetComparator(lemma));
		
		for (BabelSynset synset : synsets) testGraph(synset);
	}
	
	/**
	 * A demo to explore the BabelNet graph.
	 * 
	 * 
	 * @param synset
	 * @throws IOException
	 */
	public static void testGraph(BabelSynset synset) throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		List<BabelNetGraphEdge> successorsEdges = bn.getSuccessorEdges(synset.getId());

		System.out.println("SYNSET ID:" + synset.getId());
		System.out.println("# OUTGOING EDGES: " + successorsEdges.size());
		
		for (BabelNetGraphEdge edge : successorsEdges)
		{
			System.out.println("\tEDGE " + edge);
			System.out.println("\t" + bn.getSynsetFromId(edge.getTarget()).toString(Language.EN));
			System.out.println();
		}
	}
	
	/**
	 * A demo to see the translations of a word.
	 * 
	 * @param lemma
	 * @param languageToSearch
	 * @param languagesToPrint
	 * @throws IOException
	 */
	public static void testTranslations(String lemma, Language languageToSearch,
			  							Language... languagesToPrint) throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		
		List<Language> allowedLanguages = Arrays.asList(languagesToPrint);
		Multimap<Language, ScoredItem<String>> translations =
			bn.getTranslations(languageToSearch, lemma);
		
		System.out.println("TRANSLATIONS FOR " + lemma);
		for (Language language : translations.keySet())
		{
			if (allowedLanguages.contains(language))
				System.out.println("\t"+language+"=>"+translations.get(language)); 
		}
	}
	
	/**
	 * A demo to see the glosses of a {@link BabelSynset} given its id.
	 * 
	 * @param id
	 * @throws IOException
	 */
	public static void testGloss(String id) throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		BabelSynset synset = bn.getSynsetFromId(id);
		
		testGloss(synset);
	}
	
	/**
	 * 
	 * A demo to see the glosses of a word in a certain language
	 * 
	 * @param lemma
	 * @param language
	 * @throws IOException
	 */
	public static void testGloss(String lemma, Language language) throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		List<BabelSynset> synsets = bn.getSynsets(language, lemma);
		Collections.sort(synsets, new BabelSynsetComparator(lemma));
		
		for (BabelSynset synset : synsets) testGloss(synset);
	}

	/**
	 * A demo to see the glosses of a {@link BabelSynset}
	 * 
	 * @param synset
	 * @throws IOException
	 */
	public static void testGloss(BabelSynset synset) throws IOException
	{
		String id = synset.getId();
		BabelNet bn = BabelNet.getInstance();
		List<BabelGloss> glosses = bn.getGlosses(id);
		
		System.out.println("GLOSSES FOR SYNSET " + synset + " -- ID: " + id);
		for (BabelGloss gloss : glosses)
		{
			System.out.println(" * "+gloss.getLanguage()+" "+gloss.getSource()+" "+
							        gloss.getSourceSense()+"\n\t"+gloss.getGloss());
		}
		System.out.println();
	}
	
	public static void testImages(String lemma, Language language) throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		System.out.println("SYNSETS WITH word: \""+ lemma + "\"");
		List<BabelSynset> synsets = bn.getSynsets(language, lemma);
		Collections.sort(synsets, new BabelSynsetComparator(lemma));
		for (BabelSynset synset : synsets)
		{
			System.out.println("  =>(" + synset.getId() + ")" +
							 "  MAIN LEMMA: " + synset.getMainSense());
			for (BabelImage img : synset.getImages())
			{
				System.out.println("\tIMAGE URL:" + img.getURL());
				System.out.println("\tIMAGE VALIDATED URL:" + img.getValidatedURL());
				System.out.println("\t==");
			}
			System.out.println("  -----");
		}
	}
	
	/**
	 * The snippet contained in our WWW-12 demo paper
	 * 
	 */
	public static void www12Test() throws IOException
	{
		BabelNet bn = BabelNet.getInstance();
		System.out.println("SYNSETS WITH English word: \"History\"");
		List<BabelSynset> synsets = bn.getSynsets(Language.EN, "History");
		Collections.sort(synsets, new BabelSynsetComparator("History"));
		for (BabelSynset synset : synsets)
		{
			System.out.print("  =>(" + synset.getId() + ") SOURCE: " + synset.getSynsetSource() +
							 "; TYPE: " + synset.getSynsetType() + 
							 "; WN SYNSET: " + synset.getWordNetOffsets() + ";\n" +
							 "  MAIN LEMMA: " + synset.getMainSense() + 
							 ";\n  IMAGES: " + synset.getImages() + 
							 ";\n  CATEGORIES: " + synset.getCategories() + 
							 ";\n  SENSES (German): { ");
			for (BabelSense sense : synset.getSenses(Language.DE))
				System.out.print(sense.toString()+" ");
			System.out.println("}\n  -----");
			Map<IPointer, List<BabelSynset>> relatedSynsets = synset.getRelatedMap(); 
			for (IPointer relationType : relatedSynsets.keySet())
			{
				List<BabelSynset> relationSynsets = relatedSynsets.get(relationType);
				for (BabelSynset relationSynset : relationSynsets)
				{
					System.out.println("    EDGE " + relationType.getSymbol() +
									   " " + relationSynset.getId() +
									   " " + relationSynset.toString(Language.EN));
				}
			}
			System.out.println("  -----");
		}
	}
	
	/**
	 * A demo to test iterators.
	 * 
	 * @param iterator
	 */
	public static <T> void testIterator(BabelIterator<T> iterator)
	{
		int counter = 0;
		while (iterator.hasNext())
		{
			System.out.println(counter+". "+iterator.next().toString());
			counter++;
		}
	}
	
	/**
	 * Just for testing
	 * 
	 * @param args
	 * 
	 */
	static public void main(String[] args)
	{
		try
		{
			// UNCOMMENT TO TEST THE WWW-12 SNIPPET
			//testHindiEngl();
			count();
					//www12Test();
			
			// UNCOMMENT TO TEST THE IMAGES
//			testImages("balloon", Language.EN);
			
			// UNCOMMENT TO TEST THE ITERATORS
//			BabelNet bn = BabelNet.getInstance();
//			testIterator(bn.getOffsetIterator());
//			testIterator(bn.getSynsetIterator());
//			testIterator(bn.getLexiconIterator());
			
			// UNCOMMENT TO TEST THE LEXICON
//			System.out.println("===============TESTING BABELNET DICT===============\n");
//			for (String test : new String[] {
//					"bank",
//					"house",
//					"car",
//					"account"
//			}) testDictionary(test, Language.EN, false, Language.IT);
//			System.out.println("=====================DONE=====================");

			// UNCOMMENT TO TEST THE GRAPH
//			System.out.println("===============TESTING BABELNET GRAPH===============\n");
//			testGraph("bank", Language.EN);
//			testGraph("bn:00000010n");
//			System.out.println("=====================DONE=====================");

			// UNCOMMENT TO TEST THE TRANSLATIONS
//			System.out.println("===============TESTING BABELNET TRANSLATIONS===============\n");
//			Set<Language> languages = BabelNetConfiguration.getInstance().getBabelLanguages();
//			languages.add(Language.EN);
//			testTranslations("apple", Language.EN, languages.toArray(new Language[languages.size()]));
//			System.out.println("=====================DONE=====================");
			
			// UNCOMMENT TO TEST THE GLOSSES
//			System.out.println("===============TESTING BABELNET GLOSSES===============\n");
//			testGloss("play", Language.EN);
//			testGloss("bn:00000010n");
//			System.out.println("=====================DONE=====================");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static void writeFile(String file, String str) throws IOException{
		BufferedWriter bw = null;
        bw = new BufferedWriter(new FileWriter(file, true));
    bw.write(str);
    bw.newLine();
    bw.flush();      
	}
	
	public static void testHindiEngl() {
		 
		File arquivo = new File("/home/ccx/babelfy/inMini.txt");
		long count=1;
		 
		try {
		 
		 
		
		//faz a leitura do arquivo
		FileReader fr = new FileReader(arquivo);
		String word,sense="";
		 
		BufferedReader br = new BufferedReader(fr);
		String espChar = "(\\.|,|-|:|\\(|\\)|ª|\\||\\\\|°)";  
		 
		while (br.ready()) {

		String line = br.readLine();
		String words[] = line.split(" ");
		int n;
		for (n=0;n<words.length;n++){
			sense = "";
			if (!words[n].matches(espChar)){
			System.out.println(words[n]);
			word = words[n];
			System.out.println("TOTAL = "+ Long.toString(count));
			sense=getSense(word, "IN");
			if (sense.length() > 2){
				System.out.println(sense);
				writeFile("/home/ccx/babelfy/inMinisense.txt", ">>> "+word);
				writeFile("/home/ccx/babelfy/inMinisense.txt", sense);
				count++;
			}
			}
/*			if (words[n].matches("[a-zA-Z]*")){
				if (words[n].endsWith(".")){
					System.out.println(words[n].substring(0,words[n].length()-2));
					word = words[n].substring(0,words[n].length()-2);
				
				}else{
					System.out.println(words[n]);
					word = words[n];
				}	
				System.out.println("TOTAL = "+ Long.toString(count));
				System.out.println(sense);
				sense=getSense(word, "EN");
				writeFile("/home/ccx/babelfy/ENsense.txt", "===> "+word);
				writeFile("/home/ccx/babelfy/ENsense.txt", sense);
				count++;

			}*/				
		}
		 
		}
		 
		writeFile("/home/ccx/babelfy/inMinisense.txt", "TOTAL = "+ Long.toString(count));
		br.close();
		fr.close();

		} catch (IOException ex) {
		ex.printStackTrace();
		}
		 
		}
	
	public static void count() {
		 
		File arquivo = new File("/home/ccx/babelfy/enMinisense.txt");
		long count=0;
		String lineAnt="";
		ArrayList<String> words = new ArrayList<String> ();
         
		try {
		 
		 
			BufferedWriter bw = null;
	        bw = new BufferedWriter(new FileWriter("/home/ccx/babelfy/enMinisenseCount.txt", true));
		
		//faz a leitura do arquivo
		FileReader fr = new FileReader(arquivo);
		String word,sense="";
		 
		BufferedReader br = new BufferedReader(fr);
		 
		while (br.ready()) {

			String line = br.readLine().toLowerCase();
			//System.out.println(line);
	
			if (line.startsWith("  =>(")){
				if(!words.contains(lineAnt)){
					words.add(lineAnt);
					count = count + 1;
					System.out.println(lineAnt.replace(">>> ", "") + "\t" +line+"\t"+ Long.toString(count));
				    bw.write(lineAnt + "\t" + Long.toString(count));
				    bw.newLine();
				    bw.flush();      
					
				}

			}
			lineAnt = line;
		}


		bw.close();		 
		br.close();
		fr.close();

		} catch (IOException ex) {
		ex.printStackTrace();
		}
		 
		}

	public static String getSense(String word, String lang) throws IOException
	{
		String ret="";
		try {
		BabelNet bn = BabelNet.getInstance();

		List<BabelSynset> synsets;
		if (lang == "EN"){
			synsets = bn.getSynsets(Language.EN, word);
		}else{
			synsets = bn.getSynsets(Language.ID, word);
		}
		
		//Collections.sort(synsets, new BabelSynsetComparator(word));
		for (BabelSynset synset : synsets)
		{
			ret = ("  =>(" + synset.getId() + ") SOURCE: " + synset.getSynsetSource() +
							 "; TYPE: " + synset.getSynsetType() + 
							 "; WN SYNSET: " + synset.getWordNetOffsets() + ";\n" +
							 "  MAIN LEMMA: " + synset.getMainSense()) ;
			ret+=("}\n  -----");
			/*Map<IPointer, List<BabelSynset>> relatedSynsets = synset.getRelatedMap(); 
			for (IPointer relationType : relatedSynsets.keySet())
			{
				List<BabelSynset> relationSynsets = relatedSynsets.get(relationType);
				for (BabelSynset relationSynset : relationSynsets)
				{
					ret+=("    EDGE " + relationType.getSymbol() +
									   " " + relationSynset.getId() +
									   " " + relationSynset.toString(Language.EN));
				}
			}*/
			ret+=("  -----");
		}
		} catch (IOException ex) {
		ret=ex.toString();
				ex.printStackTrace();
		}
		return(ret);
	}
	
	public static void unCorpusConv() {
		 
		File arquivo = new File("/home/ccx/Documents/meedan/uncorpora_plain_20090831.tmx");
		long count=0;
		String lineAnt=""; 
		try {
		 
		 
			BufferedWriter enFile = null;
	        enFile = new BufferedWriter(new FileWriter("/home/ccx/babelfy/unCorpusEN.txt", true));
			BufferedWriter arFile = null;
	        arFile = new BufferedWriter(new FileWriter("/home/ccx/babelfy/unCorpusAR.txt", true));
		
		//faz a leitura do arquivo
		FileReader fr = new FileReader(arquivo);
		String word,sense="";
		 
		BufferedReader br = new BufferedReader(fr);
		 
		while (br.ready()) {

			String line = br.readLine();
			//System.out.println(line);
	
			if (lineAnt.endsWith("lang=\"EN\">")){
				line = line.replace("        <seg>", "");
				line = line.replace("</seg>", "");
				System.out.println(line+"\t");
			    enFile.write(line);
			    enFile.newLine();
			    enFile.flush();      
			}else{
				if (lineAnt.endsWith("lang=\"AR\">")){
					line = line.replace("        <seg>", "");
					line = line.replace("</seg>", "");
					System.out.println(line+"\t");
				    arFile.write(line);
				    arFile.newLine();
				    arFile.flush();      
				}
			}
			lineAnt = line;
		}


		arFile.close();		 
		enFile.close();		 
		br.close();
		fr.close();

		} catch (IOException ex) {
		ex.printStackTrace();
		}
		 
		}

}
