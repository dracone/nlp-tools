time ./word2vec -train /home/ccx/corpora/txt/esSmall/ESsmall.txt -save-vocab ./esSmallVocab.txt -output ./esSmallVector.bin -cbow 1 -size 200 -window 8 -negative 25 -hs 0 -sample 1e-4 -threads 20 -binary 1 -iter 15

time ./word2vec -train /home/ccx/corpora/txt/enSmall/ENsmall.txt -save-vocab ./enSmallVocab.txt -output ./enSmallVector.bin -cbow 1 -size 200 -window 8 -negative 25 -hs 0 -sample 1e-4 -threads 20 -binary 1 -iter 15

./distanceCLA enSmallVector.bin enSmallMatrix.txt enSmallVocab.txt

./distanceCLA esSmallVector.bin esSmallMatrix.txt esSmallVocab.txt

# enSmallVocab.txt needs to finish with EXIT <enter>

