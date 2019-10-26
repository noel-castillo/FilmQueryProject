package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {

		int userSelection = 0;
		Film film = null;
		List<Film> films = new ArrayList<>();
		while (true) {
			System.out.println("1. Look up a film by its id.");
			System.out.println("2. Look up a film by search keyword.");
			System.out.println("0. Exit the application.");

			userSelection = input.nextInt();

			switch (userSelection) {
			case 1:
				System.out.print("Enter film id >> ");
				int filmId = input.nextInt();
				film = db.findFilmById(filmId);
				if (film == null) {
					System.out.println("Film not found.");
				} else {
					System.out.println(film);
					subMenu(input, film);
				}
				break;
			case 2:
				System.out.print("Enter search keyword >> ");
				String keyword = input.next();
				films = db.findFilmByKeyword(keyword);
				if (films.size() == 0) {
					System.out.println("No film found.");
				} else {
					System.out.println("Search Results:");
					int counter = 1;
					for (Film element : films) {
						System.out.println(counter++ + ". " + element);
					}
					System.out.println();
					subMenu(input, films);
				}
				break;
			case 0:
				System.out.println("Goodbye");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid input.");
				break;
			}
		}
	}

	public void subMenu(Scanner input, Film film) {

		while (true) {
			System.out.println("1. Return to the main menu.");
			System.out.println("2. View all film details.");
			System.out.print(">> ");
			int userSelection = input.nextInt();
			switch (userSelection) {
			case 1:
				startUserInterface(input);
				break;
			case 2:
				System.out.println(film.toStringFull());
				break;
			default:
				System.out.println("Invalid input.");
				break;
			}
		}
	}
	public void subMenu(Scanner input, List<Film> films) {
		
		while (true) {
			System.out.println("1. Return to the main menu.");
			System.out.println("2. View all film details.");
			System.out.print(">> ");
			int userSelection = input.nextInt();
			switch (userSelection) {
			case 1:
				startUserInterface(input);
				break;
			case 2:
				int counter = 1;
				for (Film element : films) {
					System.out.println(counter++ + ". " + element.toStringFull());
				}
				break;
			default:
				System.out.println("Invalid input.");
				break;
			}
		}
	}

}
