package net.minecraft.src;

public class GuiVideoSettings extends GuiScreen
{
    private GuiScreen parentGuiScreen;

    /** The title string that is displayed in the top-center of the screen. */
    protected String screenTitle = "Video Settings";

    /** GUI game settings */
    private GameSettings guiGameSettings;

    /**
     * True if the system is 64-bit (using a simple indexOf test on a system property)
     */
    private boolean is64bit = false;

    /** An array of all of EnumOption's video options. */
    private static EnumOptions[] videoOptions = new EnumOptions[] {EnumOptions.GRAPHICS, EnumOptions.RENDER_DISTANCE_FINE, EnumOptions.AO_LEVEL, EnumOptions.FRAMERATE_LIMIT_FINE, EnumOptions.ANAGLYPH, EnumOptions.VIEW_BOBBING, EnumOptions.GUI_SCALE, EnumOptions.ADVANCED_OPENGL, EnumOptions.GAMMA, EnumOptions.CHUNK_LOADING, EnumOptions.FOG_FANCY, EnumOptions.FOG_START};
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;

    public GuiVideoSettings(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        this.parentGuiScreen = par1GuiScreen;
        this.guiGameSettings = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate var1 = StringTranslate.getInstance();
        this.screenTitle = var1.translateKey("options.videoTitle");
        int var2 = 0;
        EnumOptions[] var3 = videoOptions;
        int var4 = var3.length;
        int var5;
        int var7;

        for (var5 = 0; var5 < var4; ++var5)
        {
            EnumOptions var6 = var3[var5];
            var7 = this.width / 2 - 155 + var5 % 2 * 160;
            int var8 = this.height / 6 + 21 * (var5 / 2) - 10;

            if (var6.getEnumFloat())
            {
                this.controlList.add(new GuiSlider(var6.returnEnumOrdinal(), var7, var8, var6, this.guiGameSettings.getKeyBinding(var6), this.guiGameSettings.getOptionFloatValue(var6)));
            }
            else
            {
                this.controlList.add(new GuiSmallButton(var6.returnEnumOrdinal(), var7, var8, var6, this.guiGameSettings.getKeyBinding(var6)));
            }

            ++var2;
        }

        int var13 = this.height / 6 + 21 * (var5 / 2) - 10;
        boolean var14 = false;
        var7 = this.width / 2 - 155 + 0;
        this.controlList.add(new GuiSmallButton(101, var7, var13, "Details..."));
        var7 = this.width / 2 - 155 + 160;
        this.controlList.add(new GuiSmallButton(102, var7, var13, "Quality..."));
        var13 += 21;
        var7 = this.width / 2 - 155 + 0;
        this.controlList.add(new GuiSmallButton(111, var7, var13, "Animations..."));
        var7 = this.width / 2 - 155 + 160;
        this.controlList.add(new GuiSmallButton(112, var7, var13, "Performance..."));
        var13 += 21;
        var7 = this.width / 2 - 155 + 0;
        this.controlList.add(new GuiSmallButton(121, var7, var13, "Texture Packs..."));
        var7 = this.width / 2 - 155 + 160;
        this.controlList.add(new GuiSmallButton(122, var7, var13, "Other..."));
        this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, var1.translateKey("gui.done")));
        this.is64bit = false;
        String[] var15 = new String[] {"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        String[] var9 = var15;
        var5 = var15.length;

        for (int var10 = 0; var10 < var5; ++var10)
        {
            String var11 = var9[var10];
            String var12 = System.getProperty(var11);

            if (var12 != null && var12.contains("64"))
            {
                this.is64bit = true;
                break;
            }
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            int var2 = this.guiGameSettings.guiScale;

            if (par1GuiButton.id < 100 && par1GuiButton instanceof GuiSmallButton)
            {
                this.guiGameSettings.setOptionValue(((GuiSmallButton)par1GuiButton).returnEnumOptions(), 1);
                par1GuiButton.displayString = this.guiGameSettings.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
            }

            if (par1GuiButton.id == 200)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }

            if (this.guiGameSettings.guiScale != var2)
            {
                ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                int var4 = var3.getScaledWidth();
                int var5 = var3.getScaledHeight();
                this.setWorldAndResolution(this.mc, var4, var5);
            }

            if (par1GuiButton.id == 101)
            {
                this.mc.gameSettings.saveOptions();
                GuiDetailSettingsOF var6 = new GuiDetailSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var6);
            }

            if (par1GuiButton.id == 102)
            {
                this.mc.gameSettings.saveOptions();
                GuiQualitySettingsOF var7 = new GuiQualitySettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var7);
            }

            if (par1GuiButton.id == 111)
            {
                this.mc.gameSettings.saveOptions();
                GuiAnimationSettingsOF var8 = new GuiAnimationSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var8);
            }

            if (par1GuiButton.id == 112)
            {
                this.mc.gameSettings.saveOptions();
                GuiPerformanceSettingsOF var9 = new GuiPerformanceSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var9);
            }

            if (par1GuiButton.id == 121)
            {
                this.mc.gameSettings.saveOptions();
                GuiTexturePacks var10 = new GuiTexturePacks(this);
                this.mc.displayGuiScreen(var10);
            }

            if (par1GuiButton.id == 122)
            {
                this.mc.gameSettings.saveOptions();
                GuiOtherSettingsOF var11 = new GuiOtherSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(var11);
            }

            if (par1GuiButton.id == EnumOptions.AO_LEVEL.ordinal())
            {
                return;
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);

        if (Math.abs(par1 - this.lastMouseX) <= 5 && Math.abs(par2 - this.lastMouseY) <= 5)
        {
            short var4 = 700;

            if (System.currentTimeMillis() >= this.mouseStillTime + (long)var4)
            {
                int var5 = this.width / 2 - 150;
                int var6 = this.height / 6 - 5;

                if (par2 <= var6 + 98)
                {
                    var6 += 105;
                }

                int var7 = var5 + 150 + 150;
                int var8 = var6 + 84 + 10;
                GuiButton var9 = this.getSelectedButton(par1, par2);

                if (var9 != null)
                {
                    String var10 = this.getButtonName(var9.displayString);
                    String[] var11 = this.getTooltipLines(var10);

                    if (var11 == null)
                    {
                        return;
                    }

                    this.drawGradientRect(var5, var6, var7, var8, -536870912, -536870912);

                    for (int var12 = 0; var12 < var11.length; ++var12)
                    {
                        String var13 = var11[var12];
                        this.fontRenderer.drawStringWithShadow(var13, var5 + 5, var6 + 5 + var12 * 11, 14540253);
                    }
                }
            }
        }
        else
        {
            this.lastMouseX = par1;
            this.lastMouseY = par2;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private String[] getTooltipLines(String var1)
    {
        return var1.equals("Graphics") ? new String[] {"Visual quality", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Changes the appearance of clouds, leaves, water,", "shadows and grass sides."}: (var1.equals("Render Distance") ? new String[] {"Visible distance", "  Tiny - 32m (fastest)", "  Short - 64m (faster)", "  Normal - 128m", "  Far - 256m (slower)", "  Extreme - 512m (slowest!)", "The Extreme view distance is very resource demanding!"}: (var1.equals("Smooth Lighting") ? new String[] {"Smooth lighting", "  OFF - no smooth lighting (faster)", "  1% - light smooth lighting (slower)", "  100% - dark smooth lighting (slower)"}: (var1.equals("Performance") ? new String[] {"FPS Limit", "  Max FPS - no limit (fastest)", "  Balanced - limit 120 FPS (slower)", "  Power saver - limit 40 FPS (slowest)", "  VSync - limit to monitor framerate (60, 30, 20)", "Balanced and Power saver decrease the FPS even if", "the limit value is not reached."}: (var1.equals("3D Anaglyph") ? new String[] {"3D mode used with red-cyan 3D glasses."}: (var1.equals("View Bobbing") ? new String[] {"More realistic movement.", "When using mipmaps set it to OFF for best results."}: (var1.equals("GUI Scale") ? new String[] {"GUI Scale", "Smaller GUI might be faster"}: (var1.equals("Advanced OpenGL") ? new String[] {"Detect and render only visible geometry", "  OFF - all geometry is rendered (slower)", "  Fast - only visible geometry is rendered (fastest)", "  Fancy - conservative, avoids visual artifacts (faster)", "The option is available only if it is supported by the ", "graphic card."}: (var1.equals("Fog") ? new String[] {"Fog type", "  Fast - faster fog", "  Fancy - slower fog, looks better", "  OFF - no fog, fastest", "The fancy fog is available only if it is supported by the ", "graphic card."}: (var1.equals("Fog Start") ? new String[] {"Fog start", "  0.2 - the fog starts near the player", "  0.8 - the fog starts far from the player", "This option usually does not affect the performance."}: (var1.equals("Brightness") ? new String[] {"Increases the brightness of darker objects", "  OFF - standard brightness", "  100% - maximum brightness for darker objects", "This options does not change the brightness of ", "fully black objects"}: (var1.equals("Chunk Loading") ? new String[] {"Chunk Loading", "  Default - unstable FPS when loading chunks", "  Smooth - stable FPS", "  Multi-Core - stable FPS, 3x faster world loading", "Smooth and Multi-Core remove the stuttering and freezes", "caused by chunk loading.", "Multi-Core can speed up 3x the world loading and", "increase FPS by using a second CPU core."}: null)))))))))));
    }

    private String getButtonName(String var1)
    {
        int var2 = var1.indexOf(58);
        return var2 < 0 ? var1 : var1.substring(0, var2);
    }

    private GuiButton getSelectedButton(int var1, int var2)
    {
        for (int var3 = 0; var3 < this.controlList.size(); ++var3)
        {
            GuiButton var4 = (GuiButton)this.controlList.get(var3);
            boolean var5 = var1 >= var4.xPosition && var2 >= var4.yPosition && var1 < var4.xPosition + var4.width && var2 < var4.yPosition + var4.height;

            if (var5)
            {
                return var4;
            }
        }

        return null;
    }
}
