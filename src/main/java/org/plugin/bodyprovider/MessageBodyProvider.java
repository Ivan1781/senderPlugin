package org.plugin.bodyprovider;

public class MessageBodyProvider {
    public static String getBodyForMessage(String report) {
        return String.format("""
                       {"text": "%s"}
                   """, report);
    }
}