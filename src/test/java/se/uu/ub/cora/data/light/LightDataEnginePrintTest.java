package se.uu.ub.cora.data.light;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.uu.ub.cora.data.json.FluidJsonException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static se.uu.ub.cora.data.json.CoraFluidJson.group;

public class LightDataEnginePrintTest {
    private LightDataEngine lde;
    @BeforeMethod
    void initTest() {
        lde = new LightDataEngine();
        var jsonBook = readJsonResource("/test/book.json");
        lde.load(jsonBook);
    }

    @Test
    void testPrintRecordInfo() throws FluidJsonException {
        var lde = new LightDataEngine();
        String someName = "someName";
        String someRepeatId = "someRepeatId";
        var emptyGroup = group(someName, someRepeatId).build();

        LightGroup lightGroup = lde.load(emptyGroup);
        var groupAsText = lde.print();

        assertEquals(groupAsText, "someName,someRepeatId");
    }


    private JSONObject readJsonResource(String resourceFileName) {
        try (var file = getClass().getResourceAsStream(resourceFileName);
             var inputStream = new InputStreamReader(file);
             var reader = new BufferedReader(inputStream)) {
            return new JSONObject(reader.lines()
                    .collect(Collectors.joining()));
        } catch (Exception ignored) {
        }
        return null;
    }
}
