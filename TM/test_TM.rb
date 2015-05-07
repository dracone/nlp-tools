# encoding: utf-8

require File.join(File.expand_path(File.dirname(__FILE__)), '..', 'test_helper')
load File.join(File.expand_path(File.dirname(__FILE__)), '..', '..', 'lib', 'glossary.rb')
require 'glossary'

class TMTest < ActiveSupport::TestCase

  def setup
    super
    Glossary.create_index
    @glossary = Glossary.new
    @langmap = { "en" => "en_US", "es" => "es_LA", "pt" => "pt_BR", "ar" => "ar_AR" }
  end

  def teardown
    Glossary.delete_index
    FacebookPage.destroy_all
    WebMock.reset!
    WebMock.allow_net_connect!
  end

 
  test "TM test" do
    @txt = "the book is on the table"
    create_glossary_data
    #terms = @glossary.get_terms text: "Alo galera do Santos da Vila! Muito orgulho por ter jogado ai! Aquele abraco!",
    #                            lang: "pt", page: "148456285190063", offset: 0, post: 'neymar'
    terms = @glossary.get_terms text: @txt,
                                lang: "en", page: "148456285190063", offset: 0, post: 'neymar'

    #print terms.select!{ |t| t['data-source'] == 'glossary' }

    print "Source text = "	
    puts @txt + " -> "
    terms.each do |term|
	#puts term
	print term["lang"] + " - "+ term["term"] + " / "
	puts term["targets"][0][:lang] + " - "+ term["targets"][0][:term]
    end

    #puts terms.collect{ |t| t['term'] } + terms.collect{ |t| t['targets'] }.flatten.collect{ |t| t[:term] }.sort

    #puts 'term = '
    #puts terms.select!{ |t| t['data-source'] == 'glossary' }

    #puts 'term = '
    #puts terms.select!{ |t| t['data-source'] == 'glossary' }.collect!{ |t| t['term','definition'] }
    #puts 'definition = '
    #puts terms.select!{ |t| t['data-source'] == 'glossary' }.collect!{ |t| t['definition'] }

    #assert_equal ['Alo', 'Santos da Vila', 'galera'], terms
    #assert_equal ['bla bla bla which happened ten years before bla bla bla '], terms
  end

  private

  def create_glossary_data
    # Neymar, Steve Harris, Messi
    n, s, m = '148456285190063', '112894365391132', '176063032413299'
    create_facebook_page title: n
    create_facebook_page title: s
    create_facebook_page title: m
    create_post fbid: 'neymar'
    create_post fbid: 'steve'
    create_post fbid: 'messi'
    
    #which happened ten years before ; que ocurrió diez años antes
    # Some terms for Neymar's glossary: "Alo galera do Santos da Vila! Muito orgulho por ter jogado ai! Aquele abraco!"

    [
      [ 'which happened ten years before', 'que ocurrió diez años antes','', ''],
      ['on the table','sobre la mesa','',''],
      ['book', 'libro', '','']
      #['Santos da Vila', 'Saints of Village', 'Santos de la Villa', 'Time brasileiro onde Neymar iniciou a carreira'],
      #['Alo', 'Hey', 'Hola', 'Saudacao muito utilizada no Brasil'],
      #['galera', 'folks', 'personas', 'Forma coloquial de se referir a um grupo de pessoas']
    ].each do |t|
      @glossary.add_term page: n, src_lang: 'en', src_term: t[0], dst_lang: 'es', dst_term: t[1], definition: t[3], data_source: 'glossary'
      #@glossary.add_term page: n, src_lang: 'en', src_term: t[0], dst_lang: 'es', dst_term: t[2], definition: t[3], data_source: 'glossary'
    end

    # Some terms for Steve Harris' glossary: "Scream for me, Brazil! It's Rock In Rio again! We played on 2014 but we'll be there on 2015!"

    [
      ['Rock In Rio', 'Rock no Rio', 'Rock en Rio', 'Very famous music festival in Brazil'],
      ['Scream for me', 'Gritem para mim', 'Grita para mi', 'That is something asked by the vocalist on almost all Iron Maiden concerts']
    ].each do |t|
      @glossary.add_term page: s, src_lang: 'en', src_term: t[0], dst_lang: 'pt', dst_term: t[1], definition: t[3], data_source: 'glossary'
      @glossary.add_term page: s, src_lang: 'en', src_term: t[0], dst_lang: 'es', dst_term: t[2], definition: t[3], data_source: 'glossary'
    end

    # Some terms for Messi's glossary: "Maradona fue el mejor de todos los Argentinos! Mismo utilizando la mano de Dios."

    [
      ['Maradona', 'Maradona', 'Maradona', 'Famoso jugador Argentino de fútbol'],
      ['mano de Dios', 'hand of God', 'mao de Deus', 'Gol echo por Maradona en la copa del mundo, utilizando su mano']
    ].each do |t|
      @glossary.add_term page: m, src_lang: 'es', src_term: t[0], dst_lang: 'en', dst_term: t[1], definition: t[3], data_source: 'glossary'
      @glossary.add_term page: m, src_lang: 'es', src_term: t[0], dst_lang: 'pt', dst_term: t[2], definition: t[3], data_source: 'glossary'
    end

    # Some terms for the dictionary in Portuguese

    [
      ['orgulho', 'proud', 'orgullo', 'Se sentir bem com relacao a alguma coisa'],
      ['jogar', 'play', 'jugar', 'Participar de uma partida de algum esporte'],
      ['abraco', 'hug', 'abrazo', 'Saudacao que envolve juntar os dois bracos por trás de outra pessoa'],
      ['menina', 'girl', 'niña', 'Crianca do sexo feminino'],
      ['Santos', 'Saints', 'Santos', 'Plural de santo, que e uma divindade que fez milagres']
    ].each do |t|
      @glossary.add_term src_lang: 'pt', src_term: t[0], dst_lang: 'en', dst_term: t[1], definition: t[3], data_source: 'dictionary', post: 'neymar'
      @glossary.add_term src_lang: 'pt', src_term: t[0], dst_lang: 'es', dst_term: t[2], definition: t[3], data_source: 'dictionary', post: 'neymar'
    end

    # Some terms for the dictionary in Spanish

    [
      ['mejor', 'best', 'melhor', ''],
      ['ser', 'to be', 'ser', ''],
      ['utilizar', 'to use', 'usar', ''],
      ['partido', 'match', 'partida', ''],
      ['pelotas', 'balls', 'bolas', ''],
      ['niña', 'girl', 'menina', ''],
      ['jugar', 'play', 'jogar', ''],
      ['todos', 'all', 'todos', '']
    ].each do |t|
      @glossary.add_term src_lang: 'es', src_term: t[0], dst_lang: 'en', dst_term: t[1], definition: t[3], data_source: 'dictionary', post: 'messi'
      @glossary.add_term src_lang: 'es', src_term: t[0], dst_lang: 'pt', dst_term: t[2], definition: t[3], data_source: 'dictionary', post: 'messi'
    end

    # Some terms for the dictionary in English

    [
      ['play', 'tocar', 'tocar', ''],
      ['we', 'nos', 'nosotros', ''],
      ['maiden', 'donzela', 'de soltera', ''],
      ['lights', 'luzes', 'luces', ''],
      ['there', 'lá', 'por ahi', '']
    ].each do |t|
      @glossary.add_term src_lang: 'en', src_term: t[0], dst_lang: 'pt', dst_term: t[1], definition: t[3], data_source: 'dictionary', post: 'steve'
      @glossary.add_term src_lang: 'en', src_term: t[0], dst_lang: 'es', dst_term: t[2], definition: t[3], data_source: 'dictionary', post: 'steve'
    end
  end




end
