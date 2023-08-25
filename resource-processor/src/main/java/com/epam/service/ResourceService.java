package com.epam.service;

import com.epam.client.ResourceClient;
import com.epam.client.SongClient;
import com.epam.model.dto.ResourceDto;
import com.epam.model.dto.SongDTO;
import com.epam.model.message.ResourceMessage;
import com.epam.util.MP3Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.epam.util.Constants.*;

@Service
@AllArgsConstructor
@Log4j2
public class ResourceService {

	private final ResourceClient resourceClient;
	private final SongClient songClient;
	private final MP3Utils mp3Utils;

	@KafkaListener(topics = "resource-created", containerFactory = "resourceMessageKafkaListenerContainerFactory")
	public void receiveMessage(ResourceMessage message) {
		log.info("KAFKA MESSAGE: " + message);

		if (message != null) {
			ResourceDto resourceDto = resourceClient.findResourceById(message.getId());
			SongDTO songDTO = buildSongDto(resourceDto.getFile(), message.getFileName());

			if (songDTO != null) {
				songDTO.setResourceId(message.getId());
				songClient.createSong(songDTO);
			}
		}
	}

	private Metadata extractMetadata(byte[] file) {
		Metadata metadata = null;

		try {
			metadata = new Metadata();
			BodyContentHandler handler = new BodyContentHandler();
			Mp3Parser parser = new Mp3Parser();
			ParseContext parseContext = new ParseContext();

			InputStream initialStream = new ByteArrayInputStream(file);
			parser.parse(initialStream, handler, metadata, parseContext);
		} catch (IOException | TikaException | SAXException exception) {
			log.error("An exception occurred during parsing file metadata", exception);
		}

		return metadata;
	}

	private SongDTO buildSongDto(byte[] file, String fileName) {
		SongDTO songDTO = null;

		Metadata metadata = extractMetadata(file);
		if (metadata != null) {
			songDTO = new SongDTO();
			songDTO.setName(fileName);
			songDTO.setArtist(metadata.get(ARTIST));
			songDTO.setAlbum(metadata.get(ALBUM));
			songDTO.setLength(mp3Utils.convertLength(metadata.get(LENGTH)));
			songDTO.setYear(metadata.get(YEAR));
		}

		return songDTO;
	}
}
