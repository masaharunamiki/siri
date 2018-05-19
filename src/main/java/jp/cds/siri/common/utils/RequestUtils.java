package jp.cds.siri.common.utils;

import java.nio.charset.Charset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Maps;

@Component
public class RequestUtils {

    /**
     * Form��p����GET Request�𑗂�
     *
     * @param url
     *            Request URL
     * @param input
     *            Form
     * @param output
     *            �o�̓N���X
     * @return O Response
     */
    public static <I, O> O get(String url, I input, Class<O> output) {
        Map<String, String> map = convertObjectToMap(input);
        UriComponents uri = getUriComponents(url, map);

        RestTemplate restTemplate = null;
        restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        restTemplate.setInterceptors(interceptors);

        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate.getForEntity(uri.toUriString(), output).getBody();
    }

    /**
     * POJO Object��Map�ɕϊ�����
     *
     * @param object
     *            POJO
     * @return Map
     */
    public static Map<String, String> convertObjectToMap(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Tokyo")));
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        @SuppressWarnings("unchecked")
        Map<String, Object> map = mapper.convertValue(object, Map.class);

        map.values().removeIf(value -> value == null);
        Map<String, String> ret = Maps.newHashMap();
        map.entrySet().stream().forEach(entry -> {
            ret.put(entry.getKey(), String.valueOf(entry.getValue()));
        });

        return ret;
    }

    /**
     * URL, map����{@link UriComponents}���擾����
     *
     * @param url
     *            HttpUrl�܂���path
     * @param map
     *            GET Parameter
     * @return {@link UriComponents}
     */
    public static UriComponents getUriComponents(String url, Map<String, String> map) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(map);
        UriComponentsBuilder uri = url.indexOf("http") != -1 ? UriComponentsBuilder.fromHttpUrl(url) : UriComponentsBuilder.fromPath(url);
        uri.queryParams(multiValueMap);
        return uri.build();
    }
}
