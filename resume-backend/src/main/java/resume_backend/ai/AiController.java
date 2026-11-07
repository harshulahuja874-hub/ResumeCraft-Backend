
package resume_backend.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:5173")
public class AiController {

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    // Common Groq API method
    private String callGroq(
            String systemPrompt,
            String userPrompt,
            int maxTokens,
            double temperature) {

        Map<String, Object> requestBody = Map.of(
                "model", "openai/gpt-oss-20b",
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", systemPrompt
                        ),
                        Map.of(
                                "role", "user",
                                "content", userPrompt
                        )
                ),
                "max_completion_tokens", maxTokens,
                "temperature", temperature,
                "reasoning_effort", "low",
                "include_reasoning", false
        );

        Map<?, ?> response = restClient.post()
                .uri("https://api.groq.com/openai/v1/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        if (response == null) {
            throw new IllegalStateException("Empty response from Groq");
        }

        Object choicesObject = response.get("choices");

        if (!(choicesObject instanceof List<?> choices)
                || choices.isEmpty()) {
            throw new IllegalStateException("AI response not found");
        }

        Object firstChoice = choices.get(0);

        if (!(firstChoice instanceof Map<?, ?> choice)) {
            throw new IllegalStateException("Invalid AI response");
        }

        Object messageObject = choice.get("message");

        if (!(messageObject instanceof Map<?, ?> message)) {
            throw new IllegalStateException("AI message not found");
        }

        Object content = message.get("content");

        if (content == null || String.valueOf(content).isBlank()) {
            throw new IllegalStateException(
                    "Groq returned empty content. Please try again."
            );
        }

        return String.valueOf(content).trim();
    }

    // 1. AI Resume Content Generator
    @PostMapping("/generate")
    public Map<String, String> generateContent(
            @RequestBody Map<String, String> request) {

        String prompt = request.getOrDefault("prompt", "").trim();

        if (prompt.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please enter resume details."
            );
        }

        String content = callGroq(
                "You are a professional resume writer. "
                        + "Write clear, concise, ATS-friendly resume content. "
                        + "Never invent qualifications or achievements.",
                prompt,
                1000,
                0.5
        );

        return Map.of("content", content);
    }

    // 2. AI Template Recommendation
    @PostMapping("/recommend-template")
    public Map<String, String> recommendTemplate(
            @RequestBody Map<String, String> request) {

        String resumeDetails = request
                .getOrDefault("resumeDetails", "")
                .trim();

        if (resumeDetails.isEmpty()) {
            throw new IllegalArgumentException(
                    "Resume details are required."
            );
        }

        String prompt = """
                Analyze this resume and recommend the most suitable template.

                Available templates:
                1. ats - ATS Professional
                2. split - Modern Split
                3. executive - Executive
                4. tech - Tech Fresher

                Resume details:
                """ + resumeDetails + """

                Choose only one template.

                Return your answer in exactly this format:

                TEMPLATE: ats
                REASON: Your reason here.

                Replace ats with the selected template ID.
                Keep the reason short and simple.
                """;

        String recommendation = callGroq(
                "You are an expert resume design advisor. "
                        + "Recommend only from the four available templates. "
                        + "Always return a template ID and a short reason.",
                prompt,
                1000,
                0.3
        );

        return Map.of("recommendation", recommendation);
    }

    // 3. AI ATS Score Checker
    @PostMapping("/ats-score")
    public Map<String, String> checkAtsScore(
            @RequestBody Map<String, String> request) {

        String resumeDetails = request
                .getOrDefault("resumeDetails", "")
                .trim();

        if (resumeDetails.isEmpty()) {
            throw new IllegalArgumentException(
                    "Resume details are required."
            );
        }

        String prompt = """
                Analyze the following resume for general ATS readiness.

                Resume details:
                """ + resumeDetails + """

                Evaluate:
                1. Contact information
                2. Education details
                3. Technical skills
                4. Projects and experience
                5. Relevant keywords
                6. Achievements and measurable results
                7. Section completeness and clarity

                Give a score out of 100 based on the information provided.
                Do not invent missing details.
                Do not assume a job description was provided.
                Be fair to fresher resumes.

                Return the result in this exact format:

                ATS SCORE: 75/100

                STRENGTHS:
                - Strength one
                - Strength two

                IMPROVEMENTS:
                - Improvement one
                - Improvement two

                SUGGESTIONS:
                - Suggestion one
                - Suggestion two

                SUMMARY:
                Write a short overall review.

                Replace 75 with your actual score.
                Keep the language simple and useful.
                """;

        String analysis = callGroq(
                "You are an experienced ATS resume reviewer "
                        + "and placement advisor. "
                        + "Give honest and practical feedback.",
                prompt,
                1500,
                0.3
        );

        return Map.of("analysis", analysis);
    }
}
