package se.uu.ub.cora.json;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static se.uu.ub.cora.json.Compare.equal;

public class ConstructTest {
  private OrgJsonParser ojp;

  @BeforeTest
  public void setup() {
    ojp = new OrgJsonParser();

  }

  @Test
  public void testConstructEmpty() {
    JsonObject expected = ojp.parseStringAsObject("{}");
    JsonObject actual = Construct.json().build();

    assertTrue(equal(expected, actual));
  }

  @Test
  public void testConstructSimple() {
    JsonObject expected = ojp.parseStringAsObject("{ \"test\":\"value\" }");
    JsonObject actual =
        Construct.json().element("test","value").build();

    assertTrue(equal(expected, actual));
  }

  @Test
  public void testConstructFailSimple() {
    JsonObject expected = ojp.parseStringAsObject("{ \"test\":\"value\" }");
    JsonObject actual =
        Construct.json().element("test","error").build();

    assertFalse(equal(expected, actual));
  }

  @Test
  public void testConstructEmptyChild() {
    JsonObject expected = ojp.parseStringAsObject("{ \"test\":{} }");
    JsonObject actual =
        Construct.json().element("test", Construct.json()).build();

    assertTrue(equal(expected, actual));
  }

  @Test
  public void testConstructSimpleChild() {
    JsonObject expected = ojp.parseStringAsObject("{ \"test\":{ \"child\":\"value\"} }");
    JsonObject actual =
        Construct.json().element("test", Construct.json().element("child", "value")).build();

    assertTrue(equal(expected, actual));
  }

  @Test
  public void testConstructEmptyArrayChild() {
    JsonObject expected = ojp.parseStringAsObject("{ \"test\":[] }");
    JsonObject actual =
        Construct.json().array("test").build();

    assertTrue(equal(expected, actual));
  }

  @Test
  public void testConstructStringArrayChild() {
    JsonObject expected = ojp.parseStringAsObject("{ \"test\":[ \"one\", \"two\"] }");
    JsonObject actual =
        Construct.json().array("test", "one", "two").build();

    assertTrue(equal(expected, actual));
  }

  @Test
  public void testConstructArrayChild() {
    JsonObject expected = ojp.parseStringAsObject("{ \"test\":[ {} ] }");
    JsonObject actual =
        Construct.json().array("test", Construct.json()).build();

    assertTrue(equal(expected, actual));
  }

  @Test
  public void testConstructMixedArrayChild() {
    JsonObject expected = ojp.parseStringAsObject("{ \"test\":[ {}, \"text\" ] }");
    JsonObject actual =
        Construct.json().array("test", Construct.json(), "text").build();

    assertTrue(equal(expected, actual));
  }

  @Test(expectedExceptions = RuntimeException.class)
  public void testBadArray() {
    Construct.json().array("bad", 1);
  }

  @Test
  public void testConstrucComplexChild() {
    JsonObject expected = ojp.parseStringAsObject(
        "{ \"test\":[ { \"child\":\"entry\", \"arr\": []}, \"text\" ], \"some\": \"text\", \"child\": { \"key\": \"value\"} }");
    JsonObject actual =
        Construct
            .json()
            .array("test",
                Construct.json().element("child", "entry").array("arr"),
                "text")
            .element("some","text")
            .element("child", Construct.json().element("key","value"))
            .build();

    assertTrue(equal(expected, actual));
  }
}
