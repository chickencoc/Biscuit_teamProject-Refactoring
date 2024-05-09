package com.project.biscuit.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest
class BookclassServiceTest {

//    @Autowired
//    private BookclassMemberRepository bkcMemRepository;
//    @Autowired
//    private BookclassRepository bkrepo;
//    @Autowired
//    private RecycleRepository rcrepo;
//    @Autowired
//    private BookclipAndBookcntRepository bcbrepo;

    @Test
    public void test1() throws IOException {
//        List<BookclassMember> lbcm = bkcMemRepository.findByUser_No(1L);
//        Stream<BookclassMember> result = lbcm.stream().filter((item) -> item.getBookclass().getStatus().equals("Y"));
//
//        System.out.println("⛔⛔");
//        result.forEach(System.out::println);
//
//        System.out.println("⛔⛔⛔⛔");
//        lbcm.forEach(System.out::println);

        String[] cmd = new String[] {"python", "getbookinfo.py", "https://search.shopping.naver.com/book/catalog/32473290161"}; // python 실행

        ProcessBuilder pBuilder = new ProcessBuilder(cmd);

        Process process = pBuilder.start();
//        System.out.println("command: " + pBuilder.command()); 	// 커맨드 확인

        BufferedReader br = new BufferedReader(new InputStreamReader( process.getInputStream() ));

        String line = null;
        while( (line = br.readLine()) != null ){
//            System.out.println(line);
            if(line != null) break;
        }

//        int exitCode = process.waitFor();
//        System.out.println("exitCode : " + exitCode);
        process.destroy();



//        System.out.println(System.getProperty("user.dir"));
//
//        File file = new File("");
//        System.out.println(file.getAbsoluteFile().getParentFile().getParent() + "\\frontend\\public\\images");
//
//        File file2 = new File("../../");
//        System.out.println(file2.getAbsoluteFile() + "\\frontend\\public\\images/");

    }
}