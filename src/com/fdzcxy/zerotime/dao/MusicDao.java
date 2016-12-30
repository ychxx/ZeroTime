package com.fdzcxy.zerotime.dao;

import java.util.List;

import android.content.Context;

import com.fdzcxy.zerotime.domain.Music;

public interface MusicDao {
	public List<Music> loadMusic();

	public void addMusic(Music music);
	public void delMusic(int id);
	public void updateMusic(Music music);
}
