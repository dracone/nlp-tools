import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.jlt.util.Language;
import java.io.File;
import java.io.*;
import java.util.*;


//javac -classpath FindBabelNetSinsets.java
//javac -classpath bin:lib/*:config FindBabelNetSinsets.java
//java -classpath bin:lib/*:config  FindBabelNetSinsets ./enMini.txt ./enMiniOUT.txt EN
public class FindBabelNetSinsets {
	
	  public static void main(String[] args) {
	        if (args.length == 3){
	        	testLang(args[0], "./tmp.txt", args[2]);
	        	count ("./tmp.txt",args[1]);
	        	
	        	try{
	        		 
	        		File file = new File("./tmp.txt");
	     
	        		if(file.delete()){
	        			System.out.println(file.getName() + " is deleted!");
	        		}else{
	        			System.out.println("Delete operation is failed.");
	        		}
	     
	        	}catch(Exception e){
	     
	        		e.printStackTrace();
	     
	        	}	        
	        }else{
		        System.out.println("Use: java FindBabelNetSinsets.java InputFile OutputFile LANG");
		        System.out.println("Example: java FindBabelNetSinsets En.txt EnOut.txt EN");
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

	  public static void testLang(String fileIN, String fileOUT, String lang) {
			 
			File arquivo = new File(fileIN);
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
						sense=getSense(word, lang);
						if (sense.length() > 2){
							System.out.println(sense);
							writeFile(fileOUT, ">>> "+word);
							writeFile(fileOUT, sense);
							count++;
						}
					}
				
				}
			 
			}
			 
			writeFile(fileOUT, "TOTAL = "+ Long.toString(count));
			br.close();
			fr.close();

			} catch (IOException ex) {
			ex.printStackTrace();
			}
			 
			}
		
		public static void count(String fileIN, String fileOUT) {
			 
			File arquivo = new File(fileIN);
			long count=0;
			String lineAnt="";
			ArrayList<String> words = new ArrayList<String> ();
	         
			try {
			 
			 
				BufferedWriter bw = null;
		        bw = new BufferedWriter(new FileWriter(fileOUT, true));
			
			//faz a leitura do arquivo
			FileReader fr = new FileReader(arquivo);
			
			 
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
	
				List<BabelSynset> synsets = null;
				
				switch (lang) {					
				case "EN": synsets = bn.getSynsets(Language.EN, word); break;
				case "FR": synsets = bn.getSynsets(Language.FR, word); break;
				case "DE": synsets = bn.getSynsets(Language.DE, word); break;
				case "ES": synsets = bn.getSynsets(Language.ES, word); break;
				case "IT": synsets = bn.getSynsets(Language.IT, word); break;
				case "SV": synsets = bn.getSynsets(Language.SV, word); break;
				case "NL": synsets = bn.getSynsets(Language.NL, word); break;
				case "RU": synsets = bn.getSynsets(Language.RU, word); break;
				case "FA": synsets = bn.getSynsets(Language.FA, word); break;
				case "PT": synsets = bn.getSynsets(Language.PT, word); break;
				case "JA": synsets = bn.getSynsets(Language.JA, word); break;
				case "VI": synsets = bn.getSynsets(Language.VI, word); break;
				case "PL": synsets = bn.getSynsets(Language.PL, word); break;
				case "ZH": synsets = bn.getSynsets(Language.ZH, word); break;
				case "CA": synsets = bn.getSynsets(Language.CA, word); break;
				case "UK": synsets = bn.getSynsets(Language.UK, word); break;
				case "FI": synsets = bn.getSynsets(Language.FI, word); break;
				case "SR": synsets = bn.getSynsets(Language.SR, word); break;
				case "ID": synsets = bn.getSynsets(Language.ID, word); break;
				case "NO": synsets = bn.getSynsets(Language.NO, word); break;
				case "AR": synsets = bn.getSynsets(Language.AR, word); break;
				case "RO": synsets = bn.getSynsets(Language.RO, word); break;
				case "CS": synsets = bn.getSynsets(Language.CS, word); break;
				case "DA": synsets = bn.getSynsets(Language.DA, word); break;
				case "HU": synsets = bn.getSynsets(Language.HU, word); break;
				case "TR": synsets = bn.getSynsets(Language.TR, word); break;
				case "KO": synsets = bn.getSynsets(Language.KO, word); break;
				case "MS": synsets = bn.getSynsets(Language.MS, word); break;
				case "LA": synsets = bn.getSynsets(Language.LA, word); break;
				case "ET": synsets = bn.getSynsets(Language.ET, word); break;
				case "HE": synsets = bn.getSynsets(Language.HE, word); break;
				case "LT": synsets = bn.getSynsets(Language.LT, word); break;
				case "SK": synsets = bn.getSynsets(Language.SK, word); break;
				case "BG": synsets = bn.getSynsets(Language.BG, word); break;
				case "SL": synsets = bn.getSynsets(Language.SL, word); break;
				case "HR": synsets = bn.getSynsets(Language.HR, word); break;
				case "TL": synsets = bn.getSynsets(Language.TL, word); break;
				case "EL": synsets = bn.getSynsets(Language.EL, word); break;
				case "HI": synsets = bn.getSynsets(Language.HI, word); break;
				case "LV": synsets = bn.getSynsets(Language.LV, word); break;
				case "CY": synsets = bn.getSynsets(Language.CY, word); break;
				case "IS": synsets = bn.getSynsets(Language.IS, word); break;
				case "AF": synsets = bn.getSynsets(Language.AF, word); break;
				case "EO": synsets = bn.getSynsets(Language.EO, word); break;
				case "SQ": synsets = bn.getSynsets(Language.SQ, word); break;
				case "GA": synsets = bn.getSynsets(Language.GA, word); break;
				case "SW": synsets = bn.getSynsets(Language.SW, word); break;
				case "EU": synsets = bn.getSynsets(Language.EU, word); break;
				case "MT": synsets = bn.getSynsets(Language.MT, word); break;
				case "GL": synsets = bn.getSynsets(Language.GL, word); break;
				default: System.out.println("Unknown language"); break;
			}					
	
				
				//Collections.sort(synsets, new BabelSynsetComparator(word));
				for (BabelSynset synset : synsets)
				{
					ret = ("  =>(" + synset.getId() + ") SOURCE: " + synset.getSynsetSource() +
									 "; TYPE: " + synset.getSynsetType() + 
									 "; WN SYNSET: " + synset.getWordNetOffsets() + ";\n" +
									 "  MAIN LEMMA: " + synset.getMainSense()) ;
					ret+=("}\n  -----");
			}
			} catch (IOException ex) {
				ret=ex.toString();
					ex.printStackTrace();
			}
			return(ret);
		}
		
		

}
