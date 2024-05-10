package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.common.Calculator;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;

public abstract class CalculableElement<T extends SBType<?>> extends SBElement<T> {
    public boolean decimal() { return false; }
    public abstract double value();
    public double sums(double a) { return value() + a; }
    public double differences(double a) { return value() - a; }
    public double products(double a) { return value() * a; }
    public double ratios(double a) { return value() / a; }
    public double powers(double a) { return Math.pow(value(), a); }
    public double ratios_floor(double a) { return Math.floor(value() / a); }
    public double mods(double a) { return value() % a; }

    @Override
    public SBElement<?> symbol_add(SBElement<?> other) {
        if (other instanceof CalculableElement<?>) {
            return new Calculator(this, (CalculableElement<?>) other).sum();
        } else {
            return new Calculator(this, other.asNumber()).sum();
        }
    }

    @Override
    public SBElement<?> symbol_sub(SBElement<?> other) {
        if (other instanceof CalculableElement<?>) {
            return new Calculator(this, (CalculableElement<?>) other).difference();
        } else {
            return new Calculator(this, other.asNumber()).difference();
        }
    }

    @Override
    public SBElement<?> symbol_mul(SBElement<?> other) {
        if (other instanceof CalculableElement<?>) {
            return new Calculator(this, (CalculableElement<?>) other).product();
        } else {
            return new Calculator(this, other.asNumber()).product();
        }
    }

    @Override
    public SBElement<?> symbol_div(SBElement<?> other) {
        if (other instanceof CalculableElement<?>) {
            return new Calculator(this, (CalculableElement<?>) other).ratio();
        } else {
            return new Calculator(this, other.asNumber()).ratio();
        }
    }

    @Override
    public SBElement<?> symbol_double_div(SBElement<?> other) {
        if (other instanceof CalculableElement<?>) {
            return new Calculator(this, (CalculableElement<?>) other).ratio_floor();
        } else {
            return new Calculator(this, other.asNumber()).ratio_floor();
        }
    }
    @Override
    public SBElement<?> symbol_double_mul(SBElement<?> other) {
        if (other instanceof CalculableElement<?>) {
            return new Calculator(this, (CalculableElement<?>) other).power();
        } else {
            return new Calculator(this, other.asNumber()).power();
        }
    }
    @Override
    public Integer symbol_compare(SBElement<?> other) {
        return Double.compare(value(), other.asNumber().value());
    }

    @ElementMethod(name = "mod", args = {"Int"}, returnType = "Int")
    public SBElement<?> method_mod(Parser parser, EntrustInst... args) {
        return new Calculator(this, args[0].execute(parser).asInt()).mod();
    }
}
