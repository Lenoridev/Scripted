package net.kapitencraft.scripted.code.var.type;

import net.kapitencraft.scripted.code.var.type.abstracts.VarType;
import net.kapitencraft.scripted.code.var.type.primitive.IntegerType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static net.kapitencraft.scripted.init.VarTypes.register;

public class TypeRegister {
    public static VarType<?> registerVarType(Class clazz, String addMethodName, String mulMethodName, String divMethodName,
                               String subMethodName, String modMethodName, String comMethodName) {
        String name = clazz.getName();

        VarType<?> varType;
        varType = new VarType<>(name,
                invokeMethod(clazz, addMethodName),
                invokeMethod(clazz, mulMethodName),
                invokeMethod(clazz, divMethodName),
                invokeMethod(clazz, subMethodName),
                invokeMethod(clazz, modMethodName),
                invokeComparator(clazz, comMethodName)
                );

        return varType;
    }

    private static <T> BiFunction<T, T, T> invokeMethod(Class<T> clazz, String name) {
        if (clazz == null) return null;

        try {
           Method m = name != null ? clazz.getMethod(name) : null;
           if (m == null) return null;

           return (a, b) -> {
                try {
                    return (T) m.invoke(a, b);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("An error occurred while invoking method '" + m.getName() + "'! Is the method private?");
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Exception while executing method '" + m.getName() + "': " + e.getMessage());
                }
           };
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Specified type '" + clazz.getName() + "' does not contain the method '" +
                    name + "'");
        }
    }

    private static <T> Comparator<T> invokeComparator(Class<T> clazz, String name) {
        if (clazz == null) return null;

        try {
            Method m = name != null ? clazz.getMethod(name) : null;
            if (m == null) return null;

            return (a, b) -> {
                try {
                    return (int) m.invoke(a, b);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("An error occurred while invoking method '" + m.getName() + "'! Is the method private?");
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Exception while executing method '" + m.getName() + "': " + e.getMessage());
                }
            };
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Specified type '" + clazz.getName() + "' does not contain the method '" +
                    name + "'");
        }
    }
}
