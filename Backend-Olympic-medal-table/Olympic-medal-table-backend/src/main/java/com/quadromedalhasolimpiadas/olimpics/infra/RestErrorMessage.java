package com.quadromedalhasolimpiadas.olimpics.infra;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class RestErrorMessage {

	private HttpStatus httpStatus;

	private String message;
}
