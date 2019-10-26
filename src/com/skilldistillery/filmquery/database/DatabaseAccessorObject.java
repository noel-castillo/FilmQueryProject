package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String user = "student";
		String password = "student";
		String sql = "SELECT * FROM film "
				+ "JOIN language ON film.language_id = language.id "
				+ "JOIN film_actor ON film.id = film_actor.film_id " 
				+ "JOIN actor ON film_actor.actor_id = actor.id "
				+ "WHERE film.id = ?";

		try (Connection conn = DriverManager.getConnection(URL, user, password);
				PreparedStatement stmt = setUp(filmId, conn, sql);
				ResultSet filmResults = stmt.executeQuery();){

			if (filmResults.next()) {
				film = new Film();
				film.setId(filmResults.getInt("film.id"));
				film.setTitle(filmResults.getString("film.title"));
				film.setDescription(filmResults.getString("film.description"));
				film.setReleaseYear(filmResults.getInt("release_year"));
				film.setLanguageId(filmResults.getInt("language_id"));
				film.setLanguage(filmResults.getString("language.name"));
				film.setRentalDuration(filmResults.getInt("rental_duration"));
				film.setRentalRate(filmResults.getDouble("rental_rate"));
				film.setLength(filmResults.getInt("length"));
				film.setReplacementCost(filmResults.getDouble("replacement_cost"));
				film.setRating(filmResults.getString("rating"));
				film.setSpecialFeatures(filmResults.getString("special_features"));
				film.setActors(findActorsByFilmId(film.getId()));
				film.setCategories(findCategoriesByFilmId(film.getId()));
			}

			filmResults.close();

		} catch (SQLException e) {
			System.err.println(e);
		}
		return film;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();
		Film film = null;
		String user = "student";
		String password = "student";
		String sql = "SELECT * "
				+ "FROM film "
				+ "JOIN language ON film.language_id = language.id "
				+ "WHERE film.title LIKE ? OR film.description LIKE ?";

		try (Connection conn = DriverManager.getConnection(URL, user, password);
				PreparedStatement stmt = setUp(keyword, conn, sql);
				ResultSet rs = stmt.executeQuery();) {

			while (rs.next()) {
				film = new Film();
				film.setId(rs.getInt("film.id"));
				film.setTitle(rs.getString("film.title"));
				film.setDescription(rs.getString("film.description"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setLanguage(rs.getString("language.name"));
				film.setRentalDuration(rs.getInt("rental_duration"));
				film.setRentalRate(rs.getDouble("rental_rate"));
				film.setLength(rs.getInt("length"));
				film.setReplacementCost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("special_features"));
				film.setActors(findActorsByFilmId(film.getId()));
				film.setCategories(findCategoriesByFilmId(film.getId()));
				films.add(film);
			}

			rs.close();

		} catch (SQLException e) {
			System.err.println(e);
		}
		return films;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String user = "student";
		String password = "student";
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, user, password);
				PreparedStatement stmt = setUp(actorId, conn, sql);
				ResultSet actorResult = stmt.executeQuery();) {

			if (actorResult.next()) {
				actor = new Actor();
				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
			}

		} catch (

		SQLException e) {
			System.err.println(e);
		}
		return actor;
	}
	
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		Actor actor = null;
		String user = "student";
		String password = "student";
		String sql = "SELECT actor.id, actor.first_name, actor.last_name FROM actor "
				+ "JOIN film_actor ON actor.id = film_actor.actor_id "
				+ "JOIN film ON film_actor.film_id = film.id "
				+ "WHERE film.id = ?";
		
		try (Connection conn = DriverManager.getConnection(URL, user, password);
				PreparedStatement stmt = setUp(filmId, conn, sql);
				ResultSet actorResult = stmt.executeQuery();) {
			
			while (actorResult.next()) {
				actor = new Actor();
				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
				actors.add(actor);
			}
			
		} catch (
				
				SQLException e) {
			System.err.println(e);
		}
		return actors;
	}
	@Override
	public List<String> findCategoriesByFilmId(int filmId) {
		List<String> categories = new ArrayList<>();
		String user = "student";
		String password = "student";
		String sql = "SELECT category.name FROM category "
				+ "JOIN film_category ON category.id = film_category.category_id "
				+ "JOIN film ON film_category.film_id = film.id "
				+ "WHERE film.id = ?";
		
		try (Connection conn = DriverManager.getConnection(URL, user, password);
				PreparedStatement stmt = setUp(filmId, conn, sql);
				ResultSet categoryResult = stmt.executeQuery();) {
			
			while (categoryResult.next()) {
				categories.add(categoryResult.getString("name"));
			}
			
		} catch (
				
				SQLException e) {
			System.err.println(e);
		}
		return categories;
	}

	public PreparedStatement setUp(int id, Connection conn, String sql) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		return stmt;
	}

	public PreparedStatement setUp(String keyword, Connection conn, String sql) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%" + keyword + "%");
		stmt.setString(2, "%" + keyword + "%");
		return stmt;
	}

}
