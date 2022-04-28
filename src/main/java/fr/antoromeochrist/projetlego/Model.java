package fr.antoromeochrist.projetlego;

import fr.antoromeochrist.projetlego.pieces.Brick;
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

    public HashMap<Brick, Color> bricks;
    public Brick brickClicked;
    public ArrayList<ImageStorage> imageStorages;
    public boolean dropInProgress;
    public ArrayList<Step> instruction;

    /**
     * Cette variable va permettre de récupérer la dimension de la brique quand on clique sur une image pour
     * débuter le drag and drop dans le  <a href="#{@link}">{@link Controller}</a>.
     *
     * @see fr.antoromeochrist.projetlego.utils.bricks.Volume
     */
    public ImageStorage dropSelectionData;

    public Model() {
        bricks = new HashMap<>();
        dropInProgress = false;
        instruction = new ArrayList<>();
        imageStorages = new ArrayList<>();
        try {
            imageStorages.add(new ImageStorage("1x1", new ImagePath("1x1.png")));
            imageStorages.add(new ImageStorage("1x2", new ImagePath("1x2.png")));
            imageStorages.add(new ImageStorage("1x3", new ImagePath("1x3.png")));
            imageStorages.add(new ImageStorage("1x4", new ImagePath("1x4.png")));
            imageStorages.add(new ImageStorage("2x2", new ImagePath("2x2.png")));
            imageStorages.add(new ImageStorage("2x3", new ImagePath("2x3.png")));
            imageStorages.add(new ImageStorage("2x4", new ImagePath("2x4.png")));
            imageStorages.add(new ImageStorage("3x3", new ImagePath("3x3.png")));
            imageStorages.add(new ImageStorage("3x4", new ImagePath("3x4.png")));
            imageStorages.add(new ImageStorage("4x4", new ImagePath("4x4.png")));
            imageStorages.add(new ImageStorage("1x1x2", new ImagePath("1x1x2.png")));
            imageStorages.add(new ImageStorage("1x1x4", new ImagePath("1x1x4.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne le dictionnaire bricks à l'envers
     *
     * @return dictionnaire retourné
     */
    public HashMap<Color, ArrayList<Brick>> getReverseMap() {
        return new HashMap<>(
                bricks.entrySet().stream()
                        .collect(Collectors.groupingBy(Map.Entry::getValue)).values().stream()
                        .collect(Collectors.toMap(
                                item -> item.get(0).getValue(),
                                item -> new ArrayList<>(
                                        item.stream()
                                                .map(Map.Entry::getKey)
                                                .collect(Collectors.toList())
                                ))
                        ));
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
        return getReverseMap().get(c);
    }


}