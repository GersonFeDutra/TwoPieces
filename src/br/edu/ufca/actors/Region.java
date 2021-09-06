package br.edu.ufca.actors;

import java.util.ArrayList;
import java.util.Random;
import br.edu.ufca.gameObjects.*;

/* 
 * CRUD que serve de fachada para o "mundo" do jogo.
 */
public class Region {
	private static final Random _RNG = new Random();
	private static final double _EDGES_INFLUENCE_FACTOR = 0.3;
	private static final double _ISLAND_SPAWN_FACTOR = 0.2;
	private static final double _ISLAND_NEIGHBOR_INFLUENCE = 0.04;
	private static final double _CREW_SPAWN_FACTOR = 0.3;
	private static final double _CREW_NEIGHBOR_INFLUENCE = 0.02;
	static double twoPiecesSpawnFactor = 0.0;
	// Os fatores aleatórios acima influenciam na crianção do mapa.
	// A soma dos fatores deve ser <= 1.0.
	// Os valores de influência reduzem esses fatores condicionalmente.
	private Object[][] chunk; // Dados da região

	public interface MapI {
		void useData(Object xy, int x_coord, int y_coord);
	}

	/*
	 * Retorna o objeto armazenado em dada coordenada da região. Retorna null para
	 * coordenadas vazias.
	 */
	public Object getObjectAt(int x, int y) {
		return chunk[x][y];
	}

	/*
	 * Recebe a interface de função MapI (usualmente como uma expressão lambda) para
	 * mapear os elementos e utilizar os seus dados coordenada-a-coordenada.
	 */
	public void iterate(MapI func) {

		for (int i = 0; i < chunk.length; ++i)
			for (int j = 0; j < chunk[i].length; ++j)
				func.useData(chunk[i][j], i, j);
	}

	/*
	 * Realiza operações paralelas nas entidades da região, para dar impressão de
	 * "vida" ao 'mundo'.
	 */
	public void updateChunk() {

		for (Object[] objects : chunk)
			for (Object object : objects)

				if (object == null)
					continue;
				else if (object instanceof NPC) {
					// TODO
					// @laisdumont
				} else if (object instanceof Island) {
					// @laisdumont
				}
	}

	private boolean isOnBounds(int x, int y, int xLimit, int yLimit) {
		return x >= 0 && x < xLimit && y >= 0 && y < yLimit;
	}

	/* Gera um novo mapa, com base em alguns fatores de randomização. */
	public void generateChunk(int width, int height) {
		boolean twoPiecesSpawned = false;
		chunk = new Object[width][height];

		for (int i = 0; i < width; ++i)

			for (int j = 0; j < height; ++j) {
				double randomFactor = _RNG.nextDouble()
						+ (isOnBounds(i, j, width, height) ? 0.0 : _EDGES_INFLUENCE_FACTOR);
				double islandSpawnFactor = _ISLAND_SPAWN_FACTOR;
				double crewSpawnFactor = _CREW_SPAWN_FACTOR;

				ArrayList<Object> neighbors = new ArrayList<Object>();
				int neighborDirections[] = { -1, 0, 1 };

				// Adiciona os coordenadas vizinhas
				for (int k : neighborDirections)
					for (int l : neighborDirections) {
						if (k == 0 && l == 0)
							continue;

						int x = i + k, y = j + l;
						if (isOnBounds(x, y, width, height) && chunk[x][y] != null)
							neighbors.add(chunk[x][y]);
					}

				// Verifica as células vizinhas para adicionar influência
				for (Object neighbor : neighbors)
					if (neighbor instanceof Island) {
						islandSpawnFactor -= _ISLAND_NEIGHBOR_INFLUENCE;
						crewSpawnFactor -= _ISLAND_SPAWN_FACTOR * 2.0;
					} else if (neighbor instanceof Crew) {
						crewSpawnFactor -= _CREW_NEIGHBOR_INFLUENCE;
					}

				if (randomFactor < islandSpawnFactor) {
					chunk[i][j] = new Island();
					continue;
				}
				randomFactor -= _ISLAND_SPAWN_FACTOR;

				if (randomFactor < crewSpawnFactor) {
					chunk[i][j] = new Crew("name", 10, 5, 10);
					continue;
				}
				randomFactor -= _CREW_SPAWN_FACTOR;

				if (randomFactor < twoPiecesSpawnFactor && !twoPiecesSpawned)
					chunk[i][j] = new Island(); // @laisdumont
			}
	}
}
