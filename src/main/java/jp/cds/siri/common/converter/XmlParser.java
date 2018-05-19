package jp.cds.siri.common.converter;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.Lists;

public class XmlParser {
    public static String toJsonString(String xml) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        List entries = xmlMapper.readValue(xml, List.class);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonString = jsonMapper.writeValueAsString(entries);
        System.out.println(jsonString);

        return jsonString;
    }

    public static <T> List<T> toJson(String xml, Class<T> clazz) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        List entries = xmlMapper.readValue(xml, List.class);

        ObjectMapper jsonMapper = new ObjectMapper();
        List<T> jsonList = Lists.newArrayList();
        for (Object e : entries) {
            T pojo = jsonMapper.readValue(jsonMapper.writeValueAsString(e), clazz);
            jsonList.add(pojo);
        }

        return jsonList;
    }

}
