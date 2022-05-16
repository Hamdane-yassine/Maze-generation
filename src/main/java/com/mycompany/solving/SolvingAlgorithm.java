package com.mycompany.solving;

import com.mycompany.models.Cell;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public abstract class SolvingAlgorithm {

    public static final String DIJKSTRA = "La recherche de trajectoire a pris de l'importance au début des années 1950 dans le contexte du routage ; c'est-à-dire trouver les directions les plus courtes entre deux nœuds ou plus. En 1956, l'ingénieur logiciel néerlandais Edsger Wybe Dijkstra crée le plus connu de ces algorithmes : Dijkstra.\n"
            + "\n"
            + "L'algorithme de Dijkstra trouve le chemin le plus court d'un nœud racine à tous les autres nœuds (jusqu'à ce que la cible soit atteinte). Dijkstra est l'un des algorithmes de graphe les plus utiles ; de plus, il peut facilement être modifié pour résoudre de nombreux problèmes différents.\n"
            + "\n"
            + "Cet algorithme commence par un nœud racine (ou de démarrage) et une file d'attente de nœuds candidats. A chaque étape, nous calculons la distance minimale entre la racine et chaque voisin non visité. Ensuite, nous les ajoutons à la file d'attente ; celui avec la distance la plus faible est le prochain qui sera retiré de la file d'attente. Ce processus se répète jusqu'à ce qu'un chemin vers la destination soit trouvé.\n"
            + "\n"
            + "Étant donné que les nœuds de distance les plus bas sont examinés en premier, la première fois que la destination est trouvée, le chemin vers celle-ci sera le chemin le plus court.\n"
            + "\n"
            + "L'algorithme est :\n"
            + "Prenons un nœud racine et notons D la distance d'un nœud à la racine. L'algorithme de Dijkstra attribuera des valeurs de distance initiales et les améliorera étape par étape.\n"
            + "\t1. Définissez la distance de tous les nœuds D sur l'infini, à l'exception du nœud\n"
            + "\tracine (courant) dont la distance est de 0.\n"
            + "\t2. Marquer le nœud actuel comme visité et mettre à jour D pour tous ses voisins\n"
            + "\tnon visités avec la distance la plus courte.\n"
            + "\t3. Si nous avons atteint le nœud cible on arrétera ici,sinon ajoutez à la file\n"
            + "\t   d'attente les voisins non visités et revenez à l'étape 2.\n";
    public static final String BFS = """
                                     Breadth First Search (BFS) est l'un des deux algorithmes de parcours de graphe les plus fondamentaux. Publi\u00e9 pour la premi\u00e8re fois en 1959 par Edward F. Moore pour trouver le chemin le plus court hors d'un labyrinthe, il est maintenant utilis\u00e9 quotidiennement non seulement pour la travers\u00e9e r\u00e9guli\u00e8re, mais aussi pour l'analyse des r\u00e9seaux, le GPS, les moteurs de recherche, la planification et d'autres types de graphes.
                                     
                                     Breadth First Search explore \u00e9galement dans toutes les directions jusqu'\u00e0 ce que l'objectif soit atteint. En d'autres termes, il part d'un n\u0153ud choisi et examine tous ses voisins, puis il examine tous les voisins des voisins, et ainsi de suite...
                                     
                                     L'algorithme est :
                                     1. Ajoutez le n\u0153ud de d\u00e9part dans la file d'attente et marquez-le comme visit\u00e9.
                                         Tant qu'un n\u0153ud attend dans la file d'attente :
                                     \t\t1. Prenez le n\u0153ud en t\u00eate de file (file d'attente).
                                     \t\t2. Ajoutez \u00e0 la file d'attente tous les voisins disponibles, notez le parent
                                     \t\t    et marquez comme visit\u00e9.
                                     2. Revenez en arri\u00e8re depuis l'objectif pour commencer \u00e0 utiliser le lien parent afin d'obtenir le chemin le plus court.
                                     """;

    public static final String DFS = """
                                     Depth First Search (DFS) est l'autre algorithme fondamental de parcours de graphes ; Le BFS est l'autre. Aussi utile que le BFS, le DFS peut \u00eatre utilis\u00e9 pour g\u00e9n\u00e9rer un ordre topologique, pour g\u00e9n\u00e9rer des labyrinthes pour parcourir des arbres dans un ordre sp\u00e9cifique, pour construire des arbres de d\u00e9cision, pour d\u00e9couvrir un chemin de solution avec des choix hi\u00e9rarchiques, pour d\u00e9tecter un cycle dans un graphe.
                                     
                                     Cependant, veuillez d'abord noter que le DFS ne garantit pas le chemin le plus court.
                                     
                                     Ensuite, ce n'est pas un tr\u00e8s bon algorithme si l'unique but est de faire une simple recherche de chemin.
                                     
                                     Depth First Search traverse en explorant le plus loin possible chaque chemin avant de revenir en arri\u00e8re. C'est la raison pour laquelle vous pouvez aussi trouver cet algorithme sous le nom de Backtracking. De plus, cette propri\u00e9t\u00e9 permet \u00e0 l'algorithme d'\u00eatre impl\u00e9ment\u00e9 succinctement sous des formes it\u00e9ratives et r\u00e9cursives.
                                     
                                     Depth First Search (DFS) parcourt un graphe dans un mouvement vers la profondeur et utilise une pile pour se souvenir d'obtenir le sommet suivant pour d\u00e9marrer une recherche, lorsqu'une impasse se produit dans n'importe quelle it\u00e9ration.
                                     
                                     
                                     L'algorithme est :
                                     1. Ajoutez le n\u0153ud de d\u00e9part dans la pile et marquez-le comme visit\u00e9.
                                         Tant qu'un n\u0153ud attend dans la pile :
                                     \t\t1. Prenez le n\u0153ud en haut de la pile.
                                     \t\t2. Ajoutez sur la pile tous les voisins disponibles dans l'ordre, notez
                                     \t\t    le parent et marquez comme visit\u00e9 (nous avons choisi pour
                                     \t\t    la visualisation Nord, Est, Sud, Ouest comme ordre)
                                     2. Revenir en arri\u00e8re depuis l'objectif pour commencer \u00e0 utiliser le lien parent afin d'obtenir le chemin.
                                     """;

    public static final String ASTAR = """
                                       L'algorithme de recherche A* a \u00e9t\u00e9 publi\u00e9 pour la premi\u00e8re fois en 1968 par des chercheurs de Stanford dans le cadre du projet Shakey the robot. Ils voulaient une version am\u00e9lior\u00e9e de l'algorithme de Dijkstra qui utilise une fonction heuristique pour minimiser le co\u00fbt du mouvement et planifier un chemin permettant au robot de naviguer de mani\u00e8re autonome dans les couloirs et les diff\u00e9rentes pi\u00e8ces d'un b\u00e2timent.
                                       
                                       L'algorithme de Dijkstra fonctionne bien pour trouver le chemin le plus court, mais cela fait perdre du temps \u00e0 explorer des directions qui ne sont pas prometteuses. A* am\u00e9liore cela en permettant l'inclusion d'informations suppl\u00e9mentaires que l'algorithme peut utiliser dans le cadre de la fonction heuristique :
                                       \t\t- L'algorithme de Dijkstra utilise la distance du n\u0153ud racine.
                                       \t\t- L'algorithme A* utilise \u00e0 la fois la distance r\u00e9elle depuis la racine
                                       \t\t  et la distance estim\u00e9e jusqu'au but.
                                       
                                       L'algorithme est :
                                        A* s\u00e9lectionne le chemin qui minimise la fonction suivante :
                                          f(n)=g(n)+h(n) o\u00f9 :
                                           - g(n) est le co\u00fbt du chemin du point de d\u00e9part au n\u0153ud n (distance de Dijkstra).
                                           - h(n) est le co\u00fbt estim\u00e9 du chemin du n\u0153ud n au n\u0153ud de destination, tel que
                                             calcul\u00e9 par la distance de Manhattan dans notre cas.
                                       
                                       L'origine de la distance Manhattan (ou distance Taxicab) est bas\u00e9e sur la grille bien connue comme g\u00e9ographie des rues de l'arrondissement new-yorkais de Manhattan. Il s'agit de la distance entre deux points dans un environnement bas\u00e9 sur une grille (avec uniquement des d\u00e9placements horizontaux et verticaux). La distance de Manhattan est la simple somme des d\u00e9placements horizontaux et verticaux, tandis que la distance diagonale ou "\u00e0 vol d'oiseau" peut \u00eatre calcul\u00e9e en appliquant le th\u00e9or\u00e8me de Pythagore.
                                       
                                       Cette distance est id\u00e9ale pour nos labyrinthes qui permettent un mouvement dans les 4 sens (haut, bas, gauche, droite):    h(n)=|but.x\u2212racine.x|+|but.y\u2212racine.y|
                                       """;

    private boolean finished;
    private Cell root;
    private Cell target;
    private Cell[][] grid;
    protected Cell current;

    public void setRoot(Cell root) {
        this.root = root;
    }

    public void setTarget(Cell target) {
        this.target = target;
    }

    public SolvingAlgorithm(Cell root, Cell target, Cell[][] grid, GraphicsContext gc) {
        this.root = root;
        this.target = target;
        this.root.RootTarged(gc);
        this.target.RootTarged(gc);
        this.finished = false;
        this.grid = grid;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public Cell getRoot() {
        return root;
    }

    public Cell getTarget() {
        return target;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public abstract void update(GraphicsContext gc);

    public boolean isFinished() {
        return finished;
    }

    public ArrayList<Cell> getLinks(Cell cell) {
        ArrayList<Cell> links = new ArrayList<>();
        ArrayList<ArrayList<Integer>> celllinks = cell.getLinks();
        for (ArrayList<Integer> arr : celllinks) {
            links.add(this.grid[arr.get(0)][arr.get(1)]);
        }
        return links;
    }

}
