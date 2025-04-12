package src.sorts;

import java.util.ArrayList;
import java.util.List;

public class TournamentSort {
    static class TreeNode {
        int value;
        List<TreeNode> children;

        TreeNode(int value) {
            this.value = value;
            this.children = new ArrayList<>();
        }
    }
    
    public static int[] sort(int[] arr) 
    {
        List<TreeNode> trees = new ArrayList<>();
        for (int val : arr)
        {
            trees.add(new TreeNode(val));
        }

        List<Integer> result = new ArrayList<>();
        while(!trees.isEmpty())
        {
            TreeNode winner = playTournament(trees);
            result.add(winner.value);
            trees = winner.children;
        }
        
        int[] sorted = new int[result.size()];

        for (int i = 0; i < sorted.length; i++)
        {
            sorted[i] = result.get(i);
        }
        return sorted;
    }

    private static TreeNode playTournament(List<TreeNode> trees) 
    {
        if (trees.size() == 1) return trees.get(0);
        return playTournament(playRound(trees, new ArrayList<>()));
    }

    private static List<TreeNode> playRound(List<TreeNode> trees, List<TreeNode> done)
    {
        if (trees.isEmpty()) return done;
        if (trees.size() == 1) 
        {
            done.add(trees.get(0));
            return done;
        }

        TreeNode t1 = trees.get(0);
        TreeNode t2 = trees.get(1);
        TreeNode winner = playGame(t1, t2);
        done.add(winner);

        return playRound(trees.subList(2, trees.size()), done);
    }

    private static TreeNode playGame(TreeNode t1, TreeNode t2) 
    {
        if (t1.value <= t2.value) return promote(t1, t2);
        else return promote(t2, t1);
    }

    private static TreeNode promote(TreeNode winner, TreeNode loser) 
    {
        TreeNode newNode = new TreeNode(winner.value);
        newNode.children.add(loser);
        newNode.children.addAll(winner.children);
        return newNode;
    }
}