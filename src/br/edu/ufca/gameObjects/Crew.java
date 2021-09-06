package br.edu.ufca.gameObjects;

import java.util.Random;

// import br.edu.ufca.utils.GameMath;
public class Crew {
	private static final Random RNG = new Random(); // Gerador de números aleatórios
	private static final double _WILL_FACTOR = 0.01; // Fator usado em diversos cálculos na gameplay
	public final String name;
	// Quantidade de integrantes da tripulação. É importante mantê-los alimentados
	// para evitar motins.
	private int members;
	private int captainWill; // Valor usado em potencialização de ataques.

	private int ammo; // Valor usado em ataques.
	private int food; // Mantimentos necessários para manter tripulações maiores.
	// Recursos provenientes de pilhagens, podem ser usados para obter comida,
	// munição e reparos no navio;
	private int treasures;

	// Fator que influencia na "saúde" - Zero satisfação == perda certa.
	private double satisfaction = 1.0;

	public Crew(String name) {
		this.name = name;
		this.members = 1;
		this.ammo = 0;
		this.food = 10;
		this.treasures = 1;
	}

	public Crew(String name, int ammo, int food, int treasures) {
		this.name = name;
		this.ammo = ammo;
		this.food = food;
		this.treasures = treasures;
	}

	public int getMembers() {
		return members;
	}

	public void increaseMembers(int members) {
		this.members += members;
	}

	public boolean defend(int usedAmmo, double willPower, int combo) {
		boolean wasTurnLost = false;
		int lostAmmo;
		double will = _getWillPower();
		will -= willPower * combo * _WILL_FACTOR;

		if (willPower > will) {
			lostAmmo = (int) (usedAmmo * (willPower / will));
			wasTurnLost = true;
		} else {
			lostAmmo = (int) (usedAmmo / (will / willPower));
		}

		if (lostAmmo > ammo) {
			int lostMembers = (int) Math.max(1, (lostAmmo - ammo) * _WILL_FACTOR);
			this.satisfaction -= lostMembers * _WILL_FACTOR;
		}
		this.ammo -= Math.max(0, lostAmmo);

		return wasTurnLost;
	}

	public void attack(Crew enemies) {
		// TODO
		// @laisdumont
	}

	private double _getWillPower() {
		double rawMoral = members * satisfaction;

		return rawMoral + rawMoral * captainWill * _WILL_FACTOR;
	}
}
