make
if [ ! -e text8 ]; then
  wget http://mattmahoney.net/dc/text8.zip -O text8.gz
  gzip -d text8.gz -f
fi
time ./word2vec -train text8 -output vectorsTST.bin -cbow 1 -size 200 -window 8 -negative 25 -hs 0 -sample 1e-4 -threads 20 -binary 1 -iter 15
./distance vectors.bin


time ./word2vec -train PT.txt -output vectorsPT.bin -cbow 1 -size 200 -window 8 -negative 25 -hs 0 -sample 1e-4 -threads 20 -binary 1 -iter 15
./distance vectorsPT.bin

./distanceCLA vectorsES17.bin


time ./word2vec -train /home/ccx/work/nlp-tools/gensim-develop/sentences/es/ep-00-01-17.txt -save-vocab ./vectorsES17vocab.txt -output ./vectorsES17.bin -cbow 1 -size 200 -window 8 -negative 25 -hs 0 -sample 1e-4 -threads 20 -binary 1 -iter 15
./distance vectors.bin


time ./word2vec -train /home/ccx/corpora/txt/esSmall/files.txt -output esSmall.bin -cbow 1 -size 200 -window 8 -negative 25 -hs 0 -sample 1e-4 -threads 20 -binary 1 -iter 15
./distance vectorsPT.bin


time ./word2vec -train /home/ccx/corpora/txt/esSmall/ESsmall.txt -save-vocab ./esSmall.txt -output ./esSmall.bin -cbow 1 -size 200 -window 8 -negative 25 -hs 0 -sample 1e-4 -threads 20 -binary 1 -iter 15

time ./word2vec -train /home/ccx/corpora/txt/enSmall/files.txt -save-vocab ./enSmall.txt -output ./enSmall.bin -cbow 1 -size 200 -window 8 -negative 25 -hs 0 -sample 1e-4 -threads 20 -binary 1 -iter 15

./distanceCLA enSmall.bin enSmallOUTVocab.txt enSmallVocab.txt
 
./distanceCLA vectorsES17.bin wordsES17Matrix.txt wordsES17.txt
./distanceCLA vectorsEN17.bin wordsEN17Matrix.txt wordsEN17.txt

time ./word2vec -train text8 -output vectorsTST.bin -cbow 1 -size 200 -window 8 -negative 25 -hs 0 -sample 1e-4 -threads 20 -binary 1 -iter 15

