package com.epam.service;

import com.epam.model.Song;
import com.epam.model.dto.SongDTO;
import com.epam.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class SongService {

	private final SongRepository songRepository;

	public Optional<Song> findSongById(String resourceId) {
		return songRepository.findById(resourceId);
	}

	public long saveSong(SongDTO songDTO) {
		Song song = new Song();

		song.setResourceId(String.valueOf(songDTO.getResourceId()));
		song.setName(songDTO.getName());
		song.setArtist(songDTO.getArtist());
		song.setAlbum(songDTO.getAlbum());
		song.setLength(songDTO.getLength());
		song.setYear(songDTO.getYear());

		songRepository.save(song);
		return song.getId();
	}

	public List<String> deleteSongsByIds(String[] ids) {
		List<String> deletedIds = new ArrayList<>();

		for (String id : ids) {
			if (songRepository.existsById(id)) {
				songRepository.deleteById(id);
				deletedIds.add(id);
			}
		}

		return deletedIds;
	}
}
