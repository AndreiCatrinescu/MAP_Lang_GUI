package Lang.Model.Structures;

import Lang.Model.Statements.CompStatement;
import Lang.Model.Statements.Statement;

public class BinaryTreeBuilder implements TreeBuilder<Statement> {
    @Override
    public BinaryTree<Statement> buildTree(Statement program) {
        if(!(program instanceof CompStatement compStatement))
            return new BinaryTree<>(program);

        Statement first = compStatement.getFirst();
        Statement last = compStatement.getLast();

        BinaryTree<Statement> left = new BinaryTree<>(first);
        BinaryTree<Statement> right = new BinaryTree<>(last);
        return new BinaryTree<>(program, left, right);
    }
}
