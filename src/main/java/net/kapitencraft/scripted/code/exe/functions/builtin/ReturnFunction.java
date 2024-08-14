package net.kapitencraft.scripted.code.exe.functions.builtin;

import com.google.gson.JsonObject;
import net.kapitencraft.scripted.code.exe.MethodPipeline;
import net.kapitencraft.scripted.code.exe.functions.abstracts.Function;
import net.kapitencraft.scripted.code.exe.methods.Method;
import net.kapitencraft.scripted.code.var.VarMap;
import net.kapitencraft.scripted.code.var.analysis.VarAnalyser;
import net.kapitencraft.scripted.util.JsonHelper;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;

public class ReturnFunction extends Function {

    @Override
    public Method<Void>.Instance load(JsonObject object, VarAnalyser analyser) {
        return new Instance<>(object.has("ret") ?
                JsonHelper.readMethodChain(GsonHelper.getAsJsonObject(object, "ret"), analyser) :
                null
        );
    }

    public class Instance<T> extends Function.Instance {
        private final @Nullable Method<T>.Instance ret;

        public Instance(@Nullable Method<T>.Instance ret) {
            this.ret = ret;
        }

        @Override
        public void execute(VarMap map, MethodPipeline<?> source) {
            cancelPipeline(map, (MethodPipeline<T>) source);
        }

        private void cancelPipeline(VarMap map, MethodPipeline<T> pipeline) {
            if (this.ret == null) pipeline.setCanceled();
            else pipeline.cancel(this.ret.call(map, pipeline));
        }
    }
}
