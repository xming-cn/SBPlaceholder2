package com.xming.sbplaceholder2.parser.type.element;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.type.VoidType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class VoidElement extends SBElement<VoidType> {
    String cause;
    protected ArrayList<VoidElement> relational = new ArrayList<>();
    public VoidElement(String cause, SBElement<?>... relational) {
        this.cause = cause;
        this.relational.addAll(
                Arrays.stream(relational)
                        .filter(sbElement -> sbElement instanceof VoidElement)
                        .map(sbElement -> (VoidElement) sbElement)
                        .collect(Collectors.toList())
        );
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Void";
    }
    @Override
    public String toString() {
        return "void";
    }
    @Override
    public Object clone() {
        return new VoidElement(cause);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VoidElement;
    }

    public String getCause() {
        return cause;
    }

    public ArrayList<VoidElement> getRelational() {
        return relational;
    }
}
