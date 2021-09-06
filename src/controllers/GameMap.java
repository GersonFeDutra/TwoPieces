package controllers;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/*
 * Classe auxiliar do controlador `GameScene`,
 * responsável por tratar a renderização do mapa no Canvas.
 */
class GameMap {
	enum Cell {
		ISLAND, CREW
	}

	Cell[][] cellMap;

	private static final Random RNG = new Random(); // Gerador de números aleatórios
	private static final String _ASSETS_FOLDER = "assets/";
	private static final int _SPREAD = 2;
	private static final int _CELL_SIZE = 64;
	private static final int _CELL_SPACE = _CELL_SIZE + _SPREAD;

	private final GraphicsContext _context;
	private final int _grid_parts_x;
	private final int _grid_parts_y;

	private Image _spriteIsland = _loadAsset("island.png");
	private Image _spriteCrew = _loadAsset("pirate-hat.png");
	private Image _spriteIslandColored = _loadAsset("island-cyan.png");
	private Image _spriteCrewColored = _loadAsset("pirate-hat-red.png");
	private Dictionary<Cell, Image> cells = new Hashtable<Cell, Image>();
	private Dictionary<Cell, Image> cellsHighlighted = new Hashtable<Cell, Image>();

	private int _width;
	private int _height;

	private IPoint2D highlightedCell;

	GameMap(GraphicsContext gc, int width, int height) {
		this._context = gc;
		this._width = width;
		this._height = height;

		this._grid_parts_x = width / (_CELL_SIZE + _SPREAD);
		this._grid_parts_y = height / (_CELL_SIZE + _SPREAD);
		this.cellMap = new Cell[_grid_parts_x][_grid_parts_y];

		// Inicializa o dicionário de associação dos sprites com as células do mapa.
		Cell id = Cell.ISLAND;
		this.cells.put(id, _spriteIsland);
		this.cellsHighlighted.put(id, _spriteIslandColored);
		this.cellMap[2][2] = id;

		id = Cell.CREW;
		this.cells.put(id, _spriteCrew);
		this.cellsHighlighted.put(id, _spriteCrewColored);
	}

	private Image _loadAsset(String fileName) {
		return new Image("file:" + _ASSETS_FOLDER + fileName, _CELL_SIZE, 0, true, true);
	}

	private IPoint2D _mapToCoords(int x, int y) {
		return new IPoint2D(x / _CELL_SPACE % _grid_parts_x, y / _CELL_SPACE % _grid_parts_x);
	}

	Cell positionToCell(int x, int y) {
		IPoint2D cellCoords = _mapToCoords(x, y);

		return cellMap[cellCoords.x][cellCoords.y];
	}

	void setCell(int x, int y, Cell cell) {
		IPoint2D coords = _mapToCoords(x, y);

		cellMap[coords.x][coords.y] = cell;
	}

	void setCellCoords(int x, int y, Cell cell) {
		cellMap[x][y] = cell;
		update();
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	void highlightCell(int x, int y) {
		highlightedCell = _mapToCoords(x, y);
		update();
	}

	void disableHighlight() {
		highlightedCell = null;
		update();
	}

	protected void update() {
		_context.setFill(Color.LAVENDER);
		_context.fillRect(0, 0, _width, _height);

		for (int i = 0; i < _grid_parts_x; ++i)

			for (int j = 0; j < _grid_parts_y; ++j) {
				// Imprime o tabuleiro no canvas, de acordo com as células de `cellMap`.
				Cell currentCell = cellMap[i][j];

				if (currentCell != null)
					_context.drawImage(cells.get(currentCell), i * _CELL_SIZE + i * _SPREAD,
							j * _CELL_SIZE + j * _SPREAD);
			}

		if (highlightedCell != null) {
			Cell currentCell = this.cellMap[highlightedCell.x][highlightedCell.y];

			if (currentCell != null)
				_context.drawImage(cellsHighlighted.get(currentCell),
						highlightedCell.x * _CELL_SIZE + highlightedCell.x * _SPREAD,
						highlightedCell.y * _CELL_SIZE + highlightedCell.y * _SPREAD);
		}
		// System.out.println(System.getProperty("user.dir") + "/" + _ASSETS_FOLDER);
	}

	/*
	 * Sub-classe que define um tipo de dados auxiliar para tratar de coordenadas
	 * inteiras.
	 */
	class IPoint2D {
		int x;
		int y;

		IPoint2D(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {

			if (!(obj instanceof IPoint2D))
				return false;

			IPoint2D objAsPoint = (IPoint2D) obj;

			return this.x == objAsPoint.x && this.y == objAsPoint.y;
		}
	}

}
