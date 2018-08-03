package se.uu.ub.cora.json;

import se.uu.ub.cora.json.parser.JsonArray;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonString;
import se.uu.ub.cora.json.parser.JsonValue;

import java.util.Iterator;
import java.util.Map;

import static se.uu.ub.cora.json.parser.JsonValueType.*;

public class Compare {

  public static boolean equal(JsonValue lhs, JsonValue rhs) {
    boolean result = false;
    switch (lhs.getValueType()) {
      case STRING:
        if(rhs.getValueType() == STRING) {
          String rhs_value = ((JsonString) rhs).getStringValue();
          String lhs_value = ((JsonString) lhs).getStringValue();
          result = rhs_value.compareTo(lhs_value) == 0;
        }
        break;
      case OBJECT:
        if(rhs.getValueType() == OBJECT) {
          result = equal((JsonObject)lhs, (JsonObject)rhs);
        }
        break;
      case ARRAY:
        if(rhs.getValueType() == ARRAY) {
          result = equal((JsonArray) lhs,(JsonArray) rhs);
        }
        break;
      default:
        break;
    }

    return result;
  }

  public static boolean equal(JsonArray lhs, JsonArray rhs) {
    Iterator<JsonValue> lhs_it = lhs.iterator();
    Iterator<JsonValue> rhs_it = rhs.iterator();
    while(lhs_it.hasNext()) {
      if(!rhs_it.hasNext() || !equal(lhs_it.next(), rhs_it.next())) {
        return false;
      }
    }

    return !rhs_it.hasNext();
  }

  public static boolean equal(JsonObject lhs, JsonObject rhs) {
    for(Map.Entry<String, JsonValue> entry : lhs.entrySet()) {
      if(keyValueMissing(rhs, entry)) {
        return false;
      }
    }
    return lhs.size() == rhs.size();
  }

  private static boolean keyValueMissing(JsonObject rhs, Map.Entry<String, JsonValue> entry) {
    return ! (rhs.containsKey(entry.getKey()) &&
        equal(entry.getValue(), rhs.getValue(entry.getKey())));
  }
}
