package com.epam.service;

import com.epam.model.Resource;
import com.epam.repository.ResourceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Log4j2
public class ResourceService {

	private final ResourceRepository resourceRepository;
	private final BlobStorageService blobStorageService;

	public Optional<Resource> findResourceById(String resourceId) {
		return resourceRepository.findById(resourceId);
	}

	public long uploadFile(MultipartFile multipartFile) {
		long resourceId = 0;

		String fileName = multipartFile.getOriginalFilename();
		if (StringUtils.isNotBlank(fileName)) {
			String songUrl = blobStorageService.uploadToBlobStorage(multipartFile, fileName);

			if (StringUtils.isNotBlank(songUrl)) {
				resourceId = saveFile(songUrl, fileName);

//				SongDTO songDTO = buildSongDto(multipartFile);
//				songDTO.setResourceId(resourceId);
//
//				if (resourceId != 0) {
//					songClient.createSong(songDTO);
//				}
			}
		}

		return resourceId;
	}

	private long saveFile(String songUrl, String fileName) {
		Resource resource = new Resource();
		resource.setSongUrl(songUrl);
		resource.setFileName(fileName);

		resourceRepository.save(resource);
		return resource.getId();
	}

	public List<String> deleteResourceByIds(String[] ids) {
		List<String> deletedIds = new ArrayList<>();

		for (String id : ids) {
			Optional<Resource> resource = resourceRepository.findById(id);

			if (resource.isPresent()) {
				String fileName = resource.get().getFileName();
				boolean isDeleted = blobStorageService.deleteFromBlobStorage(fileName);

				if (isDeleted) {
					resourceRepository.deleteById(id);
					deletedIds.add(id);
				}
			}
		}

		return deletedIds;
	}
}
