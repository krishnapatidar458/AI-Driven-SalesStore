package com.salesstore.ai_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AISearchController {

    private final VectorStore vectorStore;

    @GetMapping("/search")
    public List<Map<String, Object>> semanticSearch(@RequestParam String query) {
        // CORRECTED: .query is a method that takes the search string as an argument
        // We use .withTopK(5) to limit results to the 5 most similar products
        List<Document> results = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(5)
                        .build()
        );

        // Map the Document results to just their metadata (where we stored the productId)
        return results.stream()
                .map(Document::getMetadata)
                .collect(Collectors.toList());
    }
}