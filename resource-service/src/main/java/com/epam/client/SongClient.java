package com.epam.client;

import com.epam.model.dto.SongDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "song-service-spr")
public interface SongClient {

	@PostMapping(value = "/songs", produces = "application/json")
	void createSong(@RequestBody SongDTO songDTO);
}
