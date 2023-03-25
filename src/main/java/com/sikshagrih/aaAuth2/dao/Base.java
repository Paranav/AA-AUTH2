package com.sikshagrih.aaAuth2.dao;

import org.springframework.data.annotation.Version;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Base {
	@EmbeddedId
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected String id;
	@Version
	protected long version;
}
