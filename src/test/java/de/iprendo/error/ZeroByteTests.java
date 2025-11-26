package de.iprendo.error;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;

class ZeroByteTests {

	@Test
	void contextLoads() {
        String pdfFilePath = "page5.pdf";

        DocumentReader reader = new PagePdfDocumentReader(pdfFilePath);

        reader.read().stream().map(Document::getText).filter(Objects::nonNull).forEach(text -> {
            int idx = text.indexOf("\u0000");
            if (idx >= 0) {
                String message = "Extracted text contains null byte at index " + idx;
                System.out.println(message);
                
                int startIndex = Math.max(idx - 5, 0);
                int endIndex = Math.min(idx + 25, text.length() - 1);
                String part = text.substring(startIndex, endIndex);
                System.out.println(part);
                System.out.println(hd(part));
                
                throw new RuntimeException(message);
            }
        });
	}
    
    private static String hd(String s) {
        if (s == null) {
            return "NULL";
        }
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        return IntStream.range(0, bytes.length)
                .map(i -> bytes[i] & 0xFF)
                .mapToObj(b -> String.format("%02X", b))
                .collect(java.util.stream.Collectors.joining(" "));
    }
}
