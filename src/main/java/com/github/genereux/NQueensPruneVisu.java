/*
 * mini-cp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License  v3
 * as published by the Free Software Foundation.
 *
 * mini-cp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY.
 * See the GNU Lesser General Public License  for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with mini-cp. If not, see http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * Copyright (c)  2018. by Laurent Michel, Pierre Schaus, Pascal Van Hentenryck
 */

package com.github.genereux;

import javafx.application.Application;
import javafx.stage.Stage;
import org.uclouvain.visualsearchtree.API.Visualization;
import org.uclouvain.visualsearchtree.tree.Tree;
import org.uclouvain.visualsearchtree.tree.TreeVisual;
import org.uclouvain.visualsearchtree.tree.VisualTree;

/**
 * Example that illustrates how to solve the NQueens
 * problem with a chronological backtracking algorithm
 * that backtracks as soon as a decision conflicts with
 * the previous decisions.
 */
public class NQueensPruneVisu extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //nqueens
        NQueensPrune nqueens = new NQueensPrune(4);

        //init pipeline
        Visualization tools = new Visualization();

        //building
        TreeVisual tv = tools.createTreeVisual();
        tools.launchProfiler(tv);

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                nqueens.dfs(new DFSListener() {
                    @Override
                    public void solution(int id, int pId) {
                        System.out.println("solution");
                        tv.createNode(id,pId, Tree.NodeType.SOLUTION,(nodeInfoData) -> {}, "{\"cost\": "+id+", \"param1\": "+id+", \"other\": \"Some info on node\"}");
                    }
                    @Override
                    public void fail(int id, int pId) {
                        System.out.println("fail");
                        tv.createNode(id,pId, Tree.NodeType.FAIL,(nodeInfoData) -> {}, "{\"cost\": "+id+", \"param1\": "+id+", \"other\": \"Some info on node\"}");
                    }
                    @Override
                    public void branch(int id, int pId, int nChilds) {
                        System.out.println("branch");
                        tv.createNode(id,pId, Tree.NodeType.INNER,(nodeInfoData) -> {}, "{\"cost\": "+id+", \"param1\": "+id+", \"other\": \"Some info on node\"}");
                    }
                });
            }
        });
        t2.start();
    }
}