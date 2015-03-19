# import modules & set up logging
import gensim

if __name__ == '__main__':
	#sentences = MySentences('/home/ccx/work/gensim-develop/sentences/') # a memory-friendly iterator
	#model = gensim.models.Word2Vec(sentences)
	#model.save('/home/ccx/work/gensim-develop/mymodel')
	model = gensim.models.Word2Vec.load('/home/ccx/work/gensim-develop/mymodel')
	#model = gensim.models.Word2Vec.load('/home/ccx/work/gensim-develop/mymodelPEQ')
	#print model.most_similar(positive=['woman', 'king'], negative=['man'], topn=1)
	print model.similarity('woman', 'man')
	print model['chapter']	
	print model



