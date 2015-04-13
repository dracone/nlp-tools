# -*- coding: utf-8 -*- 
# Join the TXT files in a folder in a unique file in the input format to Word2Vec
import gensim, logging
import os, string
logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)

if __name__ == '__main__':
	#new file
	name = 'ESsmall.txt' #output file
	f = open('/home/ccx/corpora/txt/esSmall/'+name, 'w')
		
	for fname in os.listdir('/home/ccx/corpora/txt/esSmall/'): #input folder
	  if fname != name:
		print fname
		for line in open(os.path.join('/home/ccx/corpora/txt/esSmall/', fname)):
			 line = line.replace('\n',' ')
			 line = line.replace('<P>','')
			 line = line.replace(':',' ')
			 line = line.replace('-',' ')
			 line = line.replace('.',' ')
			 line = line.replace(';',' ')
			 line = line.replace('!',' ')
			 line = line.replace(',',' ')
			 line = line.replace('¿',' ')
			 line = line.replace('?',' ')
			 line = line.replace('_',' ')
			 line = line.replace('+',' ')
			 line = line.replace('%',' ')
			 line = line.replace('$',' ')
			 line = line.replace('#',' ')
			 line = line.replace(']',' ')
			 line = line.replace('[',' ')
			 line = line.replace('}',' ')
			 line = line.replace('{',' ')
			 line = line.replace('*',' ')
			 line = line.replace('&',' ')
			 line = line.replace('@',' ')
			 line = line.replace('/',' ')
			 line = line.replace('(',' ')
			 line = line.replace(')',' ')
			 line = line.replace('<',' ')
			 line = line.replace('>',' ')
			 line = line.replace('\\',' ')
			 line = line.replace('  ',' ')
			 line = line.lower()
		         f.write(line)
	f.close()
