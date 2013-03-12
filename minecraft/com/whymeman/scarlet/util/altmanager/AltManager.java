package com.whymeman.scarlet.util.altmanager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.*;
import net.minecraft.src.*;

import javax.net.ssl.HttpsURLConnection;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;


public class AltManager extends GuiScreen
{
    public String failed;
    public static boolean showStatus;
    public AltManager(GuiScreen guiscreen)
    {
        m_gParent = guiscreen;
    }

    public void updateScreen()
    {
        serverTextField.updateCursorCounter();
        serverTextField2.updateCursorCounter();
        
    }

    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Login"));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
        serverTextField = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
        serverTextField.setText(mc.session.username);
        serverTextField.setMaxStringLength(50);
        
        serverTextField2 = new PasswordField(fontRenderer, width / 2 - 100, 116, 200, 20);
        serverTextField2.setMaxStringLength(50);
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 0)
        {
        	showStatus = true;
         if(serverTextField2.getText().length() > 0)
         {
        	 String s = serverTextField.getText();
        	 String s1 = serverTextField2.getText();
        	 try{
        		 String s2 = (new StringBuilder("user=")).append(URLEncoder.encode(s, "UTF-8")).append("&password=").append(URLEncoder.encode(s1, "UTF-8")).append("&version=").append(13).toString();
        	 String s3 = excutePost("https://login.minecraft.net", s2);
        	 if(s3 == null || !s3.contains(":"))
        	 {
        		 failed = s3;
                 mc.session = new Session(s, "");
                 showStatus = true;
                 return;
        	 }
        	 String as[] = s3.split(":");
        	 mc.session = new Session(as[2].trim(), as[3].trim());
        	 
        	 }catch(Exception exception){
        		 exception.printStackTrace();

        	 }
         }else{
        	 mc.session = new Session(serverTextField.getText(), "");
         }
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(m_gParent);
        }
    }

    protected void keyTyped(char c, int i)
    {
        serverTextField.textboxKeyTyped(c, i);
        serverTextField2.func_50037_a(c, i);
        if(c == '\t')
        {
            if(serverTextField.isFocused)
            {
            	serverTextField.isFocused = false;
            	serverTextField2.isFocused = true;
            }else
            {
            	serverTextField.isFocused = true;
            	serverTextField2.isFocused = false;
            }
        }
        if(c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }
    }

    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        serverTextField.mouseClicked(i, j, k);
        serverTextField2.mouseClicked(i, j, k);
    }

    public static String excutePost(String s, String s2)
    {
    	HttpsURLConnection httpsurlconnection = null;
    	try
    	{
    		try
    		{
    		URL url = new URL(s);
    		httpsurlconnection = (HttpsURLConnection)url.openConnection();
    		httpsurlconnection.setRequestMethod("POST");
    		httpsurlconnection.setRequestProperty("Content-Type", "application/form-urlencoded");
    		httpsurlconnection.setRequestProperty("Content-Length", Integer.toString(s2.getBytes().length));
    		httpsurlconnection.setRequestProperty("Content-Launguage", "en-US");
    		httpsurlconnection.setUseCaches(false);
    		httpsurlconnection.setDoInput(true);
    		httpsurlconnection.setDoOutput(true);
    		httpsurlconnection.connect();
    		DataOutputStream dataoutputstream = new DataOutputStream(httpsurlconnection.getOutputStream());
    		dataoutputstream.writeBytes(s2);
    		dataoutputstream.flush();
    		dataoutputstream.close();
    		InputStream inputstream = httpsurlconnection.getInputStream();
    		BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
    		StringBuffer stringbuffer = new StringBuffer();
    		String s3;
    		while((s3 = bufferedreader.readLine()) != null)
    		{
    			stringbuffer.append(s3);
    			stringbuffer.append('\r');
    		}
    		bufferedreader.close();
    		String s4 = stringbuffer.toString();
    		String s5 = s4;
    		return s4;
    		}catch(Exception exception){
    			exception.printStackTrace();
    		}
    		return null;
    	}finally{
    		if(httpsurlconnection != null){
    			httpsurlconnection.disconnect();
    		}
    			
    	}
    }
    
    public void drawScreen(int i, int j, float f)
    {
        
        drawDefaultBackground();
        drawCenteredString(fontRenderer, ("Change Login"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, ("Username"), width / 2 - 100, 63, 0xa0a0a0);
        drawString(fontRenderer, ("Password"), width / 2 - 100, 104, 0xa0a0a0);
        if(showStatus)
        {
            if(mc.session.sessionId.length() > 0) {
                drawString(fontRenderer, "Logged in!", width / 2 - 22, 145, 0x2BFF00);
            }
            else {
             drawString(fontRenderer, "Bad Login!", width / 2 - 22, 145, 0xFF0000);
            }
        }
        
      
        
        serverTextField.drawTextBox();
        serverTextField2.drawTextBox();
        super.drawScreen(i, j, f);
       
    }

    private GuiScreen m_gParent;
    private GuiTextField serverTextField;
    private PasswordField serverTextField2;
    private boolean m_bSet;
}
