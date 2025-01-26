package com.example.recommendation_system.services.url;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.*;

public class CourseraUrlGenerator {
///////// not working , couldnt get an api key/////////////////////////
    private static final String COURSERA_CATALOG_API_URL = "https://api.coursera.org/api/courses.v1";
    private static final String COURSERA_CATALOG_API_KEY = "YOUR_API_KEY_HERE";
    private static final Pattern COURSE_ID_PATTERN = Pattern.compile("([a-z0-9-]+)$");

    public static String getCourseraUrl(String courseTitle) {
        // Retrieve Course ID using Coursera Catalog API
        String courseId = retrieveCourseId(courseTitle);
        if (courseId == null) {
            return "Course not found";
        }

        // Generate Coursera URL
        return generateCourseraUrl(courseId);
    }

    private static String retrieveCourseId(String courseTitle) {
        try {
            // Set up API request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(COURSERA_CATALOG_API_URL + "?query=" + courseTitle + "&apiKey=" + COURSERA_CATALOG_API_KEY))
                    .GET()
                    .build();

            // Send request and parse response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                // Extract Course ID from response
                String responseBody = response.body();
                Matcher matcher = COURSE_ID_PATTERN.matcher(responseBody);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        } catch (IOException | InterruptedException e) {
            // Handle exceptions
        }
        return null;
    }

    private static String generateCourseraUrl(String courseId) {
        return "https://www.coursera.org/learn/" + courseId;
    }
}
