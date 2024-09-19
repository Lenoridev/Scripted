package net.kapitencraft.scripted.code.var.type;

public class CustomVarType {
    public String name;
    public Class<?> clazz;
    public String add;
    public String mul;
    public String div;
    public String sub;
    public String mod;
    public String com;

    public CustomVarType(String name, Class<?> clazz, String add, String mul, String div, String sub, String mod, String com) {
        this.name = name;
        this.clazz = clazz;
        this.add = add;
        this.mul = mul;
        this.div = div;
        this.sub = sub;
        this.mod = mod;
        this.com = com;
    }
}
