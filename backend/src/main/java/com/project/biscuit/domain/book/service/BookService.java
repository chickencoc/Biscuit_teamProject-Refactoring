package com.project.biscuit.domain.book.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.biscuit.domain.book.dto.BookResponseDto;
import com.project.biscuit.domain.book.entity.Book;
import com.project.biscuit.domain.book.repository.BookRepository;
import com.project.biscuit.domain.bookclip.entity.Bookclip;
import com.project.biscuit.domain.bookclip.repository.BookclipRepository;
import com.project.biscuit.domain.user.entity.User;
import com.project.biscuit.domain.user.repository.UserRepository;
import com.project.biscuit.global.util.NaverBookAPI;
import com.project.biscuit.global.util.ScrapingBookInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final NaverBookAPI naverBookAPI;
    private final ScrapingBookInfo naverCrawling;
    private final ObjectMapper objectMapper;
    private final BookRepository bookRepository;
    private final BookclipRepository bookclipRepository;
    private final UserRepository userRepository;

    // 도서 검색
    public String searchBooks(String title, int display, int start, String sort) {
        return naverBookAPI.searchBook(title, display, sort, start);
    }

    // 도서 조회
    public BookResponseDto searchBookInfo(String isbn, String userId) throws IOException, InterruptedException {
        Optional<Book> optBooks = bookRepository.findByIsbn(isbn);
        Optional<User> optUser = userRepository.findByUserId(userId);

        if(optBooks.isEmpty()) {
            String json = naverBookAPI.searchBookByIsbn(isbn);
            JsonNode jsonNode = parseJsonNode(json);
            Book book = parseBooksObj(jsonNode);

            bookRepository.save(book);

            boolean chkClip;
            if(optUser.isPresent()) chkClip = bookclipRepository.existsByBook_NoAndUser_NoAndDelYn(book.getNo(), optUser.get().getNo(), "N");
            else chkClip = false;

            return BookResponseDto.of(book, chkClip);

        } else {
            boolean chkClip;
            if(optUser.isPresent()) chkClip = bookclipRepository.existsByBook_NoAndUser_NoAndDelYn(optBooks.get().getNo(), optUser.get().getNo(), "N");
            else chkClip = false;

            return BookResponseDto.of(optBooks.get(), chkClip);
        }
    }

    // 북클립 추가
    public String inClip(long userNo, String isbn) {
        Optional<Book> optBooks = bookRepository.findByIsbn(isbn);
        Optional<User> optUser = userRepository.findById(userNo);
        Optional<Bookclip> optBookClip = bookclipRepository.findByBook_NoAndUser_No(optBooks.get().getNo(), optUser.get().getNo());

        if(optBooks.isPresent() & optUser.isPresent()) {
            Bookclip bookclip;
            if(optBookClip.isPresent()) { // 삭제된 상태의 기존 북클립이 있는 경우
                bookclip = optBookClip.get();
                bookclip.setDelYn("N");

            } else { // 북클립을 완전히 새로 추가하는 경우
                bookclip = Bookclip.builder()
                        .book(optBooks.get())
                        .user(optUser.get())
                        .build();

            }
            bookclipRepository.save(bookclip);
            return parseJson(bookclip);
        }
        return "{\"result\": \"error\"}";
    }


    // Java Object => String Json
    private String parseJson(Object obj) {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        JsonNode jsonNode = om.valueToTree(obj);
        return jsonNode.toString();
    }

    // String Json => JsonNode
    private JsonNode parseJsonNode(String json) throws JsonProcessingException {
        return objectMapper.readTree(json);
    }

    // JsonNode => Books Object
    private Book parseBooksObj(JsonNode jsonNode) throws IOException, InterruptedException {
        JsonNode bInfo = jsonNode.get("items").get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSS");
        JsonNode bDtl = objectMapper.readTree(naverCrawling.searchMakersInfo(bInfo.get("link").asText()));

        return Book.builder()
                .isbn(bInfo.get("isbn").asText())
                .title(bInfo.get("title").asText())
                .bookDtlUrl(bInfo.get("link").asText())
                .bookImgUrl(bInfo.get("image").asText())
                .author(bInfo.get("author").asText())
                .publisher(bInfo.get("publisher").asText())
                .publishedDate(LocalDateTime.parse(bInfo.get("pubdate").asText() + " 00:00:00.000", formatter))
                .detail(bInfo.get("description").asText())
                .price(Long.parseLong(bInfo.get("discount").asText()))
                .authorDetail(bDtl.get("authorDetail").asText())
                .translator(bDtl.get("translator").asText())
                .transDetail(bDtl.get("translatorDetail").asText())
                .build();
    }

}
