package com.fdzcxy.zerotime.dao;

import java.util.List;
import com.fdzcxy.zerotime.domain.Word;

public interface ACBDao {
	public List<Word> loadWord();
	public void addWord(Word word);
	public void updateWord(Word word);
}
