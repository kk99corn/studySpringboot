package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

	MemoryMemberRepository memberRepository;
	MemberService memberService;

	@BeforeEach	// 테스트 전
	public void beforeEach() {
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
	}


	@AfterEach	// 테스트 후
	void clear() {
		memberRepository.clearStore();
	}

	@Test
	void join() {
		// given
		Member member = new Member();
		member.setName("member1");

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

	@Test
	void findMembers() {
		List<Member> members = memberService.findMembers();
	}

	@Test
	void findOne() {
	}
}