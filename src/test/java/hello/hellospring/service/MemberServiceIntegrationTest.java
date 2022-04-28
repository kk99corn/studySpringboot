package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;

	@Test
	void join() {
		// given
		Member member = new Member();
		member.setName("member_test");

		// when
		Long id = memberService.join(member);

		//then
		Member member1 = memberService.findOne(id).get();
		assertThat(member.getName()).isEqualTo(member1.getName());
	}

	@Test
	void duplicateMemberException() {
		// given
		Member member = new Member();
		member.setName("member1");

		Member member2 = new Member();
		member2.setName("member1");

		// when
		memberService.join(member);
		Exception e = assertThrows(IllegalStateException.class, () -> {
			memberService.join(member2);
		});

		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}
}