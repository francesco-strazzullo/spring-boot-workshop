package it.flowing.workshop;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.TextNode;
import it.flowing.workshop.model.UserId;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class UserIdSerializer {
    public static class UserIdJsonSerializer extends JsonSerializer<UserId> {
        @Override
        public void serialize(
                UserId userId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            jsonGenerator.writeString(userId.toString());
        }
    }

    public static class UserJsonDeserializer extends JsonDeserializer<UserId> {
        @Override
        public UserId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
            TextNode id = (TextNode) treeNode.get("id");
            return UserId.create(id.textValue());
        }
    }
}
