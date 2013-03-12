package net.minecraft.src;

import java.lang.reflect.Method;

public class ReflectorMethod
{
    private ReflectorClass reflectorClass;
    private String targetMethodName;
    private Class[] targetMethodParameterTypes;
    private boolean checked;
    private Method targetMethod;

    public ReflectorMethod(ReflectorClass var1, String var2)
    {
        this(var1, var2, (Class[])null);
    }

    public ReflectorMethod(ReflectorClass var1, String var2, Class[] var3)
    {
        this.reflectorClass = null;
        this.targetMethodName = null;
        this.targetMethodParameterTypes = null;
        this.checked = false;
        this.targetMethod = null;
        this.reflectorClass = var1;
        this.targetMethodName = var2;
        this.targetMethodParameterTypes = var3;
        Method var4 = this.getTargetMethod();
    }

    public Method getTargetMethod()
    {
        if (this.checked)
        {
            return this.targetMethod;
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
                Method[] var2 = var1.getMethods();
                int var3 = 0;
                Method var4;

                while (true)
                {
                    if (var3 >= var2.length)
                    {
                        Config.log("(Reflector) Method not pesent: " + var1.getName() + "." + this.targetMethodName);
                        return null;
                    }

                    var4 = var2[var3];

                    if (var4.getName().equals(this.targetMethodName))
                    {
                        if (this.targetMethodParameterTypes == null)
                        {
                            break;
                        }

                        Class[] var5 = var4.getParameterTypes();

                        if (Reflector.matchesTypes(this.targetMethodParameterTypes, var5))
                        {
                            break;
                        }
                    }

                    ++var3;
                }

                this.targetMethod = var4;
                return this.targetMethod;
            }
        }
    }

    public boolean exists()
    {
        return this.getTargetMethod() != null;
    }

    public Class getReturnType()
    {
        Method var1 = this.getTargetMethod();
        return var1 == null ? null : var1.getReturnType();
    }
}
