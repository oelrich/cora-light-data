package se.uu.ub.cora.json;

import se.uu.ub.cora.json.builder.JsonArrayBuilder;
import se.uu.ub.cora.json.builder.org.OrgJsonArrayBuilderAdapter;
import se.uu.ub.cora.json.builder.org.OrgJsonObjectBuilderAdapter;
import se.uu.ub.cora.json.parser.JsonObject;

public class Construct {
  protected OrgJsonObjectBuilderAdapter builder;


  private Construct() {
    builder = new OrgJsonObjectBuilderAdapter();
  }

  public static Construct json(){
    return new Construct();
  }

  public JsonObject build() {
    return builder.toJsonObject();
  }

  public Construct element(String key, String value) {
    builder.addKeyString(key, value);
    return this;
  }

  public Construct element(String key, Construct value) {
    builder.addKeyJsonObjectBuilder(key, value.builder);
    return this;
  }

  public Construct array(String key) {
    JsonArrayBuilder ojaba = new OrgJsonArrayBuilderAdapter();
    builder.addKeyJsonArrayBuilder(key, ojaba);
    return this;
  }

  public Construct array(String key, String... values) {
    JsonArrayBuilder ojaba = new OrgJsonArrayBuilderAdapter();
    for(String value : values) {
      ojaba.addString(value);
    }
    builder.addKeyJsonArrayBuilder(key, ojaba);
    return this;
  }

  public Construct array(String key, Construct... values) {
    JsonArrayBuilder ojaba = new OrgJsonArrayBuilderAdapter();
    for(Construct value: values) {
      ojaba.addJsonObjectBuilder(value.builder);
    }
    builder.addKeyJsonArrayBuilder(key, ojaba);
    return this;
  }

  public Construct array(String key, Object... values) {
    JsonArrayBuilder ojaba = new OrgJsonArrayBuilderAdapter();
    for(Object value: values) {
      if(value instanceof Construct) {
        ojaba.addJsonObjectBuilder(((Construct)value).builder);
      } else if(value instanceof String) {
        ojaba.addString((String)value);
      } else {
        throw new RuntimeException("hissy fit");
      }
    }
    builder.addKeyJsonArrayBuilder(key, ojaba);
    return this;
  }

}
