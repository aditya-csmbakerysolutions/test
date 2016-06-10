package com.csmbs.util;

import static com.csmbs.util.Constants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csmbs.exceptions.CSMBSCustomException;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AppUtil {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppUtil.class);
	
    private static int CHART_PACK = 1;
	
	private static int PRECISION_FORMAT = 2;

	private static int PDF = 3;
	/**
	 * Convert value to list.
	 * 
	 * @param values
	 *            property value which is string array.
	 * @return list of string containing the values of property.
	 */
	public final static List<String> convertValueToList(final Value[] values) {
		final List<String> pageList = new ArrayList<String>();
		for (final Value str : values) {
			try {
				pageList.add(str.getString());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Persisted List Contains : " + str.getString());
				}
			} catch (final ValueFormatException e) {
				LOGGER.error("Value Format Exception : ", e);
			} catch (final IllegalStateException e) {
				LOGGER.error("IllegalStateException : ", e);
			} catch (final RepositoryException e) {
				LOGGER.error("RepositoryException : ", e);
			}
		}
		return pageList;
	}

	/**
	 * To convert list to array.
	 * 
	 * @param list
	 *            list
	 * @return String[] array
	 */
	public final static String[] convertListToArray(final List<String> list) {
		LOGGER.info("Start of Method -- convertListToArray");
		String[] stringArray = {};
		if (!ValidationUtil.isEmpty(list)) {
			LOGGER.info("In loop - convertListToArray");
			stringArray = list.toArray(new String[list.size()]);
		}
		LOGGER.info("End of Method -- convertListToArray");
		return stringArray;

     }
	
	/**
	 * This method is used for logging service execution time.
	 *
	 * @return String
	 */
	public static String getCurrentTime() {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		final Date date = new Date();
		return dateFormat.format(date);
	}
	
	/**
	 * This method reads an input stream and returns the value as a string.
	 * @param is
	 * @return
	 */
	public static String getStringForStream(final InputStream is) {
		LOGGER.info(":Inside getStringForStream() --> "); 
		final StringBuffer result = new StringBuffer();
		String out = "";
		final BufferedReader bo = new BufferedReader(new InputStreamReader(is));
		try {
			while ((out = bo.readLine()) != null) {
				result.append(out);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Result String : {} --> ", new String[] {result.toString()});
			}
		}
		catch (final IOException e) {
			LOGGER.error("IOException : ", e);
		}
		LOGGER.info(":Exiting getStringForStream() --> ");
		return result.toString();
	}

	/**
	 * This method converts a map to a JSON string and returns it.
	 * @param jsonValues
	 * @return
	 */
	public static String getJson(final Map<String, String> jsonValues) {
		LOGGER.info(":Inside getJson() --> "); 
		String jsonString = null;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			jsonString = mapper.writeValueAsString(jsonValues);
			if (LOGGER.isDebugEnabled()) {
    			LOGGER.debug("Output JSON string : {} ", new String[] { jsonString });
    		}
		}
		catch (final JsonGenerationException e) {
			LOGGER.error("JsonGenerationException : ", e);
		}
		catch (final JsonMappingException e) {
			LOGGER.error("JsonMappingException : ", e);
		}
		catch (final IOException e) {
			LOGGER.error("IOException : " + e);
		}
		LOGGER.info(":Exiting getJson() --> "); 
		return jsonString;
	}
	/**
	 * To take tag values stored in content type and get the tag title.
	 * 
	 * @param Property
	 *            content type
	 * @return ArrayList<String>
	 * @throws LoginException
	 * @throws RepositoryException
	*/
	public final static ArrayList<String> processContentType(
			final Property contentTypes) throws LoginException,
			ValueFormatException, RepositoryException {
		LOGGER.info("Start of Method -- processContentType");
		ArrayList<String> tagList = new ArrayList<String>();
		ResourceResolverFactory resolverFactory = CRXUtil
				.getServiceReference(ResourceResolverFactory.class);
		ResourceResolver resourceResolver  = resolverFactory
					.getAdministrativeResourceResolver(null);
		Value[] contentTypeValues = contentTypes.getValues();
		List<String> contentValues = convertValueToList(contentTypeValues);
		for (String tagPath : contentValues) {
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			Tag contentTag = tagManager.resolve(tagPath);
			tagList.add(contentTag.getTitle());
		}
		LOGGER.info("End of Method -- processContentType");
		return tagList;

	}

	/**
	 * To find the format of the content
	 * 
	 * @param Object
	 *            parameters
	 * @return int
	 * @throws LoginException
	 * @throws RepositoryException
	 * @throws ValueFormatException
	 */
	public final static int findContentFormat(Object parameters) throws CSMBSCustomException {
		
		
		String data = parameters.toString();
		if(data.contains("content/dam/csmbs")){
			return PDF; 
		}
		else{
				
		try {
			ResourceResolverFactory resolverFactory = CRXUtil.getServiceReference(ResourceResolverFactory.class);
			ResourceResolver resourceResolver  = resolverFactory
					.getAdministrativeResourceResolver(null);
			Session session = resourceResolver.adaptTo(Session.class);
			
			String path = data+SLASH+JCR_CONTENT;
			
			Node node = session.getNode(path);
			if(node.hasProperty(CQ_TEMPLATE)){
				String template = node.getProperty(CQ_TEMPLATE).getString();
				if(template.equals(CHARTPACK_TEMPLATE)){
					return CHART_PACK;
				}
				else if(template.equals(PRECISION_FORMAT_TEMPLATE)){
					return PRECISION_FORMAT;
				}
			}
			
			
		} catch (LoginException e) {
			LOGGER.error("Error while getting content format: ", e);
			throw new CSMBSCustomException(e);
		} catch (RepositoryException e) {
			LOGGER.error("Error while getting content format: ", e);
			throw new CSMBSCustomException(e);
		}
		}
		return 0;
	}
	
}
