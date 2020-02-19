package it.flowing.workshop

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.node.TextNode
import it.flowing.workshop.model.UserId
import it.flowing.workshop.model.UserId.Companion.create
import org.springframework.boot.jackson.JsonComponent
import java.io.IOException

@JsonComponent
class UserIdSerializer {
    class UserIdJsonSerializer : JsonSerializer<UserId>() {
        @Throws(IOException::class)
        override fun serialize(userId: UserId, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
            jsonGenerator.writeString(userId.toString())
        }
    }

    class UserJsonDeserializer : JsonDeserializer<UserId>() {
        @Throws(IOException::class, JsonProcessingException::class)
        override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): UserId {
            val treeNode = jsonParser.codec.readTree<TreeNode>(jsonParser)
            val id = treeNode["id"] as TextNode
            return create(id.textValue())
        }
    }
}