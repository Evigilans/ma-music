package com.epam.service;

import com.epam.client.SongClient;
import com.epam.model.Resource;
import com.epam.model.dto.SongDTO;
import com.epam.repository.ResourceRepository;
import com.epam.util.MP3Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.util.Constants.*;

@Service
@AllArgsConstructor
@Transactional
@Log4j2
public class ResourceService {

	private final ResourceRepository resourceRepository;
	private final SongClient songClient;
	private final MP3Utils mp3Utils;

	public Optional<Resource> findResourceById(String resourceId) {
		return resourceRepository.findById(resourceId);
	}

	public long uploadFile(MultipartFile multipartFile) {
		long resourceId = 0;

		SongDTO songDTO = buildSongDto(multipartFile);
		if (songDTO != null) {
			resourceId = saveFile(multipartFile);
			songDTO.setResourceId(resourceId);

			if (resourceId != 0) {
				songClient.createSong(songDTO);
			}
		}

		return resourceId;
	}

	private long saveFile(MultipartFile multipartFile) {
		long resourceId = 0;

		try {
			Resource resource = new Resource();
			resource.setData(multipartFile.getBytes());
			resourceRepository.save(resource);

			resourceId = resource.getId();
		} catch (IOException exception) {
			log.error("An exception occurred during saving resource to DB", exception);
		}

		return resourceId;
	}

	private Metadata extractMetadata(MultipartFile multipartFile) {
		Metadata metadata = null;

		try {
			metadata = new Metadata();
			BodyContentHandler handler = new BodyContentHandler();
			Mp3Parser parser = new Mp3Parser();
			ParseContext parseContext = new ParseContext();

			InputStream initialStream = multipartFile.getInputStream();
			parser.parse(initialStream, handler, metadata, parseContext);
		} catch (IOException | TikaException | SAXException exception) {
			log.error("An exception occurred during parsing file metadata", exception);
		}

		return metadata;
	}

	private SongDTO buildSongDto(MultipartFile multipartFile) {
		SongDTO songDTO = null;

		Metadata metadata = extractMetadata(multipartFile);
		if (metadata != null) {
			songDTO = new SongDTO();
			songDTO.setName(metadata.get(multipartFile.getOriginalFilename()));
			songDTO.setArtist(metadata.get(ARTIST));
			songDTO.setAlbum(metadata.get(ALBUM));
			songDTO.setLength(mp3Utils.convertLength(metadata.get(LENGTH)));
			songDTO.setYear(metadata.get(YEAR));
		}

		return songDTO;
	}

	public List<String> deleteResourceByIds(String[] ids) {
		List<String> deletedIds = new ArrayList<>();

		for (String id : ids) {
			if (resourceRepository.existsById(id)) {
				resourceRepository.deleteById(id);
				deletedIds.add(id);
			}
		}

		return deletedIds;
	}
}
