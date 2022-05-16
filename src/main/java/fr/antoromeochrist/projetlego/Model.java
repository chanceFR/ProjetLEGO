package fr.antoromeochrist.projetlego;

import fr.antoromeochrist.projetlego.utils.bricks.Brick;
import fr.antoromeochrist.projetlego.utils.bricks.Step;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import fr.antoromeochrist.projetlego.utils.images.ImageStorage;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Model {

    public ArrayList<Brick> bricks;
    public Brick brickClicked;
    public ArrayList<ImageStorage> imageStorages;
    public boolean dropInProgress;
    public ArrayList<Step> instruction;
    public boolean ctrlActive;
    public boolean rightClickActive;
    public double mouseX;
    public double mouseY;
    public float oldAngleX;
    public float oldAngleY;

    /**
     * Cette variable va permettre de récupérer la dimension de la brique quand on clique sur une image pour
     * débuter le drag and drop dans le  <a href="#{@link}">{@link Controller}</a>.
     *
     * @see fr.antoromeochrist.projetlego.utils.bricks.Volume
     */
    public ImageStorage dropSelectionData;

    public Model() {
        mouseX = 0;
        mouseY = 0;
        rightClickActive = false;
        ctrlActive = false;
        bricks = new ArrayList<>();
        dropInProgress = false;
        instruction = new ArrayList<>();
        imageStorages = new ArrayList<>();
        try {
            imageStorages.add(new ImageStorage("brick | 1x1", new ImagePath("1x1.png")));
            imageStorages.add(new ImageStorage("brick | 1x2", new ImagePath("1x2.png")));
            imageStorages.add(new ImageStorage("brick | 1x3", new ImagePath("1x3.png")));
            imageStorages.add(new ImageStorage("brick | 1x4", new ImagePath("1x4.png")));
            imageStorages.add(new ImageStorage("brick | 2x2", new ImagePath("2x2.png")));
            imageStorages.add(new ImageStorage("brick | 2x3", new ImagePath("2x3.png")));
            imageStorages.add(new ImageStorage("brick | 2x4", new ImagePath("2x4.png")));
            imageStorages.add(new ImageStorage("brick | 3x3", new ImagePath("3x3.png")));
            imageStorages.add(new ImageStorage("brick | 3x4", new ImagePath("3x4.png")));
            imageStorages.add(new ImageStorage("brick | 4x4", new ImagePath("4x4.png")));
            imageStorages.add(new ImageStorage("brick | 1x1x2", new ImagePath("1x1x2.png")));
            imageStorages.add(new ImageStorage("brick | 1x1x4", new ImagePath("1x1x4.png")));

            imageStorages.add(new ImageStorage("brick plate | 1x1x0.5", new ImagePath("1x1.png")));
            imageStorages.add(new ImageStorage("brick plate | 1x2x0.5", new ImagePath("1x2.png")));
            imageStorages.add(new ImageStorage("brick plate | 1x3x0.5", new ImagePath("1x3.png")));
            imageStorages.add(new ImageStorage("brick plate | 1x4x0.5", new ImagePath("1x4.png")));
            imageStorages.add(new ImageStorage("brick plate | 2x2x0.5", new ImagePath("2x2.png")));
            imageStorages.add(new ImageStorage("brick plate | 2x3x0.5", new ImagePath("2x3.png")));
            imageStorages.add(new ImageStorage("brick plate | 2x4x0.5", new ImagePath("2x4.png")));
            imageStorages.add(new ImageStorage("brick plate | 3x3x0.5", new ImagePath("3x3.png")));
            imageStorages.add(new ImageStorage("brick plate | 3x4x0.5", new ImagePath("3x4.png")));
            imageStorages.add(new ImageStorage("brick plate | 4x4x0.5", new ImagePath("4x4.png")));

            imageStorages.add(new ImageStorage("cylinder | 1x1", new ImagePath("1x1.png")));
            imageStorages.add(new ImageStorage("cylinder | 2x2", new ImagePath("2x2.png")));
            imageStorages.add(new ImageStorage("cylinder | 3x3", new ImagePath("3x3.png")));
            imageStorages.add(new ImageStorage("cylinder | 4x4", new ImagePath("4x4.png")));
            imageStorages.add(new ImageStorage("cylinder plate | 1x1x0.5", new ImagePath("1x1.png")));
            imageStorages.add(new ImageStorage("cylinder plate | 2x2x0.5", new ImagePath("2x2.png")));
            imageStorages.add(new ImageStorage("cylinder plate | 3x3x0.5", new ImagePath("3x3.png")));
            imageStorages.add(new ImageStorage("cylinder plate | 4x4x0.5", new ImagePath("4x4.png")));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne la liste des briques qui ont la même couleur
     * <p>
     * avec la couleur.
     *
     * @param c la couleur
     * @return une liste
     */
    public ArrayList<Brick> getBrickWithColor(Color c) {
        ArrayList<Brick> bs = new ArrayList<>();
        for (Brick b : this.bricks) {
            if (b.getColor().equals(c)) {
                bs.add(b);
            }
        }
        return bs;
    }


}