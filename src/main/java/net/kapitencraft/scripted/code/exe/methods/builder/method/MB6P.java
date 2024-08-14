package net.kapitencraft.scripted.code.exe.methods.builder.method;

import net.kapitencraft.kap_lib.collection.DoubleMap;
import net.kapitencraft.scripted.code.exe.methods.builder.ParamInst;
import net.kapitencraft.scripted.code.exe.methods.builder.ReturningInst;
import net.kapitencraft.scripted.code.var.type.abstracts.VarType;
import net.kapitencraft.scripted.util.Functions;

import java.util.function.Supplier;

public class MB6P<R, P1, P2, P3, P4, P5, P6> implements ReturningInst<P1, R> {
    private final VarType<R> retType;
    private final ParamInst<P1> param1;
    private final ParamInst<P2> param2;
    private final ParamInst<P3> param3;
    private final ParamInst<P4> param4;
    private final ParamInst<P5> param5;
    private final ParamInst<P6> param6;
    private final DoubleMap<VarType<?>, String, MB7P<R, P1, P2, P3, P4, P5, P6, ?>> children = new DoubleMap<>();

    private Functions.F6<P1, P2, P3, P4, P5, P6, R> executor;

    public MB6P(VarType<R> retType, ParamInst<P1> param1, ParamInst<P2> param2, ParamInst<P3> param3, ParamInst<P4> param4, ParamInst<P5> param5, ParamInst<P6> param6) {
        this.retType = retType;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
        this.param6 = param6;
    }

    public <P7> MB7P<R, P1, P2, P3, P4, P5, P6, P7> withParam(String name, Supplier<? extends VarType<P7>> type) {
        return withParam(ParamInst.create(name, type));
    }

    public MB6P<R, P1, P2, P3, P4, P5, P6> executes(Functions.F6<P1, P2, P3, P4, P5, P6, R> executor) {
        if (this.executor != null) throw new IllegalStateException("executor has already been set");
        this.executor = executor;
        return this;
    }

    public <P7> MB7P<R, P1, P2, P3, P4, P5, P6, P7> withParam(ParamInst<P7> inst) {
        return (MB7P<R, P1, P2, P3, P4, P5, P6, P7>) this.children.computeIfAbsent(inst.type(), inst.name(), (type1, string) -> new MB7P<>(retType, param1, param2, param3, param4, param5, param6, inst));
    }

    public <P7, P8, P9, P10> MB10P<R, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10> params(
            ParamInst<P7> param7,
            ParamInst<P8> param8,
            ParamInst<P9> param9,
            ParamInst<P10> param10
    ) {
        return withParam(param7).params(param8, param9, param10);
    }

    public <P7, P8, P9> MB9P<R, P1, P2, P3, P4, P5, P6, P7, P8, P9> params(
            ParamInst<P7> param7,
            ParamInst<P8> param8,
            ParamInst<P9> param9
    ) {
        return withParam(param7).params(param8, param9);
    }

    public <P7, P8> MB8P<R, P1, P2, P3, P4, P5, P6, P7, P8> params(
            ParamInst<P7> param7,
            ParamInst<P8> param8
    ) {
        return withParam(param7).withParam(param8);
    }
}