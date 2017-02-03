package id.base.app.service.message;

import id.base.app.SystemParameter;
import id.base.app.exception.SystemException;
import id.base.app.rest.QueryParamInterfaceRestCaller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Qualifier("ShortMessageService")
public class ShortMessageService implements ShortMessageServiceAPI {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ShortMessageService.class);

	@Override
	public void sendMessage(final String recipient, final String textMessage) throws Exception {
		execute(new QueryParamInterfaceRestCaller() {
			@Override
			public String getPath() {
				//deliberately null
				return null;
			}
			
			@Override
			public Map<String, Object> getParameters() {
				Map<String, Object> map = new LinkedHashMap<>();
					map.put("userkey", SystemParameter.SHORT_MESSAGE_SERVICE_USER_KEY);
					map.put("passkey", SystemParameter.SHORT_MESSAGE_SERVICE_PASS_KEY);
					map.put("nohp", recipient);
					map.put("pesan", textMessage);
				return map;
			}
		});
	}
	
	public void execute(QueryParamInterfaceRestCaller interfaceRestCaller) throws SystemException {
		try {
			URI url = new URI(SystemParameter.SHORT_MESSAGE_SERVICE_URL);
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
			handleQueryParam(interfaceRestCaller, builder);
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			new RestTemplate().exchange(builder.build().toUri(),HttpMethod.GET, entity, String.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void handleQueryParam(QueryParamInterfaceRestCaller interfaceRestCaller, UriComponentsBuilder builder) {
		for (Entry<String,Object> sparam : interfaceRestCaller.getParameters().entrySet()) {
			if(sparam.getValue() instanceof Collection<?>){
				String objJson = "";
				try {
					if(sparam.getValue()!=null){
						objJson = new ObjectMapper().writeValueAsString(sparam.getValue());
						builder.queryParam(sparam.getKey(), objJson);
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					LOGGER.error("findAllByFilter error build filter{}", e);
				}
			}else if(sparam.getValue() instanceof Object[]){
				builder.queryParam(sparam.getKey(), (Object[])sparam.getValue());
			}else{
				builder.queryParam(sparam.getKey(), sparam.getValue());
			}
		}
	}
	
}
