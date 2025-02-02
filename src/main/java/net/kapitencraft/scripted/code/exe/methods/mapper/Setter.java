package net.kapitencraft.scripted.code.exe.methods.mapper;

import net.kapitencraft.scripted.code.exe.MethodPipeline;
import net.kapitencraft.scripted.code.exe.methods.core.MethodInstance;
import net.kapitencraft.scripted.code.var.VarMap;
import net.kapitencraft.scripted.code.var.type.abstracts.VarType;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record Setter<T>(Setter.Type type, VarType<T> varType,
                        @Nullable MethodInstance<T> setter) {

    //TODO remove FUnctions -> methods

    public T createVal(T in, VarMap map, MethodPipeline<?> pipeline) {
        return switch (type) {
            case SET -> getSetterValue(map, pipeline);
            case GROW -> varType.add(in, varType.one());
            case SHRINK -> varType.sub(in, varType.negOne());
            case ADD_EQUAL -> varType.add(in, getSetterValue(map, pipeline));
            case DIV_EQUAL -> varType.divide(in, getSetterValue(map, pipeline));
            case MUL_EQUAL -> varType.multiply(in, getSetterValue(map, pipeline));
            case SUB_EQUAL -> varType.sub(in, getSetterValue(map, pipeline));
            case MOD_EQUAL -> varType.mod(in, getSetterValue(map, pipeline));
        };
    }

    private T getSetterValue(VarMap map, MethodPipeline<?> pipeline) {
        return Objects.requireNonNull(setter, "setter expected").call(map, pipeline);
    }

    public enum Type implements StringRepresentable {
        GROW("grow", false), SHRINK("shrink", false),
        ADD_EQUAL("add_equal", true), MUL_EQUAL("mul_equal", true), DIV_EQUAL("div_equal", true), SUB_EQUAL("sub_equal", true), MOD_EQUAL("mod_equal", true),
        SET("set", true);

        public static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        public static Type readType(String id) {
            return switch (id) {
                case "=" -> SET;
                case "&=" -> ADD_EQUAL;
                case "*=" -> MUL_EQUAL;
                case "/=" -> DIV_EQUAL;
                case "-=" -> SUB_EQUAL;
                case "++" -> GROW;
                case "--" -> SHRINK;
                default -> throw new IllegalArgumentException("unknown assign operator: " + id);
            };
        }

        private final String name;
        private final boolean reqSetter;

        Type(String name, boolean reqSetter) {
            this.name = name;
            this.reqSetter = reqSetter;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }

        public boolean requiresSetter() {
            return reqSetter;
        }
    }
}
