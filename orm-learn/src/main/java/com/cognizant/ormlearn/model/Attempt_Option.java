package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "attempt_option")
public class Attempt_Option {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ao_id")
	int id;
	@ManyToOne
	@JoinColumn(name = "ao_op_id")
	private Options options;
	@ManyToOne
	@JoinColumn(name = "ao_aq_id")
	private Attempt_Question attemptQuestion;
	@Column(name = "ao_selected")
	boolean isSelected;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options = options;
	}
	public Attempt_Question getAttemptQuestion() {
		return attemptQuestion;
	}
	public void setAttemptQuestion(Attempt_Question attemptQuestion) {
		this.attemptQuestion = attemptQuestion;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	@Override
	public String toString() {
		return "AttemptOption [id=" + id + ", options=" + options + ", isSelected=" + isSelected + "]";
	}

}
