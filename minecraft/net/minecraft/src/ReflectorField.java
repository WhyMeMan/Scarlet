package net.minecraft.src;

import java.lang.reflect.Field;

public class ReflectorField
{
    private ReflectorClass reflectorClass = null;
    private String targetFieldName = null;
    private boolean checked = false;
    private Field targetField = null;

    public ReflectorField(ReflectorClass var1, String var2)
    {
        this.reflectorClass = var1;
        this.targetFieldName = var2;
        Field var3 = this.getTargetField();
    }

    public Field getTargetField()
    {
        if (this.checked)
        {
            return this.targetField;
        }
        else
        {
            this.checked = true;
            Class var1 = this.reflectorClass.getTargetClass();

            if (var1 == null)
            {
                return null;
            }
            else
            {
                try
                {
                    this.targetField = var1.getDeclaredField(this.targetFieldName);
                }
                catch (SecurityException var3)
                {
                    var3.printStackTrace();
                }
                catch (NoSuchFieldException var4)
                {
                    Config.log("(Reflector) Field not present: " + var1.getName() + "." + this.targetFieldName);
                }

                return this.targetField;
            }
        }
    }

    public Object getValue()
    {
        return Reflector.getFieldValue((Object)null, this);
    }

    public void setValue(Object var1)
    {
        Reflector.setFieldValue((Object)null, this, var1);
    }

    public boolean exists()
    {
        return this.getTargetField() != null;
    }
}
