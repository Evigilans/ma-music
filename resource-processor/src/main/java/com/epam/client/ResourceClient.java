package com.epam.client;

import com.epam.model.dto.ResourceDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("resource-service-spr")
public interface ResourceClient {

	@Retry(name = "retryResourceProcessor")
	@GetMapping(value = "/resources/{id}", produces = "application/json")
	ResourceDto findResourceById(@PathVariable long id);
}
