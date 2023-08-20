package com.epam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "resources")
public class Resource {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "song_url")
	private String songUrl;
}
