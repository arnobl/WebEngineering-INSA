package fr.insa.rennes.web.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		if(exception instanceof WebApplicationException) return ((WebApplicationException) exception).getResponse();
		return Response.status(500).build();
	}
}
