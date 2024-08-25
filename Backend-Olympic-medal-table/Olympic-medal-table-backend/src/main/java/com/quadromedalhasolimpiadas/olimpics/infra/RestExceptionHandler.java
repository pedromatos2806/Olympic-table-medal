package com.quadromedalhasolimpiadas.olimpics.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.quadromedalhasolimpiadas.olimpics.exceptions.NoRoleException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.PaisNotExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.UserAlreadyExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.UserNotExistsException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	private ResponseEntity<RestErrorMessage> userAlreadyExistsException(UserAlreadyExistsException exception) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMessage);
	}

	@ExceptionHandler(NoRoleException.class)
	private ResponseEntity<RestErrorMessage> noRoleException(NoRoleException exception) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMessage);
	}

	@ExceptionHandler(UserNotExistsException.class)
	private ResponseEntity<RestErrorMessage> userNotExistsException(UserNotExistsException exception) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMessage);
	}
	
	@ExceptionHandler(PaisNotExistsException.class)
	private ResponseEntity<RestErrorMessage> paisNotExistsException(PaisNotExistsException exception) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMessage);
	}
	
	

}
