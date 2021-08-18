package controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameMap {
	private static final String _ASSETS_FOLDER = "assets/";
	private static final int _SPREAD = 2;
	private static final int _CELL_SIZE = 64;

	private Image _spriteIsland = new Image("file:" + _ASSETS_FOLDER + "island.png", _CELL_SIZE, 0, true, true);
	// Image _sprite = new Image("file:" + _ASSETS_FOLDER + ".png");
	private final GraphicsContext _context;
	private int _width;
	private int _height;

	public GameMap(GraphicsContext gc, int width, int height) {
		this._context = gc;
		this._width = width;
		this._height = height;
	}

	public void update() {
		_context.setFill(Color.LAVENDER);
		_context.fillRect(0, 0, _width, _height);

		for (int i = 0; i < 6; ++i) {

			for (int j = 0; j < 6; ++j) {
				_context.drawImage(_spriteIsland, i * _CELL_SIZE + i * _SPREAD, j * _CELL_SIZE + j * _SPREAD);
			}
		}

		// System.out.println(System.getProperty("user.dir") + "/" + _ASSETS_FOLDER);
	}

}
