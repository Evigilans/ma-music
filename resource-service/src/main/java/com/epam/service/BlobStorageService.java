package com.epam.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@Log4j2
public class BlobStorageService {

	private final String connectionString;
	private final String containerName;

	public BlobStorageService(@Value("${azure.storage.connection-string}") String connectionString, @Value("${azure.storage.container-name}") String containerName) {
		this.connectionString = connectionString;
		this.containerName = containerName;
	}

	public String uploadToBlobStorage(MultipartFile multipartFile, String fileName) {
		String blobUrl = null;

		try {
			BlobClient blobClient = new BlobClientBuilder()
					.connectionString(connectionString)
					.containerName(containerName)
					.blobName(fileName)
					.buildClient();

			blobClient.upload(multipartFile.getInputStream(), true);
			blobUrl = blobClient.getBlobUrl();
		} catch (IOException exception) {
			log.error("An exception occurred during parsing file metadata", exception);
		}

		return blobUrl;
	}

	public boolean deleteFromBlobStorage(String fileName) {
		BlobClient blobClient = new BlobClientBuilder()
				.connectionString(connectionString)
				.containerName(containerName)
				.blobName(fileName)
				.buildClient();

		return blobClient.deleteIfExists();
	}
}
