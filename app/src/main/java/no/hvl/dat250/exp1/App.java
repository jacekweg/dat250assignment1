package no.hvl.dat250.exp1;

import io.javalin.Javalin;

public class App {

    private static final String WEBPAGE = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Convert units</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>Unit converter</h1>\n" +
            "<form action=\"/convert\" method=\"post\">\n" +
            "    <fieldset>\n" +
            "    <label for=\"val\">Value:</label>" +
            "    <input id=\"val\" type=\"text\" name=\"value\"><br />\n" +
            "    <label for=\"source-unit\">From unit:</label>\n" +
            "    <select name=\"sunit\" id=\"source-unit\">\n" +
            "        <option value=\"in\">Inches</option>\n" +
            "        <option value=\"ft\">Feet</option>\n" +
            "        <option value=\"mi\">Miles</option>\n" +
            "        <option value=\"m\">Metres</option>\n" +
            "    </select><br />\n" +
            "    <label for=\"target-unit\">To unit:</label>\n" +
            "    <select name=\"tunit\" id=\"target-unit\">\n" +
            "        <option value=\"in\">Inches</option>\n" +
            "        <option value=\"ft\">Feet</option>\n" +
            "        <option value=\"mi\">Miles</option>\n" +
            "        <option value=\"m\">Metres</option>\n" +
            "    </select><br />\n" +
            "    <input type=\"submit\" value=\"Calculate\" />\n" +
            "    </fieldset>\n" +
            "</form>\n" +
            "</body>\n" +
            "</html>";

    private static final double IN_TO_METER = 0.0254;
    private static final double FT_TO_METER = 0.3048;
    private static final double MI_TO_METER = 1609.344;

    /**
     * Converts a value from one unit to another.
     *
     * @param value     The value to be converted.
     * @param fromUnit  The unit to convert from.
     * @param toUnit    The unit to convert to.
     * @return The converted value.
     */
    public static double convertUnits(double value, String fromUnit, String toUnit) {
        double conversionFactor;
        if (fromUnit.equals("in")) {
            conversionFactor = IN_TO_METER;
        } else if (fromUnit.equals("ft")) {
            conversionFactor = FT_TO_METER;
        } else if (fromUnit.equals("mi")) {
            conversionFactor = MI_TO_METER;
        } else {
            conversionFactor = 1.0;
        }

        double inMeters = value * conversionFactor;

        if (toUnit.equals("in")) {
            return inMeters / IN_TO_METER;
        } else if (toUnit.equals("ft")) {
            return inMeters / FT_TO_METER;
        } else if (toUnit.equals("mi")) {
            return inMeters / MI_TO_METER;
        } else {
            return inMeters;
        }
    }

    public static void main(String[] args) {
        Javalin.create()
                .get("/", ctx -> {
                    ctx.html(WEBPAGE);
                })
                .post("/convert", ctx -> {
                    double value = Double.parseDouble(ctx.formParam("value"));
                    String fromUnit = ctx.formParam("sunit");
                    String toUnit = ctx.formParam("tunit");

                    double result = convertUnits(value, fromUnit, toUnit);

                    ctx.result(Double.toString(result));
                })
                .start(9000);
    }
}
