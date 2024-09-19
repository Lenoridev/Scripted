package net.kapitencraft.scripted.code.var.type;

import net.kapitencraft.scripted.code.var.type.abstracts.VarType;

public class VarTypeRegObject {
    public String name;
    public VarType<?> varType;

    public VarTypeRegObject(String name, VarType<?> varType) {
        this.name = name;
        this.varType = varType;
    }
}
