package com.skilldistillery.filmquery.database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

class DatabaseAccessTests {
	private DatabaseAccessor db;

	@BeforeEach
	void setUp() throws Exception {
		db = new DatabaseAccessorObject();
	}

	@AfterEach
	void tearDown() throws Exception {
		db = null;
	}

	@DisplayName("findFilmById returns null with invalid id")
	@Test
	void test1() {
		Film f = db.findFilmById(-42);
		assertNull(f);
	}

//	Test2 can be improved by testing against a complete Film test object.
	@DisplayName("findFilmById returns film with valid id")
	@Test
	void test2() {
		Film f = db.findFilmById(1);
		assertEquals("ACADEMY DINOSAUR", f.getTitle());
		assertEquals(1, f.getId());
		assertEquals(1993, f.getReleaseYear());
	}

	@DisplayName("findActorById returns null with invalid id")
	@Test
	void test3() {
		Actor a = db.findActorById(-42);
		assertNull(a);
	}

	@DisplayName("findActorById returns actor with valid id")
	@Test
	void test4() {
		Actor a = db.findActorById(1);
		Actor test = new Actor(1, "Penelope", "Guiness");
		assertEquals(test, a);

	}

}
