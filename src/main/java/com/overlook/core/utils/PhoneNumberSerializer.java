package com.overlook.core.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.overlook.core.domain.provider.PhoneNumber;

import java.io.IOException;
import java.util.List;

public class PhoneNumberSerializer extends JsonSerializer<List<PhoneNumber>> {


    @Override
    public void serialize(List<PhoneNumber> phoneNumbers, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        phoneNumbers.forEach(phoneNumber -> {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeObjectField("number", phoneNumber.getPhoneNumber());
                jsonGenerator.writeObjectField("provider", phoneNumber.getProvider().getName());
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                throw new RuntimeException("Failed to serialize phone numbers");
            }
        });
        jsonGenerator.writeEndArray();
    }
}
