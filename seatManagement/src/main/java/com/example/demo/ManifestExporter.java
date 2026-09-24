package com.example.demo;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManifestExporter {

    private static final DateTimeFormatter TS_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String buildCsv(List<Attendee> confirmed, List<Attendee> waitlisted) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Name,Email,RegistrationType,Status,Seat,RegisteredAt\n");

        for (Attendee a : confirmed) {
            appendRow(sb, a);
        }
        for (Attendee a : waitlisted) {
            appendRow(sb, a);
        }

        return sb.toString();
    }

    private static void appendRow(StringBuilder sb, Attendee a) {
        sb.append(escape(a.getId())).append(",")
          .append(escape(a.getName())).append(",")
          .append(escape(a.getEmail())).append(",")
          .append(a.getType().name()).append(",")
          .append(a.getStatus().name()).append(",")
          .append(a.getSeatNumber() == null ? "" : a.getSeatNumber()).append(",")
          .append(a.getRegisteredAt().format(TS_FORMAT))
          .append("\n");
    }

    private static String escape(String field) {
        if (field == null) return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}
