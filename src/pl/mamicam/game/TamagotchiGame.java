package pl.mamicam.game;

import pl.mamicam.game.controllers.GameController;

public class TamagotchiGame {
    public static void main(String[] args) {
        GameController tamagotchiGame = new GameController("Tamagotchi");
        tamagotchiGame.showInformation();
        //tamagotchiGame.addLifeInformation();
    }
}
