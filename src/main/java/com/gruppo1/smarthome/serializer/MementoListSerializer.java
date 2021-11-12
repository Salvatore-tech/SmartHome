package com.gruppo1.smarthome.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gruppo1.smarthome.crud.memento.Memento;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@JsonComponent
public class MementoListSerializer
        extends JsonSerializer<List<Memento>> {

    @Override
    public void serialize(List<Memento> mementos, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        mementos.forEach(element -> {
                    try {
                        jsonGenerator.writeStartObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        if ((Objects.isNull(element.getHomeItem()))) {
                            jsonGenerator.writeStringField("Item name", "Invalid");
                        } else {
                            jsonGenerator.writeStringField("Item name", element.getHomeItemName());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        jsonGenerator.writeStringField("Operation", element.getDesc());
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


