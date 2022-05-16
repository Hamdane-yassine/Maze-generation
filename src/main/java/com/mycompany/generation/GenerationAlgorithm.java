package com.mycompany.generation;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;

public abstract class GenerationAlgorithm {

    public static final String RECURSIVE = """
                                           Le backtracking r\u00e9cursif est un algorithme pour r\u00e9soudre et g\u00e9n\u00e9rer des labyrinthes parfaits. Et c'est une version al\u00e9atoire de l'algorithme de recherche en profondeur d'abord, cet algorithme est rapide, facile \u00e0 comprendre et simple \u00e0 mettre en \u0153uvre.
                                           
                                           Consid\u00e9rez l'espace d'un labyrinthe comme une grande grille de cellules (comme un grand \u00e9chiquier), chaque cellule commen\u00e7ant par quatre murs. A partir d'une cellule al\u00e9atoire, l'ordinateur s\u00e9lectionne alors une cellule voisine al\u00e9atoire qui n'a pas encore \u00e9t\u00e9 visit\u00e9e. L'ordinateur supprime le mur entre les deux cellules et marque la nouvelle cellule comme visit\u00e9e, et l'ajoute \u00e0 la pile pour faciliter le retour en arri\u00e8re. L'ordinateur poursuit ce processus, une cellule qui n'a pas de voisins non visit\u00e9s \u00e9tant consid\u00e9r\u00e9e comme une impasse. Lorsqu'il se trouve dans une impasse, il revient en arri\u00e8re sur le chemin jusqu'\u00e0 ce qu'il atteigne une cellule avec un voisin non visit\u00e9, poursuivant la g\u00e9n\u00e9ration du chemin en visitant cette nouvelle cellule non visit\u00e9e (cr\u00e9ant une nouvelle jonction). Ce processus se poursuit jusqu'\u00e0 ce que chaque cellule ait \u00e9t\u00e9 visit\u00e9e, obligeant l'ordinateur \u00e0 revenir en arri\u00e8re jusqu'\u00e0 la cellule de d\u00e9part. Nous pouvons \u00eatre s\u00fbrs que chaque cellule est visit\u00e9e. 
                                           
                                           Les labyrinthes g\u00e9n\u00e9r\u00e9s avec une recherche en profondeur d'abord ont un faible facteur de ramification et contiennent de nombreux longs couloirs, car l'algorithme explore aussi loin que possible le long de chaque branche avant de revenir en arri\u00e8re.
                                           
                                           L'algorithme est :
                                           1. Choisissez un point de d\u00e9part dans le champ.
                                           2. Choisissez au hasard un mur \u00e0 cet endroit et creusez un passage jusqu'\u00e0 la cellule adjacente, mais seulement si la cellule adjacente n'a pas encore \u00e9t\u00e9 visit\u00e9e. Cela devient la nouvelle cellule courante.
                                           3. Si toutes les cellules adjacentes ont \u00e9t\u00e9 visit\u00e9es, revenez \u00e0 la derni\u00e8re cellule dont les murs ne sont pas sculpt\u00e9s et r\u00e9p\u00e9tez.
                                           4. L'algorithme se termine lorsque le processus a recul\u00e9 jusqu'au point de d\u00e9part.
                                           """;
    public static final String KRUSKAL = """
                                         L'algorithme de Kruskal a \u00e9t\u00e9 d\u00e9velopp\u00e9 par le math\u00e9maticien et informaticien Joseph Kruskal en 1956 pour construire ce qu'on appelle des arbres couvrant minimum. \u00c9tant donn\u00e9 un graphe connexe et non orient\u00e9, un arbre couvrant de ce graphe est un sous-graphe qui est un arbre et relie tous les sommets ensemble. Un m\u00eame graphe peut avoir plusieurs arbres couvrants diff\u00e9rents. Un arbre couvrant minimum (MST Minimum Spanning Tree) ou un arbre couvrant de poids minimum pour un graphe pond\u00e9r\u00e9, connect\u00e9 et non orient\u00e9 est un arbre couvrant avec un poids inf\u00e9rieur ou \u00e9gal au poids de tous les autres arbres couvrants.
                                         
                                         Pour transformer une grille en labyrinthe en utilisant l'algorithme de Kruskal, nous devrions d'abord attribuer des poids al\u00e9atoires \u00e0 tous les passages possibles, avant de pouvoir les s\u00e9lectionner dans l'ordre de ces poids. C'est un peu redondant. Il est en fait beaucoup plus facile de simplement mettre tous les passages dans une grande liste et de les s\u00e9lectionner au hasard dans la liste. Ce petit changement convertit l'algorithme de Kruskal en algorithme de Kruskal al\u00e9atoire.
                                         
                                         L'algorithme est :
                                         1. Affectez chaque cellule \u00e0 son propre ensemble.
                                         2. Choisissez la paire de cellules voisines avec le passage le moins cher entre elles.
                                         3. Si les deux cellules appartiennent \u00e0 des ensembles diff\u00e9rents, fusionnez-les.
                                         4. R\u00e9p\u00e9tez 2 et 3 jusqu'\u00e0 ce qu'il ne reste qu'une seule s\u00e9rie.
                                         """;
    public static final String ALDOUS = """
                                        L'algorithme Aldous-Broder a \u00e9t\u00e9 d\u00e9velopp\u00e9 ind\u00e9pendamment par David Aldous, professeur \u00e0 l'UC Berkeley, et Andrei Broder, actuellement chercheur \u00e9m\u00e9rite chez Google. D. Aldous et A. Broder \u00e9taient deux chercheurs, travaillant ind\u00e9pendamment, qui enqu\u00eataient sur des arbres \u00e0 port\u00e9e uniforme. Un arbre couvrant uniforme est un arbre choisi au hasard (et avec une probabilit\u00e9 \u00e9gale) parmi tous les arbres couvrants possibles d'un graphe donn\u00e9. L'algorithme AldousBroder fournit un moyen d'\u00e9chantillonner un arbre couvrant uniform\u00e9ment al\u00e9atoire (labyrinthe parfait dans notre cas) pour les graphes connect\u00e9s finis en utilisant une marche al\u00e9atoire simple.
                                        
                                        L'algorithme est :
                                        1. Choisissez une cellule al\u00e9atoire comme cellule actuelle et marquez-la comme visit\u00e9e.
                                        2. Tant qu'il y a des cellules non visit\u00e9es :
                                        \t1. Choisissez un voisin au hasard.
                                        \t2. Si le voisin choisi n'a pas \u00e9t\u00e9 visit\u00e9 :
                                        \t\t1. Enlevez le mur entre la cellule actuelle et la voisine choisie.
                                        \t\t2. Marquez le voisin choisi comme visit\u00e9.
                                        3. Faites du voisin choisi la cellule actuelle.""";
    public static final String SIMPLEPRIM = """
                                            L'algorithme de Prim a \u00e9t\u00e9 d\u00e9velopp\u00e9 pour la premi\u00e8re fois en 1930 par un math\u00e9maticien tch\u00e8que nomm\u00e9 Vojt\u011bch Jarn\u00edk, mais il tire son nom de Robert C. Prim, un informaticien qui l'a red\u00e9couvert ind\u00e9pendamment en 1957. C'est un algorithme gourmand qui trouve un arbre couvrant minimum pour un poids graphe non orient\u00e9. Cela signifie qu'il trouve un sous ensemble des ar\u00eates qui forme un arbre qui inclut chaque sommet, o\u00f9 le poids total de toutes les ar\u00eates de l'arbre est minimis\u00e9. L'algorithme fonctionne en construisant cet arbre un sommet \u00e0 la fois, \u00e0 partir d'un sommet de d\u00e9part arbitraire, \u00e0 chaque \u00e9tape en ajoutant la connexion la moins ch\u00e8re possible de l'arbre \u00e0 un autre sommet.
                                            
                                            Les versions simplifi\u00e9es de l'algorithme de Prim sont assez courantes parmi les g\u00e9n\u00e9rateurs de labyrinthe et sont g\u00e9n\u00e9ralement simplement appel\u00e9es "algorithme de Prim" par de nombreuses sources. Cependant, contrairement au v\u00e9ritable algorithme, ces versions simplifi\u00e9es ne s'embarrassent pas de co\u00fbts et de poids diff\u00e9rents pour les passages. En fait, ces algorithmes ont tendance \u00e0 \u00eatre similaires \u00e0 ce que vous obtiendriez si vous ex\u00e9cutiez le v\u00e9ritable algorithme de Prim sur une grille dans laquelle chaque passage avait le m\u00eame co\u00fbt.
                                            
                                            L'algorithme est :
                                            1. Initialiser un ensemble avec une cellule arbitraire.
                                            2. Choisissez au hasard une cellule de l'ensemble ,s'il n'a pas de voisins non visit\u00e9s, supprimez-le de l'ensemble.
                                            3. Sinon, choisissez l'un des voisins non visit\u00e9s de la cellule, liez les deux ensembles et ajoutez le voisin \u00e0 l'ensemble.
                                            4. R\u00e9p\u00e9tez jusqu'\u00e0 ce que l'ensemble soit vide.
                                            """;
    public static final String TRUEPRIM = """
                                          L'algorithme de Prim a \u00e9t\u00e9 d\u00e9velopp\u00e9 pour la premi\u00e8re fois en 1930 par un math\u00e9maticien tch\u00e8que nomm\u00e9 Vojt\u011bch Jarn\u00edk, mais il tire son nom de Robert C. Prim, un informaticien qui l'a red\u00e9couvert ind\u00e9pendamment en 1957. C'est un algorithme gourmand qui trouve un arbre couvrant minimum pour un poids graphe non orient\u00e9. Cela signifie qu'il trouve un sous ensemble des ar\u00eates qui forme un arbre qui inclut chaque sommet, o\u00f9 le poids total de toutes les ar\u00eates de l'arbre est minimis\u00e9. L'algorithme fonctionne en construisant cet arbre un sommet \u00e0 la fois, \u00e0 partir d'un sommet de d\u00e9part arbitraire, \u00e0 chaque \u00e9tape en ajoutant la connexion la moins ch\u00e8re possible de l'arbre \u00e0 un autre sommet.
                                          
                                          L'algorithme de True Prim est l\u00e9g\u00e8rement modifi\u00e9. Plut\u00f4t que d'attribuer des poids aux passages, il attribue des poids aux cellules.
                                          
                                          L'algorithme est :
                                          1. Attribuez \u00e0 chaque cellule un poids al\u00e9atoire.
                                          2. Initialisez un ensemble avec une cellule arbitraire.
                                          3. Choisissez la cellule avec le plus petit poids de l'ensemble. S'il n'a pas de voisins non visit\u00e9s, supprimez-le de l'ensemble.
                                          4. Sinon, choisissez l'un des voisins non visit\u00e9s de la cellule, liez les deux ensembles et ajoutez le voisin \u00e0 l'ensemble.
                                          5. R\u00e9p\u00e9tez jusqu'\u00e0 ce que l'ensemble soit vide.
                                          """;
    public static final String WILSON = """
                                        David Bruce Wilson, chercheur principal chez Microsoft et professeur agr\u00e9g\u00e9 de math\u00e9matiques \u00e0 l'Universit\u00e9 de Washington, a d\u00e9velopp\u00e9 l'algorithme de Wilson. L'algorithme de Wilson est un algorithme de g\u00e9n\u00e9ration de labyrinthe bas\u00e9 sur la th\u00e9orie des graphes. Comme Aldous-Broder, cet algorithme g\u00e9n\u00e8re un arbre couvrant al\u00e9atoire en fonction de l'id\u00e9e d'une marche al\u00e9atoire.
                                        
                                        Il effectue ce qu'on appelle une marche al\u00e9atoire \u00e0 boucle effac\u00e9e, ce qui signifie qu'au fur et \u00e0 mesure, si le chemin qu'il forme se croise et forme une boucle, il efface cette boucle avant de continuer
                                        
                                        L'algorithme est :
                                        1. Choisissez une cellule arbitraire et ajoutez-la au labyrinthe.
                                        2. \u00c0 partir de n'importe quelle autre cellule, effectuez une marche al\u00e9atoire effac\u00e9e en boucle jusqu'\u00e0 ce que vous rencontriez une cellule appartenant au labyrinthe.
                                        3. Ajoutez la marche r\u00e9sultante au labyrinthe.
                                        4. R\u00e9p\u00e9tez 2 et 3 jusqu'\u00e0 ce que tous les cellules aient \u00e9t\u00e9 ajout\u00e9s au labyrinthe.
                                        """;

    private Cell[][] cells;
    private int rows;
    private int columns;
    private boolean finished = false;
    public Cell SelectedCell;
    protected Random random = new Random();

    public GenerationAlgorithm(Cell[][] cells, int rows, int columns) {
        this.cells = cells;
        this.rows = rows;
        this.columns = columns;
    }

    public ArrayList<Cell> calculateNeighbours(Cell selectedCell, boolean any) {
        ArrayList<Cell> localcells = new ArrayList<>();
        int y = selectedCell.getI();
        int x = selectedCell.getJ();
        int leftNeighbourI = selectedCell.getI();
        int leftNeighbourJ = selectedCell.getJ() - 1;
        int rightNeighbourI = selectedCell.getI();
        int rightNeighbourJ = selectedCell.getJ() + 1;
        int topNeighbourI = selectedCell.getI() - 1;
        int topNeighbourJ = selectedCell.getJ();
        int bottomNeighbourI = selectedCell.getI() + 1;
        int bottomNeighbourJ = selectedCell.getJ();

        if (x - 1 >= 0) {
            if (!this.getCells()[leftNeighbourI][leftNeighbourJ].isVisited() || any) {
                localcells.add(this.getCells()[leftNeighbourI][leftNeighbourJ]);
            }
        }

        if (x + 1 < this.getColumns()) {
            if (!this.getCells()[rightNeighbourI][rightNeighbourJ].isVisited() || any) {
                localcells.add(this.getCells()[rightNeighbourI][rightNeighbourJ]);
            }
        }

        if (y - 1 >= 0) {
            if (!this.getCells()[topNeighbourI][topNeighbourJ].isVisited() || any) {
                localcells.add(this.getCells()[topNeighbourI][topNeighbourJ]);
            }
        }

        if (y + 1 < this.getRows()) {
            if (!this.getCells()[bottomNeighbourI][bottomNeighbourJ].isVisited() || any) {
                localcells.add(this.getCells()[bottomNeighbourI][bottomNeighbourJ]);
            }
        }
        return localcells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isFinished() {
        return finished;
    }

    public abstract void update(GraphicsContext gc);

}
