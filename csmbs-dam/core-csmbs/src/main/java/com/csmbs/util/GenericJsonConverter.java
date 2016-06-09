/*
 * 
 */
package com.csmbs.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class GenericJsonConverter {

	/**  The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericJsonConverter.class);

	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper = mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper = mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
		mapper = mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * This method converts the input jsonPayLoad String to Java object.
	 *
	 * @param jsonPayLoad the json pay load
	 * @param clazz the clazz
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static Object marshallJsonToObject(final String jsonPayLoad, @SuppressWarnings("rawtypes") final Class clazz) {
		LOGGER.info(":Inside marshallJsonToObject() -->");
		Object object = null;
		try {
			object = mapper.readValue(jsonPayLoad, clazz);
		} catch (final JsonParseException e) {
			LOGGER.error("Failed to convert response: JsonParseException", e);
			throw new IllegalArgumentException("JSON input is malformed ", e);
		} catch (final JsonMappingException e) {
			LOGGER.error("Failed to convert response: JsonMappingException", e);
			throw new IllegalArgumentException("JSON input is invalid ", e);
		} catch (final IOException e) {
			LOGGER.error("Failed to convert response: IOException", e);
			throw new IllegalArgumentException("JSON input is not readable ", e);
		} catch (final Exception e) {
			LOGGER.error("Failed to convert response: Exception", e);
			throw new IllegalArgumentException("There is some error in processing Input JSON ", e);
		}
		LOGGER.info(":Exiting marshallJsonToObject() -->");
		return object;

	}

	/**
	 * This method converts the input java Object to JSON string.
	 *
	 * @param obj the obj
	 * @return String
	 */
	public static String marshallObjectToJsonString(final Object obj) {
		LOGGER.info(":Inside marshallObjectToJsonString() -->");
		final ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(obj);
		} catch (final JsonParseException e) {
			LOGGER.error("Failed to convert response: JsonParseException", e);
		} catch (final JsonMappingException e) {
			LOGGER.error("Failed to convert response: JsonMappingException", e);
		} catch (final IOException e) {
			LOGGER.error("Failed to convert response: IOException", e);
		} catch (final Exception e) {
			LOGGER.error("Failed to convert response: Exception", e);
		}
		LOGGER.info(":Exiting marshallObjectToJsonString() -->");
		return jsonString;
	}
}
