package com.snake.game.handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class AssetController {

    private static AssetController instance = new AssetController();
    private AssetManager assetManager = new AssetManager();

    public static final String ELEMENTS_PACK = "images/elements.pack";
    public static final String PIXEL_FONT = "fonts/pixel.ttf";

    private AssetController() {}
    public static AssetController instance() {
        return instance;
    }

    public void loadAssets() {
        loadFont();
        loadImages();
        assetManager.finishLoading();
    }

    private void loadFont() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = PIXEL_FONT;
        mySmallFont.fontParameters.size = 16;
        mySmallFont.fontParameters.color = Color.WHITE;
        mySmallFont.fontParameters.flip = true;
        assetManager.load(PIXEL_FONT, BitmapFont.class, mySmallFont);
    }

    private void loadImages() {
        assetManager.load(ELEMENTS_PACK, TextureAtlas.class);
    }

    public <T> T get(String filename) {
        return assetManager.get(filename);
    }

    public Sprite getSprite(String name) {
        TextureAtlas atlas = get(AssetController.ELEMENTS_PACK);
        return atlas.createSprite(name);
    }

    public void dispose() {
        assetManager.dispose();
    }

}
