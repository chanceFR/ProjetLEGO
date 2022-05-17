package fr.antoromeochrist.projetlego;

import fr.antoromeochrist.projetlego.pieces.Figurine;
import fr.antoromeochrist.projetlego.pieces.HatType;
import fr.antoromeochrist.projetlego.utils.bricks.Brick;
import fr.antoromeochrist.projetlego.utils.bricks.Dim;
import fr.antoromeochrist.projetlego.utils.bricks.Step;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import fr.antoromeochrist.projetlego.utils.images.ImageStorage;
import fr.antoromeochrist.projetlego.utils.print.Fast;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static fr.antoromeochrist.projetlego.Controller.controller;
import static fr.antoromeochrist.projetlego.Main.notifWindow;


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
    public File project;

    public String lastMessageNotif = "";

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
            imageStorages.add(new ImageStorage("         brick | 1x1    ", new ImagePath("1x1.png")));
            imageStorages.add(new ImageStorage("         brick | 1x2    ", new ImagePath("1x2.png")));
            imageStorages.add(new ImageStorage("         brick | 1x3    ", new ImagePath("1x3.png")));
            imageStorages.add(new ImageStorage("         brick | 1x4    ", new ImagePath("1x4.png")));
            imageStorages.add(new ImageStorage("         brick | 2x2    ", new ImagePath("2x2.png")));
            imageStorages.add(new ImageStorage("         brick | 2x3    ", new ImagePath("2x3.png")));
            imageStorages.add(new ImageStorage("         brick | 2x4    ", new ImagePath("2x4.png")));
            imageStorages.add(new ImageStorage("         brick | 3x3    ", new ImagePath("3x3.png")));
            imageStorages.add(new ImageStorage("         brick | 3x4    ", new ImagePath("3x4.png")));
            imageStorages.add(new ImageStorage("         brick | 4x4    ", new ImagePath("4x4.png")));
            imageStorages.add(new ImageStorage("         brick | 1x1x2.0", new ImagePath("1x1x2.png")));
            imageStorages.add(new ImageStorage("         brick | 1x1x4.0", new ImagePath("1x1x4.png")));
            imageStorages.add(new ImageStorage("   brick plate | 1x1x0.5", new ImagePath("1x1.png")));
            imageStorages.add(new ImageStorage("   brick plate | 1x2x0.5", new ImagePath("1x2.png")));
            imageStorages.add(new ImageStorage("   brick plate | 1x3x0.5", new ImagePath("1x3.png")));
            imageStorages.add(new ImageStorage("   brick plate | 1x4x0.5", new ImagePath("1x4.png")));
            imageStorages.add(new ImageStorage("   brick plate | 2x2x0.5", new ImagePath("2x2.png")));
            imageStorages.add(new ImageStorage("   brick plate | 2x3x0.5", new ImagePath("2x3.png")));
            imageStorages.add(new ImageStorage("   brick plate | 2x4x0.5", new ImagePath("2x4.png")));
            imageStorages.add(new ImageStorage("   brick plate | 3x3x0.5", new ImagePath("3x3.png")));
            imageStorages.add(new ImageStorage("   brick plate | 3x4x0.5", new ImagePath("3x4.png")));
            imageStorages.add(new ImageStorage("   brick plate | 4x4x0.5", new ImagePath("4x4.png")));
            imageStorages.add(new ImageStorage("      cylinder | 1x1    ", new ImagePath("1x1c.png")));
            imageStorages.add(new ImageStorage("      cylinder | 2x2    ", new ImagePath("2x2c.png")));
            imageStorages.add(new ImageStorage("      cylinder | 3x3    ", new ImagePath("3x3c.png")));
            imageStorages.add(new ImageStorage("      cylinder | 4x4    ", new ImagePath("4x4c.png")));
            imageStorages.add(new ImageStorage("cylinder plate | 1x1x0.5", new ImagePath("1x1c.png")));
            imageStorages.add(new ImageStorage("cylinder plate | 2x2x0.5", new ImagePath("2x2c.png")));
            imageStorages.add(new ImageStorage("cylinder plate | 3x3x0.5", new ImagePath("3x3c.png")));
            imageStorages.add(new ImageStorage("cylinder plate | 4x4x0.5", new ImagePath("4x4c.png")));

            //roméo
            imageStorages.add(new ImageStorage("  brick smooth | 1x1x0.5", new ImagePath("1x1x0.5s.png")));
            imageStorages.add(new ImageStorage("  brick smooth | 1x2x0.5", new ImagePath("1x2x0.5s.png")));
            imageStorages.add(new ImageStorage("  brick smooth | 1x3x0.5", new ImagePath("1x3x0.5s.png")));
            imageStorages.add(new ImageStorage("  brick smooth | 1x4x0.5", new ImagePath("1x4x0.5s.png")));
            imageStorages.add(new ImageStorage("  brick smooth | 2x2x0.5", new ImagePath("2x2x0.5s.png")));
            imageStorages.add(new ImageStorage("  brick smooth | 2x3x0.5", new ImagePath("2x3x0.5s.png")));
            imageStorages.add(new ImageStorage("  brick smooth | 2x4x0.5", new ImagePath("2x4x0.5s.png")));
            imageStorages.add(new ImageStorage("  brick smooth | 3x3x0.5", new ImagePath("3x3x0.5s.png")));
            imageStorages.add(new ImageStorage("  brick smooth | 3x4x0.5", new ImagePath("3x3x0.5s.png")));
            imageStorages.add(new ImageStorage("  brick smooth | 4x4x0.5", new ImagePath("4x4x0.5s.png")));

            imageStorages.add(new ImageStorage("cylinder smooth | 1x1x0.5", new ImagePath("1x1x0.5cs.png")));
            imageStorages.add(new ImageStorage("cylinder smooth | 2x2x0.5", new ImagePath("2x2x0.5cs.png")));
            imageStorages.add(new ImageStorage("cylinder smooth | 3x3x0.5", new ImagePath("3x3x0.5cs.png")));
            imageStorages.add(new ImageStorage("cylinder smooth | 4x4x0.5", new ImagePath("4x4x0.5cs.png")));

            imageStorages.add(new ImageStorage("      Figurine | 4x1x5  ", new ImagePath("Figurine.png")));
            //roméo

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

    public File selectOpenProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Project");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BRICKDESIGNER", "*.bd"));
        return fileChooser.showOpenDialog(Main.software);
    }

    public File selectSaveProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Project");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BRICKDESIGNER", "*.bd"));
        return fileChooser.showSaveDialog(Main.software);
    }

    private int getSizeOfStep(ArrayList<String> lines) {
        int i = 0;
        for (String s : lines)
            if (s.startsWith("step")) i++;
        return i;
    }


    public void loadData() {
        if (project == null) {
            project = selectOpenProject();
            if (project == null) {
                sendNotif("Vous n'avez pas sélectionné un projet à ouvrir !");
                return;
            }
        } else {
            File temp = selectOpenProject();
            if (temp == null) {
                sendNotif("Vous n'avez pas sélectionné un projet à ouvrir !");
                return;
            } else {
                try {
                    saveAllData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                controller.reset();
                project = temp;
                sendNotif("L'ancien projet a été sauvegardé avec succès.");
            }
        }
        Scanner obj = null;
        try {
            obj = new Scanner(project);
        } catch (FileNotFoundException ignored) {
        }

        ArrayList<String> lines = new ArrayList<>();
        //récupération des lignes
        while (obj.hasNextLine()) lines.add(obj.nextLine());
        for (String line : lines) {
            Fast.log("Ligne récupéré: " + line);
        }


        Fast.log("Première brique: ");

        //première brique
        String idPre = "0";
        String[] dt0 = lines.get(getSizeOfStep(lines)).split("\\:");

        boolean piece0 = dt0[1].equals("figurine");
        Fast.log("> piece: " + piece0);
        String coor0 = dt0[2];
        //
        String xSt0 = "";
        int i3_0 = 1;
        while (coor0.toCharArray()[i3_0] != ';') {
            xSt0 += coor0.toCharArray()[i3_0];
            i3_0++;
        }
        int x0 = Integer.parseInt(xSt0);
        String zSt0 = "";
        int i4_0 = coor0.length() - 2;
        while (coor0.toCharArray()[i4_0] != ';') {
            zSt0 += coor0.toCharArray()[i4_0];
            i4_0--;
        }
        zSt0 = new StringBuilder(zSt0).reverse().toString();
        int z0 = Integer.parseInt(zSt0);
        String h0 = "";
        int i0 = 0;
        while (coor0.toCharArray()[i0] != ';') {
            i0++;
        }
        while (coor0.toCharArray()[i0 + 1] != ';') {
            i0++;
            h0 += coor0.toCharArray()[i0];
        }
        Fast.log("> x :" + x0 + " y:" + h0 + " z:" + z0);

        if (piece0) {
            Fast.log("Processus de création de la figurine en cours...");
            Figurine f = new Figurine(x0, Double.parseDouble(h0), z0);
            f.hide(Boolean.parseBoolean(dt0[4]));
            f.updateDisplay();
            f.setHat(HatType.valueOf(dt0[3]));
        } else {
            Dim dim = Dim.convertToDim(dt0[3]);
            Fast.log("> Dim :" + dim);
            Fast.log("> Color: " + Color.valueOf(dt0[8]));
            Fast.log("> Plate: " + (dim.getHeight() == 0.5));
            Fast.log("> Cylindrical: " + Boolean.parseBoolean(dt0[5]));
            Fast.log("> Smooth: " + Boolean.parseBoolean(dt0[6]));
            Fast.log("> Hide: " + Boolean.parseBoolean(dt0[7]));

            Brick b = new Brick(dim, x0, Double.parseDouble(h0), z0, Color.valueOf(dt0[8]), (dim.getHeight() == 0.5), false);
            b.setCylindrical(Boolean.parseBoolean(dt0[5]), 678);
            b.setSmooth(Boolean.parseBoolean(dt0[6]), 679);
            b.hide(Boolean.parseBoolean(dt0[7]));
        }

        ArrayList<String> stepNames = new ArrayList();
        for (int i = 0; i < getSizeOfStep(lines); i++) {
            stepNames.add(lines.get(i).split("\\:")[2]);
        }
        Fast.log("Step names: " + stepNames);

        instruction.get(0).setName(stepNames.get(0), 345);
        int stepDiffrent = 0;
        for (int i = getSizeOfStep(lines) + 1; i < lines.size(); i++) {
            String[] dt = lines.get(i).split("\\:");
            if (!idPre.equals(dt[0])) {
                stepDiffrent++;
                Fast.log("On ajoute une nouvelle étape");
                ListView nL = new ListView();
                instruction.add(new Step(stepNames.get(stepDiffrent), nL));
                controller.steps.getItems().add(nL);
                controller.currentStep = nL;
                idPre = dt[0];
            }
            Fast.log("Nouvelle Brick");
            boolean piece = dt[1].equals("figurine");
            Fast.log("> piece: " + piece);
            String coor = dt[2];
            //
            String xSt = "";
            int i3_ = 1;
            while (coor.toCharArray()[i3_] != ';') {
                xSt += coor.toCharArray()[i3_];
                i3_++;
            }
            int x = Integer.parseInt(xSt);
            String zSt = "";
            int i4_ = coor.length() - 2;
            while (coor.toCharArray()[i4_] != ';') {
                zSt += coor.toCharArray()[i4_];
                i4_--;
            }
            zSt = new StringBuilder(zSt).reverse().toString();
            int z = Integer.parseInt(zSt);

            String h = "";
            int i1 = 0;
            while (coor.toCharArray()[i1] != ';') {
                i1++;
            }
            while (coor.toCharArray()[i1 + 1] != ';') {
                i1++;
                h += coor.toCharArray()[i1];
            }
            Fast.log("> x :" + x + " y:" + h + " z:" + z);
            if (piece) {
                Figurine f = new Figurine(x, Double.parseDouble(h), z);
                f.hide(Boolean.parseBoolean(dt[4]));
                f.updateDisplay();
                f.setHat(HatType.valueOf(dt[3]));
            } else {
                Dim dim = Dim.convertToDim(dt[3]);
                Fast.log("> Dim :" + dim);
                Fast.log("> Color: " + Color.valueOf(dt[8]));
                Fast.log("> Plate: " + (dim.getHeight() == 0.5));
                Fast.log("> Cylindrical: " + Boolean.parseBoolean(dt[5]));
                Fast.log("> Smooth: " + Boolean.parseBoolean(dt[6]));
                Fast.log("> Hide: " + Boolean.parseBoolean(dt[7]));
                Brick b = new Brick(dim, x, Double.parseDouble(h), z, Color.valueOf(dt[8]), (dim.getHeight() == 0.5), false);
                b.setCylindrical(Boolean.parseBoolean(dt[5]), 678);
                b.setSmooth(Boolean.parseBoolean(dt[6]), 679);
                b.hide(Boolean.parseBoolean(dt[7]));
            }
        }
    }


    public String getData() {
        ArrayList<String> lines = new ArrayList<>();
        int i = 0;
        for (Step step : instruction) {
            lines.add("step:" + i + ":" + step.getName());
            i++;
        }
        Fast.log("steps size: " + instruction.size());
        for (int i2 = 0; i2 < instruction.size(); i2++) {
            Fast.log("étape actuelle: " + instruction.get(i2).getName());
            for (Brick b : ((ListView<Brick>) controller.steps.getItems().get(i2)).getItems()) {
                Fast.log("current step size: " + instruction.get(i2).getBricks().getItems().size());
                if (b.getPieceType().equals("Figurine")) {
                    Figurine f = (Figurine) b;
                    String str = i2 + ":figurine:" + b.getVolume().get(0) + ":" + f.getHat() + ":" + f.isHide();
                    Fast.log(str);
                    lines.add(str);
                } else {
                    String str = i2 + ":brick:" + b.getVolume().get(0) + ":" + b.getDim() + ":" + b.isPlate() + ":" + b.isCylindrical() + ":" + b.isSmooth() + ":" + b.isHide() + ":" + b.getColor();
                    Fast.log(str);
                    lines.add(str);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String line : lines) sb.append(line + "\n");
        return sb.toString();
    }


    private double xOffsetNW = 0;
    private double yOffsetNW = 0;

    public void sendNotif(String message) {
        lastMessageNotif = message;
        notifWindow = new Stage();
        Parent pNotifWindow = null;
        try {
            pNotifWindow = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dsview.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        notifWindow.setScene(new Scene(pNotifWindow));
        pNotifWindow.setOnMousePressed(event -> {
            xOffsetNW = event.getSceneX();
            yOffsetNW = event.getSceneY();
        });
        pNotifWindow.setOnMouseDragged(event -> {
            notifWindow.setX(event.getScreenX() - xOffsetNW);
            notifWindow.setY(event.getScreenY() - yOffsetNW);
        });
        notifWindow.setTitle("Enregistrement échoué");
        notifWindow.setMinWidth(387);
        notifWindow.setMinHeight(127);
        notifWindow.setMaxWidth(387);
        notifWindow.setMaxHeight(127);
        notifWindow.initStyle(StageStyle.UNDECORATED);
        notifWindow.show();
    }

    public void saveAllData() throws IOException {
        String data = getData();
        if (project == null) {
            Fast.log("En attente de sélection de fichier");
            project = selectSaveProject();
            if (project == null) {
                sendNotif("Vous n'avez pas sélectionné de fichier\n pour sauvegarder votre projet.");
                return;
            }
        }
        System.out.println("Ecriture dans le fichier en cours...");
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(project.getAbsolutePath());
            fWriter.write(data);
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}