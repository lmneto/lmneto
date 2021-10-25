import java.util.Calendar;
import java.util.ArrayList;
import java.util.Scanner;

public class jogo8 {

    public static void main(String[] args) {

        int tab[][] = new int[4][4];
        int goal[][] = new int[4][4];
        int numNodes = 0;
        int horaInicial = 0, horaFinal = 0, minInicial = 0, minFinal = 0, secInicial = 0, secFinal = 0;


        Calendar cal = null;



        System.out.println("1 - Config 1 \t\t 2 - Config 2\n"
                + "6 5 4 \t\t\t 6 5 7\n"
                + "7 8 1 \t\t\t 2   1\n"
                + "2 3   \t\t\t 8 4 3\n\n");

        Scanner in = new Scanner(System.in);  //Recolhe qual estado inicial deve ser carregado
        int op = in.nextInt();

        tab = carregaMat(op);
        goal = carregaGoal();

        System.out.println("Matriz Carregada!\n");

        if (paridadeMat(tab) % 3 == 0) {
            System.out.println("Não há Solução");
            return;
        } else {


            TreeNode inicialState = new TreeNode(tab);


            System.out.println("Prima 1 para realizar 'Busca em Profundidade'");
            System.out.println("Prima 2 para realizar 'Busca em Largura'");
            System.out.println("Prima 3 para realizar 'Busca em Profundidade Iterativa'");
            System.out.println("Prima 4 para realizar 'Busca Gulosa'");
            System.out.println("Prima 5 para realizar 'Busca A*'");

            in = new Scanner(System.in);  //Recolhe qual algoritmo deverá ser empregado
            op = in.nextInt();

            cal = Calendar.getInstance();


            horaInicial = cal.get(Calendar.HOUR_OF_DAY);
            minInicial = cal.get(Calendar.MINUTE);
            secInicial = cal.get(Calendar.SECOND);

            switch (op) {
                case 1:
                    numNodes = algDeep(inicialState, goal);

                    break;
                case 2:
                    numNodes = algBreadth(inicialState, goal);

                    break;
                case 3:
                    System.out.println("Entre com a profundidade desejada: \n");

                    in = new Scanner(System.in);
                    int prof = in.nextInt();
                    numNodes = algDeepIt(inicialState, goal, prof);
                    break;
                case 4:
                    /*
                     Total de nós criados: 273124
                     Total de nós Visitados: 221386
                     Profundidade: 62
                     273124
                     Tempo Gasto:0h:39m:3s*/
                    numNodes = algGreedy(inicialState, goal);
                    break;
                case 5:
                    numNodes = algA(inicialState, goal);
                    break;

            }

            if (numNodes == -1) {
                System.out.println("Não achou Solução!");
            }

            cal = Calendar.getInstance();

            horaFinal = cal.get(Calendar.HOUR_OF_DAY);
            minFinal = cal.get(Calendar.MINUTE);
            secFinal = cal.get(Calendar.SECOND);

            tempoExec(horaInicial, horaFinal, minInicial, minFinal, secInicial, secFinal);
        }
    }

    public static int algDeep(TreeNode inicialState, int[][] goalState) {
        ArrayList<TreeNode> lista = new ArrayList<TreeNode>();
        ArrayList<TreeNode> visitados = new ArrayList<TreeNode>();

        TreeNode auxNode = null;

        int nodeCriados = 1, nodeVisitados = 0;

        lista.add(inicialState);

        while (!lista.isEmpty()) {
            auxNode = lista.get(0);
            nodeVisitados++;
            lista.remove(0);

            if (auxNode.reachGoal(goalState)) {
                System.out.println("Total de nós criados: " + nodeCriados);
                System.out.println("Total de nós Visitados: " + nodeVisitados);

                System.out.println("Profundidade: " + auxNode.getProf());
                return nodeCriados;
            } else {
                if (!foiVisitado(visitados, auxNode)) {
                    TreeNode temp = new TreeNode();
                    visitados.add(auxNode);

                    if (auxNode.checkEsq()) {
                        temp.copyNode(auxNode.moveEsq());
                        lista.add(0, new TreeNode(temp));
                        nodeCriados++;
                    }

                    if (auxNode.checkDir()) {
                        temp.copyNode(auxNode.moveDir());
                        lista.add(0, new TreeNode(temp));
                        nodeCriados++;
                    }



                    if (auxNode.checkBaixo()) {
                        temp.copyNode(auxNode.moveBaixo());
                        lista.add(0, new TreeNode(temp));
                        nodeCriados++;
                    }

                    if (auxNode.checkCima()) {
                        temp.copyNode(auxNode.moveCima());
                        lista.add(0, new TreeNode(temp));
                        nodeCriados++;
                    }

                } else {
                    nodeCriados--;
                }
            }
            auxNode = null;
        }
        return -1;

    }

    public static int algDeepIt(TreeNode inicialState, int[][] goalState, int prof) {


        ArrayList<TreeNode> lista = new ArrayList<TreeNode>();
        ArrayList<TreeNode> visitados = new ArrayList<TreeNode>();
        TreeNode auxNode;
        for (int limite = 0; limite <= prof; limite++) {
            auxNode = null;

            lista.clear();
            visitados.clear();

            int nodeCriados = 1, nodeVisitados = 0;

            lista.add(inicialState);

            while (!lista.isEmpty()) {
                auxNode = lista.get(0);
                nodeVisitados++;
                lista.remove(0);

                if (auxNode.reachGoal(goalState)) {
                    System.out.println("Total de nós criados: " + nodeCriados);
                    System.out.println("Total de nós Visitados: " + nodeVisitados);

                    System.out.println("Profundidade: " + auxNode.getProf());

                    return nodeCriados;

                } else if (auxNode.getProf() < limite) {
                    if (!foiVisitado(visitados, auxNode)) {
                        TreeNode temp = new TreeNode();
                        visitados.add(auxNode);

                        if (auxNode.checkEsq()) {

                            temp.copyNode(auxNode.moveEsq());
                            lista.add(0, new TreeNode(temp));
                            nodeCriados++;

                        }

                        if (auxNode.checkDir()) {
                            temp.copyNode(auxNode.moveDir());
                            lista.add(0, new TreeNode(temp));
                            nodeCriados++;
                        }


                        if (auxNode.checkBaixo()) {

                            temp.copyNode(auxNode.moveBaixo());
                            lista.add(0, new TreeNode(temp));
                            nodeCriados++;

                        }

                        if (auxNode.checkCima()) {

                            temp.copyNode(auxNode.moveCima());
                            lista.add(0, new TreeNode(temp));
                            nodeCriados++;
                        }

                    }
                }
            }

        }
        return -1;

    }

    public static int algGreedy(TreeNode inicialState, int[][] goalState) {
        ArrayList<TreeNode> lista = new ArrayList<TreeNode>();
        ArrayList<TreeNode> visitados = new ArrayList<TreeNode>();
        ArrayList<TreeNode> temp = new ArrayList<TreeNode>();
        TreeNode auxNode = null, auxTree = null;

        int nodeCriados = 1, nodeVisitados = 0, x, y;

        lista.add(inicialState);

        while (!lista.isEmpty()) {
            auxNode = lista.get(0);
            nodeVisitados++;
            lista.remove(0);

            if (auxNode.reachGoal(goalState)) {
                System.out.println("Total de nós criados: " + nodeCriados);
                System.out.println("Total de nós Visitados: " + nodeVisitados);

                System.out.println("Profundidade: " + auxNode.getProf());
                return nodeCriados;
            } else {
                visitados.add(auxNode);
                TreeNode aux2 = new TreeNode();
                if (auxNode.checkEsq()) {

                    aux2.copyNode(auxNode.moveEsq());
                    auxTree = new TreeNode(aux2);

                    if (!foiVisitado(visitados, auxTree)) {
                        temp.add(auxTree);
                        nodeCriados++;
                    }
                }

                if (auxNode.checkDir()) {

                    aux2.copyNode(auxNode.moveDir());
                    auxTree = new TreeNode(aux2);

                    if (!foiVisitado(visitados, auxTree)) {
                        temp.add(auxTree);
                        nodeCriados++;
                    }
                }

                if (auxNode.checkBaixo()) {

                    aux2.copyNode(auxNode.moveBaixo());
                    auxTree = new TreeNode(aux2);

                    if (!foiVisitado(visitados, auxTree)) {
                        temp.add(auxTree);
                        nodeCriados++;
                    }
                }

                if (auxNode.checkCima()) {

                    aux2.copyNode(auxNode.moveCima());
                    auxTree = new TreeNode(aux2);
                    if (!foiVisitado(visitados, auxTree)) {
                        temp.add(auxTree);
                        nodeCriados++;
                    }
                }

                if (!temp.isEmpty()) {
                    x = Integer.MAX_VALUE;
                    y = 0;

                    for (int i = 0; i < temp.size(); i++) {
                        if (temp.get(i).getCusto() < x) {
                            x = temp.get(i).getCusto();
                            y = i;
                        }

                    }
                    lista.add(temp.get(y));
                    temp.remove(y);
                }
            }
        }
        return -1;
    }

    public static int algA(TreeNode inicialState, int[][] goalState) {
        ArrayList<TreeNode> lista = new ArrayList<TreeNode>();
        ArrayList<TreeNode> visitados = new ArrayList<TreeNode>();

        TreeNode auxNode = null;

        int nodeCriados = 1, nodeVisitados = 0, way = 0;

        lista.add(inicialState);

        while (!lista.isEmpty()) {
            way = getCheapWay(lista);
            auxNode = lista.get(way);
            nodeVisitados++;
            lista.remove(way);

            if (auxNode.reachGoal(goalState)) {
                System.out.println("Total de nós criados: " + nodeCriados);
                System.out.println("Total de nós Visitados: " + nodeVisitados);

                System.out.println("Profundidade: " + auxNode.getProf());
                return nodeCriados;
            } else {
                if (!foiVisitado(visitados, auxNode)) {
                    TreeNode temp = new TreeNode();
                    visitados.add(auxNode);

                    if (auxNode.checkEsq()) {
                        temp.copyNode(auxNode.moveEsq());
                        lista.add(0, new TreeNode(temp));
                        nodeCriados++;
                    }

                    if (auxNode.checkDir()) {
                        temp.copyNode(auxNode.moveDir());
                        lista.add(0, new TreeNode(temp));
                        nodeCriados++;
                    }

                    if (auxNode.checkBaixo()) {
                        temp.copyNode(auxNode.moveBaixo());
                        lista.add(0, new TreeNode(temp));
                        nodeCriados++;
                    }

                    if (auxNode.checkCima()) {
                        temp.copyNode(auxNode.moveCima());
                        lista.add(0, new TreeNode(temp));
                        nodeCriados++;
                    }

                }
            }
            auxNode = null;
        }
        return -1;

    }

    public static int algBreadth(TreeNode inicialState, int[][] goalState) {
        ArrayList<TreeNode> lista = new ArrayList<TreeNode>();
        ArrayList<TreeNode> visitados = new ArrayList<TreeNode>();

        TreeNode auxNode = null;

        int nodeCriados = 1, nodeVisitados = 0;

        lista.add(inicialState);

        while (!lista.isEmpty()) {
            nodeVisitados++;
            auxNode = lista.get(0);
            lista.remove(0);

            if (auxNode.reachGoal(goalState)) {
                System.out.println("Total de nós criados: " + nodeCriados);
                System.out.println("Total de nós visitados: " + nodeVisitados);
                System.out.println("Profundidade: " + auxNode.getProf());
                return nodeCriados;
            } else {
                if (!foiVisitado(visitados, auxNode)) {

                    visitados.add(auxNode);
                    TreeNode temp = new TreeNode();

                    if (auxNode.checkCima()) {

                        temp.copyNode(auxNode.moveCima());
                        lista.add(new TreeNode(temp));
                        nodeCriados++;
                    }

                    if (auxNode.checkBaixo()) {
                        temp.copyNode(auxNode.moveBaixo());
                        lista.add(new TreeNode(temp));
                        nodeCriados++;
                    }

                    if (auxNode.checkDir()) {
                        temp.copyNode(auxNode.moveDir());
                        lista.add(new TreeNode(temp));
                        nodeCriados++;
                    }

                    if (auxNode.checkEsq()) {
                        temp.copyNode(auxNode.moveEsq());
                        lista.add(new TreeNode(temp));
                        nodeCriados++;
                    }
                }
            }
        }
        return -1;
    }

    public static void tempoExec(int horaInicial, int horaFinal, int minInicial, int minFinal, int secInicial, int secFinal) { //função que calcula o tempo que o algoritmo demorou a terminar
        if (horaInicial > horaFinal) {
            horaFinal = 24 - horaInicial + horaFinal;
        } else {
            horaFinal = horaFinal - horaInicial;
        }
        if (minInicial > minFinal) {
            minFinal = 60 - minInicial + minFinal;
        } else {
            minFinal = minFinal - minInicial;
        }
        if (secInicial > secFinal) {
            minFinal += 1;
            secFinal = secInicial - secFinal;
        } else {
            secFinal = secFinal - secInicial;
        }
        System.out.println("Tempo Gasto:" + horaFinal + "h:" + minFinal + "m:" + secFinal + "s");
    }

    public static boolean foiVisitado(ArrayList<TreeNode> visitados, TreeNode node) {
        for (int i = 0; i < visitados.size(); i++) {
            if (visitados.get(i).compareNode(node.state)) {
                return true;
            }
        }
        return false;
    }

    private static int getCheapWay(ArrayList<TreeNode> lista) {
        int c = Integer.MAX_VALUE, indiceMenor = 0, p = Integer.MAX_VALUE;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getPeso() <= c) {
                c = lista.get(i).getPeso();
                if (lista.get(i).getProf() < p) {
                    p = lista.get(i).getProf();
                    indiceMenor = i;
                }
            }
        }
        return indiceMenor;
    }

    public static int paridadeMat(int[][] tab) { //checa paridade da matriz
        int vec[] = new int[16];
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                vec[k] = tab[i][j];
                k++;
            }
        }

        int paridade = 0;

        for (k = 0; k < 16; k++) {
            int cont = 0;
            for (int i = k + 1; i < 16; i++) {
                if (vec[k] != -1 && vec[i] != -1) {
                    if (vec[i] < vec[k]) {
                        cont++;
                    }
                }
            }
            paridade = paridade + cont;
        }
        return paridade;
    }

    public static int[][] carregaMat(int op) {
        int tab[][] = new int[4][4];
        if (op == 1) {
            tab[0][0] = 1;
            tab[0][1] = 2;
            tab[0][2] = 3;
            tab[0][3] = 4;
            tab[1][0] = 5;
            tab[1][1] = 6;
            tab[1][2] = 8;
            tab[1][3] = 12;
            tab[2][0] = 13;
            tab[2][1] = 9;
            tab[2][2] = -1;
            tab[2][3] = 7;            
            tab[3][0] = 14;
            tab[3][1] = 11;
            tab[3][2] = 10;
            tab[3][3] = 15;


        }
        if (op == 2) {
            tab[0][0] = 6;
            tab[0][1] = 5;
            tab[0][2] = 7;
            tab[1][0] = 2;
            tab[1][1] = -1;
            tab[1][2] = 1;
            tab[2][0] = 8;
            tab[2][1] = 4;
            tab[2][2] = 3;
        }
        return tab;
    }

    public static int[][] carregaGoal() {
        int goalState[][] = new int[4][4];

        goalState[0][0] = 1;
        goalState[0][1] = 2;
        goalState[0][2] = 3;
        goalState[0][3] = 4;
        goalState[1][0] = 5;
        goalState[1][1] = 6;
        goalState[1][2] = 7;
        goalState[1][3] = 8;
        goalState[2][0] = 9;
        goalState[2][1] = 10;
        goalState[2][2] = 11;
        goalState[2][3] = 12;
        goalState[3][0] = 13;
        goalState[3][1] = 14;
        goalState[3][2] = 15;
        goalState[3][3] = -1;        
        return goalState;
    }
}
