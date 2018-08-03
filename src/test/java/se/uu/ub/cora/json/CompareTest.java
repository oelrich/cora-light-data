package se.uu.ub.cora.json;


import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CompareTest {

  private OrgJsonParser ojp;

  @BeforeTest
  public void setup() {
    ojp = new OrgJsonParser();

  }

  @Test
  public void testCompareEmptyObjects() {
    JsonObject lhs = ojp.parseStringAsObject("{}");
    JsonObject rhs = ojp.parseStringAsObject("{}");

    assertTrue(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareEqualStrings() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": \"text\" }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": \"text\" }");

    assertTrue(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareTwoEqualStrings() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": \"text\", \"test2\": \"text2\" }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": \"text\", \"test2\": \"text2\" }");

    assertTrue(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareDifferentNumberOfEntries() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": \"text\" }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": \"text\", \"other\": \"text\" }");

    assertFalse(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareDifferentKeys() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"expected\": \"text\" }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"actual\": \"text\" }");

    assertFalse(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareDifferentStringValues() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": \"actual\" }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": \"expected\" }");

    assertFalse(Compare.equal(lhs, rhs));
  }


  @Test
  public void testCompareDifferentJsonValues() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": \"actual\" }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": [] }");

    assertFalse(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareEqualObjectValues() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": {} }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": {} }");

    assertTrue(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareDifferentObjectValues() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": {} }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": { \"has\":\"value\" } }");

    assertFalse(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareEqualArrayValues() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": [] }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": [] }");

    assertTrue(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareDifferentArrayValues() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": [ \"actual\" ] }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": [ \"expected\" ] }");

    assertFalse(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareDifferentArrayLenghtsLhsShort() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": [] }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": [ \"test\" ] }");

    assertFalse(Compare.equal(lhs, rhs));
  }

  @Test
  public void testCompareDifferentArrayLenghtsLhsLong() {
    JsonObject lhs = ojp.parseStringAsObject("{ \"test\": [\"test\"] }");
    JsonObject rhs = ojp.parseStringAsObject("{ \"test\": [] }");

    assertFalse(Compare.equal(lhs, rhs));
  }
}
