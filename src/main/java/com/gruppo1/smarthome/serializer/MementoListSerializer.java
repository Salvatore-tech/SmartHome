package com.gruppo1.smarthome.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.List;

@JsonComponent
public class MementoListSerializer
        extends JsonSerializer<List<ImmutablePair<String, String>>> {

    @Override
    public void serialize(List<ImmutablePair<String, String>> mementoPairOfString, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        mementoPairOfString.forEach(element -> {
            try {
                jsonGenerator.writeStartObject();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if ((StringUtils.isEmpty(element.getLeft()))) {
                    jsonGenerator.writeStringField("Item name", StringUtils.EMPTY);
                } else {
                    jsonGenerator.writeStringField("Item name", element.getLeft());
                }
            } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        jsonGenerator.writeStringField("Operation", element.getRight());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        jsonGenerator.writeEndObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        );
        jsonGenerator.writeEndArray();
    }
}


