package com.epam.repository;

import com.epam.model.Resource;
import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<Resource, String> {
}
