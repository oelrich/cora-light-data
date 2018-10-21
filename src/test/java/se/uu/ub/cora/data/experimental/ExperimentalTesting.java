package se.uu.ub.cora.data.experimental;

import se.uu.ub.cora.data.json.CoraFluidJson;
import se.uu.ub.cora.data.json.FluidJsonException;

import java.util.Map;

import static se.uu.ub.cora.data.json.CoraFluidJson.atomic;
import static se.uu.ub.cora.data.json.CoraFluidJson.group;

// for each item find metaDataType and extract designated links
// linkLists / (metaDataType) / (itemId) / collectedDataLinks / recordToRecordLink
// jq'{name: [.. | .name? ]| unique | length, value: [.. | .value?] | unique | length}' .\cora\linkLists_cora.json
// { "name": 3723, "value": 5316 }

//  E:\jq-win64.exe '{name: [.. | .name? ]| unique | length, value: [.. | .value?] | unique | length}' .\cora\linkLists_cora.json

public class ExperimentalTesting {
    Map<Integer, String> typeIndex;
    Map<String, Integer> nameValue;

    class MetaData {
        int type;
    }

    class Record {
        MetaData metaData;
    }

    CoraFluidJson recordToRecordLink(String linkSourceId, String linkTargetId) throws FluidJsonException {
        return group("recordToRecordLink",
                group("from",
                        atomic("linkRecordType", "recordType"),
                        atomic("linkedRecordId", linkSourceId)),
                group("to",
                        atomic("linkRecordType", "recordType"),
                        atomic("linkedRecordId", linkTargetId)));
    }
}
