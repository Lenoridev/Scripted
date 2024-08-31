package net.kapitencraft.scripted.code.exe.methods.builder.node.method;

import com.google.gson.JsonObject;
import net.kapitencraft.kap_lib.stream.Functions;
import net.kapitencraft.scripted.code.exe.MethodPipeline;
import net.kapitencraft.scripted.code.exe.methods.builder.ParamInst;
import net.kapitencraft.scripted.code.exe.methods.builder.node.ReturningNode;
import net.kapitencraft.scripted.code.exe.methods.core.Method;
import net.kapitencraft.scripted.code.exe.methods.core.MethodInstance;
import net.kapitencraft.scripted.code.var.VarMap;
import net.kapitencraft.scripted.code.var.analysis.IVarAnalyser;
import net.kapitencraft.scripted.code.var.analysis.VarAnalyser;
import net.kapitencraft.scripted.code.var.type.abstracts.VarType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class MN6P<R, P1, P2, P3, P4, P5, P6> implements ReturningNode<R> {
    private final VarType<R> retType;
    private final ParamInst<P1> param1;
    private final ParamInst<P2> param2;
    private final ParamInst<P3> param3;
    private final ParamInst<P4> param4;
    private final ParamInst<P5> param5;
    private final ParamInst<P6> param6;

    private final @Nullable Functions.F6<P1, P2, P3, P4, P5, P6, R> executor;

    public MN6P(VarType<R> retType, ParamInst<P1> param1, ParamInst<P2> param2, ParamInst<P3> param3, ParamInst<P4> param4, ParamInst<P5> param5, ParamInst<P6> param6, @Nullable Functions.F6<P1, P2, P3, P4, P5, P6, R> executor) {
        this.retType = retType;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
        this.param6 = param6;
        this.executor = executor;
    }

    public MethodInstance<R> read(JsonObject object, VarAnalyser analyser) {
        if (executor == null) throw new IllegalAccessError("can not create a Method without executor");
        return new Instance(
                Method.loadInstance(object, param1.name(), analyser),
                Method.loadInstance(object, param2.name(), analyser),
                Method.loadInstance(object, param3.name(), analyser),
                Method.loadInstance(object, param4.name(), analyser),
                Method.loadInstance(object, param5.name(), analyser),
                Method.loadInstance(object, param6.name(), analyser)
        );
    }

    public MethodInstance<R> createInst(List<MethodInstance<?>> params) {
        return create((MethodInstance<P1>) params.get(0), (MethodInstance<P2>) params.get(1),
                (MethodInstance<P3>) params.get(2), (MethodInstance<P4>) params.get(3),
                (MethodInstance<P5>) params.get(4), (MethodInstance<P6>) params.get(5));
    }

    public MethodInstance<R> create(MethodInstance<P1> param1Inst, MethodInstance<P2> param2Inst,
                                    MethodInstance<P3> param3Inst, MethodInstance<P4> param4Inst,
                                    MethodInstance<P5> param5Inst, MethodInstance<P6> param6Inst) {
        return new Instance(param1Inst, param2Inst, param3Inst, param4Inst, param5Inst, param6Inst);
    }

    @Override
    public int getParamCount() {
        return 2;
    }

    private class Instance extends MethodInstance<R> {
        private final MethodInstance<P1> param1;
        private final MethodInstance<P2> param2;
        private final MethodInstance<P3> param3;
        private final MethodInstance<P4> param4;
        private final MethodInstance<P5> param5;
        private final MethodInstance<P6> param6;

        private Instance(MethodInstance<P1> param1, MethodInstance<P2> param2, MethodInstance<P3> param3, MethodInstance<P4> param4, MethodInstance<P5> param5, MethodInstance<P6> param6) {
            this.param1 = param1;
            this.param2 = param2;
            this.param3 = param3;
            this.param4 = param4;
            this.param5 = param5;
            this.param6 = param6;
        }

        @Override
        public R call(VarMap origin, MethodPipeline<?> pipeline) {
            return Objects.requireNonNull(executor, "found method without executor!")
                    .apply(
                            this.param1.call(origin, pipeline),
                            this.param2.call(origin, pipeline),
                            this.param3.call(origin, pipeline),
                            this.param4.call(origin, pipeline),
                            this.param5.call(origin, pipeline),
                            this.param6.call(origin, pipeline)
                    );
        }

        @Override
        protected void saveAdditional(JsonObject object) {
            object.add(MN6P.this.param1.name(), param1.toJson());
            object.add(MN6P.this.param2.name(), param2.toJson());
            object.add(MN6P.this.param3.name(), param3.toJson());
            object.add(MN6P.this.param4.name(), param4.toJson());
            object.add(MN6P.this.param5.name(), param5.toJson());
            object.add(MN6P.this.param6.name(), param6.toJson());
        }

        @Override
        public VarType<R> getType(IVarAnalyser analyser) {
            return retType;
        }
    }
}
