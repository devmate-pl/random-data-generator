package pl.devmate.utils.random.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomWordProducer implements WordProducer {

	private final Random random = new Random();
	private final List<String> wordsList = new ArrayList<>();

	public RandomWordProducer() throws IOException {
		readWordsFromDictionary();
	}
	
	@Override
	public String randomWord() {
		int wordRandomIdx = random.nextInt(wordsList.size());
		return wordsList.get(wordRandomIdx);
	}

	private void readWordsFromDictionary() throws IOException {
		try (InputStream resourceAsStream = RandomWordProducer.class.getResourceAsStream("/dictionary/words");
				BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
			
			List<String> dictLines = reader.lines().collect(Collectors.toList());
			this.wordsList.addAll(dictLines);
		}			
	}

}
