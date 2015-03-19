# import modules & set up logging
import gensim, logging
import os
import string



if __name__ == '__main__':
	'''
	dirname = "/home/ccx/work/gensim-develop/sentences/peq"

	f = open('words.txt', 'w')
	words = []
	for fname in os.listdir(dirname):
		for line in open(os.path.join(dirname, fname)):
			 print line
			 line = "".join(l for l in line if l not in string.punctuation )
			 line = line.replace('\n',' ')
			 line = line.replace('  ',' ')
			 line = line.lower()
			 print line
			 f.write(line)
			 for w in line.split():
			 	words.append(w)

	f.close()
	'''
	#print words
	f = open('wordsES17.csv', 'w')
	print "cccccccccccccccccccccc"

	model = gensim.models.Word2Vec.load('/home/ccx/work/gensim-develop/mymodelES17')

	print "xxxxxxxxxxxxxxxxxxxxxxxx"

        f.write(';')
	for w in model.vocab:
  	        f.write(w+';')

        f.write('\n')

	for w in model.vocab:
  	        f.write(w+';')
		for w2 in model.vocab:
			print w,w2
			print model.similarity(w, w2)
	  	        f.write(str(model.similarity(w, w2))+';')
	        f.write('\n')

	f.close()
        '''
	#model1.save('/home/ccx/work/gensim-develop/testeEN')

	#sentences1 = MySentences('/home/ccx/corpora/txt/es/') # a memory-friendly iterator
	sentences1.__iter__
	attr = dir(sentences1)
	for at in attr:
		print getattr(sentences1, at)
	#f = open('workfile.txt', 'r+')
	
	#f.write(sentences1)
	

	#sentences = [['first', 'sentence'], ['second', 'sentence']]
	# train word2vec on the two sentences
	#model = gensim.models.Word2Vec(sentences, min_count=1)
	'''
