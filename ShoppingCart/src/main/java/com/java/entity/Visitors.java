package com.java.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Visitors {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Instant timestamp;
}
