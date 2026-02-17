package com.salesstore.ai_service.event;

import com.salesstore.event.ProductAddedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductConsumer {

    private final VectorStore vectorStore;

    @KafkaListener(topics = "product-added-topic", groupId = "ai-group")
    public void consumeProductAdded(ProductAddedEvent event) {
        log.info("AI Service: Indexing product: {}", event.getProductName());

        // 1. Create a searchable text representation
        String content = "Product: " + event.getProductName() +
                ". Description: " + event.getDescription() +
                ". Price: " + event.getPrice();

        // 2. Prepare metadata for filtering later
        Map<String, Object> metadata = Map.of(
                "productId", event.getProductId().toString(),
                "price", event.getPrice().doubleValue()
        );

        // 3. Create a 'Document' (Spring AI concept)
        Document document = new Document(content, metadata);

        // 4. EMBED & STORE: Spring AI calls Ollama to turn text into 768 numbers
        // and saves them to pgvector automatically.
        vectorStore.add(List.of(document));

        log.info("Product successfully embedded in Vector DB.");
    }
}