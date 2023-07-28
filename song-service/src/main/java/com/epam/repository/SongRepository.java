package com.epam.repository;

import com.epam.model.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, String> {
}
