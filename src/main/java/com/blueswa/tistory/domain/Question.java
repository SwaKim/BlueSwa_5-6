package com.blueswa.tistory.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
public class Question extends AbstractEntity{
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	@JsonProperty
//	private Long writerId; //객체기반 JPA개발을 위해 User로 대체
	private User writer;

	@JsonProperty
	private String title;

	@Lob	//상당히 긴 컨텐츠를 관리해야 할 때 clob
	@JsonProperty
	private String contents;
	
	@JsonProperty
	private Integer countOfAnswer = 0;
	
	@OneToMany(mappedBy="question") //해당값은 Answer.java에서 OneToMany에 지정된 쉴드의 이름 question
	@OrderBy("id DESC")
	private List<Answer> answers; //5-5 19:33 java.util.List 임포트
	
	public Question() {}
	
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
	
	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public boolean isSameWriter(User loginUser) {
		System.out.println("writer : " + writer);
		return this.writer.equals(loginUser);
	}

	public void addAnswer() {
		this.countOfAnswer += 1;
	}

	public void deleteAnswer() {
		this.countOfAnswer -= 1;
	}
	
}
