package com.novel.file.stream;

import org.apache.commons.math3.util.Pair;

import java.util.function.Function;

/**
 * @author 昴星
 * @date 2023-10-25 8:44
 * @explain
 */

public class Eithor<L,R> {
    private final L left;
    private final R right;

    private Eithor(L left,R right){
        this.left=left;
        this.right=right;
    }

    public static <L,R> Eithor<L,R> Left(L value){
        return new Eithor<>(value,null);
    }

    public static <L,R> Eithor<L,R> Right(R exp){
        return new Eithor<>(null,exp);
    }

    public static <L,R> Eithor<? super Boolean, Pair<L,? super RuntimeException> > mapToEithor(L value, Function<L,? super Boolean> function){
        try {
            return Eithor.Left(function.apply(value));
        } catch (Exception e) {
            return Eithor.Right(new Pair<L,RuntimeException>(value,new RuntimeException(e.getMessage())));
        }
    }

    public static Boolean mapToLeft(Eithor eithor){
        return eithor.left!=null;
    }

    public static Boolean mapToRight(Eithor eithor){
        return eithor.right!=null;
    }

    public static Boolean isLeft(Eithor eithor){
        return eithor.left!=null;
    }

    public static Boolean isRight(Eithor eithor){
        return eithor.right!=null;
    }

    public L getLeft(){
        return this.left;
    }

    public R getRight(){
        return this.right;
    }
}
