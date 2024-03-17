package com.legend.common.patterns.behavior.template;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public class TemplateClient {
    public static void main(String[] args) {
        CFGame cfGame = new CFGame();
        cfGame.play();
        LOLGame lolGame = new LOLGame();
        lolGame.play();

        GTAGame gtaGame = new GTAGame();
        gtaGame.play();
    }
}
