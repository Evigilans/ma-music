package com.epam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "songs")
public class Song {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "resource_id", nullable = false)
	private String resourceId;

	@Column(name = "name")
	private String name;

	@Column(name = "artist")
	private String artist;

	@Column(name = "album")
	private String album;

	@Column(name = "length")
	private String length;

	@Column(name = "year")
	private String year;
}
