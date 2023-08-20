package com.epam.service;

import com.epam.model.dto.SongDTO;
import com.epam.util.MP3Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import static com.epam.util.Constants.*;

@Service
@AllArgsConstructor
@Log4j2
public class ResourceService {

	private final MP3Utils mp3Utils;

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
}
