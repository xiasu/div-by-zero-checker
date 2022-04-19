package org.checkerframework.checker.dividebyzero;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;

import javax.lang.model.type.TypeKind;
import java.beans.Expression;
import java.lang.annotation.Annotation;
import com.sun.source.tree.*;

import java.util.Set;
import java.util.EnumSet;

import org.checkerframework.checker.dividebyzero.qual.*;

public class DivByZeroVisitor extends BaseTypeVisitor<DivByZeroAnnotatedTypeFactory> {

    /** Set of operators we care about */
    private static final Set<Tree.Kind> DIVISION_OPERATORS = EnumSet.of(
        /* x /  y */ Tree.Kind.DIVIDE,
        /* x /= y */ Tree.Kind.DIVIDE_ASSIGNMENT,
        /* x %  y */ Tree.Kind.REMAINDER,
        /* x %= y */ Tree.Kind.REMAINDER_ASSIGNMENT);

    /**
     * Determine whether to report an error at the given binary AST node.
     * The error text is defined in the messages.properties file.
     * @param node the AST node to inspect
     * @return true if an error should be reported, false otherwise
     */
    private boolean errorAt(BinaryTree node) {
        // A BinaryTree can represent any binary operator, including + or -.
        // TODO
        if(hasAnnotation(node.getRightOperand(),Zero.class))
            System.out.print("Zero");
        if(hasAnnotation(node.getRightOperand(),Geq0.class))
            System.out.print("Geq0");
        if(hasAnnotation(node.getRightOperand(),Leq0.class))
            System.out.print("Leq0");
        if(hasAnnotation(node.getRightOperand(),Top.class))
            System.out.print("Top");
        if(hasAnnotation(node.getRightOperand(),Pos.class))
            System.out.print("Pos");
        if(hasAnnotation(node.getRightOperand(),Neg.class))
            System.out.print("Neg");
        if(hasAnnotation(node.getRightOperand(),Btm.class))
            System.out.print("Btm");
        if(hasAnnotation(node.getRightOperand(),Nzero.class))
            System.out.print("Nzero");
        if(node.getKind()== Tree.Kind.DIVIDE || node.getKind()== Tree.Kind.REMAINDER || node.getKind()== Tree.Kind.DIVIDE_ASSIGNMENT ||node.getKind()== Tree.Kind.REMAINDER_ASSIGNMENT)
        {
            System.out.print("Binary Tree At");
            if(hasAnnotation(node.getRightOperand(),Zero.class) ||hasAnnotation(node.getRightOperand(),Geq0.class) ||hasAnnotation(node.getRightOperand(),Leq0.class) ||hasAnnotation(node.getRightOperand(),Top.class))
                return true;
        }
        return false;
    }

    /**
     * Determine whether to report an error at the given compound assignment
     * AST node. The error text is defined in the messages.properties file.
     * @param node the AST node to inspect
     * @return true if an error should be reported, false otherwise
     */
    private boolean errorAt(CompoundAssignmentTree node) {
        // A CompoundAssignmentTree represents any binary operator combined with an assignment,
        // such as "x += 10".
        // TODO
        if(hasAnnotation(node.getExpression(),Zero.class))
            System.out.print("Zero");
        if(hasAnnotation(node.getExpression(),Geq0.class))
            System.out.print("Geq0");
        if(hasAnnotation(node.getExpression(),Leq0.class))
            System.out.print("Leq0");
        if(hasAnnotation(node.getExpression(),Top.class))
            System.out.print("Top");
        if(hasAnnotation(node.getExpression(),Pos.class))
            System.out.print("Pos");
        if(hasAnnotation(node.getExpression(),Neg.class))
            System.out.print("Neg");
        if(hasAnnotation(node.getExpression(),Btm.class))
            System.out.print("Btm");
        if(hasAnnotation(node.getExpression(),Nzero.class))
            System.out.print("Nzero");
        System.out.print("Assignment Tree At");
        if(node.getKind()== Tree.Kind.DIVIDE || node.getKind()== Tree.Kind.REMAINDER || node.getKind()== Tree.Kind.DIVIDE_ASSIGNMENT ||node.getKind()== Tree.Kind.REMAINDER_ASSIGNMENT)
        {
            System.out.print("Being judged");
            if(hasAnnotation(node.getExpression(),Zero.class) ||hasAnnotation(node.getExpression(),Geq0.class) ||hasAnnotation(node.getExpression(),Leq0.class) ||hasAnnotation(node.getExpression(),Top.class))
                return true;
        }
        return false;
    }

    // ========================================================================
    // Useful helpers

    private static final Set<TypeKind> INT_TYPES = EnumSet.of(
        TypeKind.INT,
        TypeKind.LONG);

    private boolean isInt(Tree node) {
        return INT_TYPES.contains(atypeFactory.getAnnotatedType(node).getKind());
    }

    private boolean hasAnnotation(Tree node, Class<? extends Annotation> c) {
        return atypeFactory.getAnnotatedType(node).hasAnnotation(c);
    }

    // ========================================================================
    // Checker Framework plumbing

    public DivByZeroVisitor(BaseTypeChecker c) {
        super(c);
    }

    @Override
    public Void visitBinary(BinaryTree node, Void p) {
        if (isInt(node)) {
            if (errorAt(node)) {
                checker.reportError(node, "divide.by.zero");
            }
        }
        return super.visitBinary(node, p);
    }

    @Override
    public Void visitCompoundAssignment(CompoundAssignmentTree node, Void p) {
        if (isInt(node.getExpression())) {
            if (errorAt(node)) {
                checker.reportError(node, "divide.by.zero");
            }
        }
        return super.visitCompoundAssignment(node, p);
    }

}
