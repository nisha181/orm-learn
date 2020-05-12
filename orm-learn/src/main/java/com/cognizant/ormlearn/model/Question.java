package com.cognizant.ormlearn.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qt_id")
	private int id;
	@Column(name="qt_text")
	private String text;
	@OneToMany(mappedBy="question", fetch=FetchType.EAGER)
	private Set<Options> optionsList;
	@OneToMany(mappedBy="question")
	Set<Attempt_Question> attemptQuestionsList;
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Set<Options> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(Set<Options> optionsList) {
		this.optionsList = optionsList;
	}
	public Set<Attempt_Question> getAttemptQuestionsList() {
		return attemptQuestionsList;
	}
	public void setAttemptQuestionsList(Set<Attempt_Question> attemptQuestionsList) {
		this.attemptQuestionsList = attemptQuestionsList;
	}

}
