package com.epam.controller;

import com.epam.model.Song;
import com.epam.model.dto.SongDTO;
import com.epam.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/songs")
@Validated
public class SongController {

	private final SongService songService;

	@GetMapping("/{id}")
	public ResponseEntity<Song> findSongById(@PathVariable String id) {
		Optional<Song> optionalSong = songService.findSongById(id);
		return optionalSong.map(song -> ResponseEntity.ok().body(song)).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Long> createSong(@RequestBody SongDTO songDTO) {
		long songId = songService.saveSong(songDTO);
		return ResponseEntity.ok().body(songId);
	}

	@DeleteMapping
	public ResponseEntity<List<String>> deleteSongs(@RequestParam String[] ids) {
		List<String> deletedIds = songService.deleteSongsByIds(ids);
		return ResponseEntity.ok().body(deletedIds);
	}
}
