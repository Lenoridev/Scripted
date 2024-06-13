package net.kapitencraft.scripted.code.exe.methods.param;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kapitencraft.scripted.code.exe.methods.Method;
import net.kapitencraft.scripted.code.var.analysis.VarAnalyser;
import net.kapitencraft.scripted.edit.client.RenderMap;
import net.minecraft.util.GsonHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParamData extends RenderMap {
    private final List<Method<?>.Instance> params = new ArrayList<>();

    public static ParamData of(JsonObject parent, VarAnalyser analyser) {
        ParamData set = new ParamData();
        JsonArray params = GsonHelper.getAsJsonArray(parent, "params");
        params.asList().stream()
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .map(object -> Method.loadInstance(object, analyser))
                .forEach(method -> {
                    method.analyse(analyser);
                    set.addParam(method);
                });
        return set;
    }

    public static ParamData empty() {
        return new ParamData();
    }

    public static ParamData create(List<? extends Method<?>.Instance> list) {
        ParamData data = new ParamData();
        data.params.addAll(list);
        return data;
    }

    public void addParam(Method<?>.Instance method) {
        params.add(method);
    }

    public JsonArray toJson() {
        JsonArray array = new JsonArray();
        this.params.stream().map(Method.Instance::toJson)
                .forEach(array::add);
        return array;
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }

    public List<Method<?>.Instance> getParams() {
        return params;
    }
}