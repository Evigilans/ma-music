package com.epam.controller;

import com.epam.model.Resource;
import com.epam.service.ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/resources")
@Validated
public class ResourceController {

	private final ResourceService resourceService;

	@GetMapping("/{id}")
	public ResponseEntity<Resource> findResourceById(@PathVariable String id) {
		Optional<Resource> optionalResource = resourceService.findResourceById(id);
		return optionalResource.map(resource -> ResponseEntity.ok().body(resource)).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
		long songId = resourceService.uploadFile(file);
		return songId == 0 ? ResponseEntity.badRequest().body("The provided file not invalid") : ResponseEntity.ok().body(String.valueOf(songId));
	}

	@DeleteMapping
	public ResponseEntity<List<String>> deleteResources(@RequestParam String[] ids) {
		List<String> deletedIds = resourceService.deleteResourceByIds(ids);
		return ResponseEntity.ok().body(deletedIds);
	}
}
