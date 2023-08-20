package com.epam.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SongDTO {

	private long resourceId;
	private String name;
	private String artist;
	private String album;
	private String length;
	private String year;
}
