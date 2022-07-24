package com.groceries.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FetchProfileResponseDTO extends CommonResponseDTO {
	
	String userName;
	String userAddress;

}
