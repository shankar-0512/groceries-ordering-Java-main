package com.groceries.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO extends CommonResponseDTO {

	String userId;
	String userName;
	String userAddress;

}
