package com.epam.util;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MP3Utils {

	public String convertLength(String seconds) {
		double doubleSeconds = Double.parseDouble(seconds);
		int minutes = (int) doubleSeconds / 60;
		int remainingSeconds = (int) doubleSeconds % 60;

		return String.format("%d:%02d", minutes, remainingSeconds);
	}
}
