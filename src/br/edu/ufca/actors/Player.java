package br.edu.ufca.actors;

import br.edu.ufca.gameObjects.*;

public class Player {
	private final Crew _crew;
	private int score;

	public Player(String crewName) {
		this._crew = new Crew(crewName);
	}
}
