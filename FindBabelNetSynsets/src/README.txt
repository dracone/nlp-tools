====================================================================
                    Babelfy RESTful API v0.9
                     Written by Andrea Moro
                     
                       http://babelfy.org
====================================================================

This is the README file for using the Babelfy RESTful API, a unified 
graph-based approach for multilingual Word Sense Disambiguation and 
Entity Linking. 

INSTALLATION AND EXAMPLE
------------------------

To use this API you need to download the following files:

mkdir babelfy
cd babelfy
wget http://babelfy.org/data/BabelfyAPI-0.9.zip
wget http://babelnet.org/data/2.5/babelnet-2.5-index-bundle.tar.bz2

Alternatively, you can download single-license indices, according to your needs:
    
http://babelnet.org/data/2.5/babelnet-2.5-core-index.tar.bz2 (mandatory)
http://babelnet.org/data/2.5/babelnet-2.5-CC-BY-30-index.tar.bz2 (optional)
http://babelnet.org/data/2.5/babelnet-2.5-CC-BY-SA-30-index.tar.bz2 (optional)
http://babelnet.org/data/2.5/babelnet-2.5-CC-BY-NC-SA-30-index.tar.bz2 (optional)
http://babelnet.org/data/2.5/babelnet-2.5-APACHE-20-index.tar.bz2 (optional)
http://babelnet.org/data/2.5/babelnet-2.5-CECILL-C-index.tar.bz2 (optional)

Then extract them with the following commands:

unzip BabelfyAPI-0.9.zip
tar -jvxf babelnet-2.5-index-bundle.tar.bz2

At this point you can compile and run our example code as follows:

javac -classpath BabelfyAPI-0.9/\* Example.java
java -classpath .:BabelfyAPI-0.9/\* Example

An example code follows:

import it.uniroma1.lcl.babelfy.Babelfy;
import it.uniroma1.lcl.babelfy.Babelfy.Matching;
import it.uniroma1.lcl.babelfy.Babelfy.AccessType;
import it.uniroma1.lcl.babelfy.data.Annotation;
import it.uniroma1.lcl.babelfy.data.BabelSynsetAnchor;
import it.uniroma1.lcl.jlt.util.Language;

import java.io.IOException;

public class Example {
	public static void main(String[] args) throws Exception {
		// get an instance of the Babelfy RESTful API manager
		Babelfy bfy = Babelfy.getInstance(AccessType.ONLINE);
		// the string to be disambiguated
		String inputText = "hello world, I'm a computer scientist";
		// the actual disambiguation call
		Annotation annotations = bfy.babelfy("", inputText,
			Matching.EXACT, Language.EN);
		// printing the result
		System.out.println("inputText: "+inputText+"\nannotations:");
		for(BabelSynsetAnchor annotation : annotations.getAnnotations())
			System.out.println(annotation.getAnchorText()+"\t"+
				annotation.getBabelSynset().getId()+"\t"+
				annotation.getBabelSynset());
	}
}

Annotation babelfy(String key,
	String inputText, Matching candidateSelectionMode, Language language);

The first parameter is the access key (you can request a less restrictive 
key from us). A random or empty key will grant 100 requests per day.
The second parameter is a string representing the input text (you can use
sentences or whole documents, the maximum number of characters being 3500).
The third parameter is an enum with two possible values EXACT or PARTIAL
to respectively enable exact or partial matching of the candidates for
fragments of text in the input text.
The fourth parameter is the language of the input text (there are 50
languages denoted with their ISO 639-1 uppercase code).

Annotation contains the output of our system. You can access the POS-tagged 
input text with getText() which will return a list of WordLemmaTag
objects with the respective getters. With getAnnotations() you will get
a list of BabelSynsetAnchor, i.e., the actual annotations. You can use
getAnchorText() to get the disambiguated fragment of text and 
getBabelSynset() to get the selected Babel synset. Finally, you can get
the start and indices of the input text with getStart() and getEnd().


REFERENCES
------------------------

When citing Babelfy or its API, please refer to the following paper:

Andrea Moro, Alessandro Raganato and Roberto Navigli. Entity Linking 
meets Word Sense Disambiguation: A Unified Approach. Transactions 
of the Association for Computational Linguistics (TACL), 2, 2014.


COPYRIGHT
---------

Babelfy and the Babelfy API are licensed under a Creative Commons 
Attribution-Noncommercial-Share Alike 3.0 License. 
See the file LICENSE.txt for details.


ACKNOWLEDGMENTS
---------------

Babelfy and the Babelfy API are an output of the ERC Starting Grant 
MultiJEDI No. 259234.
