# import modules & set up logging
import gensim, logging
import os, string
logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)

class MySentences(object):
	def __init__(self, dirname):
		self.dirname = dirname

	def __iter__(self):
		for fname in os.listdir(self.dirname):
			for line in open(os.path.join(self.dirname, fname)):
				 line = "".join(l for l in line if l not in string.punctuation )
				 line = line.replace('\n',' ')
				 line = line.replace('  ',' ')
				 line = line.lower()
				 yield line.split()


if __name__ == '__main__':
	sentences1 = MySentences('/home/ccx/work/gensim-develop/sentences/es') # a memory-friendly iterator
	model1 = gensim.models.Word2Vec(sentences1)
	model1.save('/home/ccx/work/gensim-develop/mymodelES17')


	#new_model = gensim.models.Word2Vec.load('/tmp/mymodel')

	#sentences = [['first', 'sentence'], ['second', 'sentence']]
	# train word2vec on the two sentences
	#model = gensim.models.Word2Vec(sentences, min_count=1)
	'''
	model = gensim.models.Word2Vec() # an empty model, no training
	model.build_vocab(some_sentences)  # can be a non-repeatable, 1-pass generator
	model.train(other_sentences)  # can be a non-repeatable, 1-pass generator
	'''
