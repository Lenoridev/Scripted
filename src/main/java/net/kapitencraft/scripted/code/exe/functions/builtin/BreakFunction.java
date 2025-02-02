package net.kapitencraft.scripted.code.exe.functions.builtin;

import com.google.gson.JsonObject;
import net.kapitencraft.scripted.code.exe.MethodPipeline;
import net.kapitencraft.scripted.code.exe.functions.abstracts.Function;
import net.kapitencraft.scripted.code.exe.methods.core.MethodInstance;
import net.kapitencraft.scripted.code.var.VarMap;
import net.kapitencraft.scripted.code.var.analysis.VarAnalyser;

public class BreakFunction extends Function {

    @Override
    public Function.Instance load(JsonObject object, VarAnalyser analyser) {
        return new Instance();
    }

    public MethodInstance<?> create() {
        return new Instance();
    }

    private static class Instance extends Function.Instance {
        public Instance() {
            super("break");
        }

        @Override
        protected void execute(VarMap map, MethodPipeline<?> pipeline) {
            pipeline.setBroken();
        }
    }
}
