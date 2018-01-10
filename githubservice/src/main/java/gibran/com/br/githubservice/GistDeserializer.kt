package gibran.com.br.githubservice

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import gibran.com.br.githubservice.model.Gist
import java.lang.reflect.Type

/**
 * Created by gibranlyra on 04/07/17.
 */

class GistDeserializer : JsonDeserializer<Gist>, JsonSerializer<Gist> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Gist {
        val jsonObject = json as JsonObject

        val gson = GitHubApiModule.getGsonBuilder()
        val gist = gson.fromJson(json, Gist::class.java)
        val typeHashMap = object : TypeToken<Map<String, String>>() {}.type
        val fileMap: Map<String, String> = gson.fromJson(jsonObject.get("file").asJsonObject, typeHashMap)
        gist.files?.file = fileMap
        return gist
    }

    override fun serialize(src: Gist, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
        //TODO implement serialize
        return null
    }
}
