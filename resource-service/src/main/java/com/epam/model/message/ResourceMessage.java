package com.epam.model.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResourceMessage {
	private long id;
	private String fileName;
}
