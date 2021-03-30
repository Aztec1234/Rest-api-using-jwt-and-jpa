package com.aztec.student.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@Table(name="users")
public class User {

	@Id
	private int id;

	@Column(name="username")
	private String username;
	private String password;

}