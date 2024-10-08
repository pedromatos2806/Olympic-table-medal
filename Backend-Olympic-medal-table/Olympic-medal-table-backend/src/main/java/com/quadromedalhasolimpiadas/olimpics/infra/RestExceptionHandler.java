package com.quadromedalhasolimpiadas.olimpics.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.quadromedalhasolimpiadas.olimpics.exceptions.EsporteNotExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.MedalhaNotExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.NoRoleException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.PaisNotExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.UserAlreadyExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.UserNotExistsException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<RestErrorMessage> tratarError400(MethodArgumentNotValidException exception) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	private ResponseEntity<RestErrorMessage> userAlreadyExistsException(UserAlreadyExistsException exception) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(restErrorMessage);
	}

	@ExceptionHandler(NoRoleException.class)
	private ResponseEntity<RestErrorMessage> noRoleException(NoRoleException exception) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
	}

	@ExceptionHandler(UserNotExistsException.class)
	private ResponseEntity<RestErrorMessage> userNotExistsException(UserNotExistsException exception) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
	}
	
	@ExceptionHandler(PaisNotExistsException.class)
	private ResponseEntity<RestErrorMessage> paisNotExistsException(PaisNotExistsException exception) {
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
	}
	
	@ExceptionHandler(EsporteNotExistsException.class)
	private ResponseEntity<RestErrorMessage> esporteNotExistsException(EsporteNotExistsException exception){
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST , exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
	}
	
	@ExceptionHandler(MedalhaNotExistsException.class)
	private ResponseEntity<RestErrorMessage> medalhaNotExistsException( MedalhaNotExistsException exception){
		RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
	}

}
