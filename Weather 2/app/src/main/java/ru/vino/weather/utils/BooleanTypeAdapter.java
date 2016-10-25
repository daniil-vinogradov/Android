package ru.vino.weather.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BooleanTypeAdapter implements JsonDeserializer<Short> {

    @Override
    public Short deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        String b = json.getAsString();
        if ("true".equals(b) || "false".equals(b))
            return Boolean.valueOf(b) ? (short) 1 : (short) 0;
        else
            return null;
    }


//    @Override
//    public JsonElement serialize(Boolean arg0, Type arg1, JsonSerializationContext arg2) {
//        return new JsonPrimitive(arg0 ? 1 : 0);
//    }
//
//    @Override
//    public Boolean deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
//        return arg0.getAsInt() == 1;
//    }
}
