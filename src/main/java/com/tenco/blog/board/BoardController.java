package com.tenco.blog.board;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
@RequiredArgsConstructor
@Controller // Ioc 대상 - 싱글톤 패턴으로 관리됨
public class BoardController {

    // 생성자 의존 주의 - DI
    private final BoardPersistRepository br;

    // 주소 설계: /board/{{board.id}}/delete"
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id")Long id){
        br.deleteById(id);
        return "redirect:/";
    }

    /**
     * 주소 설계: http://localhost:8080/board/{id}/update-form
     * @return update-form.mustache
     * @param: id(board pk)
     */
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable(name = "id") Long id, HttpServletRequest request){
        // select * from board_tb where id = 4;
        Board board = br.findById(id);
        // 머스태치 파일에 조회된 데이터를 바인딩 처리
        request.setAttribute("board", board);

        return "board/update-form";
    }

    @PostMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Long id,
                         BoardRequest.UpdateDTO reqDTO){
        // 트랜잭션
        // 수정 -- select - 값을 확인해서 - 데이터를 수정 --> update
        // JPA 영속성 컨텍스트 활용
        br.update(id, reqDTO);
        // 수정 전략을 더티 채킹을 활용
        // 장점
        // 1. UPDATE 쿼리 자동 생성
        // 2. 변경된 필드만 업데이트 (성능 최적화)
        // 3. 영속성 컨텍스트에 일관성 유지
        // 4. 1차 캐시 자동 갱신 됨


        System.out.println("정상 파싱 확인: " + reqDTO.toString());

        // 업데이트 요청

        // 성공 시 리스트 화면으로 리다이렉트 처리
        return "redirect:/";
    }

    // 게시글 상세 보기
    // 주소설계 GET : http://localhost:8080/board/3

    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Long id, HttpServletRequest request){

        Board board = br.findById(id);
        request.setAttribute("board", board);

        //prefix: classpath:/templates
        //return: board/detail
        // suffix: .mustache
        // 1 차 캐시 효과 :
        //br.findById(id);
        return "board/detail";
    }

    // 1. index.mustache 파일을 반환 시키는 기능을 만든다.

    // 주소 설계: http://localhost:8080/
    @GetMapping("/")
    public String boardList(HttpServletRequest request){
        List<Board> board = br.findALL();
        request.setAttribute("boardList", board);

        return "index";
    }


    // 화면 연결 처리
    @GetMapping("/board/save-form")
    public String saveForm(){
        return "board/save-form";
    }

    //게시글 작성 액션(수행) 처리
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO){
        // HTTP 요청 본문 : title=값&content=값&username=값
        // form 태그의 MIME 타입 (application/x-www-form-urlencoded)

        // reqDTO <-- 사용자가 던진 데이터 상태가 있음
        // DTO -- Board -- DB
        // Board board = new Board(reqDTO.getTitle(), reqDTO.getContent(), reqDTO.getUsername());
        Board board = reqDTO.toEntity();
        br.save(board);
        return "redirect:/";
    }

}
