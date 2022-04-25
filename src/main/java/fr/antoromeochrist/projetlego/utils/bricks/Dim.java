package fr.antoromeochrist.projetlego.utils.bricks;
/*
                                         #@&@&*
                              ,&@@@@@#..@@@&.        @
                             @          .  @@@@.  @ @  @
                             @          , @ @   **  ,  @
                             @         / @ @  ,  @ @ ,&
                             @         (  .%@&       @
                             @         #             @
                             @         %  .@@@   & @  @              /@&%%@@(
                             @         % @ /   #*     @        @@/,,#@@      @          @
                             @         & @ &   / @ @ &,      @@         .@@.    #@      @@@*
                             @         @  %@@@       @          ,&@@@&,  @       &  &@     @
                             @         @          &@@@&                         @          @
                             @         @  %@@@/  @ %   @                        @          @
                             @         @ @ @   % @ %   (                        @          @@
                  @@(    @@/ .&&*      @ @ @   ,   @@@                          @          @@ @@@
         @      &%@@.    (@ &@@@@@,@  .@#/.&@@       @  *@%**(@@(               @        *@*    @
    @@* (%      (/@@@@@@@@ @@@&&@@@..&@@@@@*@        @@@.       @@/@/      &.   @    @%         @
    @      .@@   @@.   /@@@@@&&&@@@ @@@&&@@@( &@@@@@@@@@@@.        @        @@@@@@              @
    @              %@/    @@*. .*@@ @@&/**#@@#@&,  ./@@   @@      (@@%     ,@                   @
    @                      @@       #(     .@*     @@      @                                    @
    @                             #@#       &@.            @                                @.
    @                                   @                  @                            @,
     &@                                 @                  @                        @(
           ,@/                          @                  @                    @&
                  @@                    @                  @    .@@%        @@
                        @@              @            .@,
                              *@*       @       @%
                                     @@ @ @@

*/

/**
 * L'objet dimension a trois attributs.
 *
 * <p>
 * Ordre des attributs dans le contructeur: largeur x profondeur x hauteur
 * <p>
 * Règle: quand hauteur = 1 (on le note pas)
 * <p>
 * Exemples:
 * <p>
 * - 1X2
 * <p>
 * Exemples 2:
 * <p>
 * - 2x1x2
 * <p>
 * Cette classe permet de générer le volume nécessaire
 * <p><
 * dont aura besoin une brique lors de sa création/déplacement.
 *
 * @see fr.antoromeochrist.projetlego.utils.bricks.Brick
 */
public class Dim {

    /**
     * attributs
     */
    private int width, height, depth;

    /**
     * Constructeur sans préciser la hauteur
     * <p>
     * Hauteur à 1 par défaut
     *
     * @param width
     * @param depth
     */
    public Dim(int width, int depth) {
        this.width = width;
        this.height = 1;
        this.depth = depth;
    }

    /**
     * Constructeur complet pour générer une dimension
     *
     * @param width
     * @param depth
     * @param height
     */
    public Dim(int width, int depth, int height) {
        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    /**
     * Retourne un objet dimension à partir d'un texte
     *
     * @param text
     * @return objet de type dimension
     */
    public static Dim getDimWithText(String text) {
        String[] s = text.replace(" ", "").split("x");
        if (s.length == 3) {
            return new Dim(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
        } else {
            return new Dim(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        }
    }

    /**
     * Avoir la largeur
     *
     * @return width
     */
    public int getWeight() {
        return width;
    }

    /**
     * Avoir la hauteur
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Avoir la profondeur
     *
     * @return depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Affichage de la dimension
     *
     * @return
     */
    public String toString() {
        String s = "" + width;
        s += "x" + depth;
        if (height != 1) {
            s += "x" + height;
        }
        return s;
    }

}