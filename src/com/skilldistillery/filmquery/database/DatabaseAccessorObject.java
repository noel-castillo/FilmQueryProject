package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String sql = "SELECT * FROM film WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, user, password);
				PreparedStatement stmt = setUp(filmId, conn, sql);
				ResultSet rs = stmt.executeQuery();) {

			if (rs.next()) {
				film = new Film();
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setRentalDuration(rs.getInt("rental_duration"));
				film.setRentalRate(rs.getDouble("rental_rate"));
				film.setLength(rs.getInt("length"));
				film.setReplacementCost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("special_features"));
			}

			rs.close();

		} catch (SQLException e) {
			System.err.println(e);
		}
		return film;
	}

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

	public PreparedStatement setUp(int id, Connection conn, String sql) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		return stmt;
	}

	public List<Actor> findActorsByFilmId(int filmId) {

		return null;
	}

}
