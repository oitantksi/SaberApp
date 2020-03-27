package com.omega.server.consumer.dto;

import java.util.ArrayList;
import java.util.List;

public class MateriesListDto {
	private List<MateriaDto> materies;
	
	public List<MateriaDto> getMateries() {
		return materies;
	}

	public void setMateries(List<MateriaDto> materies) {
		this.materies = materies;
	}

	public MateriesListDto() {
		materies = new ArrayList<>();
    }
}
