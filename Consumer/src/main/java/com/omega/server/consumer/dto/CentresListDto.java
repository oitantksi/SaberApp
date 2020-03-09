package com.omega.server.consumer.dto;

import java.util.ArrayList;
import java.util.List;

public class CentresListDto {

	private List<CentreDto> centres;
	
	public List<CentreDto> getCentres() {
		return centres;
	}

	public void setCentres(List<CentreDto> centres) {
		this.centres = centres;
	}

	public CentresListDto() {
		centres = new ArrayList<>();
    }
}
