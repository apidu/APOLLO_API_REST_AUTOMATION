package paylaods;

import utils.ConfigManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Payloads {


    //readt start dated end date from file
    private static String fromDate = ConfigManager.getProperty("fromDate");
    private static String toDate = ConfigManager.getProperty("toDate");
    //paylaod for live trend api
    public static String getLiveTrendPayload() {
        return "[{\"plantId\":2,\"instanceType\":\"TRACKER\","
                + "\"instanceIdList\":[\"1\",\"13\",\"14\",\"15\"],"
                + "\"kpiCode\":\"TRACKER_ACCURACY_LIVE\","
                + "\"fromDate\":\"" + fromDate + "\","
                + "\"toDate\":\"" + toDate + "\"}]";
    }

    // Payload for Plant Active Power OMP API
    public static String getPlantActivePowerPayload() {
        return "{\n" +
                "  \"orgId\": 1,\n" +
                "  \"filter\": null,\n" +
                "  \"requests\": [\n" +
                "    {\n" +
                "      \"orgId\": 1,\n" +
                "      \"kpiCode\": \"PLANT_ACTIVE_POWER\",\n" +
                "      \"fromDate\": \"" + fromDate + "\",\n" +
                "      \"fromTime\": \"00:00\",\n" +
                "      \"toDate\": \"" + toDate + "\",\n" +
                "      \"toTime\": \"23:59\",\n" +
                "      \"groupBy\": \"MINUTE\",\n" +
                "      \"intraDayOperation\": null,\n" +
                "      \"interDayOperation\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    // Method to generate the Plant KPI Chart payload
    public static String getPlantKPIChartPayload() {

        // List of KPI codes that will be used in the payload
        List<String> kpiCodes = Arrays.asList("PLANT_ACTIVE_POWER", "PLANT_POA_IRRADIATION_POWER");

        // Template for the payload, with placeholder for the kpiCode
        String template = "{" +
                "\"plantId\": 2, " +                      // Plant ID, fixed value
                "\"instanceType\": \"PLANT\", " +         // Instance type, fixed value
                "\"instanceIdList\": [2], " +             // Instance ID list, fixed value
                "\"kpiCode\": \"%s\", " +                 // Placeholder for the KPI code
                "\"fromDate\": \"" + fromDate + "\", " +  // From date from ConfigManager
                "\"toDate\": \"" + toDate + "\", " +      // To date from ConfigManager
                "\"interDayOperation\": null, " +         // Inter-day operation, fixed value
                "\"groupBy\": \"MINUTE\", " +             // Grouping type, fixed value
                "\"timezone\": null, " +                  // Timezone, fixed value
                "\"fromTime\": \"00:00\", " +             // Start time, fixed value
                "\"toTime\": \"23:59\"" +                 // End time, fixed value
                "}";

        // Using streams to generate the payload for each KPI code
        // The template is formatted with each KPI code and then joined into a final JSON array
        return kpiCodes.stream()
                .map(code -> String.format(template, code))  // Replace the placeholder with actual KPI code
                .collect(Collectors.joining(",", "[", "]"));  // Join all the generated payloads into one JSON array


    }

}
