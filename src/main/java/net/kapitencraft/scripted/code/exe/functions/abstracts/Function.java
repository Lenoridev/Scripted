package net.kapitencraft.scripted.code.exe.functions.abstracts;

import net.kapitencraft.scripted.code.exe.MethodPipeline;
import net.kapitencraft.scripted.code.exe.methods.Method;
import net.kapitencraft.scripted.code.var.VarMap;
import net.kapitencraft.scripted.code.var.analysis.IVarAnalyser;
import net.kapitencraft.scripted.code.var.type.abstracts.VarType;
import net.kapitencraft.scripted.init.VarTypes;

public abstract class Function extends Method<Void> {

    public abstract class Instance extends Method<Void>.Instance {

        @Override
        public Void call(VarMap origin, MethodPipeline<?> pipeline) {
            execute(origin, pipeline);
            return null; //always return null
        }

        protected abstract void execute(VarMap map, MethodPipeline<?> pipeline);

        @Override
        public final VarType<Void> getType(IVarAnalyser analyser) {
            return VarTypes.VOID.get();
        }
    }
}
