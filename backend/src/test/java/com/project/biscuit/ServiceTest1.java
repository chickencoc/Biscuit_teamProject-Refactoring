//package com.project.biscuit;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import org.junit.jupiter.api.Test;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.biscuit.domain.book.entity.Books;
//import com.project.biscuit.util.NaverBookAPI;
//import com.project.biscuit.util.NaverBookInfoCrawling;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeFormatterBuilder;
//import java.time.temporal.ChronoField;
//import java.util.Locale;
//
//@SpringBootTest
//public class ServiceTest1 {
//    @Autowired
//    private NaverBookAPI naverBookAPI;
//    @Autowired
//    private NaverBookInfoCrawling naverCrawling;
//    @Autowired
//    private ObjectMapper objectMapper;
//
////    @Test
////    void setNaverBookAPITest1() throws IOException, InterruptedException {
////        String searchBook = naverBookAPI.searchBook("보이지 않는 손", 5, "sim");
////
////        JsonNode jsonNode = objectMapper.readTree(searchBook);
////        JsonNode bInfo = jsonNode.get("items").get(0);
////        System.out.println(bInfo.toPrettyString());
////        System.out.println("-----------");
////
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSS");
////
////        String json = naverCrawling.searchMakersInfo(bInfo.get("link").asText());
////        JsonNode bDtl = objectMapper.readTree(json);
////
////        System.out.println(bDtl.toPrettyString());
////
////        Books books = Books.builder()
////                .isbn(bInfo.get("isbn").asText())
////                .title(bInfo.get("title").asText())
////                .bookDtlUrl(bInfo.get("link").asText())
////                .bookImgUrl(bInfo.get("image").asText())
////                .author(bInfo.get("author").asText())
////                .publisher(bInfo.get("publisher").asText())
////                .publishedDate(LocalDateTime.parse(bInfo.get("pubdate").asText() + " 00:00:00.000", formatter))
////                .detail(bInfo.get("description").asText())
////                .price(Long.parseLong(bInfo.get("discount").asText()))
////                .authorDetail(bDtl.get("authorDetail").asText())
////                .translator(bDtl.get("translator").asText())
////                .transDetail(bDtl.get("translatorDetail").asText())
////                .build();
////
//////        System.out.println("⛔" + jsonNode.get("items").toPrettyString());
////        System.out.println(books.toString());
////    }
//
//
//}
