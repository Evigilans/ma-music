package com.epam.client;

import com.epam.model.dto.SongDTO;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("song-service-spr")
public interface SongClient {

	@Retry(name = "retryResourceProcessor")
	@PostMapping(value = "/songs", produces = "application/json")
	void createSong(@RequestBody SongDTO songDTO);
}
