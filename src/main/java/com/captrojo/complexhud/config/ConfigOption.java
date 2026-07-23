package com.captrojo.complexhud.config;

import com.captrojo.complexhud.main.I18nHlpr;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ConfigOption implements IConfigEntry
{
	public static enum Type
	{
		BOOLEAN,
		ENUM,
		INT,
		DOUBLE,
		STRING
	}
	
	boolean enabled = true;
	
	public final Type type;
	public final String key;
	Object value;
	
	Object min;
	Object max;
	
	Enum[] enum_vals;
	
	String unlocalized_name;
	
	public ConfigOption(Type type, String key, Object val)
	{
		if (type == Type.ENUM) {
			throw new IllegalArgumentException("Use the dedicated ENUM constructor!");
		}
		this.type = type;
		this.key = key;
		this.value = val;
		
		this.unlocalized_name = "options.complexhud." + key;
	}
	
	public ConfigOption(Enum[] enum_values, String key, Enum value)
	{
		this.type = Type.ENUM;
		this.key = key;
		this.value = value;
		
		this.enum_vals = enum_values;
		this.min = 0;
		this.max = enum_values.length;
		
		this.unlocalized_name = "options.complexhud." + key;
	}
	
	/* Enable/disable this config option. */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public boolean isEnabled()
	{
		return this.enabled;
	}
	
	/* Set the valid range of values for this config option.
	 * This is not necessary to call when creating an ENUM config option.
	 */
	public ConfigOption setMinMax(Object min, Object max)
	{
		this.min = min;
		this.max = max;
		return this;
	}
	
	/* Set the unlocalized name of this config option, overwriting the default one. */
	public ConfigOption setUnlocalizedName(String unlocalized_name)
	{
		this.unlocalized_name = unlocalized_name;
		return this;
	}
	
	public String getName()
	{
		return I18nHlpr.get(this.unlocalized_name);
	}
	
	public void set(Object val)
	{
		this.value = val;
	}
	
	public void incEnum()
	{
		int v = this.getEnum().ordinal() + 1;
		if (v >= this.enum_vals.length) {
			v = 0;
		}
		this.set(this.enum_vals[v]);
	}
	
	public boolean getBool()
	{
		return (boolean) this.value;
	}
	
	public <T extends Enum> T getEnum()
	{
		return (T) this.value;
	}
	
	public int getInt()
	{
		return (int) this.value;
	}
	
	public double getDouble()
	{
		return (double) this.value;
	}
	
	public String getString()
	{
		return (String) this.value;
	}
	
	public void bindToRange()
	{
		if (this.type == Type.INT) {
			this.value = Math.min(Math.max(this.getInt(), (int) this.min), (int) this.max);
		} else if (this.type == Type.DOUBLE) {
			this.value = Math.min(Math.max(this.getDouble(), (double) this.min), (double) this.max);
		}
	}
	
	@Override
	public void loadFromJson(JsonObject section_obj)
	{
		JsonElement e = section_obj.get(this.key);
		if (e == null || !e.isJsonPrimitive()) {
			return;
		}
		switch (this.type) {
		case BOOLEAN:
			this.value = e.getAsBoolean();
			break;
		case ENUM:
			this.value = this.enum_vals[e.getAsInt()];
			break;
		case INT:
			this.value = e.getAsInt();
			break;
		case DOUBLE:
			this.value = e.getAsDouble();
			break;
		case STRING:
			this.value = e.getAsString();
			break;
		}
	}
	
	@Override
	public void saveToJson(JsonObject section_obj)
	{
		JsonElement e = null;
		switch (this.type) {
		case BOOLEAN:
			e = new JsonPrimitive(this.getBool());
			break;
		case ENUM:
			e = new JsonPrimitive(this.getEnum().ordinal());
			break;
		case INT:
			e = new JsonPrimitive(this.getInt());
			break;
		case DOUBLE:
			e = new JsonPrimitive(this.getDouble());
			break;
		case STRING:
			e = new JsonPrimitive(this.getString());
			break;
		}
		section_obj.add(this.key, e);
	}
}
