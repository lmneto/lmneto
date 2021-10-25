/**********************************************************************
 * Copyright (c) 2015 
 * @lmneto Luis Neto
 *********************************************************************/

// class TreeNode definition  
public class TreeNode {

    int state[][], prof, peso;
    private TreeNode pai;
    int posX, posY; //posição do espaço em branco
    char dir;

    public TreeNode() { //construtor default
        this.state = new int[4][4];
        this.pai = null;
        this.posX = -1;
        this.posY = -1;
        this.prof = 0;
        this.peso = 0;

    }

    public TreeNode(TreeNode newNode) { //construtor onde adiciona novo nó
        this.state = new int[4][4];
        this.pai = newNode;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.state[i][j] = newNode.state[i][j];
            }
        }

        this.setProf();  //busca a profundidade do nó
        this.posBlank(state); //acha a coordenada do espaço em branco e guarda
        this.peso = this.prof + getCusto();

    }

    public TreeNode(int[][] state) {
        this.state = new int[4][4];
        this.pai = null;
        this.posX = -1;
        this.posY = -1;


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.state[i][j] = state[i][j];
            }
        }

        posBlank(this.state);
        this.setProf();
        this.peso = getCusto();
    }

    public void copyNode(TreeNode node) { //função que copia um nó
        this.state = new int[4][4];
        this.pai = node.pai;
        this.posX = node.posX;
        this.posY = node.posY;
        this.prof = node.prof;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.state[i][j] = node.state[i][j];
            }
        }
    }

    public int getNumPassosSol() { //função que acha o número de passos da solução(profundidade)
        TreeNode aux;
        aux = this;
        int cont = 0;
        while (aux != null) {
            aux = aux.pai;
            cont++;
        }
        return cont - 1;
    }

    public int getPeso() //devolve peso do nó
    {
        return this.peso;
    }

    public TreeNode getParentNode() { //devolve o pai do nó
        return this.pai;
    }

    public int getProf() { //devolve a profundidade do nó
        return this.prof;
    }

    public void setProf() {
        TreeNode aux = this;
        int prof = 0;
        while (aux.pai != null) {
            aux = aux.pai;
            prof++;
        }
        this.prof = prof;
    }

    public void setDir(char c) {
        this.dir = c;
    }

    public boolean checkCima() {

        if (this.posX == 0 || (this.pai != null && this.pai.dir == 'b')) {
            return false;
        } else {
            return true;
        }
    }

    public TreeNode moveCima() { //desloca espaço em branco para cima
        TreeNode auxState = new TreeNode();
        auxState.copyNode(this);
        auxState.setDir('c');

        int aux = auxState.state[auxState.posX - 1][auxState.posY];
        auxState.state[auxState.posX - 1][auxState.posY] = -1;
        auxState.state[auxState.posX][auxState.posY] = aux;

        return auxState;
    }

    public boolean checkBaixo() {

        if (this.posX == 3 || (this.pai != null && this.pai.dir == 'c')) {
            return false;
        } else {
            return true;
        }
    }

    public TreeNode moveBaixo() { //desloca espaço em branco para baixo
        TreeNode auxState = new TreeNode();
        auxState.copyNode(this);
        auxState.setDir('b');

        int aux = auxState.state[auxState.posX + 1][auxState.posY];
        auxState.state[auxState.posX + 1][auxState.posY] = -1;
        auxState.state[auxState.posX][auxState.posY] = aux;

        return auxState;
    }

    public boolean checkEsq() {

        if (this.posY == 0 || (this.pai != null && this.pai.dir == 'd')) {
            return false;
        } else {
            return true;
        }
    }

    public TreeNode moveEsq() { //desloca espaço em branco para esquerda
        TreeNode auxState = new TreeNode();
        auxState.copyNode(this);
        auxState.setDir('e');

        int aux = auxState.state[auxState.posX][auxState.posY - 1];
        auxState.state[auxState.posX][auxState.posY - 1] = -1;
        auxState.state[auxState.posX][auxState.posY] = aux;

        return auxState;
    }

    public boolean checkDir() {

        if (this.posY == 3 || (this.pai != null && this.pai.dir == 'e')) {
            return false;
        } else {
            return true;
        }
    }

    public TreeNode moveDir() { //desloca espaço em branco para direita
        TreeNode auxState = new TreeNode();
        auxState.copyNode(this);
        auxState.setDir('d');

        int aux = auxState.state[auxState.posX][auxState.posY + 1];
        auxState.state[auxState.posX][auxState.posY + 1] = -1;
        auxState.state[auxState.posX][auxState.posY] = aux;

        return auxState;
    }

    public void posBlank(int[][] state) { //devolve posição do espaço em branco

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if (state[i][j] == -1) {
                    this.posX = i;
                    this.posY = j;
                    break;
                }
            }
        }
    }

    public boolean compareNode(int[][] otherState) { //compara dois estados

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.state[i][j] != otherState[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean reachGoal(int[][] goalState) { //verifica se chegaste ao objectivo
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.state[i][j] != goalState[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printTabuleiro() {
        for (int i = 0; i < 4; i++) {
            System.out.println(this.state[i][0] + " " + this.state[i][1] + " " + this.state[i][2]);

        }
        System.out.println("\n");
    }

    public int getCusto() { //devolve distância do estado atual para o estado final

        int custo = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                switch (state[i][j]) {

                    case 1:
                        if (i == 1) {
                            custo += 1;
                        }
                        if (i == 2) {
                            custo += 2;
                        }
                        if (i == 3) {
                            custo += 3;
                        }
                        if (j == 1) {
                            custo += 1;
                        }
                        if (j == 2) {
                            custo += 2;
                        }
                        if (j == 3) {
                            custo += 3;
                        }
                        break;
                    case 2:
                        if (i == 0) {
                            custo += 1;
                        }
                        if (i == 2) {
                            custo += 1;
                        }
                        if (i == 3) {
                            custo += 2;
                        }
                        if (j == 1) {
                            custo += 1;
                        }
                        if (j == 2) {
                            custo += 2;
                        }
                        if (j == 3) {
                            custo += 3;
                        }
                        break;
                    case 3:
                        if (i == 0) {
                            custo += 2;
                        }
                        if (i == 1) {
                            custo += 1;
                        }
                        if (i == 3) {
                            custo += 1;
                        }
                        if (j == 1) {
                            custo += 1;
                        }
                        if (j == 2) {
                            custo += 2;
                        }
                        if (j == 3) {
                            custo += 3;
                        }
                        break;
                    case 4:
                        if (j == 0) {
                            custo += 3;
                        }
                        if (j == 1) {
                            custo += 2;
                        }
                        if (j == 2) {
                            custo += 1;
                        }
                        if (i == 1) {
                            custo += 1;
                        }
                        if (i == 2) {
                            custo += 2;
                        }
                        if (i == 3) {
                            custo += 3;
                        }
                        
                        break;
                    case 5:
                        if (i == 0) {
                            custo += 1;
                        }
                        if (i == 2) {
                            custo += 1;
                        }
                        if (i == 3) {
                            custo += 2;
                        }
                        if (j == 1) {
                            custo += 1;
                        }
                        if (j == 2) {
                            custo += 2;
                        }
                        if (j == 3) {
                            custo += 3;
                        }
                        
                        break;
                    case 6:
                        if (i != 0) {
                            custo += 1;
                        }
                        if (i != 2) {
                            custo += 1;
                        }
                        if (i != 3) {
                            custo += 2;
                        }
                        if (j == 0) {
                            custo += 1;
                        }
                        if (j == 2) {
                            custo += 1;
                        }
                        if (j == 3) {
                            custo += 2;
                        }
                        break;
                    case 7:
                        if (i == 0) {
                            custo += 1;
                        }
                        if (i == 2) {
                            custo += 1;
                        }
                        if (i == 3) {
                            custo += 2;
                        }
                        if (j == 1) {
                            custo += 1;
                        }
                        if (j == 0) {
                            custo += 2;
                        }
                        if (j == 3) {
                            custo += 1;
                        }
                        break;
                    case 8:
                        if (j == 0) {
                            custo += 3;
                        }
                        if (j == 1) {
                            custo += 2;
                        }
                        if (j == 2) {
                            custo += 1;
                        }
                        if (i == 0) {
                            custo += 1;
                        }
                        if (i == 2) {
                            custo += 1;
                        }
                        if (i == 3) {
                            custo += 2;
                        }
                        break;
                    case 9:
                        if (j == 1) {
                            custo += 1;
                        }
                        if (j == 2) {
                            custo += 2;
                        }
                        if (j == 3) {
                            custo += 3;
                        }
                        if (i == 0) {
                            custo += 2;
                        }
                        if (i == 1) {
                            custo += 1;
                        }
                        if (i == 3) {
                            custo += 1;
                        }
                        break;
                        case 10:
                        if (j == 0) {
                            custo += 1;
                        }
                        if (j == 2) {
                            custo += 1;
                        }
                        if (j == 3) {
                            custo += 2;
                        }
                        if (i == 0) {
                            custo += 2;
                        }
                        if (i == 1) {
                            custo += 1;
                        }
                        if (i == 3) {
                            custo += 1;
                        }
                        break;
                        case 11:
                        if (j == 0) {
                            custo += 2;
                        }
                        if (j == 1) {
                            custo += 1;
                        }
                        if (j == 3) {
                            custo += 1;
                        }
                        
                        if (i == 0) {
                            custo += 2;
                        }
                        if (i == 1) {
                            custo += 1;
                        }
                        if (i == 3) {
                            custo += 1;
                        }
                        break;
                        case 12:
                        if (j == 0) {
                            custo += 3;
                        }
                        if (j == 1) {
                            custo += 2;
                        }
                        if (j == 2) {
                            custo += 1;
                        }
                        
                        if (i == 0) {
                            custo += 2;
                        }
                        if (i == 1) {
                            custo += 1;
                        }
                        if (i == 3) {
                            custo += 1;
                        }
                        break;
                        case 13:
                        if (j == 1) {
                            custo += 1;
                        }
                        if (j == 2) {
                            custo += 2;
                        }
                        if (j == 3) {
                            custo += 3;
                        }
                        if (i == 0) {
                            custo += 3;
                        }
                        if (i == 1) {
                            custo += 2;
                        }
                        if (i == 2) {
                            custo += 1;
                        }
                        break;
                        case 14:
                        if (j == 0) {
                            custo += 1;
                        }
                        if (j == 2) {
                            custo += 1;
                        }
                        if (j == 3) {
                            custo += 2;
                        }
                        if (i == 0) {
                            custo += 3;
                        }
                        if (i == 1) {
                            custo += 2;
                        }
                        if (i == 2) {
                            custo += 1;
                        }
                        break;
                        case 15:
                        if (j == 0) {
                            custo += 2;
                        }
                        if (j == 1) {
                            custo += 1;
                        }
                        if (j == 3) {
                            custo += 1;
                        }
                        if (i == 0) {
                            custo += 3;
                        }
                        if (i == 1) {
                            custo += 2;
                        }
                        if (i == 2) {
                            custo += 1;
                        }
                        break;
                    case -1:
                        if (i != 1) {
                            custo += 1;
                        }
                        if (j != 1) {
                            custo += 1;
                        }
                        break;
                }
            }
        }
        return custo;
    }
} // end class TreeNode  
