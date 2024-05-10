package com.xming.sbplaceholder2.common;

import com.xming.sbplaceholder2.parser.type.CalculableElement;
import com.xming.sbplaceholder2.parser.type.element.IntElement;
import com.xming.sbplaceholder2.parser.type.element.NumberElement;

public class Calculator {
    CalculableElement<?> left;
    CalculableElement<?> right;
    public Calculator(CalculableElement<?> left, CalculableElement<?> right) {
        this.left = left;
        this.right = right;
    }
    public CalculableElement<?> sum() {
        if (left.decimal() || right.decimal()) {
            return new NumberElement(left.sums(right.value()));
        } else {
            return new IntElement((long) left.sums(right.value()));
        }
    }
    public CalculableElement<?> difference() {
        if (left.decimal() || right.decimal()) {
            return new NumberElement(left.differences(right.value()));
        } else {
            return new IntElement((long) left.differences(right.value()));
        }
    }
    public CalculableElement<?> product() {
        if (left.decimal() || right.decimal()) {
            return new NumberElement(left.products(right.value()));
        } else {
            return new IntElement((long) left.products(right.value()));
        }
    }
    public CalculableElement<?> ratio() {
        if (left.decimal() || right.decimal()) {
            return new NumberElement(left.ratios(right.value()));
        } else {
            return new IntElement((long) left.ratios(right.value()));
        }
    }
    public CalculableElement<?> power() {
        if (left.decimal() || right.decimal()) {
            return new NumberElement(left.powers(right.value()));
        } else {
            return new IntElement((long) left.powers(right.value()));
        }
    }
    public CalculableElement<?> ratio_floor() {
        if (left.decimal() || right.decimal()) {
            return new NumberElement(left.ratios_floor(right.value()));
        } else {
            return new IntElement((long) left.ratios_floor(right.value()));
        }
    }
    public CalculableElement<?> mod() {
        if (left.decimal() || right.decimal()) {
            return new NumberElement(left.mods(right.value()));
        } else {
            return new IntElement((long) left.mods(right.value()));
        }
    }
}
