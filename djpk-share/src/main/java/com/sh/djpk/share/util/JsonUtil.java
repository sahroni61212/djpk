package com.sh.djpk.share.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sh.djpk.share.model.RestResponse;

public class JsonUtil {

	public static <T> List<T> mapJsonToListObject(Object source, Class<T> clazz)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		String json = mapper.writeValueAsString(source);
		TypeFactory t = TypeFactory.defaultInstance();

		List<T> list = mapper.readValue(json,
				t.constructCollectionType(ArrayList.class, clazz));

		return list;
	}

	public static <T, K> Map<T, K> mapJsonToHashMapObject(Object source)
			throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		String json = mapper.writeValueAsString(source);

		return mapper.readValue(json, new TypeReference<HashMap<T, K>>() {
		});
	}

	public static <T> T mapJsonToSingleObject(Object source, Class<T> clazz)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		String json = mapper.writeValueAsString(source);

		return mapper.readValue(json, clazz);
	}

	public static RestResponse mapJsonToRestResponse(InputStream source)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);

		return mapper.readValue(source, RestResponse.class);
	}

	
	public static String getJson(Object source) {
		if (source == null)
			return "";
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		String json = null;

		try {
			json = mapper.writeValueAsString(source);

			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}