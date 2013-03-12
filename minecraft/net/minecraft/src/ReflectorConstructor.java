package net.minecraft.src;

import java.lang.reflect.Constructor;

public class ReflectorConstructor
{
    private ReflectorClass reflectorClass = null;
    private Class[] parameterTypes = null;
    private boolean checked = false;
    private Constructor targetConstructor = null;

    public ReflectorConstructor(ReflectorClass var1, Class[] var2)
    {
        this.reflectorClass = var1;
        this.parameterTypes = var2;
        Constructor var3 = this.getTargetConstructor();
    }

    public Constructor getTargetConstructor()
    {
        if (this.checked)
        {
            return this.targetConstructor;
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
                this.targetConstructor = findConstructor(var1, this.parameterTypes);

                if (this.targetConstructor == null)
                {
                    Config.dbg("(Reflector) Constructor not present: " + var1.getName() + ", params: " + Config.arrayToString(this.parameterTypes));
                }

                return this.targetConstructor;
            }
        }
    }

    private static Constructor findConstructor(Class var0, Class[] var1)
    {
        Constructor[] var2 = var0.getConstructors();

        for (int var3 = 0; var3 < var2.length; ++var3)
        {
            Constructor var4 = var2[var3];
            Class[] var5 = var4.getParameterTypes();

            if (Reflector.matchesTypes(var1, var5))
            {
                return var4;
            }
        }

        return null;
    }
}
