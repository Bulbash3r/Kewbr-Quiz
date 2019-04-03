package App.Model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JsonConverter implements JsonDeserializer<Package>, JsonSerializer<Package> {
    public JsonElement serialize(Package source, Type type,
                                 JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("packageName", source.getPackageName());
        object.addProperty("difficulty", Integer.toString(source.getDifficulty()));
        object.addProperty("date", source.getDate().toString().replace("9,", "9"));
        object.addProperty("questions", new Gson().toJson(source.getQuestions()));
        object.addProperty("answers", new Gson().toJson(source.getAnswers()));
        return object;
    }

    @Override
    public Package deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();

        String packageName = object.get("packageName").getAsString();
        Date date = null;
        try {
            date = new SimpleDateFormat("dd.MM.yy HH:mm").parse(object.get("date").getAsString());
        }catch (ParseException e) {
            e.printStackTrace();
        }
        int difficulty = object.get("difficulty").getAsInt();
        ArrayList<String> questions = new Gson().fromJson(object.getAsJsonArray("questions"), listType);
        ArrayList<String> answers = new Gson().fromJson(object.getAsJsonArray("answers"), listType);
        return new Package(packageName, difficulty, questions, answers, date);
    }
}
