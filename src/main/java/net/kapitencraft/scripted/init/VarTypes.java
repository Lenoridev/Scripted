package net.kapitencraft.scripted.init;

import net.kapitencraft.scripted.Scripted;
import net.kapitencraft.scripted.code.var.type.*;
import net.kapitencraft.scripted.code.var.type.abstracts.VarType;
import net.kapitencraft.scripted.code.var.type.data.DataStorageType;
import net.kapitencraft.scripted.code.var.type.entity.EntityType;
import net.kapitencraft.scripted.code.var.type.entity.LivingEntityType;
import net.kapitencraft.scripted.code.var.type.math.BlockPosType;
import net.kapitencraft.scripted.code.var.type.math.Vec2Type;
import net.kapitencraft.scripted.code.var.type.math.Vec3Type;
import net.kapitencraft.scripted.code.var.type.primitive.*;
import net.kapitencraft.scripted.code.var.type.registry.*;
import net.kapitencraft.scripted.init.custom.ModRegistryKeys;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface VarTypes {
    DeferredRegister<VarType<?>> REGISTRY = Scripted.createRegistry(ModRegistryKeys.VAR_TYPES);


    static <I, T extends VarType<I>> RegistryObject<T> register(String name, Supplier<T> register) {
        return REGISTRY.register(name, register);
    }

    static void registerCustom() {
        for (CustomVarType customVarType : VarType.CLASS_MAP) {
            RegistryObject obj = register(customVarType.name, TypeRegister.registerVarType(customVarType.clazz, customVarType.add, customVarType.mul, customVarType.div, customVarType.sub, customVarType.mod, customVarType.com)::new)
        }
    }

    //special
    RegistryObject<VarType<Void>> VOID = register("void", VoidType::new);

    //primitives
    RegistryObject<IntegerType> INTEGER = register("integer", IntegerType::new);
    RegistryObject<DoubleType> DOUBLE = register("double", DoubleType::new);
    RegistryObject<StringType> STRING = register("string", StringType::new);
    RegistryObject<CharType> CHAR = register("char", CharType::new);
    RegistryObject<BooleanType> BOOL = register("bool", BooleanType::new);

    //math-helper
    RegistryObject<Vec3Type> VEC3 = register("vec3", Vec3Type::new);
    RegistryObject<Vec2Type> VEC2 = register("vec2", Vec2Type::new);
    RegistryObject<BlockPosType> BLOCK_POS = register("block_pos", BlockPosType::new);

    //data
    RegistryObject<DataStorageType> DATA_STORAGE = REGISTRY.register("data_storage", DataStorageType::new);


    //registry
    RegistryObject<ItemType> ITEM = register("item", ItemType::new);
    RegistryObject<BlockType> BLOCK = register("block", BlockType::new);
    RegistryObject<EntityTypeType> ENTITY_TYPE = register("entity_type", EntityTypeType::new);
    RegistryObject<AttributeType> ATTRIBUTE = register("attribute", AttributeType::new);
    RegistryObject<EnchantmentType>  ENCHANTMENT = register("enchantment", EnchantmentType::new);
    RegistryObject<MobEffectType> MOB_EFFECT = register("mob_effect", MobEffectType::new);

    //entity
    RegistryObject<EntityType<Entity>> ENTITY = register("entity", () -> new EntityType<>("Entity"));
    RegistryObject<LivingEntityType<LivingEntity>> LIVING_ENTITY = register("living_entity", ()-> new LivingEntityType<>("LivingEntity"));

    //other
    RegistryObject<LevelType> LEVEL = register("level", LevelType::new);
    RegistryObject<ItemStackType> ITEM_STACK = register("item_stack", ItemStackType::new);
    RegistryObject<BlockStateType> BLOCK_STATE = register("block_state", BlockStateType::new);
    //RegistryObject<BlockStatePropertyType<?>> BLOCK_STATE_PROPERTY = register("block_state_property", BlockStatePropertyType::new); //TODO make generic var types
}