package net.nosadnile.flow.velocity.config;

import net.nosadnile.flow.velocity.errors.IncompatibleTypeException;
import net.nosadnile.flow.velocity.errors.UnknownKeyException;

import java.io.IOException;
import java.util.Map;

public class ConfigManager {
    private YamlHandler handler;
    private Map<String, Object> fullConfig;

    public ConfigManager(YamlHandler handler) {
        this.handler = handler;
        this.fullConfig = handler.getConfig();
    }

    private Object getValue(String key) {
        try {
            return this.getValueFromKey(key);
        } catch (UnknownKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getObject(String key) {
        return this.getValue(key);
    }

    private Object getValueFromKey(String key) throws UnknownKeyException {
        if (key == "" || key == ".")
            return this.fullConfig;

        String[] keys = key.split("\\.");
        if (keys.length <= 1) {
            return this.fullConfig.get(key);
        } else {
            Object currentKey = this.fullConfig.get(keys[0]);
            for (int i = 1; i < keys.length; i++) {
                if (currentKey instanceof Map) {
                    currentKey = ((Map) currentKey).get(keys[i]);
                } else {
                    throw new UnknownKeyException("Unknown key: " + key);
                }
            }
            return currentKey;
        }
    }

    public String getString(String key) {
        Object value = this.getValue(key);
        if (value instanceof String) {
            return (String) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a string: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean getBoolean(String key) {
        Object value = this.getValue(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a boolean: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public int getInt(String key) {
        Object value = this.getValue(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a integer: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public double getDouble(String key) {
        Object value = this.getValue(key);
        if (value instanceof Double) {
            return (Double) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a double: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public float getFloat(String key) {
        Object value = this.getValue(key);
        if (value instanceof Float) {
            return (Float) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a float: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public long getLong(String key) {
        Object value = this.getValue(key);
        if (value instanceof Long) {
            return (Long) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a long: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public short getShort(String key) {
        Object value = this.getValue(key);
        if (value instanceof Short) {
            return (Short) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a short: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public byte getByte(String key) {
        Object value = this.getValue(key);
        if (value instanceof Byte) {
            return (Byte) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a byte: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public char getChar(String key) {
        Object value = this.getValue(key);
        if (value instanceof Character) {
            return (Character) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a char: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public String[] getStringArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof String[]) {
            return (String[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a string array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void setStringArray(String key, String[] arr) {
        this.setValue(key, arr);
    }

    public boolean[] getBooleanArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof boolean[]) {
            return (boolean[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a boolean array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public int[] getIntArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof int[]) {
            return (int[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a int array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public double[] getDoubleArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof double[]) {
            return (double[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a double array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public float[] getFloatArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof float[]) {
            return (float[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a float array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public long[] getLongArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof long[]) {
            return (long[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a long array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public short[] getShortArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof short[]) {
            return (short[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a short array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public byte[] getByteArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof byte[]) {
            return (byte[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a byte array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public char[] getCharArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof char[]) {
            return (char[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not a char array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public Object[] getObjectArray(String key) {
        Object value = this.getValue(key);
        if (value instanceof Object[]) {
            return (Object[]) value;
        } else {
            try {
                throw new IncompatibleTypeException("Object is not an object array: " + value);
            } catch (IncompatibleTypeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void setValue(String key, Object value) {
        if (this.fullConfig.containsKey(key)) {
            this.fullConfig.replace(key, value);
        } else {
            this.fullConfig.put(key, value);
        }
    }

    public void saveConfig() throws IOException {
        handler.setConfig(this.fullConfig);
        handler.saveConfig();
    }
}
