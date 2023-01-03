package io.thadow.simplespleef.lib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Small reflection utility class to use CraftBukkit and NMS.
 *
 * @author MrMicky
 */
public final class Reflection {

    private static final String NM_PACKAGE = "net.minecraft";
    public static final String OBC_PACKAGE = "org.bukkit.craftbukkit";
    public static final String NMS_PACKAGE = NM_PACKAGE + ".server";

    public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().substring(OBC_PACKAGE.length() + 1);

    private static final MethodType VOID_METHOD_TYPE = MethodType.methodType(void.class);
    private static final boolean NMS_REPACKAGED = optionalClass(NM_PACKAGE + ".network.protocol.Packet").isPresent();

    private static volatile Object theUnsafe;

    private Reflection() {
        throw new UnsupportedOperationException();
    }

    public static boolean isRepackaged() {
        return NMS_REPACKAGED;
    }

    public static String nmsClassName(String post1_17package, String className) {
        if (NMS_REPACKAGED) {
            String classPackage = post1_17package == null ? NM_PACKAGE : NM_PACKAGE + '.' + post1_17package;
            return classPackage + '.' + className;
        }
        return NMS_PACKAGE + '.' + VERSION + '.' + className;
    }

    public static Class<?> nmsClass(String post1_17package, String className) throws ClassNotFoundException {
        return Class.forName(nmsClassName(post1_17package, className));
    }

    public static Optional<Class<?>> nmsOptionalClass(String post1_17package, String className) {
        return optionalClass(nmsClassName(post1_17package, className));
    }

    public static String obcClassName(String className) {
        return OBC_PACKAGE + '.' + VERSION + '.' + className;
    }

    public static Class<?> obcClass(String className) throws ClassNotFoundException {
        return Class.forName(obcClassName(className));
    }

    public static Optional<Class<?>> obcOptionalClass(String className) {
        return optionalClass(obcClassName(className));
    }

    public static Optional<Class<?>> optionalClass(String className) {
        try {
            return Optional.of(Class.forName(className));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    public static Object enumValueOf(Class<?> enumClass, String enumName) {
        return Enum.valueOf(enumClass.asSubclass(Enum.class), enumName);
    }

    public static Object enumValueOf(Class<?> enumClass, String enumName, int fallbackOrdinal) {
        try {
            return enumValueOf(enumClass, enumName);
        } catch (IllegalArgumentException e) {
            Object[] constants = enumClass.getEnumConstants();
            if (constants.length > fallbackOrdinal) {
                return constants[fallbackOrdinal];
            }
            throw e;
        }
    }

    public static Class<?> innerClass(Class<?> parentClass, Predicate<Class<?>> classPredicate) throws ClassNotFoundException {
        for (Class<?> innerClass : parentClass.getDeclaredClasses()) {
            if (classPredicate.test(innerClass)) {
                return innerClass;
            }
        }
        throw new ClassNotFoundException("No class in " + parentClass.getCanonicalName() + " matches the predicate.");
    }

    public static PacketConstructor findPacketConstructor(Class<?> packetClass, MethodHandles.Lookup lookup) throws Exception {
        try {
            MethodHandle constructor = lookup.findConstructor(packetClass, VOID_METHOD_TYPE);
            return constructor::invoke;
        } catch (NoSuchMethodException | IllegalAccessException e) {
            // try below with Unsafe
        }

        if (theUnsafe == null) {
            synchronized (Reflection.class) {
                if (theUnsafe == null) {
                    Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
                    Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
                    theUnsafeField.setAccessible(true);
                    theUnsafe = theUnsafeField.get(null);
                }
            }
        }

        MethodType allocateMethodType = MethodType.methodType(Object.class, Class.class);
        MethodHandle allocateMethod = lookup.findVirtual(theUnsafe.getClass(), "allocateInstance", allocateMethodType);
        return () -> allocateMethod.invoke(theUnsafe, packetClass);
    }

    @FunctionalInterface
    public interface PacketConstructor {
        Object invoke() throws Throwable;
    }

    public static Class<?> getNMSClass(final String name) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return getClass("net.minecraft.server." + version + "." + name);
    }

    public static Class<?> getNMSClassArray(final String name) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return getClass("[Lnet.minecraft.server." + version + "." + name + ";");
    }

    public static Class<?> getCraftClass(final String name) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return getClass("org.bukkit.craftbukkit." + version + "." + name);
    }

    public static Class<?> getBukkitClass(final String name) {
        return getClass("org.bukkit." + name);
    }

    public static void sendPacket(final Player player, final Object packet) {
        try {
            final Object handle = player.getClass().getMethod("getHandle", (Class<?>[]) new Class[0]).invoke(player, new Object[0]);
            final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Object getFieldValue(final Object obj, final String name) {
        try {
            final Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception exception) {
            return null;
        }
    }

    public static void setFieldValue(final Object obj, final String name, final Object value) {
        try {
            final Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception ex) {
        }
    }

    public static Class<?> getClass(final String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static enum Version {
        v1_5(10500),
        v1_6(10600),
        v1_7(10700),
        v1_8_8(10808),
        v1_8_9(10809),
        v1_9(10900),
        v1_9_1(10901),
        v1_9_2(10902),
        v1_9_3(10903),
        v1_9_4(10904),
        v1_9_x(10910),
        v1_10(11000),
        v1_10_1(11001),
        v1_10_2(11002),
        v1_10_x(11010),
        v1_11(11100),
        v1_11_1(11101),
        v1_11_2(11102),
        v1_11_x(11110),
        v1_12(11200),
        v1_12_1(11201),
        v1_12_2(11202),
        v1_12_x(11210),
        v1_13(11300),
        v1_13_1(11301),
        v1_13_2(11302),
        v1_13_x(11310),
        v1_14(11400),
        v1_14_1(11401),
        v1_14_2(11402),
        v1_14_3(11403),
        v1_14_4(11404),
        v1_14_x(11410),
        v1_15(11500),
        v1_15_1(11501),
        v1_15_2(11502),
        v1_15_x(11510),
        v1_16(11600),
        v1_16_1(11601),
        v1_16_2(11602),
        v1_16_3(11603),
        v1_16_4(11604),
        v1_16_5(11605),
        v1_16_x(11610),
        v1_17(11700),
        v1_17_1(11701),
        v1_17_x(11710),
        v1_18(11800),
        v1_18_1(11801),
        v1_18_2(11802),
        v1_18_x(11810),
        v1_19(11900),
        v1_19_1(11901),
        v1_19_2(11902),
        v1_19_x(11910),
        vUnsupported(1000000);

        private int value;

        private boolean contains;

        private boolean equals;

        Version(int n) {
            this.value = n;
            this.contains = Bukkit.getBukkitVersion().split("-")[0].contains(toString());
            this.equals = Bukkit.getBukkitVersion().split("-")[0].equalsIgnoreCase(toString());
        }

        private int getValue() {
            return this.value;
        }

        public boolean Is() {
            return this.contains;
        }

        public boolean IsEquals() {
            return this.equals;
        }

        public String toString() {
            return name().replaceAll("_", ".").split("v")[1];
        }

        public boolean esMayor(Version v) {
            return (getValue() > v.getValue());
        }

        public boolean esMayorIgual(Version v) {
            return (getValue() >= v.getValue());
        }

        public boolean esMenor(Version v) {
            return (getValue() < v.getValue());
        }

        public boolean esMenorIgual(Version v) {
            return (getValue() <= v.getValue());
        }

        public static Version getVersion() {
            Version retorno = vUnsupported;
            byte b;
            int i;
            Version[] arrayOfCoreVersion;
            for (i = (arrayOfCoreVersion = values()).length, b = 0; b < i; ) {
                Version version = arrayOfCoreVersion[b];
                if (version.IsEquals()) {
                    retorno = version;
                    break;
                }
                b++;
            }
            return retorno;
        }
    }
}
