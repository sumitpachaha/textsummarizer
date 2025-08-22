package summarizer;
import java.util.*;
import java.lang.*;



public class GenerateSummary {

    private String fullText;
    private HashMap<String, Double> wordFrequency = new HashMap<>();
    private HashMap<Integer, Double> sentenceScores = new HashMap<>();
    private HashMap<String, Double> tf_score = new HashMap<>();
    private HashMap<String, Double> idf_score = new HashMap<>();
    private String[] words;
    private ArrayList<String> sentences = new ArrayList<>();
    private HashMap<String, Double> tfidf_score = new HashMap<>();

    private String[] stopWords = {"i", "me", "my", "myself", "we", "us", "our",
    		"ours", "ourselves", "you", "your", "yours", "yourself", "yourselves",
    		"he", "him", "his", "himself", "she", "her", "hers", "herself", "it",
            "they", "them", "their", "theirs", "themselves", "what", "which", "its",
            "who", "whom", "this", "that", "these", "those", "am", "is", "are",
            "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "would", "should", "could", "ought",
            "i'm", "you're", "he's", "she's", "it's", "we're", "they've", "i've",
            "you've", "we've", "they've", "i'd", "you'd", "he'd", "she'd", "we'd",
            "they'd", "i'll", "you'll", "he'll", "she'll", "we'll", "they'll",
            "isn't", "aren't", "wasn't", "weren't", "hasn't", "haven't", "hadn't",
            "doesn't", "don't", "didn't", "won't", "wouldn't", "can't", "cannot",
            "couldn't", "mustn't", "let's", "that's", "who's", "what's", "here's",
            "there's", "when's", "where's", "why's", "how's", "a", "an", "the",
            "and", "but", "if", "or", "because", "as", "until", "while", "of", "at",
            "by", "for", "with", "about", "against", "between", "into", "through",
            "during", "before", "after", "above", "below", "to", "from", "up", "down",
            "in", "out", "on", "off", "over", "under", "again", "further", "then",
            "once", "here", "there", "when", "where", "why", "how", "all", "any",
            "both", "each", "few", "more", "most", "other", "some", "such", "no",
            "nor", "not", "only", "own", "same", "so", "than", "too", "very", "itself"};

    public GenerateSummary(String fullText) {
        this.fullText = fullText;
    }


    public ArrayList<String> summarize(int n) {
        // Get the words from the full text and count their frequency
        getWords();
        // split text up into "sentences"
        parseSentences();
        // calculate the score for each sentence (each word adds its frequency and length as a value)
        tfscore();
        idf();
        tfidfscore();
        evaluateSentences();
        // return "x" number of sentences with the highest score
       return showSummary(n);

    }

    private void getWords() {
        // count the frequency of every word in the document (ignoring stop words)
        words = fullText.split("\\W+");   //  "\\W+" means to split on any non character such as .,!* etc

        // convert all words to lowercase for comparison to stop words
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toLowerCase();
        }

        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i];

            if (Arrays.asList(stopWords).contains(currentWord)) {
                int j=0; // ignore stop words
            } else {
                double count = wordFrequency.containsKey(currentWord) ? wordFrequency.get(currentWord) : 0; // conditional operator
                wordFrequency.put(currentWord, count + 1);
            }
        }
    }
    public void tfscore() {
    double total_words = wordFrequency.size();
    		for (String i : wordFrequency.keySet()) {
    			double b = wordFrequency.get(i)/total_words; //TF = (Frequency of the word in the sentence) / (Total number of words in the sentence)
    			  			
    			tf_score.put(i, b);		
    			}
    		
    }
    
    private void idf() {
    	int no_of_sentence = sentences.size();
    	for(String i : wordFrequency.keySet()) {
    		double b= Math.log10(no_of_sentence/wordFrequency.get(i));
    		idf_score.put(i, b);            //IDF: log((Total number of sentences (documents))/(Number of sentences (documents) containing the word))
    	}
    }
    private void tfidfscore() {
    	for(String i : tf_score.keySet()) {
    		double b = tf_score.get(i) * idf_score.get(i);
    		tfidf_score.put(i,b);
    	}
    }

    private void parseSentences() {
        String[] ignoreWords = {"Dr", "Mr", "Mrs", "Ms", "Sr", "Jr"};
        String currentSentence;
        int currentChar = 0;
        int previousStop = 0;
        while (currentChar < fullText.length() - 1) {
            if (fullText.charAt(currentChar) == '?' || fullText.charAt(currentChar) == '!') {
                // end of sentence
                currentSentence = fullText.substring(previousStop, currentChar + 1);
                sentences.add(currentSentence);
                currentChar++;
                previousStop = currentChar;
            }
            // Check to see if sentence if over when we see a period
            else if (fullText.charAt(currentChar) == '.') {
                if (currentChar - previousStop <= 2) {
                    // sentence only has two characters so skip there are no one character sentences??
                    // This would be one letter and a "."
                    currentChar++;
                }
                else if (currentChar > 2) {
                    String twoLetterAbbrev = fullText.substring(currentChar - 2, currentChar);
                    String threeLetterAbbrev = fullText.substring(currentChar - 3, currentChar);

                    // ignore words like Mr, Mrs, Dr, Etc
                    if (Arrays.asList(ignoreWords).contains(twoLetterAbbrev) ||
                            Arrays.asList(ignoreWords).contains(threeLetterAbbrev)) {
                        currentChar++;
                    }

                    // end of sentence
                    else {
                        currentSentence = fullText.substring(previousStop, currentChar + 1);
                        sentences.add(currentSentence);
                        currentChar++;
                        previousStop = currentChar;
                    }
                }
            }
            currentChar++;
        }

     //   System.out.println();
      //  System.out.println("Number of Sentences: " + sentences.size());
    }

    private void evaluateSentences() {
        // calculate the score for each sentence (each word adds its frequency and length as a value)

        // 1. Get a sentence
        int sentenceCount = 0;
        for (String s: sentences) {
            double sentenceScore = 0;
            // 2. Calculate the score for each sentence
            String[] wordsInSentence = s.split(" ");
            for (String word: wordsInSentence) {
                if (wordFrequency.get(word) != null) {
                    Double dbvalue = tfidf_score.get(word);
                    int value = dbvalue.intValue();
                    
                    sentenceScore += dbvalue;
                }

            }
			/*
			 * for (String i: tfidf_score) { if (tfidf_score.get(i) != null) { double value
			 * = tfidf_score.get(i); sentenceScore += value; }
			 * 
			 * }
			 */
            // Done looping over the words in the sentence
            // 3. Save the score for each sentence
            sentenceScores.put(sentenceCount, sentenceScore);
            sentenceCount++;
        }
    }

    private ArrayList<String> showSummary(int n) {
    	
    	 ArrayList<String> returnSentences = new ArrayList<>();
    	
        // 1. Get the highest values in sentenceScores
        int[] highest = findhighest(n);
        // 2. Print those sentences in order
        for (int x: highest) {
         //   System.out.println(sentences.get(x));
            returnSentences.add(sentences.get(x));
        }
        return returnSentences;

    }
    


    private int[] findhighest(int n) {
        int[] topKeys = new int[n];
        List<Double> values = new ArrayList<>(sentenceScores.values());
        Collections.sort(values, Collections.reverseOrder());
        List<Double> topN = values.subList(0, n);
        List<Integer> keys = new ArrayList<>(sentenceScores.keySet());

        for (int key: keys) {
            double currentScore = sentenceScores.get(key);
            if (topN.contains(currentScore)) {
                // Find the index
                for (int i = 0; i < n; i++) {
                    if (topN.get(i) == currentScore) {
                        topKeys[i] = key;
                    }
                }
            }
        }
        return topKeys;
    }
}


